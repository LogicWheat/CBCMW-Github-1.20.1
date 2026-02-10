package riftyboi.cbcmodernwarfare;

import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import rbasamoyai.createbigcannons.mixin.client.CameraAccessor;
import riftyboi.cbcmodernwarfare.content.sights.camera_handler.CameraPossessionController;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;
import riftyboi.cbcmodernwarfare.content.smoke_discharger.SmokeDischargerBoxRenderer;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockPartials;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareEntityVisualisers;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareFlywheelVisuals;
import riftyboi.cbcmodernwarfare.ponder.CBCModernWarfarePonderIndex;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CBCModernWarfareClientCommon {

	public static void onClientSetup() {
		CBCModernWarfareBlockPartials.resolveDeferredModels();
		CBCModernWarfareEntityVisualisers.register();
		CBCModernWarfareFlywheelVisuals.registerVisuals();
	}

	public static void onClientTickStart(Minecraft mc) {
		if (isGameActive()) {
			CameraPossessionController.onClientTick(mc, true);
		}
	}




	public static boolean onScrollMouse(Minecraft mc, double delta) {
		if (mc.player == null || mc.level == null) return false;
		if (mc.cameraEntity instanceof AbstractSightEntity cam && mc.options.keySprint.isDown()) {
			if (delta != 0) {
				cam.changeFov(-delta);
				return true;
			}
		}
		return false;
	}

	public static boolean onCameraSetup(Camera camera, double partialTicks, Supplier<Float> yaw, Supplier<Float> pitch, Supplier<Float> roll,
										Consumer<Float> setYaw, Consumer<Float> setPitch, Consumer<Float> setRoll) {
		Minecraft mc = Minecraft.getInstance();
		LocalPlayer player = mc.player;
		CameraAccessor camAccess = (CameraAccessor) camera;

		if (player != null && camera.getEntity() instanceof AbstractSightEntity sight) {
			if (mc.options.getCameraType() == CameraType.FIRST_PERSON) {
				float yRot = sight.getYRot(); // Adjust based on how your entity class provides this

				// Calculate the offsets
				double offsetX = sight.getHorizontalOffset() * -Math.sin(Math.toRadians(yRot));
				double offsetZ = sight.getHorizontalOffset() * Math.cos(Math.toRadians(yRot));
				double offsetY = -sight.getHeightOffset();
				camAccess.callSetPosition(sight.getEyePosition().add(offsetX,offsetY,offsetZ));
			}
			setYaw.accept(yaw.get());
			setPitch.accept(pitch.get());
			setRoll.accept(0f);
		}
		return false;
	}

	public static void onClientGameTick(Minecraft mc) {
		if (mc.player == null || mc.level == null) return;
		SmokeDischargerBoxRenderer.tick();
	}

	protected static boolean isGameActive() {
		return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
	}

	public static void onClientTickEnd(Minecraft mc) {
		if (isGameActive()) {
			CameraPossessionController.onClientTick(mc, false);
		}
	}
}
