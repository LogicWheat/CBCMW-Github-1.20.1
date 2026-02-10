package riftyboi.cbcmodernwarfare.crafting.munition_assembly;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonAmmoItem;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonRoundItem;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareItem;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareRecipeTypes;
import riftyboi.cbcmodernwarfare.munitions.autocannon.AmmoItemMixinInerface;

public class IncendiaryApplicationDeployerRecipe implements Recipe<Container> {

	private final ItemStack munition;
	private final ItemStack fuze;

	public IncendiaryApplicationDeployerRecipe() {
		this.munition = ItemStack.EMPTY;
		this.fuze = ItemStack.EMPTY;
	}

	public IncendiaryApplicationDeployerRecipe(ItemStack munition, ItemStack fuze) {
		this.munition = munition.copy();
		this.fuze = fuze.copy();
	}

	@Override
	public boolean matches(Container container, Level level) {
		if (!CBCModernWarfareItem.INCENDIARY_TIP.isIn(this.fuze)) return false;
		if (this.munition.getItem() instanceof AutocannonRoundItem)
			return !this.munition.getOrCreateTag().getBoolean("Incendiary");
		if (this.munition.getItem() instanceof AutocannonAmmoItem item && item instanceof AmmoItemMixinInerface mixinItem) return !mixinItem.isIncendiary(this.munition);
		return false;
	}

	@Override
	public ItemStack assemble(Container inv, RegistryAccess access) {
		return this.getResultItem(access);
	}

	@Override
	public ItemStack getResultItem(RegistryAccess access) {
		ItemStack result = this.munition.copy();
		result.setCount(1);
		if (result.getItem() instanceof AutocannonRoundItem) {
			result.getOrCreateTag().putBoolean("Incendiary", true);
		} else if (result.getItem() instanceof AutocannonAmmoItem item && item instanceof AmmoItemMixinInerface mixinItem) {
			mixinItem.setIncendiary(result, true);
		}
		return result;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ResourceLocation getId() {
		return CBCModernWarfareRecipeTypes.INCENDIARY_DEPLOYER_APPLICATION.getId();
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return CBCModernWarfareRecipeTypes.INCENDIARY_DEPLOYER_APPLICATION.getSerializer();
	}

	@Override
	public RecipeType<?> getType() {
		return CBCModernWarfareRecipeTypes.INCENDIARY_DEPLOYER_APPLICATION.getType();
	}
}
