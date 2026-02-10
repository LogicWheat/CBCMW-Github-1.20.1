package riftyboi.cbcmodernwarfare.content;

import java.util.List;

import javax.annotation.Nullable;

import com.simibubi.create.content.equipment.goggles.GogglesItem;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.CreateLang;

import net.createmod.catnip.lang.FontHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import rbasamoyai.createbigcannons.CreateBigCannons;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBlock;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.material.MediumcannonMaterial;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.material.MunitionsLauncherMaterialProperties;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBlock;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.material.RotarycannonMaterial;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionsLauncherMaterials;

public class CBCModernWarfareTooltip {
	private static Style primary = FontHelper.Palette.GRAY_AND_WHITE.primary();
	private static Style highlight = FontHelper.Palette.GRAY_AND_WHITE.highlight();

	private static void addHoldShift(boolean desc, List<Component> tooltip) {
		String[] holdDesc = CreateLang.translateDirect("tooltip.holdForDescription", "$").getString().split("\\$");
		if (holdDesc.length < 2) {
			return;
		}
		Component keyShift = CreateLang.translateDirect("tooltip.keyShift");
		MutableComponent tabBuilder = Component.literal("");
		tabBuilder.append(Component.literal(holdDesc[0]).withStyle(ChatFormatting.DARK_GRAY));
		tabBuilder.append(keyShift.plainCopy().withStyle(desc ? ChatFormatting.WHITE : ChatFormatting.GRAY));
		tabBuilder.append(Component.literal(holdDesc[1]).withStyle(ChatFormatting.DARK_GRAY));
		tooltip.add(tabBuilder);
	}
	private static Component getNoGogglesMeter(int outOfFive, boolean invertColor, boolean canBeInvalid) {
		int value = invertColor ? 5 - outOfFive : outOfFive;
		ChatFormatting color = switch (value) {
			case 0, 1 -> ChatFormatting.RED;
			case 2, 3 -> ChatFormatting.GOLD;
			case 4, 5 -> ChatFormatting.YELLOW;
			default -> canBeInvalid ? ChatFormatting.DARK_GRAY : value < 0 ? ChatFormatting.RED : ChatFormatting.YELLOW;
		};
		return Component.literal(" " + TooltipHelper.makeProgressBar(5, outOfFive)).withStyle(color);
	}

	public static FontHelper.Palette getPalette(Level level, ItemStack stack) {
		return FontHelper.Palette.STANDARD_CREATE;
	}
	public static <T extends Block & MunitionsLauncherBlock> void appendLauncherBlockText(ItemStack stack, @Nullable Level level,
																						  List<Component> tooltip, TooltipFlag flag, T block) {
		boolean desc = Screen.hasShiftDown();
		addHoldShift(desc, tooltip);
		if (!desc) {
			return;
		}

		FontHelper.Palette palette = getPalette(level, stack);
		MunitionsLauncherMaterialProperties material = block.getCannonMaterial().properties();
		Minecraft mc = Minecraft.getInstance();
		boolean hasGoggles = GogglesItem.isWearingGoggles(mc.player);
		String rootKey = "block." + CreateBigCannons.MOD_ID + ".cannon.tooltip";
		tooltip.add(Component.literal(I18n.get(rootKey + ".materialProperties")).withStyle(ChatFormatting.GRAY));

		tooltip.add(Component.literal(" " + I18n.get(rootKey + ".strength")).withStyle(ChatFormatting.GRAY));
		float rawStrength = material.maxSafePropellantStress();

		if (hasGoggles) {
			String strength =
				rawStrength > 1000 ? I18n.get(rootKey + ".strength.unlimited") : String.format("%.2f", rawStrength);
			tooltip.addAll(FontHelper.cutStringTextComponent(I18n.get(rootKey + ".strength.goggles", strength),
				palette.primary(), palette.highlight(), 2));
		} else {
			float nethersteelStrength = CBCModernWarfareMunitionsLauncherMaterials.GUN_LAUNCHER.properties().maxSafePropellantStress();
			int strength = Mth.ceil(Math.min(rawStrength / nethersteelStrength * 5, 5));
			tooltip.add(getNoGogglesMeter(strength, false, true));
		}

		double minVelPerBarrel = material.minimumVelocityPerBarrel();
		tooltip.add(Component.literal(" " + I18n.get(rootKey + ".cannonJamming")).withStyle(ChatFormatting.GRAY));
		if (hasGoggles) {
			tooltip.addAll(FontHelper.cutStringTextComponent(I18n.get(rootKey + ".cannonJamming.goggles", String.format("%.2f", minVelPerBarrel * 20)), palette.primary(), palette.highlight(), 2));
		} else {
			tooltip.add(getNoGogglesMeter(minVelPerBarrel < 1d ? 0 : Mth.ceil(minVelPerBarrel * 5d / 6d), false, true));
		}

		tooltip.add(Component.literal(" " + I18n.get(rootKey + ".weightImpact")).withStyle(ChatFormatting.GRAY));
		float weightImpact = material.weight();
		if (hasGoggles) {
			tooltip.addAll(FontHelper.cutStringTextComponent(
				I18n.get(rootKey + ".weightImpact.goggles", String.format("%.2f", weightImpact)), palette.primary(),
				palette.highlight(), 2));
		} else {
			tooltip.add(getNoGogglesMeter(weightImpact < 1d ? 0 : (int) (weightImpact * 0.5f), true, true));
		}

		tooltip.add(Component.literal(" " + I18n.get(rootKey + ".onFailure")).withStyle(ChatFormatting.GRAY));
		String failKey = material.failureMode() == MunitionsLauncherMaterialProperties.FailureMode.RUPTURE ? ".onFailure.rupture" : ".onFailure.fragment";
		tooltip.addAll(FontHelper.cutStringTextComponent(I18n.get(rootKey + failKey), palette.primary(), palette.highlight(), 2));
	}
	public static <T extends Block & MediumcannonBlock> void appendTextMediumcannon(ItemStack stack, @Nullable Level level,
																					List<Component> tooltip, TooltipFlag flag, T block) {
		boolean desc = Screen.hasShiftDown();
		addHoldShift(desc, tooltip);
		if (!desc) {
			return;
		}

		FontHelper.Palette palette = getPalette(level, stack);
		MediumcannonMaterial material = block.getMediumcannonMaterial();
		Minecraft mc = Minecraft.getInstance();
		boolean hasGoggles = GogglesItem.isWearingGoggles(mc.player);
		String rootKey = "block." + CreateBigCannons.MOD_ID + ".autocannon.tooltip";
		tooltip.add(Component.literal(I18n.get(rootKey + ".materialProperties")).withStyle(ChatFormatting.GRAY));

		int maxLength = material.properties().maxBarrelLength();
		tooltip.add(Component.literal(" " + I18n.get(rootKey + ".maxBarrelLength")).withStyle(ChatFormatting.GRAY));
		if (hasGoggles) {
			tooltip.addAll(
				FontHelper.cutStringTextComponent(I18n.get(rootKey + ".maxBarrelLength.goggles", maxLength + 1),
					palette.primary(), palette.highlight(), 2));
		} else {
			tooltip.add(getNoGogglesMeter(maxLength == 0 ? 0 : (maxLength - 1) / 2 + 1, false, true));
		}

		tooltip.add(Component.literal(" " + I18n.get(rootKey + ".weightImpact")).withStyle(ChatFormatting.GRAY));
		float weightImpact = material.properties().weight();
		if (hasGoggles) {
			tooltip.addAll(FontHelper.cutStringTextComponent(
				I18n.get(rootKey + ".weightImpact.goggles", String.format("%.2f", weightImpact)), palette.primary(),
				palette.highlight(), 2));
		} else {
			tooltip.add(getNoGogglesMeter(weightImpact < 1d ? 0 : Mth.ceil(weightImpact), true, true));
		}
	}
	public static <T extends Block & RotarycannonBlock> void appendTextRotarycannon(ItemStack stack, @Nullable Level level,
																					List<Component> tooltip, TooltipFlag flag, T block) {
		boolean desc = Screen.hasShiftDown();
		addHoldShift(desc, tooltip);
		if (!desc) {
			return;
		}

		FontHelper.Palette palette = getPalette(level, stack);
		RotarycannonMaterial material = block.getRotarycannonMaterial();
		Minecraft mc = Minecraft.getInstance();
		boolean hasGoggles = GogglesItem.isWearingGoggles(mc.player);
		String rootKey = "block." + CreateBigCannons.MOD_ID + ".autocannon.tooltip";
		tooltip.add(Component.literal(I18n.get(rootKey + ".materialProperties")).withStyle(ChatFormatting.GRAY));

		int maxLength = material.properties().maxBarrelLength();
		tooltip.add(Component.literal(" " + I18n.get(rootKey + ".maxBarrelLength")).withStyle(ChatFormatting.GRAY));
		if (hasGoggles) {
			tooltip.addAll(
				FontHelper.cutStringTextComponent(I18n.get(rootKey + ".maxBarrelLength.goggles", maxLength + 1),
					palette.primary(), palette.highlight(), 2));
		} else {
			tooltip.add(getNoGogglesMeter(maxLength == 0 ? 0 : (maxLength - 1) / 2 + 1, false, true));
		}

		tooltip.add(Component.literal(" " + I18n.get(rootKey + ".weightImpact")).withStyle(ChatFormatting.GRAY));
		float weightImpact = material.properties().weight();
		if (hasGoggles) {
			tooltip.addAll(FontHelper.cutStringTextComponent(
				I18n.get(rootKey + ".weightImpact.goggles", String.format("%.2f", weightImpact)), palette.primary(),
				palette.highlight(), 2));
		} else {
			tooltip.add(getNoGogglesMeter(weightImpact < 1d ? 0 : Mth.ceil(weightImpact), true, true));
		}
	}
}
