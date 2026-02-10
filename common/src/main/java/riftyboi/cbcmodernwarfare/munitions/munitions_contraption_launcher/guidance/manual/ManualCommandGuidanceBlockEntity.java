package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.manual;

import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.GuidanceBlockEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.MunitionsLauncherGuidanceBlock;

public class ManualCommandGuidanceBlockEntity extends GuidanceBlockEntity {

	private Integer trackedContraptionId; // Store entity ID for persistence

	public ManualCommandGuidanceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
		this.trackedContraptionId = -1;
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
		if (trackedContraptionId == null || trackedContraptionId == -1) return;
		if (!(state.getBlock() instanceof MunitionsLauncherGuidanceBlock guidance)) return;

		Entity trackedEntity = contraptionEntity.level().getEntity(trackedContraptionId);
		if (trackedEntity == null) {
			this.clearTrackedContraption();
			return;
		}

		if (trackedEntity instanceof PitchOrientedContraptionEntity trackedContraption) {
			// Calculate the target's facing direction
			BlockPos firstPos = null;
			Vec3 targetFacingDirection = null;

			for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> s : trackedContraption.getContraption().getBlocks().entrySet()) {
				firstPos = s.getKey();
			}

			if (firstPos != null) {
				Vec3 spawnPos = trackedContraption.toGlobalVector(
					Vec3.atCenterOf(firstPos.relative(trackedContraption.getInitialOrientation())), 0);
				targetFacingDirection = spawnPos.subtract(
					trackedContraption.toGlobalVector(Vec3.atCenterOf(BlockPos.ZERO), 0)).normalize();
			}

			if (targetFacingDirection == null) return;

			// Get the missile's current direction and speed
			Vec3 currentDirection = contraptionEntity.getDeltaMovement().normalize();
			double missileSpeed = contraptionEntity.getDeltaMovement().length();





			double blendFactor = 0.1; // Adjust this for more/less responsiveness
			Vec3 blendedDirection = currentDirection.scale(1 - blendFactor).add(targetFacingDirection.scale(blendFactor)).normalize();

			// Set the missile's motion towards the blended direction
			Vec3 newVelocity = blendedDirection.scale(missileSpeed);

			// Update the missile's motion
			contraptionEntity.setContraptionMotion(newVelocity);
		}
	}

	// Read and write tracked contraption ID to NBT
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
