package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionPropertiesHandlers;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.ContraptionMunitionBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config.GuidanceBlockProperties;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.propulsion.MunitionsLauncherThrusterBlock;

import java.util.List;
import java.util.Map;

public abstract class MunitionsLauncherGuidanceBlock extends ContraptionMunitionBlock implements IGuidanceBlock {
	public MunitionsLauncherGuidanceBlock(Properties properties) {
		super(properties);
	}

	public boolean isComplete(Map<BlockPos, StructureTemplate.StructureBlockInfo> total) {
		int count = 0;
		for(Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> s : total.entrySet()) {
			if (s.getValue().state().getBlock() instanceof MunitionsLauncherThrusterBlock) count++;
		}
		return count == this.getExpectedSize();
	}

	public GuidanceBlockProperties getProperties() {
		return CBCModernWarfareMunitionPropertiesHandlers.STANDARD_GUIDANCE.getPropertiesOf(this);
	}

	@Override
	public BallisticPropertiesComponent getBallistics() {
		return this.getProperties().ballisticPropertiesComponent();
	}
	@Override
	public EntityDamagePropertiesComponent getDamage() {
		return this.getProperties().entityDamagePropertiesComponent();
	}
	@Override
	public float turnRate() {
		return this.getProperties().guidanceBlockProperties().turnRate();
	}
	@Override
	public float addedGravity() {
		return this.getProperties().guidanceBlockProperties().addedGravity();
	}
	@Override
	public float addedSpread() {
		return this.getProperties().guidanceBlockProperties().addedSpread();
	}
	@Override
	public float maxSpeed() {
		return this.getProperties().guidanceBlockProperties().maxSpeed();
	}

	public boolean isValidAddition(Map<BlockPos, StructureTemplate.StructureBlockInfo> total, StructureTemplate.StructureBlockInfo data) {
		return false;
	}
	public int getExpectedSize() { return 1; }

	public boolean isComplete(List<StructureTemplate.StructureBlockInfo> total, Direction dir) {
		return total.size() == this.getExpectedSize();
	}
	@Override
	public StructureTemplate.StructureBlockInfo getHandloadingInfo(ItemStack stack, BlockPos localPos, Direction cannonOrientation) {
		BlockState state = this.defaultBlockState().setValue(FACING, cannonOrientation);
		CompoundTag baseTag = stack.getOrCreateTag();
		CompoundTag blockEntityTag;

		// Retrieve or create the BlockEntityTag
		if (baseTag.contains("BlockEntityTag")) {
			blockEntityTag = baseTag.getCompound("BlockEntityTag").copy();
		} else {
			blockEntityTag = new CompoundTag();
			ResourceLocation resourceLocation = BlockEntityType.getKey(getBlockEntityType());
			blockEntityTag.putString("id",resourceLocation.toString());
			baseTag.put("BlockEntityTag", blockEntityTag);
		}
		// Remove unnecessary positional data
		blockEntityTag.remove("x");
		blockEntityTag.remove("y");
		blockEntityTag.remove("z");
		// Attach the modified BlockEntityTag back to the base tag
		return new StructureTemplate.StructureBlockInfo(localPos, state, blockEntityTag);
	}
	public abstract BlockEntityType<?> getBlockEntityType();
}
