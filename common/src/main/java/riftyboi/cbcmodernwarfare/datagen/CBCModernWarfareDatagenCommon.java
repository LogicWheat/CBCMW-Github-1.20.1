package riftyboi.cbcmodernwarfare.datagen;

import rbasamoyai.createbigcannons.datagen.CBCDatagenCommon;
import rbasamoyai.createbigcannons.datagen.CBCDatagenPlatform;

public class CBCModernWarfareDatagenCommon {
	public static final CBCModernWarfareDatagenPlatform PLATFORM = CBCModernWarfareDatagenPlatform.getPlatform(System.getProperty("cbcmodernwarfare.datagen.platform"));
	public static final int FLUID_MULTIPLIER = PLATFORM.fluidMultiplier();

	public static void init() {}

}
