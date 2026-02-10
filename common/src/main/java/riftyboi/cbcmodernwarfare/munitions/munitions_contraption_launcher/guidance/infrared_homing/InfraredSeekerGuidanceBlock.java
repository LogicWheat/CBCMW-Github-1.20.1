package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.infrared_homing;

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
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionPropertiesHandlers;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.MunitionsLauncherGuidanceBlock;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InfraredSeekerGuidanceBlock extends MunitionsLauncherGuidanceBlock implements IBE<InfraredSeekerGuidanceBlockEntity> {
	public InfraredSeekerGuidanceBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isValidAddition(Map<BlockPos, StructureTemplate.StructureBlockInfo> total, StructureTemplate.StructureBlockInfo data) {
		List<BlockPos> posList = new ArrayList<>();
		for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> entry : total.entrySet()) {
			posList.add(entry.getKey());
		}
		return total.get(posList.get(posList.size() - 1)) == data;
	}


	@Override
	public boolean canFire(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, PitchOrientedContraptionEntity poce, BlockPos currentPos) {
		if (blockEntity instanceof InfraredSeekerGuidanceBlockEntity seeker) {
			seeker.setCurrentPos(currentPos);
			tickGuidance(level, pos, state, blockEntity, poce);
			return seeker.isLockedOn();
		}
		return false;
	}

	@Override
	public void tickGuidance(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, OrientedContraptionEntity oce) {
		if (blockEntity instanceof InfraredSeekerGuidanceBlockEntity infrared) {
			if (oce instanceof MunitionsPhysicsContraptionEntity mpce) infrared.guideEntity(mpce, state);
			infrared.tickGuidance(level, pos, state, blockEntity, oce);
		}
	}
	@Override
	public void tickFromContraption(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, @Nullable PitchOrientedContraptionEntity poce) {
		if (poce != null && blockEntity instanceof InfraredSeekerGuidanceBlockEntity guidanceBlockEntity) {
			guidanceBlockEntity.tickFromContraption(level,pos,state,poce);
		}
	}



	public InfraredSeekerProperties getInfraredProperties() {
		return CBCModernWarfareMunitionPropertiesHandlers.INFRARED_SEEKER.getPropertiesOf(this);
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		ItemStack itemStack = super.getCloneItemStack(level, pos, state);
		if (level.getBlockEntity(pos) instanceof InfraredSeekerGuidanceBlockEntity be) be.saveToItem(itemStack);
		return itemStack;
	}


	@Override
	public BallisticPropertiesComponent getBallistics() {
		return this.getInfraredProperties().ballisticPropertiesComponent();
	}
	@Override
	public EntityDamagePropertiesComponent getDamage() {
		return this.getInfraredProperties().entityDamagePropertiesComponent();
	}
	@Override
	public float turnRate() {
		return this.getInfraredProperties().guidanceBlockProperties().turnRate();
	}
	@Override
	public float addedGravity() {
		return this.getInfraredProperties().guidanceBlockProperties().addedGravity();
	}
	@Override
	public float addedSpread() {
		return this.getInfraredProperties().guidanceBlockProperties().addedSpread();
	}
	@Override
	public float maxSpeed() {
		return this.getInfraredProperties().guidanceBlockProperties().maxSpeed();
	}

	public double trackFov() {
		return this.getInfraredProperties().infraredProperties().trackFov();
	}
	public double lockFov() {
		return this.getInfraredProperties().infraredProperties().lockFov();
	}
	public double getRange() {
		return this.getInfraredProperties().infraredProperties().range();
	}

	@Override
	public Class<InfraredSeekerGuidanceBlockEntity> getBlockEntityClass() {
		return InfraredSeekerGuidanceBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends InfraredSeekerGuidanceBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.INFRARED_SEEKER_GUIDANCE.get();
	}

}
