package riftyboi.cbcmodernwarfare.content.sights.block.types;

import com.simibubi.create.AllShapes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;
import riftyboi.cbcmodernwarfare.content.sights.block.RotationalSightBlock;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareEntityTypes;

public class RemoteGunSightBlock extends RotationalSightBlock {
	public RemoteGunSightBlock(Properties p) {
		super(p);
	}

	@Override
	public AbstractSightEntity spawn(Level level, BlockPos pos, Direction facing) {
		AbstractSightEntity result = new AbstractSightEntity(CBCModernWarfareEntityTypes.SIGHT_ENTITY.get(), level);
		result.setParams(facing, 70.0f, 45, 360.0f, 55, 25, 0.0, 0.0, AbstractSightEntity.SightType.ANALOG);
		result.setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
		level.addFreshEntity(result);
		return result;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos,
							   CollisionContext context) {
		return state.getValue(CEILING) ? AllShapes.MECHANICAL_ARM_CEILING : AllShapes.MECHANICAL_ARM;
	}
}
