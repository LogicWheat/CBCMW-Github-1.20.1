package riftyboi.cbcmodernwarfare.forge;

import com.simibubi.create.content.kinetics.deployer.DeployerRecipeSearchEvent;

import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import riftyboi.cbcmodernwarfare.CBCModernWarfareClientCommon;
import riftyboi.cbcmodernwarfare.content.CBCModernWarfareCommonEvents;
import net.minecraftforge.event.AddReloadListenerEvent;

public class CBCMWCommonForgeEvents {
	public static void register(IEventBus forgeEventBus) {
		forgeEventBus.addListener(CBCMWCommonForgeEvents::onDeployerRecipeSearch);
		forgeEventBus.addListener(CBCMWCommonForgeEvents::onDatapackSync);
		forgeEventBus.addListener(CBCMWCommonForgeEvents::onAddReloadListeners);
	}

	public static void onDatapackSync(OnDatapackSyncEvent evt) {
		if (evt.getPlayer() == null) {
			CBCModernWarfareCommonEvents.onDatapackReload(evt.getPlayerList().getServer());
		} else {
			CBCModernWarfareCommonEvents.onDatapackSync(evt.getPlayer());
		}
	}


	public static void onDeployerRecipeSearch(DeployerRecipeSearchEvent evt) {
		CBCModernWarfareCommonEvents.onAddDeployerRecipes(evt.getBlockEntity(), evt.getInventory(), evt::addRecipe);
	}

	public static void onAddReloadListeners(AddReloadListenerEvent event) {
		CBCModernWarfareCommonEvents.onAddReloadListeners((m, l) -> event.addListener(m));
	}

}
