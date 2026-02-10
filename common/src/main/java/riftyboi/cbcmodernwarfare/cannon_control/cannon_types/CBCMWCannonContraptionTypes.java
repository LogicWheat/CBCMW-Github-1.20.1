package riftyboi.cbcmodernwarfare.cannon_control.cannon_types;

import net.minecraft.resources.ResourceLocation;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.cannon_control.cannon_types.CBCCannonContraptionTypes;
import rbasamoyai.createbigcannons.cannon_control.cannon_types.CannonContraptionTypeRegistry;
import rbasamoyai.createbigcannons.cannon_control.cannon_types.ICannonContraptionType;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;

import javax.annotation.Nullable;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CBCMWCannonContraptionTypes implements ICannonContraptionType {
	MEDIUMCANNON,
	MOUNTED_LAUNCHER,
	ROTARYCANNON;
	private static final Map<ResourceLocation, CBCMWCannonContraptionTypes> BY_ID =
		Arrays.stream(values()).collect(Collectors.toMap(CBCMWCannonContraptionTypes::getId, Function.identity()));

	private final ResourceLocation id = CBCModernWarfare.resource(this.name().toLowerCase(Locale.ROOT));

	CBCMWCannonContraptionTypes() {
		CannonContraptionTypeRegistry.register(this.id, this);
	}

	@Override public ResourceLocation getId() { return this.id; }

	@Nullable public static CBCMWCannonContraptionTypes byId(ResourceLocation loc) { return BY_ID.get(loc); }

	public static void register() {
	}

}
