package riftyboi.cbcmodernwarfare.multiloader.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.ChunkEvent;

import java.util.function.Supplier;


import com.tterrag.registrate.util.nullness.NonNullSupplier;


import net.minecraft.world.item.crafting.RecipeType;

import riftyboi.cbcmodernwarfare.cannons.rotarycannon.breech.AbstractRotarycannonBreechBlockEntity;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;
import riftyboi.cbcmodernwarfare.forge.CBCModernWarfareForge;
import riftyboi.cbcmodernwarfare.forge.SightFakePlayerForge;
import riftyboi.cbcmodernwarfare.forge.cannons.RotarycannonBreechBlockEntity;


public class IndexPlatformImpl {

	public static AbstractRotarycannonBreechBlockEntity makeRotarycannonBreech(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		return new RotarycannonBreechBlockEntity(type, pos, state);
	}

	public static void registerDeferredParticleType(String name, ParticleType<?> type) {
		CBCModernWarfareForge.PARTICLE_REGISTER.register(name, () -> type);
	}
	public static void registerDeferredParticles() {
		CBCModernWarfareForge.PARTICLE_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static Supplier<RecipeSerializer<?>> registerRecipeSerializer(ResourceLocation id, NonNullSupplier<RecipeSerializer<?>> sup) {
		return CBCModernWarfareForge.RECIPE_SERIALIZER_REGISTER.register(id.getPath(), sup);
	}

	public static void registerRecipeType(ResourceLocation id, Supplier<RecipeType<?>> type) {
		CBCModernWarfareForge.RECIPE_TYPE_REGISTER.register(id.getPath(), type);
	}

	public static ServerPlayer createSightFakePlayer(ServerLevel level, AbstractSightEntity conductor) {
		return new SightFakePlayerForge(level, conductor);
	}
	public static void postChunkEventClient(LevelChunk chunk, boolean load) {
		if (load) {
			MinecraftForge.EVENT_BUS.post(new ChunkEvent.Load(chunk, false));
		} else {
			MinecraftForge.EVENT_BUS.post(new ChunkEvent.Unload(chunk));
		}
	}
}
