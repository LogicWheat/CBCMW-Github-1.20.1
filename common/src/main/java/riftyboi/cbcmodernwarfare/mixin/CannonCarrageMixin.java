package riftyboi.cbcmodernwarfare.mixin;


import net.minecraft.world.entity.Entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import rbasamoyai.createbigcannons.base.goggles.IHaveEntityGoggleInformation;
import rbasamoyai.createbigcannons.cannon_control.ControlPitchContraption;
import rbasamoyai.createbigcannons.cannon_control.carriage.CannonCarriageEntity;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedAutocannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedRotarycannonContraption;

@Mixin(value = CannonCarriageEntity.class, remap = false)
public abstract class CannonCarrageMixin extends Entity implements ControlPitchContraption, IHaveEntityGoggleInformation {

	@Shadow private PitchOrientedContraptionEntity cannonContraption;
	public CannonCarrageMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}
	@Inject(method = "trySettingFireRateCarriage", at = @At("HEAD"))
	public void trySettingFireRateCarriage(int fireRateAdjustment, CallbackInfo ci) {
		if (!this.level().isClientSide && this.cannonContraption != null && this.cannonContraption.getContraption() instanceof MountedRotarycannonContraption rotarycannonContraption)
			rotarycannonContraption.trySettingFireRateCarriage(fireRateAdjustment);
	}
}
