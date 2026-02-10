package riftyboi.cbcmodernwarfare.mixin;

import com.simibubi.create.AllSoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import  riftyboi.cbcmodernwarfare.index.CBCModernWarfareSoundEvents;

@Mixin(AllSoundEvents.SoundEntryBuilder.class)
public abstract class CBCMWSoundEventsMixin {

	@Inject(method = "build",
		at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"),
		locals = LocalCapture.CAPTURE_FAILHARD,
		cancellable = true,
		remap = false)
	private void cbcmodernwarfare$build(CallbackInfoReturnable<AllSoundEvents.SoundEntry> cir, AllSoundEvents.SoundEntry entry) {
		if (((Object) this) instanceof CBCModernWarfareSoundEvents.CBCModernWarfareSoundEntryBuilder) cir.setReturnValue(entry);
	}

}
