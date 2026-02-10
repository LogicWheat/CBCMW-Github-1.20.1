package riftyboi.cbcmodernwarfare.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.world.entity.Entity;
import rbasamoyai.createbigcannons.munitions.AbstractCannonProjectile;
import rbasamoyai.ritchiesprojectilelib.network.ClientboundPreciseMotionSyncPacket;
import rbasamoyai.ritchiesprojectilelib.network.RPLClientHandlers;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;

@Mixin(RPLClientHandlers.class)
public class RPLClientContraptionMixin {

	@Inject(method = "syncPreciseMotion", at = @At("TAIL"), remap = false)
	private static void cbcmodernwarfare$syncPreciseMotion(ClientboundPreciseMotionSyncPacket packet, CallbackInfo ci,
														   @Local Entity entity) {
		if (entity instanceof MunitionsPhysicsContraptionEntity mpce)
			mpce.updateKinematics(packet);
	}

}
