package riftyboi.cbcmodernwarfare.mixin.interfaces;

import net.minecraft.core.Position;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.phys.HitResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import rbasamoyai.createbigcannons.munitions.AbstractCannonProjectile;
import rbasamoyai.createbigcannons.munitions.ProjectileContext;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.fuzes.FuzeItem;

import java.util.function.Predicate;

@Mixin(value = AbstractCannonProjectile.class)
public interface AbstractBigCannonAccessor {
	@Invoker(value = "onImpact", remap = false)
	boolean invokeImpact(HitResult hitResult, AbstractCannonProjectile.ImpactResult impactResult, ProjectileContext projectileContext);
}
