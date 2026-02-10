package riftyboi.cbcmodernwarfare.index;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.material.MediumcannonMaterial;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.material.RotarycannonMaterial;
import riftyboi.cbcmodernwarfare.crafting.casting.CBCModernWarfareCastShapes;


import java.util.*;
import java.util.function.Supplier;

public class CBCModernWarfareBlockPartials {
	private static final Collection<Runnable> DEFERRED_MODEL_CALLBACKS = new ArrayList<>();
	private static final Map<CannonCastShape, PartialModel> CANNON_CAST_BY_SIZE = new HashMap<>();
	private static final Map<MediumcannonMaterial, PartialModel> MEDIUMCANNON_EJECTOR_BY_MATERIAL = new HashMap<>();
	public static final PartialModel
		APDS_SHOT_FLYING = PartialModel.of(CBCModernWarfare.resource("block/apds_shot_flying")),
		CAST_IRON_MEDIUMCANNON_FALLING_BREECH = mediumcannonEjectorPartial(CBCModernWarfareMediumcannonMaterials.CAST_IRON, "cast_iron"),
		BRONZE_MEDIUMCANNON_FALLING_BREECH = mediumcannonEjectorPartial(CBCModernWarfareMediumcannonMaterials.BRONZE, "bronze"),
		STEEL_MEDIUMCANNON_FALLING_BREECH = mediumcannonEjectorPartial(CBCModernWarfareMediumcannonMaterials.STEEL, "steel"),
		BASE = block("smoke_discharger/base"),
		TUBES = block("smoke_discharger/quad_tubes"),
		NETHERSTEEL_MEDIUMCANNON_FALLING_BREECH = mediumcannonEjectorPartial(CBCModernWarfareMediumcannonMaterials.NETHERSTEEL, "nethersteel");

	private static PartialModel block(String path) {
		return PartialModel.of(CBCModernWarfare.resource("block/" + path));
	}

	public static PartialModel mediumcannonEjectorFor(MediumcannonMaterial material) {
		return MEDIUMCANNON_EJECTOR_BY_MATERIAL.getOrDefault(material, CAST_IRON_MEDIUMCANNON_FALLING_BREECH);
	}

	private static PartialModel mediumcannonEjectorPartial(MediumcannonMaterial material, String path) {
		return mediumcannonEjectorPartial(material, CBCModernWarfare.resource("item/" + path + "_mediumcannon_falling_breech"));
	}

	public static PartialModel mediumcannonEjectorPartial(MediumcannonMaterial material, ResourceLocation loc) {
		PartialModel model = PartialModel.of(loc);
		MEDIUMCANNON_EJECTOR_BY_MATERIAL.put(material, model);
		return model;
	}



	private static PartialModel cannonCastPartial(Supplier<CannonCastShape> size, String path) {
		PartialModel model = PartialModel.of(CBCModernWarfare.resource("block/" + path));
		DEFERRED_MODEL_CALLBACKS.add(() -> {
			CANNON_CAST_BY_SIZE.put(size.get(), model);
		});
		return model;
	}

	public static void init() {}

	public static void resolveDeferredModels() {
		for (Runnable run : DEFERRED_MODEL_CALLBACKS) run.run();
		DEFERRED_MODEL_CALLBACKS.clear();
	}

}
