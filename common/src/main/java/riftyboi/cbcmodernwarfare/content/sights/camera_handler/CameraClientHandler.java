package riftyboi.cbcmodernwarfare.content.sights.camera_handler;


import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;

public class CameraClientHandler {
	public static boolean isPlayerMountedOnCamera() {
		return Minecraft.getInstance().getCameraEntity() instanceof AbstractSightEntity;
	}

	@Nullable
	public static AbstractSightEntity getPlayerMountedOnCamera() {
		Entity entity = Minecraft.getInstance().getCameraEntity();
		if (entity instanceof AbstractSightEntity cam) {
			return (AbstractSightEntity) entity;
		}
		return null;
	}

	public static boolean isPlayerMountedOnRemote() {
		return Minecraft.getInstance().getCameraEntity() instanceof AbstractSightEntity;
	}

	// Check if the player is "possessing" a specific CameraEntity2
	public static boolean isPossessed(AbstractSightEntity conductor) {
		return getPlayerMountedOnCamera() == conductor;
	}
}
