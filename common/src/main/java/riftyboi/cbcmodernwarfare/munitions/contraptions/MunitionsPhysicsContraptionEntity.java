package riftyboi.cbcmodernwarfare.munitions.contraptions;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;

import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.OrientedContraptionEntity;

import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.math.VecHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import org.joml.Quaternionf;

import org.joml.Vector3f;

import rbasamoyai.createbigcannons.CreateBigCannons;

import rbasamoyai.createbigcannons.block_armor_properties.BlockArmorPropertiesHandler;
import rbasamoyai.createbigcannons.block_armor_properties.BlockArmorPropertiesProvider;
import rbasamoyai.createbigcannons.config.CBCCfgMunitions;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.index.CBCDamageTypes;
import rbasamoyai.createbigcannons.index.CBCEntityTypes;
import rbasamoyai.createbigcannons.multiloader.NetworkPlatform;
import rbasamoyai.createbigcannons.munitions.AbstractCannonProjectile;
import rbasamoyai.createbigcannons.munitions.CannonDamageSource;
import rbasamoyai.createbigcannons.munitions.ImpactExplosion;
import rbasamoyai.createbigcannons.munitions.ProjectileContext;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.ProjectileBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.solid_shot.SolidShotProjectile;
import rbasamoyai.createbigcannons.munitions.config.DimensionMunitionPropertiesHandler;
import rbasamoyai.createbigcannons.munitions.config.FluidDragHandler;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.fuzes.FuzeItem;
import rbasamoyai.createbigcannons.network.ClientboundPlayBlockHitEffectPacket;
import rbasamoyai.createbigcannons.network.ClientboundUpdateContraptionPacket;
import rbasamoyai.createbigcannons.utils.CBCRegistryUtils;
import rbasamoyai.createbigcannons.utils.CBCUtils;
import rbasamoyai.ritchiesprojectilelib.RitchiesProjectileLib;
import rbasamoyai.ritchiesprojectilelib.network.ClientboundPreciseMotionSyncPacket;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareEntityTypes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionPropertiesHandlers;
import riftyboi.cbcmodernwarfare.mixin.interfaces.AbstractBigCannonAccessor;
import riftyboi.cbcmodernwarfare.mixin.interfaces.AbstractProjectleAccessor;
import riftyboi.cbcmodernwarfare.mixin.interfaces.FuzedProjectileAccessor;
import riftyboi.cbcmodernwarfare.munitions.MWProjectileContext;
import riftyboi.cbcmodernwarfare.munitions.contraptions.config.MunitionsContraptionEntityProperties;
import riftyboi.cbcmodernwarfare.munitions.contraptions.render.MunitionsContraptionRotationState;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.PropelledContraptionMunitionBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.fuel_tanks.IFuelTankBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.IGuidanceBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.MunitionsLauncherGuidanceBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.propulsion.IThrusterBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.propulsion.MunitionsLauncherThrusterBlock;


import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;


public class MunitionsPhysicsContraptionEntity extends OrientedContraptionEntity {
	protected static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(MunitionsPhysicsContraptionEntity.class, EntityDataSerializers.FLOAT);
	protected static final EntityDataAccessor<Integer> FUEL = SynchedEntityData.defineId(MunitionsPhysicsContraptionEntity.class, EntityDataSerializers.INT);
	protected static final EntityDataAccessor<Float> BURN_RATE = SynchedEntityData.defineId(MunitionsPhysicsContraptionEntity.class, EntityDataSerializers.FLOAT);
	protected static final EntityDataAccessor<Float> THRUST = SynchedEntityData.defineId(MunitionsPhysicsContraptionEntity.class, EntityDataSerializers.FLOAT);
	protected Map<BlockPos, Float> blockMass = new HashMap<>();
	protected int inGroundTime = 0;
	protected int inFluidTime = 0;
	protected int penetrationTime = 0;
	@Nullable protected Vec3 nextVelocity = null;
	@Nullable protected Vec3 orientation = null;
	protected BlockState lastPenetratedBlock = Blocks.AIR.defaultBlockState();
	protected AbstractBigCannonProjectile firstWarhead = null;
	protected BlockPos firstWarheadPos = null;
	protected BlockPos firstPos = null;
	protected BlockPos endPos = null;
	protected Set<BlockPos> changed = new HashSet<>();
	protected int localSoundCooldown;
	protected WeakHashMap<Entity, Integer> untouchableEntities = new WeakHashMap<>();


	public MunitionsPhysicsContraptionEntity(EntityType<?> type, Level world) {
		super(type, world);
	}

	public static MunitionsPhysicsContraptionEntity create(Level level, MunitionsPhysicsContraption c, Direction direction) {
		MunitionsPhysicsContraptionEntity entity = new MunitionsPhysicsContraptionEntity(CBCModernWarfareEntityTypes.MUNITIONS_CONTRAPTION.get(), level);
		entity.setContraption(c);
		entity.setInitialOrientation(direction);
		entity.setData();
		entity.setBoundingBox(new AABB(entity.blockPosition()));
		entity.setContraptionBounds();
		return entity;
	}




	@Override
	public void tick() {
		if (this.contraption == null) {
			this.discard();
			return;
		}

		this.tickWarheads();

		this.updateBlocks();

		this.setContraptionBounds();

		ChunkPos cpos = new ChunkPos(this.blockPosition());
		if (this.contraption != null) this.contraption.anchor = this.blockPosition();
		if (this.level().isClientSide || this.level().hasChunk(cpos.x, cpos.z)) {
			super.tick();


			if (this.nextVelocity != null) {
				boolean stop = this.nextVelocity.lengthSqr() < 1e-4d;
				// Bouncing is stochastic
				if (!this.level().isClientSide || stop) {
					if (stop)
						this.setOnGround(true);
					this.setContraptionMotion(this.nextVelocity);
				}
				this.nextVelocity = null;
			}

			if (!this.onGround())
				this.clipAndDamage();

			if (this.onGround()) {
				this.setContraptionMotion(Vec3.ZERO);
				this.entityData.set(FUEL, 0);
				if (!this.level().isClientSide) {
					if (this.shouldFall()) {
						this.setOnGround(false);
					} else if (!this.canLingerInGround()) {
						this.setOnGround(true);
						this.inGroundTime++;
						if (this.inGroundTime == 400) {
							this.discard();
						}
					}
				}
			} else {
				this.inGroundTime = 0;
				Vec3 oldVel = this.getDeltaMovement();
				Vec3 oldPos = this.position();
				if (this.nextVelocity != null) {
					Vec3 newPos = oldPos.add(oldVel);
					this.setPos(newPos);
				} else if (this.entityData.get(FUEL) > 0) {
					this.entityData.set(FUEL, (int) Math.max(this.entityData.get(FUEL) - this.entityData.get(BURN_RATE) ,0));
					Vec3 thrust = this.getThrust(oldPos, oldVel);
					Vec3 thrustPos = oldPos.add(oldVel).add(thrust.scale(0.5));
					this.setPos(thrustPos);
					this.setContraptionMotion(oldVel.add(thrust));
				} else {
					Vec3 accel = this.getForces(oldPos, oldVel);
					Vec3 newPos = oldPos.add(oldVel).add(accel.scale(0.5));
					this.setPos(newPos);
					this.setContraptionMotion(oldVel.add(accel));
				}
				this.setYRot(lerpRotation(this.xRotO, this.getYRot()));
				this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
			}

			this.tickGuidance();

			for (Iterator<Map.Entry<Entity, Integer>> iter = this.untouchableEntities.entrySet().iterator(); iter.hasNext(); ) {
				Map.Entry<Entity, Integer> entry = iter.next();
				if (entry.getKey().isRemoved() || entry.getValue() > 0 && entry.getValue() - 1 == 0) {
					iter.remove();
				} else if (entry.getValue() > 0) {
					entry.setValue(entry.getValue() - 1);
				}
			}

			if (this.inFluidTime > 0)
				--this.inFluidTime;
			if (this.penetrationTime > 0)
				--this.penetrationTime;
			if (this.localSoundCooldown > 0)
				--this.localSoundCooldown;

			if (this.level() instanceof ServerLevel slevel && !this.isRemoved()) {
				if (CBCConfigs.server().munitions.projectilesCanChunkload.get()) {
					ChunkPos cpos1 = new ChunkPos(this.blockPosition());
					RitchiesProjectileLib.queueForceLoad(slevel, cpos1.x, cpos1.z);
				}
			}
		}
	}


	@Override
	public void lerpTo(double x, double y, double z, float yRot, float xRot, int lerpSteps, boolean teleport) {
		if (this.tickCount < 2)
			return;
		super.lerpTo(x, y, z, yRot, xRot, lerpSteps, teleport);
	}

	protected void tickGuidance() {
		for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> s : this.contraption.getBlocks().entrySet()) {
		if (contraption instanceof MunitionsPhysicsContraption mpc && this.contraption.getBlocks().get(s.getKey()).state().getBlock() instanceof MunitionsLauncherGuidanceBlock guidance) {
				guidance.tickGuidance(this.level,s.getKey(),s.getValue().state(), mpc.presentBlockEntities.get(s.getKey()), this);
			}
		}
	}

	public ContraptionRotationState getRotationState() {
		return new MunitionsContraptionRotationState(this);
	}



	@Override
	@Environment(EnvType.CLIENT)
	public void applyLocalTransforms(PoseStack stack, float partialTicks) {
		Vec3 vel = this.getOrientation();
		if (vel.lengthSqr() < 1e-4d) vel = new Vec3(0, -1, 0);
		Vector3f facing = this.getInitialOrientation().step();
		Vec3 vecFacing = new Vec3(facing.x,facing.y,facing.z);

		stack.translate(-0.5f, 0.0f, -0.5f);

		TransformStack tstack = TransformStack.of(stack)
			.nudge(this.getId())
			.center();

		stack.mulPoseMatrix(CBCUtils.mat4x4fFacing(vel.normalize(), vecFacing));

		tstack.uncenter();
	}

	protected void clipAndDamage() {
		MWProjectileContext projCtx = new MWProjectileContext(this, CBCConfigs.server().munitions.damageRestriction.get());

		Vec3 pos = this.position();
		Vec3 currentStart = pos;
		double reach = Math.max(this.getBbWidth(), this.getBbHeight()) * 0.5;

		double t = 1;
		int MAX_ITER = 100;
		Vec3 originalVel = this.getDeltaMovement();
		Vec3 accel = this.getForces(pos, originalVel);
		Vec3 trajectory = originalVel.add(accel);
		double velMag = trajectory.length();
		double vmRecip = 1 / velMag;

		boolean shouldRemove = false;
		boolean stop = false;


		for (int p = 0; p < MAX_ITER; ++p) {
			if (velMag * t < 1e-2d) {
				this.lastPenetratedBlock = Blocks.AIR.defaultBlockState();
				break;
			}

			Vec3 currentEnd = currentStart.add(trajectory.scale(t));
			BlockHitResult blockResult = this.level().clip(new ClipContext(currentStart, currentEnd, ClipContext.Block.COLLIDER,
				ClipContext.Fluid.NONE, this));
			if (blockResult.getType() != HitResult.Type.MISS)
				currentEnd = blockResult.getLocation();
			if (p == 0) {
				BlockHitResult fluidResult = this.level().clip(new ClipContext(currentStart, currentEnd, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, this));
				if (fluidResult.getType() != HitResult.Type.MISS) {
					BlockPos fluidPos = fluidResult.getBlockPos();
					BlockState state = this.level().getBlockState(fluidPos);
					if (state.getBlock() instanceof LiquidBlock) {
						if (this.inFluidTime <= 0) {
							stop = this.onImpactFluid(projCtx, state, this.level().getFluidState(fluidPos), fluidResult.getLocation(), fluidResult);
							if (stop)
								currentEnd = blockResult.getLocation();
						}
						this.inFluidTime = 2;
					}
				}
			}

			if (this.onClip(projCtx, currentStart, currentEnd)) {
				shouldRemove = true;
				break;
			}

			Vec3 disp = currentEnd.subtract(currentStart);

			AABB box = new AABB(this.toGlobalVector(firstPos.getCenter(),0),this.toGlobalVector(firstPos.getCenter(),0)).inflate(0.25);


			AABB currentMovementRegion = box.expandTowards(disp).inflate(1).move(currentStart.subtract(pos));

			Vec3 endStart = currentStart;
			Vec3 endCopy = currentEnd;
			for (Entity target : this.level().getEntities(this, currentMovementRegion)) {
				if (projCtx.hasHitEntity(target) || !this.canHitEntity(target))
					continue;
				AABB targetBB = target.getBoundingBox();
				if (targetBB.intersects(box) || targetBB.inflate(reach).clip(endStart, endCopy).isPresent())
					projCtx.addEntity(target);
			}
			if (stop)
				break;

			currentStart = currentEnd;
			t -= disp.length() * vmRecip;
			if (blockResult.getType() != HitResult.Type.MISS) {
				BlockPos bpos = blockResult.getBlockPos().immutable();
				BlockState state = this.level().getChunkAt(bpos).getBlockState(bpos);

				AbstractCannonProjectile.ImpactResult result = this.calculateBlockPenetration(projCtx, state, blockResult);
				switch (result.kinematics()) {
					case PENETRATE -> {
						this.lastPenetratedBlock = state;
						this.penetrationTime = 2;
					}
					case BOUNCE -> {
						this.setContraptionMotion(trajectory.scale(1 - t));
						Vec3 normal = CBCUtils.getSurfaceNormalVector(this.level(), blockResult);
						double elasticity = 1.7f;
						this.nextVelocity = trajectory.subtract(normal.scale(normal.dot(trajectory) * elasticity));
						this.setLocalSoundCooldown(3);
						stop = true;
					}
					case STOP -> {
						this.setContraptionMotion(trajectory.scale(1 - t));
						this.nextVelocity = Vec3.ZERO;
						this.lastPenetratedBlock = state;
						this.penetrationTime = 2;
						this.setLocalSoundCooldown(3);
						stop = true;

					}
				}
				shouldRemove |= result.shouldRemove();
			}
			if (shouldRemove || stop || t <= 0)
				break;
		}

		for (Entity e : projCtx.hitEntities())
			shouldRemove |= this.onHitEntity(e, projCtx);

		if (!this.level().isClientSide) {
			if (projCtx.griefState() != CBCCfgMunitions.GriefState.NO_DAMAGE) {
				Vec3 oldVel = this.getDeltaMovement();
				for (Map.Entry<BlockPos, Float> queued : projCtx.getQueuedExplosions().entrySet()) {
					Vec3 impactPos = Vec3.atCenterOf(queued.getKey());
					ImpactExplosion explosion = new ImpactExplosion(this.level(), this, this.getEntityDamage(),
						impactPos.x, impactPos.y, impactPos.z, queued.getValue(), Level.ExplosionInteraction.BLOCK);
					CreateBigCannons.handleCustomExplosion(this.level(), explosion);
				}
				this.setContraptionMotion(oldVel);
			}
			for (ClientboundPlayBlockHitEffectPacket pkt : projCtx.getPlayedEffects())
				NetworkPlatform.sendToClientTracking(pkt, this);
		}
		if (!this.level().isClientSide || !stop)
			this.orientation = trajectory;
			Vec3 vel = this.getOrientation();
			Vec3 angleVec = vel.normalize();
			this.setXRot(pitchFromVector(angleVec));
			this.setYRot(yawFromVector(angleVec));

		if (shouldRemove) {
			changed.add(firstPos);
			blockMass.remove(firstPos);
		}
	}


	@Override
	public void setDeltaMovement(Vec3 motionIn) {
		setContraptionMotion(motionIn);
	}

	private boolean shouldFall() {
		return this.onGround() && this.level().noCollision(this.getBoundingBox());
	}

	public void updateKinematics(ClientboundPreciseMotionSyncPacket packet) {
		if (this.getDeltaMovement().lengthSqr() > 1e-4d) this.orientation = this.getDeltaMovement();
	}
	protected boolean onClip(MWProjectileContext ctx, Vec3 start, Vec3 end) {
		for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> entry : this.contraption.getBlocks().entrySet()) {
			AbstractCannonProjectile foundWarhead = null;
			BlockPos foundPos = null;
			List<StructureTemplate.StructureBlockInfo> warheads = new ArrayList<>();
			if (entry.getValue().state().getBlock() instanceof ProjectileBlock<?> pb) {
				warheads.add(entry.getValue());
				foundPos = entry.getKey();
				foundWarhead = pb.getProjectile(level(), warheads);
			}
			if (foundWarhead instanceof FuzedBigCannonProjectile fuzed) {
				boolean baseFuze = ((FuzedProjectileAccessor)fuzed).invokeGetFuzeProperties().baseFuze();
				fuzed.setPos(this.toGlobalVector(Vec3.atCenterOf(foundPos.relative(this.getInitialOrientation())), 0));
				fuzed.setDeltaMovement(this.getDeltaMovement());
				AABB box = new AABB(this.blockPosition());
				fuzed.setBoundingBox(box.move(0, -box.getYsize() * 0.5d, 0));
				ItemStack fuze = ((FuzedProjectileAccessor) fuzed).getFuze();
				ProjectileContext newCTX = new ProjectileContext(fuzed, CBCConfigs.server().munitions.damageRestriction.get());
				for (Entity e : ctx.hitEntities()) {
					newCTX.addEntity(e);
				}
				if (((FuzedProjectileAccessor)fuzed).invokeCanDetonate(fz -> fz.onProjectileClip(fuze, fuzed, start, end, newCTX, baseFuze))) {
					this.detonate(foundPos, fuzed);
					fuzed.discard();
					changed.add(foundPos);
					if (foundPos == firstWarheadPos && firstWarhead != null) {
						firstWarhead.discard();
						firstWarhead = null;
						firstWarheadPos = null;
					}
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	protected Vec3 getForces(Vec3 position, Vec3 velocity) {
		return velocity.normalize().scale(-this.getDragForce())
			.add(0.0d, this.getGravity(), 0.0d);
	}

	protected Vec3 getThrust(Vec3 position, Vec3 velocity) {
		return velocity.normalize().scale(this.getThrustForce()).add(0.0d, this.getGravity(), 0.0d);
	}


	protected double getGravity() {
		return this.isNoGravity() ? 0 : this.entityData.get(GRAVITY) * DimensionMunitionPropertiesHandler
			.getProperties(this.level()).gravityMultiplier();
	}

	protected double getDragForce() {
		double vel = this.getDeltaMovement().length();
		double formDrag = this.getAllProperties().drag();
		double density = DimensionMunitionPropertiesHandler.getProperties(this.level()).dragMultiplier();
		FluidState fluidState = this.level().getFluidState(this.blockPosition());
		if (!fluidState.isEmpty())
			density += FluidDragHandler.getFluidDrag(fluidState);
		double drag = formDrag * density * vel;
		if (this.getAllProperties().isQuadraticDrag())
			drag *= vel;
		return Math.min(drag, vel);
	}


	protected double getThrustForce() {
		double vel = this.getDeltaMovement().length();
		double formThrust = this.entityData.get(THRUST);
		double density = DimensionMunitionPropertiesHandler.getProperties(this.level()).dragMultiplier();
		FluidState fluidState = this.level().getFluidState(this.blockPosition());
		if (!fluidState.isEmpty())
			density += FluidDragHandler.getFluidDrag(fluidState);
		double scalingFactor = Math.max(0.1, 1.0 / (1.0 + vel * vel));
		double thrustForce = formThrust * vel * density * scalingFactor;
		if (this.getAllProperties().isQuadraticDrag())
			thrustForce *= vel;
		return thrustForce - this.getDragForce();
	}

	protected boolean onImpactFluid(MWProjectileContext projectileContext, BlockState blockState, FluidState fluidState,
									Vec3 impactPos, BlockHitResult fluidHitResult) {
		Vec3 pos = this.position();
		Vec3 accel = this.getForces(this.position(), this.getDeltaMovement());
		Vec3 curVel = this.getDeltaMovement().add(accel);

		Vec3 normal = CBCUtils.getSurfaceNormalVector(this.level(), fluidHitResult);
		double incidence = Math.max(0, curVel.normalize().dot(normal.reverse()));
		double velMag = curVel.length();
		double mass = 0;
		if (blockMass.containsKey(firstPos)) {
			mass = blockMass.get(firstPos);
		}
		double projectileDeflection = 0.7;
		double incidentVel = velMag * incidence;
		double momentum = mass * incidentVel;
		double fluidDensity = FluidDragHandler.getFluidDrag(fluidState);

		boolean canBounce = CBCConfigs.server().munitions.projectilesCanBounce.get();
		double baseChance = CBCConfigs.server().munitions.baseProjectileFluidBounceChance.getF();
		boolean criticalAngle = projectileDeflection > 1e-2d && incidence <= projectileDeflection;
		boolean buoyant = fluidDensity > 1e-2d && momentum < fluidDensity;

		double incidenceFactor = criticalAngle ? Math.max(0, 1 - incidence / projectileDeflection) : 0;
		double massFactor = buoyant ? 0 : Math.max(0, 1 - momentum / fluidDensity);
		double chance = Math.max(baseChance, incidenceFactor * massFactor);

		boolean bounced = canBounce && criticalAngle && buoyant && this.level().getRandom().nextDouble() < chance;
		if (bounced) {
			this.setContraptionMotion(fluidHitResult.getLocation().subtract(pos));
			double elasticity = 1.7f;
			this.nextVelocity = curVel.subtract(normal.scale(normal.dot(curVel) * elasticity));
		}
		if (!this.level().isClientSide) {
			Vec3 effectNormal = bounced ? normal.scale(incidentVel) : curVel.reverse();
			Vec3 fluidExplosionPos = fluidHitResult.getLocation();
			projectileContext.addPlayedEffect(new ClientboundPlayBlockHitEffectPacket(blockState, this.getType(), bounced, true,
				fluidExplosionPos.x, fluidExplosionPos.y, fluidExplosionPos.z, (float) effectNormal.x,
				(float) effectNormal.y, (float) effectNormal.z));
		}
		return bounced;
	}

	protected AbstractCannonProjectile.ImpactResult calculateBlockPenetration(MWProjectileContext projectileContext, BlockState state, BlockHitResult blockHitResult) {
		BlockPos pos = blockHitResult.getBlockPos();
		Vec3 hitLoc = blockHitResult.getLocation();

		BallisticPropertiesComponent ballistics = null;
		double mass = 0;
		if (this.contraption.getBlocks().isEmpty()) {
			this.discard();
			return new AbstractCannonProjectile.ImpactResult(AbstractCannonProjectile.ImpactResult.KinematicOutcome.STOP, true);
		}
		if (firstWarhead != null && firstWarheadPos == firstPos) {
			ballistics = ((AbstractProjectleAccessor)firstWarhead).invokeGetBallisticProperties();
		} else if (this.contraption.getBlocks().get(firstPos).state().getBlock() instanceof IFuelTankBlock fuel) {;
			ballistics = fuel.getProperties().ballisticPropertiesComponent();
		} else if (this.contraption.getBlocks().get(firstPos).state().getBlock() instanceof IThrusterBlock thruster) {
			ballistics = thruster.getBallistics();
		} else if (this.contraption.getBlocks().get(firstPos).state().getBlock() instanceof IGuidanceBlock guidance) {
			ballistics = guidance.getBallistics();
		}

		if (blockMass.containsKey(firstPos)) {
			mass = blockMass.get(firstPos);
		}
		if (ballistics == null) {
			this.discard();
			return new AbstractCannonProjectile.ImpactResult(AbstractCannonProjectile.ImpactResult.KinematicOutcome.STOP, true);
		}
		BlockArmorPropertiesProvider blockArmor = BlockArmorPropertiesHandler.getProperties(state);
		boolean unbreakable = projectileContext.griefState() == CBCCfgMunitions.GriefState.NO_DAMAGE || state.getDestroySpeed(this.level(), pos) == -1;

		Vec3 accel = this.getForces(this.position(), this.getDeltaMovement());
		Vec3 curVel = this.getDeltaMovement().add(accel);

		Vec3 normal = CBCUtils.getSurfaceNormalVector(this.level(), blockHitResult);
		double incidence = Math.max(0, curVel.normalize().dot(normal.reverse()));
		double velMag = curVel.length();
		double bonusMomentum = 1 + Math.max(0, (velMag - CBCConfigs.server().munitions.minVelocityForPenetrationBonus.getF())
			* CBCConfigs.server().munitions.penetrationBonusScale.getF());
		double incidentVel = velMag * incidence;
		double momentum = mass * incidentVel * bonusMomentum;

		double toughness = blockArmor.toughness(this.level(), state, pos, true);
		double toughnessPenalty = toughness - momentum;
		double hardnessPenalty = blockArmor.hardness(this.level(), state, pos, true) - ballistics.penetration();

		double projectileDeflection = ballistics.deflection();
		double baseChance = CBCConfigs.server().munitions.baseProjectileBounceChance.getF();
		double bounceChance = projectileDeflection < 1e-2d || incidence > projectileDeflection ? 0 : Math.max(baseChance, 1 - incidence / projectileDeflection);

		boolean surfaceImpact = this.canHitSurface();
		boolean canBounce = CBCConfigs.server().munitions.projectilesCanBounce.get();
		boolean blockBroken = toughnessPenalty < 1e-2d && !unbreakable;
		AbstractCannonProjectile.ImpactResult.KinematicOutcome outcome;
		if (surfaceImpact && canBounce && this.level().getRandom().nextDouble() < bounceChance) {
			outcome = AbstractCannonProjectile.ImpactResult.KinematicOutcome.BOUNCE;
		} else if (blockBroken && !this.level().isClientSide) {
			outcome = AbstractCannonProjectile.ImpactResult.KinematicOutcome.PENETRATE;
		} else {
			outcome = AbstractCannonProjectile.ImpactResult.KinematicOutcome.STOP;
		}
		boolean shatter = surfaceImpact && outcome != AbstractCannonProjectile.ImpactResult.KinematicOutcome.BOUNCE && hardnessPenalty > ballistics.toughness();
		float durabilityPenalty = ((float) Math.max(0, hardnessPenalty) + 1) * (float) toughness / (float) incidentVel;

		if (firstWarheadPos == firstPos) {
			state.onProjectileHit(this.level(), state, blockHitResult, firstWarhead);
		} else {
			state.onProjectileHit(this.level(), state, blockHitResult, new SolidShotProjectile(CBCEntityTypes.SHOT.get(),this.level()));
		}
		if (!this.level().isClientSide) {
			boolean bounced = outcome == AbstractCannonProjectile.ImpactResult.KinematicOutcome.BOUNCE;
			Vec3 effectNormal;
			if (bounced) {
				double elasticity = 1.7f;
				effectNormal = curVel.subtract(normal.scale(normal.dot(curVel) * elasticity));
			} else {
				effectNormal = curVel.reverse();
			}
			for (BlockState state1 : blockArmor.containedBlockStates(this.level(), state, pos.immutable(), true)) {
				projectileContext.addPlayedEffect(new ClientboundPlayBlockHitEffectPacket(state1, this.getType(), bounced, true,
					hitLoc.x, hitLoc.y, hitLoc.z, (float) effectNormal.x, (float) effectNormal.y, (float) effectNormal.z));
			}
		}
		if (blockBroken) {
			this.blockMass.put(firstPos, (incidentVel < 1e-4d ? 0 : Math.max(this.blockMass.get(firstPos) - durabilityPenalty, 0)));
			this.level().setBlock(pos, Blocks.AIR.defaultBlockState(), ProjectileBlock.UPDATE_ALL_IMMEDIATE);
			if (surfaceImpact) {
				float f = (float) toughness / (float) momentum;
				float overPenetrationPower = f < 0.15f ? 2 - 2 * f : 0;
				if (overPenetrationPower > 0 && outcome == AbstractCannonProjectile.ImpactResult.KinematicOutcome.PENETRATE)
					projectileContext.queueExplosion(pos, overPenetrationPower);
			}
		} else {
			if (outcome == AbstractCannonProjectile.ImpactResult.KinematicOutcome.STOP) {
				this.blockMass.put(firstPos, 0f);
			} else {
				if (this.blockMass.containsKey(firstPos)) {
					this.blockMass.put(firstPos, (incidentVel < 1e-4d ? 0 : Math.max(this.blockMass.get(firstPos) - durabilityPenalty / 2f, 0)));
				}
			}
			Vec3 spallLoc = hitLoc.add(curVel.normalize().scale(2));
			if (!this.level().isClientSide) {
				ImpactExplosion explosion = new ImpactExplosion(this.level(), this, this.indirectArtilleryFire(false), spallLoc.x, spallLoc.y, spallLoc.z, 2, Level.ExplosionInteraction.NONE);
				CreateBigCannons.handleCustomExplosion(this.level(), explosion);
			}
			SoundType sound = state.getSoundType();
			if (!this.level().isClientSide)
				this.level().playSound(null, spallLoc.x, spallLoc.y, spallLoc.z, sound.getBreakSound(), SoundSource.BLOCKS,
					sound.getVolume(), sound.getPitch());
		}
		shatter |= this.onImpact(blockHitResult, new AbstractCannonProjectile.ImpactResult(outcome, shatter), projectileContext);
		return new AbstractCannonProjectile.ImpactResult(outcome, shatter);
	}


	public DamageSource indirectArtilleryFire(boolean bypassArmor) {
		return new CannonDamageSource(CannonDamageSource.getDamageRegistry(this.level()).getHolderOrThrow(CBCDamageTypes.CANNON_PROJECTILE), bypassArmor);
	}

	protected boolean canHitSurface() {
		return this.lastPenetratedBlock.isAir() && this.penetrationTime == 0;
	}

	protected boolean onImpact(HitResult hitResult, AbstractCannonProjectile.ImpactResult impactResult, MWProjectileContext projectileContext) {
		if (firstWarheadPos == firstPos && firstWarhead != null && firstWarhead instanceof FuzedBigCannonProjectile fuzedWarhead) {
			fuzedWarhead.setPos(this.toGlobalVector(Vec3.atCenterOf(firstWarheadPos.relative(this.getInitialOrientation())), 1.0f));
			fuzedWarhead.setDeltaMovement(this.getOrientation());
			boolean baseFuze = ((FuzedProjectileAccessor)fuzedWarhead).invokeGetFuzeProperties().baseFuze();
			if (((FuzedProjectileAccessor)fuzedWarhead).invokeCanDetonate(fz -> fz.onProjectileImpact(((FuzedProjectileAccessor) fuzedWarhead).getFuze(), fuzedWarhead, hitResult, impactResult, baseFuze))) {
				this.detonate(firstWarheadPos, fuzedWarhead);
				firstWarhead.discard();
				firstWarhead = null;
				changed.add(firstWarheadPos);
				firstWarheadPos = null;
				return true;
			}
		}
		return blockMass.containsKey(firstPos) && blockMass.get(firstPos) <= 0;
	}
	protected boolean onHitEntity(Entity entity, MWProjectileContext projectileContext) {
		if (!this.level().isClientSide) {
			if (firstWarhead != null && firstWarheadPos == firstPos) {
				ProjectileContext newCTX = new ProjectileContext(firstWarhead, CBCConfigs.server().munitions.damageRestriction.get());
				for (Entity e : projectileContext.hitEntities()) {
					newCTX.addEntity(e);
				}
				((AbstractBigCannonAccessor)firstWarhead).invokeImpact(new EntityHitResult(entity), new AbstractCannonProjectile.ImpactResult(AbstractCannonProjectile.ImpactResult.KinematicOutcome.PENETRATE, firstWarhead.getProjectileMass() <= 0), newCTX);
				EntityDamagePropertiesComponent properties = firstWarhead.getDamageProperties();
				entity.setDeltaMovement(this.getDeltaMovement().scale(properties.knockback()));
				DamageSource source = this.getEntityDamage();

				if (properties == null || properties.ignoresInvulnerability()) entity.invulnerableTime = 0;
				entity.hurt(source, firstWarhead.getDamageProperties().entityDamage());
				if (properties == null || !properties.rendersInvulnerable()) entity.invulnerableTime = 0;

				float penalty = entity.isAlive() ? 2f : 0.2f;
				blockMass.put(firstPos,Math.max(blockMass.get(firstPos) - penalty, 0));
			} else if (!this.contraption.getBlocks().isEmpty() && this.contraption.getBlocks().get(firstPos).state().getBlock() instanceof PropelledContraptionMunitionBlock propelled) {
				EntityDamagePropertiesComponent properties = null;
				if (propelled instanceof IFuelTankBlock fuel) {
					properties = fuel.getProperties().entityDamagePropertiesComponent();
				} else if (propelled instanceof IThrusterBlock thruster) {
					properties = thruster.getDamage();
				} else if (propelled instanceof IGuidanceBlock guidance) {
					properties = guidance.getDamage();
				}
				if (properties != null) entity.setDeltaMovement(this.getDeltaMovement().scale((properties.knockback())));
				DamageSource source = this.getEntityDamage();

				if (properties == null || properties.ignoresInvulnerability()) entity.invulnerableTime = 0;
				entity.hurt(source, properties.entityDamage());
				if (properties == null || !properties.rendersInvulnerable()) entity.invulnerableTime = 0;

				float penalty = entity.isAlive() ? 2f : 0.2f;
				blockMass.put(firstPos,Math.max(blockMass.get(firstPos) - penalty, 0));
			}
		}
		return this.onImpact(new EntityHitResult(entity), new AbstractCannonProjectile.ImpactResult(AbstractCannonProjectile.ImpactResult.KinematicOutcome.PENETRATE, false), projectileContext);
	}

	public boolean canHitEntity(Entity entity) {
		if (!entity.canBeHitByProjectile())
			return false;
		if (entity instanceof Projectile)
			return false; // TODO better detection for interception?
		return !this.untouchableEntities.containsKey(entity);
	}

	protected void detonate(BlockPos pos, FuzedBigCannonProjectile fuzed){
		BlockPos oldPos = this.blockPosition();
		Vec3 oldDelta = this.getDeltaMovement();
		fuzed.setDeltaMovement(oldDelta);
		((FuzedProjectileAccessor) fuzed).invokeDetonate((this.toGlobalVector(Vec3.atCenterOf(pos.relative(this.getInitialOrientation())), 0)));
		this.setPos(oldPos.getX(),oldPos.getY(),oldPos.getZ());
		this.setContraptionMotion(oldDelta.scale(0.75));
	}

	protected void tickWarheads(){
		if (this.getContraption() == null) this.discard();
		if (firstWarhead != null) {
			firstWarhead.setPos(this.toGlobalVector(firstWarheadPos.getCenter(),0));
			firstWarhead.setDeltaMovement(this.getDeltaMovement());
		}
		if (this.contraption != null) {
			for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> entry : this.contraption.getBlocks().entrySet()) {
				AbstractCannonProjectile foundWarhead = null;
				BlockPos foundPos = null;
				List<StructureTemplate.StructureBlockInfo> warheads = new ArrayList<>();
				if (entry.getValue().state().getBlock() instanceof ProjectileBlock<?> pb) {
					warheads.add(entry.getValue());
					foundPos = entry.getKey();
					foundWarhead = pb.getProjectile(level(), warheads);
				}
				if (foundWarhead instanceof FuzedBigCannonProjectile fuzed) {
					fuzed.setPos(this.toGlobalVector(Vec3.atCenterOf(foundPos.relative(this.getInitialOrientation())), 0));
					fuzed.setDeltaMovement(this.getDeltaMovement());
					AABB box = new AABB(this.blockPosition());
					fuzed.setBoundingBox(box.move(0, -box.getYsize() * 0.5d, 0));
					if (((FuzedProjectileAccessor)fuzed).invokeCanDetonate(fz -> fz.onProjectileTick(((FuzedProjectileAccessor) fuzed).getFuze(), fuzed))) {
						this.detonate(foundPos, fuzed);
						fuzed.discard();
						changed.add(foundPos);
					}
				}
			}
		}
	}

	protected DamageSource getEntityDamage() {
		boolean bypassesArmor = false;
		AbstractCannonProjectile foundWarhead = null;
		for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> entry : this.contraption.getBlocks().entrySet()) {
			List<StructureTemplate.StructureBlockInfo> warheads = new ArrayList<>();
			if (entry.getValue().state().getBlock() instanceof ProjectileBlock<?> pb) {
				warheads.add(entry.getValue());
				foundWarhead = pb.getProjectile(level(), warheads);
			}
		}
		if (foundWarhead != null) 	bypassesArmor = foundWarhead.getDamageProperties().ignoresEntityArmor();
		return new CannonDamageSource(CannonDamageSource.getDamageRegistry(this.level()).getHolderOrThrow(CBCDamageTypes.CANNON_PROJECTILE), bypassesArmor);
	}

	@Override
	protected void readAdditional(CompoundTag tag, boolean spawnPacket) {
		super.readAdditional(tag, spawnPacket);
		this.entityData.set(FUEL, tag.getInt("Fuel"));
		ListTag motionTag = tag.getList("Motion", Tag.TAG_DOUBLE);
		this.setContraptionMotion(new Vec3(motionTag.getDouble(0), motionTag.getDouble(1), motionTag.getDouble(2)));

		if (tag.contains("NextMotion", Tag.TAG_LIST)) {
			ListTag nextMotion = tag.getList("NextMotion", Tag.TAG_DOUBLE);
			this.nextVelocity = nextMotion.size() == 3 ? new Vec3(nextMotion.getDouble(0), nextMotion.getDouble(1), nextMotion.getDouble(2)) : null;
		} else {
			this.nextVelocity = null;
		}

		if (tag.contains("Orientation", Tag.TAG_LIST)) {
			ListTag nextMotion = tag.getList("Orientation", Tag.TAG_DOUBLE);
			this.orientation = nextMotion.size() == 3 ? new Vec3(nextMotion.getDouble(0), nextMotion.getDouble(1), nextMotion.getDouble(2)) : null;
		} else {
			this.orientation = this.getDeltaMovement();
		}

		this.lastPenetratedBlock = tag.contains("LastPenetration", Tag.TAG_COMPOUND)
			? NbtUtils.readBlockState(this.level().holderLookup(CBCRegistryUtils.getBlockRegistryKey()), tag.getCompound("LastPenetration"))
			: Blocks.AIR.defaultBlockState();

		if (tag.contains("BlockMasses", Tag.TAG_LIST)) {
			ListTag blockMassList = tag.getList("BlockMasses", Tag.TAG_COMPOUND);
			for (int i = 0; i < blockMassList.size(); i++) {
				CompoundTag blockData = blockMassList.getCompound(i);
				BlockPos pos = new BlockPos(blockData.getInt("X"), blockData.getInt("Y"), blockData.getInt("Z"));
				float mass = blockData.getFloat("Mass");
				blockMass.put(pos, mass);  // Populate the map
			}
		}
	}



	@Override
	protected void writeAdditional(CompoundTag tag, boolean spawnPacket) {
		super.writeAdditional(tag,spawnPacket);
		tag.putFloat("Fuel", this.entityData.get(FUEL));
		if (this.nextVelocity != null)
			tag.put("NextMotion", this.newDoubleList(this.nextVelocity.x, this.nextVelocity.y, this.nextVelocity.z));
		if (this.orientation != null)
			tag.put("Orientation", this.newDoubleList(this.orientation.x, this.orientation.y, this.orientation.z));
		tag.put("LastPenetration", NbtUtils.writeBlockState(this.lastPenetratedBlock));
		if (!this.blockMass.isEmpty()) {
			ListTag blockMassesTag = new ListTag();
			for (Map.Entry<BlockPos, Float> entry : this.blockMass.entrySet()) {
				CompoundTag compoundTag = new CompoundTag();
				compoundTag.putInt("X", entry.getKey().getX());
				compoundTag.putInt("Y", entry.getKey().getY());
				compoundTag.putInt("Z", entry.getKey().getZ());
				compoundTag.putFloat("Mass", entry.getValue());
				blockMassesTag.add(compoundTag);  // Add each compound to the ListTag
			}
			tag.put("BlockMasses", blockMassesTag);  // Put the ListTag into the compound tag
		}
	}
	public void setLocalSoundCooldown(int value) { this.localSoundCooldown = value; }
	public int getLocalSoundCooldown() { return this.localSoundCooldown; }

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(GRAVITY, (float) 0);
		this.entityData.define(FUEL, 0);
		this.entityData.define(BURN_RATE, (float) 0);
		this.entityData.define(THRUST, (float) 0);
	}



	public Vec3 getOrientation() {
		return this.orientation == null ? this.getDeltaMovement() : this.orientation;
	}

	public AABB setContraptionBounds() {
		if (!this.contraption.getBlocks().isEmpty()) {
			BlockPos tempFirst = null;
			BlockPos boundsEnd = null;
			for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> s : this.contraption.getBlocks().entrySet()) {
				tempFirst = s.getKey();
			}
			BlockPos firstBounds = tempFirst;
			for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> s : this.contraption.getBlocks().entrySet()) {
				boundsEnd = s.getKey();
				break;
			}
			if (this.firstPos == firstBounds && this.endPos == boundsEnd) return this.contraption.bounds;
			if (firstBounds == null || boundsEnd == null) return this.contraption.bounds;
			if (contraption instanceof MunitionsPhysicsContraption mpc) {
				contraption.bounds = mpc.createBoundsFromPositions(firstBounds.getCenter(), boundsEnd.getCenter()).inflate(0.25);
				return mpc.createBoundsFromPositions(firstBounds.getCenter(), boundsEnd.getCenter()).inflate(0.25);

			}
		}
		return this.getBoundingBox();
	}

	public void setData() {
		float gravity = 0;
		int fuel = 0;
		float rate = 0;
		float thrust = 0;
		for(Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> entry : this.contraption.getBlocks().entrySet()) {
			if (entry.getValue().state().getBlock() instanceof ProjectileBlock<?> pb) {
				AbstractCannonProjectile foundWarhead = null;
				List<StructureTemplate.StructureBlockInfo> warheads = new ArrayList<>();
				warheads.add(entry.getValue());
				foundWarhead = pb.getProjectile(level(), warheads);
				blockMass.put(entry.getKey(), foundWarhead.getProjectileMass());
				gravity += Math.abs(((AbstractProjectleAccessor) foundWarhead).invokeGetBallisticProperties().gravity());
			} else if (entry.getValue().state().getBlock() instanceof IFuelTankBlock tank) {
				blockMass.put(entry.getKey(), tank.getProperties().ballisticPropertiesComponent().durabilityMass());
				gravity += tank.getProperties().fuelTankProperties().addedGravity();
				fuel += tank.getProperties().fuelTankProperties().storedFuel();
			} else if (entry.getValue().state().getBlock() instanceof IThrusterBlock thruster) {
				blockMass.put(entry.getKey(), thruster.getBallistics().durabilityMass());
				gravity += thruster.addedGravity();
				fuel += thruster.storedFuel();
				rate += thruster.burnRate();
				thrust += thruster.thrust();
			} else if (entry.getValue().state().getBlock() instanceof IGuidanceBlock guidance) {
				blockMass.put(entry.getKey(), guidance.getBallistics().durabilityMass());
				gravity += guidance.addedGravity();
			}
		}
		this.entityData.set(FUEL, fuel * 20);
		this.entityData.set(BURN_RATE, rate);
		this.entityData.set(THRUST, thrust);
		this.entityData.set(GRAVITY, Math.min(gravity, -0.02f));
	}
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		Vec3 vec3 = (new Vec3(x, y, z)).normalize().add(this.random.nextGaussian() * 0.007499999832361937 * (double)inaccuracy, this.random.nextGaussian() * 0.007499999832361937 * (double)inaccuracy, this.random.nextGaussian() * 0.007499999832361937 * (double)inaccuracy).scale((double)velocity);
		setContraptionMotion(vec3);
		double d = vec3.horizontalDistance();
		this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 57.2957763671875));
		this.setXRot((float)(Mth.atan2(vec3.y, d) * 57.2957763671875));
		this.xRotO = this.getYRot();
		this.xRotO = this.getXRot();
	}

	protected static float lerpRotation(float currentRotation, float targetRotation) {
		while(targetRotation - currentRotation < -180.0F) {
			currentRotation -= 360.0F;
		}

		while(targetRotation - currentRotation >= 180.0F) {
			currentRotation += 360.0F;
		}

		return Mth.lerp(0.2F, currentRotation, targetRotation);
	}

	public void addUntouchableEntity(Entity entity, int duration) {
		if (entity.isRemoved())
			return;
		if (duration < 1)
			throw new IllegalArgumentException("Use #addAlwaysUntouchableEntity when duration < 1 (was " + duration + ")");
		this.untouchableEntities.put(entity, duration);
	}

	public void addAlwaysUntouchableEntity(Entity entity) { this.untouchableEntities.put(entity, -1); }
	public void removeUntouchableEntity(Entity entity) { this.untouchableEntities.remove(entity); }


	public void lerpMotion(double x, double y, double z) {
		this.setContraptionMotion(new Vec3(x, y, z));
		if (this.xRotO == 0.0F && this.xRotO == 0.0F) {
			double d = Math.sqrt(x * x + z * z);
			this.setXRot((float)(Mth.atan2(y, d) * 57.2957763671875));
			this.setYRot((float)(Mth.atan2(x, z) * 57.2957763671875));
			this.xRotO = this.getXRot();
			this.xRotO = this.getYRot();
			this.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
		}

	}
	protected void updateBlocks() {
		for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> s : this.contraption.getBlocks().entrySet()) {
			firstPos = s.getKey();
		}
		for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> s : this.contraption.getBlocks().entrySet()) {
			endPos = s.getKey();
			break;
		}
		for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> s : this.contraption.getBlocks().entrySet()) {
			List<StructureTemplate.StructureBlockInfo> warheads = new ArrayList<>();
			if (firstWarhead == null && s.getKey() == firstPos && this.contraption.getBlocks().get(s.getKey()).state().getBlock() instanceof ProjectileBlock<?> block) {
				warheads.add(s.getValue());
				firstWarhead = block.getProjectile(level(), warheads);
				firstWarheadPos = s.getKey();
			} else if (this.contraption.getBlocks().get(s.getKey()).state().getBlock() instanceof MunitionsLauncherThrusterBlock thruster) {
				thruster.spawnParticles(s.getKey(), this);
			}
		}
		if (!changed.isEmpty() && contraption instanceof MunitionsPhysicsContraption mpc) {
			this.contraption.bounds = mpc.createBoundsFromPositions(firstPos.getCenter(), endPos.getCenter()).inflate(0.25);
			for (BlockPos b : changed) {
				if (b == firstWarheadPos && firstWarhead != null) {
					firstWarhead.discard();
					firstWarhead = null;
					firstWarheadPos = null;
				}
				StructureTemplate.StructureBlockInfo temp = this.contraption.getBlocks().get(b);
				StructureTemplate.StructureBlockInfo air = new StructureTemplate.StructureBlockInfo(temp.pos(), Blocks.AIR.defaultBlockState(), null);
				this.contraption.getBlocks().put(b, air);
				if (!this.level().isClientSide) {
					NetworkPlatform.sendToClientTracking(new ClientboundUpdateContraptionPacket(this, b, air), this);
				}
				this.contraption.getBlocks().remove(b);
				blockMass.remove(b);
			}
			changed.clear();
			for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> s : this.contraption.getBlocks().entrySet()) {
				firstPos = s.getKey();
			}
		}
		if (this.contraption.getBlocks().isEmpty()) this.discard();
	}

	public boolean canLingerInGround() {
		boolean checkFuze = false;
		if (firstWarhead != null && firstWarhead instanceof FuzedBigCannonProjectile fuzedWarhead) {
			checkFuze = ((FuzedProjectileAccessor) fuzedWarhead).getFuze().getItem() instanceof FuzeItem fuzeItem && fuzeItem.canLingerInGround(((FuzedProjectileAccessor)fuzedWarhead).getFuze(), fuzedWarhead);
		}
		return !this.level().isClientSide && this.level().hasChunkAt(this.blockPosition()) && checkFuze;
	}

	protected MunitionsContraptionEntityProperties getAllProperties() {
		return CBCModernWarfareMunitionPropertiesHandlers.MUNITIONS_CONTRAPTION.getPropertiesOf(this);
	}

	public float getFuel() {
		return this.entityData.get(FUEL);
	}

	@Override
	public Vec3 getLightProbePosition(float partialTicks) {
		Vec3 eyePos = super.getLightProbePosition(partialTicks);
		return this.onGround() && this.orientation != null ? eyePos.subtract(this.orientation.normalize().scale(0.1)) : eyePos;
	}

	@Override
	public Vec3 applyRotation(Vec3 localPos, float partialTicks) {
		Vec3 direction = this.getOrientation().normalize();
		float initialYaw = this.getInitialOrientation().toYRot();
		float yaw = (float) Math.toDegrees(Math.atan2(-direction.x, -direction.z)) + initialYaw;
		float pitch = (float) Math.toDegrees(Math.asin(-direction.y));
		localPos = VecHelper.rotate(localPos, pitch, this.getInitialOrientation().getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X);
		localPos = VecHelper.rotate(localPos, yaw, Direction.Axis.Y);
		localPos = VecHelper.rotate(localPos, initialYaw, Direction.Axis.Y);
		return localPos;
	}

	@Override
	public Vec3 reverseRotation(Vec3 localPos, float partialTicks) {
		Vec3 direction = this.getOrientation().normalize();
		float initialYaw = this.getInitialOrientation().toYRot();
		float yaw = (float) Math.toDegrees(Math.atan2(-direction.x, -direction.z)) + initialYaw;
		float pitch = (float) Math.toDegrees(Math.asin(-direction.y));
		localPos = VecHelper.rotate(localPos, -initialYaw, Direction.Axis.Y);
		localPos = VecHelper.rotate(localPos, -yaw, Direction.Axis.Y);
		localPos = VecHelper.rotate(localPos, -pitch, this.getInitialOrientation().getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X);
		return localPos;
	}


}
