package riftyboi.cbcmodernwarfare.datagen.recipies;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.crafting.BlockRecipeIngredient;
import rbasamoyai.createbigcannons.crafting.BlockRecipeSerializer;
import rbasamoyai.createbigcannons.datagen.recipes.BlockRecipeProvider;
import rbasamoyai.createbigcannons.datagen.recipes.FinishedBlockRecipe;
import rbasamoyai.createbigcannons.utils.CBCRegistryUtils;
import rbasamoyai.createbigcannons.utils.CBCUtils;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlocks;

import java.util.function.Consumer;

public class CBCModernWarfareDrillRecipies extends BlockRecipeProvider {

	CBCModernWarfareDrillRecipies(PackOutput output) {
		this(CreateBigCannons.MOD_ID, output);
	}

	public CBCModernWarfareDrillRecipies(String modid, PackOutput output) {
		super(modid, output);
	}

	@Override
	protected void registerRecipes(Consumer<FinishedBlockRecipe> cons) {
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_BARREL.get(), CBCModernWarfareBlocks.GUN_LAUNCHER_CHAMBER.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_BUILT_UP_GUN_LAUNCHER_BARREL.get(), CBCModernWarfareBlocks.BUILT_UP_GUN_LAUNCHER_BARREL.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_CHAMBER.get(), CBCModernWarfareBlocks.GUN_LAUNCHER_CHAMBER.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_SLIDING_BREECH.get(), CBCModernWarfareBlocks.INCOMPLETE_GUN_LAUNCHER_SLIDING_BREECH.get()));

		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BARREL.get(), CBCModernWarfareBlocks.BRONZE_ROTARYCANNON_BARREL.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BEARING.get(), CBCModernWarfareBlocks.INCOMPLETE_BRONZE_ROTARYCANNON_BEARING.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BREECH.get(), CBCModernWarfareBlocks.INCOMPLETE_BRONZE_ROTARYCANNON_BEARING.get()));

		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_STEEL_ROTARYCANNON_BARREL.get(), CBCModernWarfareBlocks.STEEL_ROTARYCANNON_BARREL.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_STEEL_ROTARYCANNON_BEARING.get(), CBCModernWarfareBlocks.INCOMPLETE_STEEL_ROTARYCANNON_BEARING.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_STEEL_ROTARYCANNON_BREECH.get(), CBCModernWarfareBlocks.INCOMPLETE_STEEL_ROTARYCANNON_BEARING.get()));

		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_ROTARYCANNON_BARREL.get(), CBCModernWarfareBlocks.NETHERSTEEL_ROTARYCANNON_BARREL.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_ROTARYCANNON_BEARING.get(), CBCModernWarfareBlocks.INCOMPLETE_NETHERSTEEL_ROTARYCANNON_BEARING.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_ROTARYCANNON_BREECH.get(), CBCModernWarfareBlocks.INCOMPLETE_NETHERSTEEL_ROTARYCANNON_BEARING.get()));


		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_BARREL.get(), CBCModernWarfareBlocks.CAST_IRON_MEDIUMCANNON_BARREL.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_RECOIL_BARREL.get(), CBCModernWarfareBlocks.INCOMPLETE_CAST_IRON_MEDIUMCANNON_RECOIL_BARREL.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_BREECH.get(), CBCModernWarfareBlocks.INCOMPLETE_CAST_IRON_MEDIUMCANNON_BREECH.get()));

		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_BARREL.get(), CBCModernWarfareBlocks.BRONZE_MEDIUMCANNON_BARREL.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_RECOIL_BARREL.get(), CBCModernWarfareBlocks.INCOMPLETE_BRONZE_MEDIUMCANNON_RECOIL_BARREL.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_BREECH.get(), CBCModernWarfareBlocks.INCOMPLETE_BRONZE_MEDIUMCANNON_BREECH.get()));

		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_BARREL.get(), CBCModernWarfareBlocks.STEEL_MEDIUMCANNON_BARREL.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_RECOIL_BARREL.get(), CBCModernWarfareBlocks.INCOMPLETE_STEEL_MEDIUMCANNON_RECOIL_BARREL.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_BREECH.get(), CBCModernWarfareBlocks.INCOMPLETE_STEEL_MEDIUMCANNON_BREECH.get()));

		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_BARREL.get(), CBCModernWarfareBlocks.NETHERSTEEL_MEDIUMCANNON_BARREL.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL.get(), CBCModernWarfareBlocks.INCOMPLETE_NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL.get()));
		cons.accept(recipe(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_BREECH.get(), CBCModernWarfareBlocks.INCOMPLETE_NETHERSTEEL_MEDIUMCANNON_BREECH.get()));
	}


	protected Result recipe(Block input, Block result) {
		return recipe(CBCRegistryUtils.getBlockLocation(result).getPath(), input, result);
	}

	protected Result recipe(String path, Block input, Block result) {
		return recipe(path, input, result, true);
	}

	protected Result recipe(String path, Block input, Block result, boolean obeyFacing) {
		return new Result(BlockRecipeIngredient.of(input), result, CBCUtils.location(this.modid, path), obeyFacing);
	}

	protected Result recipe(TagKey<Block> input, Block result) {
		return recipe(CBCRegistryUtils.getBlockLocation(result).getPath(), input, result);
	}

	protected Result recipe(String path, TagKey<Block> input, Block result) {
		return recipe(path, input, result, true);
	}

	protected Result recipe(String path, TagKey<Block> input, Block result, boolean obeyFacing) {
		return new Result(BlockRecipeIngredient.of(input), result, CBCUtils.location(this.modid, path), obeyFacing);
	}

	private record Result(BlockRecipeIngredient input, Block result, ResourceLocation id,
						  boolean obeyFacing) implements FinishedBlockRecipe {
		@Override
		public void serializeRecipeData(JsonObject obj) {
			obj.addProperty("input", this.input.stringForSerialization());
			obj.addProperty("result", CBCRegistryUtils.getBlockLocation(this.result).toString());
			obj.addProperty("obey_facing_or_axis", this.obeyFacing);
		}

		@Override
		public BlockRecipeSerializer<?> getSerializer() {
			return BlockRecipeSerializer.DRILL_BORING;
		}
	}
}
