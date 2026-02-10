package riftyboi.cbcmodernwarfare.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;

import net.minecraft.world.item.ItemStack;

import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.At;

import rbasamoyai.createbigcannons.cannon_control.ControlPitchContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedMediumcannonContraption;
import riftyboi.cbcmodernwarfare.cannons.RecoilingCannon;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.breech.MediumcannonBreechBlockEntity;

@Mixin(value = MountedMediumcannonContraption.class, remap = false)
public abstract class MediumCannonContraptionMixin extends AbstractMountedCannonContraption {

	@WrapOperation(
		method = "fireShot",
		at = @At(
			value = "INVOKE",
			target = "Lrbasamoyai/createbigcannons/cannon_control/ControlPitchContraption;onRecoil(Lnet/minecraft/world/phys/Vec3;Lcom/simibubi/create/content/contraptions/AbstractContraptionEntity;)V"
		)
	)
	private void mixinRecoil(ControlPitchContraption instance, Vec3 vector, AbstractContraptionEntity cannon, Operation<Void> original) {
		original.call(instance, vector.scale(0.22f), cannon);

		if (this.entity instanceof RecoilingCannon mpoe) {
			mpoe.recoil(vector.normalize());
		}
		MountedMediumcannonContraption mmc = (MountedMediumcannonContraption) (Object) this;
		mmc.hasFired = true;
	}

	@WrapOperation(
		method = "fireShot",
		at = @At(
			value = "INVOKE",
			target = "Lriftyboi/cbcmodernwarfare/cannons/medium_cannon/breech/MediumcannonBreechBlockEntity;setInputBuffer(Lnet/minecraft/world/item/ItemStack;)V"
		)
	)
	private void mixinAfterFire(MediumcannonBreechBlockEntity breech, ItemStack stack, Operation<Void> original) {
		original.call(breech, stack);

		breech.setLoadingCooldown((int)(CBCConfigs.server().cannons.quickfiringBreechLoadingCooldown.get() * 1.8));
	}
}
