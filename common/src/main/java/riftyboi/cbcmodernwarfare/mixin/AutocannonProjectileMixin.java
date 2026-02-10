package riftyboi.cbcmodernwarfare.mixin;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import net.minecraft.world.phys.Vec2;

import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import rbasamoyai.createbigcannons.munitions.AbstractCannonProjectile;
import rbasamoyai.createbigcannons.munitions.ProjectileContext;
import rbasamoyai.createbigcannons.munitions.autocannon.AbstractAutocannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.autocannon.AutocannonProjectileMixinInterface;

@Mixin(value = AbstractAutocannonProjectile.class)
public abstract class AutocannonProjectileMixin extends AbstractCannonProjectile implements AutocannonProjectileMixinInterface {

	private static final EntityDataAccessor<Byte> ID_FIRE = SynchedEntityData.defineId(AutocannonProjectileMixin.class, EntityDataSerializers.BYTE);
	protected AutocannonProjectileMixin(EntityType<? extends AbstractAutocannonProjectile> type, Level level) {
		super(type, level);
	}
	@Override
	public boolean isIncendiary() { return (this.entityData.get(ID_FIRE) & 3) != 0; }

	@Override
	public void setIncendiary(boolean incendiary) {
		if (incendiary) {
			this.entityData.set(ID_FIRE, (byte)(this.entityData.get(ID_FIRE) | 3));
		} else {
			this.entityData.set(ID_FIRE, (byte)(this.entityData.get(ID_FIRE) & 0b11111101));
		}
	}

	@Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
	public void saveDataInject(CompoundTag tag, CallbackInfo ci) {
		tag.putBoolean("Incendiary", this.isIncendiary());
	}
	@Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
	public void readDataInject(CompoundTag tag, CallbackInfo ci) {
		this.setIncendiary(tag.getBoolean("Incendiary"));
	}

	@Override
	protected boolean onImpact(HitResult hitResult, ImpactResult impactResult, ProjectileContext projectileContext) {
		if (!this.level().isClientSide && this.isIncendiary()) {
			level().explode(this, indirectArtilleryFire(this.getDamageProperties().ignoresEntityArmor()), null, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, 1, true, Level.ExplosionInteraction.NONE);
			this.setIncendiary(false);
			return true;
		}
		return false;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ID_FIRE, (byte) 0);
	}

	@Override
	protected boolean onHitEntity(Entity entity, ProjectileContext projectileContext) {
		super.onHitEntity(entity,projectileContext);
		if (!this.level().isClientSide && this.isIncendiary()) {
			level().explode(this, indirectArtilleryFire(this.getDamageProperties().ignoresEntityArmor()), null, entity.blockPosition().getX(), entity.blockPosition().getY(), entity.blockPosition().getZ(), 1, true, Level.ExplosionInteraction.NONE);
			entity.lavaHurt();
			this.setIncendiary(false);
		}
		return this.onImpact(new EntityHitResult(entity), new ImpactResult(ImpactResult.KinematicOutcome.PENETRATE, false), projectileContext);
	}
}
