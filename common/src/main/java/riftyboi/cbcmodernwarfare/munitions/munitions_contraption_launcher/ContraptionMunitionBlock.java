package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.equipment.wrench.IWrenchable;

import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.munitions.big_cannon.BigCannonMunitionBlock;
import rbasamoyai.createbigcannons.remix.CBCExplodableBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlock;

public class ContraptionMunitionBlock extends DirectionalBlock implements IWrenchable, PropelledContraptionMunitionBlock, CBCExplodableBlock {

	private final VoxelShaper shapes;

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty DAMP = BooleanProperty.create("damp");

	public ContraptionMunitionBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.UP));
		this.shapes = this.makeShapes();
	}

	static boolean canDry(BlockState state) { return !state.getValue(WATERLOGGED) && state.getValue(DAMP); }

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	protected VoxelShaper makeShapes() {
		VoxelShape base = box(3, 0, 3, 13, 16, 13);
		return new AllShapes.Builder(base).forDirectional();
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Player player = context.getPlayer();
		Direction facing = context.getClickedFace();
		boolean flag = player != null && player.isShiftKeyDown();
		BlockState clickedState = context.getLevel().getBlockState(context.getClickedPos().relative(facing.getOpposite()));

		if (clickedState.getBlock() instanceof MunitionsLauncherBlock cblock
			&& cblock.getFacing(clickedState).getAxis() == facing.getAxis()
			&& !flag) {
			facing = facing.getOpposite();
		}
		return this.defaultBlockState().setValue(FACING, facing);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
		return this.shapes.get(state.getValue(FACING));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
	}


	@Override
	public boolean canBeLoaded(BlockState state, Direction.Axis facing) {
		return state.getValue(FACING).getAxis() == facing;
	}

	@Override
	public BlockState onCannonRotate(BlockState oldState, Direction.Axis rotationAxis, Rotation rotation) {
		Direction facing = oldState.getValue(BlockStateProperties.FACING);
		for (int i = 0; i < rotation.ordinal(); ++i) {
			facing = facing.getClockWise(rotationAxis);
		}
		return oldState.setValue(BlockStateProperties.FACING, facing);
	}

	@Override
	public StructureTemplate.StructureBlockInfo getHandloadingInfo(ItemStack stack, BlockPos localPos, Direction cannonOrientation) {
		BlockState state = this.defaultBlockState().setValue(FACING, cannonOrientation);
		CompoundTag baseTag = stack.getOrCreateTag();
		if (baseTag.contains("BlockEntityTag")) {
			CompoundTag tag = baseTag.getCompound("BlockEntityTag").copy();
			tag.remove("x");
			tag.remove("y");
			tag.remove("z");
			return new StructureTemplate.StructureBlockInfo(localPos, state, tag);
		}
		return new StructureTemplate.StructureBlockInfo(localPos, state, null);
	}

	@Override
	public ItemStack getExtractedItem(StructureTemplate.StructureBlockInfo info) {
		ItemStack stack = new ItemStack(this);
		if (info.nbt() != null) {
			stack.getOrCreateTag().put("BlockEntityTag", info.nbt());
		}
		return stack;
	}

	@Override public Direction.Axis getAxis(BlockState state) { return state.getValue(FACING).getAxis(); }

	static boolean doesntIgnite(BlockState state) {
		return state.getValue(WATERLOGGED) || state.getValue(DAMP) || !CBCConfigs.server().munitions.munitionBlocksCanExplode.get();
	}
}
