package riftyboi.cbcmodernwarfare.munitions.big_cannon.heap_shell;

import com.mojang.math.Axis;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import rbasamoyai.createbigcannons.munitions.big_cannon.shrapnel.ShrapnelBurst;
import rbasamoyai.createbigcannons.munitions.big_cannon.shrapnel.ShrapnelBurstRenderer;
import rbasamoyai.ritchiesprojectilelib.projectile_burst.ProjectileBurst;
import rbasamoyai.ritchiesprojectilelib.projectile_burst.ProjectileBurstRenderer;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;


import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import rbasamoyai.ritchiesprojectilelib.projectile_burst.ProjectileBurst.SubProjectile;

public class HEAPBurstRenderer<T extends HEAPBurst> extends ProjectileBurstRenderer<T> {

	private static final ResourceLocation TEXTURE_LOCATION = CBCModernWarfare.resource("textures/entity/heat_jet.png");
	private static final RenderType RENDER_TYPE;

	public HEAPBurstRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	protected void renderSubProjectile(ProjectileBurst.SubProjectile subProjectile, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int packedLight) {
		poseStack.pushPose();
		poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
		PoseStack.Pose lastPose = poseStack.last();
		Matrix4f pose = lastPose.pose();
		Matrix3f normal = lastPose.normal();
		VertexConsumer builder = buffers.getBuffer(RENDER_TYPE);
		vertex(builder, pose, normal, packedLight, 0.0F, 0, 0, 1);
		vertex(builder, pose, normal, packedLight, 1.0F, 0, 1, 1);
		vertex(builder, pose, normal, packedLight, 1.0F, 1, 1, 0);
		vertex(builder, pose, normal, packedLight, 0.0F, 1, 0, 0);
		poseStack.popPose();
	}

	private static void vertex(VertexConsumer builder, Matrix4f pose, Matrix3f normal, int packedLight, float x, int y, int u, int v) {
		builder.vertex(pose, x - 0.5F, (float)y - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)u, (float)v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
	}

	public ResourceLocation getTextureLocation(HEAPBurst entity) {
		return TEXTURE_LOCATION;
	}

	static {
		RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);
	}
}
