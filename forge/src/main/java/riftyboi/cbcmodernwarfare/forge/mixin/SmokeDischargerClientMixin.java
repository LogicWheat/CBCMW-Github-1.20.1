package riftyboi.cbcmodernwarfare.forge.mixin;

import net.minecraft.world.phys.BlockHitResult;

import net.minecraft.world.phys.HitResult;

import org.spongepowered.asm.mixin.Shadow;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllKeys;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsClient;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.simple.SimpleChannel;
import riftyboi.cbcmodernwarfare.content.smoke_discharger.SmokeDischargerBlockEntity;
import  riftyboi.cbcmodernwarfare.content.smoke_discharger.SmokeDischargerBlockEntity.SmokeDischargerScrollValueBehavior;
import rbasamoyai.createbigcannons.multiloader.NetworkPlatform;
import riftyboi.cbcmodernwarfare.network.ServerboundSetSmokeDischargerValuePacket;

@Mixin(ValueSettingsClient.class)
public class SmokeDischargerClientMixin {
	@Shadow
	public BlockPos interactHeldPos;
	@Shadow
	public InteractionHand interactHeldHand;
	@Shadow
	public Direction interactHeldFace;

	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/network/simple/SimpleChannel;sendToServer(Ljava/lang/Object;)V"), remap = false)
	private void createbigcannons$tick$cancelPacket(SimpleChannel instance, Object message, Operation<Void> original, @Local ValueSettingsBehaviour valueSettingBehaviour, @Local HitResult hitResult) {
		if (valueSettingBehaviour instanceof SmokeDischargerBlockEntity.SmokeDischargerScrollValueBehavior fixedMountBehaviour && hitResult instanceof BlockHitResult blockHitResult) {
			NetworkPlatform.sendToServer(new ServerboundSetSmokeDischargerValuePacket(this.interactHeldPos, 0, 0,
				this.interactHeldHand, blockHitResult, this.interactHeldFace, AllKeys.ctrlDown(), fixedMountBehaviour.setsPitch()));
			return;
		}
		original.call(instance, message);
	}
}
