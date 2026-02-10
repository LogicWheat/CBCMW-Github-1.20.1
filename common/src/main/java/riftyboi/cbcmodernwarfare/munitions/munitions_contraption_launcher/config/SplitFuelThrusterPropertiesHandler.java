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


public class SplitFuelThrusterPropertiesHandler extends BlockPropertiesTypeHandler<SplitFuelThrusterProperties> {

	private static final SplitFuelThrusterProperties DEFAULT = new SplitFuelThrusterProperties(ThrusterPropertiesComponent.DEFAULT, BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT);

	@Override
	protected SplitFuelThrusterProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		ThrusterPropertiesComponent propellantProperties = ThrusterPropertiesComponent.fromJson(location.toString(), obj);
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(location.toString(), obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(location.toString(), obj);
		return new SplitFuelThrusterProperties(propellantProperties, ballistics, damage);
	}

	@Override
	protected SplitFuelThrusterProperties readPropertiesFromNetwork(Block block, FriendlyByteBuf buf) {
		ThrusterPropertiesComponent propellantProperties = ThrusterPropertiesComponent.fromNetwork(buf);
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		return new SplitFuelThrusterProperties(propellantProperties, ballistics, damage);
	}

	@Override
	protected void writePropertiesToNetwork(SplitFuelThrusterProperties properties, FriendlyByteBuf buf) {
		properties.thrusterProperties().toNetwork(buf);
		properties.ballisticPropertiesComponent().toNetwork(buf);
		properties.entityDamagePropertiesComponent().toNetwork(buf);
	}

	@Override protected SplitFuelThrusterProperties getNoPropertiesValue() { return DEFAULT; }

}
