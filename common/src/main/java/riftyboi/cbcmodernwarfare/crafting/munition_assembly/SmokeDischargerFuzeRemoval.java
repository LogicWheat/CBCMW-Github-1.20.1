package riftyboi.cbcmodernwarfare.crafting.munition_assembly;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareRecipeTypes;
import riftyboi.cbcmodernwarfare.munitions.smoke_discharger.SmokeDischargerRoundItem;

public class SmokeDischargerFuzeRemoval extends CustomRecipe {


	public SmokeDischargerFuzeRemoval(ResourceLocation location) { super(location, CraftingBookCategory.MISC); }

	@Override
	public boolean matches(CraftingContainer container, Level level) {
		ItemStack target = ItemStack.EMPTY;

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack = container.getItem(i);
			if (stack.isEmpty()) continue;
			if (!target.isEmpty()) return false;
			if (stack.getItem() instanceof SmokeDischargerRoundItem) {
				if (!stack.getOrCreateTag().contains("Fuze", Tag.TAG_COMPOUND)) return false;
				target = stack;
			} else {
				return false;
			}
		}
		return !target.isEmpty();
	}

	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
		ItemStack target = ItemStack.EMPTY;

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack = container.getItem(i);
			if (stack.isEmpty()) continue;
			if (!target.isEmpty()) return ItemStack.EMPTY;

			if (stack.getItem() instanceof SmokeDischargerRoundItem) {
				if (!stack.getOrCreateTag().contains("Fuze", Tag.TAG_COMPOUND)) return ItemStack.EMPTY;
				target = stack;
			} else {
				return ItemStack.EMPTY;
			}
		}
		return ItemStack.of(target.getOrCreateTag().getCompound("Fuze"));
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
		NonNullList<ItemStack> result = super.getRemainingItems(container);
		int sz = container.getContainerSize();

		for (int i = 0; i < sz; ++i) {
			ItemStack stack = container.getItem(i);
			if (stack.getItem() instanceof SmokeDischargerRoundItem) {
				if (stack.getOrCreateTag().contains("Fuze", Tag.TAG_COMPOUND)) {
					ItemStack copy = stack.copy();
					copy.getOrCreateTag().remove("Fuze");
					copy.setCount(1);
					result.set(i, copy);
				}
				break;
			}
		}
		return result;
	}

	@Override public boolean canCraftInDimensions(int width, int height) { return width * height >= 1; }
	@Override public RecipeSerializer<?> getSerializer() { return CBCModernWarfareRecipeTypes.SMOKE_FUZE_REMOVAL.getSerializer(); }

}
