package riftyboi.cbcmodernwarfare.munitions.smoke_discharger;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.utils.CBCUtils;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.AbstractMediumcannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.smoke_discharger.SmokeDischargerProjectile;

public class SmokeDischargerProjectileRenderer<T extends SmokeDischargerProjectile> extends EntityRenderer<T> {


	public SmokeDischargerProjectileRenderer(EntityRendererProvider.Context context) { super(context); }

	@Override
	public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int packedLight) {
		ItemStack item = entity.getItem();
		Vec3 vel = entity.getOrientation();
		if (vel.lengthSqr() < 1e-4d)
			vel = new Vec3(0, -1, 0);


		poseStack.pushPose();
		if (vel.horizontalDistanceSqr() > 1e-4d && Math.abs(vel.y) > 1e-2d) {
			Vec3 horizontal = new Vec3(vel.x, 0, vel.z).normalize();
			poseStack.mulPoseMatrix(CBCUtils.mat4x4fFacing(vel.normalize().reverse(), horizontal));
			poseStack.mulPoseMatrix(CBCUtils.mat4x4fFacing(horizontal));
		} else {
			poseStack.mulPoseMatrix(CBCUtils.mat4x4fFacing(vel.normalize()));
		}
		Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.NONE, packedLight, 0, poseStack, buffers, entity.level(),  0);

		poseStack.popPose();

		super.render(entity, entityYaw, partialTicks, poseStack, buffers, packedLight);
	}

	@Override public ResourceLocation getTextureLocation(T projectile) { return null; }

	private static void vertex(VertexConsumer builder, Matrix4f pose, Matrix3f normal, int packedLight, float x, float y, int u, int v) {
		builder.vertex(pose, x, y, 0.0f)
			.color(255, 255, 255, 255)
			.uv((float) u, (float) v)
			.overlayCoords(OverlayTexture.NO_OVERLAY)
			.uv2(packedLight)
			.normal(normal, 0.0f, 1.0f, 0.0f)
			.endVertex();
	}

}
