package riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;


import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class MunitionsLauncherBlockRenderer implements BlockEntityRenderer<MunitionsLauncherBlockEntity> {

	public MunitionsLauncherBlockRenderer(BlockEntityRendererProvider.Context context) {
		super();
	}


	@Override
	public void render(MunitionsLauncherBlockEntity blockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light,
					   int overlay) {
		if (blockEntity.getBlockState().getBlock() instanceof MunitionsLauncherBlock block) {
			BlockState loadedBlockState = blockEntity.cannonBehavior().block().state();
			BlockState blockState = blockEntity.getBlockState();
			Direction facing = blockState.getValue(BlockStateProperties.FACING);
			VertexConsumer vcons = buffer.getBuffer(RenderType.solid());
			SuperByteBuffer c = CachedBuffers.block(loadedBlockState);
			c.rotateCentered(Axis.YP.rotationDegrees(facing.getOpposite().getAxis().isVertical() ? 0 : 180)).light(light).renderInto(ms, vcons);
			ms.popPose();
			ms.pushPose();
		}
	}
}

