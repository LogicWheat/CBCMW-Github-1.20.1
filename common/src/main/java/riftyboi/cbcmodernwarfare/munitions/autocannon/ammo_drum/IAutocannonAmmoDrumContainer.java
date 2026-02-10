package riftyboi.cbcmodernwarfare.munitions.autocannon.ammo_drum;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonAmmoType;

public interface IAutocannonAmmoDrumContainer extends Container {


	int AMMO_SLOT = 0;
	int AMMO_SLOT1 = 1;
	int TRACER_SLOT = 2;

	ItemStack getMainAmmoStack();
	ItemStack getSecondaryAmmoStack();
	ItemStack getTracerStack();

	default int getMainAmmoCapacity() {
		int remainder = Math.max(0, this.getAmmoType().getCapacity() * 3 - this.getTotalCount());
		ItemStack stack = this.getMainAmmoStack();
		return Math.min(stack.getCount() + remainder, stack.getMaxStackSize());
	}

	default int getSecondaryAmmoCapacity() {
		int remainder = Math.max(0, this.getAmmoType().getCapacity() * 3 - this.getTotalCount());
		ItemStack stack = this.getSecondaryAmmoStack();
		return Math.min(stack.getCount() + remainder, stack.getMaxStackSize());
	}

	default int getTracerAmmoCapacity() {
		int remainder = Math.max(0, this.getAmmoType().getCapacity() * 3 - this.getTotalCount());
		ItemStack stack = this.getTracerStack();
		return Math.min(stack.getCount() + remainder, stack.getMaxStackSize());
	}

	default int getTotalCount() { return this.getMainAmmoStack().getCount() + this.getSecondaryAmmoStack().getCount() + this.getTracerStack().getCount(); }

	default AutocannonAmmoType getAmmoType() {
		AutocannonAmmoType type = AutocannonAmmoType.of(this.getMainAmmoStack());
		AutocannonAmmoType type1 = AutocannonAmmoType.of(this.getSecondaryAmmoStack());
		return type != AutocannonAmmoType.NONE ? type : type1 != AutocannonAmmoType.NONE ? type1 : AutocannonAmmoType.of(this.getTracerStack());
	}

	@Override default int getContainerSize() { return 3; }

	@Override
	default boolean isEmpty() {
		return this.getMainAmmoStack().isEmpty() && this.getSecondaryAmmoStack().isEmpty()  && this.getTracerStack().isEmpty();
	}

	@Override
	default ItemStack getItem(int slot) {
		return switch (slot) {
			case 0 -> this.getMainAmmoStack();
			case 1 -> this.getSecondaryAmmoStack();
			case 2 -> this.getTracerStack();
			default -> ItemStack.EMPTY;
		};
	}

	@Override
	default boolean canPlaceItem(int index, ItemStack stack) {
		if (index != AMMO_SLOT && index != AMMO_SLOT1 && index != TRACER_SLOT) return false;
		AutocannonAmmoType ammoType = this.getAmmoType();
		if (!ammoType.isValidMunition(stack)) return false;
		boolean ammoSlot = index == AMMO_SLOT;
		boolean ammoSlot1 = index == AMMO_SLOT1;
		int currentCapacity;
		if (ammoType == AutocannonAmmoType.NONE) {
			currentCapacity = AutocannonAmmoType.of(stack).getCapacity();
		} else {
			currentCapacity = ammoSlot ? this.getMainAmmoCapacity() : ammoSlot1 ? this.getSecondaryAmmoCapacity() : this.getTracerAmmoCapacity();
			currentCapacity -= this.getItem(index).getCount();
		}
		return currentCapacity > 0;
	}

}
