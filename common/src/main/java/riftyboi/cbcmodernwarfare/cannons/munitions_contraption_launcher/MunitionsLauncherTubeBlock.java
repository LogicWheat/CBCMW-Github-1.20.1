package riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher;

import java.util.function.Supplier;

import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.IBE;
import net.createmod.catnip.math.VoxelShaper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import rbasamoyai.createbigcannons.index.CBCBlockEntities;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end.MunitionsLauncherEnd;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.material.MunitionsLauncherMaterial;
import riftyboi.cbcmodernwarfare.crafting.casting.CBCModernWarfareCastShapes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareChecks;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareEntityTypes;

public class MunitionsLauncherTubeBlock extends MunitionsLauncherBaseBlock implements IBE<MunitionsLauncherBlockEntity> {

	private final VoxelShaper visualShapes;
	private final VoxelShaper collisionShapes;
	private final Supplier<CannonCastShape> cannonShape;

	public MunitionsLauncherTubeBlock(Properties properties, MunitionsLauncherMaterial material, Supplier<CannonCastShape> cannonShape, VoxelShape base) {
		this(properties, material, cannonShape, base, base);
	}

	public MunitionsLauncherTubeBlock(Properties properties, MunitionsLauncherMaterial material, Supplier<CannonCastShape> cannonShape, VoxelShape visualShape, VoxelShape collisionShape) {
		super(properties, material);
		this.visualShapes = new AllShapes.Builder(visualShape).forDirectional();
		this.collisionShapes = new AllShapes.Builder(collisionShape).forDirectional();
		this.cannonShape = cannonShape;
	}

	public static MunitionsLauncherTubeBlock medium(Properties properties, MunitionsLauncherMaterial material) {
		return new MunitionsLauncherTubeBlock(properties, material, () -> CannonCastShape.MEDIUM, Shapes.block());
	}
	public static MunitionsLauncherTubeBlock verySmall(Properties properties, MunitionsLauncherMaterial material) {
		return new MunitionsLauncherTubeBlock(properties, material, () -> CannonCastShape.VERY_SMALL, Block.box(2, 0, 2, 14, 16, 14));
	}
	public static MunitionsLauncherTubeBlock small(Properties properties, MunitionsLauncherMaterial material) {
		return new MunitionsLauncherTubeBlock(properties, material, () -> CannonCastShape.SMALL, Block.box(1, 0, 1, 15, 16, 15));
	}

	@Override
	public CannonCastShape getCannonShape() {
		return this.cannonShape.get();
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.collisionShapes.get(this.getFacing(state));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.visualShapes.get(this.getFacing(state));
	}

	@Override public MunitionsLauncherEnd getDefaultOpeningType() { return MunitionsLauncherEnd.OPEN; }

	@Override
	public boolean isComplete(BlockState state) {
		return true;
	}

	@Override
	public Class<MunitionsLauncherBlockEntity> getBlockEntityClass() {
		return MunitionsLauncherBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends MunitionsLauncherBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.MUNITIONS_LAUNCHER.get();
	}

}
