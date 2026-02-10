package riftyboi.cbcmodernwarfare.cannons.rotarycannon.bearing;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.math.Axis;


import com.simibubi.create.content.kinetics.base.ShaftRenderer;

import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.renderer.RenderType;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class RotarycannonBearingRenderer extends SmartBlockEntityRenderer<RotarycannonBearingBlockEntity> {
	public RotarycannonBearingRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(RotarycannonBearingBlockEntity spring, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(spring, partialTicks, ms, buffer, light, overlay);
		if (VisualizationManager.supportsVisualization(spring.getLevel())) return;


		Direction facing = spring.getBlockState().getValue(BlockStateProperties.FACING);

		float time = AnimationTickHolder.getRenderTime();
		float rot = spring.getRot();
		spring.setRotation(rot, time);


		Quaternionf qrot = new Quaternionf().rotationAxis(spring.getRotation(), facing.step().set(spring.getBlockPos().getX(),spring.getBlockPos().getY(),spring.getBlockPos().getZ()) );
		ms.pushPose();

		BlockRenderDispatcher brd = Minecraft.getInstance().getBlockRenderer();
		for (Map.Entry<BlockPos, BlockState> entry : spring.toAnimate.entrySet()) {
			if (entry.getValue() == null) continue;
			ms.pushPose();
			ms.translate(entry.getKey().getX(),entry.getKey().getY(),entry.getKey().getZ());
			ms.translate(.5f, .5f, .5f);
			ms.mulPose(qrot);
			ms.translate(-.5f, -.5f, -.5f);
			brd.renderSingleBlock(entry.getValue(), ms, buffer, light, OverlayTexture.NO_OVERLAY);
			ms.popPose();
		}
		ms.popPose();
	}
}
