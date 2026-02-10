package riftyboi.cbcmodernwarfare.content.sights.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;

public interface ISightBlock {
	AbstractSightEntity spawn(Level level, BlockPos pos, Direction facing);
}
