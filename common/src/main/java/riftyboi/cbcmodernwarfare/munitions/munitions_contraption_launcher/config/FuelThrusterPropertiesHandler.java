package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import rbasamoyai.createbigcannons.munitions.config.BlockPropertiesTypeHandler;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config.components.ThrusterPropertiesComponent;


public class FuelThrusterPropertiesHandler extends BlockPropertiesTypeHandler<FuelThrusterProperties> {

	private static final FuelThrusterProperties DEFAULT = new FuelThrusterProperties(ThrusterPropertiesComponent.DEFAULT, BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT);

	@Override
	protected FuelThrusterProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		ThrusterPropertiesComponent propellantProperties = ThrusterPropertiesComponent.fromJson(location.toString(), obj);
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(location.toString(), obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(location.toString(), obj);
		return new FuelThrusterProperties(propellantProperties, ballistics, damage);
	}

	@Override
	protected FuelThrusterProperties readPropertiesFromNetwork(Block block, FriendlyByteBuf buf) {
		ThrusterPropertiesComponent propellantProperties = ThrusterPropertiesComponent.fromNetwork(buf);
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		return new FuelThrusterProperties(propellantProperties, ballistics, damage);
	}

	@Override
	protected void writePropertiesToNetwork(FuelThrusterProperties properties, FriendlyByteBuf buf) {
		properties.thrusterProperties().toNetwork(buf);
		properties.ballisticPropertiesComponent().toNetwork(buf);
		properties.entityDamagePropertiesComponent().toNetwork(buf);
	}

	@Override protected FuelThrusterProperties getNoPropertiesValue() { return DEFAULT; }

}
