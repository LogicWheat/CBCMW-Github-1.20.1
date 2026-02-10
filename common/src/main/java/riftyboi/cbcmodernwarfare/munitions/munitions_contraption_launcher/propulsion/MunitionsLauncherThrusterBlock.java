package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.propulsion;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.ContraptionMunitionBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.FuelType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class MunitionsLauncherThrusterBlock extends ContraptionMunitionBlock implements IThrusterBlock {

	public MunitionsLauncherThrusterBlock(Properties properties) {
		super(properties);
	}

	public int getExpectedSize() { return 1; }

	public boolean isComplete(Map<BlockPos, StructureTemplate.StructureBlockInfo> total) {
		int count = 0;
		for(Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> s : total.entrySet()) {
			if (s.getValue().state().getBlock() instanceof MunitionsLauncherThrusterBlock) count++;
		}
		return count == this.getExpectedSize();
	}


	public float getVelocityBasedScale(Vec3 velocity) {
		double velocityMagnitude = velocity.length();

		float minScale = 1.0f;
		float maxScale = 8.0f;

		float baselineSpeed = 0.5f;

		if (velocityMagnitude == 0) {
			return maxScale;
		}

		float scale = maxScale - (float)(Math.log(velocityMagnitude / baselineSpeed) * 1.5);

		scale = Math.max(minScale, Math.min(maxScale, scale));

		return scale;
	}

	public boolean isValidAddition(Map<BlockPos, StructureTemplate.StructureBlockInfo> total, StructureTemplate.StructureBlockInfo data) {
		List<BlockPos> posList = new ArrayList<>();
		for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> entry : total.entrySet()) {
			posList.add(entry.getKey());
		}
		return total.get(posList.get(0)) == data;
	}
}
