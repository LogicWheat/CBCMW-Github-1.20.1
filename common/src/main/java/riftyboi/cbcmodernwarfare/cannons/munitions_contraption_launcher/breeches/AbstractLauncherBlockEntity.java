package riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.IMunitionsLauncherBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBehavior;

import java.util.List;

public abstract class AbstractLauncherBlockEntity extends KineticBlockEntity implements IBreechBlockEntity {

	protected MunitionsLauncherBehavior cannonBehavior;

	public AbstractLauncherBlockEntity(BlockEntityType<? extends AbstractLauncherBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviors) {
		super.addBehaviours(behaviors);
		behaviors.add(this.cannonBehavior = new MunitionsLauncherBehavior(this, this::canLoadBlock));
	}

	public abstract boolean isOpen();

	@Override
	public boolean canLoadBlock(StructureTemplate.StructureBlockInfo blockInfo) {
		return this.isOpen() && IBreechBlockEntity.super.canLoadBlock(blockInfo);
	}

	@Override
	public MunitionsLauncherBehavior cannonBehavior() {
		return this.cannonBehavior;
	}

}
