package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.infrared_homing;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

public record InfraredSeekerPropertiesComponent(float trackFov, float lockFov, float range ) {

	public static final InfraredSeekerPropertiesComponent DEFAULT = new InfraredSeekerPropertiesComponent(0, 0,0);

	public static InfraredSeekerPropertiesComponent fromJson(String id, JsonObject obj) {
		float trackFov = Math.max(0, GsonHelper.getAsFloat(obj, "trackFov", 1));
		float lockFov = Math.max(0, GsonHelper.getAsFloat(obj, "lockFov", 1));
		float range = Math.max(0, GsonHelper.getAsFloat(obj, "range", 1));
		return new InfraredSeekerPropertiesComponent(trackFov, lockFov , range);
	}

	public static InfraredSeekerPropertiesComponent fromNetwork(FriendlyByteBuf buf) {
		return new InfraredSeekerPropertiesComponent(buf.readFloat(), buf.readFloat(), buf.readFloat());
	}

	public void toNetwork(FriendlyByteBuf buf) {
		buf.writeFloat(this.trackFov)
			.writeFloat(this.lockFov)
			.writeFloat(this.range);
	}

}
