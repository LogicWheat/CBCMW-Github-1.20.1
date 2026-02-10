package riftyboi.cbcmodernwarfare.cannon_control.compact_mount;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mojang.math.Constants;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.render.SuperByteBuffer;

import org.joml.Quaternionf;

import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountBlockEntity;
import rbasamoyai.createbigcannons.index.CBCBlockPartials;

public class CompactCannonMountBlockEntityRenderer extends KineticBlockEntityRenderer<CompactCannonMountBlockEntity> {

	public CompactCannonMountBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(CompactCannonMountBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
		if (VisualizationManager.supportsVisualization(be.getLevel())) return;;

		BlockState state = be.getBlockState();

		VertexConsumer solidBuf = buffer.getBuffer(RenderType.solid());

		ms.pushPose();

		Direction.Axis pitchAxis = ((IRotate) state.getBlock()).getRotationAxis(state);
		SuperByteBuffer pitchShaft = CachedBuffers.block(shaft(pitchAxis));
		KineticBlockEntity pitchInterface = be.getPitchInterface();
		kineticRotationTransform(pitchShaft, pitchInterface, pitchAxis, getAngleForBe(pitchInterface, be.getBlockPos(), pitchAxis), light)
			.renderInto(ms, solidBuf);

		float yaw = be.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot();
		Quaternionf qyaw = Axis.YN.rotation(yaw);

		BlockPos pos = new BlockPos(0,0,0).relative(be.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getClockWise(),1);
		CachedBuffers.partialFacing(CBCBlockPartials.CANNON_CARRIAGE_AXLE, state, Direction.NORTH)
			.translate(pos.getX(),pos.getY(),pos.getZ())
			.rotateCentered(qyaw)
			.light(light)
			.renderInto(ms, solidBuf);

		ms.popPose();
	}



	@Override
	protected BlockState getRenderedBlockState(CompactCannonMountBlockEntity te) {
		return shaft(getRotationAxisOf(te));
	}


}
