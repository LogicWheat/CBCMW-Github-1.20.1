package riftyboi.cbcmodernwarfare.crafting.munition_assembly;

import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import rbasamoyai.createbigcannons.index.CBCRecipeTypes;
import rbasamoyai.createbigcannons.munitions.FuzedItemMunition;
import rbasamoyai.createbigcannons.munitions.fuzes.FuzeItem;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareRecipeTypes;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonRoundItem;

public class MediumFuzingDeployerRecipe implements Recipe<Container> {
	private final ItemStack munition;
	private final ItemStack fuze;

	public MediumFuzingDeployerRecipe() {
		this.munition = ItemStack.EMPTY;
		this.fuze = ItemStack.EMPTY;
	}

	public MediumFuzingDeployerRecipe(ItemStack munition, ItemStack fuze) {
		this.munition = munition.copy();
		this.fuze = fuze.copy();
	}

	public boolean matches(Container container, Level level) {
		if (!(this.fuze.getItem() instanceof FuzeItem)) {
			return false;
		} else if (this.munition.getItem() instanceof FuzedItemMunition) {
			return !this.munition.getOrCreateTag().contains("Fuze", 10);
		} else if (!(this.munition.getItem() instanceof MediumcannonRoundItem)) {
			return false;
		} else {
			ItemStack cartridgeRound = MediumcannonCartridgeItem.getProjectileStack(this.munition);
			return !cartridgeRound.isEmpty() && cartridgeRound.getItem() instanceof FuzedItemMunition && !cartridgeRound.getOrCreateTag().contains("Fuze", 10);
		}
	}

	public ItemStack assemble(Container inv, RegistryAccess access) {
		return this.getResultItem(access);
	}

	public ItemStack getResultItem(RegistryAccess access) {
		ItemStack result = this.munition.copy();
		result.setCount(1);
		ItemStack fuzeCopy = this.fuze.copy();
		fuzeCopy.setCount(1);
		CompoundTag tag = result.getOrCreateTag();
		if (result.getItem() instanceof FuzedItemMunition) {
			tag.put("Fuze", fuzeCopy.save(new CompoundTag()));
		} else if (result.getItem() instanceof MediumcannonRoundItem) {
			CompoundTag projectileTag = tag.getCompound("Projectile").getCompound("tag");
			projectileTag.put("Fuze", fuzeCopy.save(new CompoundTag()));
			tag.getCompound("Projectile").put("tag", projectileTag);
		}

		return result;
	}

	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	public ResourceLocation getId() {
		return CBCModernWarfareRecipeTypes.MEDIUM_DEPLOYER_MUNITION_FUZING.getId();
	}

	public RecipeSerializer<?> getSerializer() {
		return CBCModernWarfareRecipeTypes.MEDIUM_DEPLOYER_MUNITION_FUZING.getSerializer();
	}

	public RecipeType<?> getType() {
		return CBCModernWarfareRecipeTypes.MEDIUM_DEPLOYER_MUNITION_FUZING.getType();
	}
}
