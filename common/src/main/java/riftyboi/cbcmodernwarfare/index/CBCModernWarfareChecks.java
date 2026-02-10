package riftyboi.cbcmodernwarfare.index;

import com.simibubi.create.AllBlocks;

import com.simibubi.create.api.contraption.BlockMovementChecks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import rbasamoyai.createbigcannons.index.CBCBlocks;
import riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.IMediumcannonBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.IMunitionsLauncherBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end.MunitionsLauncherEnd;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.IRotarycannonBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBlock;


public class CBCModernWarfareChecks {

	private static BlockMovementChecks.CheckResult attachedCheckLauncher(BlockState state, Level level, BlockPos pos, Direction attached) {
		if (!(state.getBlock() instanceof MunitionsLauncherBlock cannonBlock)) return BlockMovementChecks.CheckResult.PASS;
		BlockPos otherPos = pos.relative(attached);
		BlockState attachedState = level.getBlockState(otherPos);
		if (!(attachedState.getBlock() instanceof MunitionsLauncherBlock otherBlock))
			return BlockMovementChecks.CheckResult.PASS;

		if (!(level.getBlockEntity(pos) instanceof IMunitionsLauncherBlockEntity cbe) || !(level.getBlockEntity(otherPos) instanceof IMunitionsLauncherBlockEntity cbe1)) {
			return BlockMovementChecks.CheckResult.PASS;
		}

		boolean result = cbe.cannonBehavior().isConnectedTo(attached) && cbe1.cannonBehavior().isConnectedTo(attached.getOpposite());

		return BlockMovementChecks.CheckResult.of(result);
	}

	private static BlockMovementChecks.CheckResult attachedCheckLauncherLoader(BlockState state, Level level, BlockPos pos, Direction attached) {
		BlockState rootState = level.getBlockState(pos.relative(attached));
		state = IMunitionsLauncherBlockEntity.getInnerCannonBlockState(level, pos, state);
		rootState = IMunitionsLauncherBlockEntity.getInnerCannonBlockState(level, pos.relative(attached), rootState);

		if (CBCBlocks.CANNON_LOADER.has(state)) {
			Direction facing = state.getValue(BlockStateProperties.FACING);
			if (CBCBlocks.RAM_HEAD.has(rootState) || CBCBlocks.WORM_HEAD.has(rootState)) {
				Direction facing1 = rootState.getValue(BlockStateProperties.FACING);
				return BlockMovementChecks.CheckResult.of(facing == facing1 && facing == attached);
			}
			if (AllBlocks.PISTON_EXTENSION_POLE.has(rootState)) {
				Direction facing1 = rootState.getValue(BlockStateProperties.FACING);
				return BlockMovementChecks.CheckResult.of(facing.getAxis() == facing1.getAxis() && facing.getAxis() == attached.getAxis());
			}
		}
		if (AllBlocks.PISTON_EXTENSION_POLE.has(state)) {
			Direction facing = state.getValue(BlockStateProperties.FACING);
			if (CBCBlocks.RAM_HEAD.has(rootState) || CBCBlocks.WORM_HEAD.has(rootState)) {
				Direction facing1 = rootState.getValue(BlockStateProperties.FACING);
				return BlockMovementChecks.CheckResult.of(facing.getAxis() == facing1.getAxis() && facing1 == attached);
			}
			if (CBCBlocks.CANNON_LOADER.has(rootState)) {
				Direction facing1 = rootState.getValue(BlockStateProperties.FACING);
				return BlockMovementChecks.CheckResult.of(facing.getAxis() == facing1.getAxis() && facing.getAxis() == attached.getAxis());
			}
			if (AllBlocks.PISTON_EXTENSION_POLE.has(rootState)) {
				Direction facing1 = rootState.getValue(BlockStateProperties.FACING);
				return BlockMovementChecks.CheckResult.of(facing.getAxis() == facing1.getAxis() && facing.getAxis() == attached.getAxis());
			}
		}
		if (CBCBlocks.WORM_HEAD.has(state) || CBCBlocks.RAM_HEAD.has(state)) {
			Direction facing = state.getValue(BlockStateProperties.FACING);
			if (CBCBlocks.CANNON_LOADER.has(rootState)) {
				Direction facing1 = rootState.getValue(BlockStateProperties.FACING);
				return BlockMovementChecks.CheckResult.of(facing == facing1 && facing == attached.getOpposite());
			}
			if (AllBlocks.PISTON_EXTENSION_POLE.has(rootState)) {
				Direction facing1 = rootState.getValue(BlockStateProperties.FACING);
				return BlockMovementChecks.CheckResult.of(facing.getAxis() == facing1.getAxis() && facing == attached.getOpposite());
			}
		}

		return BlockMovementChecks.CheckResult.PASS;
	}

	private static BlockMovementChecks.CheckResult overridePushReactionCheck(BlockState state, Level level, BlockPos pos) {
		if (state.getBlock() instanceof MediumcannonBlock) return BlockMovementChecks.CheckResult.SUCCESS;
		if (state.getBlock() instanceof RotarycannonBlock) return BlockMovementChecks.CheckResult.SUCCESS;
		if (state.getBlock() instanceof MunitionsLauncherBlock) return BlockMovementChecks.CheckResult.SUCCESS;
		return BlockMovementChecks.CheckResult.PASS;
	}

	private static BlockMovementChecks.CheckResult unmovableCompactMount(BlockState blockState, Level level, BlockPos blockPos, Direction direction) {
		return level.getBlockEntity(blockPos) instanceof CompactCannonMountBlockEntity mount ? mount.isRunning() ? BlockMovementChecks.CheckResult.FAIL : BlockMovementChecks.CheckResult.PASS : BlockMovementChecks.CheckResult.PASS;
	}

	private static BlockMovementChecks.CheckResult attachedCheckMediumcannons(BlockState state, Level level, BlockPos pos, Direction attached) {
		if (!(state.getBlock() instanceof MediumcannonBlock)) return BlockMovementChecks.CheckResult.PASS;
		BlockPos otherPos = pos.relative(attached);
		BlockState attachedState = level.getBlockState(otherPos);
		if (!(attachedState.getBlock() instanceof MediumcannonBlock)) return BlockMovementChecks.CheckResult.PASS;

		if (!(level.getBlockEntity(pos) instanceof IMediumcannonBlockEntity cbe) || !(level.getBlockEntity(otherPos) instanceof IMediumcannonBlockEntity cbe1)) {
			return BlockMovementChecks.CheckResult.PASS;
		}

		boolean result = cbe.cannonBehavior().isConnectedTo(attached) && cbe1.cannonBehavior().isConnectedTo(attached.getOpposite());
		return BlockMovementChecks.CheckResult.of(result);
	}
	private static BlockMovementChecks.CheckResult attachedCheckRotarycannons(BlockState state, Level level, BlockPos pos, Direction attached) {
		if (!(state.getBlock() instanceof RotarycannonBlock)) return BlockMovementChecks.CheckResult.PASS;
		BlockPos otherPos = pos.relative(attached);
		BlockState attachedState = level.getBlockState(otherPos);
		if (!(attachedState.getBlock() instanceof RotarycannonBlock)) return BlockMovementChecks.CheckResult.PASS;

		if (!(level.getBlockEntity(pos) instanceof IRotarycannonBlockEntity cbe) || !(level.getBlockEntity(otherPos) instanceof IRotarycannonBlockEntity cbe1)) {
			return BlockMovementChecks.CheckResult.PASS;
		}

		boolean result = cbe.cannonBehavior().isConnectedTo(attached) && cbe1.cannonBehavior().isConnectedTo(attached.getOpposite());
		return BlockMovementChecks.CheckResult.of(result);
	}
	public static void register() {
		BlockMovementChecks.registerMovementAllowedCheck(CBCModernWarfareChecks::overridePushReactionCheck);
		BlockMovementChecks.registerAttachedCheck(CBCModernWarfareChecks::unmovableCompactMount);
		BlockMovementChecks.registerAttachedCheck(CBCModernWarfareChecks::attachedCheckMediumcannons);
		BlockMovementChecks.registerAttachedCheck(CBCModernWarfareChecks::attachedCheckRotarycannons);
		BlockMovementChecks.registerAttachedCheck(CBCModernWarfareChecks::attachedCheckLauncher);
		BlockMovementChecks.registerAttachedCheck(CBCModernWarfareChecks::attachedCheckLauncherLoader);
	}
}
