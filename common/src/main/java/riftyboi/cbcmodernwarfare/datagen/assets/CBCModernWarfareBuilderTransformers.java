package riftyboi.cbcmodernwarfare.datagen.assets;



import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import rbasamoyai.createbigcannons.CBCTags;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.sliding_breech.SlidingBreechBlockGen;
import rbasamoyai.createbigcannons.index.fluid_utils.CBCFlowingFluid;
import rbasamoyai.createbigcannons.index.fluid_utils.FluidBuilder;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBarrelBlock;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBlock;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBlockItem;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlockItem;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBarrelBlock;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBlock;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBlockItem;
import riftyboi.cbcmodernwarfare.munitions.autocannon.ammo_drum.AutocannonAmmoDrumBlock;
import riftyboi.cbcmodernwarfare.munitions.autocannon.ammo_drum.AutocannonAmmoDrumItem;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonRoundItem;

public class CBCModernWarfareBuilderTransformers {

	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> slidingBreechIncomplete(String pathAndMaterial) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> slidingBreechUnbored(String pathAndMaterial) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block & MunitionsLauncherBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> slidingBreech(String pathAndMaterial) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> castMould(String size) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> autocannonAmmoDrum(boolean isCreative) { throw new AssertionError(); }

	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> cannonBarrel(String material, boolean bored) { throw new AssertionError(); }

	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> builtUpCannonBarrel(String material, boolean bored) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> munitonsLauncherBlock(ResourceLocation model, String texture) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> cannonChamber(String material, boolean bored) { throw new AssertionError(); }

	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> projectileLegacy(String pathAndMaterial) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> projectile(String pathAndMaterial) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> projectile(String pathAndMaterial, boolean useStandardModel) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Item, P> NonNullUnaryOperator<ItemBuilder<T, P>> fuzedProjectileItem(String pathAndMaterial) { throw new AssertionError(); }

	@ExpectPlatform public static <T extends Item, P> NonNullUnaryOperator<ItemBuilder<T, P>> liquidBucket(String pathAndMaterial) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> compactMount() { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> compositeBlock() { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> reactiveArmorBlock(String material) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> slabBlock(String material) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> stairsBlock(String material) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends MediumcannonRoundItem, P> NonNullUnaryOperator<ItemBuilder<T, P>> mediumRound(String path) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends MediumcannonCartridgeItem, P> NonNullUnaryOperator<ItemBuilder<T, P>> mediumCartridge(String path) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block & MunitionsLauncherBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> torpedoScrewBreech(String pathAndMaterial) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> apdsShot() { throw new AssertionError(); }

	@ExpectPlatform public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> mediumcannonBarrel(String pathAndMaterial) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> mediumcannonBreech(String pathAndMaterial, boolean complete) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> incompleteCannonBreech(String pathAndMaterial, boolean complete) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> mediumcannonRecoilBarrel(String pathAndMaterial, boolean complete) { throw new AssertionError(); }

	@ExpectPlatform public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredMediumcannonBarrel(String pathAndMaterial) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredMediumcannonRecoilBarrel(String pathAndMaterial) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredMediumcannonBreech(String pathAndMaterial) { throw new AssertionError(); }

	private static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredMediumcannonBlock(ResourceLocation tex, ResourceLocation tex1, ResourceLocation model) { throw new AssertionError(); }

	@ExpectPlatform public static <T extends Item, P> NonNullUnaryOperator<ItemBuilder<T, P>> mediumcannonBreechBlock(String pathAndMaterial) { throw new AssertionError(); }

	@ExpectPlatform public static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> rotarycannonBarrel(String pathAndMaterial) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> rotarycannonBreech(String pathAndMaterial, boolean complete) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> rotarycannonBearing(String pathAndMaterial, boolean complete) { throw new AssertionError(); }

	@ExpectPlatform public static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredRotarycannonBarrel(String pathAndMaterial) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredRotarycannonBearing(String pathAndMaterial) { throw new AssertionError(); }
	@ExpectPlatform public static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredRotarycannonBreech(String pathAndMaterial) { throw new AssertionError(); }

	private static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredRotarycannonBlock(ResourceLocation tex, ResourceLocation tex1, ResourceLocation model) { throw new AssertionError(); }


}
