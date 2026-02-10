package riftyboi.cbcmodernwarfare.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import rbasamoyai.createbigcannons.multiloader.NetworkPlatform;
import rbasamoyai.createbigcannons.network.ClientboundPreciseRotationSyncPacket;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;

@Mixin(ServerEntity.class)
public class MWServerEntityMixin {

	@Shadow @Final private Entity entity;

	@Inject(method = "sendChanges", at = @At("HEAD"))
	private void cbcmodernwarfare$sendChanges1(CallbackInfo ci) {
		if (this.entity instanceof MunitionsPhysicsContraptionEntity mpce) {
				NetworkPlatform.sendToClientTracking(new ClientboundPreciseRotationSyncPacket(this.entity.getId(), this.entity.getYRot(), this.entity.getXRot()), this.entity);
			this.entity.hasImpulse = false;
		}
	}

}
