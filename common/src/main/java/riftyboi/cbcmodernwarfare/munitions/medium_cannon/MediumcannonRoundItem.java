package riftyboi.cbcmodernwarfare.munitions.medium_cannon;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


import net.createmod.catnip.lang.Lang;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import rbasamoyai.createbigcannons.CreateBigCannons;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.config.MediumcannonProjectilePropertiesComponent;

public abstract class MediumcannonRoundItem extends Item {

    protected MediumcannonRoundItem(Properties properties) {
        super(properties);
    }

    public abstract AbstractMediumcannonProjectile getMediumcannonProjectile(ItemStack stack, Level level);
	@Nonnull
	public abstract MediumcannonProjectilePropertiesComponent getMediumcannonProperties(ItemStack itemStack);

	public abstract EntityType<?> getEntityType(ItemStack stack);

	public abstract ItemStack getCartridgeType();

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
		super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
		if (stack.getOrCreateTag().getBoolean("Tracer")) {
			Lang.builder("tooltip").translate(CreateBigCannons.MOD_ID + ".tracer").addTo(tooltipComponents);
		}
	}
}
