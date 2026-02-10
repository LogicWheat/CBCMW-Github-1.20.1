package riftyboi.cbcmodernwarfare.mixin.camera;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import riftyboi.cbcmodernwarfare.content.sights.camera_handler.CameraPossessionController;

@Mixin({LocalPlayer.class})
public abstract class MixinLocalPlayer {
   @WrapOperation(method = {"sendPosition"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isControlledCamera()Z")})
   private boolean cbcmodernwarfare$isControlledCamera(LocalPlayer instance, Operation<Integer> original) {
      return instance.isLocalPlayer() || CameraPossessionController.isPossessingConductor(instance);
   }

}
