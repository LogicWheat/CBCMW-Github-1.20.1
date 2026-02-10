package riftyboi.cbcmodernwarfare.index;

import static riftyboi.cbcmodernwarfare.CBCModernWarfare.REGISTRATE;

import com.simibubi.create.AllCreativeModeTabs;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import rbasamoyai.createbigcannons.CBCTags;
import rbasamoyai.createbigcannons.datagen.assets.CBCBuilderTransformers;
import rbasamoyai.createbigcannons.utils.CBCRegistryUtils;
import rbasamoyai.createbigcannons.utils.CBCUtils;
import riftyboi.cbcmodernwarfare.CBCModernWarfareTags;
import riftyboi.cbcmodernwarfare.ModGroup;
import riftyboi.cbcmodernwarfare.datagen.assets.CBCModernWarfareBuilderTransformers;
import riftyboi.cbcmodernwarfare.munitions.autocannon.apds.APDSAutocannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.autocannon.apds.APDSAutocannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.autocannon.canister.CanisterAutocannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.autocannon.he.ExplosiveAutocannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.autocannon.hvap.HVAPAutocannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.ap.APMediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.ap.APMediumcannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.apds.APDSMediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.apds.APDSMediumcannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.apfsds.APFSDSMediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.apfsds.APFSDSMediumcannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.aphe.APHEMediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.aphe.APHEMediumcannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.canister.CanisterMediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.canister.CanisterMediumcannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.he.HEMediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.he.HEMediumcannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.heap.HEAPMediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.heap.HEAPMediumcannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.hefrag.HEFMediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.hefrag.HEFMediumcannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.smoke.SmokeMediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.smoke.SmokeMediumcannonRoundItem;
import riftyboi.cbcmodernwarfare.munitions.smoke_discharger.SmokeDischargerRoundItem;

public class CBCModernWarfareItem {

	static { ModGroup.useModTab(ModGroup.MAIN_TAB_KEY); }
	public static final ItemEntry<Item>
		//Med Cannon Breeches

		CAST_IRON_MEDIUMCANNON_FALLING_BREECH = REGISTRATE.item("cast_iron_mediumcannon_falling_breech", Item::new)
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBreechBlock("mediumcannon/cast_iron_medium"))
		.register(),
		BRONZE_MEDIUMCANNON_FALLING_BREECH = REGISTRATE.item("bronze_mediumcannon_falling_breech", Item::new)
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBreechBlock("mediumcannon/bronze_medium"))
		.register(),
		STEEL_MEDIUMCANNON_FALLING_BREECH = REGISTRATE.item("steel_mediumcannon_falling_breech", Item::new)
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBreechBlock("mediumcannon/steel_medium"))
		.register(),
		NETHERSTEEL_MEDIUMCANNON_FALLING_BREECH = REGISTRATE.item("nethersteel_mediumcannon_falling_breech", Item::new)
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBreechBlock("mediumcannon/nethersteel_medium"))
		.register(),



		BRONZE_ROTARYCANNON_BREECH_EXTRACTOR = REGISTRATE.item("bronze_rotarycannon_breech_extractor", Item::new)
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBreechBlock("mediumcannon/bronze_medium"))
		.register(),

		STEEL_ROTARYCANNON_BREECH_EXTRACTOR = REGISTRATE.item("steel_rotaryannon_breech_extractor", Item::new)
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBreechBlock("mediumcannon/steel_medium"))
		.register(),

		NETHERSTEEL_ROTARYCANNON_BREECH_EXTRACTOR = REGISTRATE.item("nethersteel_rotarycannon_breech_extractor", Item::new)
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBreechBlock("mediumcannon/nethersteel_medium"))
		.register(),

		//Crafting Items


		CARBON_STEEL_INGOT = REGISTRATE.item("carbon_steel_ingot", Item::new)
		.tag(forgeTag("ingots/carbon_steel"))
		.tag(fabricTag("ingots/carbon_steel"))
		.tag(fabricTag("carbon_steel_ingots"))
		.register(),
		CARBON_STEEL_NUGGET = REGISTRATE.item("carbon_steel_nugget", Item::new)
			.tag(forgeTag("nuggets/carbon_steel"))
			.tag(fabricTag("nuggets/carbon_steel"))
			.tag(fabricTag("carbon_steel_nuggets"))
			.register(),
		SILICON_INGOT = REGISTRATE.item("silicon_ingot", Item::new)
			.tag(forgeTag("ingots/silicon"))
			.tag(fabricTag("ingots/silicon"))
			.tag(fabricTag("silicon_ingots"))
			.register(),
		SILICON_NUGGET = REGISTRATE.item("silicon_nugget", Item::new)
			.tag(forgeTag("nuggets/silicon"))
			.tag(fabricTag("nuggets/silicon"))
			.tag(fabricTag("silicon_nuggets"))
			.register(),


		INERT_EXPLOSIVES = REGISTRATE.item("inert_explosives", Item::new).register(),

		HEAVY_RECOIL_SPRING = REGISTRATE.item("heavy_recoil_spring", Item::new).register(),


		//Medium Cannon items


		MEDIUMCANNON_CARTRIDGE_SHEET = REGISTRATE.item("mediumcannon_cartridge_sheet", Item::new).register(),

		EMPTY_MEDIUMCANNON_CARTRIDGE = REGISTRATE.item("empty_mediumcannon_cartridge", Item::new)
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.SPENT_MEDIUMCANNON_CASINGS)
		.model((c, p) -> {})
		.register(),

		FILLED_MEDIUMCANNON_CARTRIDGE = REGISTRATE
		.item("filled_mediumcannon_cartridge", Item::new)
		.model((c, p) -> {})
		.register();




	//Autocannon


	public static final ItemEntry<HVAPAutocannonRoundItem> HVAP_AUTOCANNON_ROUND = REGISTRATE
		.item("hvap_autocannon_round", HVAPAutocannonRoundItem::new)
		.lang("High Velocity (HVAP) Armor Piercing Autocannon Round")
		.tag(CBCTags.CBCItemTags.AUTOCANNON_ROUNDS)
		.register();

	public static final ItemEntry<APDSAutocannonRoundItem> APDS_AUTOCANNON_ROUND = REGISTRATE
		.item("apds_autocannon_round", APDSAutocannonRoundItem::new)
		.lang("Armor Piercing (APDS) Discarding Sabot Autocannon Round")
		.tag(CBCTags.CBCItemTags.AUTOCANNON_ROUNDS)
		.register();

	public static final ItemEntry<CanisterAutocannonRoundItem> CANISTER_AUTOCANNON_ROUND = REGISTRATE
		.item("canister_autocannon_round", CanisterAutocannonRoundItem::new)
		.tag(CBCTags.CBCItemTags.AUTOCANNON_ROUNDS)
		.register();

	public static final ItemEntry<ExplosiveAutocannonRoundItem> HE_AUTOCANNON_ROUND = REGISTRATE
		.item("he_autocannon_round", ExplosiveAutocannonRoundItem::new)
		.tag(CBCTags.CBCItemTags.AUTOCANNON_ROUNDS)
		.register();

	public static final ItemEntry<Item> INCENDIARY_TIP = REGISTRATE.item("incendiary_tip", Item::new).register();



	//Med Cannon cartridge

	public static final ItemEntry<APMediumcannonCartridgeItem> AP_MEDIUMCANNON_CARTRIDGE = REGISTRATE
		.item("ap_mediumcannon_cartridge", APMediumcannonCartridgeItem::new)
		.removeTab(CreativeModeTabs.SEARCH)
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_CARTRIDGES)
		.transform(CBCModernWarfareBuilderTransformers.mediumCartridge("ap"))
		.register();

	public static final ItemEntry<APMediumcannonRoundItem> AP_MEDIUMCANNON_ROUND = REGISTRATE
		.item("ap_mediumcannon_round", APMediumcannonRoundItem::new)
		.lang("Armor Piercing Mediumcannon Round")
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_ROUNDS)
		.transform(CBCModernWarfareBuilderTransformers.mediumRound("ap"))
		.register();

	public static final ItemEntry<APDSMediumcannonCartridgeItem> APDS_MEDIUMCANNON_CARTRIDGE = REGISTRATE
		.item("apds_mediumcannon_cartridge", APDSMediumcannonCartridgeItem::new)
		.model((c, p) -> {})
		.removeTab(CreativeModeTabs.SEARCH)
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_CARTRIDGES)
		.transform(CBCModernWarfareBuilderTransformers.mediumCartridge("apds"))
		.register();

	public static final ItemEntry<APDSMediumcannonRoundItem> APDS_MEDIUMCANNON_ROUND = REGISTRATE
		.item("apds_mediumcannon_round", APDSMediumcannonRoundItem::new)
		.lang("Armor Piercing Discarding Sabot Round")
		.transform(CBCModernWarfareBuilderTransformers.mediumRound("apds"))
		.register();

	public static final ItemEntry<APFSDSMediumcannonCartridgeItem> APFSDS_MEDIUMCANNON_CARTRIDGE = REGISTRATE
		.item("apfsds_mediumcannon_cartridge", APFSDSMediumcannonCartridgeItem::new)
		.model((c, p) -> {})
		.removeTab(CreativeModeTabs.SEARCH)
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_CARTRIDGES)
		.transform(CBCModernWarfareBuilderTransformers.mediumCartridge("apfsds"))
		.register();

	public static final ItemEntry<APFSDSMediumcannonRoundItem> APFSDS_MEDIUMCANNON_ROUND = REGISTRATE
		.item("apfsds_mediumcannon_round", APFSDSMediumcannonRoundItem::new)
		.lang("Armor Piercing Discarding Sabot Round")
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_ROUNDS)
		.transform(CBCModernWarfareBuilderTransformers.mediumRound("apfsds"))
		.register();
	public static final ItemEntry<APHEMediumcannonCartridgeItem> APHE_MEDIUMCANNON_CARTRIDGE = REGISTRATE
		.item("aphe_mediumcannon_cartridge", APHEMediumcannonCartridgeItem::new)
		.model((c, p) -> {})
		.removeTab(CreativeModeTabs.SEARCH)
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_CARTRIDGES)
		.transform(CBCModernWarfareBuilderTransformers.mediumCartridge("aphe"))
		.register();

	public static final ItemEntry<APHEMediumcannonRoundItem> APHE_MEDIUMCANNON_ROUND = REGISTRATE
		.item("aphe_mediumcannon_round", APHEMediumcannonRoundItem::new)
		.lang("Armor Piercing High Explosive Mediumcannon Round")
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_ROUNDS)
		.transform(CBCModernWarfareBuilderTransformers.mediumRound("aphe"))
		.register();

	public static final ItemEntry<HEMediumcannonCartridgeItem> HE_MEDIUMCANNON_CARTRIDGE = REGISTRATE
		.item("he_mediumcannon_cartridge", HEMediumcannonCartridgeItem::new)
		.model((c, p) -> {})
		.removeTab(CreativeModeTabs.SEARCH)
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_CARTRIDGES)
		.transform(CBCModernWarfareBuilderTransformers.mediumCartridge("he"))
		.register();

	public static final ItemEntry<HEMediumcannonRoundItem> HE_MEDIUMCANNON_ROUND = REGISTRATE
		.item("he_mediumcannon_round", HEMediumcannonRoundItem::new)
		.lang("High Explosive Mediumcannon Round")
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_ROUNDS)
		.transform(CBCModernWarfareBuilderTransformers.mediumRound("he"))
		.register();

	public static final ItemEntry<HEFMediumcannonCartridgeItem> HEF_MEDIUMCANNON_CARTRIDGE = REGISTRATE
		.item("hef_mediumcannon_cartridge", HEFMediumcannonCartridgeItem::new)
		.model((c, p) -> {})
		.removeTab(CreativeModeTabs.SEARCH)
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_CARTRIDGES)
		.transform(CBCModernWarfareBuilderTransformers.mediumCartridge("hef"))
		.register();

	public static final ItemEntry<HEFMediumcannonRoundItem> HEF_MEDIUMCANNON_ROUND = REGISTRATE
		.item("hef_mediumcannon_round", HEFMediumcannonRoundItem::new)
		.lang("High Explosive Fragmentation Mediumcannon Round")
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_ROUNDS)
		.transform(CBCModernWarfareBuilderTransformers.mediumRound("hef"))
		.register();

	public static final ItemEntry<HEAPMediumcannonCartridgeItem> HEAP_MEDIUMCANNON_CARTRIDGE = REGISTRATE
		.item("heap_mediumcannon_cartridge", HEAPMediumcannonCartridgeItem::new)
		.model((c, p) -> {})
		.removeTab(CreativeModeTabs.SEARCH)
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_CARTRIDGES)
		.transform(CBCModernWarfareBuilderTransformers.mediumCartridge("heap"))
		.register();

	public static final ItemEntry<HEAPMediumcannonRoundItem> HEAP_MEDIUMCANNON_ROUND = REGISTRATE
		.item("heap_mediumcannon_round", HEAPMediumcannonRoundItem::new)
		.lang("Armor Piercing High Explosive Mediumcannon Round")
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_ROUNDS)
		.transform(CBCModernWarfareBuilderTransformers.mediumRound("heap"))
		.register();

	public static final ItemEntry<CanisterMediumcannonCartridgeItem> CANISTER_MEDIUMCANNON_CARTRIDGE = REGISTRATE
		.item("canister_mediumcannon_cartridge", CanisterMediumcannonCartridgeItem::new)
		.model((c, p) -> {})
		.removeTab(CreativeModeTabs.SEARCH)
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_CARTRIDGES)
		.transform(CBCModernWarfareBuilderTransformers.mediumCartridge("canister"))
		.register();

	public static final ItemEntry<CanisterMediumcannonRoundItem> CANISTER_MEDIUMCANNON_ROUND = REGISTRATE
		.item("canister_mediumcannon_round", CanisterMediumcannonRoundItem::new)
		.lang("Canister Mediumcannon Round")
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_ROUNDS)
		.transform(CBCModernWarfareBuilderTransformers.mediumRound("canister"))
		.register();


	public static final ItemEntry<SmokeMediumcannonCartridgeItem> SMOKE_MEDIUMCANNON_CARTRIDGE = REGISTRATE
		.item("smoke_mediumcannon_cartridge", SmokeMediumcannonCartridgeItem::new)
		.model((c, p) -> {})
		.removeTab(CreativeModeTabs.SEARCH)
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_CARTRIDGES)
		.transform(CBCModernWarfareBuilderTransformers.mediumCartridge("smoke"))
		.register();

	public static final ItemEntry<SmokeMediumcannonRoundItem> SMOKE_MEDIUMCANNON_ROUND = REGISTRATE
		.item("smoke_mediumcannon_round", SmokeMediumcannonRoundItem::new)
		.lang("Smoke Mediumcannon Round")
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.MEDIUMCANNON_ROUNDS)
		.transform(CBCModernWarfareBuilderTransformers.mediumRound("smoke"))
		.register();


	public static final ItemEntry<SmokeDischargerRoundItem> SMOKE_DISCHARGER_ROUND = REGISTRATE
		.item("smoke_discharger_round", SmokeDischargerRoundItem::new)
		.lang("Smoke Discharger Round")
		.register();


	public static TagKey<Item> tag(ResourceLocation loc) { return CBCRegistryUtils.createItemTag(loc); }
	private static TagKey<Item> forgeTag(String loc) { return tag(CBCUtils.location("forge", loc)); }
	private static TagKey<Item> fabricTag(String loc) { return tag(CBCUtils.location("c", loc)); }


	public static void register() {
	}
}
