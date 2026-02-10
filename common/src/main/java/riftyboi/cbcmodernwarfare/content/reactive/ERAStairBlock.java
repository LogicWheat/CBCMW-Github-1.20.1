package riftyboi.cbcmodernwarfare.content.reactive;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.munitions.autocannon.flak.FlakExplosion;
import riftyboi.cbcmodernwarfare.munitions.big_cannon.heap_shell.HEAPBurst;
import riftyboi.cbcmodernwarfare.munitions.big_cannon.heap_shell.HEAPShellProjectile;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.heap.HEAPMediumcannonProjectile;


public class ERAStairBlock extends StairBlock {

	public ERAStairBlock(BlockState baseState, Properties properties) {
		super(baseState, properties);
	}

	@Override
	public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
		if(!level.isClientSide) {
			super.onProjectileHit(level, state, hit, projectile);
			if (projectile instanceof HEAPBurst || projectile instanceof HEAPShellProjectile || projectile instanceof HEAPMediumcannonProjectile) {
				BlockPos pos = hit.getBlockPos();
				projectile.discard();
				level.destroyBlock(new BlockPos(hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ()), false);
				FlakExplosion explosion = new FlakExplosion(level, null, (DamageSource) null, hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ(),
					4.0f, Level.ExplosionInteraction.NONE);
				CreateBigCannons.handleCustomExplosion(level, explosion);
				SoundType type = state.getSoundType();
				level.playSound(null, pos, type.getBreakSound(), SoundSource.NEUTRAL, type.getVolume() * 0.25f, type.getPitch());
			}
		}
	}
}
