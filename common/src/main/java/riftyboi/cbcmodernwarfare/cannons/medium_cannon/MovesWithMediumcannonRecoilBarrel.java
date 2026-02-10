package riftyboi.cbcmodernwarfare.cannons.medium_cannon;

import net.minecraft.world.level.block.state.BlockState;

public interface MovesWithMediumcannonRecoilBarrel {
	BlockState getMovingState(BlockState original);
	BlockState getStationaryState(BlockState original);
}
