package riftyboi.cbcmodernwarfare.munitions.big_cannon.apds_shot;

import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.munitions.big_cannon.InertProjectileBlock;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareEntityTypes;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;

public class APDSShotBlock extends InertProjectileBlock {

	public APDSShotBlock(Properties properties) {
		super(properties);
	}

	@Override
	public EntityType<? extends AbstractBigCannonProjectile> getAssociatedEntityType() {
		return CBCModernWarfareEntityTypes.APDS_SHOT.get();
	}

}
