package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.infrared_homing;

import com.simibubi.create.content.contraptions.OrientedContraptionEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.GuidanceBlockEntity;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Optional;

public class InfraredSeekerGuidanceBlockEntity extends GuidanceBlockEntity {
	private int trackedEntityId;
	private BlockPos currentPos;
	private boolean isLockedOn;
	public InfraredSeekerGuidanceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
		isLockedOn = false;
		trackedEntityId = -1;
	}
	public boolean isLockedOn() {
		return isLockedOn;
	}

	public void setCurrentPos(BlockPos currentPos) {
		this.currentPos = currentPos;
	}

	public void clearTrackedEntityID() {
		this.trackedEntityId = -1;
	}

	public void tickFromContraption(Level level, BlockPos pos, BlockState state, PitchOrientedContraptionEntity poce) {
	}

	public void guideEntity(MunitionsPhysicsContraptionEntity contraptionEntity, BlockState state) {
		if (contraptionEntity.level().isClientSide) return; // Skip on client-side
		if (trackedEntityId == -1) return;
		if (!(state.getBlock() instanceof InfraredSeekerGuidanceBlock guidance)) return;

		// Get the tracked entity (target)
		Entity trackedEntity = contraptionEntity.level().getEntity(trackedEntityId);
		if (trackedEntity == null) {
			this.clearTrackedEntityID();
			return;
		}

		// Get current positions and velocities
		Vec3 missilePos = contraptionEntity.position();
		Vec3 targetPos = trackedEntity.position();
		Vec3 targetVelocity = trackedEntity.getDeltaMovement();
		Vec3 missileVelocity = contraptionEntity.getDeltaMovement();

		// Calculate relative position and velocity
		Vec3 relativePos = targetPos.subtract(missilePos);
		Vec3 relativeVelocity = targetVelocity.subtract(missileVelocity);
		double missileSpeed = missileVelocity.length();

		// Calculate the coefficients for the quadratic equation
		double a = relativeVelocity.lengthSqr() - (missileSpeed * missileSpeed);
		double b = 2 * relativePos.dot(relativeVelocity);
		double c = relativePos.lengthSqr();

		// Solve the quadratic equation for time to intercept
		double discriminant = b * b - 4 * a * c;
		double timeToIntercept;
		if (discriminant > 0 && a != 0) {
			// Two possible solutions; choose the smallest positive time
			double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
			double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
			timeToIntercept = Math.min(t1, t2) > 0 ? Math.min(t1, t2) : Math.max(t1, t2);
		} else {
			// Default to direct path if no valid solution
			double distance = contraptionEntity.distanceTo(trackedEntity);
			timeToIntercept = distance / missileSpeed;
		}

		// Calculate intercept point based on timeToIntercept
		Vec3 interceptPoint = targetPos.add(targetVelocity.scale(timeToIntercept));

		// Calculate direction to intercept point
		Vec3 directionToIntercept = interceptPoint.subtract(missilePos).normalize();
		Vec3 currentDirection = missileVelocity.normalize();

		double cosineAngle = currentDirection.dot(directionToIntercept);

		// If the target is outside the FOV, clear tracking to avoid overshoot
		if (!isTargetWithinFOV(trackedEntity, missilePos, currentDirection, state)) {
			this.clearTrackedEntityID();
			return;
		}

		// Gradual turning: clamp the turn to a maximum angle per tick
		double turningRate = calculateTurningSpeed(contraptionEntity.getContraption().getBlocks().size(), missileSpeed, state);

		// Apply turn clamp with lerp for gradual orientation adjustment
		Vec3 adjustedDirection = currentDirection.lerp(directionToIntercept, turningRate).normalize();

		// Update missile's movement with the adjusted direction and speed
		contraptionEntity.setContraptionMotion(adjustedDirection.scale(missileSpeed));
	}



	public void tickGuidance(Level level, BlockPos localPos, BlockState state, @Nullable BlockEntity blockEntity, OrientedContraptionEntity oce) {
		if (level.isClientSide) return; // Skip on client-side
		if (!(state.getBlock() instanceof InfraredSeekerGuidanceBlock guidance)) return;

		isLockedOn = false;



		Vec3 seekerPos = oce.toGlobalVector(Vec3.atCenterOf(localPos.relative(oce.getInitialOrientation())), 0);;
		Vec3 vec = null;
 		if (oce instanceof PitchOrientedContraptionEntity poce) {
			Vec3 spawnPos = poce.toGlobalVector(Vec3.atCenterOf(currentPos.relative(poce.getInitialOrientation())), 0);
			vec = spawnPos.subtract(poce.toGlobalVector(Vec3.atCenterOf(BlockPos.ZERO), 0)).normalize();
		} else if (oce instanceof MunitionsPhysicsContraptionEntity mpce) {
			vec = mpce.getDeltaMovement().normalize();
		}
		if (seekerPos == null) return;
		AABB detectionArea = new AABB(
			seekerPos.x - guidance.getRange(), seekerPos.y - guidance.getRange(), seekerPos.z - guidance.getRange(),
			seekerPos.x + guidance.getRange(), seekerPos.y + guidance.getRange(), seekerPos.z + guidance.getRange()
		);
		// Find entities within the detection area that are within the FOV and have line of sight
		Vec3 finalVec = vec;
		List<Entity> entitiesInRange = level.getEntities(
			oce, detectionArea,
			entity -> entity instanceof LivingEntity && isTargetWithinFOV(entity, seekerPos, finalVec, state) && hasLineOfSight(level, seekerPos, entity, oce)
		);

		// Select the highest priority target if available
		Optional<Entity> priorityTarget = entitiesInRange.stream().findFirst();
		Entity target = priorityTarget.orElse(null);

		if (target != null) {
			trackedEntityId = target.getId();
			isLockedOn = true;
		}
	}

	private boolean isTargetWithinFOV(Entity target, Vec3 seekerPosition, Vec3 seekerDirection, BlockState state) {
		if (!(state.getBlock() instanceof InfraredSeekerGuidanceBlock seeker)) return false;

		// Get the Field of View (FOV) from the seeker (in degrees)
		double fov = isLockedOn ? seeker.trackFov() : seeker.lockFov();
		// Convert FOV to radians for calculation
		double fovRadians = Math.toRadians(fov / 2);

		// Calculate the vector from the seeker to the target (relative direction)
		Vec3 targetDirection = target.position().subtract(seekerPosition).normalize();

		// Calculate the cosine of the angle between the seeker's direction and the target direction
		double cosineAngle = seekerDirection.dot(targetDirection);

		// Compare cosine of the angle with the cosine of half the FOV angle
		return cosineAngle >= Math.cos(fovRadians);
	}


	private boolean hasLineOfSight(Level level, Vec3 startPos, Entity target, Entity sender) {
		Vec3 endPos = target.position().add(target.getBoundingBox().getCenter());
		return level.clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, sender))
			.getType() == HitResult.Type.MISS;
	}




	// Read and write tracked contraption ID to NBT
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		this.trackedEntityId = nbt.getInt("TrackedEntityId");
		this.isLockedOn = nbt.getBoolean("isLockedOn");
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		nbt.putInt("TrackedEntityId", this.trackedEntityId);
		nbt.putBoolean("isLockedOn", isLockedOn);
	}
}
