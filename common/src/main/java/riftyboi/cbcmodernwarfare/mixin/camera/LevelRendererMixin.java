package riftyboi.cbcmodernwarfare.mixin.camera;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import riftyboi.cbcmodernwarfare.content.sights.camera_handler.CameraPossessionController;

/**
 * This mixin fixes camera chunks disappearing when the player entity moves while viewing a camera (e.g. while being in a
 * minecart or falling) - modified by Slimeist to instead change the position to use the conductor position if the player is mounted on a conductor
 *
 * Confirmed to be SC compatible
 */

@Mixin(value = LevelRenderer.class)
public class LevelRendererMixin {
	@WrapOperation(
		method = "setupRender",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getX()D")
	)
	private double injectCameraGetX(LocalPlayer instance, Operation<Double> original) {
		if (CameraPossessionController.isPossessingConductor(instance)) {
			return CameraPossessionController.getPossessingConductor(instance).getX();
		}
		return original.call(instance);
	}
	@WrapOperation(
		method = "setupRender",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getY()D")
	)
	private double injectCameraGetY(LocalPlayer instance, Operation<Double> original) {
		if (CameraPossessionController.isPossessingConductor(instance)) {
			return CameraPossessionController.getPossessingConductor(instance).getY();
		}
		return original.call(instance);
	}
	@WrapOperation(
		method = "setupRender",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getZ()D")
	)
	private double injectCameraGetZ(LocalPlayer instance, Operation<Double> original) {
		if (CameraPossessionController.isPossessingConductor(instance)) {
			return CameraPossessionController.getPossessingConductor(instance).getZ();
		}
		return original.call(instance);
	}
}
