package riftyboi.cbcmodernwarfare.content.smoke_discharger;

import com.simibubi.create.content.equipment.wrench.IWrenchable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;


public class SmokeDischargerBlock extends DirectionalBlock implements IBE<SmokeDischargerBlockEntity> {

	public static final BooleanProperty CEILING = BooleanProperty.create("ceiling");
	public static final BooleanProperty POWERED = BooleanProperty.create("powered");

	public SmokeDischargerBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any()
			.setValue(FACING, Direction.NORTH)
			.setValue(POWERED, false));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(FACING, POWERED, CEILING));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return defaultBlockState().setValue(CEILING, ctx.getClickedFace() == Direction.DOWN).setValue(FACING, ctx.getHorizontalDirection());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos,
							   CollisionContext context) {
		return state.getValue(CEILING) ? AllShapes.MECHANICAL_ARM_CEILING : AllShapes.MECHANICAL_ARM;
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		boolean prevFirePowered = state.getValue(POWERED);
		boolean firePowered = level.hasNeighborSignal(pos);
		this.withBlockEntityDo(level, pos, sdbe -> sdbe.onRedstoneUpdate(firePowered, prevFirePowered));
	}


	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean isMoving) {
		if (!level.isClientSide) {
			if (!level.getBlockTicks().willTickThisTick(pos, this)) {
				level.scheduleTick(pos, this, 0);
			}
		}
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
	public Class<SmokeDischargerBlockEntity> getBlockEntityClass() {
		return SmokeDischargerBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends SmokeDischargerBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.SMOKE_DISCHARGER.get();
	}



	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!level.isClientSide()) {
			withBlockEntityDo(level, pos, smbe -> smbe.dropItems(level, pos));
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (player.isShiftKeyDown()) {
			if (this.getBlockEntity(world,pos).removeOneItem(player)) {
				return InteractionResult.SUCCESS;
			}
		} else {
			if (this.getBlockEntity(world,pos).handleItemInteraction(player, hand)) {
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}
