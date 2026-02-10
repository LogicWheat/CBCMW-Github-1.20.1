package riftyboi.cbcmodernwarfare.cannons.medium_cannon.breech;

import java.util.function.Consumer;

import net.createmod.catnip.animation.AnimationTickHolder;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.mojang.math.Axis;

import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.instance.Instancer;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import rbasamoyai.createbigcannons.CBCClientCommon;
import rbasamoyai.createbigcannons.cannons.autocannon.AutocannonBlock;
import rbasamoyai.createbigcannons.index.CBCBlockPartials;
import rbasamoyai.createbigcannons.munitions.autocannon.ammo_container.AutocannonAmmoContainerBlock;
import rbasamoyai.createbigcannons.munitions.autocannon.ammo_container.AutocannonAmmoContainerItem;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBlock;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockPartials;

public class MediumcannonBreechVisual extends AbstractBlockEntityVisual<MediumcannonBreechBlockEntity> implements SimpleDynamicVisual {

	private final OrientedInstance breechblock;
	private Direction blockRotation;
	private final Direction facing;

	public MediumcannonBreechVisual(VisualizationContext ctx, MediumcannonBreechBlockEntity blockEntity, float partialTick) {
		super(ctx, blockEntity, partialTick);

		Direction.Axis axis = CBCClientCommon.getRotationAxis(this.blockState);
		facing = this.blockState.getValue(BlockStateProperties.FACING);
		Direction blockRotation = facing.getCounterClockWise(axis);
		if (blockRotation == Direction.DOWN)
			blockRotation = Direction.UP;
		this.blockRotation = blockRotation;

		this.breechblock = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(getPartialModelForState())).createInstance();

		boolean alongFirst = this.blockState.getValue(MediumcannonBreechBlock.AXIS);
		if (!alongFirst) {
			this.breechblock.rotateYDegrees(90f);
		}
		if (facing.getAxis().isHorizontal()) {
			this.breechblock.rotateTo(Direction.NORTH, Direction.UP);
		}

		this.updateTransforms(partialTick);
	}


	@Override
	public void beginFrame(DynamicVisual.Context ctx) {
		this.updateTransforms(ctx.partialTick());
	}

	private void updateTransforms(float partialTick) {

		float scale = this.blockEntity.getAnimateOffset(partialTick);
		Vector3f offs = this.facing.step();
		offs.mul((1 - scale) * -0.4f);
		offs.add(this.getVisualPosition().getX(), this.getVisualPosition().getY(), this.getVisualPosition().getZ());


		float renderedBreechblockOffset = this.blockEntity.getOpenProgress(partialTick);
		renderedBreechblockOffset = renderedBreechblockOffset / 16.0f * 13.0f;
		Vector3f normal = this.blockRotation.step();
		normal.mul(renderedBreechblockOffset);
		this.breechblock.position(this.getVisualPosition()).translatePosition(normal.x(), normal.y(), normal.z()).setChanged();
	}

	@Override
	public void updateLight(float partialTick) {
		this.relight(this.pos, this.breechblock);

	}

	@Override
	protected void _delete() {
		this.breechblock.delete();

	}

	@Override
	public void collectCrumblingInstances(Consumer<Instance> consumer) {
		consumer.accept(breechblock);

	}

	private PartialModel getPartialModelForState() {
		return this.blockState.getBlock() instanceof MediumcannonBlock cBlock
			? CBCModernWarfareBlockPartials.mediumcannonEjectorFor(cBlock.getMediumcannonMaterial())
			: CBCModernWarfareBlockPartials.CAST_IRON_MEDIUMCANNON_FALLING_BREECH;
	}


}
