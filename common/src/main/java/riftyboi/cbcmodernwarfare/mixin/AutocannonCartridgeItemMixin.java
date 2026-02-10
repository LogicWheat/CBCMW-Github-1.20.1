package riftyboi.cbcmodernwarfare.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.autocannon.AmmoItemMixinInerface;

@Mixin(AutocannonCartridgeItem.class)
public abstract class AutocannonCartridgeItemMixin extends Item implements AmmoItemMixinInerface {




	@Shadow
	public static ItemStack getProjectileStack(ItemStack stack) {
		return null;
	}
	@Shadow
	public static boolean hasProjectile(ItemStack stack) {
		return false;
	}

	public AutocannonCartridgeItemMixin(Properties properties) {
		super(properties);
	}

	@Override public boolean isIncendiary(ItemStack stack) {
		return hasProjectile(stack) && getProjectileStack(stack).getOrCreateTag().getBoolean("Incendiary"); }

	@Override
	public void setIncendiary(ItemStack stack, boolean value) {
		if (!hasProjectile(stack)) return;
		CompoundTag tag = stack.getOrCreateTag().getCompound("Projectile");
		if (!tag.contains("tag", Tag.TAG_COMPOUND)) tag.put("tag", new CompoundTag());
		tag.getCompound("tag").putBoolean("Incendiary", value);
	}

}
