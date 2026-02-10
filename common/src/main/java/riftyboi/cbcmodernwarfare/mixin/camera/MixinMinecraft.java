package riftyboi.cbcmodernwarfare.mixin.camera;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import riftyboi.cbcmodernwarfare.content.sights.camera_handler.CameraPossessionController;

@Mixin(Minecraft.class)
public class MixinMinecraft {
	@Inject(method = "handleKeybinds", at = @At("HEAD"))
	private void cbcmodernwarfare$handleStart(CallbackInfo ci) {
		CameraPossessionController.onHandleKeybinds((Minecraft) (Object) this, true);
	}

	@Inject(method = "handleKeybinds", at = @At("RETURN"))
	private void cbcmodernwarfare$handleEnd(CallbackInfo ci) {
		CameraPossessionController.onHandleKeybinds((Minecraft) (Object) this, false);
	}
}
