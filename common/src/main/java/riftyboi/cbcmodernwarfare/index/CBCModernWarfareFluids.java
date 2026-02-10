package riftyboi.cbcmodernwarfare.index;

import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.nullness.NonNullFunction;

import io.github.fabricators_of_create.porting_lib.models.generators.item.ItemModelBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import rbasamoyai.createbigcannons.CBCTags;
import rbasamoyai.createbigcannons.crafting.foundry.MoltenMetalLiquidBlock;
import rbasamoyai.createbigcannons.index.fluid_utils.CBCFlowingFluid;
import rbasamoyai.createbigcannons.index.fluid_utils.FluidBuilder;
import rbasamoyai.createbigcannons.multiloader.IndexPlatform;
import rbasamoyai.createbigcannons.utils.CBCRegistryUtils;
import rbasamoyai.createbigcannons.utils.CBCUtils;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.datagen.assets.CBCModernWarfareBuilderTransformers;

import static riftyboi.cbcmodernwarfare.CBCModernWarfare.REGISTRATE;

public class CBCModernWarfareFluids {


		public static final RegistryEntry<CBCFlowingFluid.Flowing> MOLTEN_CARBON_STEEL =
			standardFluid("molten_carbon_steel")
				.lang("Molten Carbon Steel")
				.tag(forgeTag("molten_carbon_steel"))
				.tag(fabricTag("molten_carbon_steel"))
				.tag(CBCTags.CBCFluidTags.MOLTEN_METAL)
//			.attributes(b -> b.viscosity(1250)
//					.density(8770)
//					.temperature(920))
				.properties(p -> p.levelDecreasePerBlock(2)
					.tickRate(25)
					.flowSpeed(3)
					.blastResistance(100f))
				.block(MoltenMetalLiquidBlock::new).build()
				.transform(IndexPlatform::doFluidBuilderTransforms)
				.register();


	public static void register() {}

	private static <T extends CBCFlowingFluid, P> FluidBuilder<T, P> createFluid(String name, NonNullFunction<CBCFlowingFluid.Properties, T> fac) {
		ResourceLocation stillTex = CBCModernWarfare.resource("fluid/" + name + "_still");
		ResourceLocation flowingTex = CBCModernWarfare.resource("fluid/" + name + "_flow");
		return REGISTRATE.entry(name, cb -> FluidBuilder.create(REGISTRATE, regSelf(REGISTRATE), name, cb, stillTex, flowingTex, fac));
	}

	private static <P> FluidBuilder<CBCFlowingFluid.Flowing, P> standardFluid(String name) {
		return createFluid(name, CBCFlowingFluid.Flowing::new);
	}

	@SuppressWarnings("unchecked")
	private static <S> S regSelf(AbstractRegistrate<?> reg) { return (S) reg; }

	private static TagKey<Fluid> forgeTag(String path) {
		return TagKey.create(CBCRegistryUtils.getFluidRegistryKey(), CBCUtils.location("forge", path));
	}

	private static TagKey<Fluid> fabricTag(String path) {
		return TagKey.create(CBCRegistryUtils.getFluidRegistryKey(), CBCUtils.location("c", path));
	}

}
