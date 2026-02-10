package riftyboi.cbcmodernwarfare.forge;

import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import riftyboi.cbcmodernwarfare.CBCModernWarfareClientCommon;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockPartials;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;


@EventBusSubscriber(Dist.CLIENT)
public class CBCModernWarfareClientForge {


	public static void prepareClient(IEventBus modEventBus, IEventBus forgeEventBus) {
		CBCModernWarfareBlockPartials.init();
		modEventBus.addListener(CBCModernWarfareClientForge::onClientSetup);
		forgeEventBus.addListener(CBCModernWarfareClientForge::onScrollMouse);
		forgeEventBus.addListener(CBCModernWarfareClientForge::onSetupCamera);
		forgeEventBus.addListener(CBCModernWarfareClientForge::onClientGameTick);
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == Phase.START) CBCModernWarfareClientCommon.onClientTickStart(Minecraft.getInstance());
		else if (event.phase == Phase.END) CBCModernWarfareClientCommon.onClientTickEnd(Minecraft.getInstance());
	}

	public static void onSetupCamera(ViewportEvent.ComputeCameraAngles evt) {
		if (CBCModernWarfareClientCommon.onCameraSetup(evt.getCamera(), evt.getPartialTick(), evt::getYaw, evt::getPitch, evt::getRoll,
			evt::setYaw, evt::setPitch, evt::setRoll) && evt.isCancelable()) {
			evt.setCanceled(true);
		}
	}



	public static void onClientGameTick(TickEvent.ClientTickEvent evt) {
		CBCModernWarfareClientCommon.onClientGameTick(Minecraft.getInstance());
	}



	public static void onScrollMouse(InputEvent.MouseScrollingEvent evt) {
		if (CBCModernWarfareClientCommon.onScrollMouse(Minecraft.getInstance(), evt.getScrollDelta())) evt.setCanceled(true);
	}

	public static void onClientSetup(FMLClientSetupEvent event) {
		CBCModernWarfareClientCommon.onClientSetup();
	}


}
