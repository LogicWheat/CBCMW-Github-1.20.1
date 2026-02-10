package riftyboi.cbcmodernwarfare.crafting.boring;

import com.simibubi.create.AllShapes;

import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.AbstractIncompleteMediumcannonBlock;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.material.MediumcannonMaterial;
import riftyboi.cbcmodernwarfare.crafting.casting.CBCModernWarfareCastShapes;

import java.util.function.Supplier;

public class UnboredMediumcannonBlock extends AbstractIncompleteMediumcannonBlock {

	private final VoxelShaper shapes;
	private final Supplier<CannonCastShape> cannonShape;

	public UnboredMediumcannonBlock(Properties properties, MediumcannonMaterial material, VoxelShape shape, Supplier<CannonCastShape> castShape) {
		super(properties, material);
		this.shapes = new AllShapes.Builder(shape).forDirectional();
		this.cannonShape = castShape;
	}

	public static UnboredMediumcannonBlock barrel(Properties properties, MediumcannonMaterial material) {
		return new UnboredMediumcannonBlock(properties, material, box(4, 0, 4, 12, 16, 12), () -> CBCModernWarfareCastShapes.MEDIUMCANNON_BARREL);
	}

	public static UnboredMediumcannonBlock recoilBarrel(Properties properties, MediumcannonMaterial material) {
		return new UnboredMediumcannonBlock(properties, material, box(3, 0, 3, 13, 16, 13), () -> CBCModernWarfareCastShapes.MEDIUMCANNON_RECOIL_BARREL);
	}

	public static UnboredMediumcannonBlock breech(Properties properties, MediumcannonMaterial material) {
		return new UnboredMediumcannonBlock(properties, material, box(1, 0, 1, 15, 16, 15), () -> CBCModernWarfareCastShapes.MEDIUMCANNON_BREECH);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.shapes.get(this.getFacing(state));
	}

	@Override public CannonCastShape getCannonShape() { return this.cannonShape.get(); }

}
