package riftyboi.cbcmodernwarfare.munitions.medium_cannon;

import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import javax.annotation.Nullable;

import riftyboi.cbcmodernwarfare.index.CBCModernWarfareItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.config.MediumcannonProjectilePropertiesComponent;

import java.util.ArrayList;
import java.util.List;

public class MediumcannonCartridgeItem extends Item implements MediumcannonAmmoItem {

    public MediumcannonCartridgeItem(Properties properties) {
        super(properties);
    }

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		ItemStack round = getProjectileStack(stack);
		if (!round.isEmpty()) {
			tooltip.add(Component.translatable("item.minecraft.crossbow.projectile").append(" ").append(round.getDisplayName()));
			if (round.getItem() instanceof MediumcannonRoundItem) {
				List<Component> subTooltip = new ArrayList<>();
				round.getItem().appendHoverText(round, level, subTooltip, flag);
				for (int i = 0; i < subTooltip.size(); ++i) {
					subTooltip.set(i, Component.literal("  ").append(subTooltip.get(i)).withStyle(ChatFormatting.GRAY));
				}
				tooltip.addAll(subTooltip);
			}
		}
	}


	@Override
	public MediumcannonProjectilePropertiesComponent getMediumcannonProperties(ItemStack itemStack) {
		ItemStack projectileStack = getProjectileStack(itemStack);
		return projectileStack.getItem() instanceof MediumcannonRoundItem roundItem ? roundItem.getMediumcannonProperties(itemStack) :
			MediumcannonProjectilePropertiesComponent.DEFAULT;
	}

	@Override public ItemStack getSpentItem(ItemStack stack) { return CBCModernWarfareItem.EMPTY_MEDIUMCANNON_CARTRIDGE.asStack(); }


	@Override public MediumcannonAmmoType getType() { return MediumcannonAmmoType.MEDIUMCANNON; }

	@Override
	@Nullable
    public AbstractMediumcannonProjectile getMediumcannonProjectile(ItemStack stack, Level level) {
        ItemStack projectileStack = getProjectileStack(stack);
        return projectileStack.getItem() instanceof MediumcannonRoundItem projectileItem ? projectileItem.getMediumcannonProjectile(projectileStack, level) : null;
    }

	@Nullable
	@Override
	public EntityType<?> getEntityType(ItemStack stack) {
		ItemStack projectileStack = getProjectileStack(stack);
		return projectileStack.getItem() instanceof MediumcannonRoundItem projectileItem ? projectileItem.getEntityType(projectileStack) : null;
	}

	public static ItemStack getProjectileStack(ItemStack stack) {
        return hasProjectile(stack) ? ItemStack.of(stack.getOrCreateTag().getCompound("Projectile")) : ItemStack.EMPTY;
    }

    public static boolean hasProjectile(ItemStack stack) {
        return stack.getOrCreateTag().contains("Projectile", Tag.TAG_COMPOUND);
    }

    public static void writeProjectile(ItemStack round, ItemStack cartridge) {
        if (round.getItem() instanceof MediumcannonRoundItem) {
            cartridge.getOrCreateTag().put("Projectile", round.save(new CompoundTag()));
        }
    }

	@Override
	public boolean isTracer(ItemStack stack) {
		return hasProjectile(stack) && getProjectileStack(stack).getOrCreateTag().getBoolean("Tracer");
	}

	@Override
	public void setTracer(ItemStack stack, boolean value) {
		if (!hasProjectile(stack)) return;
		CompoundTag tag = stack.getOrCreateTag().getCompound("Projectile");
		if (!tag.contains("tag", Tag.TAG_COMPOUND)) tag.put("tag", new CompoundTag());
		tag.getCompound("tag").putBoolean("Tracer", true);
	}
}
