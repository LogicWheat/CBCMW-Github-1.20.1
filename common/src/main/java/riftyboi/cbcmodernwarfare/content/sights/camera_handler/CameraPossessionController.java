package riftyboi.cbcmodernwarfare.content.sights.camera_handler;


import com.simibubi.create.content.redstone.link.controller.LinkedControllerClientHandler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;


import java.util.Arrays;

import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import rbasamoyai.createbigcannons.multiloader.NetworkPlatform;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;
import riftyboi.cbcmodernwarfare.mixin.camera.AccessorKeyMapping;
import riftyboi.cbcmodernwarfare.network.CameraMovePacket;
import riftyboi.cbcmodernwarfare.network.DismountCameraPacket;


public class CameraPossessionController {

	@Environment(EnvType.CLIENT)
	private static ClientChunkCache.Storage cameraStorage;
	private static boolean wasUpPressed;
	private static boolean wasDownPressed;
	private static boolean wasLeftPressed;
	private static boolean wasRightPressed;
	private static boolean wasJumpPressed;
	private static boolean wasSprintPressed;
	private static boolean wasMounted;
	private static final boolean[] wasMouseClicked = new boolean[3];
	private static final boolean[] wasMousePressed = new boolean[3];
	private static boolean wasUsingBefore;
	private static int ticksSincePacket = 0;
	private static int positionReminder = 0;

	@Environment(EnvType.CLIENT)
	public static void onClientTick(Minecraft mc, boolean start) {
		Entity cameraEntity = mc.getCameraEntity();
		if (cameraEntity instanceof AbstractSightEntity) {
			AbstractSightEntity cam = (AbstractSightEntity) cameraEntity;
			wasMounted = true;
			Options options = mc.options;
			if (start) {
				if (wasUpPressed == options.keyUp.isDown()) {
					options.keyUp.setDown(false);
				}

				if (wasDownPressed == options.keyDown.isDown()) {
					options.keyDown.setDown(false);
				}

				if (wasLeftPressed == options.keyLeft.isDown()) {
					options.keyLeft.setDown(false);
				}

				if (wasRightPressed == options.keyRight.isDown()) {
					options.keyRight.setDown(false);
				}

				if (wasJumpPressed == options.keyJump.isDown()) {
					options.keyJump.setDown(false);
				}

				Arrays.fill(wasMouseClicked, false);

				while (options.keyAttack.consumeClick()) {
					wasMouseClicked[0] = true;
				}

				while (options.keyUse.consumeClick()) {
					wasMouseClicked[2] = true;
				}

				wasSprintPressed = options.keySprint.isDown();
				if (options.keyShift.isDown() && LinkedControllerClientHandler.MODE != LinkedControllerClientHandler.Mode.ACTIVE) {
					dismount();
					options.keyShift.setDown(false);
				}
			} else {
				if (wasUpPressed) {
					options.keyUp.setDown(true);
				}

				if (wasDownPressed) {
					options.keyDown.setDown(true);
				}

				if (wasLeftPressed) {
					options.keyLeft.setDown(true);
				}

				if (wasRightPressed) {
					options.keyRight.setDown(true);
				}

				if (wasJumpPressed) {
					options.keyJump.setDown(true);
				}

				if (wasMouseClicked[2]) {
					KeyMapping.click(((AccessorKeyMapping) options.keyUse).getKey());
				}

				LocalPlayer player = mc.player;
				double yRotChange = player.getYRot() - player.yRotO;
				double xRotChange = player.getXRot() - player.xRotO;
				if (yRotChange != 0.0D || xRotChange != 0.0D || ++ticksSincePacket > 10) {
					ticksSincePacket = 0;
					player.connection.send(new ServerboundMovePlayerPacket.PosRot(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot(), player.onGround()));
					player.connection.send(new ServerboundPlayerInputPacket(0.0F, 0.0F, false, false));
				}

				double d = cam.getX() - cam.xOld;
				double e = cam.getY() - cam.yOld;
				double f = cam.getZ() - cam.zOld;
				double g = cam.getYRot() - cam.yRotO;
				double h = cam.getXRot() - cam.xRotO;
				++positionReminder;
				boolean bl3 = Mth.lengthSquared(d, e, f) > Mth.square(2.0E-4D) || positionReminder >= 20;
				boolean bl5 = g != 0.0D || h != 0.0D;
				if (bl3 || bl5) {
					NetworkPlatform.sendToServer(new CameraMovePacket(cam, new ServerboundMovePlayerPacket.PosRot(cam.getX(), cam.getY(), cam.getZ(), cam.getYRot(), cam.getXRot(), cam.onGround())));
				}
			}
		} else if (wasMounted) {
			wasMounted = false;
			dismount();
		}
	}

	@Environment(EnvType.CLIENT)
	public static void onHandleKeybinds(Minecraft mc, boolean start) {
		Entity cameraEntity = mc.getCameraEntity();
		if (cameraEntity instanceof AbstractSightEntity) {
			AbstractSightEntity cam = (AbstractSightEntity) cameraEntity;
			wasMounted = true;
			Options options = mc.options;
			if (start) {
				Arrays.fill(wasMouseClicked, false);
				Arrays.fill(wasMousePressed, false);

				while (options.keyAttack.consumeClick()) {
					wasMouseClicked[0] = true;
				}

				if (wasMousePressed[0] == options.keyAttack.isDown()) {
					options.keyAttack.setDown(false);
				}
			} else {
				if (wasMouseClicked[0]) {
					KeyMapping.click(((AccessorKeyMapping) options.keyAttack).getKey());
				}

				if (wasMousePressed[0]) {
					options.keyAttack.setDown(true);
				}
			}
		}
	}

	@Environment(EnvType.CLIENT)
	private static void dismount() {
		NetworkPlatform.sendToServer(new DismountCameraPacket());
		wasMounted = false;
	}

	@Environment(EnvType.CLIENT)
	@NotNull
	public static ClientChunkCache.Storage getCameraStorage() {
		return cameraStorage;
	}

	@Environment(EnvType.CLIENT)
	public static void setCameraStorage(ClientChunkCache.Storage newStorage) {
		cameraStorage = newStorage;
	}

	@Environment(EnvType.CLIENT)
	public static void setRenderPosition(Entity entity) {
		if (entity instanceof AbstractSightEntity) {
			SectionPos cameraPos = SectionPos.of(entity);
			cameraStorage.viewCenterX = cameraPos.getX();
			cameraStorage.viewCenterZ = cameraPos.getY();
		}
	}

	@Environment(EnvType.CLIENT)
	public static void tryUpdatePossession(AbstractSightEntity cam) {
		if (CameraClientHandler.getPlayerMountedOnCamera() == cam) {
			setRenderPosition(cam);
		}
	}

	public static boolean isPossessingConductor(Entity entity) {
		if (entity instanceof Player) {
			Player player = (Player) entity;
			return player.level.isClientSide ? CameraClientHandler.isPlayerMountedOnCamera() : ((ServerPlayer) player).getCamera() instanceof AbstractSightEntity;
		} else {
			return false;
		}
	}

	@Nullable
	public static AbstractSightEntity getPossessingConductor(Entity entity) {
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (player.level.isClientSide) {
				return CameraClientHandler.getPlayerMountedOnCamera();
			} else {
				Entity camera = ((ServerPlayer) player).getCamera();
				return camera instanceof AbstractSightEntity ? (AbstractSightEntity) camera : null;
			}
		} else {
			return null;
		}
	}

	@Environment(EnvType.CLIENT)
	public static boolean wasUpPressed() {
		return wasUpPressed;
	}

	@Environment(EnvType.CLIENT)
	public static boolean wasDownPressed() {
		return wasDownPressed;
	}

	@Environment(EnvType.CLIENT)
	public static boolean wasLeftPressed() {
		return wasLeftPressed;
	}

	@Environment(EnvType.CLIENT)
	public static boolean wasRightPressed() {
		return wasRightPressed;
	}

	@Environment(EnvType.CLIENT)
	public static boolean wasSprintPressed() {
		return wasSprintPressed;
	}

	@Environment(EnvType.CLIENT)
	public static boolean wasJumpPressed() {
		return wasJumpPressed;
	}
}
