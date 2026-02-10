package riftyboi.cbcmodernwarfare.cannons.medium_cannon;

import com.simibubi.create.foundation.block.IBE;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.material.MediumcannonMaterial;
import riftyboi.cbcmodernwarfare.crafting.incomplete.IncompleteMediumcannonBlockEntity;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;

public abstract class AbstractIncompleteMediumcannonBlock extends MediumcannonBaseBlock implements IBE<IncompleteMediumcannonBlockEntity> {

	protected AbstractIncompleteMediumcannonBlock(Properties properties, MediumcannonMaterial material) {
		super(properties, material);
	}

	@Override
	public boolean isComplete(BlockState state) {
		return false;
	}

	@Override
	public boolean isBreechMechanism(BlockState state) {
		return false;
	}

	@Override
	public Class<IncompleteMediumcannonBlockEntity> getBlockEntityClass() {
		return IncompleteMediumcannonBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends IncompleteMediumcannonBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.INCOMPLETE_MEDIUMCANNON.get();
	}

}
