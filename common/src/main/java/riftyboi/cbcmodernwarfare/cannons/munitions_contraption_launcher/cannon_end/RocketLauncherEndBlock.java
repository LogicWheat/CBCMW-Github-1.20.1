package riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end;

import java.util.function.Supplier;

import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.IBE;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherTubeBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.material.MunitionsLauncherMaterial;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;

public class RocketLauncherEndBlock extends MunitionsLauncherTubeBlock {
	public RocketLauncherEndBlock(Properties properties, MunitionsLauncherMaterial material, Supplier<CannonCastShape> cannonShape, VoxelShape base) {
		super(properties, material, cannonShape, base, base);
	}
	public static RocketLauncherEndBlock small(Properties properties, MunitionsLauncherMaterial material) {
		return new RocketLauncherEndBlock(properties, material, () -> CannonCastShape.CANNON_END, Block.box(1, 0, 1, 15, 16, 15));
	}
	@Override public MunitionsLauncherEnd getDefaultOpeningType() { return MunitionsLauncherEnd.CLOSED; }

}
