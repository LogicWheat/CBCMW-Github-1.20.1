package riftyboi.cbcmodernwarfare.index;

import static rbasamoyai.createbigcannons.CreateBigCannons.REGISTRATE;

import java.util.function.Supplier;

import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.tterrag.registrate.util.entry.RegistryEntry;

import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountDisplaySource;
import riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountDisplaySource;

public class CBCModernWarfareDisplaySources {

	public static final RegistryEntry<CompactCannonMountDisplaySource> COMPACT_MOUNT = simple("compact_mount", CompactCannonMountDisplaySource::new);

	public static void register() {}

	private static <T extends DisplaySource> RegistryEntry<T> simple(String name, Supplier<T> sup) {
		return REGISTRATE.displaySource(name, sup).register();
	}

}
