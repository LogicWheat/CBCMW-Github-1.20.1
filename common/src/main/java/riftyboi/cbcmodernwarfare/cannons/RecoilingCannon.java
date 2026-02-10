package riftyboi.cbcmodernwarfare.cannons;

import net.minecraft.world.phys.Vec3;
import riftyboi.cbcmodernwarfare.network.RecoilingCannonUpdatePacket;

public interface RecoilingCannon {
	void recoil(Vec3 v);
	void handlePacket(RecoilingCannonUpdatePacket p);
}
