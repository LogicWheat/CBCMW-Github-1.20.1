package riftyboi.cbcmodernwarfare;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.palettes.AllPaletteBlocks;
import com.simibubi.create.foundation.block.CopperBlockSet;
import com.tterrag.registrate.providers.ProviderType;

import net.minecraft.data.tags.TagsProvider;
import net.minecraft.data.tags.TagsProvider.TagAppender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.material.Fluid;
import rbasamoyai.createbigcannons.base.tag_utils.ForcedTagEntry;
import rbasamoyai.createbigcannons.mixin.TagAppenderAccessor;
import rbasamoyai.createbigcannons.utils.CBCRegistryUtils;
import rbasamoyai.createbigcannons.utils.CBCUtils;

import static riftyboi.cbcmodernwarfare.CBCModernWarfare.REGISTRATE;

public class CBCModernWarfareTags {

	public static class CBCModernWarfareItemTags {
		// TODO: Update fabric ore tags to use fabric-convention-tags in 1.21
		public static final TagKey<Item>
			NUGGET_CARBON_STEEL = commonTag("nugget_carbon_steel", "nuggets/carbon_steel", "carbon_steel_nuggets"),
			INGOT_CARBON_STEEL = commonTag("ingot_carbon_steel", "ingots/carbon_steel", "carbon_steel_ingots"),
			BLOCK_CARBON_STEEL = commonTag("block_carbon_steel", "storage_blocks/carbon_steel", "carbon_steel_blocks"),
			NUGGET_SILICON = commonTag("nugget_silicon", "nuggets/silicon", "silicon_nuggets"),
			INGOT_SILICON = commonTag("ingot_silicon", "ingots/silicon", "silicon_ingots"),

		// Crafting tags
			SPENT_MEDIUMCANNON_CASINGS = makeTag("spent_mediumcannon_casings"),
			MEDIUMCANNON_CARTRIDGES = makeTag("mediumcannon_cartridges"),
			MEDIUMCANNON_ROUNDS = makeTag("mediumcannon_rounds");

		public static TagKey<Item> makeTag(String loc) {
			TagKey<Item> tag = CBCRegistryUtils.createItemTag(CBCModernWarfare.resource(loc));
			REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> ((TagsProvider<Item>) prov).tag(tag));
			return tag;
		}

		public static TagKey<Item> commonTag(String mainPath, String forgePath, String fabricPath) {
			TagKey<Item> mainTag = makeTag(mainPath);
			addOptionalTagsToItemTag(mainTag, Arrays.asList(
				CBCUtils.location("forge", forgePath),
				CBCUtils.location("c", forgePath), // For forge -> fabric ports, e.g. Create
				CBCUtils.location("c", fabricPath)));
			return mainTag;
		}

		public static void addItemsToItemTag(TagKey<Item> tag, Item... items) {
			REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> {
				TagAppender<Item> app = ((TagsProvider<Item>) prov).tag(tag);
				for (Item item : items) {
					CBCRegistryUtils.getItemRegistry().getResourceKey(item).ifPresent(app::add);
				}
			});
		}

		public static void addItemsToItemTag(TagKey<Item> tag, ItemLike... items) {
			REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> {
				TagAppender<Item> app = ((TagsProvider<Item>) prov).tag(tag);
				for (ItemLike bp : items) {
					CBCRegistryUtils.getItemRegistry().getResourceKey(bp.asItem()).ifPresent(app::add);
				}
			});
		}

		@SafeVarargs
		public static void addTagsToItemTag(TagKey<Item> tag, TagKey<Item>... tags) {
			REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> {
				TagAppender<Item> app = ((TagsProvider<Item>) prov).tag(tag);
				for(TagKey<Item> t : tags) {
					addTag(app, t);
				}
			});
		}

		public static void addIdsToItemTag(TagKey<Item> tag, ResourceLocation... ids) {
			REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> {
				TagAppender<Item> app = ((TagsProvider<Item>) prov).tag(tag);
				for (ResourceLocation id : ids) {
					app.addOptional(id);
				}
			});
		}

		public static void addOptionalTagsToItemTag(TagKey<Item> tag, List<ResourceLocation> ops) {
			REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> {
				TagAppender<Item> app = ((TagsProvider<Item>) prov).tag(tag);
				ops.forEach(app::addOptionalTag);
			});
		}

	}



	private static <T> void addTag(TagAppender<T> app, TagKey<T> tag) {
		TagAppenderAccessor accessor = (TagAppenderAccessor) app;
		TagBuilder builder = accessor.getBuilder();
		builder.add(new ForcedTagEntry(TagEntry.tag(tag.location())));
	}

}
