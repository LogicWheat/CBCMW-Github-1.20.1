package riftyboi.cbcmodernwarfare.index;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static riftyboi.cbcmodernwarfare.CBCModernWarfare.REGISTRATE;
import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import com.simibubi.create.AllBlocks;

import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import io.github.fabricators_of_create.porting_lib.models.generators.ConfiguredModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.Shapes;
import rbasamoyai.createbigcannons.base.CBCDefaultStress;
import rbasamoyai.createbigcannons.index.CBCItems;
import rbasamoyai.createbigcannons.munitions.FuzedProjectileBlockItem;
import rbasamoyai.createbigcannons.munitions.big_cannon.ProjectileBlockItem;
import rbasamoyai.createbigcannons.munitions.config.MunitionPropertiesHandler;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.CBCModernWarfareTags;
import riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountBlock;

import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.SoundType;

import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.function.Supplier;
import rbasamoyai.createbigcannons.CBCTags;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastMouldBlock;
import rbasamoyai.createbigcannons.datagen.assets.CBCBuilderTransformers;
import riftyboi.cbcmodernwarfare.content.reactive.ERABlock;
import riftyboi.cbcmodernwarfare.content.reactive.ERASlabBlock;
import riftyboi.cbcmodernwarfare.content.reactive.ERAStairBlock;
import riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountDisplaySource;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBarrelBlock;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.breech.MediumcannonBreechBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlockItem;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherTubeBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.quickfiring.LauncherQuickFiringBreechBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.sliding_breech.LauncherSlidingBreechBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end.RocketLauncherEndBlock;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBarrelBlock;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.breech.RotarycannonBreechBlock;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.bearing.RotarycannonBearingBlock;
import riftyboi.cbcmodernwarfare.content.sights.block.types.PeriscopeCommanderSightBlock;
import riftyboi.cbcmodernwarfare.content.sights.block.types.PeriscopeSightBlock;
import riftyboi.cbcmodernwarfare.content.sights.block.types.RemoteGunSightBlock;
import riftyboi.cbcmodernwarfare.content.smoke_discharger.SmokeDischargerBlock;
import riftyboi.cbcmodernwarfare.crafting.boring.UnboredLauncherSlidingBreech;
import riftyboi.cbcmodernwarfare.crafting.boring.UnboredMediumcannonBlock;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import riftyboi.cbcmodernwarfare.crafting.boring.UnboredMunitionsLauncherBlock;
import riftyboi.cbcmodernwarfare.crafting.boring.UnboredRotarycannonBlock;
import riftyboi.cbcmodernwarfare.crafting.casting.CBCModernWarfareCastShapes;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.recoil_barrel.MediumcannonRecoilBarrelBlock;
import riftyboi.cbcmodernwarfare.crafting.incomplete.IncompleteLauncherSlidingBreech;
import riftyboi.cbcmodernwarfare.crafting.incomplete.IncompleteMediumcannonBlock;
import riftyboi.cbcmodernwarfare.crafting.incomplete.IncompleteRotarycannonBlock;
import riftyboi.cbcmodernwarfare.datagen.assets.CBCModernWarfareBuilderTransformers;
import riftyboi.cbcmodernwarfare.ModGroup;
import riftyboi.cbcmodernwarfare.munitions.autocannon.ammo_drum.AutocannonAmmoDrumBlock;
import riftyboi.cbcmodernwarfare.munitions.big_cannon.heap_shell.HEAPShellBlock;
import riftyboi.cbcmodernwarfare.munitions.big_cannon.apds_shot.APDSShotBlock;
import riftyboi.cbcmodernwarfare.munitions.big_cannon.hefrag_shell.HEFShellBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.fuel_tanks.SolidFuelTank;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.infrared_homing.InfraredSeekerGuidanceBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.manual.ManualCommandGuidanceBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.optical.OpticalCommandGuidanceBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.propulsion.SolidFuelSplitThruster;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.propulsion.SolidFuelThruster;

public class CBCModernWarfareBlocks {

	static { ModGroup.useModTab(ModGroup.MAIN_TAB_KEY); }

	//Tech Blocks

	public static final BlockEntry<SmokeDischargerBlock> SMOKE_DISCHARGER = REGISTRATE.block("smoke_discharger", SmokeDischargerBlock::new)
		.initialProperties(SharedProperties::softMetal)
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.transform(axeOrPickaxe())
		.transform(cannonBlock())
//		.blockstate((c, p) -> p.getVariantBuilder(c.get())
//			.forAllStates(s -> ConfiguredModel.builder()
//				.modelFile(AssetLookup.partialBaseModel(c, p))
//				.rotationX(s.getValue(SmokeDischargerBlock.CEILING) ? 180 : 0)
//				.build()))
		.item()
		.transform(customItemModel())
		.register();



	public static final BlockEntry<RemoteGunSightBlock> REMOTE_GUN_SIGHT = REGISTRATE.block("remote_gun_sight", RemoteGunSightBlock::new)
		.initialProperties(SharedProperties::softMetal)
		.transform(cannonBlock())
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.transform(axeOrPickaxe())
		.item()
		.build()
		.register();

	public static final BlockEntry<PeriscopeSightBlock> PERISCOPE_ARC_SIGHT = REGISTRATE.block("periscope_arc_sight", PeriscopeSightBlock::new)
		.initialProperties(SharedProperties::softMetal)
		.transform(cannonBlock())
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK).noOcclusion())
		.transform(axeOrPickaxe())
		.item()
		.build()
		.register();

	public static final BlockEntry<PeriscopeCommanderSightBlock> PERISCOPE_RING_SIGHT = REGISTRATE.block("periscope_ring_sight", PeriscopeCommanderSightBlock::new)
		.initialProperties(SharedProperties::softMetal)
		.transform(cannonBlock())
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK).noOcclusion())
		.transform(axeOrPickaxe())
		.item()
		.build()
		.register();

	//Torpedo Munitions







	// Guidance Blocks

	public static final BlockEntry<InfraredSeekerGuidanceBlock> INFRARED_SEEKER_GUIDANCE = REGISTRATE
		.block("infrared_seeker_guidance", InfraredSeekerGuidanceBlock::new)
		.initialProperties(() -> Blocks.TNT)
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.transform(shell(MapColor.COLOR_GRAY))
		.transform(axeOrPickaxe())
		.transform(CBCBuilderTransformers.safeNbt())
		.item()
		.build()
		.onRegister(block -> MunitionPropertiesHandler.registerBlockPropellantHandler(block, CBCModernWarfareMunitionPropertiesHandlers.INFRARED_SEEKER))
		.register();

	public static final BlockEntry<OpticalCommandGuidanceBlock> OPTICAL_COMMAND_GUIDANCE = REGISTRATE
		.block("optical_command_guidance", OpticalCommandGuidanceBlock::new)
		.initialProperties(() -> Blocks.TNT)
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.transform(shell(MapColor.COLOR_GRAY))
		.transform(axeOrPickaxe())
		.transform(CBCBuilderTransformers.safeNbt())
		.item()
		.build()
		.onRegister(block -> MunitionPropertiesHandler.registerBlockPropellantHandler(block, CBCModernWarfareMunitionPropertiesHandlers.STANDARD_GUIDANCE))
		.register();

	public static final BlockEntry<ManualCommandGuidanceBlock> MANUAL_COMMAND_GUIDANCE = REGISTRATE
		.block("manual_command_guidance", ManualCommandGuidanceBlock::new)
		.initialProperties(() -> Blocks.TNT)
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.transform(shell(MapColor.COLOR_GRAY))
		.transform(axeOrPickaxe())
		.transform(CBCBuilderTransformers.safeNbt())
		.item()
		.build()
		.onRegister(block -> MunitionPropertiesHandler.registerBlockPropellantHandler(block, CBCModernWarfareMunitionPropertiesHandlers.STANDARD_GUIDANCE))
		.register();


	//Rocket Parts
	public static final BlockEntry<SolidFuelTank> SOLID_FUEL_TANK = REGISTRATE
		.block("solid_fuel_tank", SolidFuelTank::new)
		.initialProperties(() -> Blocks.TNT)
		.transform(shell(MapColor.COLOR_GRAY))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.transform(CBCModernWarfareBuilderTransformers.munitonsLauncherBlock(CBCModernWarfare.resource("block/tank"),"projectile/solid_fuel_tank"))
		.transform(axeOrPickaxe())
		.transform(CBCBuilderTransformers.safeNbt())
		.item()
		.build()
		.onRegister(block -> MunitionPropertiesHandler.registerBlockPropellantHandler(block, CBCModernWarfareMunitionPropertiesHandlers.SOLID_FUEL_TANK))
		.register();

	public static final BlockEntry<SolidFuelThruster> SOLID_FUEL_THRUSTER = REGISTRATE
		.block("solid_fuel_thruster", SolidFuelThruster::new)
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.transform(shell(MapColor.COLOR_GRAY))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.transform(CBCModernWarfareBuilderTransformers.munitonsLauncherBlock(CBCModernWarfare.resource("block/fins"),"projectile/solid_fuel_thruster"))
		.transform(axeOrPickaxe())
		.transform(CBCBuilderTransformers.safeNbt())
		.item()
		.build()
		.onRegister(block -> MunitionPropertiesHandler.registerBlockPropellantHandler(block, CBCModernWarfareMunitionPropertiesHandlers.SOLID_FUEL_THRUSTER))
		.register();

	public static final BlockEntry<SolidFuelSplitThruster> SOLID_FUEL_SPLIT_THRUSTER = REGISTRATE
		.block("solid_fuel_split_thruster", SolidFuelSplitThruster::new)
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.transform(shell(MapColor.COLOR_GRAY))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.transform(CBCModernWarfareBuilderTransformers.munitonsLauncherBlock(CBCModernWarfare.resource("block/split_thruster"),"projectile/solid_fuel_tank"))
		.transform(axeOrPickaxe())
		.transform(CBCBuilderTransformers.safeNbt())
		.item()
		.build()
		.onRegister(block -> MunitionPropertiesHandler.registerBlockPropellantHandler(block, CBCModernWarfareMunitionPropertiesHandlers.SOLID_FUEL_SPlIT_THRUSTER))
		.register();

	//Ammo Drum

	public static final BlockEntry<AutocannonAmmoDrumBlock> AUTOCANNON_AMMO_DRUM = REGISTRATE
		.block("rotarycannon_ammo_drum", AutocannonAmmoDrumBlock::new)
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(p -> p.strength(0.0f, 2.5f))
		.properties(p -> p.sound(SoundType.CHAIN))
		.properties(BlockBehaviour.Properties::noOcclusion)
		.transform(CBCModernWarfareBuilderTransformers.autocannonAmmoDrum(false))
		.register();



	//Rocket Launcher Parts

	public static final BlockEntry<MunitionsLauncherTubeBlock> ROCKET_RAILS = REGISTRATE
		.block("rocket_rails", p -> MunitionsLauncherTubeBlock.small(p, CBCModernWarfareMunitionsLauncherMaterials.ROCKET_LAUNCHER))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.steelScrapLoot(10))
		.item(MunitionsLauncherBlockItem::new).build()
		.register();


	public static final BlockEntry<RocketLauncherEndBlock> ROCKET_RAILS_END = REGISTRATE
		.block("rocket_rails_end", p -> RocketLauncherEndBlock.small(p, CBCModernWarfareMunitionsLauncherMaterials.ROCKET_LAUNCHER))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.steelScrapLoot(10))
		.item(MunitionsLauncherBlockItem::new).build()
		.register();


	// Gun Launcher Parts

	public static final BlockEntry<MunitionsLauncherTubeBlock> GUN_LAUNCHER_BARREL = REGISTRATE
		.block("gun_launcher_barrel", p -> MunitionsLauncherTubeBlock.verySmall(p, CBCModernWarfareMunitionsLauncherMaterials.GUN_LAUNCHER))
		.transform(cannonBlock())
		.transform(CBCModernWarfareBuilderTransformers.cannonBarrel("gun_launcher", true))
		.loot(CBCBuilderTransformers.castIronScrapLoot(10))
		.item(MunitionsLauncherBlockItem::new).build()
		.register();

	public static final BlockEntry<MunitionsLauncherTubeBlock> BUILT_UP_GUN_LAUNCHER_BARREL = REGISTRATE
		.block("built_up_gun_launcher_barrel", p -> MunitionsLauncherTubeBlock.small(p, CBCModernWarfareMunitionsLauncherMaterials.GUN_LAUNCHER))
		.transform(cannonBlock())
		.transform(CBCModernWarfareBuilderTransformers.builtUpCannonBarrel("gun_launcher", true))
		.loot(CBCBuilderTransformers.castIronScrapLoot(10))
		.item(MunitionsLauncherBlockItem::new).build()
		.register();


	public static final BlockEntry<MunitionsLauncherTubeBlock> GUN_LAUNCHER_CHAMBER = REGISTRATE
		.block("gun_launcher_chamber", p -> MunitionsLauncherTubeBlock.medium(p, CBCModernWarfareMunitionsLauncherMaterials.GUN_LAUNCHER))
		.transform(cannonBlock())
		.transform(CBCModernWarfareBuilderTransformers.cannonChamber("gun_launcher", true))
		.loot(CBCBuilderTransformers.castIronScrapLoot(10))
		.item(MunitionsLauncherBlockItem::new).build()
		.register();


	public static final BlockEntry<LauncherQuickFiringBreechBlock> GUN_LAUNCHER_QUICKFIRING_BREECH = REGISTRATE
		.block("gun_launcher_quickfiring_breech", p -> new LauncherQuickFiringBreechBlock(p, CBCModernWarfareMunitionsLauncherMaterials.GUN_LAUNCHER, gunlauncherSlidingBrech()))
		.lang("Gun Launcher Quick-Firing Breech")
		.transform(cannonBlock(false))
		.transform(CBCModernWarfareBuilderTransformers.slidingBreech("sliding_breech/gun_launcher"))
		.loot(CBCBuilderTransformers.castIronScrapLoot(10))
		.register();

	private static NonNullSupplier<? extends Block> gunlauncherSlidingBrech() {
		return GUN_LAUNCHER_SLIDING_BREECH;
	}

	public static final BlockEntry<LauncherSlidingBreechBlock> GUN_LAUNCHER_SLIDING_BREECH = REGISTRATE
		.block("gun_launcher_sliding_breech", p -> new LauncherSlidingBreechBlock(p, CBCModernWarfareMunitionsLauncherMaterials.GUN_LAUNCHER, GUN_LAUNCHER_QUICKFIRING_BREECH))
		.transform(cannonBlock(false))
		.transform(CBCModernWarfareBuilderTransformers.slidingBreech("sliding_breech/gun_launcher"))
		.loot(CBCBuilderTransformers.castIronScrapLoot(10))
		.transform(CBCDefaultStress.setImpact(16.0d))
		.register();


	public static final BlockEntry<UnboredMunitionsLauncherBlock> UNBORED_GUN_LAUNCHER_BARREL = REGISTRATE
		.block("unbored_gun_launcher_barrel", p -> UnboredMunitionsLauncherBlock.verySmall(p, CBCModernWarfareMunitionsLauncherMaterials.GUN_LAUNCHER))
		.transform(cannonBlock(false))
		.transform(CBCModernWarfareBuilderTransformers.cannonBarrel("gun_launcher", false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(15))
		.item(MunitionsLauncherBlockItem::new).build()
		.register();

	public static final BlockEntry<UnboredMunitionsLauncherBlock> UNBORED_BUILT_UP_GUN_LAUNCHER_BARREL = REGISTRATE
		.block("unbored_built_up_gun_launcher_barrel", p -> UnboredMunitionsLauncherBlock.small(p, CBCModernWarfareMunitionsLauncherMaterials.GUN_LAUNCHER))
		.transform(cannonBlock(false))
		.transform(CBCModernWarfareBuilderTransformers.builtUpCannonBarrel("gun_launcher", false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(15))
		.item(MunitionsLauncherBlockItem::new).build()
		.register();

	public static final BlockEntry<UnboredMunitionsLauncherBlock> UNBORED_GUN_LAUNCHER_CHAMBER = REGISTRATE
		.block("unbored_gun_launcher_chamber", p -> UnboredMunitionsLauncherBlock.medium(p, CBCModernWarfareMunitionsLauncherMaterials.GUN_LAUNCHER))
		.transform(cannonBlock(false))
		.transform(CBCModernWarfareBuilderTransformers.cannonChamber("gun_launcher", false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(15))
		.item(MunitionsLauncherBlockItem::new).build()
		.register();

	public static final BlockEntry<IncompleteLauncherSlidingBreech> INCOMPLETE_GUN_LAUNCHER_SLIDING_BREECH = REGISTRATE
		.block("incomplete_gun_launcher_sliding_breech", p -> new IncompleteLauncherSlidingBreech(p, CBCModernWarfareMunitionsLauncherMaterials.GUN_LAUNCHER, CBCItems.CAST_IRON_SLIDING_BREECHBLOCK, GUN_LAUNCHER_SLIDING_BREECH))
		.transform(cannonBlock())
		.transform(CBCModernWarfareBuilderTransformers.slidingBreechIncomplete("sliding_breech/gun_launcher"))
		.loot(CBCBuilderTransformers.castIronScrapLoot(10))
		.item(MunitionsLauncherBlockItem::new).build()
		.register();

	public static final BlockEntry<UnboredLauncherSlidingBreech> UNBORED_GUN_LAUNCHER_SLIDING_BREECH = REGISTRATE
		.block("unbored_gun_launcher_sliding_breech", p -> new UnboredLauncherSlidingBreech(p, CBCModernWarfareMunitionsLauncherMaterials.GUN_LAUNCHER, Shapes.block()))
		.transform(cannonBlock(false))
		.transform(CBCModernWarfareBuilderTransformers.slidingBreechUnbored("sliding_breech/unbored_gun_launcher"))
		.loot(CBCBuilderTransformers.castIronScrapLoot(15))
		.item(MunitionsLauncherBlockItem::new).build()
		.register();





	// Torpedo Launcher Parts

	//public static final BlockEntry<TorpedoScrewBreechBlock> TORPEDO_SCREW_BREECH = REGISTRATE
	//	.block("torpedo_screw_breech", p -> new TorpedoScrewBreechBlock(p, CBCModernWarfareMunitionsLauncherMaterials.TORPEDO_LAUNCHER))
	//	.transform(cannonBlock(false))
	//	.loot(CBCBuilderTransformers.steelScrapLoot(10))
	//	.transform(CBCModernWarfareBuilderTransformers.torpedoScrewBreech("screw_breech/steel"))
	//	.transform(CBCDefaultStress.setImpact(16.0d))
	//	.item(MunitionsLauncherBlockItem::new).build()
	//	.register();




	public static final BlockEntry<MunitionsLauncherTubeBlock> TORPEDO_TUBE = REGISTRATE
		.block("torpedo_tube", p -> MunitionsLauncherTubeBlock.medium(p, CBCModernWarfareMunitionsLauncherMaterials.TORPEDO_LAUNCHER))
		.transform(cannonBlock(false))
		.transform(CBCBuilderTransformers.cannonChamber("steel", true))
		.loot(CBCBuilderTransformers.steelScrapLoot(10))
		.item(MunitionsLauncherBlockItem::new).build()
		.register();





	//////// Bronze Rotarycannon blocks ////////

	public static final BlockEntry<RotarycannonBarrelBlock> BRONZE_ROTARYCANNON_BARREL = REGISTRATE
		.block("bronze_rotarycannon_barrel", p -> new RotarycannonBarrelBlock(p, CBCModernWarfareRotarycannonMaterials.BRONZE))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.bronzeScrapLoot(2))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBarrel("rotarycannon/bronze"))
		.register();

	public static final BlockEntry<RotarycannonBreechBlock> BRONZE_ROTARYCANNON_BREECH = REGISTRATE
		.block("bronze_rotarycannon_breech", p -> new RotarycannonBreechBlock(p, CBCModernWarfareRotarycannonMaterials.BRONZE))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.bronzeScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBreech("rotarycannon/bronze", true))
		.register();

	public static final BlockEntry<RotarycannonBearingBlock> BRONZE_ROTARYCANNON_BEARING = REGISTRATE
		.block("bronze_rotarycannon_bearing", p -> new RotarycannonBearingBlock(p, CBCModernWarfareRotarycannonMaterials.BRONZE))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.bronzeScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBearing("rotarycannon/bronze", true))
		.register();

	public static final BlockEntry<IncompleteRotarycannonBlock> INCOMPLETE_BRONZE_ROTARYCANNON_BEARING = REGISTRATE
		.block("incomplete_bronze_rotarycannon_bearing", p ->
			IncompleteRotarycannonBlock.bearing(p, CBCModernWarfareRotarycannonMaterials.BRONZE, BRONZE_ROTARYCANNON_BEARING, AllBlocks.SHAFT))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.bronzeScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBearing("rotarycannon/bronze", false))
		.register();

	public static final BlockEntry<IncompleteRotarycannonBlock> INCOMPLETE_BRONZE_ROTARYCANNON_BREECH = REGISTRATE
		.block("incomplete_bronze_rotarycannon_breech", p ->
			IncompleteRotarycannonBlock.breech(p, CBCModernWarfareRotarycannonMaterials.BRONZE, BRONZE_ROTARYCANNON_BREECH, CBCModernWarfareItem.BRONZE_ROTARYCANNON_BREECH_EXTRACTOR))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.bronzeScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBreech("rotarycannon/bronze", false))
		.register();

	public static final BlockEntry<UnboredRotarycannonBlock> UNBORED_BRONZE_ROTARYCANNON_BARREL = REGISTRATE
		.block("unbored_bronze_rotarycannon_barrel", p -> UnboredRotarycannonBlock.barrel(p, CBCModernWarfareRotarycannonMaterials.BRONZE))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.bronzeScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.unboredRotarycannonBarrel("rotarycannon/unbored_bronze"))
		.register();

	public static final BlockEntry<UnboredRotarycannonBlock> UNBORED_BRONZE_ROTARYCANNON_BEARING = REGISTRATE
		.block("unbored_bronze_rotarycannon_bearing", p -> UnboredRotarycannonBlock.bearing(p, CBCModernWarfareRotarycannonMaterials.BRONZE))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.bronzeScrapLoot(6))
		.transform(CBCModernWarfareBuilderTransformers.unboredRotarycannonBearing("rotarycannon/unbored_bronze"))
		.register();

	public static final BlockEntry<UnboredRotarycannonBlock> UNBORED_BRONZE_ROTARYCANNON_BREECH = REGISTRATE
		.block("unbored_bronze_rotarycannon_breech", p -> UnboredRotarycannonBlock.breech(p, CBCModernWarfareRotarycannonMaterials.BRONZE))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.bronzeScrapLoot(8))
		.transform(CBCModernWarfareBuilderTransformers.unboredRotarycannonBreech("rotarycannon/unbored_bronze"))
		.register();




	//////// Steel Rotarycannon blocks ////////

	public static final BlockEntry<RotarycannonBarrelBlock> STEEL_ROTARYCANNON_BARREL = REGISTRATE
		.block("steel_rotarycannon_barrel", p -> new RotarycannonBarrelBlock(p, CBCModernWarfareRotarycannonMaterials.STEEL))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.steelScrapLoot(2))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBarrel("rotarycannon/steel"))
		.register();

	public static final BlockEntry<RotarycannonBreechBlock> STEEL_ROTARYCANNON_BREECH = REGISTRATE
		.block("steel_rotarycannon_breech", p -> new RotarycannonBreechBlock(p, CBCModernWarfareRotarycannonMaterials.STEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.steelScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBreech("rotarycannon/steel", true))
		.register();

	public static final BlockEntry<RotarycannonBearingBlock> STEEL_ROTARYCANNON_BEARING = REGISTRATE
		.block("steel_rotarycannon_bearing", p -> new RotarycannonBearingBlock(p, CBCModernWarfareRotarycannonMaterials.STEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.steelScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBearing("rotarycannon/steel", true))
		.register();

	public static final BlockEntry<IncompleteRotarycannonBlock> INCOMPLETE_STEEL_ROTARYCANNON_BEARING = REGISTRATE
		.block("incomplete_steel_rotarycannon_bearing", p ->
			IncompleteRotarycannonBlock.bearing(p, CBCModernWarfareRotarycannonMaterials.STEEL, STEEL_ROTARYCANNON_BEARING, AllBlocks.SHAFT))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.steelScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBearing("rotarycannon/steel", false))
		.register();

	public static final BlockEntry<IncompleteRotarycannonBlock> INCOMPLETE_STEEL_ROTARYCANNON_BREECH = REGISTRATE
		.block("incomplete_steel_rotarycannon_breech", p ->
			IncompleteRotarycannonBlock.breech(p, CBCModernWarfareRotarycannonMaterials.STEEL, STEEL_ROTARYCANNON_BREECH, CBCModernWarfareItem.STEEL_ROTARYCANNON_BREECH_EXTRACTOR))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.steelScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBreech("rotarycannon/steel", false))
		.register();

	public static final BlockEntry<UnboredRotarycannonBlock> UNBORED_STEEL_ROTARYCANNON_BARREL = REGISTRATE
		.block("unbored_steel_rotarycannon_barrel", p -> UnboredRotarycannonBlock.barrel(p, CBCModernWarfareRotarycannonMaterials.STEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.steelScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.unboredRotarycannonBarrel("rotarycannon/unbored_steel"))
		.register();

	public static final BlockEntry<UnboredRotarycannonBlock> UNBORED_STEEL_ROTARYCANNON_BEARING = REGISTRATE
		.block("unbored_steel_rotarycannon_bearing", p -> UnboredRotarycannonBlock.bearing(p, CBCModernWarfareRotarycannonMaterials.STEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.steelScrapLoot(6))
		.transform(CBCModernWarfareBuilderTransformers.unboredRotarycannonBearing("rotarycannon/unbored_steel"))
		.register();

	public static final BlockEntry<UnboredRotarycannonBlock> UNBORED_STEEL_ROTARYCANNON_BREECH = REGISTRATE
		.block("unbored_steel_rotarycannon_breech", p -> UnboredRotarycannonBlock.breech(p, CBCModernWarfareRotarycannonMaterials.STEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.steelScrapLoot(8))
		.transform(CBCModernWarfareBuilderTransformers.unboredRotarycannonBreech("rotarycannon/unbored_steel"))
		.register();







	//////// Nethersteel Rotarycannon blocks ////////

	public static final BlockEntry<RotarycannonBarrelBlock> NETHERSTEEL_ROTARYCANNON_BARREL = REGISTRATE
		.block("nethersteel_rotarycannon_barrel", p -> new RotarycannonBarrelBlock(p, CBCModernWarfareRotarycannonMaterials.NETHERSTEEL))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.nethersteelScrapLoot(2))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBarrel("rotarycannon/nethersteel"))
		.register();

	public static final BlockEntry<RotarycannonBreechBlock> NETHERSTEEL_ROTARYCANNON_BREECH = REGISTRATE
		.block("nethersteel_rotarycannon_breech", p -> new RotarycannonBreechBlock(p, CBCModernWarfareRotarycannonMaterials.NETHERSTEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.nethersteelScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBreech("rotarycannon/nethersteel", true))
		.register();

	public static final BlockEntry<RotarycannonBearingBlock> NETHERSTEEL_ROTARYCANNON_BEARING = REGISTRATE
		.block("nethersteel_rotarycannon_bearing", p -> new RotarycannonBearingBlock(p, CBCModernWarfareRotarycannonMaterials.NETHERSTEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.nethersteelScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBearing("rotarycannon/nethersteel", true))
		.register();

	public static final BlockEntry<IncompleteRotarycannonBlock> INCOMPLETE_NETHERSTEEL_ROTARYCANNON_BEARING = REGISTRATE
		.block("incomplete_nethersteel_rotarycannon_bearing", p ->
			IncompleteRotarycannonBlock.bearing(p, CBCModernWarfareRotarycannonMaterials.NETHERSTEEL, STEEL_ROTARYCANNON_BEARING, AllBlocks.SHAFT))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.nethersteelScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBearing("rotarycannon/nethersteel", false))
		.register();

	public static final BlockEntry<IncompleteRotarycannonBlock> INCOMPLETE_NETHERSTEEL_ROTARYCANNON_BREECH = REGISTRATE
		.block("incomplete_nethersteel_rotarycannon_breech", p ->
			IncompleteRotarycannonBlock.breech(p, CBCModernWarfareRotarycannonMaterials.NETHERSTEEL, NETHERSTEEL_ROTARYCANNON_BREECH, CBCModernWarfareItem.NETHERSTEEL_ROTARYCANNON_BREECH_EXTRACTOR))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.nethersteelScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.rotarycannonBreech("rotarycannon/nethersteel", false))
		.register();

	public static final BlockEntry<UnboredRotarycannonBlock> UNBORED_NETHERSTEEL_ROTARYCANNON_BARREL = REGISTRATE
		.block("unbored_nethersteel_rotarycannon_barrel", p -> UnboredRotarycannonBlock.barrel(p, CBCModernWarfareRotarycannonMaterials.NETHERSTEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.nethersteelScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.unboredRotarycannonBarrel("rotarycannon/unbored_nethersteel"))
		.register();

	public static final BlockEntry<UnboredRotarycannonBlock> UNBORED_NETHERSTEEL_ROTARYCANNON_BEARING = REGISTRATE
		.block("unbored_nethersteel_rotarycannon_bearing", p -> UnboredRotarycannonBlock.bearing(p, CBCModernWarfareRotarycannonMaterials.NETHERSTEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.nethersteelScrapLoot(6))
		.transform(CBCModernWarfareBuilderTransformers.unboredRotarycannonBearing("rotarycannon/unbored_nethersteel"))
		.register();

	public static final BlockEntry<UnboredRotarycannonBlock> UNBORED_NETHERSTEEL_ROTARYCANNON_BREECH = REGISTRATE
		.block("unbored_nethersteel_rotarycannon_breech", p -> UnboredRotarycannonBlock.breech(p, CBCModernWarfareRotarycannonMaterials.NETHERSTEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.nethersteelScrapLoot(8))
		.transform(CBCModernWarfareBuilderTransformers.unboredRotarycannonBreech("rotarycannon/unbored_nethersteel"))
		.register();



	// Cast Iron Medium Cannon



	public static final BlockEntry<MediumcannonBarrelBlock> CAST_IRON_MEDIUMCANNON_BARREL = REGISTRATE
	.block("cast_iron_mediumcannon_barrel", p -> new MediumcannonBarrelBlock(p, CBCModernWarfareMediumcannonMaterials.CAST_IRON))
	.transform(cannonBlock())
	.loot(CBCBuilderTransformers.castIronScrapLoot(2))
	.transform(CBCModernWarfareBuilderTransformers.mediumcannonBarrel("mediumcannon/cast_iron"))
	.register();

	public static final BlockEntry<MediumcannonRecoilBarrelBlock> CAST_IRON_MEDIUMCANNON_RECOIL_BARREL = REGISTRATE
		.block("cast_iron_mediumcannon_recoil_barrel", p -> new MediumcannonRecoilBarrelBlock(p, CBCModernWarfareMediumcannonMaterials.CAST_IRON, CBCModernWarfareBlocks::castIronMediumcannonBarrel))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonRecoilBarrel("mediumcannon/cast_iron", true))
		.register();

	public static final BlockEntry<MediumcannonBreechBlock> CAST_IRON_MEDIUMCANNON_BREECH = REGISTRATE
		.block("cast_iron_mediumcannon_breech", p -> new MediumcannonBreechBlock(p, CBCModernWarfareMediumcannonMaterials.CAST_IRON))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBreech("mediumcannon/cast_iron", true))
		.register();

	public static final BlockEntry<IncompleteMediumcannonBlock> INCOMPLETE_CAST_IRON_MEDIUMCANNON_BREECH = REGISTRATE
		.block("incomplete_cast_iron_mediumcannon_breech", p ->
			IncompleteMediumcannonBlock.breech(p, CBCModernWarfareMediumcannonMaterials.CAST_IRON, CAST_IRON_MEDIUMCANNON_BREECH, CBCModernWarfareItem.CAST_IRON_MEDIUMCANNON_FALLING_BREECH))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.castIronScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.incompleteCannonBreech("mediumcannon/cast_iron", false))
		.register();

	public static final BlockEntry<IncompleteMediumcannonBlock> INCOMPLETE_CAST_IRON_MEDIUMCANNON_RECOIL_BARREL = REGISTRATE
		.block("incomplete_cast_iron_mediumcannon_recoil_barrel", p -> IncompleteMediumcannonBlock.recoilBarrel(p, CBCModernWarfareMediumcannonMaterials.CAST_IRON, CAST_IRON_MEDIUMCANNON_RECOIL_BARREL, CBCModernWarfareItem.HEAVY_RECOIL_SPRING))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.castIronScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonRecoilBarrel("mediumcannon/cast_iron", false))
		.register();


	public static final BlockEntry<UnboredMediumcannonBlock> UNBORED_CAST_IRON_MEDIUMCANNON_BARREL = REGISTRATE
		.block("unbored_cast_iron_mediumcannon_barrel", p -> UnboredMediumcannonBlock.barrel(p, CBCModernWarfareMediumcannonMaterials.CAST_IRON))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.unboredMediumcannonBarrel("mediumcannon/unbored_cast_iron"))
		.register();

	public static final BlockEntry<UnboredMediumcannonBlock> UNBORED_CAST_IRON_MEDIUMCANNON_BREECH = REGISTRATE
		.block("unbored_cast_iron_mediumcannon_breech", p -> UnboredMediumcannonBlock.breech(p, CBCModernWarfareMediumcannonMaterials.CAST_IRON))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(8))
		.transform(CBCModernWarfareBuilderTransformers.unboredMediumcannonBreech("mediumcannon/unbored_cast_iron"))
		.register();

	public static final BlockEntry<UnboredMediumcannonBlock> UNBORED_CAST_IRON_MEDIUMCANNON_RECOIL_BARREL = REGISTRATE
		.block("unbored_cast_iron_mediumcannon_recoil_barrel", p -> UnboredMediumcannonBlock.recoilBarrel(p, CBCModernWarfareMediumcannonMaterials.CAST_IRON))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(6))
		.transform(CBCModernWarfareBuilderTransformers.unboredMediumcannonRecoilBarrel("mediumcannon/unbored_cast_iron"))
		.register();




	// Bronze Medium Cannon



	public static final BlockEntry<MediumcannonBarrelBlock> BRONZE_MEDIUMCANNON_BARREL = REGISTRATE
		.block("bronze_mediumcannon_barrel", p -> new MediumcannonBarrelBlock(p, CBCModernWarfareMediumcannonMaterials.BRONZE))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.castIronScrapLoot(2))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBarrel("mediumcannon/bronze"))
		.register();

	public static final BlockEntry<MediumcannonRecoilBarrelBlock> BRONZE_MEDIUMCANNON_RECOIL_BARREL = REGISTRATE
		.block("bronze_mediumcannon_recoil_barrel", p -> new MediumcannonRecoilBarrelBlock(p, CBCModernWarfareMediumcannonMaterials.BRONZE, CBCModernWarfareBlocks::bronzeMediumcannonBarrel))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonRecoilBarrel("mediumcannon/bronze", true))
		.register();

	public static final BlockEntry<MediumcannonBreechBlock> BRONZE_MEDIUMCANNON_BREECH = REGISTRATE
		.block("bronze_mediumcannon_breech", p -> new MediumcannonBreechBlock(p, CBCModernWarfareMediumcannonMaterials.BRONZE))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBreech("mediumcannon/bronze", true))
		.register();

	public static final BlockEntry<IncompleteMediumcannonBlock> INCOMPLETE_BRONZE_MEDIUMCANNON_BREECH = REGISTRATE
		.block("incomplete_bronze_mediumcannon_breech", p ->
			IncompleteMediumcannonBlock.breech(p, CBCModernWarfareMediumcannonMaterials.BRONZE, BRONZE_MEDIUMCANNON_BREECH, CBCModernWarfareItem.BRONZE_MEDIUMCANNON_FALLING_BREECH))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.castIronScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.incompleteCannonBreech("mediumcannon/bronze", false))
		.register();

	public static final BlockEntry<IncompleteMediumcannonBlock> INCOMPLETE_BRONZE_MEDIUMCANNON_RECOIL_BARREL = REGISTRATE
		.block("incomplete_bronze_mediumcannon_recoil_barrel", p ->
			IncompleteMediumcannonBlock.recoilBarrel(p, CBCModernWarfareMediumcannonMaterials.BRONZE, BRONZE_MEDIUMCANNON_RECOIL_BARREL, CBCModernWarfareItem.HEAVY_RECOIL_SPRING))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.castIronScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonRecoilBarrel("mediumcannon/bronze", false))
		.register();


	public static final BlockEntry<UnboredMediumcannonBlock> UNBORED_BRONZE_MEDIUMCANNON_BARREL = REGISTRATE
		.block("unbored_bronze_mediumcannon_barrel", p -> UnboredMediumcannonBlock.barrel(p, CBCModernWarfareMediumcannonMaterials.BRONZE))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.unboredMediumcannonBarrel("mediumcannon/unbored_bronze"))
		.register();

	public static final BlockEntry<UnboredMediumcannonBlock> UNBORED_BRONZE_MEDIUMCANNON_BREECH = REGISTRATE
		.block("unbored_bronze_mediumcannon_breech", p -> UnboredMediumcannonBlock.breech(p, CBCModernWarfareMediumcannonMaterials.BRONZE))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(8))
		.transform(CBCModernWarfareBuilderTransformers.unboredMediumcannonBreech("mediumcannon/unbored_bronze"))
		.register();

	public static final BlockEntry<UnboredMediumcannonBlock> UNBORED_BRONZE_MEDIUMCANNON_RECOIL_BARREL = REGISTRATE
		.block("unbored_bronze_mediumcannon_recoil_barrel", p -> UnboredMediumcannonBlock.recoilBarrel(p, CBCModernWarfareMediumcannonMaterials.BRONZE))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(6))
		.transform(CBCModernWarfareBuilderTransformers.unboredMediumcannonRecoilBarrel("mediumcannon/unbored_bronze"))
		.register();


	// Steel Medium Cannon


	public static final BlockEntry<MediumcannonBarrelBlock> STEEL_MEDIUMCANNON_BARREL = REGISTRATE
		.block("steel_mediumcannon_barrel", p -> new MediumcannonBarrelBlock(p, CBCModernWarfareMediumcannonMaterials.STEEL))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.castIronScrapLoot(2))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBarrel("mediumcannon/steel"))
		.register();

	public static final BlockEntry<MediumcannonRecoilBarrelBlock> STEEL_MEDIUMCANNON_RECOIL_BARREL = REGISTRATE
		.block("steel_mediumcannon_recoil_barrel", p -> new MediumcannonRecoilBarrelBlock(p, CBCModernWarfareMediumcannonMaterials.STEEL, CBCModernWarfareBlocks::steelMediumcannonBarrel))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonRecoilBarrel("mediumcannon/steel", true))
		.register();

	public static final BlockEntry<MediumcannonBreechBlock> STEEL_MEDIUMCANNON_BREECH = REGISTRATE
		.block("steel_mediumcannon_breech", p -> new MediumcannonBreechBlock(p, CBCModernWarfareMediumcannonMaterials.STEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBreech("mediumcannon/steel", true))
		.register();

	public static final BlockEntry<IncompleteMediumcannonBlock> INCOMPLETE_STEEL_MEDIUMCANNON_BREECH = REGISTRATE
		.block("incomplete_steel_mediumcannon_breech", p ->
			IncompleteMediumcannonBlock.breech(p, CBCModernWarfareMediumcannonMaterials.STEEL, STEEL_MEDIUMCANNON_BREECH, CBCModernWarfareItem.STEEL_MEDIUMCANNON_FALLING_BREECH))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.castIronScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.incompleteCannonBreech("mediumcannon/steel", false))
		.register();

	public static final BlockEntry<IncompleteMediumcannonBlock> INCOMPLETE_STEEL_MEDIUMCANNON_RECOIL_BARREL = REGISTRATE
		.block("incomplete_steel_mediumcannon_recoil_barrel", p ->
			IncompleteMediumcannonBlock.recoilBarrel(p, CBCModernWarfareMediumcannonMaterials.STEEL, STEEL_MEDIUMCANNON_RECOIL_BARREL, CBCModernWarfareItem.HEAVY_RECOIL_SPRING))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.castIronScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonRecoilBarrel("mediumcannon/steel", false))
		.register();


	public static final BlockEntry<UnboredMediumcannonBlock> UNBORED_STEEL_MEDIUMCANNON_BARREL = REGISTRATE
		.block("unbored_steel_mediumcannon_barrel", p -> UnboredMediumcannonBlock.barrel(p, CBCModernWarfareMediumcannonMaterials.STEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.unboredMediumcannonBarrel("mediumcannon/unbored_steel"))
		.register();

	public static final BlockEntry<UnboredMediumcannonBlock> UNBORED_STEEL_MEDIUMCANNON_BREECH = REGISTRATE
		.block("unbored_steel_mediumcannon_breech", p -> UnboredMediumcannonBlock.breech(p, CBCModernWarfareMediumcannonMaterials.STEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(8))
		.transform(CBCModernWarfareBuilderTransformers.unboredMediumcannonBreech("mediumcannon/unbored_steel"))
		.register();

	public static final BlockEntry<UnboredMediumcannonBlock> UNBORED_STEEL_MEDIUMCANNON_RECOIL_BARREL = REGISTRATE
		.block("unbored_steel_mediumcannon_recoil_barrel", p -> UnboredMediumcannonBlock.recoilBarrel(p, CBCModernWarfareMediumcannonMaterials.STEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(6))
		.transform(CBCModernWarfareBuilderTransformers.unboredMediumcannonRecoilBarrel("mediumcannon/unbored_steel"))
		.register();

	// Nethersteel Medium Cannon


	public static final BlockEntry<MediumcannonBarrelBlock> NETHERSTEEL_MEDIUMCANNON_BARREL = REGISTRATE
		.block("nethersteel_mediumcannon_barrel", p -> new MediumcannonBarrelBlock(p, CBCModernWarfareMediumcannonMaterials.NETHERSTEEL))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.castIronScrapLoot(2))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBarrel("mediumcannon/nethersteel"))
		.register();

	public static final BlockEntry<MediumcannonRecoilBarrelBlock> NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL = REGISTRATE
		.block("nethersteel_mediumcannon_recoil_barrel", p -> new MediumcannonRecoilBarrelBlock(p, CBCModernWarfareMediumcannonMaterials.NETHERSTEEL, CBCModernWarfareBlocks::nethersteelMediumcannonBarrel))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonRecoilBarrel("mediumcannon/nethersteel", true))
		.register();

	public static final BlockEntry<MediumcannonBreechBlock> NETHERSTEEL_MEDIUMCANNON_BREECH = REGISTRATE
		.block("nethersteel_mediumcannon_breech", p -> new MediumcannonBreechBlock(p, CBCModernWarfareMediumcannonMaterials.NETHERSTEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonBreech("mediumcannon/nethersteel", true))
		.register();

	public static final BlockEntry<IncompleteMediumcannonBlock> INCOMPLETE_NETHERSTEEL_MEDIUMCANNON_BREECH = REGISTRATE
		.block("incomplete_nethersteel_mediumcannon_breech", p ->
			IncompleteMediumcannonBlock.breech(p, CBCModernWarfareMediumcannonMaterials.NETHERSTEEL, NETHERSTEEL_MEDIUMCANNON_BREECH, CBCModernWarfareItem.NETHERSTEEL_MEDIUMCANNON_FALLING_BREECH))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.castIronScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.incompleteCannonBreech("mediumcannon/nethersteel", false))
		.register();

	public static final BlockEntry<IncompleteMediumcannonBlock> INCOMPLETE_NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL = REGISTRATE
		.block("incomplete_nethersteel_mediumcannon_recoil_barrel", p ->
			IncompleteMediumcannonBlock.recoilBarrel(p, CBCModernWarfareMediumcannonMaterials.NETHERSTEEL, NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL, CBCModernWarfareItem.HEAVY_RECOIL_SPRING))
		.transform(cannonBlock())
		.loot(CBCBuilderTransformers.castIronScrapLoot(3))
		.transform(CBCModernWarfareBuilderTransformers.mediumcannonRecoilBarrel("mediumcannon/nethersteel", false))
		.register();


	public static final BlockEntry<UnboredMediumcannonBlock> UNBORED_NETHERSTEEL_MEDIUMCANNON_BARREL = REGISTRATE
		.block("unbored_nethersteel_mediumcannon_barrel", p -> UnboredMediumcannonBlock.barrel(p, CBCModernWarfareMediumcannonMaterials.NETHERSTEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(4))
		.transform(CBCModernWarfareBuilderTransformers.unboredMediumcannonBarrel("mediumcannon/unbored_nethersteel"))
		.register();

	public static final BlockEntry<UnboredMediumcannonBlock> UNBORED_NETHERSTEEL_MEDIUMCANNON_BREECH = REGISTRATE
		.block("unbored_nethersteel_mediumcannon_breech", p -> UnboredMediumcannonBlock.breech(p, CBCModernWarfareMediumcannonMaterials.NETHERSTEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(8))
		.transform(CBCModernWarfareBuilderTransformers.unboredMediumcannonBreech("mediumcannon/unbored_nethersteel"))
		.register();

	public static final BlockEntry<UnboredMediumcannonBlock> UNBORED_NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL = REGISTRATE
		.block("unbored_nethersteel_mediumcannon_recoil_barrel", p -> UnboredMediumcannonBlock.recoilBarrel(p, CBCModernWarfareMediumcannonMaterials.NETHERSTEEL))
		.transform(cannonBlock(false))
		.loot(CBCBuilderTransformers.castIronScrapLoot(6))
		.transform(CBCModernWarfareBuilderTransformers.unboredMediumcannonRecoilBarrel("mediumcannon/unbored_nethersteel"))
		.register();





	//Cannon Mount Shit

	public static final BlockEntry<CompactCannonMountBlock> COMPACT_MOUNT = REGISTRATE
		.block("compact_mount", CompactCannonMountBlock::new)
		.transform(cbcMachine())
		.properties(p -> p.isRedstoneConductor(CBCModernWarfareBlocks::never))
		.transform(axeOrPickaxe())
		.transform(CBCModernWarfareBuilderTransformers.compactMount())
		.transform(displaySource(CBCModernWarfareDisplaySources.COMPACT_MOUNT))
		.register();


	//Casting
	public static final BlockEntry<CannonCastMouldBlock> MEDIUMCANNON_BREECH_CAST_MOULD =
	castMould("mediumcannon_breech", Block.box(1, 0, 1, 15, 16, 15), () -> CBCModernWarfareCastShapes.MEDIUMCANNON_BREECH);

	public static final BlockEntry<CannonCastMouldBlock> MEDIUMCANNON_RECOIL_BARREL_CAST_MOULD =
		castMould("mediumcannon_recoil_barrel", Block.box(3, 0, 3, 13, 16, 13), () ->CBCModernWarfareCastShapes.MEDIUMCANNON_RECOIL_BARREL);

	public static final BlockEntry<CannonCastMouldBlock> MEDIUMCANNON_BARREL_CAST_MOULD =
		castMould("mediumcannon_barrel", Block.box(4, 0, 4, 12, 16, 12), () -> CBCModernWarfareCastShapes.MEDIUMCANNON_BREECH);


	public static final BlockEntry<CannonCastMouldBlock> ROTARYCANNON_BREECH_CAST_MOULD =
		castMould("rotarycannon_breech", Block.box(1, 0, 1, 15, 16, 15), () -> CBCModernWarfareCastShapes.MEDIUMCANNON_BREECH);

	public static final BlockEntry<CannonCastMouldBlock> ROTARYCANNON_BEARING_CAST_MOULD =
		castMould("rotarycannon_bearing", Block.box(3, 0, 3, 13, 16, 13), () ->CBCModernWarfareCastShapes.MEDIUMCANNON_RECOIL_BARREL);

	public static final BlockEntry<CannonCastMouldBlock> ROTARYCANNON_BARREL_CAST_MOULD =
		castMould("rotarycannon_barrel", Block.box(4, 0, 4, 12, 16, 12), () -> CBCModernWarfareCastShapes.MEDIUMCANNON_BREECH);



	// Big Cannon Shells\

	public static final BlockEntry<HEFShellBlock> HEF_SHELL = REGISTRATE
		.block("hefrag_shell", HEFShellBlock::new)
		.transform(shell(MapColor.COLOR_RED))
		.transform(axeOrPickaxe())
		.transform(CBCModernWarfareBuilderTransformers.projectile("projectile/hefrag_shell"))
		.transform(CBCBuilderTransformers.safeNbt())
		.loot(CBCBuilderTransformers.shellLoot())
		.lang("High Explosive (HE-F) Shell")
		.item(FuzedProjectileBlockItem::new)
		.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
		.build()
		.register();

	public static final BlockEntry<HEAPShellBlock> HEAP_SHELL = REGISTRATE
		.block("heap_shell", HEAPShellBlock::new)
		.transform(shell(MapColor.COLOR_GREEN))
		.transform(axeOrPickaxe())
		.transform(CBCModernWarfareBuilderTransformers.projectile("projectile/heap_shell"))
		.transform(CBCBuilderTransformers.safeNbt())
		.loot(CBCBuilderTransformers.shellLoot())
		.lang("High Explosive (HEAT) Anti Tank Shell")
		.item(FuzedProjectileBlockItem::new)
		.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
		.build()
		.register();
	public static final BlockEntry<APDSShotBlock> APDS_SHOT = REGISTRATE
		.block("apds_shot", APDSShotBlock::new)
		.transform(shell(MapColor.COLOR_GRAY))
		.transform(axeOrPickaxe())
		.transform(CBCModernWarfareBuilderTransformers.apdsShot())
		.loot(CBCBuilderTransformers.tracerProjectileLoot())
		.lang("Armor Piercing (APDS) Discarding Sabot Shot")
		.item(ProjectileBlockItem::new)
		.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
		.build()
		.register();



//Armor Blocks

	public static final BlockEntry<Block> COMPOSITE_BLOCK = REGISTRATE
		.block("composite_block", Block::new)
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
		.properties(p -> p.strength(20.0F, 600.0F))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
		.tag(BlockTags.NEEDS_STONE_TOOL)
		.lang("Composite Armor Block")
		.transform(CBCModernWarfareBuilderTransformers.compositeBlock())
		.item()
		.build()
		.register();

	public static final BlockEntry<Block> CARBON_STEEL_BLOCK = REGISTRATE
		.block("carbon_steel_block",Block::new)
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
		.properties(p -> p.strength(8.0F, 24.0F))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.simpleItem()
		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
		.tag(BlockTags.NEEDS_STONE_TOOL)
		.item()
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.BLOCK_CARBON_STEEL)
		.build()
		.register();

	public static final BlockEntry<StairBlock> CARBON_STEEL_STAIRS = REGISTRATE
		.block("carbon_steel_stairs", properties ->
			new StairBlock(Blocks.BRICK_STAIRS.defaultBlockState(), properties))
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
		.properties(p -> p.strength(8.0F, 22.0F))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.item().tag(ItemTags.STAIRS).build()
		.tag(BlockTags.STAIRS)
		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
		.tag(BlockTags.NEEDS_STONE_TOOL)
		.blockstate((ctx, prov) -> prov.stairsBlock(ctx.get(),
			prov.modLoc("block/carbon_steel_block")))
		.item()
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.BLOCK_CARBON_STEEL)
		.build()
		.register();


	public static final BlockEntry<SlabBlock> CARBON_STEEL_SLAB = REGISTRATE
		.block("carbon_steel_slab", SlabBlock::new)
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(p -> p.strength(8.0F, 22.0F))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
		.item().tag(ItemTags.SLABS).build()
		.tag(BlockTags.SLABS)
		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
		.tag(BlockTags.NEEDS_STONE_TOOL)
		.blockstate((ctx, prov) -> prov.slabBlock(ctx.get(),
			prov.modLoc("block/carbon_steel_block"),
			prov.modLoc("block/carbon_steel_block")))
		.item()
		.tag(CBCModernWarfareTags.CBCModernWarfareItemTags.BLOCK_CARBON_STEEL)
		.build()
		.register();


	public static final BlockEntry<ERABlock> ERA_BLOCK = REGISTRATE
		.block("era_block", ERABlock::new)
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
		.properties(p -> p.strength(15.0F, 12.0F))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.simpleItem()
		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
		.transform(CBCModernWarfareBuilderTransformers.reactiveArmorBlock(""))
		.tag(BlockTags.NEEDS_STONE_TOOL)
		.register();

	public static final BlockEntry<ERAStairBlock> ERA_STAIRS = REGISTRATE
		.block("era_stairs", p -> new ERAStairBlock(Blocks.BRICK_STAIRS.defaultBlockState(), p))
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(p -> p.strength(15.0F, 12.0F))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
		.item().tag(ItemTags.STAIRS).build()
		.tag(BlockTags.STAIRS)
		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
		.tag(BlockTags.NEEDS_STONE_TOOL)
		.blockstate((ctx, prov) ->
			prov.stairsBlock(ctx.get(), ctx.getName(), prov.modLoc("block/tiled_reactive_armor"),
				prov.modLoc("block/tiled_reactive_armor_bottom"),prov.modLoc("block/tiled_reactive_armor")))
		.register();


	public static final BlockEntry<ERASlabBlock> ERA_SLAB = REGISTRATE
		.block("era_slab", ERASlabBlock::new)
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(p -> p.strength(10.0F, 12.0F))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
		.item().tag(ItemTags.SLABS).build()
		.tag(BlockTags.SLABS)
		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
		.tag(BlockTags.NEEDS_STONE_TOOL)
		.blockstate((ctx, prov) -> prov.slabBlock(ctx.get(),
			prov.modLoc("block/tiled_reactive_slab"),
			prov.modLoc("block/tiled_reactive_armor")))
		.register();


	//Tan Era

	public static final BlockEntry<ERABlock> TAN_ERA_BLOCK = REGISTRATE
		.block("tan_era_block",ERABlock::new)
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(p -> p.strength(10.0F, 12.0F))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
		.simpleItem()
		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
		.tag(BlockTags.NEEDS_STONE_TOOL)
		.transform(CBCModernWarfareBuilderTransformers.reactiveArmorBlock("_tan"))
		.register();

	public static final BlockEntry<ERAStairBlock> TAN_ERA_STAIRS = REGISTRATE
		.block("tan_era_stairs", p -> new ERAStairBlock(Blocks.BRICK_STAIRS.defaultBlockState(), p))
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(p -> p.strength(10.0F, 12.0F))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
		.item().tag(ItemTags.STAIRS).build()
		.tag(BlockTags.STAIRS)
		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
		.tag(BlockTags.NEEDS_STONE_TOOL)
		.blockstate((ctx, prov) ->
			prov.stairsBlock(ctx.get(), ctx.getName(), prov.modLoc("block/tiled_tan_reactive_armor"),
				prov.modLoc("block/tiled_tan_reactive_armor_bottom"),prov.modLoc("block/tiled_tan_reactive_armor")))
		.register();


	public static final BlockEntry<ERASlabBlock> TAN_ERA_SLAB = REGISTRATE
		.block("tan_era_slab", ERASlabBlock::new)
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(p -> p.strength(10.0F, 12.0F))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
		.item().tag(ItemTags.SLABS).build()
		.tag(BlockTags.SLABS)
		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
		.tag(BlockTags.NEEDS_STONE_TOOL)
		.blockstate((ctx, prov) -> prov.slabBlock(ctx.get(),
			prov.modLoc("block/tiled_tan_reactive_slab"),
			prov.modLoc("block/tiled_tan_reactive_armor")))
		.register();


	//Green Era

	public static final BlockEntry<ERABlock> GREEN_ERA_BLOCK = REGISTRATE
		.block("green_era_block",ERABlock::new)
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(p -> p.strength(10.0F, 12.0F))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
		.simpleItem()
		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
		.tag(BlockTags.NEEDS_STONE_TOOL)
		.transform(CBCModernWarfareBuilderTransformers.reactiveArmorBlock("_green"))
		.register();

	public static final BlockEntry<ERAStairBlock> GREEN_ERA_STAIRS = REGISTRATE
		.block("green_era_stairs",  p -> new ERAStairBlock(Blocks.BRICK_STAIRS.defaultBlockState(), p))
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(p -> p.strength(10.0F, 12.0F))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
		.item().tag(ItemTags.STAIRS).build()
		.tag(BlockTags.STAIRS)
		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
		.tag(BlockTags.NEEDS_STONE_TOOL)
		.blockstate((ctx, prov) ->
			prov.stairsBlock(ctx.get(), ctx.getName(), prov.modLoc("block/tiled_green_reactive_armor"),
				prov.modLoc("block/tiled_green_reactive_armor_bottom"),prov.modLoc("block/tiled_green_reactive_armor")))
		.register();


	public static final BlockEntry<ERASlabBlock> GREEN_ERA_SLAB = REGISTRATE
		.block("green_era_slab", ERASlabBlock::new)
		.initialProperties(() -> Blocks.NETHERITE_BLOCK)
		.properties(p -> p.strength(10.0F, 12.0F))
		.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
		.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
		.item().tag(ItemTags.SLABS).build()
		.tag(BlockTags.SLABS)
		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
		.tag(BlockTags.NEEDS_STONE_TOOL)
		.blockstate((ctx, prov) -> prov.slabBlock(ctx.get(),
			prov.modLoc("block/tiled_green_reactive_slab"),
			prov.modLoc("block/tiled_green_reactive_armor")))
		.register();




	//Methods


	private static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> cbcMachine() {
		return b -> b.initialProperties(() -> Blocks.GOLD_BLOCK)
			.properties(p -> p.mapColor(MapColor.PODZOL))
			.properties(p -> p.sound(SoundType.NETHERITE_BLOCK));
	}


	private static BlockEntry<CannonCastMouldBlock> castMould(String name, VoxelShape blockShape, Supplier<CannonCastShape> castShape) {
		return REGISTRATE.block(name + "_cast_mould", p -> new CannonCastMouldBlock(p, blockShape, castShape))
			.transform(CBCModernWarfareBuilderTransformers.castMould(name))
			.register();
	}

	private static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> cannonBlock() {
		return cannonBlock(true);
	}
	private static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> cannonBlock(boolean canPassThrough) {
		NonNullUnaryOperator<BlockBuilder<T, P>> transform = b -> b.properties(p -> p.strength(5.0f, 6.0f))
			.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
			.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
			.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.tag(BlockTags.NEEDS_IRON_TOOL);
		return canPassThrough ? transform.andThen(b -> b.tag(CBCTags.CBCBlockTags.DRILL_CAN_PASS_THROUGH)) : transform;
	}

	private static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> shell(MapColor color) {
		return b -> b.addLayer(() -> RenderType::solid)
			.properties(p -> p.mapColor(color))
			.properties(p -> p.strength(2.0f, 3.0f))
			.properties(p -> p.sound(SoundType.STONE));
	}

	private static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> warhead(MapColor color) {
		return b -> b.addLayer(() -> RenderType::solid)
			.properties(p -> p.mapColor(color))
			.properties(p -> p.strength(2.0f, 3.0f))
			.properties(p -> p.sound(SoundType.STONE));
	}

	private static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> axeOrPickaxe() {
		return b -> b.tag(BlockTags.MINEABLE_WITH_AXE)
			.tag(BlockTags.MINEABLE_WITH_PICKAXE);
	}


	private static BlockState castIronMediumcannonBarrel(Direction facing) {
		return CAST_IRON_MEDIUMCANNON_BARREL.getDefaultState().setValue(MediumcannonBarrelBlock.FACING, facing);
	}
	private static BlockState bronzeMediumcannonBarrel(Direction facing) {
		return BRONZE_MEDIUMCANNON_BARREL.getDefaultState().setValue(MediumcannonBarrelBlock.FACING, facing);
	}
	private static BlockState steelMediumcannonBarrel(Direction facing) {
		return STEEL_MEDIUMCANNON_BARREL.getDefaultState().setValue(MediumcannonBarrelBlock.FACING, facing);
	}

	private static BlockState nethersteelMediumcannonBarrel(Direction facing) {
		return NETHERSTEEL_MEDIUMCANNON_BARREL.getDefaultState().setValue(MediumcannonBarrelBlock.FACING, facing);
	}


	private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return false;
	}

	public static void register() {
	}
}
