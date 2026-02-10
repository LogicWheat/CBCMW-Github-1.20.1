package riftyboi.cbcmodernwarfare.multiloader;


import dev.architectury.injectables.annotations.ExpectPlatform;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.breech.AbstractRotarycannonBreechBlockEntity;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;

import java.util.function.Supplier;

public class IndexPlatform {

	@ExpectPlatform
	public static AbstractRotarycannonBreechBlockEntity makeRotarycannonBreech(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		throw new AssertionError();
	}
	@ExpectPlatform
	public static Supplier<RecipeSerializer<?>> registerRecipeSerializer(ResourceLocation id, NonNullSupplier<RecipeSerializer<?>> sup) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static void registerRecipeType(ResourceLocation id, Supplier<RecipeType<?>> type) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static ServerPlayer createSightFakePlayer(ServerLevel level, AbstractSightEntity conductor) {
		throw new AssertionError();
	}
	@ExpectPlatform
	public static void postChunkEventClient(LevelChunk chunk, boolean load) {
		throw new AssertionError();
	}

}
