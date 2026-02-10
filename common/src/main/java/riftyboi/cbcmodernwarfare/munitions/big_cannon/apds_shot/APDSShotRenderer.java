package riftyboi.cbcmodernwarfare.munitions.big_cannon.apds_shot;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.utils.CBCUtils;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockPartials;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlocks;

public class APDSShotRenderer extends EntityRenderer<APDSShotProjectile> {

	public APDSShotRenderer(EntityRendererProvider.Context context) { super(context); }

	@Override
	public void render(APDSShotProjectile entity, float entityYaw, float partialTicks, PoseStack poseStack,
					   MultiBufferSource buffers, int packedLight) {
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

		CachedBuffers.partial(CBCModernWarfareBlockPartials.APDS_SHOT_FLYING, CBCModernWarfareBlocks.APDS_SHOT.getDefaultState().setValue(BlockStateProperties.FACING, Direction.NORTH))
			.light(packedLight)
			.renderInto(poseStack, buffers.getBuffer(RenderType.cutout()));

		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, buffers, packedLight);
	}

	@Override public ResourceLocation getTextureLocation(APDSShotProjectile entity) { return null; }
}
