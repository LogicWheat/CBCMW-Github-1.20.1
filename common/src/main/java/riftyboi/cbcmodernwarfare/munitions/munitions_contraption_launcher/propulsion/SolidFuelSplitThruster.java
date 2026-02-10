package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.propulsion;

import io.github.fabricators_of_create.porting_lib.common.util.Lazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.effects.particles.smoke.ShellExplosionSmokeParticleData;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionPropertiesHandlers;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraption;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.FuelType;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config.FuelThrusterProperties;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config.SplitFuelThrusterProperties;

public class SolidFuelSplitThruster extends MunitionsLauncherThrusterBlock  {
	public SolidFuelSplitThruster(Properties properties) {
		super(properties);
	}

	@Override
	public void spawnParticles(BlockPos localPos, MunitionsPhysicsContraptionEntity contraptionEntity) {
		ChunkPos cpos = new ChunkPos(contraptionEntity.blockPosition());
		Vec3 thrustVel = contraptionEntity.getDeltaMovement().reverse().scale(0.0125);

		Vec3 spawnPos = contraptionEntity.toGlobalVector(Vec3.atCenterOf(localPos), 0);

		Vec3 vel = contraptionEntity.getDeltaMovement();
		float scale = getVelocityBasedScale(vel) / 2;

		Vec3 leftSpawnPos = spawnPos.relative(contraptionEntity.getDirection().getCounterClockWise(), 0.5);
		Vec3 oldLeftPos = leftSpawnPos.subtract(vel);
		Vec3 rightSpawnPos = spawnPos.relative(contraptionEntity.getDirection().getClockWise(), 0.5);
		Vec3 oldRightPos = rightSpawnPos.subtract(vel);
		Vec3 leftVelocity = thrustVel.yRot(-35);
		Vec3 rightVelocity = thrustVel.yRot(35);

		if (contraptionEntity.level().hasChunk(cpos.x, cpos.z)) {
			if (!contraptionEntity.onGround()) {
				AbstractBigCannonProjectile.TrailType trailType = CBCConfigs.server().munitions.bigCannonTrailType.get();
				int lifetime = trailType == AbstractBigCannonProjectile.TrailType.SHORT ? 5 : 20 + contraptionEntity.level().random.nextInt(50);
				int smokeLifetime = trailType == AbstractBigCannonProjectile.TrailType.SHORT ? 100 : 280 + contraptionEntity.level().random.nextInt(50);
				if (trailType != AbstractBigCannonProjectile.TrailType.NONE) {
					if (contraptionEntity.getFuel() > 0) {
						ParticleOptions options = new ShellExplosionSmokeParticleData(lifetime, scale);
						for (int i = 0; i < 6; ++i) {
							double partial = i * 0.1f;
							double lx = Mth.lerp(partial, oldLeftPos.x,  leftSpawnPos.x);
							double ly = Mth.lerp(partial, oldLeftPos.y, leftSpawnPos.y);
							double lz = Mth.lerp(partial, oldLeftPos.z,  leftSpawnPos.z);
							double rx = Mth.lerp(partial, oldRightPos.x,  rightSpawnPos.x);
							double ry = Mth.lerp(partial, oldRightPos.y, rightSpawnPos.y);
							double rz = Mth.lerp(partial, oldRightPos.z,  rightSpawnPos.z);
							contraptionEntity.level().addAlwaysVisibleParticle(options, true, lx, ly + 0.5, lz, leftVelocity.x, leftVelocity.y, leftVelocity.z);
							contraptionEntity.level().addAlwaysVisibleParticle(options, true, rx, ry + 0.5, rz, rightVelocity.x, rightVelocity.y, rightVelocity.z);
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
	public int storedFuel() {
		return this.getProperties().thrusterProperties().storedFuel();
	}
	@Override
	public float addedSpread() {
		return this.getProperties().thrusterProperties().addedSpread();
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

	public SplitFuelThrusterProperties getProperties() {
		return CBCModernWarfareMunitionPropertiesHandlers.SOLID_FUEL_SPlIT_THRUSTER.getPropertiesOf(this);
	}
}
