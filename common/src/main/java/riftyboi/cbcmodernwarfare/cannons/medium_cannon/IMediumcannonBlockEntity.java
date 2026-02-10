package riftyboi.cbcmodernwarfare.cannons.medium_cannon;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.cannons.ICannonBlockEntity;
import rbasamoyai.createbigcannons.cannons.ItemCannonBehavior;

import java.util.ArrayList;
import java.util.List;

public interface IMediumcannonBlockEntity extends ICannonBlockEntity<ItemCannonBehavior> {

	default void tickFromContraption(Level level, PitchOrientedContraptionEntity poce, BlockPos localPos) {}

	default List<ItemStack> getDrops() {
		List<ItemStack> list = new ArrayList<>();
		list.add(this.cannonBehavior().getItem());
		return list;
	}

}
