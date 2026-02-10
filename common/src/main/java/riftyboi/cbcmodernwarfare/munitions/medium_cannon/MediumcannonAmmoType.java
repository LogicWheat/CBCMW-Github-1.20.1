package riftyboi.cbcmodernwarfare.munitions.medium_cannon;

import net.minecraft.world.item.ItemStack;
import rbasamoyai.createbigcannons.config.CBCConfigs;

public enum MediumcannonAmmoType {
	MEDIUMCANNON {
		@Override public int getCapacity() { return 0; }
	},
	MACHINE_GUN {
		@Override public int getCapacity() { return 0; }
	},
	NONE {
		@Override public int getCapacity() { return 0; }
	};

	MediumcannonAmmoType() {
	}

	public abstract int getCapacity();

	public static MediumcannonAmmoType of(ItemStack stack) {
		return stack.getItem() instanceof MediumcannonAmmoItem item ? item.getType() : NONE;
	}

}
