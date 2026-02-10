package riftyboi.cbcmodernwarfare.mixin.interfaces;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.fuzes.FuzeItem;

import java.util.function.Predicate;

@Mixin(value = FuzedBigCannonProjectile.class)
public interface FuzedProjectileAccessor {

	@Accessor
	ItemStack getFuze();

	@Invoker(value = "detonate", remap = false)
	void invokeDetonate(Position position);

	@Invoker(value = "canDetonate", remap = false)
	boolean invokeCanDetonate(Predicate<FuzeItem> cons);
	@Invoker(value = "getFuzeProperties",remap = false)
	BigCannonFuzePropertiesComponent invokeGetFuzeProperties();

}
