package riftyboi.cbcmodernwarfare.mixin.camera;

import net.minecraft.client.renderer.ItemInHandRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import riftyboi.cbcmodernwarfare.content.sights.camera_handler.CameraClientHandler;

@Mixin({ItemInHandRenderer.class})
public class MixinItemInHandRenderer {
   @Inject(
      method = {"renderHandsWithItems"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cbcmodernwarfare$cancelHandRendering(CallbackInfo ci) {
      if (CameraClientHandler.isPlayerMountedOnCamera()) {
         ci.cancel();
      }

   }
}
