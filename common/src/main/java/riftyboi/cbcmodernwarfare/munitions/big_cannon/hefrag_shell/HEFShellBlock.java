package riftyboi.cbcmodernwarfare.munitions.big_cannon.hefrag_shell;

import net.minecraft.world.entity.EntityType;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareEntityTypes;
import rbasamoyai.createbigcannons.munitions.big_cannon.SimpleShellBlock;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionPropertiesHandlers;
import riftyboi.cbcmodernwarfare.munitions.big_cannon.MWShellBlock;


public class HEFShellBlock extends MWShellBlock<HEFShellProjectile> {

	public HEFShellBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCModernWarfareMunitionPropertiesHandlers.HEF_SHELL.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

	@Override
	public EntityType<? extends HEFShellProjectile> getAssociatedEntityType() {
		return CBCModernWarfareEntityTypes.HEF_SHELL.get();
	}

}
