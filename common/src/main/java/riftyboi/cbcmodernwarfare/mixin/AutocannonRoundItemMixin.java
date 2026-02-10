package riftyboi.cbcmodernwarfare.mixin;


import net.createmod.catnip.lang.Lang;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonRoundItem;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.munitions.autocannon.AmmoItemMixinInerface;

import java.util.List;


@Mixin(AutocannonRoundItem.class)
public abstract class AutocannonRoundItemMixin extends Item implements AmmoItemMixinInerface {

	public AutocannonRoundItemMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "appendHoverText", at = @At("HEAD"))
	private void cbcAppendHoverText(ItemStack stack, Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced, CallbackInfo ci) {
		if (stack.getOrCreateTag().getBoolean("Incendiary")) {
			Lang.builder("tooltip").translate(CBCModernWarfare.MOD_ID + ".incendiary").addTo(tooltipComponents);
		}

	}

}
