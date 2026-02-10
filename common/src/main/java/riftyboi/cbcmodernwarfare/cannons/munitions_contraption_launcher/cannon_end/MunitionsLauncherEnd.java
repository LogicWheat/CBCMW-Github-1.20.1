package riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end;

import net.minecraft.util.StringRepresentable;

public enum MunitionsLauncherEnd implements StringRepresentable {
	CLOSED("closed"),
	OPEN("open"),
	PARTIAL("partial");

	private final String serializedName;

	private MunitionsLauncherEnd(String name) {
		this.serializedName = name;
	}

	@Override public String getSerializedName() { return this.serializedName; }

	public static MunitionsLauncherEnd getOpeningType(float openProgress) {
		return openProgress <= 0 ? CLOSED : openProgress >= 1 ? OPEN : PARTIAL;
	}
}
