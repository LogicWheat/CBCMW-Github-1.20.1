package riftyboi.cbcmodernwarfare.forge.cannons;

import dev.engine_room.flywheel.lib.visualization.VisualizationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.items.IItemHandler;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.breech.AbstractRotarycannonBreechBlockEntity;

public class RotarycannonBreechBlockEntity extends AbstractRotarycannonBreechBlockEntity {

	private IItemHandler inventory;


	public RotarycannonBreechBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public IItemHandler createItemHandler() {
		return this.inventory == null ? this.inventory = new RotarycannonBreechInterface(this) : this.inventory;
	}

	@Override
	public void requestModelDataUpdate() {
		super.requestModelDataUpdate();
		if (!this.remove)
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> VisualizationHelper.queueUpdate(this));
	}

	@Override
	protected void read(CompoundTag tag, boolean clientPacket) {
		super.read(tag, clientPacket);
		if (clientPacket && !this.isVirtual()) this.requestModelDataUpdate();
	}

}
