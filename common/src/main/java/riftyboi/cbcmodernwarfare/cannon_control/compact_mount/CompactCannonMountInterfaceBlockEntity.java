package riftyboi.cbcmodernwarfare.cannon_control.compact_mount;


import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.transmission.sequencer.SequencerInstructions;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public abstract class CompactCannonMountInterfaceBlockEntity extends KineticBlockEntity {

	protected final CompactCannonMountBlockEntity parent;
	private double sequencedAngleLimit;

	public CompactCannonMountInterfaceBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state, CompactCannonMountBlockEntity parent) {
		super(typeIn, pos, state);
		this.parent = parent;
		this.setLazyTickRate(3);
		this.sequencedAngleLimit = -1;
	}

	@Override
	public float calculateStressApplied() {
		return this.parent.calculateCannonStressApplied();
	}

	@Override
	public void onSpeedChanged(float previousSpeed) {
		super.onSpeedChanged(previousSpeed);
		this.sequencedAngleLimit = -1;

		if (this.sequenceContext != null && this.sequenceContext.instruction() == SequencerInstructions.TURN_ANGLE) {
			this.sequencedAngleLimit = this.sequenceContext.getEffectiveValue(getTheoreticalSpeed()) * 0.125f;
		}
	}

	public void tryUpdateSpeed() {
		if (this.preventSpeedUpdate > 0)
			return;
		this.warnOfMovement();
		this.clearKineticInformation();
		this.updateSpeed = true;
	}

	public double getSequencedAngleLimit() { return this.sequencedAngleLimit; }
	public void setSequencedAngleLimit(double value) { this.sequencedAngleLimit = value; }

	@Override public void sendData() { this.parent.sendData(); }
	@Override public void setChanged() { this.parent.setChanged(); }

	public static class PitchInterface extends CompactCannonMountInterfaceBlockEntity {
		public PitchInterface(BlockEntityType<?> typeIn, BlockPos pos, BlockState state, CompactCannonMountBlockEntity parent) {
			super(typeIn, pos, state, parent);
		}

		@Override
		public List<BlockPos> addPropagationLocations(IRotate block, BlockState state, List<BlockPos> neighbours) {
			Direction.Axis axis = block.getRotationAxis(state);
			BlockPos pos1 = BlockPos.ZERO.relative(Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE));
			return List.of(this.worldPosition.offset(pos1), this.worldPosition.subtract(pos1));
		}
	}


}
