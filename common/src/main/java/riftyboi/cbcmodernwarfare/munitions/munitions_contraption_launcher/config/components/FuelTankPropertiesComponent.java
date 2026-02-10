package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config.components;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

public record FuelTankPropertiesComponent(int storedFuel, float addedGravity,float addedSpread) {

	public static final FuelTankPropertiesComponent DEFAULT = new FuelTankPropertiesComponent(0, 0, 0);

	public static FuelTankPropertiesComponent fromJson(String id, JsonObject obj) {
		int fuel = Math.max(0, GsonHelper.getAsInt(obj, "storedFuel", 1));
		float gravity = Math.max(0, GsonHelper.getAsFloat(obj, "added_gravity", 1));
		float spread = Math.max(0, GsonHelper.getAsFloat(obj, "added_spread", 1));
		return new FuelTankPropertiesComponent(fuel, gravity, spread);
	}

	public static FuelTankPropertiesComponent fromNetwork(FriendlyByteBuf buf) {
		return new FuelTankPropertiesComponent(buf.readInt(), buf.readFloat(), buf.readFloat());
	}

	public void toNetwork(FriendlyByteBuf buf) {
		buf.writeInt(this.storedFuel)
			.writeFloat(this.addedGravity)
			.writeFloat(this.addedSpread);
	}

}
