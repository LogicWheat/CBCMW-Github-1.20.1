package riftyboi.cbcmodernwarfare.multiloader.forge;

import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ModelFile;
import rbasamoyai.createbigcannons.CreateBigCannons;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.multiloader.SlidingBreechBlockGen;

public class SlidingBreechBlockGenImpl extends SlidingBreechBlockGen {



	public static SlidingBreechBlockGen create(String pathAndMaterial) {
		return new SlidingBreechBlockGenImpl(pathAndMaterial);
	}

	public SlidingBreechBlockGenImpl(String pathAndMaterial) { super(pathAndMaterial); }

	@Override
	public <T extends Block> ModelFile getModel(DataGenContext<Block, T> context, RegistrateBlockstateProvider provider, BlockState state) {
		boolean axisAlongFirst = state.getValue(DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE);
		boolean rotated = (state.getValue(BlockStateProperties.FACING).getAxis() == Direction.Axis.X) == axisAlongFirst;
		String suf = rotated ? "_rotated" : "";
		ResourceLocation baseLoc = CBCModernWarfare.resource("block/sliding_breech" + suf);

		ResourceLocation holeLoc = CBCModernWarfare.resource("block/" + this.pathAndMaterial + "_sliding_breech_hole");
		ResourceLocation sideLoc = CBCModernWarfare.resource("block/" + this.pathAndMaterial + "_sliding_breech_side");
		ResourceLocation sideHoleLoc = CBCModernWarfare.resource("block/" + this.pathAndMaterial + "_sliding_breech_side_hole");
		ResourceLocation insideLoc = CBCModernWarfare.resource("block/" + this.pathAndMaterial + "_sliding_breech_inside");
		return provider.models().withExistingParent(context.getName() + suf, baseLoc)
			.texture("hole", holeLoc)
			.texture("side", sideLoc)
			.texture("side_hole", sideHoleLoc)
			.texture("inside", insideLoc);
	}

}
