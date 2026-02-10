package riftyboi.cbcmodernwarfare.crafting.munition_assembly;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import rbasamoyai.createbigcannons.index.CBCItems;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareRecipeTypes;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonAmmoItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonRoundItem;

public class MediumTracerApply extends CustomRecipe {

	public MediumTracerApply(ResourceLocation location) { super(location, CraftingBookCategory.MISC); }

	@Override
	public boolean matches(CraftingContainer container, Level level) {
		ItemStack round = ItemStack.EMPTY;
		ItemStack tracer = ItemStack.EMPTY;

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack = container.getItem(i);
			if (stack.isEmpty()) continue;
			if (stack.getItem() instanceof MediumcannonRoundItem) {
				if (!round.isEmpty() || stack.getOrCreateTag().getBoolean("Tracer")) return false;
				round = stack;
			} else if (stack.getItem() instanceof MediumcannonAmmoItem item) {
				if (!round.isEmpty() || item.isTracer(stack)) return false;
				round = stack;
			} else if (CBCItems.TRACER_TIP.isIn(stack)) {
				if (!tracer.isEmpty()) return false;
				tracer = stack;
			} else {
				return false;
			}
		}

		return !round.isEmpty() && !tracer.isEmpty();
	}

	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
		ItemStack round = ItemStack.EMPTY;
		ItemStack tracer = ItemStack.EMPTY;

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack = container.getItem(i);
			if (stack.isEmpty()) continue;
			if (stack.getItem() instanceof MediumcannonRoundItem || stack.getItem() instanceof MediumcannonAmmoItem) {
				if (!round.isEmpty()) return ItemStack.EMPTY;
				round = stack;
			} else if (CBCItems.TRACER_TIP.isIn(stack)) {
				if (!tracer.isEmpty()) return ItemStack.EMPTY;
				tracer = stack;
			} else {
				return ItemStack.EMPTY;
			}
		}

		if (round.isEmpty() || tracer.isEmpty()) return ItemStack.EMPTY;
		ItemStack result = round.copy();
		result.setCount(1);
		if (result.getItem() instanceof MediumcannonRoundItem) {
			result.getOrCreateTag().putBoolean("Tracer", true);
		} else if (result.getItem() instanceof MediumcannonAmmoItem item) {
			item.setTracer(result, true);
		}
		return result;
	}


	@Override public boolean canCraftInDimensions(int width, int height) { return width * height >= 2; }
	@Override public RecipeSerializer<?> getSerializer() { return CBCModernWarfareRecipeTypes.MEDIUM_TRACER_APPLICATION.getSerializer(); }
}
