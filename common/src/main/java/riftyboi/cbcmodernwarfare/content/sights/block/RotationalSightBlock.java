package riftyboi.cbcmodernwarfare.content.sights.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public abstract class RotationalSightBlock extends AbstractSightBlock {
	public static final BooleanProperty CEILING = BooleanProperty.create("ceiling");


	public RotationalSightBlock(Properties p) {
		super(p);
		registerDefaultState(defaultBlockState().setValue(CEILING, false));
	}


	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
		super.createBlockStateDefinition(state.add(CEILING));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return defaultBlockState().setValue(CEILING, ctx.getClickedFace() == Direction.DOWN);
	}

}
