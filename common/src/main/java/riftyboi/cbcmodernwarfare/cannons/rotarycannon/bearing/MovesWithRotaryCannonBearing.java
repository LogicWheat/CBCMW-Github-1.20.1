package riftyboi.cbcmodernwarfare.cannons.rotarycannon.bearing;
import net.minecraft.world.level.block.state.BlockState;
public interface  MovesWithRotaryCannonBearing {
	BlockState getMovingState(BlockState original);
	BlockState getStationaryState(BlockState original);
	}
