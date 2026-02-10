package riftyboi.cbcmodernwarfare.cannons.rotarycannon.bearing;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
public class RotaryCannonBearingVisual extends AbstractBlockEntityVisual<RotarycannonBearingBlockEntity> implements SimpleDynamicVisual {

		private final Map<BlockPos, OrientedInstance> blocks = new HashMap<>();

		private final Direction facing;

		public RotaryCannonBearingVisual(VisualizationContext ctx, RotarycannonBearingBlockEntity blockEntity, float partialTicks) {
			super(ctx, blockEntity, partialTicks);
			this.facing = this.blockState.getValue(BlockStateProperties.FACING);

			this.blocks.clear();
			for (Map.Entry<BlockPos, BlockState> entry : this.blockEntity.toAnimate.entrySet()) {
				if (entry.getValue() == null) continue;
				this.blocks.put(entry.getKey(), instancerProvider().instancer(InstanceTypes.ORIENTED, Models.block(entry.getValue())).createInstance());
			}

			this.updateTransforms(partialTicks);
		}

		@Override
		public void beginFrame(DynamicVisual.Context ctx) {
			this.updateTransforms(ctx.partialTick());
		}

		private void updateTransforms(float partialTicks) {
			Vector3f offs = this.facing.step();

			float rot = this.blockEntity.getRot();
			this.blockEntity.setRotation(rot, partialTicks);
			Quaternionf qrot = new Quaternionf().rotationAxis(this.blockEntity.getRotation(), facing.step().set(this.blockEntity.getBlockPos().getX(),this.blockEntity.getBlockPos().getY(),this.blockEntity.getBlockPos().getZ()));

			for (Map.Entry<BlockPos, OrientedInstance> entry : this.blocks.entrySet()) {
				BlockPos pos1 = entry.getKey();
				entry.getValue().position(offs).translatePosition(pos1.getX(), pos1.getY(), pos1.getZ());
				entry.getValue().rotate(qrot);
			}
		}

		@Override
		public void updateLight(float partialTicks) {
			this.relight(this.pos);
			for (Map.Entry<BlockPos, OrientedInstance> entry : this.blocks.entrySet()) {
				this.relight(this.pos.offset(entry.getKey()), entry.getValue());
			}
		}

		@Override
		protected void _delete() {
			for (OrientedInstance block : this.blocks.values())
				block.delete();
		}



	@Override
	public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {

	}
}
