package riftyboi.cbcmodernwarfare.mixin.camera;

import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;

import javax.annotation.Nullable;

@Mixin(value = PlayerList.class)
public class MixinPlayer {
	@SuppressWarnings("InvalidInjectorMethodSignature")
	@Inject(method = "broadcast", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/server/level/ServerPlayer;getZ()D"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true, require = 0)
	private void brodcast(@Nullable Player except, double x, double y, double z, double radius, ResourceKey<Level> dimension, Packet<?> packet, CallbackInfo callback, int iteration, ServerPlayer serverPlayer) {
		if (serverPlayer.getCamera() instanceof AbstractSightEntity conductor) {
			double dX = x - conductor.getX();
			double dY = y - conductor.getY();
			double dZ = z - conductor.getZ();

			if (dX * dX + dY * dY + dZ * dZ < radius * radius)
				serverPlayer.connection.send(packet);

			callback.cancel();
		}
	}
}
