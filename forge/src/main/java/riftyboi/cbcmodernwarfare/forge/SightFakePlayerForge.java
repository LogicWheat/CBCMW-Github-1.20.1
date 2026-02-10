package riftyboi.cbcmodernwarfare.forge;

import com.mojang.authlib.GameProfile;


import java.lang.ref.WeakReference;
import java.util.OptionalInt;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;
import riftyboi.cbcmodernwarfare.content.sights.camera_handler.ISightHoldingFakePlayer;

import net.minecraftforge.common.util.FakePlayer;

public class SightFakePlayerForge extends FakePlayer implements ISightHoldingFakePlayer {
	private static final Connection NETWORK_MANAGER = new Connection(PacketFlow.CLIENTBOUND);

	private final WeakReference<AbstractSightEntity> conductor;
	public SightFakePlayerForge(ServerLevel level, AbstractSightEntity conductor) {
		super(level, FAKE_PLAYER_PROFILE);
		connection = new ConductorNetHandler(level.getServer(), this);
		this.conductor = new WeakReference<>(conductor);
	}

	@Override
	@NotNull
	public OptionalInt openMenu(MenuProvider container) {
		return OptionalInt.empty();
	}

	@Override
	@NotNull
	public Component getDisplayName() {
		return Component.translatable(CBCModernWarfare.MOD_ID + "." + "sight_name");
	}

	@Override
	public float getEyeHeight(@NotNull Pose pose) {
		return 0;
	}

	@Override
	public Vec3 position() {
		return new Vec3(getX(), getY(), getZ());
	}

	@Override
	public float getCurrentItemAttackStrengthDelay() {
		return 1 / 64f;
	}

	@Override
	public boolean canEat(boolean ignoreHunger) {
		return false;
	}

	@Override
	@NotNull
	public ItemStack eat(@NotNull Level world, ItemStack stack) {
		stack.shrink(1);
		return stack;
	}

	@Override
	public @Nullable AbstractSightEntity getConductor() {
		return conductor.get();
	}

	private static class ConductorNetHandler extends ServerGamePacketListenerImpl {
		public ConductorNetHandler(MinecraftServer server, ServerPlayer player) {
			super(server, NETWORK_MANAGER, player);
		}

		@Override
		public void send(@NotNull Packet<?> packet) {
		}

		@Override
		public void send(Packet<?> packet, @Nullable PacketSendListener listener) {
		}
	}

	public static final GameProfile FAKE_PLAYER_PROFILE = new GameProfile(
		UUID.fromString("52E2D650-73AB-3A89-8B32-8F868CE6484A"),
		"[SIGHT PROFILE]"
	);
}
