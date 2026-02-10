package riftyboi.cbcmodernwarfare.datagen.recipies;


import java.util.function.Consumer;

import com.tterrag.registrate.providers.ProviderType;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareRecipeTypes;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;

public class CBCMWCraftingRecipieProvider {
	public static void register() {
		CBCModernWarfare.REGISTRATE.addDataGenerator(ProviderType.RECIPE, CBCMWCraftingRecipieProvider::buildCraftingRecipes);
	}
	public static void buildCraftingRecipes(Consumer<FinishedRecipe> cons) {
		SpecialRecipeBuilder.special(CBCModernWarfareRecipeTypes.SMOKE_MUNITION_FUZING.getSerializer()).save(cons, "smoke_munition_fuzing");
		SpecialRecipeBuilder.special(CBCModernWarfareRecipeTypes.SMOKE_FUZE_REMOVAL.getSerializer()).save(cons, "smoke_fuze_removal");
		SpecialRecipeBuilder.special(CBCModernWarfareRecipeTypes.MEDIUM_MUNITION_FUZING.getSerializer()).save(cons, "medium_munition_fuzing");
		SpecialRecipeBuilder.special(CBCModernWarfareRecipeTypes.MEDIUM_CARTRIDGE_ASSEMBLY.getSerializer()).save(cons, "medium_cartridge_assembly");
		SpecialRecipeBuilder.special(CBCModernWarfareRecipeTypes.MEDIUM_TRACER_APPLICATION.getSerializer()).save(cons, "medium_tracer_application");
		SpecialRecipeBuilder.special(CBCModernWarfareRecipeTypes.MEDIUM_FUZE_REMOVAL.getSerializer()).save(cons, "medium_fuze_removal");
		SpecialRecipeBuilder.special(CBCModernWarfareRecipeTypes.INCENDIARY_APPLICATION.getSerializer()).save(cons, "incendiary_application");
		SpecialRecipeBuilder.special(CBCModernWarfareRecipeTypes.INCENDIARY_DEPLOYER_APPLICATION.getSerializer()).save(cons, "incendiary_deployer_application");
		SpecialRecipeBuilder.special(CBCModernWarfareRecipeTypes.INCENDIARY_REMOVAL.getSerializer()).save(cons, "incendiary_removal");
	}
}
