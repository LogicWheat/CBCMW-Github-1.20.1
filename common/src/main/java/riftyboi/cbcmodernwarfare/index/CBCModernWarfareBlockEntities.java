package riftyboi.cbcmodernwarfare.index;

import static riftyboi.cbcmodernwarfare.CBCModernWarfare.REGISTRATE;

import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBlockEntity;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBlockEntityRenderer;
import riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountBlockEntity;
import riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountBlockEntityRenderer;
import riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.breech.MediumcannonBreechBlockEntity;
// import riftyboi.cbcmodernwarfare.cannons.medium_cannon.breech.MediumcannonBreechInstance;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.breech.MediumcannonBreechRenderer;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.recoil_barrel.MediumcannonRecoilBarrelBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlockRenderer;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.quickfiring.LauncherQuickFiringBreechBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.quickfiring.LauncherQuickFiringBreechBlockEntityRenderer;
// import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.quickfiring.LauncherQuickFiringBreechInstance;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.sliding_breech.LauncherSlidingBreechBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.sliding_breech.LauncherSlidingBreechBlockEntityRenderer;
// import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.sliding_breech.LauncherSlidingBreechInstance;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end.MunitionsLauncherEndBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBlockEntity;
// import riftyboi.cbcmodernwarfare.cannons.rotarycannon.bearing.RotarycannonBearingInstance;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.bearing.RotaryCannonBearingVisual;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.bearing.RotarycannonBearingRenderer;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.breech.AbstractRotarycannonBreechBlockEntity;
// import riftyboi.cbcmodernwarfare.cannons.rotarycannon.breech.RotarycannonBreechInstance;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.breech.RotarycannonBreechRenderer;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.bearing.RotarycannonBearingBlockEntity;
import riftyboi.cbcmodernwarfare.content.smoke_discharger.SmokeDischargerBlockEntity;
// import riftyboi.cbcmodernwarfare.content.smoke_discharger.SmokeDischargerInstance;
import riftyboi.cbcmodernwarfare.crafting.incomplete.IncompleteLauncherBlockEntity;
import riftyboi.cbcmodernwarfare.crafting.incomplete.IncompleteMediumcannonBlockEntity;
// import riftyboi.cbcmodernwarfare.cannons.medium_cannon.recoil_barrel.MediumcannonRecoilBarrelInstance;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.recoil_barrel.MediumcannonRecoilBarrelRenderer;
import riftyboi.cbcmodernwarfare.crafting.incomplete.IncompleteRotarycannonBlockEntity;
import riftyboi.cbcmodernwarfare.multiloader.IndexPlatform;
import riftyboi.cbcmodernwarfare.munitions.autocannon.ammo_drum.AutocannonAmmoDrumBlockEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.infrared_homing.InfraredSeekerGuidanceBlockEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.manual.ManualCommandGuidanceBlockEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.optical.OpticalCommandGuidanceBlockEntity;

public class CBCModernWarfareBlockEntities {
	public static final BlockEntityEntry<FuzedBlockEntity> FUZED_BLOCK = REGISTRATE
		.blockEntity("fuzed_block", FuzedBlockEntity::new)
//		.instance(() -> FuzedBlockInstance::new)
		.renderer(() -> FuzedBlockEntityRenderer::new)
		.validBlocks(CBCModernWarfareBlocks.HEF_SHELL, CBCModernWarfareBlocks.HEAP_SHELL)
		.register();

	public static final BlockEntityEntry<OpticalCommandGuidanceBlockEntity> OPTICAL_COMMAND_GUIDANCE = REGISTRATE
		.blockEntity("optical_guidance", OpticalCommandGuidanceBlockEntity::new)
		.validBlocks(CBCModernWarfareBlocks.OPTICAL_COMMAND_GUIDANCE)
		.register();

	public static final BlockEntityEntry<ManualCommandGuidanceBlockEntity> MANUAL_COMMAND_GUIDANCE = REGISTRATE
		.blockEntity("manual_guidance", ManualCommandGuidanceBlockEntity::new)
		.validBlocks(CBCModernWarfareBlocks.MANUAL_COMMAND_GUIDANCE)
		.register();
	public static final BlockEntityEntry<InfraredSeekerGuidanceBlockEntity> INFRARED_SEEKER_GUIDANCE = REGISTRATE
		.blockEntity("infrared_guidance", InfraredSeekerGuidanceBlockEntity::new)
		.validBlocks(CBCModernWarfareBlocks.INFRARED_SEEKER_GUIDANCE)
		.register();

	public static final BlockEntityEntry<SmokeDischargerBlockEntity> SMOKE_DISCHARGER = REGISTRATE
		.blockEntity("smoke_discharger", SmokeDischargerBlockEntity::new)
//		.instance(() -> SmokeDischargerInstance::new)
		.validBlocks(CBCModernWarfareBlocks.SMOKE_DISCHARGER)
		.register();

	public static final BlockEntityEntry<RotarycannonBlockEntity> ROTARYCANNON = REGISTRATE
		.blockEntity("rotarycannon", RotarycannonBlockEntity::new)
		.validBlocks(CBCModernWarfareBlocks.BRONZE_ROTARYCANNON_BARREL,CBCModernWarfareBlocks.STEEL_ROTARYCANNON_BARREL,CBCModernWarfareBlocks.NETHERSTEEL_ROTARYCANNON_BARREL)
		.register();

	public static final BlockEntityEntry<AbstractRotarycannonBreechBlockEntity> ROTARYCANNON_BREECH = REGISTRATE
		.blockEntity("rotarycannon_breech", IndexPlatform::makeRotarycannonBreech)
//		.instance(() -> RotarycannonBreechInstance::new)
		.renderer(() -> RotarycannonBreechRenderer::new)
		.validBlocks(CBCModernWarfareBlocks.BRONZE_ROTARYCANNON_BREECH,CBCModernWarfareBlocks.STEEL_ROTARYCANNON_BREECH,CBCModernWarfareBlocks.NETHERSTEEL_ROTARYCANNON_BREECH)
		.register();

	public static final BlockEntityEntry<RotarycannonBearingBlockEntity> ROTARYCANNON_BEARING = REGISTRATE
		.blockEntity("rotarycannon_bearing", RotarycannonBearingBlockEntity::new)
		.renderer(() -> RotarycannonBearingRenderer::new)
		.validBlocks(CBCModernWarfareBlocks.BRONZE_ROTARYCANNON_BEARING,CBCModernWarfareBlocks.STEEL_ROTARYCANNON_BEARING,CBCModernWarfareBlocks.NETHERSTEEL_ROTARYCANNON_BEARING)
		.register();

	public static final BlockEntityEntry<IncompleteRotarycannonBlockEntity> INCOMPLETE_ROTARYCANNON = REGISTRATE
		.blockEntity("incomplete_rotarycannoncannon", IncompleteRotarycannonBlockEntity::new)
		.validBlocks(CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BARREL, CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BEARING, CBCModernWarfareBlocks.UNBORED_BRONZE_ROTARYCANNON_BREECH, CBCModernWarfareBlocks.INCOMPLETE_BRONZE_ROTARYCANNON_BEARING, CBCModernWarfareBlocks.INCOMPLETE_BRONZE_ROTARYCANNON_BREECH,
			CBCModernWarfareBlocks.UNBORED_STEEL_ROTARYCANNON_BARREL, CBCModernWarfareBlocks.UNBORED_STEEL_ROTARYCANNON_BEARING, CBCModernWarfareBlocks.UNBORED_STEEL_ROTARYCANNON_BREECH, CBCModernWarfareBlocks.INCOMPLETE_STEEL_ROTARYCANNON_BEARING, CBCModernWarfareBlocks.INCOMPLETE_STEEL_ROTARYCANNON_BREECH,
			CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_ROTARYCANNON_BARREL, CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_ROTARYCANNON_BEARING, CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_ROTARYCANNON_BREECH, CBCModernWarfareBlocks.INCOMPLETE_NETHERSTEEL_ROTARYCANNON_BEARING, CBCModernWarfareBlocks.INCOMPLETE_NETHERSTEEL_ROTARYCANNON_BREECH)
		.register();

	public static final BlockEntityEntry<AutocannonAmmoDrumBlockEntity> AUTOCANNON_AMMO_DRUM = REGISTRATE
		.blockEntity("autocannon_ammo_drum", AutocannonAmmoDrumBlockEntity::new)
		.validBlocks(CBCModernWarfareBlocks.AUTOCANNON_AMMO_DRUM)
		.register();


	public static final BlockEntityEntry<LauncherSlidingBreechBlockEntity> LAUNCHER_SLIDING_BREECH = REGISTRATE
		.blockEntity("launcher_sliding_breech", LauncherSlidingBreechBlockEntity::new)
		.renderer(() -> LauncherSlidingBreechBlockEntityRenderer::new)
		.validBlocks(CBCModernWarfareBlocks.GUN_LAUNCHER_SLIDING_BREECH)
		.register();


	public static final BlockEntityEntry<MunitionsLauncherEndBlockEntity> LAUNCHER_END = REGISTRATE
		.blockEntity("launcher_end", MunitionsLauncherEndBlockEntity::new)
		.validBlocks(CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_SLIDING_BREECH, CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_BARREL, CBCModernWarfareBlocks.UNBORED_BUILT_UP_GUN_LAUNCHER_BARREL, CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_CHAMBER, CBCModernWarfareBlocks.UNBORED_GUN_LAUNCHER_SLIDING_BREECH)
		.register();


	public static final BlockEntityEntry<LauncherQuickFiringBreechBlockEntity> LAUNCHER_QUICKFIRING_BREECH = REGISTRATE
		.blockEntity("launcher_quickfiring_breech", LauncherQuickFiringBreechBlockEntity::new)
		.renderer(() -> LauncherQuickFiringBreechBlockEntityRenderer::new)
		.validBlocks(CBCModernWarfareBlocks.GUN_LAUNCHER_QUICKFIRING_BREECH)
		.register();

	public static final BlockEntityEntry<IncompleteLauncherBlockEntity> INCOMPLETE_LAUNCHER = CreateBigCannons.REGISTRATE
		.blockEntity("incomplete_cannon", IncompleteLauncherBlockEntity::new)
		.validBlocks(CBCModernWarfareBlocks.INCOMPLETE_GUN_LAUNCHER_SLIDING_BREECH)
		.register();

	public static final BlockEntityEntry<MunitionsLauncherBlockEntity> MUNITIONS_LAUNCHER = REGISTRATE
		.blockEntity("munitions_launcher", MunitionsLauncherBlockEntity::new)
		.validBlocks(CBCModernWarfareBlocks.ROCKET_RAILS, CBCModernWarfareBlocks.ROCKET_RAILS_END, CBCModernWarfareBlocks.TORPEDO_TUBE, CBCModernWarfareBlocks.GUN_LAUNCHER_BARREL, CBCModernWarfareBlocks.BUILT_UP_GUN_LAUNCHER_BARREL, CBCModernWarfareBlocks.GUN_LAUNCHER_CHAMBER)
		.renderer(() -> MunitionsLauncherBlockRenderer::new)
		.register();

//	public static final BlockEntityEntry<TorpedoScrewBreechBlockEntity> TORPEDO_SCREW_BREECH = REGISTRATE
//		.blockEntity("torpedo_screw_breech", TorpedoScrewBreechBlockEntity::new)
//		.instance(() -> TorpedoScrewBreechInstance::new, false)
//		.renderer(() -> TorpedoScrewBreechBlockEntityRenderer::new)
//		.validBlocks(CBCModernWarfareBlocks.TORPEDO_SCREW_BREECH)
//		.register();

	public static final BlockEntityEntry<MediumcannonBlockEntity> MEDIUMCANNON = REGISTRATE
		.blockEntity("mediumcannon", MediumcannonBlockEntity::new)
		.validBlocks(CBCModernWarfareBlocks.CAST_IRON_MEDIUMCANNON_BARREL, CBCModernWarfareBlocks.BRONZE_MEDIUMCANNON_BARREL, CBCModernWarfareBlocks.STEEL_MEDIUMCANNON_BARREL, CBCModernWarfareBlocks.NETHERSTEEL_MEDIUMCANNON_BARREL)
		.register();

	public static final BlockEntityEntry<MediumcannonBreechBlockEntity> MEDIUMCANNON_BREECH = REGISTRATE
		.blockEntity("mediumcannon_breech", MediumcannonBreechBlockEntity::new)
//		.instance(() -> MediumcannonBreechInstance::new)
		.renderer(() -> MediumcannonBreechRenderer::new)
		.validBlocks(CBCModernWarfareBlocks.CAST_IRON_MEDIUMCANNON_BREECH, CBCModernWarfareBlocks.BRONZE_MEDIUMCANNON_BREECH, CBCModernWarfareBlocks.STEEL_MEDIUMCANNON_BREECH, CBCModernWarfareBlocks.NETHERSTEEL_MEDIUMCANNON_BREECH)
		.register();

	public static final BlockEntityEntry<MediumcannonRecoilBarrelBlockEntity> MEDIUMCANNON_RECOIL_BARREL = REGISTRATE
		.blockEntity("mediumcannon_recoil_barrel", MediumcannonRecoilBarrelBlockEntity::new)
//		.instance(() -> MediumcannonRecoilBarrelInstance::new)
		.renderer(() -> MediumcannonRecoilBarrelRenderer::new)
		.validBlocks(CBCModernWarfareBlocks.CAST_IRON_MEDIUMCANNON_RECOIL_BARREL,CBCModernWarfareBlocks.BRONZE_MEDIUMCANNON_RECOIL_BARREL,CBCModernWarfareBlocks.STEEL_MEDIUMCANNON_RECOIL_BARREL,CBCModernWarfareBlocks.NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL)
		.register();
	public static final BlockEntityEntry<IncompleteMediumcannonBlockEntity> INCOMPLETE_MEDIUMCANNON = REGISTRATE
		.blockEntity("incomplete_mediumcannon", IncompleteMediumcannonBlockEntity::new)
		.validBlocks(CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_BARREL, CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_RECOIL_BARREL, CBCModernWarfareBlocks.UNBORED_CAST_IRON_MEDIUMCANNON_BREECH, CBCModernWarfareBlocks.INCOMPLETE_CAST_IRON_MEDIUMCANNON_RECOIL_BARREL, CBCModernWarfareBlocks.INCOMPLETE_CAST_IRON_MEDIUMCANNON_BREECH,
			CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_BARREL, CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_RECOIL_BARREL, CBCModernWarfareBlocks.UNBORED_BRONZE_MEDIUMCANNON_BREECH, CBCModernWarfareBlocks.INCOMPLETE_BRONZE_MEDIUMCANNON_RECOIL_BARREL, CBCModernWarfareBlocks.INCOMPLETE_BRONZE_MEDIUMCANNON_BREECH,
			CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_BARREL, CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_RECOIL_BARREL, CBCModernWarfareBlocks.UNBORED_STEEL_MEDIUMCANNON_BREECH, CBCModernWarfareBlocks.INCOMPLETE_STEEL_MEDIUMCANNON_RECOIL_BARREL, CBCModernWarfareBlocks.INCOMPLETE_STEEL_MEDIUMCANNON_BREECH,
			CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_BARREL, CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL, CBCModernWarfareBlocks.UNBORED_NETHERSTEEL_MEDIUMCANNON_BREECH, CBCModernWarfareBlocks.INCOMPLETE_NETHERSTEEL_MEDIUMCANNON_RECOIL_BARREL, CBCModernWarfareBlocks.INCOMPLETE_NETHERSTEEL_MEDIUMCANNON_BREECH)
		.register();

	public static final BlockEntityEntry<CompactCannonMountBlockEntity> COMPACT_MOUNT = REGISTRATE
		.blockEntity("compact_mount", CompactCannonMountBlockEntity::new)
//		.visual(() -> CompactCannonMountVisual::new)
		.renderer(() -> CompactCannonMountBlockEntityRenderer::new)
		.validBlock(CBCModernWarfareBlocks.COMPACT_MOUNT)
		.register();

	public static void register() {
	}

}
