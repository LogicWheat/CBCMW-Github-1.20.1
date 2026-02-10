package riftyboi.cbcmodernwarfare.index;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.contraptions.Contraption;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.simibubi.create.AllContraptionTypes.BY_LEGACY_NAME;

import com.simibubi.create.api.contraption.ContraptionType;

import net.minecraft.resources.ResourceLocation;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedMediumcannonContraption;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedMunitionsLauncherContraption;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedRotarycannonContraption;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraption;

public class CBCModernWarfareContraptionTypes {
	public static final Holder.Reference<ContraptionType>
		MOUNTED_MEDIUMCANNON = register("mounted_mediumcannon", MountedMediumcannonContraption::new),
		MOUNTED_ROTARYCANNON = register("mounted_rotarycannon", MountedRotarycannonContraption::new),
		MOUNTED_LAUNCHER = register("mounted_launcher", MountedMunitionsLauncherContraption::new),
		MUNITIONS_CONTRAPTION = register("munitions_contraption", MunitionsPhysicsContraption::new);
	private static Holder.Reference<ContraptionType> register(String name, Supplier<? extends Contraption> factory) {
		ContraptionType type = new ContraptionType(factory);
		BY_LEGACY_NAME.put(name, type);

		return Registry.registerForHolder(CreateBuiltInRegistries.CONTRAPTION_TYPE, CBCModernWarfare.resource(name), type);
	}

	public static void init() {
	}

}
