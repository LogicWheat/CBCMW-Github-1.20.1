package riftyboi.cbcmodernwarfare.cannons.medium_cannon.material;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.util.HashMap;
import java.util.Map;

public record MediumcannonMaterial(ResourceLocation name, MediumcannonMaterialProperties defaultProperties) {

	public MediumcannonMaterialProperties properties() {
		MediumcannonMaterialProperties handlerProperties = MediumcannonMaterialPropertiesHandler.getMaterial(this);
		return handlerProperties == null ? this.defaultProperties : handlerProperties;
	}

    private static final Map<ResourceLocation, MediumcannonMaterial> CANNON_MATERIALS = new HashMap<>();

    public static MediumcannonMaterial register(ResourceLocation loc, MediumcannonMaterialProperties defaultProperties) {
        MediumcannonMaterial material = new MediumcannonMaterial(loc, defaultProperties);
        CANNON_MATERIALS.put(material.name(), material);
        return material;
    }

    public static MediumcannonMaterial fromName(ResourceLocation loc) {
		if (!CANNON_MATERIALS.containsKey(loc)) throw new IllegalArgumentException("No autocannon material '" + loc + "' registered");
        return CANNON_MATERIALS.get(loc);
    }

	public static MediumcannonMaterial fromNameOrNull(ResourceLocation loc) { return CANNON_MATERIALS.get(loc); }

}
