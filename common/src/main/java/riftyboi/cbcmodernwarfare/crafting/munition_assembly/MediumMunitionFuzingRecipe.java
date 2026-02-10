package riftyboi.cbcmodernwarfare.crafting.munition_assembly;

import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import rbasamoyai.createbigcannons.munitions.fuzes.FuzeItem;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareRecipeTypes;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.FuzedMediumItemMunition;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonCartridgeItem;

public class MediumMunitionFuzingRecipe extends CustomRecipe  {
	public MediumMunitionFuzingRecipe(ResourceLocation location) { super(location, CraftingBookCategory.MISC); }

	@Override
	public boolean matches(CraftingContainer container, Level level) {
		ItemStack round = ItemStack.EMPTY;
		ItemStack fuze = ItemStack.EMPTY;

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack = container.getItem(i);
			if (stack.isEmpty()) continue;

			if (stack.getItem() instanceof MediumcannonCartridgeItem) {
				if (!round.isEmpty()) return false;
				stack = MediumcannonCartridgeItem.getProjectileStack(stack);
			}
			if (stack.getItem() instanceof FuzedMediumItemMunition) {
				if (!round.isEmpty() || stack.getOrCreateTag().contains("Fuze", Tag.TAG_COMPOUND)) return false;
				round = stack;
			} else if (stack.getItem() instanceof FuzeItem) {
				if (!fuze.isEmpty()) return false;
				fuze = stack;
			} else {
				return false;
			}
		}

		return !round.isEmpty() && !fuze.isEmpty();
	}

	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
		ItemStack round = ItemStack.EMPTY;
		ItemStack fuze = ItemStack.EMPTY;

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack = container.getItem(i);
			if (stack.isEmpty()) continue;
			if (stack.getItem() instanceof FuzedMediumItemMunition || stack.getItem() instanceof MediumcannonCartridgeItem) {
				if (!round.isEmpty()) return ItemStack.EMPTY;
				round = stack;
			} else if (stack.getItem() instanceof FuzeItem) {
				if (!fuze.isEmpty()) return ItemStack.EMPTY;
				fuze = stack;
			} else {
				return ItemStack.EMPTY;
			}
		}

		if (round.isEmpty() || fuze.isEmpty()) return ItemStack.EMPTY;
		ItemStack result = round.copy();
		result.setCount(1);
		ItemStack fuzeCopy = fuze.copy();
		fuzeCopy.setCount(1);
		CompoundTag tag = result.getOrCreateTag();
		if (result.getItem() instanceof FuzedMediumItemMunition) {
			tag.put("Fuze", fuzeCopy.save(new CompoundTag()));
		} else if (result.getItem() instanceof MediumcannonCartridgeItem) {
			CompoundTag projectileTag = tag.getCompound("Projectile").getCompound("tag");
			projectileTag.put("Fuze", fuzeCopy.save(new CompoundTag()));
			tag.getCompound("Projectile").put("tag", projectileTag);
		}
		return result;
	}

	@Override public boolean canCraftInDimensions(int width, int height) { return width * height >= 2; }
	@Override public RecipeSerializer<?> getSerializer() { return CBCModernWarfareRecipeTypes.MEDIUM_MUNITION_FUZING.getSerializer(); }
}

