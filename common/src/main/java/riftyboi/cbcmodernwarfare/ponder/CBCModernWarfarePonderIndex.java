package riftyboi.cbcmodernwarfare.ponder;

import com.simibubi.create.AllBlocks;

import com.tterrag.registrate.util.entry.ItemProviderEntry;

import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.foundation.PonderTag;
import net.createmod.ponder.foundation.registration.PonderSceneRegistry;
import net.minecraft.resources.ResourceLocation;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.index.CBCBlocks;
import com.tterrag.registrate.util.entry.RegistryEntry;
import rbasamoyai.createbigcannons.index.CBCItems;
import rbasamoyai.createbigcannons.ponder.CBCPonderTags;
import rbasamoyai.createbigcannons.ponder.CannonCraftingScenes;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlocks;

public class CBCModernWarfarePonderIndex {

	public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
		PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
		HELPER.forComponents(
				CBCModernWarfareBlocks.INCOMPLETE_CAST_IRON_MEDIUMCANNON_BREECH,
				CBCModernWarfareBlocks.INCOMPLETE_BRONZE_MEDIUMCANNON_BREECH,
				CBCModernWarfareBlocks.INCOMPLETE_STEEL_MEDIUMCANNON_BREECH,
				CBCModernWarfareBlocks.INCOMPLETE_CAST_IRON_MEDIUMCANNON_RECOIL_BARREL,
				CBCModernWarfareBlocks.INCOMPLETE_BRONZE_MEDIUMCANNON_RECOIL_BARREL,
				CBCModernWarfareBlocks.INCOMPLETE_STEEL_MEDIUMCANNON_RECOIL_BARREL)
			.addStoryBoard("cannon_crafting/incomplete_cannon_blocks", CannonCraftingScenes::incompleteCannonBlocks);

	}

	public static void register() {


	}
}
