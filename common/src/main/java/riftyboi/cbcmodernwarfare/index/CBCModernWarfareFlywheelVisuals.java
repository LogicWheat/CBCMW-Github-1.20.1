package riftyboi.cbcmodernwarfare.index;

import dev.engine_room.flywheel.api.visualization.VisualizerRegistry;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;

import riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountVisual;

import riftyboi.cbcmodernwarfare.cannons.medium_cannon.breech.MediumcannonBreechVisual;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.recoil_barrel.MediumcannonRecoilBarrelVisual;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.quickfiring.LauncherQuickFiringBreechVisual;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.sliding_breech.LauncherSlidingBrechVisual;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.bearing.RotaryCannonBearingVisual;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.breech.RotaryCannonBreechVisual;

public class CBCModernWarfareFlywheelVisuals {
	private CBCModernWarfareFlywheelVisuals() {}

	public static void registerVisuals() {
		VisualizerRegistry.setVisualizer(CBCModernWarfareBlockEntities.LAUNCHER_SLIDING_BREECH.get(),
			new SimpleBlockEntityVisualizer<>(LauncherSlidingBrechVisual::new, be -> false));

		VisualizerRegistry.setVisualizer(CBCModernWarfareBlockEntities.LAUNCHER_QUICKFIRING_BREECH.get(),
			new SimpleBlockEntityVisualizer<>(LauncherQuickFiringBreechVisual::new, be -> false));


		VisualizerRegistry.setVisualizer(CBCModernWarfareBlockEntities.COMPACT_MOUNT.get(),
			new SimpleBlockEntityVisualizer<>(CompactCannonMountVisual::new, be -> false));


		VisualizerRegistry.setVisualizer(CBCModernWarfareBlockEntities.MEDIUMCANNON_BREECH.get(),
			new SimpleBlockEntityVisualizer<>(MediumcannonBreechVisual::new, be -> false));

		VisualizerRegistry.setVisualizer(CBCModernWarfareBlockEntities.MEDIUMCANNON_RECOIL_BARREL.get(),
			new SimpleBlockEntityVisualizer<>(MediumcannonRecoilBarrelVisual::new, be -> false));

		VisualizerRegistry.setVisualizer(CBCModernWarfareBlockEntities.ROTARYCANNON_BREECH.get(),
			new SimpleBlockEntityVisualizer<>(RotaryCannonBreechVisual::new, be -> false));

		VisualizerRegistry.setVisualizer(CBCModernWarfareBlockEntities.ROTARYCANNON_BEARING.get(),
			new SimpleBlockEntityVisualizer<>(RotaryCannonBearingVisual::new, be -> false));

	}
}
