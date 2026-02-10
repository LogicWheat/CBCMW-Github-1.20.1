package riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.simibubi.create.content.contraptions.Contraption;

import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.cannons.CannonContraptionProviderBlock;
import rbasamoyai.createbigcannons.cannons.InteractableCannonBlock;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBlock;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import rbasamoyai.createbigcannons.crafting.welding.WeldableBlock;
import rbasamoyai.createbigcannons.equipment.manual_loading.RamRodItem;
import rbasamoyai.createbigcannons.equipment.manual_loading.WormItem;
import rbasamoyai.createbigcannons.munitions.big_cannon.BigCannonMunitionBlock;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedMunitionsLauncherContraption;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end.MunitionsLauncherEnd;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.material.MunitionsLauncherMaterial;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.ContraptionMunitionBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.PropelledContraptionMunitionBlock;

import static rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBlock.writeAndSyncSingleBlockData;


public interface MunitionsLauncherBlock extends WeldableBlock, CannonContraptionProviderBlock, InteractableCannonBlock {

	MunitionsLauncherMaterial getCannonMaterial();

	CannonCastShape getCannonShape();

	Direction getFacing(BlockState state);

	default MunitionsLauncherEnd getOpeningType(@Nullable Level level, BlockState state, BlockPos pos) {
		return this.getDefaultOpeningType();
	}
	default MunitionsLauncherEnd getOpeningType(MountedMunitionsLauncherContraption contraption, BlockState state, BlockPos pos) {
		return this.getDefaultOpeningType();
	}


	MunitionsLauncherEnd getDefaultOpeningType();

	boolean isComplete(BlockState state);

	default MunitionsLauncherMaterial getCannonMaterialInLevel(LevelAccessor level, BlockState state, BlockPos pos) {
		return this.getCannonMaterial();
	}

	default CannonCastShape getShapeInLevel(LevelAccessor level, BlockState state, BlockPos pos) {
		return this.getCannonShape();
	}

	default boolean canConnectToSide(BlockState state, Direction dir) { return this.getFacing(state).getAxis() == dir.getAxis(); }

	default boolean isImmovable(BlockState state) {
		return false;
	}

	default void onRemoveCannon(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.is(newState.getBlock())) return;
		Direction facing = this.getFacing(state);
		Direction opposite = facing.getOpposite();
		MunitionsLauncherMaterial material = this.getCannonMaterial();

		BlockPos pos1 = pos.relative(facing);
		BlockState state1 = level.getBlockState(pos1);
		BlockEntity be1 = level.getBlockEntity(pos1);

		if (this.canConnectToSide(state, facing)
			&& state1.getBlock() instanceof MunitionsLauncherBlock cBlock1
			&& cBlock1.getCannonMaterialInLevel(level, state1, pos1) == material
			&& be1 instanceof IMunitionsLauncherBlockEntity cbe1
			&& cBlock1.canConnectToSide(state1, opposite)) {
			cbe1.cannonBehavior().setConnectedFace(opposite, false);
			be1.setChanged();
		}
		BlockPos pos2 = pos.relative(opposite);
		BlockState state2 = level.getBlockState(pos2);
		BlockEntity be2 = level.getBlockEntity(pos2);

		if (this.canConnectToSide(state, opposite)
			&& state2.getBlock() instanceof MunitionsLauncherBlock cBlock2
			&& cBlock2.getCannonMaterialInLevel(level, state2, pos2) == material
			&& be2 instanceof IMunitionsLauncherBlockEntity cbe2
			&& cBlock2.canConnectToSide(state2, facing)) {
			cbe2.cannonBehavior().setConnectedFace(facing, false);
			be2.setChanged();
		}
	}

	default void playerWillDestroyMunitionLauncher(Level level, BlockPos pos, BlockState state, Player player) {
		BlockEntity be = level.getBlockEntity(pos);
		if (be instanceof IMunitionsLauncherBlockEntity cbe) {
			StructureBlockInfo info = cbe.cannonBehavior().block();
			BlockState innerState = info.state();
			ItemStack stack = innerState.getBlock() instanceof PropelledContraptionMunitionBlock munition ? munition.getExtractedItem(info) : innerState.getBlock() instanceof BigCannonMunitionBlock munition ? munition.getExtractedItem(info) : ItemStack.EMPTY;
			if (!stack.isEmpty()) {
				Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
			}
			cbe.cannonBehavior().removeBlock();
			be.setChanged();
			if (!innerState.isAir()) {
				innerState.getBlock().playerWillDestroy(level, pos, innerState, player);
				SoundType soundtype = innerState.getSoundType();
				level.playSound(null, pos, soundtype.getBreakSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
			}
		}
	}

	static void onPlace(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);

		if (state.getBlock() instanceof MunitionsLauncherBlock cBlock) {
			Direction facing = cBlock.getFacing(state);
			Direction opposite = facing.getOpposite();
			Vec3 center = Vec3.atCenterOf(pos);
			Vec3 offset = Vec3.atBottomCenterOf(facing.getNormal()).scale(0.5d);
			MunitionsLauncherMaterial material = cBlock.getCannonMaterial();
			CannonCastShape shape = cBlock.getCannonShape();

			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof IMunitionsLauncherBlockEntity cbe) {
				BlockPos pos1 = pos.relative(facing);
				BlockState state1 = level.getBlockState(pos1);
				BlockEntity be1 = level.getBlockEntity(pos1);

				if (cBlock.canConnectToSide(state, facing)
					&& state1.getBlock() instanceof MunitionsLauncherBlock cBlock1
					&& cBlock1.getCannonMaterialInLevel(level, state1, pos1) == material
					&& level.getBlockEntity(pos1) instanceof IMunitionsLauncherBlockEntity cbe1
					&& cBlock1.canConnectToSide(state1, opposite)) {
					cbe.cannonBehavior().setConnectedFace(facing, true);
					cbe1.cannonBehavior().setConnectedFace(opposite, true);
					be1.setChanged();
					if (level instanceof ServerLevel slevel) {
						Vec3 particlePos = center.add(offset);
						slevel.sendParticles(ParticleTypes.CRIT, particlePos.x, particlePos.y, particlePos.z, 10, 0.5d, 0.5d, 0.5d, 0.1d);
					}
				}

				BlockPos pos2 = pos.relative(opposite);
				BlockState state2 = level.getBlockState(pos2);
				BlockEntity be2 = level.getBlockEntity(pos2);

				if (cBlock.canConnectToSide(state, opposite)
					&& state2.getBlock() instanceof MunitionsLauncherBlock cBlock2
					&& cBlock2.getCannonMaterialInLevel(level, state2, pos2) == material
					&& level.getBlockEntity(pos2) instanceof IMunitionsLauncherBlockEntity cbe2
					&& cBlock2.canConnectToSide(state2, facing)) {
					cbe.cannonBehavior().setConnectedFace(opposite, true);
					cbe2.cannonBehavior().setConnectedFace(facing, true);
					be2.setChanged();
					if (level instanceof ServerLevel slevel) {
						Vec3 particlePos = center.add(offset.reverse());
						slevel.sendParticles(ParticleTypes.CRIT, particlePos.x, particlePos.y, particlePos.z, 10, 0.5d, 0.5d, 0.5d, 0.1d);
					}
				}
				be.setChanged();
			}
		}
	}


	default boolean onInteractWhileAssembled(Player player, BlockPos localPos, Direction side, InteractionHand interactionHand,
											 Level level, AbstractMountedCannonContraption contraption, BlockEntity be,
											 StructureBlockInfo info, PitchOrientedContraptionEntity entity) {
		if (!(be instanceof IMunitionsLauncherBlockEntity cbe)
			|| !(contraption instanceof MountedMunitionsLauncherContraption cannon)
			|| ((MunitionsLauncherBlock) info.state().getBlock()).getFacing(info.state()).getAxis() != side.getAxis()
			|| cbe.cannonBehavior().isConnectedTo(side))
			return false;
		ItemStack stack = player.getItemInHand(interactionHand);
		if (Block.byItem(stack.getItem()) instanceof BigCannonMunitionBlock munition) {
			if (!level.isClientSide) {
				StructureBlockInfo loadInfo = munition.getHandloadingInfo(stack, localPos, side);
				boolean flag = false;
				if (cbe.cannonBehavior().tryLoadingBlock(loadInfo)) {
					writeAndSyncSingleBlockData(be, info, entity, contraption);
					flag = true;
				}
				if (flag) {
					SoundType sound = loadInfo.state().getSoundType();
					level.playSound(null, player.blockPosition(), sound.getPlaceSound(), SoundSource.BLOCKS, sound.getVolume(), sound.getPitch());
					if (!player.isCreative()) stack.shrink(1);
				}
			}
			return true;
		} else if (Block.byItem(stack.getItem()) instanceof ContraptionMunitionBlock munition) {
			if (!level.isClientSide) {
				StructureBlockInfo loadInfo = munition.getHandloadingInfo(stack, localPos, side);
				boolean flag = false;
				if (cbe.cannonBehavior().tryLoadingBlock(loadInfo)) {
					writeAndSyncSingleBlockData(be, info, entity, contraption);
					flag = true;
				}
				if (flag) {
					SoundType sound = loadInfo.state().getSoundType();
					level.playSound(null, player.blockPosition(), sound.getPlaceSound(), SoundSource.BLOCKS, sound.getVolume(), sound.getPitch());
					if (!player.isCreative()) stack.shrink(1);
				}
			}
			return true;
		}
		if (stack.getItem() instanceof RamRodItem tool && !player.getCooldowns().isOnCooldown(stack.getItem())) {
			ramOnLauncher(player, level, localPos, side, cannon, tool);
			return true;
		}
		if (stack.getItem() instanceof WormItem tool && !player.getCooldowns().isOnCooldown(stack.getItem())) {
			wormOnLauncher(player, level, localPos, side, cannon, tool);
			return true;
		}
		return false;
	}

	@Override default boolean isWeldable(BlockState state) { return this.getCannonMaterial().properties().isWeldable(); }
	@Override default int weldDamage() { return this.getCannonMaterial().properties().weldDamage(); }

	@Override
	default boolean canWeldSide(Level level, Direction dir, BlockState state, BlockState otherState, BlockPos pos) {
		return otherState.getBlock() instanceof MunitionsLauncherBlock cblock
			&& cblock.getCannonMaterial() == this.getCannonMaterial()
			&& this.isWeldable(state)
			&& cblock.isWeldable(otherState)
			&& this.canConnectToSide(state, dir)
			&& cblock.canConnectToSide(otherState, dir.getOpposite())
			&& level.getBlockEntity(pos) instanceof IMunitionsLauncherBlockEntity cbe
			&& level.getBlockEntity(pos.relative(dir)) instanceof IMunitionsLauncherBlockEntity cbe1
			&& (!cbe.cannonBehavior().isConnectedTo(dir) || !cbe1.cannonBehavior().isConnectedTo(dir.getOpposite()));
	}

	@Override
	default void weldBlock(Level level, BlockState state, BlockPos pos, Direction dir) {
		if (!(level.getBlockEntity(pos) instanceof IMunitionsLauncherBlockEntity cbe)) return;
		MunitionsLauncherBehavior behavior = cbe.cannonBehavior();
		behavior.setConnectedFace(dir, true);
		behavior.setWelded(dir, true);
		behavior.blockEntity.notifyUpdate();
	}
	@Nonnull
	@Override
	default AbstractMountedCannonContraption getCannonContraption() {
		return new MountedMunitionsLauncherContraption();
	}


	default void ramOnLauncher(Player player, Level level, BlockPos startPos, Direction face, MountedMunitionsLauncherContraption contraption, RamRodItem tool) {
		if (player instanceof DeployerFakePlayer && !deployersCanUse()) return;
		Direction pushDirection = face.getOpposite();

		int k = 0;
		if (contraption.presentBlockEntities.get(startPos) instanceof IMunitionsLauncherBlockEntity) {
			k = -1;
			for (int i = 0; i < getReach(); ++i) {
				BlockPos pos1 = startPos.relative(pushDirection, i);
				StructureBlockInfo info = contraption.getBlocks().get(pos1);
				if (info == null || !isValidRamItem(info.state(), contraption, pos1, pushDirection)) return;

				if (contraption.presentBlockEntities.get(pos1) instanceof IMunitionsLauncherBlockEntity cbe) {
					StructureBlockInfo info1 = cbe.cannonBehavior().block();
					if (info1.state().isAir()) continue;
				}
				k = i;
				break;
			}
			if (k == -1) return;
		}

		List<StructureBlockInfo> toPush = new ArrayList<>();
		boolean encounteredCannon = false;
		int maxCount = getPushStrength();
		for (int i = 0; i < maxCount + 1; ++i) {
			BlockPos pos1 = startPos.relative(pushDirection, i + k);
			StructureBlockInfo info = contraption.getBlocks().get(pos1);
			if (info == null || !isValidRamItem(info.state(), contraption, pos1, pushDirection)) return;
			if (!(contraption.presentBlockEntities.get(pos1) instanceof IMunitionsLauncherBlockEntity cbe)) break;
			encounteredCannon = true;
			StructureBlockInfo info1 = cbe.cannonBehavior().block();
			if (info1.state().isAir()) break;
			toPush.add(info1);
			if (toPush.size() > maxCount) return;
		}
		if (!encounteredCannon || toPush.isEmpty()) return;

		if (!level.isClientSide) {
			Set<BlockPos> changes = new HashSet<>(2);
			for (int i = toPush.size() - 1; i >= 0; --i) {
				BlockPos pos1 = startPos.relative(pushDirection, i + k);
				BlockPos pos2 = pos1.relative(pushDirection);
				StructureBlockInfo info = toPush.get(i);

				BlockEntity be1 = contraption.presentBlockEntities.get(pos1);
				BlockEntity be2 = contraption.presentBlockEntities.get(pos2);
				if (!(be1 instanceof IMunitionsLauncherBlockEntity cbe)
					|| !(be2 instanceof IMunitionsLauncherBlockEntity cbe1)) break;
				cbe.cannonBehavior().removeBlock();
				cbe1.cannonBehavior().tryLoadingBlock(info);

				changes.add(pos2);
				if (i == 0) changes.add(pos1);
			}
			BigCannonBlock.writeAndSyncMultipleBlockData(changes, contraption.entity, contraption);
		}

		level.playSound(null, player.blockPosition(), SoundEvents.WOOL_PLACE, SoundSource.PLAYERS, 1, 1);
		player.causeFoodExhaustion(toPush.size() * CBCConfigs.server().cannons.loadingToolHungerConsumption.getF());
		player.getCooldowns().addCooldown(tool, CBCConfigs.server().cannons.loadingToolCooldown.get());
	}

	public static boolean isValidRamItem(BlockState state, MountedMunitionsLauncherContraption contraption, BlockPos pos, Direction dir) {
		if (state.getBlock() instanceof PropelledContraptionMunitionBlock munition)
			return munition.canBeLoaded(state, dir.getAxis());
		if (state.getBlock() instanceof BigCannonMunitionBlock munition)
			return munition.canBeLoaded(state, dir.getAxis());
		if (state.getBlock() instanceof MunitionsLauncherBlock cBlock)
			return cBlock.getFacing(state).getAxis() == dir.getAxis();
		return false;
	}

	default void wormOnLauncher(Player player, Level level, BlockPos startPos, Direction face, MountedMunitionsLauncherContraption contraption, WormItem tool) {
		if (player instanceof DeployerFakePlayer && !CBCConfigs.server().cannons.deployersCanUseLoadingTools.get())
			return;
		Direction reachDirection = face.getOpposite();

		Set<BlockPos> changes = new HashSet<>(2);
		for (int i = 0; i < CBCConfigs.server().cannons.wormReach.get(); ++i) {
			BlockPos pos1 = startPos.relative(reachDirection, i);
			StructureBlockInfo info = contraption.getBlocks().get(pos1);
			if (info == null || !isValidWormBlock(info.state(), contraption, pos1, reachDirection)) return;
			BlockEntity be = contraption.presentBlockEntities.get(pos1);
			if (!(be instanceof IMunitionsLauncherBlockEntity cbe)) return;

			StructureBlockInfo info1 = cbe.cannonBehavior().block();
			if (info1.state().isAir()) continue;

			BlockPos pos2 = pos1.relative(face);
			BlockEntity be1 = contraption.presentBlockEntities.get(pos2);
			if (be1 instanceof IMunitionsLauncherBlockEntity cbe1 && cbe1.cannonBehavior().canLoadBlock(info1)) {
				if (!level.isClientSide) {
					cbe1.cannonBehavior().loadBlock(info1);
					changes.add(pos2);
				}
			} else if (i == 0) {
				if (!level.isClientSide) {
					ItemStack stack = info1.state().getBlock() instanceof PropelledContraptionMunitionBlock munition ? munition.getExtractedItem(info1) :
						info1.state().getBlock() instanceof BigCannonMunitionBlock bigMunition ? bigMunition.getExtractedItem(info1) : ItemStack.EMPTY;
					if (!player.addItem(stack) && !player.isCreative()) {
						ItemEntity item = player.drop(stack, false);
						if (item != null) {
							item.setNoPickUpDelay();
							item.setTarget(player.getUUID());
						}
					}
				}
			} else {
				return;
			}
			if (!level.isClientSide) {
				cbe.cannonBehavior().removeBlock();
				changes.add(pos1);
				BigCannonBlock.writeAndSyncMultipleBlockData(changes, contraption.entity, contraption);
			}

			level.playSound(null, player.blockPosition(), SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 1, 1);
			player.causeFoodExhaustion(CBCConfigs.server().cannons.loadingToolHungerConsumption.getF());
			player.getCooldowns().addCooldown(tool, CBCConfigs.server().cannons.loadingToolCooldown.get());
			return;
		}
	}

	static boolean isValidWormBlock(BlockState state, MountedMunitionsLauncherContraption contraption, BlockPos pos, Direction dir) {
		if (state.getBlock() instanceof PropelledContraptionMunitionBlock munition)
			return munition.canBeLoaded(state, dir.getAxis());
		if (state.getBlock() instanceof BigCannonMunitionBlock munition)
			return munition.canBeLoaded(state, dir.getAxis());
		if (state.getBlock() instanceof MunitionsLauncherBlock cBlock)
			return cBlock.getFacing(state).getAxis() == dir.getAxis();
		return false;
	}

	static int getPushStrength() {
		return CBCConfigs.server().cannons.ramRodStrength.get();
	}

	static int getReach() {
		return CBCConfigs.server().cannons.ramRodReach.get();
	}
	static boolean deployersCanUse() {
		return CBCConfigs.server().cannons.deployersCanUseLoadingTools.get();
	}
}
