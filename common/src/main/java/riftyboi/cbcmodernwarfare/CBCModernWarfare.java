package riftyboi.cbcmodernwarfare;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;

import net.createmod.catnip.lang.FontHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.resources.ResourceLocation;
import rbasamoyai.createbigcannons.utils.CBCUtils;
import riftyboi.cbcmodernwarfare.cannon_control.cannon_types.CBCMWCannonContraptionTypes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareArmInteractionPointTypes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlocks;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareChecks;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareContraptionTypes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareEntityTypes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareFluids;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareItem;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMenuTypes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionPropertiesHandlers;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareRecipeTypes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareSoundEvents;


public class CBCModernWarfare {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "cbcmodernwarfare";
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

	public static void init() {
		CBCModernWarfareSoundEvents.prepare();

		CBCModernWarfareMunitionPropertiesHandlers.init();
		ModGroup.register();
		CBCModernWarfareBlocks.register();
		CBCModernWarfareItem.register();
		CBCModernWarfareBlockEntities.register();
		CBCModernWarfareFluids.register();
		CBCModernWarfareMenuTypes.register();
		CBCModernWarfareEntityTypes.register();
		CBCModernWarfareRecipeTypes.register();
		CBCMWCannonContraptionTypes.register();
		CBCModernWarfareChecks.register();
		}
	static {
		REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
			.andThen(TooltipModifier.mapNull(KineticStats.create(item))));
	}

	public static ResourceLocation resource(String path) {
		return CBCUtils.location(MOD_ID, path);
	}
}
