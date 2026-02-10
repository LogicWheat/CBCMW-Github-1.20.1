package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public interface PropelledContraptionMunitionBlock {

	BlockState onCannonRotate(BlockState oldState, Direction.Axis rotationAxis, Rotation rotation);
	Direction.Axis getAxis(BlockState state);
	boolean canBeLoaded(BlockState state, Direction.Axis facing);
	StructureTemplate.StructureBlockInfo getHandloadingInfo(ItemStack stack, BlockPos localPos, Direction cannonOrientation);
	ItemStack getExtractedItem(StructureTemplate.StructureBlockInfo info);

}
