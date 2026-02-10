package riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher;

import com.simibubi.create.foundation.block.IBE;

import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end.MunitionsLauncherEnd;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end.MunitionsLauncherEndBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.material.MunitionsLauncherMaterial;

public abstract class SolidMunitionsLauncherBlock<TE extends MunitionsLauncherEndBlockEntity> extends MunitionsLauncherBaseBlock implements IBE<TE> {

	public SolidMunitionsLauncherBlock(Properties properties, MunitionsLauncherMaterial material) {
		super(properties, material);
	}

	@Override
	public MunitionsLauncherEnd getDefaultOpeningType() {
		return MunitionsLauncherEnd.CLOSED;
	}
}
