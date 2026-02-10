package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.propulsion;

import net.minecraft.client.particle.CampfireSmokeParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.effects.particles.plumes.BigCannonPlumeParticleData;
import rbasamoyai.createbigcannons.effects.particles.smoke.CannonSmokeParticleData;
import rbasamoyai.createbigcannons.effects.particles.smoke.DebrisSmokeParticleData;
import rbasamoyai.createbigcannons.effects.particles.smoke.ShellExplosionSmokeParticleData;
import rbasamoyai.createbigcannons.effects.particles.smoke.SmokeShellSmokeParticleData;
import rbasamoyai.createbigcannons.effects.particles.smoke.TrailSmokeParticleData;
import rbasamoyai.createbigcannons.effects.particles.splashes.ProjectileSplashParticle;
import rbasamoyai.createbigcannons.effects.particles.splashes.ProjectileSplashParticleData;
import rbasamoyai.createbigcannons.effects.particles.splashes.SplashSprayParticle;
import rbasamoyai.createbigcannons.effects.particles.splashes.SplashSprayParticleData;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionPropertiesHandlers;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraption;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.FuelType;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config.FuelThrusterProperties;

public class SolidFuelThruster extends MunitionsLauncherThrusterBlock {
	public SolidFuelThruster(Properties properties) {
		super(properties);
	}


	@Override
	public void spawnParticles(BlockPos localPos, MunitionsPhysicsContraptionEntity contraptionEntity) {
		ChunkPos cpos = new ChunkPos(contraptionEntity.blockPosition());
		Vec3 thrustVel = contraptionEntity.getDeltaMovement().reverse().scale(0.0125);


		Vec3 spawnPos = contraptionEntity.toGlobalVector(Vec3.atCenterOf(localPos), 0);

		Vec3 vel = contraptionEntity.getDeltaMovement();
		float scale = getVelocityBasedScale(vel);
		Vec3 oldPos = spawnPos.subtract(contraptionEntity.getDeltaMovement());

		Vec3 oldPlumePos = oldPos.subtract(vel);


		if (contraptionEntity.level().hasChunk(cpos.x, cpos.z)) {
			if (!contraptionEntity.onGround()) {
				AbstractBigCannonProjectile.TrailType trailType = CBCConfigs.server().munitions.bigCannonTrailType.get();
				int lifetime = trailType == AbstractBigCannonProjectile.TrailType.SHORT ? 20 : 40 + contraptionEntity.level().random.nextInt(50);
				if (trailType != AbstractBigCannonProjectile.TrailType.NONE) {
					if (contraptionEntity.getFuel() > 0) {
						ParticleOptions options = new ShellExplosionSmokeParticleData(lifetime,scale);
						ParticleOptions smoke = new DebrisSmokeParticleData(scale);
						for (int i = 0; i < 10; ++i) {
							double partial = i * 0.1f;
							double dx = Mth.lerp(partial, oldPos.x,  spawnPos.x);
							double dy = Mth.lerp(partial, oldPos.y, spawnPos.y);
							double dz = Mth.lerp(partial, oldPos.z,  spawnPos.z);
							contraptionEntity.level().addAlwaysVisibleParticle(options, true, dx, dy + 0.05, dz, thrustVel.x, thrustVel.y, thrustVel.z);
							double px = Mth.lerp(partial, oldPlumePos.x,  oldPos.x);
							double py = Mth.lerp(partial, oldPlumePos.y, oldPos.y);
							double pz = Mth.lerp(partial, oldPlumePos.z,  oldPos.z);
							double sx = contraptionEntity.level().random.nextDouble() * 0.004d - 0.002d;
							double sy = contraptionEntity.level().random.nextDouble() * 0.004d - 0.002d;
							double sz = contraptionEntity.level().random.nextDouble() * 0.004d - 0.002d;
							contraptionEntity.level().addAlwaysVisibleParticle(smoke, true, px, py + 0.05, pz, sx, sy, sz);
						}
					}
				}
			}
		}
	}

	@Override
	public BallisticPropertiesComponent getBallistics() {
		return this.getProperties().ballisticPropertiesComponent();
	}
	@Override
	public EntityDamagePropertiesComponent getDamage() {
		return this.getProperties().entityDamagePropertiesComponent();
	}
	@Override
	public float addedGravity() {
		return this.getProperties().thrusterProperties().addedGravity();
	}
	@Override
	public float addedSpread() {
		return this.getProperties().thrusterProperties().addedSpread();
	}
	@Override
	public int storedFuel() {
		return this.getProperties().thrusterProperties().storedFuel();
	}
	@Override
	public float burnRate(){
		return this.getProperties().thrusterProperties().burnRate();
	}
	@Override
	public float thrust(){
		return this.getProperties().thrusterProperties().thrust();
	}

	public FuelType getFuelType() {
		return FuelType.SOLID_FUEL;
	}

	public FuelThrusterProperties getProperties() {
		return CBCModernWarfareMunitionPropertiesHandlers.SOLID_FUEL_THRUSTER.getPropertiesOf(this);
	}
}
