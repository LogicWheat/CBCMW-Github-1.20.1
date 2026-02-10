package riftyboi.cbcmodernwarfare.mixin;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import rbasamoyai.createbigcannons.network.CBCRootNetwork;
import rbasamoyai.createbigcannons.network.RootPacket;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.network.CameraMovePacket;
import riftyboi.cbcmodernwarfare.network.DismountCameraPacket;
import riftyboi.cbcmodernwarfare.network.RecoilingCannonUpdatePacket;
import riftyboi.cbcmodernwarfare.network.ServerboundSetSmokeDischargerValuePacket;
import riftyboi.cbcmodernwarfare.network.SetCameraViewPacket;

import java.util.List;
import java.util.function.Function;

@Mixin(value = CBCRootNetwork.class, remap = false)
public class RootNetworkMixin {

	@Shadow
	private static  <T extends RootPacket> void addMsg(int id, Class<T> clazz, Function<FriendlyByteBuf, T> decoder) {

	}

	@Inject(method = "init", at = @At("RETURN"), remap = false)
	private static void injectInit(CallbackInfo ci, @Local int id) {
		addMsg(id++, CameraMovePacket.class,CameraMovePacket::new);
		addMsg(id++, DismountCameraPacket.class,DismountCameraPacket::new);
		addMsg(id++, SetCameraViewPacket.class,SetCameraViewPacket::new);
		addMsg(id++, ServerboundSetSmokeDischargerValuePacket.class,ServerboundSetSmokeDischargerValuePacket::new);
		addMsg(id++, RecoilingCannonUpdatePacket.class,RecoilingCannonUpdatePacket::new);
	}
}
