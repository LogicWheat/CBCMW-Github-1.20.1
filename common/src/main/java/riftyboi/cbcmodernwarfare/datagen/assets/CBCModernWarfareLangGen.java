package riftyboi.cbcmodernwarfare.datagen.assets;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.minecraft.resources.ResourceLocation;
import riftyboi.cbcmodernwarfare.crafting.casting.CBCModernWarfareCastShapes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlocks;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareItem;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;

import static riftyboi.cbcmodernwarfare.CBCModernWarfare.REGISTRATE;

public class 	CBCModernWarfareLangGen {

	public static void prepare() {
		tooltip(CBCModernWarfareBlocks.HEF_SHELL)
			.header("HIGH EXPLOSIVE (HE-F) Fragmentation SHELL")
			.summary("Delivers a high potent _explosive_ force to the battlefield.")
			.conditionAndBehavior("On Detonation", "Explodes with a explosion larger than _TNT_.");

		tooltip(CBCModernWarfareBlocks.HEAP_SHELL)
			.header("HIGH EXPLOSIVE (HE-AP) SHELL")
			.summary("Delivers an _explosive_ shaped charge that uses chemical energy to pierce through blocks .")
			.conditionAndBehavior("On Detonation", "Explodes to form a molten jet of copper that can effectively _pierce through blocks_.");

		tooltip(CBCModernWarfareBlocks.APDS_SHOT)
			.header("ARMOR PIERCING Steel (APDS) Discarding Sabot")
			.summary("Can effectively _pierce through blocks_. More effective against _armored targets_. _Saboted projectile to acheive high velocity its velocity._");
	}

	private static class TooltipBuilder {
		private final ResourceLocation loc;
		private final String type;
		private int cbCount = 1;
		private int caCount = 1;
		public TooltipBuilder(ItemProviderEntry<?> provider, boolean item) {
			this.loc = provider.getId();
			this.type = item ? "item" : "block";
		}

		public TooltipBuilder header(String enUS) {
			REGISTRATE.addLang(this.type, this.loc, "tooltip", enUS);
			return this;
		}

		public TooltipBuilder summary(String enUS) {
			REGISTRATE.addLang(this.type, this.loc, "tooltip.summary", enUS);
			return this;
		}

		public TooltipBuilder conditionAndBehavior(String enUSCondition, String enUSBehaviour) {
			REGISTRATE.addLang(this.type, this.loc, String.format("tooltip.condition%d", this.cbCount), enUSCondition);
			REGISTRATE.addLang(this.type, this.loc, String.format("tooltip.behaviour%d", this.cbCount), enUSBehaviour);
			this.cbCount++;
			return this;
		}

		public TooltipBuilder controlAndAction(String enUSControl, String enUSAction) {
			REGISTRATE.addLang(this.type, this.loc, String.format("tooltip.control%d", this.caCount), enUSControl);
			REGISTRATE.addLang(this.type, this.loc, String.format("tooltip.action%d", this.caCount), enUSAction);
			this.caCount++;
			return this;
		}
	}

	private static TooltipBuilder tooltip(BlockEntry<?> provider) {
		return new TooltipBuilder(provider, false);
	}

	private static TooltipBuilder tooltip(ItemEntry<?> provider) {
		return new TooltipBuilder(provider, true);
	}
}
