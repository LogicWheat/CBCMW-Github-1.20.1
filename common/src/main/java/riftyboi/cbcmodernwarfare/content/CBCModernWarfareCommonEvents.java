package riftyboi.cbcmodernwarfare.content;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import rbasamoyai.createbigcannons.munitions.config.MunitionPropertiesHandler;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.IncendiaryApplicationDeployerRecipe;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.MediumFuzingDeployerRecipe;
import riftyboi.cbcmodernwarfare.crafting.munition_assembly.MediumTracerDeployerRecipe;

public class CBCModernWarfareCommonEvents {
	public static void onDatapackReload(MinecraftServer server) {
		MunitionPropertiesHandler.syncToAll(server);
	}

	public static void onDatapackSync(ServerPlayer player) {
		MunitionPropertiesHandler.syncTo(player);
	}

	public static void onAddReloadListeners(BiConsumer<PreparableReloadListener, ResourceLocation> cons) {;
		cons.accept(MunitionPropertiesHandler.ReloadListenerProjectiles.INSTANCE, CBCModernWarfare.resource("projectile_properties_handler"));
	}
	public static void onAddDeployerRecipes(DeployerBlockEntity deployer, Container container,
											BiConsumer<Supplier<Optional<? extends Recipe<? extends Container>>>, Integer> cons) {
		Level level = ((BlockEntity) deployer).getLevel();
		ItemStack containerItem = container.getItem(0);
		ItemStack deployerItem = container.getItem(1);

		MediumFuzingDeployerRecipe fuzingRecipe = new MediumFuzingDeployerRecipe(containerItem, deployerItem);
		if (fuzingRecipe.matches(container, level)) {
			cons.accept(() -> Optional.of(fuzingRecipe), 25);
		}
		IncendiaryApplicationDeployerRecipe incendiaryRecipie = new IncendiaryApplicationDeployerRecipe(containerItem, deployerItem);
		if (incendiaryRecipie.matches(container, level)) {
			cons.accept(() -> Optional.of(incendiaryRecipie), 25);
		}
		MediumTracerDeployerRecipe tracerRecipie = new MediumTracerDeployerRecipe(containerItem, deployerItem);
		if (tracerRecipie.matches(container, level)) {
			cons.accept(() -> Optional.of(tracerRecipie), 25);
		}
	}
}

