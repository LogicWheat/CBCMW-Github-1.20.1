package riftyboi.cbcmodernwarfare.index;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.createmod.catnip.lang.Lang;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.MediumFuzeRemoval;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.MediumFuzingDeployerRecipe;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.MediumTracerDeployerRecipe;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.SmokeDischargerFuzeRemoval;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.SmokeDischargerFuzing;
import riftyboi.cbcmodernwarfare.multiloader.IndexPlatform;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.CartridgeAssemblyRecipe;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.MediumMunitionFuzingRecipe;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.MediumTracerApply;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.IncendiaryApplicationRecipie;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.IncendiaryApplicationDeployerRecipe;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.IncendiaryRemovalRecipie;


public enum CBCModernWarfareRecipeTypes implements IRecipeTypeInfo {

	SMOKE_MUNITION_FUZING(noSerializer(SmokeDischargerFuzing::new)),
	SMOKE_FUZE_REMOVAL(noSerializer(SmokeDischargerFuzeRemoval::new)),
	MEDIUM_MUNITION_FUZING(noSerializer(MediumMunitionFuzingRecipe::new)),
	MEDIUM_FUZE_REMOVAL(noSerializer(MediumFuzeRemoval::new)),
	MEDIUM_CARTRIDGE_ASSEMBLY(noSerializer(CartridgeAssemblyRecipe::new)),
	MEDIUM_TRACER_APPLICATION(noSerializer(MediumTracerApply::new)),
	INCENDIARY_APPLICATION(noSerializer(IncendiaryApplicationRecipie::new)),
	MEDIUM_TRACER_DEPLOYER_RECIPE(noSerializer(r -> new MediumTracerDeployerRecipe())),
	INCENDIARY_DEPLOYER_APPLICATION(noSerializer(r -> new IncendiaryApplicationDeployerRecipe())),
	MEDIUM_DEPLOYER_MUNITION_FUZING(noSerializer(r -> new MediumFuzingDeployerRecipe())),
	INCENDIARY_REMOVAL(noSerializer(IncendiaryRemovalRecipie::new));


	private final ResourceLocation id;
	private final Supplier<RecipeSerializer<?>> serializerObject;
	@Nullable
	private final RecipeType<?> typeObject;
	private final NonNullSupplier<RecipeType<?>> type;

	CBCModernWarfareRecipeTypes(NonNullSupplier<RecipeSerializer<?>> serializerSupplier, NonNullSupplier<RecipeType<?>> typeSupplier, boolean registerType) {
		String name = Lang.asId(name());
		id = CBCModernWarfare.resource(name);
		serializerObject = IndexPlatform.registerRecipeSerializer(this.id, serializerSupplier);
		if (registerType) {
			typeObject = typeSupplier.get();
			IndexPlatform.registerRecipeType(this.id, typeSupplier);
			type = typeSupplier;
		} else {
			typeObject = null;
			type = typeSupplier;
		}
	}

	CBCModernWarfareRecipeTypes(NonNullSupplier<RecipeSerializer<?>> serializerSupplier) {
		String name = Lang.asId(name());
		id = CBCModernWarfare.resource(name);
		serializerObject = IndexPlatform.registerRecipeSerializer(this.id, serializerSupplier);
		typeObject = simpleType(id);
		type = () -> typeObject;
		IndexPlatform.registerRecipeType(this.id, this.type);
	}

	CBCModernWarfareRecipeTypes(ProcessingRecipeBuilder.ProcessingRecipeFactory<?> processingFactory) {
		this(() -> new ProcessingRecipeSerializer<>(processingFactory));
	}

	public static <T extends Recipe<?>> RecipeType<T> simpleType(ResourceLocation id) {
		String stringId = id.toString();
		return new RecipeType<>() {
			@Override
			public String toString() {
				return stringId;
			}
		};
	}

	@Override public ResourceLocation getId() { return this.id; }

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RecipeSerializer<?>> T getSerializer() { return (T) this.serializerObject.get(); }

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RecipeType<?>> T getType() { return (T) this.type.get(); }

	public static void register() {
	}

	private static <T extends Recipe<?>> NonNullSupplier<RecipeSerializer<?>> noSerializer(Function<ResourceLocation, T> prov) {
		return () -> new SimpleRecipeSerializer<>(prov);
	}

	private static class SimpleRecipeSerializer<T extends Recipe<?>> implements RecipeSerializer<T> {
		private final Function<ResourceLocation, T> constructor;

		public SimpleRecipeSerializer(Function<ResourceLocation, T> constructor) {
			this.constructor = constructor;
		}

		@Override
		public T fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
			return this.constructor.apply(recipeId);
		}

		@Override
		public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			return this.constructor.apply(recipeId);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, T recipe) {
		}
	}

}
