package riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher;

import java.util.List;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import rbasamoyai.createbigcannons.CBCTags;

public class MunitionsLauncherBlockEntity extends SmartBlockEntity implements IMunitionsLauncherBlockEntity {

	private MunitionsLauncherBehavior cannonBehavior;

	public MunitionsLauncherBlockEntity(BlockEntityType<? extends MunitionsLauncherBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviors) {
		super.addBehavioursDeferred(behaviors);
		behaviors.add(this.cannonBehavior = new MunitionsLauncherBehavior(this, this::canLoadBlock));
	}
	@Override
	public MunitionsLauncherBehavior cannonBehavior() {
		return this.cannonBehavior;
	}
}
