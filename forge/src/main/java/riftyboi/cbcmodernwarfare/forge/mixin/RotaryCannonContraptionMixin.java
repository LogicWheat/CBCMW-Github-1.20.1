package riftyboi.cbcmodernwarfare.forge.mixin;

import javax.annotation.Nonnull;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.ItemCannon;
import rbasamoyai.createbigcannons.forge.mixin_interface.GetItemStorage;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedRotarycannonContraption;
import riftyboi.cbcmodernwarfare.forge.cannons.RotarycannonBreechBlockEntity;

@Mixin(MountedRotarycannonContraption.class)
public abstract class RotaryCannonContraptionMixin extends AbstractMountedCannonContraption implements ItemCannon, GetItemStorage {

	@Override
	public ItemStack insertItemIntoCannon(ItemStack stack, boolean simulate) {
		return this.getItemStorage().map(h -> h.insertItem(1, stack, simulate)).orElse(stack);
	}

	@Override
	public ItemStack extractItemFromCannon(boolean simulate) {
		return this.getItemStorage().map(h -> h.extractItem(0, 1, simulate)).orElse(ItemStack.EMPTY);
	}

	@Nonnull
	@Override
	public LazyOptional<IItemHandler> getItemStorage() {
		return this.presentBlockEntities.get(this.startPos) instanceof RotarycannonBreechBlockEntity breech
			? LazyOptional.of(breech::createItemHandler)
			: LazyOptional.empty();
	}
}
