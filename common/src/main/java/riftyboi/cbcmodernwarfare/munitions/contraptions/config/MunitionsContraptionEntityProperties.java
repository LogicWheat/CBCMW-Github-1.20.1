package riftyboi.cbcmodernwarfare.munitions.contraptions.config;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

public record MunitionsContraptionEntityProperties(double drag, boolean isQuadraticDrag, float addedWeightPerWarhead) {
	public static final MunitionsContraptionEntityProperties DEFAULT = new MunitionsContraptionEntityProperties(0, false, 0);
	public static MunitionsContraptionEntityProperties fromJson(String id, JsonObject obj) {
		double drag = Math.max(0, GsonHelper.getAsDouble(obj, "drag", 0.0d));
		boolean isQuadraticDrag = GsonHelper.getAsBoolean(obj, "quadratic_drag", false);
		float addedWeightPerWarhead = Math.max(0, GsonHelper.getAsFloat(obj, "added_weight_per_warhead", 0.0f));
		return new MunitionsContraptionEntityProperties(drag, isQuadraticDrag, addedWeightPerWarhead);
	}

	public static MunitionsContraptionEntityProperties fromNetwork(FriendlyByteBuf buf) {
		return new MunitionsContraptionEntityProperties(buf.readDouble(), buf.readBoolean(), buf.readFloat());
	}

	public void toNetwork(FriendlyByteBuf buf) {
		buf.writeDouble(this.drag)
			.writeBoolean(this.isQuadraticDrag)
			.writeFloat(this.addedWeightPerWarhead);
	}

}
