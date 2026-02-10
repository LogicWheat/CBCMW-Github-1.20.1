package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.infrared_homing;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import rbasamoyai.createbigcannons.munitions.config.BlockPropertiesTypeHandler;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config.components.GuidanceBlockPropertiesComponent;

public class InfraredSeekerPropertiesHandler extends BlockPropertiesTypeHandler<InfraredSeekerProperties> {

	private static final InfraredSeekerProperties DEFAULT = new InfraredSeekerProperties(InfraredSeekerPropertiesComponent.DEFAULT ,GuidanceBlockPropertiesComponent.DEFAULT, BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT);

	@Override
	protected InfraredSeekerProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		InfraredSeekerPropertiesComponent infraredProperties = InfraredSeekerPropertiesComponent.fromJson(location.toString(), obj);
		GuidanceBlockPropertiesComponent guidanceProperties = GuidanceBlockPropertiesComponent.fromJson(location.toString(), obj);
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(location.toString(), obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(location.toString(), obj);
		return new InfraredSeekerProperties(infraredProperties, guidanceProperties, ballistics, damage);
	}

	@Override
	protected InfraredSeekerProperties readPropertiesFromNetwork(Block block, FriendlyByteBuf buf) {
		InfraredSeekerPropertiesComponent infraredProperties = InfraredSeekerPropertiesComponent.fromNetwork(buf);
		GuidanceBlockPropertiesComponent guidanceProperties = GuidanceBlockPropertiesComponent.fromNetwork(buf);
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		return new InfraredSeekerProperties(infraredProperties, guidanceProperties, ballistics, damage);
	}

	@Override
	protected void writePropertiesToNetwork(InfraredSeekerProperties properties, FriendlyByteBuf buf) {
		properties.infraredProperties().toNetwork(buf);
		properties.guidanceBlockProperties().toNetwork(buf);
		properties.ballisticPropertiesComponent().toNetwork(buf);
		properties.entityDamagePropertiesComponent().toNetwork(buf);
	}

	@Override
	protected InfraredSeekerProperties getNoPropertiesValue() {
		return DEFAULT;
	}
}
