package riftyboi.cbcmodernwarfare.munitions.medium_cannon;

import javax.annotation.Nullable;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.config.MediumcannonProjectilePropertiesComponent;

public interface MediumcannonAmmoItem {

	@Nullable AbstractMediumcannonProjectile getMediumcannonProjectile(ItemStack stack, Level level);
	@Nullable EntityType<?> getEntityType(ItemStack stack);

	MediumcannonProjectilePropertiesComponent getMediumcannonProperties(ItemStack itemStack);

	boolean isTracer(ItemStack stack);
	void setTracer(ItemStack stack, boolean value);

	ItemStack getSpentItem(ItemStack stack);

	MediumcannonAmmoType getType();

}
