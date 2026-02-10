package riftyboi.cbcmodernwarfare.datagen.recipies;

import com.google.gson.JsonObject;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.base.CBCRegistries;
import rbasamoyai.createbigcannons.crafting.BlockRecipeSerializer;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import rbasamoyai.createbigcannons.datagen.recipes.BlockRecipeProvider;
import rbasamoyai.createbigcannons.datagen.recipes.FinishedBlockRecipe;
import rbasamoyai.createbigcannons.utils.CBCRegistryUtils;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.crafting.casting.CBCModernWarfareCastShapes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlocks;
import rbasamoyai.createbigcannons.index.CBCFluids;
import rbasamoyai.createbigcannons.multiloader.IndexPlatform;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareFluids;

import java.util.Objects;
import java.util.function.Consumer;

public class CBCModernWarfareCastingRecipies extends BlockRecipeProvider {

	CBCModernWarfareCastingRecipies(PackOutput output) {
		this(CreateBigCannons.MOD_ID, output);
	}

	public CBCModernWarfareCastingRecipies(String modid, PackOutput output) {
		super(modid, output);
		this.info = CreateBigCannons.resource("cannon_casting");
	}

	@Override
	protected void registerRecipes(Consumer<FinishedBlockRecipe> cons) {

		builder("unbored_cast_iron_mediumcannon_breech")
			.castingShape(CBCModernWarfareCastShapes.MEDIUMCANNON_BREECH)
			.ingredient(CBCFluids.MOLTEN_CAST_IRON.get())
			.result(CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_BREECH.get())
			.save(cons);

		builder("unbored_cast_iron_mediumcannon_recoil_barrel")
			.castingShape(CBCModernWarfareCastShapes.MEDIUMCANNON_RECOIL_BARREL)
			.ingredient(CBCFluids.MOLTEN_CAST_IRON.get())
			.result(CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_RECOIL_BARREL.get())
			.save(cons);

		builder("unbored_cast_iron_mediumcannon_barrel")
			.castingShape(CBCModernWarfareCastShapes.MEDIUMCANNON_BARREL)
			.ingredient(CBCFluids.MOLTEN_CAST_IRON.get())
			.result(CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_BARREL.get())
			.save(cons);

		builder("unbored_bronze_mediumcannon_breech")
			.castingShape(CBCModernWarfareCastShapes.MEDIUMCANNON_BREECH)
			.ingredient(CBCFluids.MOLTEN_BRONZE.get())
			.result(CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_BREECH.get())
			.save(cons);

		builder("unbored_bronze_mediumcannon_recoil_barrel")
			.castingShape(CBCModernWarfareCastShapes.MEDIUMCANNON_RECOIL_BARREL)
			.ingredient(CBCFluids.MOLTEN_BRONZE.get())
			.result(CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_RECOIL_BARREL.get())
			.save(cons);

		builder("unbored_bronze_mediumcannon_barrel")
			.castingShape(CBCModernWarfareCastShapes.MEDIUMCANNON_BARREL)
			.ingredient(CBCFluids.MOLTEN_BRONZE.get())
			.result(CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_BARREL.get())
			.save(cons);

		builder("unbored_steel_mediumcannon_breech")
			.castingShape(CBCModernWarfareCastShapes.MEDIUMCANNON_BREECH)
			.ingredient(CBCFluids.MOLTEN_STEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_BREECH.get())
			.save(cons);

		builder("unbored_steel_mediumcannon_recoil_barrel")
			.castingShape(CBCModernWarfareCastShapes.MEDIUMCANNON_RECOIL_BARREL)
			.ingredient(CBCFluids.MOLTEN_STEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_RECOIL_BARREL.get())
			.save(cons);

		builder("unbored_steel_mediumcannon_barrel")
			.castingShape(CBCModernWarfareCastShapes.MEDIUMCANNON_BARREL)
			.ingredient(CBCFluids.MOLTEN_STEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_BARREL.get())
			.save(cons);

		builder("unbored_nethersteel_mediumcannon_breech")
			.castingShape(CBCModernWarfareCastShapes.MEDIUMCANNON_BREECH)
			.ingredient(CBCFluids.MOLTEN_NETHERSTEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_BREECH.get())
			.save(cons);

		builder("unbored_nethersteel_mediumcannon_recoil_barrel")
			.castingShape(CBCModernWarfareCastShapes.MEDIUMCANNON_RECOIL_BARREL)
			.ingredient(CBCFluids.MOLTEN_NETHERSTEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL.get())
			.save(cons);

		builder("unbored_nethersteel_mediumcannon_barrel")
			.castingShape(CBCModernWarfareCastShapes.MEDIUMCANNON_BARREL)
			.ingredient(CBCFluids.MOLTEN_NETHERSTEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_BARREL.get())
			.save(cons);
		builder("unbored_bronze_rotarycannon_breech")
			.castingShape(CBCModernWarfareCastShapes.ROTARYCANNON_BREECH)
			.ingredient(CBCFluids.MOLTEN_BRONZE.get())
			.result(CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BREECH.get())
			.save(cons);

		builder("unbored_bronze_rotarycannon_bearing")
			.castingShape(CBCModernWarfareCastShapes.ROTARYCANNON_BEARING)
			.ingredient(CBCFluids.MOLTEN_BRONZE.get())
			.result(CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BEARING.get())
			.save(cons);

		builder("unbored_bronze_rotarycannon_barrel")
			.castingShape(CBCModernWarfareCastShapes.ROTARYCANNON_BARREL)
			.ingredient(CBCFluids.MOLTEN_BRONZE.get())
			.result(CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BARREL.get())
			.save(cons);
		builder("unbored_steel_rotarycannon_breech")
			.castingShape(CBCModernWarfareCastShapes.ROTARYCANNON_BREECH)
			.ingredient(CBCFluids.MOLTEN_STEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_STEEL_ROTARYCANNON_BREECH.get())
			.save(cons);

		builder("unbored_steel_rotarycannon_bearing")
			.castingShape(CBCModernWarfareCastShapes.ROTARYCANNON_BEARING)
			.ingredient(CBCFluids.MOLTEN_STEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_STEEL_ROTARYCANNON_BEARING.get())
			.save(cons);

		builder("unbored_steel_rotarycannon_barrel")
			.castingShape(CBCModernWarfareCastShapes.ROTARYCANNON_BARREL)
			.ingredient(CBCFluids.MOLTEN_STEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_STEEL_ROTARYCANNON_BARREL.get())
			.save(cons);
		builder("unbored_nethersteel_rotarycannon_breech")
			.castingShape(CBCModernWarfareCastShapes.ROTARYCANNON_BREECH)
			.ingredient(CBCFluids.MOLTEN_NETHERSTEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_ROTARYCANNON_BREECH.get())
			.save(cons);

		builder("unbored_nethersteel_rotarycannon_bearing")
			.castingShape(CBCModernWarfareCastShapes.ROTARYCANNON_BEARING)
			.ingredient(CBCFluids.MOLTEN_NETHERSTEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_ROTARYCANNON_BEARING.get())
			.save(cons);

		builder("unbored_nethersteel_rotarycannon_barrel")
			.castingShape(CBCModernWarfareCastShapes.ROTARYCANNON_BARREL)
			.ingredient(CBCFluids.MOLTEN_NETHERSTEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_ROTARYCANNON_BARREL.get())
			.save(cons);
		builder("unbored_gun_launcher_barrel")
			.castingShape(CannonCastShape.VERY_SMALL)
			.ingredient(CBCModernWarfareFluids.MOLTEN_CARBON_STEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_BARREL.get())
			.save(cons);

		builder("unbored_built_up_gun_launcher_barrel")
			.castingShape(CannonCastShape.SMALL)
			.ingredient(CBCModernWarfareFluids.MOLTEN_CARBON_STEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_BUILT_UP_GUN_LAUNCHER_BARREL.get())
			.save(cons);

		builder("unbored_gun_launcher_chamber")
			.castingShape(CannonCastShape.MEDIUM)
			.ingredient(CBCModernWarfareFluids.MOLTEN_CARBON_STEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_CHAMBER.get())
			.save(cons);
		builder("unbored_gun_launcher_sliding_breech")
			.castingShape(CannonCastShape.SLIDING_BREECH)
			.ingredient(CBCModernWarfareFluids.MOLTEN_CARBON_STEEL.get())
			.result(CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_SLIDING_BREECH.get())
			.save(cons);
	}

	protected Builder builder(String name) {
		return new Builder(name);
	}

	private class Builder {
		private final ResourceLocation id;

		private CannonCastShape shape = null;
		private FluidIngredient ingredient = null;
		private Block result = null;

		private Builder(String name) {
			this.id = new ResourceLocation(CBCModernWarfareCastingRecipies.this.modid, name);
		}
		public Builder castingShape(CannonCastShape shape) {
			this.shape = shape;
			return this;
		}

		public Builder ingredient(Fluid ingredient) {
			this.ingredient = IndexPlatform.fluidIngredientFrom(ingredient, 1);
			return this;
		}

		public Builder ingredient(TagKey<Fluid> ingredient) {
			this.ingredient = IndexPlatform.fluidIngredientFrom(ingredient, 1);
			return this;
		}

		public Builder result(Block result) {
			this.result = result;
			return this;
		}

		public void save(Consumer<FinishedBlockRecipe> cons) {
			Objects.requireNonNull(this.shape, "Recipe " + this.id + " has no casting shape specified");
			Objects.requireNonNull(this.ingredient, "Recipe " + this.id + " has no fluid ingredient specified");
			Objects.requireNonNull(this.result, "Recipe " + this.id + " has no result specified");
			cons.accept(new Result(this.shape, this.ingredient, this.result, this.id));
		}
	}

	private static class Result implements FinishedBlockRecipe {
		private final ResourceLocation id;
		private final CannonCastShape shape;
		private final FluidIngredient ingredient;
		private final Block result;

		public Result(CannonCastShape shape, FluidIngredient ingredient, Block result, ResourceLocation id) {
			this.shape = shape;
			this.ingredient = ingredient;
			this.result = result;
			this.id = id;
		}

		@Override
		public void serializeRecipeData(JsonObject obj) {
			obj.addProperty("cast_shape", CBCRegistries.cannonCastShapes().getKey(this.shape).toString());
			obj.add("fluid", this.ingredient.serialize());
			obj.addProperty("result", CBCRegistryUtils.getBlockLocation(this.result).toString());
		}

		@Override public ResourceLocation id() { return this.id; }
		@Override public BlockRecipeSerializer<?> getSerializer() { return BlockRecipeSerializer.CANNON_CASTING; }
	}

}
