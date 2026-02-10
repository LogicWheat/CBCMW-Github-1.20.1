package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.propulsion;

import net.minecraft.core.BlockPos;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraption;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.FuelType;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.PropelledContraptionMunitionBlock;

public interface IThrusterBlock extends PropelledContraptionMunitionBlock {
	void spawnParticles(BlockPos pos, MunitionsPhysicsContraptionEntity contraptionEntity);
	BallisticPropertiesComponent getBallistics();
	EntityDamagePropertiesComponent getDamage();
	float addedGravity();
	int storedFuel();
	float addedSpread();
	float burnRate();
	float thrust();
	FuelType getFuelType();
}
