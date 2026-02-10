package riftyboi.cbcmodernwarfare.cannons.medium_cannon.recoil_barrel;

import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.IBE;

import com.tterrag.registrate.util.nullness.NonNullFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBarrelBlock;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBaseBlock;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MovesWithMediumcannonRecoilBarrel;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.material.MediumcannonMaterial;
import riftyboi.cbcmodernwarfare.crafting.casting.CBCModernWarfareCastShapes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareShapes;

public class MediumcannonRecoilBarrelBlock extends MediumcannonBaseBlock implements IBE<MediumcannonRecoilBarrelBlockEntity>, MovesWithMediumcannonRecoilBarrel {

	private final NonNullFunction<Direction, BlockState> movingBlockFunction;

	public static final BooleanProperty ASSEMBLED = BooleanProperty.create("assembled");

	public MediumcannonRecoilBarrelBlock(Properties properties, MediumcannonMaterial material, NonNullFunction<Direction, BlockState> movingBlockFunction) {
		super(properties, material);
		this.movingBlockFunction = movingBlockFunction;
		this.registerDefaultState(this.defaultBlockState().setValue(ASSEMBLED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ASSEMBLED);
	}

	@Override
	public Class<MediumcannonRecoilBarrelBlockEntity> getBlockEntityClass() {
		return MediumcannonRecoilBarrelBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends MediumcannonRecoilBarrelBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.MEDIUMCANNON_RECOIL_BARREL.get();
	}

	@Override
	public CannonCastShape getCannonShape() {
		return CBCModernWarfareCastShapes.MEDIUMCANNON_RECOIL_BARREL;
	}

	@Override
	public boolean isBreechMechanism(BlockState state) {
		return false;
	}

	@Override
	public boolean isComplete(BlockState state) {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return CBCModernWarfareShapes.BARREL.get(this.getFacing(state).getAxis());
	}

	@Override
	public BlockState getMovingState(BlockState original) {
		return this.movingBlockFunction.apply(this.getFacing(original));
	}

	@Override public BlockState getStationaryState(BlockState original) { return original.setValue(ASSEMBLED, false); }

	public BlockState getDisconnectedState(BlockState original) { return original.setValue(ASSEMBLED, true); }


}
