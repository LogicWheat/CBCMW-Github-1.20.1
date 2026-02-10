package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher;

import net.minecraft.util.StringRepresentable;

public enum FuelType implements StringRepresentable {
	SOLID_FUEL("solid"),
	LIQUID_FUEL("liquid");
	private final String serializedName;
	FuelType(String name) {
		this.serializedName = name;
	}

	@Override public String getSerializedName() { return this.serializedName; }

}

