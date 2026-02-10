package riftyboi.cbcmodernwarfare.munitions.contraptions.render;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;

import com.simibubi.create.foundation.collision.Matrix3d;


import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraption;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;

public class MunitionsContraptionRotationState extends AbstractContraptionEntity.ContraptionRotationState {

	private final MunitionsPhysicsContraptionEntity entity;
	private Matrix3d matrix;
	private float yaw;
	private float yawOffset;

	public MunitionsContraptionRotationState(MunitionsPhysicsContraptionEntity entity) {
		float initialYaw = entity.getInitialOrientation().toYRot();
		Vec3 direction = entity.getOrientation().normalize();
		float yaw = (float) Math.toDegrees(Math.atan2(-direction.x, -direction.z)) + initialYaw;
		float pitch = (float) Math.toDegrees(Math.asin(-direction.y));
		this.entity = entity;
		if (pitch != 0 & yaw != 0) {
			this.yawOffset = yaw;
			this.yaw = -initialYaw;
		} else {
			this.yaw = yaw + initialYaw;
		}
	}

	@Override
	public Matrix3d asMatrix() {
		if (this.matrix != null) return this.matrix;
		Vec3 direction = entity.getOrientation().normalize();
		float pitch = (float) Math.toDegrees(Math.asin(-direction.y));
		this.matrix = new Matrix3d().asIdentity();
		boolean flag = this.entity.getInitialOrientation().getAxis() == Direction.Axis.X;
		float yawAdjust = flag && !this.hasVerticalRotation() ? this.yaw + 180 : this.yaw;
		if (this.hasVerticalRotation()) {
			if (flag) {
				this.matrix.multiply(new Matrix3d().asZRotation(AngleHelper.rad(-pitch)));
			} else {
				this.matrix.multiply(new Matrix3d().asXRotation(AngleHelper.rad(-pitch)));
			}
		}/* else {
			yawAdjust += flag ? 180 : 0;
		}*/
		this.matrix.multiply(new Matrix3d().asYRotation(AngleHelper.rad(yawAdjust)));
		return this.matrix;
	}

	@Override
	public boolean hasVerticalRotation() {
		Vec3 direction = entity.getOrientation().normalize();
		float pitch = (float) Math.toDegrees(Math.asin(-direction.y));
		return pitch != 0;
	}

	@Override
	public float getYawOffset() {
		return -this.yawOffset;
	}

}
