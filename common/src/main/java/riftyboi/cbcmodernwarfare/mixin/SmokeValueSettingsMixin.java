package riftyboi.cbcmodernwarfare.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsClient;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import riftyboi.cbcmodernwarfare.content.smoke_discharger.SmokeDischargerBlockEntity;
import riftyboi.cbcmodernwarfare.content.smoke_discharger.SmokeDischargerBlockEntity.SmokeDischargerScrollValueBehavior;
import riftyboi.cbcmodernwarfare.content.smoke_discharger.SmokeDischargerValueScreen;

@Mixin(ValueSettingsClient.class)
public class SmokeValueSettingsMixin {

	@Shadow
	public BlockPos interactHeldPos;

	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/createmod/catnip/gui/ScreenOpener;open(Lnet/minecraft/client/gui/screens/Screen;)V"))
	private void createbigcannons$tick$openScreen(Screen screen, Operation<Void> original, @Local ValueSettingsBehaviour valueSettingBehaviour,
												  @Local Player player, @Local BlockHitResult blockHitResult) {
		if (valueSettingBehaviour instanceof SmokeDischargerScrollValueBehavior fixedMountBehaviour) {
			original.call(new SmokeDischargerValueScreen(this.interactHeldPos, valueSettingBehaviour.createBoard(player, blockHitResult),
				valueSettingBehaviour.getValueSettings(), valueSettingBehaviour::newSettingHovered, fixedMountBehaviour.setsPitch(), valueSettingBehaviour.netId()));
			return;
		}
		original.call(screen);
	}
}
