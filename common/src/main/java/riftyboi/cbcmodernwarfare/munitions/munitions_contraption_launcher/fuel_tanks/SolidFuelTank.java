package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.fuel_tanks;

import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionPropertiesHandlers;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.FuelType;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config.FuelTankProperties;

public class SolidFuelTank extends MunitionsLauncherFuelTankBlock {
	public SolidFuelTank(Properties properties) {
		super(properties);
	}

	public FuelTankProperties getProperties() {
		return CBCModernWarfareMunitionPropertiesHandlers.SOLID_FUEL_TANK.getPropertiesOf(this);
	}

	@Override
	public FuelType getFuelType() {
		return FuelType.SOLID_FUEL;
	}
}
