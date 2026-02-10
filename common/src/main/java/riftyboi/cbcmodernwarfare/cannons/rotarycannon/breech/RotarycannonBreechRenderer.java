package riftyboi.cbcmodernwarfare.cannons.rotarycannon.breech;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import dev.engine_room.flywheel.api.visualization.VisualizationManager;

import net.createmod.catnip.render.CachedBuffers;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.munitions.autocannon.ammo_container.AutocannonAmmoContainerBlock;
import rbasamoyai.createbigcannons.munitions.autocannon.ammo_container.AutocannonAmmoContainerItem;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBlock;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockPartials;

public class RotarycannonBreechRenderer extends SmartBlockEntityRenderer<AbstractRotarycannonBreechBlockEntity> {

	public RotarycannonBreechRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(AbstractRotarycannonBreechBlockEntity breech, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(breech, partialTicks, ms, buffer, light, overlay);
		if (VisualizationManager.supportsVisualization(breech.getLevel())) return;


		BlockState state = breech.getBlockState();
		Direction facing = state.getValue(RotarycannonBreechBlock.FACING);

		ms.pushPose();

		Vector3f normal = facing.step();
		normal.mul(breech.getAnimateOffset(partialTicks) * -0.5f);

		ItemStack container = breech.getMagazine();
		if (container.getItem() instanceof AutocannonAmmoContainerItem) {
			boolean flag = facing.getAxis().isVertical();
			Quaternionf q1;
			if (flag) {
				float f = facing == Direction.UP ? 90 : -90;
				q1 = Axis.ZP.rotationDegrees(f);
				q1.mul(Axis.XP.rotationDegrees(f));
			} else {
				q1 = Axis.YP.rotationDegrees(-90 - facing.toYRot());
			}
			Direction offset = flag
				? facing.getCounterClockWise(Direction.Axis.Z)
				: facing.getClockWise(Direction.Axis.Y);
			Vector3f normal1 = facing == Direction.UP ? offset.getOpposite().step() : offset.step();
			normal1.mul(10 / 16f);

			CachedBuffers.block(getAmmoContainerModel(container))
				.translate(normal1)
				.rotateCentered(q1)
				.light(light)
				.renderInto(ms, buffer.getBuffer(RenderType.cutoutMipped()));
		}

		ms.popPose();
	}

	private static BlockState getAmmoContainerModel(ItemStack stack) {
		BlockState state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
		if (state.hasProperty(AutocannonAmmoContainerBlock.CONTAINER_STATE)) {
			state = state.setValue(AutocannonAmmoContainerBlock.CONTAINER_STATE,
				AutocannonAmmoContainerBlock.State.getFromFilled(AutocannonAmmoContainerItem.getTotalAmmoCount(stack) > 0));
		}
		return state;
	}

}
