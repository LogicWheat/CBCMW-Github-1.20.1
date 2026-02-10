package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.fuel_tanks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.ContraptionMunitionBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.FuelType;

import java.util.List;

public abstract class MunitionsLauncherFuelTankBlock extends ContraptionMunitionBlock implements IFuelTankBlock {

	public MunitionsLauncherFuelTankBlock(Properties properties) {
		super(properties);
	}

	public int getExpectedSize() { return 1; }

	public boolean isComplete(List<StructureTemplate.StructureBlockInfo> total, Direction dir) {
		return total.size() == this.getExpectedSize();
	}

	public FuelType getType() {
		return FuelType.SOLID_FUEL;
	}
}

