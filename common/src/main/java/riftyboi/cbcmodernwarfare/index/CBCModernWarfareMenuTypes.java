package riftyboi.cbcmodernwarfare.index;


import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.entry.MenuEntry;

import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.index.CBCMenuTypes;
import rbasamoyai.createbigcannons.munitions.autocannon.ammo_container.AutocannonAmmoContainerScreen;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.munitions.autocannon.ammo_drum.AutocannonAmmoDrumMenu;
import riftyboi.cbcmodernwarfare.munitions.autocannon.ammo_drum.AutocannonAmmoDrumScreen;

public class CBCModernWarfareMenuTypes {
	public static final MenuEntry<AutocannonAmmoDrumMenu> AUTOCANNON_AMMO_DRUM = CBCModernWarfare.REGISTRATE
		.menu("autocannon_ammo_drum", AutocannonAmmoDrumMenu::getClientMenu, () -> AutocannonAmmoDrumScreen::new)
		.register();

	public static void register() {
	}

}
