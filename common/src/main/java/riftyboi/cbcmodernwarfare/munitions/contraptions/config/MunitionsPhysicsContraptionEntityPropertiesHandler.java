package riftyboi.cbcmodernwarfare.munitions.contraptions.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.munitions.config.EntityPropertiesTypeHandler;

public class MunitionsPhysicsContraptionEntityPropertiesHandler extends EntityPropertiesTypeHandler<MunitionsContraptionEntityProperties> {

	private static final MunitionsContraptionEntityProperties DEFAULT = new MunitionsContraptionEntityProperties(MunitionsContraptionEntityProperties.DEFAULT.drag(),MunitionsContraptionEntityProperties.DEFAULT.isQuadraticDrag(), MunitionsContraptionEntityProperties.DEFAULT.addedWeightPerWarhead());

	@Override
	protected MunitionsContraptionEntityProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		String id = location.toString();
		MunitionsContraptionEntityProperties ballistics = MunitionsContraptionEntityProperties.fromJson(id, obj);
		return new MunitionsContraptionEntityProperties(ballistics.drag(),ballistics.isQuadraticDrag(), ballistics.addedWeightPerWarhead());
	}

	@Override
	protected MunitionsContraptionEntityProperties readPropertiesFromNetwork(EntityType<?> entityType, FriendlyByteBuf buf) {
		MunitionsContraptionEntityProperties ballistics = MunitionsContraptionEntityProperties.fromNetwork(buf);
		return new MunitionsContraptionEntityProperties(ballistics.drag(),ballistics.isQuadraticDrag(), ballistics.addedWeightPerWarhead());
	}



	@Override
	protected void writePropertiesToNetwork(MunitionsContraptionEntityProperties properties, FriendlyByteBuf buf) {
		properties.toNetwork(buf);
	}

	@Override protected MunitionsContraptionEntityProperties getNoPropertiesValue() { return DEFAULT; }

}
