package riftyboi.cbcmodernwarfare.cannons.medium_cannon.recoil_barrel;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import net.createmod.catnip.animation.AnimationTickHolder;

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
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.recoil_barrel.MediumcannonRecoilBarrelBlock;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.recoil_barrel.MediumcannonRecoilBarrelBlockEntity;

public class MediumcannonRecoilBarrelVisual extends AbstractBlockEntityVisual<MediumcannonRecoilBarrelBlockEntity> implements SimpleDynamicVisual {

	private final Map<BlockPos, OrientedInstance> blocks = new HashMap<>();

	private final Direction facing;

	public MediumcannonRecoilBarrelVisual(VisualizationContext ctx, MediumcannonRecoilBarrelBlockEntity blockEntity, float partialTicks) {
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
		float scale = this.blockEntity.getAnimateOffset(partialTicks);

		Vector3f offs = this.facing.step();
		offs.mul((1 - scale) * -0.5f);
		offs.add(this.getVisualPosition().getX(), this.getVisualPosition().getY(), this.getVisualPosition().getZ());

		for (Map.Entry<BlockPos, OrientedInstance> entry : this.blocks.entrySet()) {
			BlockPos pos1 = entry.getKey();
			entry.getValue().position(offs).translatePosition(pos1.getX(), pos1.getY(), pos1.getZ()).setChanged();
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
	public void collectCrumblingInstances(Consumer<Instance> consumer) {
	}

}
