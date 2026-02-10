package riftyboi.cbcmodernwarfare.content.sights.entity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.NotNull;

import rbasamoyai.createbigcannons.multiloader.NetworkPlatform;
import riftyboi.cbcmodernwarfare.content.sights.camera_handler.CameraPossessionController;
import riftyboi.cbcmodernwarfare.content.sights.camera_handler.CameraClientHandler;
import riftyboi.cbcmodernwarfare.multiloader.IndexPlatform;
import riftyboi.cbcmodernwarfare.network.SetCameraViewPacket;

public class AbstractSightEntity extends Entity {
	public SightType sightType;
	public Direction facing;
	public BlockPos oldPos;
	private float yawLimit;
	private float elevation;
	private float depression;
	private int currentFOV;
	private int maxFov;
	private int minFov;
	private double horizontalOffset;
	private double heightOffset;
	public static final List<Player> recentlyDismountedPlayers = new ArrayList<>();
	private ServerPlayer fakePlayer = null;
	public double firstGoodX;
	public double firstGoodY;
	public double firstGoodZ;
	public double lastGoodX;
	public double lastGoodY;
	public double lastGoodZ;
	public int receivedMovePacketCount;
	public int knownMovePacketCount;

	@NotNull
	public WeakReference<ServerPlayer> currentlyViewing = new WeakReference<>(null);
	private boolean hasSentChunks = false;

	public AbstractSightEntity(EntityType<? extends Entity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void defineSynchedData() {
	}

	public void setParams(Direction facing, float elevation, float depression, float yawLimit, int maxFov, int minFov, double Horitzontaloffset, double heightOffset, SightType type) {
		this.facing = facing;
		this.elevation = elevation;
		this.depression = depression;
		this.yawLimit = yawLimit;
		this.currentFOV = maxFov;
		this.maxFov = maxFov;
		this.minFov = minFov;
		this.sightType = type;
		this.heightOffset = heightOffset;
		this.horizontalOffset = Horitzontaloffset;
		this.setYRot(facing.toYRot());
		this.yRotO = facing.toYRot();
		this.oldPos = this.blockPosition();
	}

	public boolean hasSentChunks() {
		return hasSentChunks;
	}

	public void setHasSentChunks(boolean hasSentChunks) {
		this.hasSentChunks = hasSentChunks;
	}

	public SectionPos oldSectionPos = null;

	public static boolean hasRecentlyDismounted(Player player) {
		return recentlyDismountedPlayers.remove(player);
	}


	public boolean startViewing(ServerPlayer player) {
		ServerPlayer current = currentlyViewing.get();
		if (current != null && current.getCamera() == this && current.isAlive() && current != player) {
			return false;
		}
		ServerLevel serverLevel = player.serverLevel();
		if (serverLevel != level) {
			return false;
		}

		currentlyViewing = new WeakReference<>(player);
		oldSectionPos = null;
		SectionPos chunkPos = SectionPos.of(blockPosition());
		int viewDistance = player.server.getPlayerList().getViewDistance();

		if (player.getCamera() instanceof AbstractSightEntity conductor)
			conductor.stopViewing(player);


		//can't use ServerPlayer#setCamera here because it also teleports the player
		player.camera = this;
		NetworkPlatform.sendToClientPlayer(new SetCameraViewPacket(this), player);
		resetPosition();
		// update ConductorPossessionController.setRenderPosition in #tick
		return true;
	}

	public void stopViewing(ServerPlayer player) {
		if (!level.isClientSide) {
			currentlyViewing.clear();
			player.camera = player;
			NetworkPlatform.sendToClientPlayer(new SetCameraViewPacket(player), player);
			recentlyDismountedPlayers.add(player);
			this.discard();
		}
	}

	public double getHorizontalOffset() {
		return horizontalOffset;
	}

	public double getHeightOffset(){
		return heightOffset;
	}

	protected float getEyeHeight(@Nonnull Pose pose, EntityDimensions dimensions) {
		return dimensions.height;
	}



	@Override
	public void tick() {
		this.resetPosition();
		this.oldPos = this.blockPosition();
		SectionPos sectionPos = SectionPos.of(this);
		if (!sectionPos.equals(oldSectionPos)) {
			setHasSentChunks(false);
		}
		if (level.isClientSide) {
			CameraPossessionController.tryUpdatePossession(this);
		}
		super.tick();
		if (level instanceof ServerLevel serverLevel) {
			if (fakePlayer == null) {
				fakePlayer = IndexPlatform.createSightFakePlayer(serverLevel, this);
			}
			if ((Object) currentlyViewing.get() instanceof ServerPlayer player) {
				SectionPos chunkPos = SectionPos.of(blockPosition());
				int viewDistance = player.server.getPlayerList().getViewDistance();
				for (int x = chunkPos.getX() - viewDistance; x <= chunkPos.getX() + viewDistance; x++) {
					for (int z = chunkPos.getZ() - viewDistance; z <= chunkPos.getZ() + viewDistance; z++) {
						serverLevel.getChunkSource().addRegionTicket(TicketType.FORCED, new ChunkPos(x, z), 3, new ChunkPos(x, z));
					}
				}
			}
		}
	}
	public void turnView(double yRot, double xRot) {
		turnView(yRot, xRot, false);
	}


	public BlockPos getOldPos () {
		return oldPos;
	}

	public void turnView(double yRot, double xRot, boolean raw) {
		float base_yaw = this.getBaseYaw();
		float sense = !raw && this.getFOV() > 0 ? (float) this.getFOV() / 70.0F : 1.0F;
		float f = (float) xRot * sense;
		float g = (float) yRot * sense;

		this.setXRot(this.getXRot() + f);
		this.setYRot(this.getYRot() + g);

		if (!(yawLimit >= 180.0F)) {
			this.setYRot(Mth.clamp(this.getYRot(), base_yaw - yawLimit, base_yaw + yawLimit));
		}

		this.setXRot(Mth.clamp(this.getXRot(), -elevation, depression));
		this.xRotO += f;
		this.yRotO += g;
		this.xRotO = Mth.clamp(this.xRotO, -elevation, depression);
		if (yawLimit < 180.0F) {
			this.yRotO = Mth.clamp(this.yRotO, base_yaw - yawLimit, base_yaw + yawLimit);
		}


		if (this.getVehicle() != null) {
			this.getVehicle().onPassengerTurned(this);
		}
	}


	public void changeFov(double change) {
		if (this.currentFOV > this.maxFov) {
			this.currentFOV = maxFov;
		}
		this.currentFOV += change;
		if (this.currentFOV < this.minFov) {
			this.currentFOV = minFov;
		}
	}

	@Override
	public boolean isControlledByLocalInstance() {
		return super.isControlledByLocalInstance() || isPossessedAndClient();
	}

	public boolean isPossessedAndClient() {
		return level.isClientSide && isPossessed();
	}


	public boolean isPossessed() {
		return level.isClientSide ? CameraClientHandler.isPossessed(this) : currentlyViewing.get() != null;
	}

	public int getFOV() {
		return this.currentFOV;
	}

	public SightType getSightType() {
		return this.sightType;
	}

	public float getBaseYaw() {
		return this.facing.toYRot();
	}

	protected Vec2 getRotationFromTarget(Vec3 target, float partialTick) {
		Vec3 relativePos = target.subtract(this.getEyePosition(partialTick));
		double distance = relativePos.length();

		double d0 = relativePos.x;
		double d1 = relativePos.y;
		double d2 = relativePos.z;
		double horizontalDistance = Math.sqrt(d0 * d0 + d2 * d2);
		double angleX = -(Mth.atan2(d1, horizontalDistance) * (180.0 / Math.PI));
		double angleY = (Mth.atan2(d2, d0) * (180.0 / Math.PI)) - 90.0;
		return new Vec2((float) angleX, (float) angleY);
	}
	public Vec3 getCameraOffset() {
		// Get the player's yRot (yaw rotation)
		float yRot = this.getYRot(); // Adjust based on how your entity class provides this

		// Calculate the offsets
		double offsetX = horizontalOffset * -Math.sin(Math.toRadians(yRot));
		double offsetZ = horizontalOffset * Math.cos(Math.toRadians(yRot));
		double offsetY = -heightOffset;

		// Return the offset as a Vec3 (Minecraft's vector class)
		return new Vec3(offsetX, offsetY, offsetZ);
	}


	private void resetPosition() {
		this.firstGoodX = this.lastGoodX = this.getX();
		this.firstGoodY = this.lastGoodY = this.getY();
		this.firstGoodZ = this.lastGoodZ = this.getZ();
		this.knownMovePacketCount = this.receivedMovePacketCount;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
		this.yawLimit = nbt.getFloat("yawLimit");
		this.elevation = nbt.getFloat("elevation");
		this.depression = nbt.getFloat("depression");
		this.facing = Direction.byName(nbt.getString("facing"));
		this.currentFOV = nbt.getInt("fieldOfView");
		this.minFov = nbt.getInt("minFOV");
		this.maxFov = nbt.getInt("maxFOV");
		this.sightType = SightType.valueOf(nbt.getString("SightType"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		nbt.putFloat("yawLimit", this.yawLimit);
		nbt.putFloat("elevation", this.elevation);
		nbt.putFloat("depression", this.depression);
		nbt.putString("facing", this.facing.toString());
		nbt.putInt("fieldOfView", this.getFOV());
		nbt.putInt("minFOV", this.minFov);
		nbt.putInt("maxFOV", this.maxFov);
		nbt.putString("SightType", this.getSightType().toString());
	}

	public enum SightType {
		ANALOG,
		TELESCOPIC,
		PERISCOPE
	}
}
