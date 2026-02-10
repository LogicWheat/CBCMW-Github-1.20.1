package riftyboi.cbcmodernwarfare.index;

import com.simibubi.create.content.contraptions.render.ContraptionVisual;

import dev.engine_room.flywheel.api.visualization.VisualizerRegistry;
import dev.engine_room.flywheel.lib.visualization.SimpleEntityVisualizer;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.contraptions.config.MunitionsContraptionEntityProperties;

public class CBCModernWarfareEntityVisualisers {
	private CBCModernWarfareEntityVisualisers() {}

	public static void register() {
		VisualizerRegistry.setVisualizer(
			CBCModernWarfareEntityTypes.MUNITIONS_CONTRAPTION.get(),
			new SimpleEntityVisualizer<MunitionsPhysicsContraptionEntity>(ContraptionVisual::new, entity -> false)
		);
	}
}
