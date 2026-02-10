package riftyboi.cbcmodernwarfare.content.sights.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.NotNull;

import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;
import riftyboi.cbcmodernwarfare.content.sights.block.AbstractSightBlock;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareEntityTypes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareShapes;

public class PeriscopeSightBlock extends AbstractSightBlock {

	public PeriscopeSightBlock(Properties p) {
		super(p);
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos,
										@NotNull CollisionContext context) {
		return CBCModernWarfareShapes.PERISCOPE.get(state.getValue(HORIZONTAL_FACING));
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return getShape(state, world, pos, context);
	}


		@Override
	public AbstractSightEntity spawn(Level level, BlockPos pos, Direction facing) {
		AbstractSightEntity result = new AbstractSightEntity(CBCModernWarfareEntityTypes.SIGHT_ENTITY.get(), level);
		result.setParams(facing, 2.5f, 2.5f, 45.0f, 75, 45, 12.0/16.0,15.0/16.0, AbstractSightEntity.SightType.PERISCOPE);
		result.setPos((double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D);
		level.addFreshEntity(result);
		return result;
	}
}
