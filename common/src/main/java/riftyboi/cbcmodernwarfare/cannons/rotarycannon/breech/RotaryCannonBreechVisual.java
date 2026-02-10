package riftyboi.cbcmodernwarfare.cannons.rotarycannon.breech;

import java.util.function.Consumer;

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
import rbasamoyai.createbigcannons.cannons.autocannon.AutocannonBlock;
import rbasamoyai.createbigcannons.index.CBCBlockPartials;
import rbasamoyai.createbigcannons.munitions.autocannon.ammo_container.AutocannonAmmoContainerBlock;
import rbasamoyai.createbigcannons.munitions.autocannon.ammo_container.AutocannonAmmoContainerItem;

public class RotaryCannonBreechVisual extends AbstractBlockEntityVisual<AbstractRotarycannonBreechBlockEntity> implements SimpleDynamicVisual {

	private final OrientedInstance ammoContainer;
	private final Direction facing;
	private boolean isFilled = false;
	private Item magazineItem = null;

	public RotaryCannonBreechVisual(VisualizationContext ctx, AbstractRotarycannonBreechBlockEntity blockEntity, float partialTick) {
		super(ctx, blockEntity, partialTick);

		this.facing = this.blockState.getValue(BlockStateProperties.FACING);
		Quaternionf q = Axis.YP.rotationDegrees(this.facing.getAxis().isVertical() ? 180 : 0);


		this.ammoContainer = this.containerInstancer().createInstance();
		boolean flag = this.facing.getAxis().isVertical();
		Quaternionf q1;
		if (flag) {
			float f = this.facing == Direction.UP ? 90 : -90;
			q1 = Axis.ZP.rotationDegrees(f);
			q1.mul(Axis.XP.rotationDegrees(f));
		} else {
			q1 = Axis.YP.rotationDegrees(-90 - this.facing.toYRot());
		}
		Direction offset = flag
			? this.facing.getCounterClockWise(Direction.Axis.Z)
			: this.facing.getClockWise(Direction.Axis.Y);
		Vector3f normal = this.facing == Direction.UP ? offset.getOpposite().step() : offset.step();
		normal.mul(10 / 16f);
		this.ammoContainer.rotation(q1).position(this.getVisualPosition()).translatePosition(normal.x(), normal.y(), normal.z());
		this.isFilled = this.isFilled();
		this.magazineItem = this.getMagazineItem();

		this.updateTransforms(partialTick);
	}

	protected void refreshContainer() {
		this.isFilled = this.isFilled();
		this.magazineItem = this.getMagazineItem();
		this.containerInstancer().stealInstance(this.ammoContainer);
	}

	protected Instancer<OrientedInstance> containerInstancer() {
		return this.instancerProvider().instancer(InstanceTypes.ORIENTED, Models.block(this.getAmmoContainerModel()));
	}

	@Override
	public void beginFrame(DynamicVisual.Context ctx) {
		this.updateTransforms(ctx.partialTick());
	}

	private void updateTransforms(float partialTick) {
		this.ammoContainer.setVisible(this.getMagazineItem() instanceof AutocannonAmmoContainerItem);

		if (this.isFilled != this.isFilled() || this.magazineItem != this.getMagazineItem())
			this.refreshContainer();

		this.ammoContainer.setChanged();
	}

	@Override
	public void updateLight(float partialTick) {
		this.relight(this.pos, this.ammoContainer);
	}

	@Override
	protected void _delete() {
		this.ammoContainer.delete();
	}

	@Override
	public void collectCrumblingInstances(Consumer<Instance> consumer) {
		consumer.accept(ammoContainer);
	}

	private PartialModel getPartialModelForState() {
		return this.blockState.getBlock() instanceof AutocannonBlock cBlock
			? CBCBlockPartials.autocannonEjectorFor(cBlock.getAutocannonMaterial())
			: CBCBlockPartials.CAST_IRON_AUTOCANNON_EJECTOR;
	}

	private BlockState getAmmoContainerModel() {
		ItemStack item = this.blockEntity.getMagazine();
		if (item == null || item.isEmpty() || !(item.getItem() instanceof AutocannonAmmoContainerItem blockItem))
			return Blocks.AIR.defaultBlockState();
		BlockState state = blockItem.getBlock().defaultBlockState();
		if (state.hasProperty(AutocannonAmmoContainerBlock.CONTAINER_STATE)) {
			state = state.setValue(AutocannonAmmoContainerBlock.CONTAINER_STATE,
				AutocannonAmmoContainerBlock.State.getFromFilled(AutocannonAmmoContainerItem.getTotalAmmoCount(item) > 0));
		}
		return state;
	}

	private boolean isFilled() {
		return AutocannonAmmoContainerItem.getTotalAmmoCount(this.blockEntity.getMagazine()) > 0;
	}

	private Item getMagazineItem() {
		ItemStack stack = this.blockEntity.getMagazine();
		return stack == null || stack.isEmpty() ? null : stack.getItem();
	}
}
