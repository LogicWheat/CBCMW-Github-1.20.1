package riftyboi.cbcmodernwarfare.mixin;

import com.simibubi.create.content.contraptions.Contraption;

import com.simibubi.create.content.contraptions.MountedStorageManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Contraption.class)
public interface CreateContraptionAcessor {
	@Accessor("storage")
	MountedStorageManager getStorage();

	@Invoker("gatherBBsOffThread")
	void callGatherBBsOffThread();
}
