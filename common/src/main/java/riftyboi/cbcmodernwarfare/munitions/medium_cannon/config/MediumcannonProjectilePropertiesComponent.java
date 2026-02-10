package riftyboi.cbcmodernwarfare.munitions.medium_cannon.config;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

public record MediumcannonProjectilePropertiesComponent(double addedRecoil, boolean canSquib) {

	public static final MediumcannonProjectilePropertiesComponent DEFAULT = new MediumcannonProjectilePropertiesComponent(0, false);

	public static MediumcannonProjectilePropertiesComponent fromJson(String id, JsonObject obj) {
		double addedRecoil = Math.max(0, GsonHelper.getAsDouble(obj, "added_recoil", 1));
		boolean canSquib = GsonHelper.getAsBoolean(obj, "can_squib", true);
		return new MediumcannonProjectilePropertiesComponent(addedRecoil, canSquib);
	}

	public static MediumcannonProjectilePropertiesComponent fromNetwork(FriendlyByteBuf buf) {
		return new MediumcannonProjectilePropertiesComponent(buf.readDouble(), buf.readBoolean());
	}

	public void toNetwork(FriendlyByteBuf buf) {
		buf.writeDouble(this.addedRecoil)
			.writeBoolean(this.canSquib);
	}

}
