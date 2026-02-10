package riftyboi.cbcmodernwarfare.content.sights.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareEntityTypes;

public class TelescopicSightBlock {

	public AbstractSightEntity spawn(Level level, BlockPos pos, Direction facing) {
		AbstractSightEntity result = new AbstractSightEntity(CBCModernWarfareEntityTypes.SIGHT_ENTITY.get(), level);
		result.setParams(facing, 55, 35, 0, 45,15, 12.0/16.0, 0.0, AbstractSightEntity.SightType.PERISCOPE);
		result.setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
		level.addFreshEntity(result);
		return result;
	}
}
