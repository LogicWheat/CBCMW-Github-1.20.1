package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance;

import com.simibubi.create.content.contraptions.OrientedContraptionEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.PropelledContraptionMunitionBlock;

import javax.annotation.Nullable;

public interface IGuidanceBlock extends PropelledContraptionMunitionBlock {
	BallisticPropertiesComponent getBallistics();
	EntityDamagePropertiesComponent getDamage();
	float addedGravity();
	float maxSpeed();
	float turnRate();
	float addedSpread();
	void tickFromContraption(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, PitchOrientedContraptionEntity oce);
	public boolean canFire(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, PitchOrientedContraptionEntity poce, BlockPos currentPos); //checks if it can fire when on a cannon called by cannon itself
	void tickGuidance(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, OrientedContraptionEntity oce); //logic for the aiming and called by muntion contraption
}

