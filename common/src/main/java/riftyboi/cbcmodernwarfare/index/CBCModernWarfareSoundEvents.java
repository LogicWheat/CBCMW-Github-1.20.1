package riftyboi.cbcmodernwarfare.index;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.simibubi.create.AllSoundEvents.SoundEntry;
import com.simibubi.create.AllSoundEvents.SoundEntryBuilder;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import rbasamoyai.createbigcannons.CreateBigCannons;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import rbasamoyai.createbigcannons.index.CBCSoundEvents;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;

public class CBCModernWarfareSoundEvents {

	public static final Map<ResourceLocation, SoundEntry> ALL = new HashMap<>();

	public static final SoundEntry

		FIRE_MEDIUMCANNON = create("fire_mediumcannon").subtitle("Mediumcannon fires")
		.category(SoundSource.BLOCKS)
		.build(),
		ROCKET_FLYING = create("rocket_flying").subtitle("Rocket Flying")
			.category(SoundSource.BLOCKS)
			.build();


	private static SoundEntryBuilder create(String id) { return new CBCModernWarfareSoundEvents.CBCModernWarfareSoundEntryBuilder(CBCModernWarfare.resource(id)); }

	public static void prepare() {
		for (SoundEntry entry : ALL.values())
			entry.prepare();
	}

	public static void register(Consumer<SoundEntry> cons) {
		for (SoundEntry entry : ALL.values())
			cons.accept(entry);
	}

	public static void registerLangEntries() {
		for (SoundEntry entry : ALL.values()) {
			if (entry.hasSubtitle())
				CreateBigCannons.REGISTRATE.addRawLang(entry.getSubtitleKey(), entry.getSubtitle());
		}
	}

	public static class CBCModernWarfareSoundEntryBuilder extends SoundEntryBuilder {
		public CBCModernWarfareSoundEntryBuilder(ResourceLocation id) {
			super(id);
		}

		@Override
		public SoundEntry build() {
			SoundEntry entry = super.build();
			ALL.put(entry.getId(), entry);
			return entry;
		}
	}

	public static SoundEntryProvider provider(PackOutput output) {
		return new SoundEntryProvider(output);
	}

	public static class SoundEntryProvider implements DataProvider {
		private PackOutput output;

		public SoundEntryProvider(PackOutput output) {
			this.output = output;
		}

		@Override
		public CompletableFuture<?> run(CachedOutput cache) {
			Path path = this.output.getOutputFolder().resolve("assets/" + CreateBigCannons.MOD_ID);
			JsonObject json = new JsonObject();
			ALL.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> entry.getValue().write(json));
			return DataProvider.saveStable(cache, json, path.resolve("sounds.json"));
		}

		@Override public String getName() { return "Create Big Cannons custom sounds"; }
	}

}
