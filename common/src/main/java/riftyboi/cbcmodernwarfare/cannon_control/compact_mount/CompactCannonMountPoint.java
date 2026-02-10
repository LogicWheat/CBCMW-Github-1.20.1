package riftyboi.cbcmodernwarfare.cannon_control.compact_mount;

import java.util.HashSet;
import java.util.Set;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountBlockEntity;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedAutocannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedBigCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.cannons.autocannon.breech.AbstractAutocannonBreechBlockEntity;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBlock;
import rbasamoyai.createbigcannons.cannons.big_cannons.IBigCannonBlockEntity;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.quickfiring_breech.CannonMountPoint;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.quickfiring_breech.QuickfiringBreechBlockEntity;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.munitions.autocannon.ammo_container.AutocannonAmmoContainerItem;
import rbasamoyai.createbigcannons.munitions.big_cannon.BigCannonMunitionBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.ProjectileBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.BigCartridgeBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.BigCartridgeBlockItem;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedMediumcannonContraption;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.breech.MediumcannonBreechBlockEntity;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonAmmoItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonCartridgeItem;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonRoundItem;

public class CompactCannonMountPoint extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {
	public CompactCannonMountPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
		super(type, level, pos, state);
	}

	@Override
	protected Vec3 getInteractionPositionVector() {
		return this.level.getBlockEntity(this.pos) instanceof CannonMountBlockEntity mount ? mount.getInteractionLocation() : super.getInteractionPositionVector();
	}

	public ItemStack getInsertedResultAndDoSomething(ItemStack stack, boolean simulate, AbstractMountedCannonContraption cannon,
													 PitchOrientedContraptionEntity poce) {
		if (cannon instanceof MountedBigCannonContraption bigCannon) {
			return CannonMountPoint.bigCannonInsert(stack, simulate, bigCannon, poce);
		}
		if (cannon instanceof MountedAutocannonContraption autocannon) {
			return CannonMountPoint.autocannonInsert(stack, simulate, autocannon, poce);
		}
		if (cannon instanceof MountedMediumcannonContraption mediumcannon) {
			return mediumcannonInsert(stack, simulate, mediumcannon, poce);
		}
		return stack;
	}

	public static ItemStack mediumcannonInsert(ItemStack stack, boolean simulate, MountedMediumcannonContraption mediumcannon,
											 PitchOrientedContraptionEntity poce) {
		if (!(stack.getItem() instanceof MediumcannonAmmoItem)) return stack;
		BlockEntity be = mediumcannon.presentBlockEntities.get(mediumcannon.getStartPos());
		if (!(be instanceof MediumcannonBreechBlockEntity breech) || !breech.canBeAutomaticallyLoaded()) return stack;

		ItemStack containedItem = breech.getInputBuffer();
		if (containedItem.getItem() instanceof MediumcannonAmmoItem && !(CBCModernWarfareItem.EMPTY_MEDIUMCANNON_CARTRIDGE.is(containedItem.getItem()))) return stack;

		if (simulate) stack.setCount(1);
		if (!simulate) {
			breech.setInputBuffer(stack);
			breech.setLoadingCooldown(getLoadingCooldown());
		}
		return containedItem.isEmpty() ? ItemStack.EMPTY : containedItem;
	}

	private static int getLoadingCooldown() {
		return (int) (CBCConfigs.server().cannons.quickfiringBreechLoadingCooldown.get() * 1.5);
	}

}
