package riftyboi.cbcmodernwarfare.index;

import static riftyboi.cbcmodernwarfare.CBCModernWarfare.REGISTRATE;

import java.util.function.Consumer;

import com.simibubi.create.content.contraptions.render.OrientedContraptionEntityRenderer;
import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;

import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;
import rbasamoyai.createbigcannons.multiloader.EntityTypeConfigurator;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.MobCategory;
import rbasamoyai.createbigcannons.munitions.autocannon.AbstractAutocannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.BigCannonProjectileRenderer;
import rbasamoyai.createbigcannons.munitions.big_cannon.grapeshot.GrapeshotBurstRenderer;
import rbasamoyai.createbigcannons.munitions.config.MunitionPropertiesHandler;
import rbasamoyai.createbigcannons.munitions.config.PropertiesTypeHandler;
import rbasamoyai.ritchiesprojectilelib.RPLTags;
import riftyboi.cbcmodernwarfare.munitions.autocannon.apds.APDSAutocannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.autocannon.he.ExplosiveAutocannonProjectile;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightRenderer;
import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;
import riftyboi.cbcmodernwarfare.munitions.autocannon.canister.CanisterAutocannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.autocannon.hvap.HVAPAutocannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.big_cannon.apds_shot.APDSShotProjectile;
import riftyboi.cbcmodernwarfare.munitions.big_cannon.heap_shell.HEAPBurst;
import riftyboi.cbcmodernwarfare.munitions.big_cannon.heap_shell.HEAPBurstRenderer;
import riftyboi.cbcmodernwarfare.munitions.big_cannon.hefrag_shell.HEFShellProjectile;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.big_cannon.heap_shell.HEAPShellProjectile;
import riftyboi.cbcmodernwarfare.munitions.big_cannon.apds_shot.APDSShotRenderer;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonProjectileRenderer;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.AbstractMediumcannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonProjectileRenderer;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.ap.APMediumcannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.apds.APDSMediumcannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.apfsds.APFSDSMediumcannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.aphe.APHEMediumcannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.canister.CanisterBurst;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.canister.CanisterMediumcannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.he.HEMediumcannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.heap.HEAPMediumcannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.hefrag.HEFMediumcannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.smoke_discharger.SmokeDischargerProjectileRenderer;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.smoke.SmokeMediumcannonProjectile;
import riftyboi.cbcmodernwarfare.munitions.smoke_discharger.SmokeDischargerProjectile;

public class CBCModernWarfareEntityTypes {
	//Sights


	public static final EntityEntry<AbstractSightEntity> SIGHT_ENTITY = REGISTRATE
		.entity("sight_entity", AbstractSightEntity::new, MobCategory.MISC)
			.properties(configure(c -> c.trackingRange(128)
				.updateInterval(3)
				.updateVelocity(true)
				.fireImmune()
				.size(0.5f, 0.5f)))
		.renderer(() -> AbstractSightRenderer::new)
			.register();



	//Muitions Contraption

	public static final EntityEntry<MunitionsPhysicsContraptionEntity> MUNITIONS_CONTRAPTION = REGISTRATE
		.entity("munitions_contraption", MunitionsPhysicsContraptionEntity::new, MobCategory.MISC)
		.properties(configure(c -> c.trackingRange(16)
			.updateInterval(3)
			.updateVelocity(true)
			.fireImmune()
			.size(1, 1)))
		.renderer(() -> OrientedContraptionEntityRenderer::new)
		.onRegister(type -> MunitionPropertiesHandler.registerProjectileHandler(type, CBCModernWarfareMunitionPropertiesHandlers.MUNITIONS_CONTRAPTION))
		.register();






	//Medium Cannon Rounds
	public static final EntityEntry<APMediumcannonProjectile> AP_ROUND = mediumcannonProjectile("ap_mediumshell", APMediumcannonProjectile::new, "Armor Piercing (AP) Mediumcannon Round", CBCModernWarfareMunitionPropertiesHandlers.INERT_MEDIUMCANNON_PROJECTILE);
	public static final EntityEntry<APDSMediumcannonProjectile> APDS_ROUND = mediumcannonProjectile("apds_mediumshell", APDSMediumcannonProjectile::new, "Armor Piercing (APDS) Discarding Sabot Mediumcannon Round", CBCModernWarfareMunitionPropertiesHandlers.INERT_MEDIUMCANNON_PROJECTILE);
	public static final EntityEntry<APFSDSMediumcannonProjectile> APFSDS_ROUND = mediumcannonProjectile("apfsds_mediumshell", APFSDSMediumcannonProjectile::new, "Armor Piercing Fin (APFSDS) Stabalised Discarding Sabot Mediumcannon Round", CBCModernWarfareMunitionPropertiesHandlers.INERT_MEDIUMCANNON_PROJECTILE);
	public static final EntityEntry<APHEMediumcannonProjectile> APHE_ROUND = mediumcannonProjectile("aphe_mediumshell", APHEMediumcannonProjectile::new, "Armor Piercing (APHE) High Explosive Mediumcannon Round", CBCModernWarfareMunitionPropertiesHandlers.APHE_MEDIUMCANNON);
	public static final EntityEntry<CanisterMediumcannonProjectile> CANISTER_ROUND = mediumcannonProjectile("canister_mediumshell", CanisterMediumcannonProjectile::new, "Canister Anti Infantry (CAN) Mediumcannon Round", CBCModernWarfareMunitionPropertiesHandlers.CANISTER_MEDIUMCANNON);
	public static final EntityEntry<HEMediumcannonProjectile> HE_ROUND = mediumcannonProjectile("he_mediumshell", HEMediumcannonProjectile::new, "High Explosive (HE) Mediumcannon Round", CBCModernWarfareMunitionPropertiesHandlers.HE_MEDIUMCANNON);
	public static final EntityEntry<HEFMediumcannonProjectile> HEF_ROUND = mediumcannonProjectile("hef_mediumshell", HEFMediumcannonProjectile::new, "High Explosive (HE-F) Fragmentation Mediumcannon Round", CBCModernWarfareMunitionPropertiesHandlers.HEF_MEDIUMCANNON);
	public static final EntityEntry<HEAPMediumcannonProjectile> HEAP_ROUND = mediumcannonProjectile("heap_mediumshell", HEAPMediumcannonProjectile::new, "High Explosive (HEAT) Anti Tank Mediumcannon Round", CBCModernWarfareMunitionPropertiesHandlers.HEAP_MEDIUMCANNON);
	public static final EntityEntry<SmokeMediumcannonProjectile> SMOKE_ROUND = mediumcannonProjectile("smoke_mediumshell", SmokeMediumcannonProjectile::new, "Smoke Mediumcannon Round", CBCModernWarfareMunitionPropertiesHandlers.SMOKE_MEDIUMCANNON);
	public static final EntityEntry<SmokeDischargerProjectile> SMOKE_GRENADE = smokeDischargerProjectile("smoke_grenade", SmokeDischargerProjectile::new, "Smoke Discharger Grenade", CBCModernWarfareMunitionPropertiesHandlers.SMOKE_GRENADE);


	public static final EntityEntry<HEAPBurst> HEAP_BURST = REGISTRATE
		.entity("heat_jet", HEAPBurst::new, MobCategory.MISC)
		.properties(shrapnel())
		.renderer(() -> HEAPBurstRenderer::new)
		.onRegister(type -> MunitionPropertiesHandler.registerProjectileHandler(type, CBCMunitionPropertiesHandlers.PROJECTILE_BURST))
		.register();


	public static final EntityEntry<CanisterBurst> CANISTER_BURST = REGISTRATE
		.entity("canister_burst", CanisterBurst::new, MobCategory.MISC)
		.properties(shrapnel())
		.renderer(() -> GrapeshotBurstRenderer::new)
		.onRegister(type -> MunitionPropertiesHandler.registerProjectileHandler(type, CBCMunitionPropertiesHandlers.PROJECTILE_BURST))
		.register();


	//Big Cannon Rounds
	public static final EntityEntry<HEFShellProjectile> HEF_SHELL = cannonProjectile("hefrag_shell", HEFShellProjectile::new, CBCModernWarfareMunitionPropertiesHandlers.HEF_SHELL);
	public static final EntityEntry<HEAPShellProjectile> HEAP_SHELL = cannonProjectile("heap_shell", HEAPShellProjectile::new, CBCModernWarfareMunitionPropertiesHandlers.HEAP_SHELL);
	public static final EntityEntry<APDSShotProjectile> APDS_SHOT = REGISTRATE
		.entity("apds_shot", APDSShotProjectile::new, MobCategory.MISC)
		.properties(cannonProperties())
		.renderer(() -> APDSShotRenderer::new)
		.tag(RPLTags.PRECISE_MOTION)
		.onRegister(type -> MunitionPropertiesHandler.registerProjectileHandler(type, CBCMunitionPropertiesHandlers.INERT_BIG_CANNON_PROJECTILE))
		.register();



	//Auto Cannon Rounds

	public static final EntityEntry<HVAPAutocannonProjectile> HVAP_AUTOCANNON = autocannonProjectile("hvap_autocannon", HVAPAutocannonProjectile::new, "High Velocity Armor Piercing (AP) Autocannon Round", CBCMunitionPropertiesHandlers.INERT_AUTOCANNON_PROJECTILE);
	public static final EntityEntry<APDSAutocannonProjectile> APDS_AUTOCANNON = autocannonProjectile("apds_autocannon", APDSAutocannonProjectile::new, "Armor Piercing (APDS) Discarding Sabot Autocannon Round", CBCMunitionPropertiesHandlers.INERT_AUTOCANNON_PROJECTILE);
	public static final EntityEntry<CanisterAutocannonProjectile> CANISTER_AUTOCANNON = autocannonProjectile("canister_autocannon", CanisterAutocannonProjectile::new, "Canister Autocannon Round", CBCModernWarfareMunitionPropertiesHandlers.CANISTER_AUTOCANNON);

	public static final EntityEntry<ExplosiveAutocannonProjectile> HE_AUTOCANNON = autocannonProjectile("he_autocannon", ExplosiveAutocannonProjectile::new, "HE Autocannon Round", CBCModernWarfareMunitionPropertiesHandlers.HE_AUTOCANNON);

	public static void register() {
	}

	private static <T extends AbstractBigCannonProjectile> EntityEntry<T>
	cannonProjectile(String id, EntityFactory<T> factory, PropertiesTypeHandler<EntityType<?>, ?> handler) {
		return REGISTRATE
			.entity(id, factory, MobCategory.MISC)
			.properties(cannonProperties())
			.renderer(() -> BigCannonProjectileRenderer::new)
			.tag(RPLTags.PRECISE_MOTION)
			.onRegister(type -> MunitionPropertiesHandler.registerProjectileHandler(type, handler))
			.register();
	}

	private static <T extends AbstractMediumcannonProjectile> EntityEntry<T>
	mediumcannonProjectile(String id, EntityFactory<T> factory, String enUSdiffLang, PropertiesTypeHandler<EntityType<?>, ?> handler) {
		return REGISTRATE
			.entity(id, factory, MobCategory.MISC)
			.properties(autocannonProperties())
			.renderer(() -> MediumcannonProjectileRenderer::new)
			.lang(enUSdiffLang)
			.tag(RPLTags.PRECISE_MOTION)
			.onRegister(type -> MunitionPropertiesHandler.registerProjectileHandler(type, handler))
			.register();
	}

	private static <T extends SmokeDischargerProjectile> EntityEntry<T>
	smokeDischargerProjectile(String id, EntityFactory<T> factory, String enUSdiffLang, PropertiesTypeHandler<EntityType<?>, ?> handler) {
		return REGISTRATE
			.entity(id, factory, MobCategory.MISC)
			.properties(autocannonProperties())
			.renderer(() -> SmokeDischargerProjectileRenderer::new)
			.lang(enUSdiffLang)
			.tag(RPLTags.PRECISE_MOTION)
			.onRegister(type -> MunitionPropertiesHandler.registerProjectileHandler(type, handler))
			.register();
	}

	private static <T extends AbstractAutocannonProjectile> EntityEntry<T>
	autocannonProjectile(String id, EntityFactory<T> factory, String enUSdiffLang, PropertiesTypeHandler<EntityType<?>, ?> handler) {
		return REGISTRATE
			.entity(id, factory, MobCategory.MISC)
			.properties(autocannonProperties())
			.renderer(() -> AutocannonProjectileRenderer::new)
			.lang(enUSdiffLang)
			.tag(RPLTags.PRECISE_MOTION)
			.onRegister(type -> MunitionPropertiesHandler.registerProjectileHandler(type, handler))
			.register();
	}



	private static <T> NonNullConsumer<T> configure(Consumer<EntityTypeConfigurator> cons) {
		return b -> cons.accept(EntityTypeConfigurator.of(b));
	}

	private static <T> NonNullConsumer<T> autocannonProperties() {
		return configure(c -> c.size(0.2f, 0.2f)
			.fireImmune()
			.updateInterval(1)
			.updateVelocity(false) // Mixin ServerEntity to not track motion
			.trackingRange(16));
	}

	private static <T> NonNullConsumer<T> cannonProperties() {
		return configure(c -> c.size(0.8f, 0.8f)
			.fireImmune()
			.updateInterval(1)
			.updateVelocity(false) // Ditto
			.trackingRange(16));
	}

	private static <T> NonNullConsumer<T> shrapnel() {
		return configure(c -> c.size(0.8f, 0.8f)
			.fireImmune()
			.updateInterval(1)
			.updateVelocity(true)
			.trackingRange(16));
	}
}

