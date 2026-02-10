package riftyboi.cbcmodernwarfare.mixin.camera;

import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import riftyboi.cbcmodernwarfare.content.sights.camera_handler.CameraClientHandler;
import riftyboi.cbcmodernwarfare.content.sights.camera_handler.CameraPossessionController;
import riftyboi.cbcmodernwarfare.multiloader.IndexPlatform;

import java.util.function.Consumer;

/**
 * These mixins aim at implementing the camera chunk storage from CameraController into all the places
 * ClientChunkCache#storage is used
 *
 * Confirmed working with Security Craft
 */
@Mixin(value = ClientChunkCache.class)
public abstract class ClientChunkCacheMixin {
	@Shadow
	volatile ClientChunkCache.Storage storage;
	@Shadow
	@Final
	ClientLevel level;

	private ClientChunkCache.Storage newStorage(int viewDistance) {
		if ((Object) this instanceof ClientChunkCache cache)
			return cache.new Storage(viewDistance);

		return null;
	}

	@Shadow
	private static boolean isValidChunk(LevelChunk chunk, int x, int z) {
		throw new IllegalStateException("Shadowing isValidChunk did not work!");
	}

	/**
	 * Initializes the camera storage
	 */
	@Inject(method = "<init>", at = @At(value = "TAIL"))
	public void cbcmodernwarfare$securitycraft$onInit(ClientLevel level, int viewDistance, CallbackInfo ci) {
		CameraPossessionController.setCameraStorage(newStorage(Math.max(2, viewDistance) + 3));
	}

	/**
	 * Updates the camera storage's view radius by creating a new Storage instance with the same view center and chunks as the
	 * previous one
	 */
	@Inject(method = "updateViewRadius", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientChunkCache$Storage;<init>(Lnet/minecraft/client/multiplayer/ClientChunkCache;I)V"))
	public void cbcmodernwarfare$securitycraft$onUpdateViewRadius(int viewDistance, CallbackInfo ci) {
		ClientChunkCache.Storage oldStorage = CameraPossessionController.getCameraStorage();
		ClientChunkCache.Storage newStorage = newStorage(Math.max(2, viewDistance) + 3);

		newStorage.viewCenterX = oldStorage.viewCenterX;
		newStorage.viewCenterZ = oldStorage.viewCenterZ;

		for (int i = 0; i < oldStorage.chunks.length(); ++i) {
			LevelChunk chunk = oldStorage.chunks.get(i);

			if (chunk != null) {
				ChunkPos pos = chunk.getPos();

				if (newStorage.inRange(pos.x, pos.z))
					newStorage.replace(newStorage.getIndex(pos.x, pos.z), chunk);
			}
		}

		CameraPossessionController.setCameraStorage(newStorage);
	}

	/**
	 * Handles chunks that are dropped in range of the camera storage
	 */
	@Inject(method = "drop", at = @At(value = "HEAD"))
	public void cbcmodernwarfare$securitycraft$onDrop(int x, int z, CallbackInfo ci) {
		ClientChunkCache.Storage cameraStorage = CameraPossessionController.getCameraStorage();

		if (cameraStorage.inRange(x, z)) {
			int i = cameraStorage.getIndex(x, z);
			LevelChunk chunk = cameraStorage.getChunk(i);

			if (chunk != null && chunk.getPos().x == x && chunk.getPos().z == z) {
				IndexPlatform.postChunkEventClient(chunk, false);
				cameraStorage.replace(i, chunk, null);
			}
		}
	}

	/**
	 * Handles chunks that get sent to the client which are in range of the camera storage, i.e. place them into the storage for
	 * them to be acquired afterwards
	 */
	@Inject(method = "replaceWithPacketData", at = @At(value = "HEAD"), cancellable = true)
	private void cbcmodernwarfare$securitycraft$onReplace(int x, int z, FriendlyByteBuf buffer, CompoundTag chunkTag, Consumer<ClientboundLevelChunkPacketData.BlockEntityTagOutput> tagOutputConsumer, CallbackInfoReturnable<LevelChunk> callback) {
		ClientChunkCache.Storage cameraStorage = CameraPossessionController.getCameraStorage();

		if (CameraClientHandler.isPlayerMountedOnCamera() && cameraStorage.inRange(x, z)) {
			int index = cameraStorage.getIndex(x, z);
			LevelChunk chunk = cameraStorage.getChunk(index);
			ChunkPos chunkPos = new ChunkPos(x, z);

			if (!isValidChunk(chunk, x, z)) {
				chunk = new LevelChunk(level, chunkPos);
				chunk.replaceWithPacketData(buffer, chunkTag, tagOutputConsumer);
				cameraStorage.replace(index, chunk);
			}
			else
				chunk.replaceWithPacketData(buffer, chunkTag, tagOutputConsumer);

			level.onChunkLoaded(chunkPos);
			IndexPlatform.postChunkEventClient(chunk, true);
			callback.setReturnValue(chunk);
		}
	}

	/**
	 * If chunks in range of a camera storage need to be acquired, ask the camera storage about these chunks
	 */
	@Inject(method = "getChunk(IILnet/minecraft/world/level/chunk/ChunkStatus;Z)Lnet/minecraft/world/level/chunk/LevelChunk;",
		slice = @Slice(from = @At(value = "RETURN", ordinal = 1)),
		at = @At("RETURN"), cancellable = true)
	private void cbcmodernwarfare$securitycraft$onGetChunk(int x, int z, ChunkStatus requiredStatus, boolean load, CallbackInfoReturnable<LevelChunk> callback) {
		if (CameraClientHandler.isPlayerMountedOnCamera() && CameraPossessionController.getCameraStorage().inRange(x, z)) {
			LevelChunk chunk = CameraPossessionController.getCameraStorage().getChunk(CameraPossessionController.getCameraStorage().getIndex(x, z));

			if (chunk != null && chunk.getPos().x == x && chunk.getPos().z == z)
				callback.setReturnValue(chunk);
		}
	}
}
