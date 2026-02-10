package riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.material.PushReaction;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end.MunitionsLauncherEnd;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.material.MunitionsLauncherMaterial;

public abstract class MunitionsLauncherBaseBlock extends DirectionalBlock implements MunitionsLauncherBlock {

	private final MunitionsLauncherMaterial material;

	protected MunitionsLauncherBaseBlock(Properties properties, MunitionsLauncherMaterial material) {
		super(properties);
		this.material = material;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
		super.createBlockStateDefinition(builder);
	}

	@Override public MunitionsLauncherMaterial getCannonMaterial() { return this.material; }
	@Override public Direction getFacing(BlockState state) { return state.getValue(FACING); }

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
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Player player = context.getPlayer();
		Direction facing = context.getClickedFace();
		boolean flag = player != null && player.isShiftKeyDown();
		BlockState clickedState = context.getLevel().getBlockState(context.getClickedPos().relative(facing.getOpposite()));

		if (clickedState.getBlock() instanceof MunitionsLauncherBaseBlock cblock
			&& cblock.getFacing(clickedState).getAxis() == facing.getAxis()
			&& !flag) {
			return this.defaultBlockState().setValue(FACING, cblock.getFacing(clickedState));
		} else {
			return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection());
		}
	}

	@Override public BlockState rotate(BlockState state, Rotation rotation) { return state.setValue(FACING, rotation.rotate(state.getValue(FACING))); }
	@Override public BlockState mirror(BlockState state, Mirror mirror) { return state.setValue(FACING, mirror.mirror(state.getValue(FACING))); }

}
