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

public class FuelTankPropertiesHandler extends BlockPropertiesTypeHandler<FuelTankProperties> {

	private static final FuelTankProperties DEFAULT = new FuelTankProperties(FuelTankPropertiesComponent.DEFAULT, BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT);

	@Override
	protected FuelTankProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		FuelTankPropertiesComponent propellantProperties = FuelTankPropertiesComponent.fromJson(location.toString(), obj);
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(location.toString(), obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(location.toString(), obj);
		return new FuelTankProperties(propellantProperties, ballistics, damage);
	}

	@Override
	protected FuelTankProperties readPropertiesFromNetwork(Block block, FriendlyByteBuf buf) {
		FuelTankPropertiesComponent propellantProperties = FuelTankPropertiesComponent.fromNetwork(buf);
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		return new FuelTankProperties(propellantProperties, ballistics, damage);
	}

	@Override
	protected void writePropertiesToNetwork(FuelTankProperties properties, FriendlyByteBuf buf) {
		properties.fuelTankProperties().toNetwork(buf);
		properties.ballisticPropertiesComponent().toNetwork(buf);
		properties.entityDamagePropertiesComponent().toNetwork(buf);
	}

	@Override
	protected FuelTankProperties getNoPropertiesValue() {
		return DEFAULT;
	}
}
