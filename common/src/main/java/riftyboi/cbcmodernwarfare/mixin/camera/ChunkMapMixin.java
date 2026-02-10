package riftyboi.cbcmodernwarfare.mixin.camera;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;
import riftyboi.cbcmodernwarfare.content.sights.camera_handler.CameraPossessionController;

@Mixin(value = ChunkMap.class)
public abstract class ChunkMapMixin {
	@Shadow
	int viewDistance;

	@Shadow
	protected abstract void updateChunkTracking(ServerPlayer player, ChunkPos chunkPos, MutableObject<ClientboundLevelChunkWithLightPacket> packetCache, boolean wasLoaded, boolean load);


	/**
	 * Fixes block updates not getting sent to chunks loaded by cameras by returning the camera's SectionPos to the distance
	 * checking methods
	 *
	 * @return
	 */
	@ModifyExpressionValue(method = "getPlayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getLastSectionPos()Lnet/minecraft/core/SectionPos;"))
	private SectionPos cbcmodernwarfare$getCameraSectionPos(SectionPos original, @Local ServerPlayer player) {
		// Check if the player is possessing a conductor or is using a Security Camera
		if (CameraPossessionController.isPossessingConductor(player) || player.getCamera().getClass().getName().equals("net.geforcemods.securitycraft.entity.camera.SecurityCamera")) {
			// Set the return value to the camera's section position
			return SectionPos.of(player.getCamera());
		} else {
			// Allow the method to return the original value
			return player.getLastSectionPos();
		}
	}

	/**
	 * Tracks chunks loaded by cameras to send them to the client, and tracks chunks around the player to properly update them
	 * when they stop viewing a camera
	 */
	@Inject(method = "move", at = @At(value = "TAIL"))
	private void cbcmodernwarfare$trackCameraLoadedChunks(ServerPlayer player, CallbackInfo callback) {
		if (player.getCamera() instanceof AbstractSightEntity camera) {
			if (!camera.hasSentChunks()) {
				SectionPos oldPos = camera.oldSectionPos;
				SectionPos pos = SectionPos.of(camera);
				camera.oldSectionPos = pos;

				for (int i = pos.x() - viewDistance - 1; i <= pos.x() + viewDistance + 1; ++i) {
					for (int j = pos.z() - viewDistance - 1; j <= pos.z() + viewDistance + 1; ++j) {
						if (oldPos != null) { // if we are updating from a previous position, only load / unload relevant chunks
							updateChunkTracking(player, new ChunkPos(i, j), new MutableObject<>(),
								ChunkMap.isChunkInRange(i, j, oldPos.x(), oldPos.z(), viewDistance), // was loaded
								ChunkMap.isChunkInRange(i, j, pos.x(), pos.z(), viewDistance)        // is  loaded
							);
						} else if (ChunkMap.isChunkInRange(i, j, pos.x(), pos.z(), viewDistance))
							updateChunkTracking(player, new ChunkPos(i, j), new MutableObject<>(), false, true);
					}
				}

				camera.setHasSentChunks(true);
			}
		} else if (AbstractSightEntity.hasRecentlyDismounted(player)) {
			SectionPos pos = player.getLastSectionPos();

			for (int i = pos.x() - viewDistance; i <= pos.x() + viewDistance; ++i) {
				for (int j = pos.z() - viewDistance; j <= pos.z() + viewDistance; ++j) {
					updateChunkTracking(player, new ChunkPos(i, j), new MutableObject<>(), false, true);
				}
			}
		}
	}
}
