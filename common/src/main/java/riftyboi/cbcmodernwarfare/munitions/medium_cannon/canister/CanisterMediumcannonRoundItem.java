package riftyboi.cbcmodernwarfare.munitions.medium_cannon.canister;

import net.createmod.catnip.lang.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.munitions.fuzes.FuzeItem;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareEntityTypes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareItem;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionPropertiesHandlers;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.FuzedMediumItemMunition;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.AbstractMediumcannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.config.MediumcannonProjectilePropertiesComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CanisterMediumcannonRoundItem extends MediumcannonRoundItem implements FuzedMediumItemMunition {

	public CanisterMediumcannonRoundItem(Properties properties) {
		super(properties);
	}

	@Override
	public AbstractMediumcannonProjectile getMediumcannonProjectile(ItemStack stack, Level level) {
		CanisterMediumcannonProjectile projectile = CBCModernWarfareEntityTypes.CANISTER_ROUND.create(level);
		CompoundTag tag = stack.getOrCreateTag();
		if (tag.contains("Fuze", Tag.TAG_COMPOUND)) {
			projectile.setFuze(ItemStack.of(tag.getCompound("Fuze")));
		}
		return projectile;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		CompoundTag tag = stack.getOrCreateTag();
		ItemStack fuze =
			tag.contains("Fuze", Tag.TAG_COMPOUND) ? ItemStack.of(tag.getCompound("Fuze")) : ItemStack.EMPTY;
		if (!fuze.isEmpty()) {
			Lang.builder("block")
				.translate(CreateBigCannons.MOD_ID + ".shell.tooltip.fuze")
				.add(Component.literal(" "))
				.add(fuze.getDisplayName().copy())
				.addTo(tooltip);
			if (fuze.getItem() instanceof FuzeItem) {
				List<Component> subTooltip = new ArrayList<>();
				fuze.getItem().appendHoverText(fuze, level, subTooltip, flag);
				subTooltip.replaceAll(sibling -> Component.literal("  ").append(sibling).withStyle(ChatFormatting.GRAY));
				tooltip.addAll(subTooltip);
			}
		}
	}


	@Override
	public EntityType<?> getEntityType(ItemStack stack) {
		return CBCModernWarfareEntityTypes.CANISTER_ROUND.get();
	}

	@Nonnull
	@Override
	public MediumcannonProjectilePropertiesComponent getMediumcannonProperties(ItemStack itemStack) {
		return CBCModernWarfareMunitionPropertiesHandlers.CANISTER_MEDIUMCANNON.getPropertiesOf(this.getEntityType(itemStack)).mediumcannonProperties();
	}
	@Override public ItemStack getCartridgeType() { return CBCModernWarfareItem.CANISTER_MEDIUMCANNON_CARTRIDGE.asStack(); }
}
