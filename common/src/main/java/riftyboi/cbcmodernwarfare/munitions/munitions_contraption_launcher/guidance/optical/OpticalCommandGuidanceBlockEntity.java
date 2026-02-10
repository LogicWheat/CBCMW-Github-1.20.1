package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.optical;

import com.simibubi.create.content.contraptions.OrientedContraptionEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedAutocannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedBigCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.GuidanceBlockEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.MunitionsLauncherGuidanceBlock;

import javax.annotation.Nullable;

import java.util.Map;

public class OpticalCommandGuidanceBlockEntity extends GuidanceBlockEntity {

	private Integer trackedContraptionId;

	private BlockPos currentPos;

	public OpticalCommandGuidanceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
		this.trackedContraptionId = -1;
	}

	public void setCurrentPos(BlockPos currentPos) {
		this.currentPos = currentPos;
	}

	public boolean hasTrackedContraption() {
		return trackedContraptionId != null;
	}

	public void setTrackedContraption(PitchOrientedContraptionEntity poce) {
		this.trackedContraptionId = poce.getId();
	}
	public void clearTrackedContraption() {
		this.trackedContraptionId = null;
	}



	public void tickFromContraption(Level level, BlockPos pos, BlockState state, PitchOrientedContraptionEntity poce) {
	}

	public void guideEntity(MunitionsPhysicsContraptionEntity contraptionEntity, BlockState state) {
		if (contraptionEntity.level().isClientSide) return; // Skip on client-side
		if (trackedContraptionId == null || trackedContraptionId == -1 || currentPos == null) return;
		if (!(state.getBlock() instanceof MunitionsLauncherGuidanceBlock)) return; // Check for the correct block type

		Entity trackedEntity = contraptionEntity.level().getEntity(trackedContraptionId);
		if (trackedEntity == null) {
			this.clearTrackedContraption();
			return;
		}
		if (trackedEntity instanceof PitchOrientedContraptionEntity trackedContraption) {
			Vec3 spawnPos = trackedContraption.toGlobalVector(Vec3.atCenterOf(currentPos.relative(trackedContraption.getInitialOrientation())), 0);
			Vec3 targetFacingDirection = spawnPos.subtract(trackedContraption.toGlobalVector(Vec3.atCenterOf(BlockPos.ZERO), 0)).normalize();

			Vec3 currentDirection = contraptionEntity.getDeltaMovement().normalize();
			double missileSpeed = contraptionEntity.getDeltaMovement().length();

			// Calculate the turning rate
			double turningRate = calculateTurningSpeed(contraptionEntity.getContraption().getBlocks().size(), missileSpeed, state);

			// Use the turning rate to blend the current direction with the target direction
			Vec3 blendedDirection = currentDirection.lerp(targetFacingDirection, Math.min(1.0, turningRate)).normalize();

			// Set the missile's motion towards the blended direction
			Vec3 newVelocity = blendedDirection.scale(missileSpeed);

			// Update the missile's motion
			contraptionEntity.setContraptionMotion(newVelocity);
		}
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		this.trackedContraptionId = nbt.getInt("TrackedContraptionId");
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		if (trackedContraptionId != null) nbt.putInt("TrackedContraptionId", this.trackedContraptionId);
	}
}
