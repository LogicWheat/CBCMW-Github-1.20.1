package riftyboi.cbcmodernwarfare.content.sights.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class AbstractSightRenderer extends EntityRenderer<AbstractSightEntity> {

	public AbstractSightRenderer(EntityRendererProvider.Context context) {
		super(context);
		}

	@Override
	public ResourceLocation getTextureLocation(AbstractSightEntity entity) {
		return null;
	}
	public boolean shouldRender(AbstractSightEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
		return false;
	}

	public void render(AbstractSightEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

	}
}
