package riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.sliding_breech;

import org.joml.Vector3f;

import com.simibubi.create.content.kinetics.base.ShaftVisual;

import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import rbasamoyai.createbigcannons.CBCClientCommon;

import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.quickfiring.LauncherQuickFiringBreechBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.sliding_breech.LauncherSlidingBreechBlockEntity;

public class LauncherSlidingBrechVisual extends ShaftVisual<LauncherSlidingBreechBlockEntity> implements SimpleDynamicVisual {

	private final LauncherSlidingBreechBlockEntity breech;
	private final OrientedInstance breechblock;
	private final Direction blockRotation;

	public LauncherSlidingBrechVisual(VisualizationContext ctx, LauncherSlidingBreechBlockEntity tile, float partialTick) {
		super(ctx, tile, partialTick);
		this.breech = tile;
		Direction.Axis axis = CBCClientCommon.getRotationAxis(this.blockState);
		Direction facing = this.blockState.getValue(BlockStateProperties.FACING);
		Direction blockRotation = facing.getCounterClockWise(axis);
		if (blockRotation == Direction.DOWN)
			blockRotation = Direction.UP;
		this.blockRotation = blockRotation;

		this.breechblock = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(CBCClientCommon.getBreechblockForState(this.blockState))).createInstance();

		boolean alongFirst = this.blockState.getValue(LauncherQuickFiringBreechBlock.AXIS);
		if (!alongFirst) {
			this.breechblock.rotateYDegrees(90f);
		}
		if (facing.getAxis().isHorizontal()) {
			this.breechblock.rotateTo(Direction.NORTH, Direction.UP);
		}

		this.transformModels(partialTick);
	}

	@Override
	public void beginFrame(DynamicVisual.Context ctx) {
		this.transformModels(ctx.partialTick());
	}

	private void transformModels(float partialTick) {
		float renderedBreechblockOffset = this.breech.getRenderedBlockOffset(AnimationTickHolder.getPartialTicks());
		renderedBreechblockOffset = renderedBreechblockOffset / 16.0f * 13.0f;
		Vector3f normal = this.blockRotation.step();
		normal.mul(renderedBreechblockOffset);
		this.breechblock.position(this.getVisualPosition()).translatePosition(normal.x(), normal.y(), normal.z()).setChanged();
	}

	@Override
	public void updateLight(float partialTick) {
		super.updateLight(partialTick);
		this.relight(this.pos, this.breechblock);
	}

	@Override
	public void _delete() {
		super._delete();
		this.breechblock.delete();
	}

}
