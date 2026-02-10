package riftyboi.cbcmodernwarfare.crafting.munition_assembly;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import rbasamoyai.createbigcannons.index.CBCItems;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareRecipeTypes;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonAmmoItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonRoundItem;

public class MediumTracerDeployerRecipe implements Recipe<Container> {
	private final ItemStack munition;
	private final ItemStack fuze;

	public MediumTracerDeployerRecipe() {
		this.munition = ItemStack.EMPTY;
		this.fuze = ItemStack.EMPTY;
	}

	public MediumTracerDeployerRecipe(ItemStack munition, ItemStack fuze) {
		this.munition = munition.copy();
		this.fuze = fuze.copy();
	}

	@Override
	public boolean matches(Container container, Level level) {
		if (!CBCItems.TRACER_TIP.isIn(this.fuze)) return false;
		if (this.munition.getItem() instanceof MediumcannonRoundItem) return !this.munition.getOrCreateTag().getBoolean("Tracer");
		if (this.munition.getItem() instanceof MediumcannonAmmoItem item) return !item.isTracer(this.munition);
		return false;
	}

	@Override public ItemStack assemble(Container inv, RegistryAccess access) { return this.getResultItem(access); }

	@Override
	public ItemStack getResultItem(RegistryAccess access) {
		ItemStack result = this.munition.copy();
		result.setCount(1);
		if (result.getItem() instanceof MediumcannonRoundItem) {
			result.getOrCreateTag().putBoolean("Tracer", true);
		} else if (result.getItem() instanceof MediumcannonAmmoItem item) {
			item.setTracer(result, true);
		}
		return result;
	}

	@Override public boolean canCraftInDimensions(int width, int height) { return true; }

	@Override public ResourceLocation getId() { return CBCModernWarfareRecipeTypes.MEDIUM_TRACER_DEPLOYER_RECIPE.getId(); }
	@Override public RecipeSerializer<?> getSerializer() { return CBCModernWarfareRecipeTypes.MEDIUM_TRACER_DEPLOYER_RECIPE.getSerializer(); }
	@Override public RecipeType<?> getType() { return CBCModernWarfareRecipeTypes.MEDIUM_TRACER_DEPLOYER_RECIPE.getType(); }

}
