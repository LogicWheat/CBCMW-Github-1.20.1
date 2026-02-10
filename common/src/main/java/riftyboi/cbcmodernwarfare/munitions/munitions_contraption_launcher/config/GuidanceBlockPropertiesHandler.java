package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import rbasamoyai.createbigcannons.munitions.config.BlockPropertiesTypeHandler;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config.components.FuelTankPropertiesComponent;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config.components.GuidanceBlockPropertiesComponent;

public class GuidanceBlockPropertiesHandler extends BlockPropertiesTypeHandler<GuidanceBlockProperties> {

	private static final GuidanceBlockProperties DEFAULT = new GuidanceBlockProperties(GuidanceBlockPropertiesComponent.DEFAULT, BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT);

	@Override
	protected GuidanceBlockProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		GuidanceBlockPropertiesComponent guidanceProperties = GuidanceBlockPropertiesComponent.fromJson(location.toString(), obj);
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(location.toString(), obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(location.toString(), obj);
		return new GuidanceBlockProperties(guidanceProperties, ballistics, damage);
	}

	@Override
	protected GuidanceBlockProperties readPropertiesFromNetwork(Block block, FriendlyByteBuf buf) {
		GuidanceBlockPropertiesComponent guidanceProperties = GuidanceBlockPropertiesComponent.fromNetwork(buf);
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		return new GuidanceBlockProperties(guidanceProperties, ballistics, damage);
	}

	@Override
	protected void writePropertiesToNetwork(GuidanceBlockProperties properties, FriendlyByteBuf buf) {
		properties.guidanceBlockProperties().toNetwork(buf);
		properties.ballisticPropertiesComponent().toNetwork(buf);
		properties.entityDamagePropertiesComponent().toNetwork(buf);
	}

	@Override
	protected GuidanceBlockProperties getNoPropertiesValue() {
		return DEFAULT;
	}
}
