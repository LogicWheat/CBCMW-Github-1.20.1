package riftyboi.cbcmodernwarfare.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.game.ServerPacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import rbasamoyai.createbigcannons.network.RootPacket;
import riftyboi.cbcmodernwarfare.cannons.RecoilingCannon;

import javax.annotation.Nullable;

import java.util.concurrent.Executor;

public class RecoilingCannonUpdatePacket implements RootPacket {
    public int entityID;
    public double motion;
    public double target;



    public RecoilingCannonUpdatePacket() {}

    public RecoilingCannonUpdatePacket(int entityID, double motion, double sequenceLimit) {
        this.entityID = entityID;
        this.motion = motion;
        this.target = sequenceLimit;
    }

    public RecoilingCannonUpdatePacket(FriendlyByteBuf buffer) {
        entityID = buffer.readInt();
        motion = buffer.readFloat();
        target = buffer.readFloat();
    }


	@Override
	public void rootEncode(FriendlyByteBuf buffer) {
		buffer.writeInt(entityID);
		buffer.writeFloat((float) motion);
		buffer.writeFloat((float) target);
	}
	@Environment(EnvType.CLIENT)
	@Override
	public void handle(Executor exc, PacketListener listener, @Nullable ServerPlayer player) {
		Entity entity = Minecraft.getInstance().level.getEntity(this.entityID);
		if (!(entity instanceof RecoilingCannon rce))
			return;
		rce.handlePacket(this);
	}


}
