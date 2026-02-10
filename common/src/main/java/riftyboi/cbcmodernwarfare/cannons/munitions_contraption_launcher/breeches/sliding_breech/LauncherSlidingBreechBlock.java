package riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.sliding_breech;

import javax.annotation.Nullable;

import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedBigCannonContraption;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedMunitionsLauncherContraption;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end.MunitionsLauncherEnd;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.material.MunitionsLauncherMaterial;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlocks;

public class LauncherSlidingBreechBlock extends DirectionalAxisKineticBlock implements IBE<LauncherSlidingBreechBlockEntity>, MunitionsLauncherBlock {

	private final MunitionsLauncherMaterial cannonMaterial;
	private final NonNullSupplier<? extends Block> quickfiringConversion;

	public LauncherSlidingBreechBlock(Properties properties, MunitionsLauncherMaterial cannonMaterial, NonNullSupplier<? extends Block> quickfiringConversion) {
		super(properties);
		this.cannonMaterial = cannonMaterial;
		this.quickfiringConversion = quickfiringConversion;
	}

	@Override
	public MunitionsLauncherMaterial getCannonMaterial() {
		return this.cannonMaterial;
	}

	@Override
	public CannonCastShape getCannonShape() {
		return CannonCastShape.SLIDING_BREECH;
	}

	@Override
	public Direction getFacing(BlockState state) {
		return state.getValue(FACING);
	}

	@Override
	public MunitionsLauncherEnd getOpeningType(@Nullable Level level, BlockState state, BlockPos pos) {
		return level != null && level.getBlockEntity(pos) instanceof LauncherSlidingBreechBlockEntity breech ? breech.getOpeningType() : MunitionsLauncherEnd.OPEN;
	}

	@Override
	public MunitionsLauncherEnd getOpeningType(MountedMunitionsLauncherContraption contraption, BlockState state, BlockPos pos) {
		return contraption.presentBlockEntities.get(pos) instanceof LauncherSlidingBreechBlockEntity breech ? breech.getOpeningType() : MunitionsLauncherEnd.OPEN;
	}

	@Override public MunitionsLauncherEnd getDefaultOpeningType() { return MunitionsLauncherEnd.CLOSED; }

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction facing = context.getNearestLookingDirection().getOpposite();
		Direction horizontal = context.getHorizontalDirection();
		return this.defaultBlockState()
			.setValue(FACING, facing)
			.setValue(AXIS_ALONG_FIRST_COORDINATE, horizontal.getAxis() == Direction.Axis.Z);
	}

	@Override
	public InteractionResult onWrenched(BlockState state, UseOnContext context) {
		return InteractionResult.PASS;
	}

	@Override
	public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
		return InteractionResult.PASS;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!level.isClientSide) this.onRemoveCannon(state, level, pos, newState, isMoving);
		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Override
	public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (!level.isClientSide) this.playerWillDestroyMunitionLauncher(level, pos, state, player);
		super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	public Class<LauncherSlidingBreechBlockEntity> getBlockEntityClass() {
		return LauncherSlidingBreechBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends LauncherSlidingBreechBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.LAUNCHER_SLIDING_BREECH.get();
	}

	@Override
	public boolean isComplete(BlockState state) {
		return true;
	}

	public BlockState getConversion(BlockState old) {
		return this.quickfiringConversion.get().defaultBlockState()
			.setValue(FACING, old.getValue(FACING))
			.setValue(AXIS_ALONG_FIRST_COORDINATE, old.getValue(AXIS_ALONG_FIRST_COORDINATE));
	}

}
