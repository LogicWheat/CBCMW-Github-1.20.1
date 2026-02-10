package riftyboi.cbcmodernwarfare.mixin.camera;


import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;
import riftyboi.cbcmodernwarfare.content.sights.camera_handler.CameraClientHandler;

@Mixin(value = Gui.class,priority = 1100)
public abstract class MixinGui {
	@Shadow
	private int screenWidth;  // formerly f_92977_
	@Shadow
	private int screenHeight;  // formerly f_92978_

//	@Unique
//	private static final ResourceLocation SCOPE_LOCATION = CBCModernWarfare.resource("textures/entity/scope.png");

	@Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
	private void cancelExperienceBar(GuiGraphics graphics, int mouseX, CallbackInfo ci) {
		if (CameraClientHandler.isPlayerMountedOnCamera()) {
			ci.cancel();
		}
	}

	@Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
	private void cancelCrosshairRendering(GuiGraphics graphics, CallbackInfo ci) {
		if (CameraClientHandler.isPlayerMountedOnCamera()) {
			ci.cancel();
		}
	}

	@Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
	private void renderSpyglassOverlay(GuiGraphics graphics, float partialTicks, CallbackInfo ci) {
		if (CameraClientHandler.isPlayerMountedOnCamera()) {
			AbstractSightEntity cameraEntity = CameraClientHandler.getPlayerMountedOnCamera();
//			this.renderOverlay(graphics, SCOPE_LOCATION, partialTicks);
			ci.cancel();
		}
	}

	@Unique
	protected void renderOverlay(GuiGraphics graphics, ResourceLocation texture, float partialTicks) {
		int overlayWidth = Mth.ceil((float) this.screenWidth * partialTicks);
		int overlayHeight = Mth.ceil((float) this.screenHeight * partialTicks);
		int xOffset = (this.screenWidth - overlayWidth) / 2;
		int yOffset = (this.screenHeight - overlayHeight) / 2;
		int xEnd = xOffset + overlayWidth;
		int yEnd = yOffset + overlayHeight;

		graphics.blit(texture, xOffset, yOffset, -90, 0.0F, 0.0F, overlayWidth, overlayHeight, overlayWidth, overlayHeight);
		graphics.fill(RenderType.gui(), 0, yEnd, this.screenWidth, this.screenHeight, -90, -16777216);
		graphics.fill(RenderType.gui(), 0, 0, this.screenWidth, yOffset, -90, -16777216);
		graphics.fill(RenderType.gui(), 0, yOffset, xOffset, yEnd, -90, -16777216);
		graphics.fill(RenderType.gui(), xEnd, yOffset, this.screenWidth, yEnd, -90, -16777216);
	}
}
