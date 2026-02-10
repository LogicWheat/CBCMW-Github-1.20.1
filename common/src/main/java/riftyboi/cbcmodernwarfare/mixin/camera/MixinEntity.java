package riftyboi.cbcmodernwarfare.mixin.camera;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;
import riftyboi.cbcmodernwarfare.content.sights.camera_handler.CameraPossessionController;

@Mixin(Entity.class)
public abstract class MixinEntity {
	@Shadow
	@Final
	protected SynchedEntityData entityData;

	@Shadow
	public abstract CompoundTag saveWithoutId(CompoundTag compound);

	@SuppressWarnings({"ConstantValue"})
	@Inject(method = "turn", at = @At("HEAD"), cancellable = true)
	private void turnInject(double yaw, double pitch, CallbackInfo ci) {
		AbstractSightEntity sight;
		if ((Object) this instanceof Player this$player &&
			(sight = CameraPossessionController.getPossessingConductor(this$player)) != null) {
			ci.cancel();
			sight.turnView(yaw, pitch);
		}
	}


	@Inject(method = "push(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
	private void cbcmodernwarfare$stopPushing(Entity entity, CallbackInfo ci) {
		if (CameraPossessionController.getPossessingConductor((Entity) (Object) this) == entity)
			ci.cancel();
	}
}
