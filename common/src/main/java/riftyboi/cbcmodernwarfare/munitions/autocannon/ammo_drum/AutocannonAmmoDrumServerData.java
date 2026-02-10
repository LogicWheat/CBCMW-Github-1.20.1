package riftyboi.cbcmodernwarfare.munitions.autocannon.ammo_drum;

import javax.annotation.Nullable;
import net.minecraft.world.inventory.ContainerData;

public class AutocannonAmmoDrumServerData implements ContainerData {

	@Nullable private final AutocannonAmmoDrumBlockEntity be;

	public AutocannonAmmoDrumServerData(@Nullable AutocannonAmmoDrumBlockEntity be) {
		this.be = be;
	}

	@Override
	public int get(int index) {
		return this.be != null && index == 0 ? this.be.getSpacing() : 1;
	}

	@Override
	public void set(int index, int value) {
		if (this.be != null && index == 0) this.be.setSpacing(value);
	}

	@Override public int getCount() { return 1; }

}
