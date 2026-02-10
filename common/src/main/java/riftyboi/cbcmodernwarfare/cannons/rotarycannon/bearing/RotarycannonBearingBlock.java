package riftyboi.cbcmodernwarfare.cannons.rotarycannon.bearing;

import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.IBE;

import com.tterrag.registrate.util.nullness.NonNullFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBaseBlock;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.material.RotarycannonMaterial;
import riftyboi.cbcmodernwarfare.crafting.casting.CBCModernWarfareCastShapes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareShapes;

public class RotarycannonBearingBlock extends RotarycannonBaseBlock implements IBE<RotarycannonBearingBlockEntity>, MovesWithRotaryCannonBearing {


	public RotarycannonBearingBlock(Properties properties, RotarycannonMaterial material) {
		super(properties, material);
	}

	@Override
	public Class<RotarycannonBearingBlockEntity> getBlockEntityClass() {
		return RotarycannonBearingBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends RotarycannonBearingBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.ROTARYCANNON_BEARING.get();
	}

	@Override
	public CannonCastShape getCannonShape() {
		return CBCModernWarfareCastShapes.ROTARYCANNON_BEARING;
	}

	@Override
	public boolean isBreechMechanism(BlockState state) {
		return false;
	}

	@Override
	public boolean isComplete(BlockState state) {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return CBCModernWarfareShapes.RECOIL_BARREL.get(this.getFacing(state).getAxis());
	}
	@Override
	public BlockState getMovingState(BlockState original) {
		return Blocks.AIR.defaultBlockState();
	}
	@Override public BlockState getStationaryState(BlockState original) { return original; }

}
