package riftyboi.cbcmodernwarfare.munitions.big_cannon;

import net.minecraft.world.level.block.entity.BlockEntityType;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBlockEntity;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedProjectileBlock;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;

public abstract class MWShellBlock<ENTITY_TYPE extends FuzedBigCannonProjectile>
	extends FuzedProjectileBlock<FuzedBlockEntity, ENTITY_TYPE> {

	protected MWShellBlock(Properties properties) {
		super(properties);
	}

	@Override
	public Class<FuzedBlockEntity> getBlockEntityClass() {
		return FuzedBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends FuzedBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.FUZED_BLOCK.get();
	}

}
