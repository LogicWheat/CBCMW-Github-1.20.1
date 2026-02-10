package riftyboi.cbcmodernwarfare.forge;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.base.CBCRegistries;
import rbasamoyai.createbigcannons.crafting.BlockRecipeSerializer;
import rbasamoyai.createbigcannons.crafting.BlockRecipeType;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import riftyboi.cbcmodernwarfare.cannon_control.config.MWCannonMountPropertiesSerializers;
import riftyboi.cbcmodernwarfare.crafting.casting.CBCModernWarfareCastShapes;
import riftyboi.cbcmodernwarfare.forge.CBCMWCommonForgeEvents;
import rbasamoyai.createbigcannons.forge.CBCForgeRegisterEvent;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareArmInteractionPointTypes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareContraptionTypes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareSoundEvents;

@Mod(CBCModernWarfare.MOD_ID)
public class CBCModernWarfareForge {



	public static final DeferredRegister<ParticleType<?>> PARTICLE_REGISTER = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, CBCModernWarfare.MOD_ID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CBCModernWarfare.MOD_ID);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, CBCModernWarfare.MOD_ID);
    public CBCModernWarfareForge() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

		RECIPE_SERIALIZER_REGISTER.register(modEventBus);
		RECIPE_TYPE_REGISTER.register(modEventBus);

		CBCModernWarfare.REGISTRATE.registerEventListeners(modEventBus);
		CBCModernWarfare.init();
		ModGroupImpl.registerForge(modEventBus);

		modEventBus.addListener(this::onCommonSetup);
		modEventBus.addListener(this::onRegisterSounds);
		modEventBus.addListener(this::onRegister);

		CBCMWCommonForgeEvents.register(forgeEventBus);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CBCModernWarfareClientForge.prepareClient(modEventBus, forgeEventBus));

	}

	private void onRegister(RegisterEvent evt) {
		ResourceKey<? extends Registry<?>> key = evt.getRegistryKey();
		if (CBCRegistries.BLOCK_RECIPE_SERIALIZERS.equals(key)) {
			BlockRecipeSerializer.register();
		} else if (CBCRegistries.BLOCK_RECIPE_TYPES.equals(key)) {
			BlockRecipeType.register();
		} else if (CBCRegistries.CANNON_CAST_SHAPES.equals(key)) {
			CBCModernWarfareCastShapes.register();
		}
		CBCModernWarfareArmInteractionPointTypes.init();
		CBCModernWarfareContraptionTypes.init();
		FMLJavaModLoadingContext.get().getModEventBus().post(new CBCForgeRegisterEvent<>(CannonCastShape.class, CBCRegistries.cannonCastShapes()));
	}

	private void onRegisterSounds(RegisterEvent event) {
		event.register(Registries.SOUND_EVENT, helper -> CBCModernWarfareSoundEvents.register(soundEntry -> soundEntry.register(helper)));
	}
	private void onCommonSetup(FMLCommonSetupEvent event) {
		MWCannonMountPropertiesSerializers.init();
	}
}
