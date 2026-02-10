package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.manual;

import java.util.Map;

import javax.annotation.Nullable;

import com.simibubi.create.content.contraptions.OrientedContraptionEntity;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.MunitionsLauncherGuidanceBlock;

public class ManualCommandGuidanceBlock extends MunitionsLauncherGuidanceBlock implements IBE<ManualCommandGuidanceBlockEntity>{

	public ManualCommandGuidanceBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isValidAddition(Map<BlockPos, StructureTemplate.StructureBlockInfo> total, StructureTemplate.StructureBlockInfo data) {
		return true;
	}

	public boolean canFire(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, PitchOrientedContraptionEntity poce, BlockPos currentPos) {
		if (blockEntity instanceof ManualCommandGuidanceBlockEntity guidanceBlockEntity) {
			guidanceBlockEntity.setTrackedContraption(poce);
			return guidanceBlockEntity.hasTrackedContraption();
		}
		return false;
	}

	@Override
	public void tickGuidance(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, OrientedContraptionEntity oce) {
		if (level.isClientSide) return; //
		if (oce instanceof MunitionsPhysicsContraptionEntity mpce && blockEntity instanceof ManualCommandGuidanceBlockEntity guidanceBlockEntity) {
			guidanceBlockEntity.guideEntity(mpce, state);
		}
	}


	@Override
	public void tickFromContraption(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, PitchOrientedContraptionEntity poce) {
		if (blockEntity instanceof ManualCommandGuidanceBlockEntity guidanceBlockEntity) {
			guidanceBlockEntity.tickFromContraption(level,pos,state,poce);
		}
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		ItemStack itemStack = super.getCloneItemStack(level, pos, state);
		if (level.getBlockEntity(pos) instanceof ManualCommandGuidanceBlockEntity be) be.saveToItem(itemStack);
		return itemStack;
	}

	@Override
	public Class<ManualCommandGuidanceBlockEntity> getBlockEntityClass() {
		return ManualCommandGuidanceBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends ManualCommandGuidanceBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.MANUAL_COMMAND_GUIDANCE.get();
	}

}
