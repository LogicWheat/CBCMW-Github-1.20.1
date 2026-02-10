package riftyboi.cbcmodernwarfare.cannons.rotarycannon;

import com.simibubi.create.foundation.block.IBE;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.crafting.incomplete.IncompleteAutocannonBlockEntity;
import rbasamoyai.createbigcannons.index.CBCBlockEntities;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.material.RotarycannonMaterial;
import riftyboi.cbcmodernwarfare.crafting.incomplete.IncompleteRotarycannonBlock;
import riftyboi.cbcmodernwarfare.crafting.incomplete.IncompleteRotarycannonBlockEntity;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;

public abstract class AbstractIncompleteRotarycannonBlock extends RotarycannonBaseBlock implements IBE<IncompleteRotarycannonBlockEntity> {

	protected AbstractIncompleteRotarycannonBlock(Properties properties, RotarycannonMaterial material) {
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
	public Class<IncompleteRotarycannonBlockEntity> getBlockEntityClass() {
		return IncompleteRotarycannonBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends IncompleteRotarycannonBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.INCOMPLETE_ROTARYCANNON.get();
	}

}
