package riftyboi.cbcmodernwarfare.datagen.assets.forge;
import com.simibubi.create.AllTags.AllBlockTags;
import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import rbasamoyai.createbigcannons.CBCTags;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastMouldBlock;
import rbasamoyai.createbigcannons.index.fluid_utils.CBCFlowingFluid;;
import rbasamoyai.createbigcannons.index.fluid_utils.FluidBuilder;
import rbasamoyai.createbigcannons.utils.CBCUtils;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.breech.MediumcannonBreechBlock;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.recoil_barrel.MediumcannonRecoilBarrelBlock;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBarrelBlock;
import riftyboi.cbcmodernwarfare.multiloader.IncompleteSlidingBreechBlockGen;
import riftyboi.cbcmodernwarfare.multiloader.SlidingBreechBlockGen;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBarrelBlock;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBlock;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBlockItem;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlockItem;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBlock;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBlockItem;
import riftyboi.cbcmodernwarfare.munitions.autocannon.ammo_drum.AutocannonAmmoDrumBlock;
import riftyboi.cbcmodernwarfare.munitions.autocannon.ammo_drum.AutocannonAmmoDrumItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonRoundItem;

@SuppressWarnings("removal")
public class CBCModernWarfareBuilderTransformersImpl {


	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> cannonBarrel(String material, boolean bored) {
		NonNullUnaryOperator<BlockBuilder<T, P>> b1 = cannonPart(CBCModernWarfare.resource("block/cannon_barrel"),
			"cannon_barrel/" + material + "_barrel_side",
			"cannon_barrel/" + (bored ? "" : "unbored_") + material + "_barrel_end");
		return bored ? b1.andThen(b -> b.tag(CBCTags.CBCBlockTags.REDUCES_SPREAD)) : b1;
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> builtUpCannonBarrel(String material, boolean bored) {
		NonNullUnaryOperator<BlockBuilder<T, P>> b1 = cannonPart(CBCModernWarfare.resource("block/built_up_cannon_barrel"),
			"cannon_barrel/built_up_" + material + "_barrel_side",
			"cannon_barrel/" + (bored ? "" : "unbored_") + "built_up_" + material + "_barrel_end");
		return bored ? b1.andThen(b -> b.tag(CBCTags.CBCBlockTags.REDUCES_SPREAD)) : b1;
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> cannonChamber(String material, boolean bored) {
		NonNullUnaryOperator<BlockBuilder<T, P>> b1 = cannonPart(CBCUtils.location("block/cube_column"),
			"cannon_chamber/" + material + "_chamber_side",
			"cannon_chamber/" + (bored ? "" : "unbored_") + material + "_chamber_end");
		return bored ? b1.andThen(b -> b.tag(CBCTags.CBCBlockTags.THICK_TUBING).tag(CBCTags.CBCBlockTags.REDUCES_SPREAD)) : b1;
	}



	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> cannonPart(ResourceLocation model, String side, String end) {
		ResourceLocation sideLoc = CBCModernWarfare.resource("block/" + side);
		ResourceLocation endLoc = CBCModernWarfare.resource("block/" + end);
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> p.directionalBlock(c.get(), p.models().withExistingParent(c.getName(), model)
				.texture("side", sideLoc)
				.texture("end", endLoc)
				.texture("particle", sideLoc)))
			.tag(AllBlockTags.SAFE_NBT.tag);
	}


	public static <T extends Block & MunitionsLauncherBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> slidingBreech(String pathAndMaterial) {
		ResourceLocation itemBaseLoc = CBCModernWarfare.resource("block/sliding_breech_item");
		ResourceLocation holeLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_sliding_breech_hole");
		ResourceLocation sideLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_sliding_breech_side");
		ResourceLocation sideHoleLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_sliding_breech_side_hole");
		ResourceLocation insideLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_sliding_breech_inside");
		ResourceLocation breechblockTopLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_sliding_breech_breechblock_top");
		ResourceLocation breechblockEndLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_sliding_breech_breechblock_end");
		ResourceLocation breechblockSideLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_sliding_breech_breechblock_side");
		ResourceLocation breechblockBottomLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_sliding_breech_breechblock_bottom");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate(SlidingBreechBlockGen.create(pathAndMaterial)::generate)
			.tag(AllBlockTags.SAFE_NBT.tag)
			.item(MunitionsLauncherBlockItem::new)
			.model((c, p) -> p.getBuilder(c.getName()).parent(p.getExistingFile(itemBaseLoc))
				.texture("hole", holeLoc)
				.texture("side", sideLoc)
				.texture("side_hole", sideHoleLoc)
				.texture("inside", insideLoc)
				.texture("breechblock_top", breechblockTopLoc)
				.texture("breechblock_end", breechblockEndLoc)
				.texture("breechblock_side", breechblockSideLoc)
				.texture("breechblock_bottom", breechblockBottomLoc))
			.build();
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> munitonsLauncherBlock(ResourceLocation model, String texture) {
		ResourceLocation sideLoc = CBCModernWarfare.resource("block/" + texture);
		ResourceLocation topLoc = CBCModernWarfare.resource("block/" + texture + "_top");
		ResourceLocation bottomLoc = CBCModernWarfare.resource("block/" + texture + "_bottom");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> p.directionalBlock(c.get(),p.models().withExistingParent(c.getName(),model)
				.texture("side", sideLoc)
				.texture("bottom", bottomLoc)
				.texture("top", topLoc)
				.texture("particle", topLoc)));
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> compositeBlock() {
		ResourceLocation model = CBCModernWarfare.resource("block/sided_block");
		ResourceLocation sideLoc = CBCModernWarfare.resource("block/composite_armor_side");
		ResourceLocation bottomLoc = CBCModernWarfare.resource("block/composite_armor_bottom");
		ResourceLocation topLoc = CBCModernWarfare.resource("block/composite_armor");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> p.simpleBlock(c.get(), p.models().withExistingParent(c.getName(), model)
				.texture("side", sideLoc)
				.texture("bottom", bottomLoc)
				.texture("top", topLoc)
				.texture("particle", topLoc)));
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> reactiveArmorBlock(String name) {
		ResourceLocation model = CBCModernWarfare.resource("block/reactive_block");
		ResourceLocation sideLoc = CBCModernWarfare.resource("block/tiled" + name + "_reactive_armor");
		ResourceLocation bottomLoc = CBCModernWarfare.resource("block/tiled" + name + "_reactive_armor_bottom");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> p.simpleBlock(c.get(), p.models().withExistingParent(c.getName(), model)
				.texture("side", sideLoc)
				.texture("bottom", bottomLoc)
				.texture("particle", sideLoc)));
	}



	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> slidingBreechUnbored(String pathAndMaterial) {
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate(SlidingBreechBlockGen.create(pathAndMaterial)::generate)
			.tag(AllBlockTags.SAFE_NBT.tag);
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> slidingBreechIncomplete(String pathAndMaterial) {
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate(IncompleteSlidingBreechBlockGen.create(pathAndMaterial)::generate)
			.tag(AllBlockTags.SAFE_NBT.tag);
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> autocannonAmmoDrum(boolean isCreative) {
		String root = isCreative ? "creative": "regular";
		return b -> b.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> p.getVariantBuilder(c.get())
				.forAllStatesExcept(state -> {
					AutocannonAmmoDrumBlock.State containerState = state.getValue(AutocannonAmmoDrumBlock.CONTAINER_STATE);
					String suffix = switch (containerState) {
						case CLOSED -> "";
						case EMPTY -> "_empty";
						case FILLED -> "_filled";
					};
					ResourceLocation loc = p.modLoc("block/autocannon_ammo_drum/" + root + suffix);
					Direction.Axis axis = state.getValue(BlockStateProperties.HORIZONTAL_AXIS);
					return ConfiguredModel.builder()
						.modelFile(p.models().getExistingFile(loc))
						.rotationY(axis == Direction.Axis.X ? 90 : 0)
						.build();
				}, BlockStateProperties.WATERLOGGED))
			.loot((t, c) -> {
				CopyNbtFunction.Builder func = CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
					.copy("Ammo", "Ammo")
					.copy("Tracers", "Tracers")
					.copy("TracerSpacing", "TracerSpacing");
				if (isCreative)
					func = func.copy("CurrentIndex", "CurrentIndex");
				t.add(c, LootTable.lootTable()
					.withPool(t.applyExplosionCondition(c, LootPool.lootPool()
							.add(LootItem.lootTableItem(c))
							.apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)))
						.apply(func)));
			})
			.tag(AllBlockTags.SAFE_NBT.tag)
			.item(AutocannonAmmoDrumItem::new)
			.properties(p -> p.stacksTo(1))
			.properties(p -> isCreative ? p.rarity(Rarity.EPIC) : p)
			.tag(CBCTags.CBCItemTags.AUTOCANNON_AMMO_CONTAINERS)
			.model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/autocannon_ammo_drum/" + root)))
			.build();
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> apdsShot() {
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutout)
			.blockstate((c, p) -> p.directionalBlock(c.get(), p.models().getExistingFile(CBCModernWarfare.resource("block/apds_shot"))));
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> castMould(String size) {
		ResourceLocation baseLoc = CBCModernWarfare.resource("block/cast_mould/" + size + "_cast_mould");
		ResourceLocation sandLoc = CBCModernWarfare.resource("block/casting_sand");
		return b -> b.properties(p -> p.mapColor(MapColor.PODZOL))
			.properties(p -> p.strength(2.0f, 3.0f))
			.properties(p -> p.sound(SoundType.WOOD))
			.properties(p -> p.noOcclusion())
			.tag(BlockTags.MINEABLE_WITH_AXE)
			.tag(AllBlockTags.SAFE_NBT.tag)
			.addLayer(() -> RenderType::solid)
			.blockstate((c, p) -> p.getMultipartBuilder(c.get())
				.part()
				.modelFile(p.models().getExistingFile(baseLoc))
				.addModel()
				.end()
				.part()
				.modelFile(p.models().getExistingFile(sandLoc))
				.addModel()
				.condition(CannonCastMouldBlock.SAND, true)
				.end())
			.item()
			.model((c, p) -> p.getBuilder(c.getName()).parent(p.getExistingFile(baseLoc)))
			.build();
	}


	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> projectileLegacy(String pathAndMaterial) {
		return projectile(pathAndMaterial, true);
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> projectile(String pathAndMaterial) {
		return projectile(pathAndMaterial, false);
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> projectile(String pathAndMaterial, boolean useStandardModel) {
		ResourceLocation baseLoc = CBCModernWarfare.resource(String.format("block/%sprojectile_block", useStandardModel ? "standard_" : ""));
		ResourceLocation sideLoc = CBCModernWarfare.resource("block/" + pathAndMaterial);
		ResourceLocation topLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_top");
		ResourceLocation bottomLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_bottom");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::solid)
			.blockstate((c, p) -> {
				BlockModelBuilder builder = p.models().withExistingParent(c.getName(), baseLoc)
					.texture("side", sideLoc)
					.texture("top", topLoc)
					.texture("particle", topLoc);
				if (!useStandardModel) builder.texture("bottom", bottomLoc);
				p.directionalBlock(c.get(), builder);
			});
	}

	public static <T extends Item, P> NonNullUnaryOperator<ItemBuilder<T, P>> fuzedProjectileItem(String pathAndMaterial) {
		ResourceLocation headFuzeLoc = CBCModernWarfare.resource("block/projectile_block_fuze_head");
		ResourceLocation baseFuzeLoc = CBCModernWarfare.resource("block/projectile_block_fuze_base");
		ResourceLocation sideLoc = CBCModernWarfare.resource("block/" + pathAndMaterial);
		ResourceLocation topLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_top");
		ResourceLocation bottomLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_bottom");
		return b -> b.model((c, p) -> {
			var headFuzeModel = p.withExistingParent(c.getName() + "_head_fuze", headFuzeLoc)
				.texture("side", sideLoc)
				.texture("top", topLoc)
				.texture("bottom", bottomLoc)
				.texture("particle", topLoc);
			var baseFuzeModel = p.withExistingParent(c.getName() + "_base_fuze", baseFuzeLoc)
				.texture("side", sideLoc)
				.texture("top", topLoc)
				.texture("bottom", bottomLoc)
				.texture("particle", topLoc);
			p.blockItem(c)
				.override().model(headFuzeModel).predicate(CBCModernWarfare.resource("fuze_state"), 1).end()
				.override().model(baseFuzeModel).predicate(CBCModernWarfare.resource("fuze_state"), 2).end();
		});
	}
	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> compactMount() {
		ResourceLocation baseLoc = CBCModernWarfare.resource("block/compact_mount/compact_mount");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> p.horizontalBlock(c.get(), p.models().getExistingFile(baseLoc), 0))
			.tag(AllBlockTags.SAFE_NBT.tag)
			.item()
			.model((c, p) -> {})
			.build();
	}
	public static <T extends Block & MunitionsLauncherBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> torpedoScrewBreech(String pathAndMaterial) {
		ResourceLocation baseLoc = CBCModernWarfare.resource("block/screw_breech");
		ResourceLocation itemBaseLoc = CBCModernWarfare.resource("block/screw_breech_item");
		ResourceLocation topLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_screw_breech_top");
		ResourceLocation bottomLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_screw_breech_bottom");
		ResourceLocation sideLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_screw_breech_side");
		ResourceLocation lockLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_screw_lock");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> p.directionalBlock(c.get(), p.models().withExistingParent(c.getName(), baseLoc)
				.texture("side", sideLoc)
				.texture("top", topLoc)
				.texture("bottom", bottomLoc)
				.texture("particle", topLoc)))
			.tag(AllBlockTags.SAFE_NBT.tag)
			.item(MunitionsLauncherBlockItem::new)
			.model((c, p) -> p.getBuilder(c.getName()).parent(p.getExistingFile(itemBaseLoc))
				.texture("side", sideLoc)
				.texture("top", topLoc)
				.texture("bottom", bottomLoc)
				.texture("lock", lockLoc))
			.build();
	}

	public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> mediumcannonBarrel(String pathAndMaterial) {
		ResourceLocation texLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_medium_barrel");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> BlockStateGen.directionalBlockIgnoresWaterlogged(c, p, s -> {
				String name = c.getName() + "_" + (s.getValue(MediumcannonBarrelBlock.ASSEMBLED) ? "assembled" : s.getValue(MediumcannonBarrelBlock.BARREL_END).getSerializedName());
				if (s.getValue(MediumcannonBarrelBlock.ASSEMBLED))
					return p.models().getBuilder(name).texture("particle", texLoc);
				ResourceLocation loc = switch (s.getValue(MediumcannonBarrelBlock.BARREL_END)) {
					case FLANGED -> CBCModernWarfare.resource("block/mediumcannon/barrel_flanged");
					default -> CBCModernWarfare.resource("block/mediumcannon/barrel");
				};
				return p.models().withExistingParent(name, loc).texture("material", texLoc);
			}))
			.tag(AllBlockTags.SAFE_NBT.tag)
			.item(MediumcannonBlockItem::new)
			.model((c, p) -> p.withExistingParent(c.getName(), CBCModernWarfare.resource("block/mediumcannon/barrel")).texture("material", texLoc))
			.build();
	}




	public static <T extends MediumcannonRoundItem, P> NonNullUnaryOperator<ItemBuilder<T, P>> mediumRound(String path) {
		ResourceLocation texLoc = CBCModernWarfare.resource("item/" + path);
		return b -> b.model((c, p) -> p.getBuilder(c.getName())
			.parent(p.getExistingFile(CBCModernWarfare.resource("item/" + path + "_mediumcannon_round"))));
	}
	public static <T extends MediumcannonCartridgeItem, P> NonNullUnaryOperator<ItemBuilder<T, P>> mediumCartridge(String path) {
		ResourceLocation texLoc = CBCModernWarfare.resource("item/" + path);
		return b -> b.model((c, p) -> p.getBuilder(c.getName())
			.parent(p.getExistingFile(CBCModernWarfare.resource("item/" + path + "_mediumcannon_cartridge"))));
	}


	public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> mediumcannonBreech(String pathAndMaterial, boolean complete) {
		ResourceLocation texLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_medium_breech");
		ResourceLocation baseLoc = CBCModernWarfare.resource("block/mediumcannon/breech");
		NonNullUnaryOperator<BlockBuilder<T, P>> result = b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> BlockStateGen.directionalBlockIgnoresWaterlogged(c, p, s ->
				{
					if (complete) {
						if (s.getValue(MediumcannonBreechBlock.ASSEMBLED))
							return p.models().getBuilder(baseLoc + "_assembled").texture("particle", texLoc);
					}
					boolean axisAlongFirst = s.getValue(DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE);
					boolean rotated = (s.getValue(BlockStateProperties.FACING).getAxis() == Direction.Axis.X) == axisAlongFirst;
					String suf = rotated ? "_rotated" : "";
					return p.models().withExistingParent(c.getName() + suf, baseLoc + suf).texture("material", texLoc); }))
			.tag(AllBlockTags.SAFE_NBT.tag);
		if (complete) {
			result = result.andThen(b -> b.item(MediumcannonBlockItem::new)
				.model((c, p) -> p.withExistingParent(c.getName(), CBCModernWarfare.resource("block/mediumcannon/breech_item")).texture("material", texLoc))
				.build());
		} else {
			result = result.andThen(b -> b.item(MediumcannonBlockItem::new)
				.model((c, p) -> p.blockItem(c))
				.build());
		}
		return result;
	}

	public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> incompleteCannonBreech(String pathAndMaterial, boolean complete) {
		ResourceLocation texLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_medium_breech");
		ResourceLocation baseLoc = CBCModernWarfare.resource("block/mediumcannon/breech");
		NonNullUnaryOperator<BlockBuilder<T, P>> result = b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> BlockStateGen.directionalBlockIgnoresWaterlogged(c, p, s ->
				p.models().withExistingParent(c.getName(), baseLoc).texture("material", texLoc)))
			.tag(AllBlockTags.SAFE_NBT.tag);
		result = result.andThen(b -> b.item(MediumcannonBlockItem::new)
			.model((c, p) -> p.blockItem(c))
			.build());
		return result;
	}


	public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> mediumcannonRecoilBarrel(String pathAndMaterial, boolean complete) {
		ResourceLocation texLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_medium_recoil_barrel");
		ResourceLocation baseLoc = CBCModernWarfare.resource("block/mediumcannon/recoil_barrel");
		NonNullUnaryOperator<BlockBuilder<T, P>> result = b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> BlockStateGen.directionalBlockIgnoresWaterlogged(c, p,
				s -> {
					if (complete) {
						if (s.getValue(MediumcannonRecoilBarrelBlock.ASSEMBLED))
							return p.models().getBuilder(baseLoc + "_assembled").texture("particle", texLoc);
					}
					return p.models().withExistingParent(c.getName(), CBCModernWarfare.resource("block/mediumcannon/recoil_barrel")).texture("material", texLoc);
				}))
			.tag(AllBlockTags.SAFE_NBT.tag);
		if (complete) {
			result = result.andThen(b -> b.item(MediumcannonBlockItem::new)
				.model((c, p) -> p.withExistingParent(c.getName(), CBCModernWarfare.resource("block/mediumcannon/recoil_barrel")).texture("material", texLoc))
				.build());
		} else {
			result = result.andThen(b -> b.item(MediumcannonBlockItem::new)
				.model((c, p) -> p.blockItem(c))
				.build());
		}
		return result;
	}

	public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredMediumcannonBarrel(String pathAndMaterial) {
		return unboredMediumcannonBlock(CBCModernWarfare.resource("block/" + pathAndMaterial + "_medium_barrel"),
			CBCModernWarfare.resource("block/mediumcannon/barrel"));
	}

	public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredMediumcannonRecoilBarrel(String pathAndMaterial) {
		return unboredMediumcannonBlock(CBCModernWarfare.resource("block/" + pathAndMaterial + "_medium_recoil_barrel"),
			CBCModernWarfare.resource("block/mediumcannon/recoil_barrel"));
	}

	public static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredMediumcannonBreech(String pathAndMaterial) {
		return unboredMediumcannonBlock(CBCModernWarfare.resource("block/" + pathAndMaterial + "_medium_breech"),
			CBCModernWarfare.resource("block/mediumcannon/breech"));
	}

	private static <T extends Block & MediumcannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredMediumcannonBlock(ResourceLocation tex, ResourceLocation model) {
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> BlockStateGen.directionalBlockIgnoresWaterlogged(c, p,
				$ -> p.models().withExistingParent(c.getName(), model)
					.texture("material", tex)))
			.tag(AllBlockTags.SAFE_NBT.tag)
			.item(MediumcannonBlockItem::new)
			.model((c, p) -> p.blockItem(c))
			.build();
	}

	public static <T extends Item, P> NonNullUnaryOperator<ItemBuilder<T, P>> mediumcannonBreechBlock(String pathAndMaterial) {
		return b -> b.model((c, p) -> p.getBuilder(c.getName())
			.parent(p.getExistingFile(CBCModernWarfare.resource("block/mediumcannon/falling_breech")))
			.texture("material", CBCModernWarfare.resource("block/" + pathAndMaterial + "_breech")));
	}

	public static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> rotarycannonBarrel(String pathAndMaterial) {
		ResourceLocation texLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_rotarycannon_barrel");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> BlockStateGen.directionalBlockIgnoresWaterlogged(c, p, s -> {
				String name = c.getName() + "_" + (s.getValue(RotarycannonBarrelBlock.ASSEMBLED) ? "assembled" : s.getValue(RotarycannonBarrelBlock.BARREL_END).getSerializedName());
				if (s.getValue(RotarycannonBarrelBlock.ASSEMBLED))
					return p.models().getBuilder(name).texture("particle", texLoc);
				ResourceLocation loc = switch (s.getValue(RotarycannonBarrelBlock.BARREL_END)) {
					case FLANGED -> CBCModernWarfare.resource("block/rotarycannon/barrel_flanged");
					default -> CBCModernWarfare.resource("block/rotarycannon/barrel");
				};
				return p.models().withExistingParent(name, loc).texture("material", texLoc);
			}))
			.tag(AllBlockTags.SAFE_NBT.tag)
			.item(RotarycannonBlockItem::new)
			.model((c, p) -> p.withExistingParent(c.getName(), CBCModernWarfare.resource("block/rotarycannon/barrel")).texture("material", texLoc))
			.build();
	}

	public static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> rotarycannonBreech(String pathAndMaterial, boolean complete) {
		ResourceLocation texLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_rotarycannon_breech");
		ResourceLocation baseLoc = CBCModernWarfare.resource("block/rotarycannon/breech");
		NonNullUnaryOperator<BlockBuilder<T, P>> result = b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> BlockStateGen.directionalBlockIgnoresWaterlogged(c, p,
				s -> p.models().withExistingParent(c.getName(), baseLoc)
					.texture("material", texLoc)))
			.tag(AllBlockTags.SAFE_NBT.tag);
		if (complete) {
			result = result.andThen(b -> b.item(RotarycannonBlockItem::new)
				.model((c, p) -> p.withExistingParent(c.getName(), CBCModernWarfare.resource("block/rotarycannon/breech_item")).texture("material", texLoc))
				.build());
		} else {
			result = result.andThen(b -> b.item(RotarycannonBlockItem::new)
				.model((c, p) -> p.blockItem(c))
				.build());
		}
		return result;
	}

	public static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> rotarycannonBearing(String pathAndMaterial, boolean complete) {
		ResourceLocation texLoc = CBCModernWarfare.resource("block/" + pathAndMaterial + "_rotarycannon_bearing");
		NonNullUnaryOperator<BlockBuilder<T, P>> result = b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> BlockStateGen.directionalBlockIgnoresWaterlogged(c, p,
				$ -> p.models().withExistingParent(c.getName(), CBCModernWarfare.resource("block/rotarycannon/bearing")).texture("material", texLoc)))
			.tag(AllBlockTags.SAFE_NBT.tag);
		if (complete) {
			result = result.andThen(b -> b.item(RotarycannonBlockItem::new)
				.model((c, p) -> p.withExistingParent(c.getName(), CBCModernWarfare.resource("block/rotarycannon/bearing")).texture("material", texLoc))
				.build());
		} else {
			result = result.andThen(b -> b.item(RotarycannonBlockItem::new)
				.model((c, p) -> p.blockItem(c))
				.build());
		}
		return result;
	}

	public static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredRotarycannonBarrel(String pathAndMaterial) {
		return unboredRotarycannonBlock(CBCModernWarfare.resource("block/" + pathAndMaterial + "_rotarycannon_barrel"),
			CBCModernWarfare.resource("block/rotarycannon/barrel"));
	}

	public static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredRotarycannonBearing(String pathAndMaterial) {
		return unboredRotarycannonBlock(CBCModernWarfare.resource("block/" + pathAndMaterial + "_rotarycannon_bearing"),
			CBCModernWarfare.resource("block/rotarycannon/bearing"));
	}

	public static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredRotarycannonBreech(String pathAndMaterial) {
		return unboredRotarycannonBlock(CBCModernWarfare.resource("block/" + pathAndMaterial + "_rotarycannon_breech"),
			CBCModernWarfare.resource("block/rotarycannon/breech"));
	}

	private static <T extends Block & RotarycannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> unboredRotarycannonBlock(ResourceLocation tex, ResourceLocation model) {
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> BlockStateGen.directionalBlockIgnoresWaterlogged(c, p,
				$ -> p.models().withExistingParent(c.getName(), model)
					.texture("material", tex)))
			.tag(AllBlockTags.SAFE_NBT.tag)
			.item(RotarycannonBlockItem::new)
			.model((c, p) -> p.blockItem(c))
			.build();
	}

}
