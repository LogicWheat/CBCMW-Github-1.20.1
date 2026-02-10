package riftyboi.cbcmodernwarfare.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import org.jetbrains.annotations.Nullable;

import rbasamoyai.createbigcannons.network.RootPacket;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;

import java.util.concurrent.Executor;

public class SetCameraViewPacket implements RootPacket {
		private final int id;

		public int id() {
			return this.id;
		}

		public SetCameraViewPacket(Entity entity) {
			this.id = entity.getId();
		}
		public SetCameraViewPacket(FriendlyByteBuf buf) {
			this.id = buf.readVarInt();
		}

		@Override
		public void rootEncode(FriendlyByteBuf buf) {
			buf.writeVarInt(this.id);
		}

		public void handle(Executor exec, PacketListener listener, @Nullable ServerPlayer sender) {
			Minecraft mc = Minecraft.getInstance();
			if (mc.level == null) return;

			Entity entity = mc.level.getEntity(this.id());

			// Check if the entity is a CameraEntity2 or a Player
			if (entity instanceof AbstractSightEntity || entity instanceof Player) {
				mc.setCameraEntity(entity);
			}
		}
}



