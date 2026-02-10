package riftyboi.cbcmodernwarfare.munitions.big_cannon.heap_shell;


import java.util.List;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBlockEntity;
import  riftyboi.cbcmodernwarfare.index.CBCModernWarfareEntityTypes;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.SimpleShellBlock;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionPropertiesHandlers;
import riftyboi.cbcmodernwarfare.munitions.big_cannon.MWShellBlock;

public class HEAPShellBlock extends MWShellBlock<HEAPShellProjectile> {

	public HEAPShellBlock(Properties properties) {
		super(properties);
	}

	@Override
	public AbstractBigCannonProjectile getProjectile(Level level, List<StructureBlockInfo> projectileBlocks) {
		HEAPShellProjectile projectile = CBCModernWarfareEntityTypes.HEAP_SHELL.create(level);
		projectile.setFuze(getFuzeFromBlocks(projectileBlocks));
		return projectile;
	}


	@Override
	public boolean isBaseFuze() {
		return CBCModernWarfareMunitionPropertiesHandlers.HEAP_SHELL.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

	public EntityType<? extends HEAPShellProjectile> getAssociatedEntityType() {
		return CBCModernWarfareEntityTypes.HEAP_SHELL.get();
	}
}
