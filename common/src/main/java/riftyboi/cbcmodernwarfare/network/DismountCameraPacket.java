package riftyboi.cbcmodernwarfare.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.Nullable;

import rbasamoyai.createbigcannons.multiloader.NetworkPlatform;
import rbasamoyai.createbigcannons.network.RootPacket;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;

import java.util.concurrent.Executor;

public class DismountCameraPacket implements RootPacket {
	public DismountCameraPacket() {
	}

	public DismountCameraPacket(FriendlyByteBuf buf) {
	}

	@Override
	public void rootEncode(FriendlyByteBuf buf) {
	}

	@Override
	public void handle(Executor exec, PacketListener listener, @Nullable ServerPlayer sender) {
		if (sender.getCamera() instanceof AbstractSightEntity conductor) {
			conductor.stopViewing(sender);
		} else {
			NetworkPlatform.sendToClientTracking(new SetCameraViewPacket(sender), sender);
		}
	}
}
