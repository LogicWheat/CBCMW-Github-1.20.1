package riftyboi.cbcmodernwarfare.mixin;

import com.simibubi.create.foundation.utility.CreateLang;


import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;

import rbasamoyai.createbigcannons.munitions.autocannon.bullet.MachineGunRoundItem;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.munitions.autocannon.AmmoItemMixinInerface;

import javax.annotation.Nullable;

import java.util.List;

@Mixin(MachineGunRoundItem.class)
public abstract class AutocannonMGRoundItemMixin extends Item implements AmmoItemMixinInerface {

	public AutocannonMGRoundItemMixin(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
		super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
		if (stack.getOrCreateTag().getBoolean("Tracer")) {
			CreateLang.builder("tooltip").translate(CBCModernWarfare.MOD_ID + ".incendiary").addTo(tooltipComponents);
		}
	}
	@Override public boolean isIncendiary(ItemStack stack) { return stack.getOrCreateTag().getBoolean("Incendiary"); }

	@Override
	public void setIncendiary(ItemStack stack, boolean value) {
		if (!stack.isEmpty()) stack.getOrCreateTag().putBoolean("Incendiary", value);
	}
}
