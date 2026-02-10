package riftyboi.cbcmodernwarfare.munitions.autocannon;

import net.minecraft.world.item.ItemStack;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonAmmoItem;

public interface AmmoItemMixinInerface {
	boolean isIncendiary(ItemStack stack);
	void setIncendiary(ItemStack stack, boolean value);
}
