package riftyboi.cbcmodernwarfare.forge.mixin;


import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.block.entity.BlockEntity;

import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.quickfiring_breech.CannonMountPoint;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountPoint;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedMediumcannonContraption;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.breech.MediumcannonBreechBlockEntity;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonAmmoItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonCartridgeItem;

import static riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountPoint.mediumcannonInsert;

@Mixin(value = CannonMountPoint.class, remap = false)
public class CannonMountPointMixin {

	@Inject(method = "getInsertedResultAndDoSomething", at = @At(value = "RETURN"), cancellable = true)
	public void cbcmodernwarfare$getInsertedResultAndDoSomethingInject(ItemStack stack, boolean simulate, AbstractMountedCannonContraption cannon, PitchOrientedContraptionEntity poce, CallbackInfoReturnable<ItemStack> cir) {
		if (cannon instanceof MountedMediumcannonContraption medcannon) {
			cir.setReturnValue(CompactCannonMountPoint.mediumcannonInsert(stack, simulate, medcannon, poce));
		}
	}
}
