package riftyboi.cbcmodernwarfare.cannon_control.compact_mount;

import java.util.Random;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountBlockEntity;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;

public class CompactCannonMountBlock extends KineticBlock implements IBE<CompactCannonMountBlockEntity> {
		public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
		public static final BooleanProperty ASSEMBLY_POWERED = BooleanProperty.create("assembly_powered");
		public static final BooleanProperty FIRE_POWERED = BooleanProperty.create("fire_powered");

		public CompactCannonMountBlock(Properties properties) {
			super(properties);
			this.registerDefaultState(this.stateDefinition.any()
				.setValue(HORIZONTAL_FACING, Direction.NORTH)
				.setValue(ASSEMBLY_POWERED, false)
				.setValue(FIRE_POWERED, false));

		}
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HORIZONTAL_FACING, ASSEMBLY_POWERED, FIRE_POWERED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection());
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return state.getValue(HORIZONTAL_FACING).getAxis() == Axis.X ? Axis.Z : Axis.X;
	}

	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face.getAxis() == this.getRotationAxis(state);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(HORIZONTAL_FACING, mirror.mirror(state.getValue(HORIZONTAL_FACING)));
	}

	@Override
	public Class<CompactCannonMountBlockEntity> getBlockEntityClass() {
		return CompactCannonMountBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends CompactCannonMountBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.COMPACT_MOUNT.get();
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
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, level, pos, oldState, isMoving);
		// Adapted from super
		if (level.getBlockEntity(pos) instanceof CannonMountBlockEntity mount) {
			for (KineticBlockEntity kbe : mount.getAllKineticBlockEntities()) {
				kbe.preventSpeedUpdate = 0;
				if (oldState.getBlock() == state.getBlock() && state.hasBlockEntity() == oldState.hasBlockEntity()
					&& this.areStatesKineticallyEquivalent(oldState, state))
					kbe.preventSpeedUpdate = 2;
			}
		}
	}


	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		boolean prevAssemblyPowered = state.getValue(ASSEMBLY_POWERED);
		boolean prevFirePowered = state.getValue(FIRE_POWERED);
		boolean assemblyPowered = this.hasNeighborSignal(level, state, pos, ASSEMBLY_POWERED);
		boolean firePowered = this.hasNeighborSignal(level, state, pos, FIRE_POWERED);
		Direction fireDirection = state.getValue(HORIZONTAL_FACING);
		int firePower = level.getSignal(pos.relative(fireDirection), fireDirection);
		this.withBlockEntityDo(level, pos, cmbe -> cmbe.onRedstoneUpdate(assemblyPowered, prevAssemblyPowered, firePowered, prevFirePowered, firePower));
	}

	private boolean hasNeighborSignal(Level level, BlockState state, BlockPos pos, BooleanProperty property) {
		if (property == FIRE_POWERED) {
			Direction fireDirection = state.getValue(HORIZONTAL_FACING);
			return level.getSignal(pos.relative(fireDirection), fireDirection) > 0;
		}
		if (property == ASSEMBLY_POWERED) {
			Direction assemblyDirection = state.getValue(HORIZONTAL_FACING).getOpposite();
			return level.getSignal(pos.relative(assemblyDirection), assemblyDirection) > 0;
		}
		return false;
	}

	@Override
	public void updateIndirectNeighbourShapes(BlockState stateIn, LevelAccessor level, BlockPos pos, int flags, int count) {
		if (!level.isClientSide() && level.getBlockEntity(pos) instanceof CompactCannonMountBlockEntity mount)
			mount.tryUpdatingSpeed();
	}
		@Override
		public InteractionResult onWrenched(BlockState state, UseOnContext context) {
			if (context.getClickedFace() == state.getValue(HORIZONTAL_FACING).getClockWise()) {
				if (!context.getLevel().isClientSide || state.getValue(ASSEMBLY_POWERED).equals(false))

					return InteractionResult.SUCCESS;
			}
			return InteractionResult.PASS;
		}

	}
