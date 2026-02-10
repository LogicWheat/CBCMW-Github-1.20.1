package riftyboi.cbcmodernwarfare.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.Nullable;

import rbasamoyai.createbigcannons.multiloader.NetworkPlatform;
import rbasamoyai.createbigcannons.network.RootPacket;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;

import java.util.concurrent.Executor;

import com.google.common.primitives.Floats;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Collections;
import java.util.Set;

public class CameraMovePacket implements RootPacket {
	final int id;
	final ServerboundMovePlayerPacket.PosRot packet;


	public CameraMovePacket(AbstractSightEntity entity, ServerboundMovePlayerPacket.PosRot packet) {
		this.id = entity.getId();
		this.packet = packet;
	}

	@Override
	public void rootEncode(FriendlyByteBuf buf) {
		buf.writeInt(this.id);
		this.packet.write(buf);
	}

	public CameraMovePacket(FriendlyByteBuf buf) {
		this.id = buf.readVarInt();
		this.packet = ServerboundMovePlayerPacket.PosRot.read(buf);
	}

	private static boolean containsInvalidValues(double x, double y, double z, float yaw, float pitch) {
		return Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z) || !Floats.isFinite(pitch) || !Floats.isFinite(yaw);
	}

	private static double clampHorizontal(double value) {
		return Mth.clamp(value, -3.0E7, 3.0E7);
	}

	private static double clampVertical(double value) {
		return Mth.clamp(value, -2.0E7, 2.0E7);
	}


	private static boolean isPlayerCollidingWithAnythingNew(AbstractSightEntity conductor, LevelReader world, AABB box) {
		Iterable<VoxelShape> iterable = world.getCollisions(conductor, conductor.getBoundingBox().deflate(1.0E-5f));
		VoxelShape voxelShape = Shapes.create(box.deflate(1.0E-5f));
		for (VoxelShape voxelShape2 : iterable) {
			if (Shapes.joinIsNotEmpty(voxelShape2, voxelShape, BooleanOp.AND)) continue;
			return true;
		}
		return false;
	}

	public static void teleport(ServerPlayer player, AbstractSightEntity conductor, double x, double y, double z, float yaw, float pitch) {
		teleport(player, conductor, x, y, z, yaw, pitch, Collections.emptySet(), false);
	}

	public static void teleport(ServerPlayer player, AbstractSightEntity conductor, double x, double y, double z, float yaw, float pitch, Set<RelativeMovement> relativeSet, boolean dismountVehicle) {
		conductor.absMoveTo(x, y, z, yaw, pitch);
		NetworkPlatform.sendToClientPlayer(new CameraMovePacket(conductor, new ServerboundMovePlayerPacket.PosRot(x, y, y, yaw, pitch, conductor.onGround())), player);
	}


	@Override
	public void handle(Executor exec, PacketListener listener, @Nullable ServerPlayer sender) {
		if (sender != null) handle(sender);
		handle(Minecraft.getInstance());
	}

	public void handle(Minecraft mc) {
		if (mc.level != null && mc.level.getEntity(id) instanceof AbstractSightEntity conductor && mc.cameraEntity == conductor) {
            conductor.absMoveTo(packet.getX(mc.cameraEntity.getX()), packet.getY(mc.cameraEntity.getY()), packet.getZ(mc.cameraEntity.getZ()), packet.getYRot(mc.cameraEntity.getYRot()), packet.getXRot(mc.cameraEntity.getXRot()));
			double d0 = packet.getX(conductor.getX());
			double d1 = packet.getY(conductor.getY());
			double d2 = packet.getZ(conductor.getZ());
			conductor.syncPacketPositionCodec(d0, d1, d2);
			if (true) {
				conductor.setPos(d0, d1, d2);
				float f = (float)(packet.getYRot(conductor.getYRot()) * 360) / 256.0F;
				float f1 = (float)(packet.getXRot(conductor.getXRot()) * 360) / 256.0F;
				conductor.lerpTo(d0, d1, d2, f, f1, 3, true);
				conductor.setOnGround(packet.isOnGround());
			}
		}
	}


	public void handle(ServerPlayer sender1) {
		if (sender1.level.getEntity(id) instanceof AbstractSightEntity conductor && sender1.getCamera() == conductor) {
			if (containsInvalidValues(packet.getX(0.0), packet.getY(0.0), packet.getZ(0.0), packet.getYRot(0.0f), packet.getXRot(0.0f))) {
				sender1.connection.disconnect(Component.translatable("multiplayer.disconnect.invalid_player_movement"));
				return;
			}
			if (!(conductor.level instanceof ServerLevel serverLevel))
				return;
			double d = clampHorizontal(packet.getX(conductor.getX()));
			double e = clampVertical(packet.getY(conductor.getY()));
			double f = clampHorizontal(packet.getZ(conductor.getZ()));
			float g = Mth.wrapDegrees(packet.getYRot(conductor.getYRot()));
			float h = Mth.wrapDegrees(packet.getXRot(conductor.getXRot()));
			if (conductor.isPassenger()) {
				conductor.absMoveTo(conductor.getX(), conductor.getY(), conductor.getZ(), g, h);
				return;
			}
			double i = conductor.getX();
			double j = conductor.getY();
			double k = conductor.getZ();
			double l = conductor.getY();
			double m = d - conductor.firstGoodX;
			double n = e - conductor.firstGoodY;
			double o = f - conductor.firstGoodZ;
			double p = conductor.getDeltaMovement().lengthSqr();
			double q = m * m + n * n + o * o;

			++conductor.receivedMovePacketCount;
			int r = conductor.receivedMovePacketCount - conductor.knownMovePacketCount;
			if (true) {
				float s = 100.0f;
				if (q - p > (double)(s * (float)r)) {// && !sender1.server.isSingleplayerOwner(sender1.getGameProfile())) {
					teleport(sender1, conductor, conductor.getX(), conductor.getY(), conductor.getZ(), conductor.getYRot(), conductor.getXRot());
					return;
				}
			}
			AABB aABB = conductor.getBoundingBox();
			m = d - conductor.lastGoodX;
			n = e - conductor.lastGoodY;
			o = f - conductor.lastGoodZ;
			boolean bl22 = conductor.verticalCollisionBelow;
			conductor.move(MoverType.PLAYER, new Vec3(m, n, o));
			double t = n;
			m = d - conductor.getX();
			n = e - conductor.getY();
			if (n > -0.5 || n < 0.5) {
				n = 0.0;
			}
			o = f - conductor.getZ();
			q = m * m + n * n + o * o;
			boolean bl3 = false;
			if (q > 0.0625) {
				bl3 = true;
//                Railways.LOGGER.warn("{} moved wrongly!", (Object)sender1.getName().getString());
				return;
			}
			conductor.absMoveTo(d, e, f, g, h);
			if (!conductor.noPhysics && (bl3 && serverLevel.noCollision(conductor, aABB) || isPlayerCollidingWithAnythingNew(conductor, serverLevel, aABB))) {
				teleport(sender1, conductor, i, j, k, g, h);
				return;
			}

			conductor.setOnGround(packet.isOnGround());
			conductor.lastGoodX = conductor.getX();
			conductor.lastGoodY = conductor.getY();
			conductor.lastGoodZ = conductor.getZ();
		}
	}


}
