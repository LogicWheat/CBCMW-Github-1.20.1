package riftyboi.cbcmodernwarfare.crafting.boring;

import net.createmod.catnip.math.VoxelShaper;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.SolidMunitionsLauncherBlock;


import java.util.function.Supplier;

import com.simibubi.create.AllShapes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end.MunitionsLauncherEndBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.material.MunitionsLauncherMaterial;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;

public class UnboredMunitionsLauncherBlock extends SolidMunitionsLauncherBlock<MunitionsLauncherEndBlockEntity> {

	private final VoxelShaper visualShapes;
	private final VoxelShaper collisionShapes;
	private final Supplier<CannonCastShape> cannonShape;

	public UnboredMunitionsLauncherBlock(Properties properties, MunitionsLauncherMaterial material, Supplier<CannonCastShape> cannonShape, VoxelShape base) {
		this(properties, material, cannonShape, base, base);
	}

	public UnboredMunitionsLauncherBlock(Properties properties, MunitionsLauncherMaterial material, Supplier<CannonCastShape> cannonShape, VoxelShape visualShape, VoxelShape collisionShape) {
		super(properties, material);
		this.visualShapes = new AllShapes.Builder(visualShape).forDirectional();
		this.collisionShapes = new AllShapes.Builder(collisionShape).forDirectional();
		this.cannonShape = cannonShape;
	}

	public static UnboredMunitionsLauncherBlock verySmall(Properties properties, MunitionsLauncherMaterial material) {
		return new UnboredMunitionsLauncherBlock(properties, material, () -> CannonCastShape.VERY_SMALL, box(2, 0, 2, 14, 16, 14));
	}

	public static UnboredMunitionsLauncherBlock small(Properties properties, MunitionsLauncherMaterial material) {
		return new UnboredMunitionsLauncherBlock(properties, material, () -> CannonCastShape.SMALL, box(1, 0, 1, 15, 16, 15));
	}

	public static UnboredMunitionsLauncherBlock medium(Properties properties, MunitionsLauncherMaterial material) {
		return new UnboredMunitionsLauncherBlock(properties, material, () -> CannonCastShape.MEDIUM, Shapes.block());
	}

	public static UnboredMunitionsLauncherBlock large(Properties properties, MunitionsLauncherMaterial material) {
		return new UnboredMunitionsLauncherBlock(properties, material, () -> CannonCastShape.LARGE, box(-1, 0, -1, 17, 16, 17), Shapes.block());
	}

	public static UnboredMunitionsLauncherBlock veryLarge(Properties properties, MunitionsLauncherMaterial material) {
		return new UnboredMunitionsLauncherBlock(properties, material, () -> CannonCastShape.VERY_LARGE, box(-2, 0, -2, 18, 16, 18), Shapes.block());
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.collisionShapes.get(this.getFacing(state));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.visualShapes.get(this.getFacing(state));
	}

	@Override
	public CannonCastShape getCannonShape() {
		return this.cannonShape.get();
	}

	@Override
	public boolean isComplete(BlockState state) {
		return false;
	}

	@Override
	public Class<MunitionsLauncherEndBlockEntity> getBlockEntityClass() {
		return MunitionsLauncherEndBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends MunitionsLauncherEndBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.LAUNCHER_END.get();
	}

}
