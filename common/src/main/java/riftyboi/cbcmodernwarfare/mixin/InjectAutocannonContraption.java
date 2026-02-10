package riftyboi.cbcmodernwarfare.mixin;


import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.ItemCannon;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedAutocannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.munitions.autocannon.AbstractAutocannonProjectile;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonAmmoItem;
import riftyboi.cbcmodernwarfare.munitions.autocannon.AmmoItemMixinInerface;
import riftyboi.cbcmodernwarfare.munitions.autocannon.AutocannonProjectileMixinInterface;

@Mixin(value = MountedAutocannonContraption.class,priority = 1100)
public abstract class InjectAutocannonContraption extends AbstractMountedCannonContraption implements ItemCannon {

	@Inject(at = @At(value = "INVOKE", target = "Lrbasamoyai/createbigcannons/munitions/autocannon/AbstractAutocannonProjectile;getYRot()F"), method = "fireShot")
	public void injectFireShot(ServerLevel level, PitchOrientedContraptionEntity entity, CallbackInfo ci, @Local(name = "foundProjectile") ItemStack foundProjectile, @Local AutocannonAmmoItem round, @Local AbstractAutocannonProjectile projectile) {
		if (round instanceof AmmoItemMixinInerface roundItemInterface && projectile instanceof AutocannonProjectileMixinInterface autoProjectileInterface) {
			boolean isIncendiary = roundItemInterface.isIncendiary(foundProjectile);
			autoProjectileInterface.setIncendiary(isIncendiary);
		}
	}
}
