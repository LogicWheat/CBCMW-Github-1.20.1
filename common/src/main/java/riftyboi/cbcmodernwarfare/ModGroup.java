package riftyboi.cbcmodernwarfare;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlocks;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareFluids;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareItem;

import java.util.Arrays;
import java.util.function.Supplier;

public class ModGroup {
	public static final ResourceKey<CreativeModeTab> MAIN_TAB_KEY = makeKey("base");

	public static final Supplier<CreativeModeTab> GROUP = wrapGroup("base", () -> createBuilder()
		.title(Component.translatable("itemGroup." + CBCModernWarfare.MOD_ID))
		.icon(CBCModernWarfareBlocks.HEAP_SHELL::asStack)
		.displayItems((param, output) -> {
			output.acceptAll(Arrays.asList(
				CBCModernWarfareBlocks.COMPACT_MOUNT.asStack(),

				CBCModernWarfareBlocks.CAST_IRON_MEDIUMCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.CAST_IRON_MEDIUMCANNON_RECOIL_BARREL.asStack(),
				CBCModernWarfareBlocks.CAST_IRON_MEDIUMCANNON_BARREL.asStack(),

				CBCModernWarfareBlocks.BRONZE_MEDIUMCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.BRONZE_MEDIUMCANNON_RECOIL_BARREL.asStack(),
				CBCModernWarfareBlocks.BRONZE_MEDIUMCANNON_BARREL.asStack(),

				CBCModernWarfareBlocks.STEEL_MEDIUMCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.STEEL_MEDIUMCANNON_RECOIL_BARREL.asStack(),
				CBCModernWarfareBlocks.STEEL_MEDIUMCANNON_BARREL.asStack(),

				CBCModernWarfareBlocks.NETHERSTEEL_MEDIUMCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL.asStack(),
				CBCModernWarfareBlocks.NETHERSTEEL_MEDIUMCANNON_BARREL.asStack(),

				CBCModernWarfareBlocks.ROCKET_RAILS_END.asStack(),
				CBCModernWarfareBlocks.ROCKET_RAILS.asStack(),

				CBCModernWarfareBlocks.GUN_LAUNCHER_SLIDING_BREECH.asStack(),
				CBCModernWarfareBlocks.GUN_LAUNCHER_QUICKFIRING_BREECH.asStack(),
				CBCModernWarfareBlocks.GUN_LAUNCHER_CHAMBER.asStack(),
				CBCModernWarfareBlocks.BUILT_UP_GUN_LAUNCHER_BARREL.asStack(),
				CBCModernWarfareBlocks.GUN_LAUNCHER_BARREL.asStack(),

//				CBCModernWarfareBlocks.TORPEDO_SCREW_BREECH.asStack(),
				CBCModernWarfareBlocks.TORPEDO_TUBE.asStack(),

				CBCModernWarfareBlocks.BRONZE_ROTARYCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.BRONZE_ROTARYCANNON_BEARING.asStack(),
				CBCModernWarfareBlocks.BRONZE_ROTARYCANNON_BARREL.asStack(),

				CBCModernWarfareBlocks.STEEL_ROTARYCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.STEEL_ROTARYCANNON_BEARING.asStack(),
				CBCModernWarfareBlocks.STEEL_ROTARYCANNON_BARREL.asStack(),

				CBCModernWarfareBlocks.NETHERSTEEL_ROTARYCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.NETHERSTEEL_ROTARYCANNON_BEARING.asStack(),
				CBCModernWarfareBlocks.NETHERSTEEL_ROTARYCANNON_BARREL.asStack(),

				CBCModernWarfareBlocks.COMPOSITE_BLOCK.asStack(),

				CBCModernWarfareBlocks.CARBON_STEEL_BLOCK.asStack(),
				CBCModernWarfareBlocks.CARBON_STEEL_SLAB.asStack(),
				CBCModernWarfareBlocks.CARBON_STEEL_STAIRS.asStack(),

				CBCModernWarfareBlocks.ERA_BLOCK.asStack(),
				CBCModernWarfareBlocks.ERA_STAIRS.asStack(),
				CBCModernWarfareBlocks.ERA_SLAB.asStack(),

				CBCModernWarfareBlocks.TAN_ERA_BLOCK.asStack(),
				CBCModernWarfareBlocks.TAN_ERA_STAIRS.asStack(),
				CBCModernWarfareBlocks.TAN_ERA_SLAB.asStack(),

				CBCModernWarfareBlocks.GREEN_ERA_BLOCK.asStack(),
				CBCModernWarfareBlocks.GREEN_ERA_STAIRS.asStack(),
				CBCModernWarfareBlocks.GREEN_ERA_SLAB.asStack(),

				CBCModernWarfareBlocks.INFRARED_SEEKER_GUIDANCE.asStack(),
				CBCModernWarfareBlocks.OPTICAL_COMMAND_GUIDANCE.asStack(),
				CBCModernWarfareBlocks.MANUAL_COMMAND_GUIDANCE.asStack(),

				CBCModernWarfareBlocks.SOLID_FUEL_TANK.asStack(),
				CBCModernWarfareBlocks.SOLID_FUEL_THRUSTER.asStack(),
				CBCModernWarfareBlocks.SOLID_FUEL_SPLIT_THRUSTER.asStack(),

				CBCModernWarfareItem.FILLED_MEDIUMCANNON_CARTRIDGE.asStack(),
				CBCModernWarfareItem.EMPTY_MEDIUMCANNON_CARTRIDGE.asStack(),
				CBCModernWarfareItem.MEDIUMCANNON_CARTRIDGE_SHEET.asStack(),

				CBCModernWarfareItem.AP_MEDIUMCANNON_CARTRIDGE.get().getCreativeTabCartridgeItem(),
				CBCModernWarfareItem.AP_MEDIUMCANNON_ROUND.asStack(),

				CBCModernWarfareItem.APHE_MEDIUMCANNON_CARTRIDGE.get().getCreativeTabCartridgeItem(),
				CBCModernWarfareItem.APHE_MEDIUMCANNON_ROUND.asStack(),

				CBCModernWarfareItem.APDS_MEDIUMCANNON_CARTRIDGE.get().getCreativeTabCartridgeItem(),
				CBCModernWarfareItem.APDS_MEDIUMCANNON_ROUND.asStack(),

				CBCModernWarfareItem.APFSDS_MEDIUMCANNON_CARTRIDGE.get().getCreativeTabCartridgeItem(),
				CBCModernWarfareItem.APFSDS_MEDIUMCANNON_ROUND.asStack(),

				CBCModernWarfareItem.CANISTER_MEDIUMCANNON_CARTRIDGE.get().getCreativeTabCartridgeItem(),
				CBCModernWarfareItem.CANISTER_MEDIUMCANNON_ROUND.asStack(),

				CBCModernWarfareItem.HE_MEDIUMCANNON_CARTRIDGE.get().getCreativeTabCartridgeItem(),
				CBCModernWarfareItem.HE_MEDIUMCANNON_ROUND.asStack(),

				CBCModernWarfareItem.HEF_MEDIUMCANNON_CARTRIDGE.get().getCreativeTabCartridgeItem(),
				CBCModernWarfareItem.HEF_MEDIUMCANNON_ROUND.asStack(),

				CBCModernWarfareItem.HEAP_MEDIUMCANNON_CARTRIDGE.get().getCreativeTabCartridgeItem(),
				CBCModernWarfareItem.HEAP_MEDIUMCANNON_ROUND.asStack(),

				CBCModernWarfareItem.SMOKE_MEDIUMCANNON_CARTRIDGE.get().getCreativeTabCartridgeItem(),
				CBCModernWarfareItem.SMOKE_MEDIUMCANNON_ROUND.asStack(),

				CBCModernWarfareItem.HVAP_AUTOCANNON_ROUND.get().getCreativeTabCartridgeItem(),
				CBCModernWarfareItem.HVAP_AUTOCANNON_ROUND.asStack(),

				CBCModernWarfareItem.APDS_AUTOCANNON_ROUND.get().getCreativeTabCartridgeItem(),
				CBCModernWarfareItem.APDS_AUTOCANNON_ROUND.asStack(),

				CBCModernWarfareItem.CANISTER_AUTOCANNON_ROUND.get().getCreativeTabCartridgeItem(),
				CBCModernWarfareItem.CANISTER_AUTOCANNON_ROUND.asStack(),

				CBCModernWarfareItem.HE_AUTOCANNON_ROUND.get().getCreativeTabCartridgeItem(),
				CBCModernWarfareItem.HE_AUTOCANNON_ROUND.asStack(),

				CBCModernWarfareBlocks.HEAP_SHELL.asStack(),
				CBCModernWarfareBlocks.HEF_SHELL.asStack(),
				CBCModernWarfareBlocks.APDS_SHOT.asStack(),

				CBCModernWarfareItem.INCENDIARY_TIP.asStack(),
				CBCModernWarfareBlocks.AUTOCANNON_AMMO_DRUM.asStack(),

				CBCModernWarfareItem.HEAVY_RECOIL_SPRING.asStack(),
				CBCModernWarfareItem.INERT_EXPLOSIVES.asStack(),
				CBCModernWarfareItem.CARBON_STEEL_INGOT.asStack(),
				CBCModernWarfareItem.CARBON_STEEL_NUGGET.asStack(),
				CBCModernWarfareItem.SILICON_INGOT.asStack(),
				CBCModernWarfareItem.SILICON_NUGGET.asStack(),

				CBCModernWarfareBlocks.MEDIUMCANNON_BARREL_CAST_MOULD.asStack(),
				CBCModernWarfareBlocks.MEDIUMCANNON_RECOIL_BARREL_CAST_MOULD.asStack(),
				CBCModernWarfareBlocks.MEDIUMCANNON_BREECH_CAST_MOULD.asStack(),

				CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_BARREL.asStack(),
				CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_RECOIL_BARREL.asStack(),
				CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_CAST_IRON_MEDIUMCANNON_RECOIL_BARREL.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_CAST_IRON_MEDIUMCANNON_BREECH.asStack(),
				CBCModernWarfareItem.CAST_IRON_MEDIUMCANNON_FALLING_BREECH.asStack(),

				CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_BARREL.asStack(),
				CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_RECOIL_BARREL.asStack(),
				CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_BRONZE_MEDIUMCANNON_RECOIL_BARREL.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_BRONZE_MEDIUMCANNON_BREECH.asStack(),
				CBCModernWarfareItem.BRONZE_MEDIUMCANNON_FALLING_BREECH.asStack(),

				CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_BARREL.asStack(),
				CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_RECOIL_BARREL.asStack(),
				CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_STEEL_MEDIUMCANNON_RECOIL_BARREL.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_STEEL_MEDIUMCANNON_BREECH.asStack(),
				CBCModernWarfareItem.STEEL_MEDIUMCANNON_FALLING_BREECH.asStack(),

				CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_BARREL.asStack(),
				CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL.asStack(),
				CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_NETHERSTEEL_MEDIUMCANNON_BREECH.asStack(),
				CBCModernWarfareItem.NETHERSTEEL_MEDIUMCANNON_FALLING_BREECH.asStack(),

				CBCModernWarfareBlocks.ROTARYCANNON_BARREL_CAST_MOULD.asStack(),
				CBCModernWarfareBlocks.ROTARYCANNON_BEARING_CAST_MOULD.asStack(),
				CBCModernWarfareBlocks.ROTARYCANNON_BREECH_CAST_MOULD.asStack(),

				CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BARREL.asStack(),
				CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BEARING.asStack(),
				CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_BRONZE_ROTARYCANNON_BEARING.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_BRONZE_ROTARYCANNON_BREECH.asStack(),
				CBCModernWarfareItem.BRONZE_ROTARYCANNON_BREECH_EXTRACTOR.asStack(),

				CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BARREL.asStack(),
				CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BEARING.asStack(),
				CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_BRONZE_ROTARYCANNON_BEARING.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_BRONZE_ROTARYCANNON_BREECH.asStack(),
				CBCModernWarfareItem.STEEL_ROTARYCANNON_BREECH_EXTRACTOR.asStack(),

				CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BARREL.asStack(),
				CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BEARING.asStack(),
				CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BREECH.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_BRONZE_ROTARYCANNON_BEARING.asStack(),
				CBCModernWarfareBlocks.INCOMPLETE_BRONZE_ROTARYCANNON_BREECH.asStack(),
				CBCModernWarfareItem.NETHERSTEEL_ROTARYCANNON_BREECH_EXTRACTOR.asStack(),

				new ItemStack(CBCModernWarfareFluids.MOLTEN_CARBON_STEEL.get().getBucket()),
				CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_BARREL.asStack(),
				CBCModernWarfareBlocks.UNBORED_BUILT_UP_GUN_LAUNCHER_BARREL.asStack(),
				CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_CHAMBER.asStack(),
				CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_SLIDING_BREECH.asStack()));
		})
		.build());

	@ExpectPlatform public static Supplier<CreativeModeTab> wrapGroup(String id, Supplier<CreativeModeTab> sup) { throw new AssertionError(); }
	@ExpectPlatform public static CreativeModeTab.Builder createBuilder() { throw new AssertionError(); }

	@ExpectPlatform public static void useModTab(ResourceKey<CreativeModeTab> key) { throw new AssertionError(); }

	public static ResourceKey<CreativeModeTab> makeKey(String id) {
		return ResourceKey.create(Registries.CREATIVE_MODE_TAB, CBCModernWarfare.resource(id));
	}

	public static void register() {
		CBCModernWarfare.REGISTRATE.addRawLang("itemGroup." + CBCModernWarfare.MOD_ID, "CBC Modern Warfare");
	}

}
