package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;

public class GuidanceBlockEntity extends SyncedBlockEntity {

	public GuidanceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}

	public void tickFromContraption(Level level, BlockPos pos, BlockState state, PitchOrientedContraptionEntity poce) {
	}

	protected double calculateTurningSpeed(double length, double speed, BlockState state) {
		double turnRate = 0;

		// Get turn rate from the guidance block
		if (state.getBlock() instanceof IGuidanceBlock guidance) {
			turnRate = guidance.turnRate();
		}

		double h = 6;
		double b = 0.05; // Controls the exponential decay rate

		// Calculate the speed factor based on an exponential equation
		double speedFactor = turnRate * Math.exp(-b * Math.abs(speed - h));

		// Length factor: larger length reduces turning ability
		double lengthFactor = length * -0.015;

		// Combine factors
		return speedFactor + lengthFactor;
	}
}
