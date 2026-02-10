package riftyboi.cbcmodernwarfare.munitions.big_cannon.heap_shell;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.EntityPropertiesTypeHandler;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.ExplosionPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.fragment_burst.ProjectileBurstParentPropertiesComponent;

public class HEAPShellPropertiesHandler extends EntityPropertiesTypeHandler<HEAPShellProperties> {

	private static final HEAPShellProperties DEFAULT = new HEAPShellProperties(BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT, BigCannonProjectilePropertiesComponent.DEFAULT, BigCannonFuzePropertiesComponent.DEFAULT,
		ExplosionPropertiesComponent.DEFAULT, ProjectileBurstParentPropertiesComponent.DEFAULT);

	@Override
	protected HEAPShellProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		String id = location.toString();
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(id, obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(id, obj);
		BigCannonProjectilePropertiesComponent bigCannonProperties = BigCannonProjectilePropertiesComponent.fromJson(id, obj);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromJson(id, obj);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromJson(id, obj);
		ProjectileBurstParentPropertiesComponent shrapnelBurst = ProjectileBurstParentPropertiesComponent.fromJson(id, "jet_", obj);
		return new HEAPShellProperties(ballistics, damage, bigCannonProperties, fuze, explosion, shrapnelBurst);
	}

	@Override
	protected HEAPShellProperties readPropertiesFromNetwork(EntityType<?> entityType, FriendlyByteBuf buf) {
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		BigCannonProjectilePropertiesComponent bigCannonProperties = BigCannonProjectilePropertiesComponent.fromNetwork(buf);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromNetwork(buf);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromNetwork(buf);
		ProjectileBurstParentPropertiesComponent shrapnelBurst = ProjectileBurstParentPropertiesComponent.fromNetwork(buf);
		return new HEAPShellProperties(ballistics, damage, bigCannonProperties, fuze, explosion, shrapnelBurst);
	}

	@Override
	protected void writePropertiesToNetwork(HEAPShellProperties properties, FriendlyByteBuf buf) {
		properties.ballistics().toNetwork(buf);
		properties.damage().toNetwork(buf);
		properties.bigCannonProperties().toNetwork(buf);
		properties.fuze().toNetwork(buf);
		properties.explosion().toNetwork(buf);
		properties.shrapnelBurst().toNetwork(buf);
	}

	@Override protected HEAPShellProperties getNoPropertiesValue() { return DEFAULT; }

}
