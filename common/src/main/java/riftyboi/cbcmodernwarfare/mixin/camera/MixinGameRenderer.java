package riftyboi.cbcmodernwarfare.mixin.camera;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity.SightType;

import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;

@Mixin({GameRenderer.class})
public abstract class MixinGameRenderer{
	@Shadow @Final
	Minecraft minecraft;

	@Shadow
	void loadEffect(ResourceLocation resourceLocation) {

	}
	@Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
	private void modifyFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> ci) {
		if (camera.getEntity() instanceof AbstractSightEntity sight) {
			ci.setReturnValue((double) sight.getFOV()); // Spyglass FOV effect
		}
	}


	@Inject(method = "checkEntityPostEffect", at = @At("RETURN"))
	private void cbcmodernwarfare$checkEntityPostEffect(Entity entity, CallbackInfo ci) {
		if (entity instanceof AbstractSightEntity sight) {
			if (sight.getSightType() == SightType.ANALOG) loadEffect(new ResourceLocation("shaders/post/scan_pincushion.json"));
		}
	}
}
