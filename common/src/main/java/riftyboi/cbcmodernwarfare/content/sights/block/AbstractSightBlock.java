package riftyboi.cbcmodernwarfare.content.sights.block;

import com.simibubi.create.content.equipment.wrench.IWrenchable;

import com.simibubi.create.foundation.block.ProperWaterloggedBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;

public abstract class AbstractSightBlock extends Block implements IWrenchable, ProperWaterloggedBlock, ISightBlock {
	public int sightEntityId;
	public static final Property<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

	public AbstractSightBlock(Properties p) {
		super(p);
		this.registerDefaultState(this.defaultBlockState()
			.setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HORIZONTAL_FACING).add(WATERLOGGED);
	}
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection());
	}

	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (player.isShiftKeyDown())
			return InteractionResult.PASS;
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		if (level.getEntity(sightEntityId) == null) {
			if (level instanceof ServerLevel serverLevel) {
				AbstractSightEntity entity = this.spawn(serverLevel, pos, state.getValue(HORIZONTAL_FACING));
				sightEntityId = entity.getId();
				if (player instanceof ServerPlayer serverPlayer) {
					entity.startViewing(serverPlayer);
				}
				return InteractionResult.CONSUME;
			}
		} else if (player instanceof ServerPlayer serverPlayer && level.getEntity(sightEntityId) != null && level.getEntity(sightEntityId) instanceof AbstractSightEntity sight) {
			sight.startViewing(serverPlayer);
			return InteractionResult.CONSUME;
		}
		return super.use(state, level, pos, player, hand, hit);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(HORIZONTAL_FACING, rot.rotate(state.getValue(HORIZONTAL_FACING)));
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
	}

	@Override
	public BlockState updateShape(BlockState state, Direction face, BlockState otherState, LevelAccessor level, BlockPos pos, BlockPos otherPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(state, face, otherState, level, pos, otherPos);
	}


	@Override
	@SuppressWarnings("deprecation")
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(HORIZONTAL_FACING)));
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		if (level.getEntity(sightEntityId) instanceof AbstractSightEntity sight && level.getEntity(sightEntityId) != null) sight.discard();
		super.onRemove(state, level, pos, newState, movedByPiston);
	}
}
