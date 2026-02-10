package riftyboi.cbcmodernwarfare.forge.mixin.compat;


import com.simibubi.create.content.decoration.copycat.CopycatBlock;

import com.simibubi.create.content.decoration.copycat.CopycatBlockEntity;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import org.spongepowered.asm.mixin.Mixin;

import riftyboi.cbcmodernwarfare.content.reactive.ERABlock;

@Mixin(CopycatBlock.class)
public class CopycatBlockReactiveArmorMixin extends Block {
	public CopycatBlockReactiveArmorMixin(Properties properties) {
		super(properties);
	}

	@Override
	public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
		if (level.getBlockEntity(hit.getBlockPos()) instanceof CopycatBlockEntity cbe)
			if (cbe.getMaterial().getBlock() instanceof ERABlock era){
				era.onProjectileHit(level, state, hit, projectile);
			}
	}
}
