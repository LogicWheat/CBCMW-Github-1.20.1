package riftyboi.cbcmodernwarfare.datagen.loot;

import java.util.function.BiConsumer;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import rbasamoyai.createbigcannons.index.CBCItems;
import rbasamoyai.createbigcannons.utils.CBCRegistryUtils;
import rbasamoyai.createbigcannons.utils.CBCUtils;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlocks;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareItem;

public class BoringScrapLoot implements LootTableSubProvider {

	@Override
	public void generate(BiConsumer<ResourceLocation, LootTable.Builder> cons) {
		ItemLike castIron = CBCItems.CAST_IRON_NUGGET.get();
		ItemLike bronze = CBCItems.BRONZE_SCRAP.get();
		ItemLike steel = CBCItems.STEEL_SCRAP.get();
		ItemLike nethersteel = CBCItems.NETHERSTEEL_NUGGET.get();
		ItemLike carbonSteel = CBCModernWarfareItem.CARBON_STEEL_NUGGET.get();

		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_BARREL.get()), dropAmount(carbonSteel, 40, 50));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_BARREL.get()), dropAmount(carbonSteel, 40, 50));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_CHAMBER.get()), dropAmount(carbonSteel, 85, 95));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_SLIDING_BREECH.get()), dropAmount(carbonSteel, 58, 68));

		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BARREL.get()), dropAmount(bronze, 25, 30));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BEARING.get()), dropAmount(bronze, 35, 40));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BREECH.get()), dropAmount(bronze, 35, 40));

		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_STEEL_ROTARYCANNON_BARREL.get()), dropAmount(steel, 25, 30));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_STEEL_ROTARYCANNON_BEARING.get()), dropAmount(steel, 35, 40));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_STEEL_ROTARYCANNON_BREECH.get()), dropAmount(steel, 35, 50));

		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_ROTARYCANNON_BARREL.get()), dropAmount(nethersteel, 25, 30));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_ROTARYCANNON_BEARING.get()), dropAmount(nethersteel, 35, 30));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_ROTARYCANNON_BREECH.get()), dropAmount(nethersteel, 35, 40));

		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_BARREL.get()), dropAmount(castIron, 25, 30));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_RECOIL_BARREL.get()), dropAmount(castIron, 35, 40));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_BREECH.get()), dropAmount(castIron, 35, 40));

		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_BARREL.get()), dropAmount(bronze, 25, 30));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_RECOIL_BARREL.get()), dropAmount(bronze, 35, 40));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_BREECH.get()), dropAmount(bronze, 35, 40));

		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_BARREL.get()), dropAmount(steel, 25, 30));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_RECOIL_BARREL.get()), dropAmount(steel, 35, 40));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_BREECH.get()), dropAmount(steel, 35, 50));

		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_BARREL.get()), dropAmount(nethersteel, 25, 30));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL.get()), dropAmount(nethersteel, 35, 30));
		cons.accept(loc(CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_BREECH.get()), dropAmount(nethersteel, 35, 40));

	}

	protected static ResourceLocation loc(Block block) {
		ResourceLocation id = CBCRegistryUtils.getBlockLocation(block);
		return CBCUtils.location(id.getNamespace(), "boring_scrap/" + id.getPath());
	}

	protected static LootTable.Builder dropAmount(ItemLike drop, int min, int max) {
		int maxSz = new ItemStack(drop).getMaxStackSize();
		LootTable.Builder table = LootTable.lootTable();
		for (int i = 0; i < Mth.floor((float) min / maxSz); ++i)
			table.withPool(LootPool.lootPool()
				.add(LootItem.lootTableItem(drop))
				.apply(SetItemCountFunction.setCount(ConstantValue.exactly(64))));
		int base = min % maxSz;
		int diff = max - min;
		if (base + diff > maxSz) {
			table.withPool(LootPool.lootPool()
				.add(LootItem.lootTableItem(drop))
				.apply(SetItemCountFunction.setCount(ConstantValue.exactly(base))));
			base = 0;
		}
		return table.withPool(LootPool.lootPool()
			.add(LootItem.lootTableItem(drop))
			.apply(SetItemCountFunction.setCount(UniformGenerator.between(base, base + diff))));
	}

}
