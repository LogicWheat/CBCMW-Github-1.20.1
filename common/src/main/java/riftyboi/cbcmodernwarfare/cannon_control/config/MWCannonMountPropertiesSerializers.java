package riftyboi.cbcmodernwarfare.cannon_control.config;

import rbasamoyai.createbigcannons.cannon_control.config.CannonMountPropertiesHandler;
import rbasamoyai.createbigcannons.cannon_control.config.SimpleBlockMountProperties;
import rbasamoyai.createbigcannons.cannon_control.config.SimpleEntityMountProperties;
import rbasamoyai.createbigcannons.index.CBCBlockEntities;
import rbasamoyai.createbigcannons.index.CBCEntityTypes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;

public class MWCannonMountPropertiesSerializers {
	public static void init() {
		CannonMountPropertiesHandler.registerBlockMountSerializer(CBCModernWarfareBlockEntities.COMPACT_MOUNT.get(), new SimpleBlockMountProperties.Serializer());
	}
}
