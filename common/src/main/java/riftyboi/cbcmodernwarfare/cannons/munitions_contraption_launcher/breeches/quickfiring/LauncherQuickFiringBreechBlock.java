package riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.quickfiring;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.simibubi.create.content.contraptions.Contraption;

import com.simibubi.create.api.contraption.transformable.TransformableBlock;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;
import com.simibubi.create.foundation.block.IBE;

import net.createmod.catnip.data.Iterate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBlock;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import rbasamoyai.createbigcannons.effects.particles.smoke.QuickFiringBreechSmokeParticleData;
import rbasamoyai.createbigcannons.equipment.manual_loading.RamRodItem;
import rbasamoyai.createbigcannons.equipment.manual_loading.WormItem;
import rbasamoyai.createbigcannons.index.CBCItems;
import rbasamoyai.createbigcannons.munitions.big_cannon.BigCannonMunitionBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.BigCartridgeBlock;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedMunitionsLauncherContraption;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.IMunitionsLauncherBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBaseBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end.MunitionsLauncherEnd;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.material.MunitionsLauncherMaterial;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.PropelledContraptionMunitionBlock;

public class LauncherQuickFiringBreechBlock extends MunitionsLauncherBaseBlock implements IBE<LauncherQuickFiringBreechBlockEntity>, TransformableBlock, IWrenchable {

		public static final BooleanProperty AXIS = DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE;

		private final NonNullSupplier<? extends Block> slidingConversion;

	public LauncherQuickFiringBreechBlock(Properties properties, MunitionsLauncherMaterial material, NonNullSupplier<? extends Block> slidingConversion) {
			super(properties, material);
			this.slidingConversion = slidingConversion;
		}

		@Override
		protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
			super.createBlockStateDefinition(builder);
			builder.add(AXIS);
		}

		@Override
		public BlockState getStateForPlacement(BlockPlaceContext context) {
			return super.getStateForPlacement(context).setValue(AXIS, context.getNearestLookingDirection().getAxis() == Direction.Axis.Z);
		}

		@Override
		public Class<LauncherQuickFiringBreechBlockEntity> getBlockEntityClass() {
			return LauncherQuickFiringBreechBlockEntity.class;
		}

		@Override
		public BlockEntityType<? extends LauncherQuickFiringBreechBlockEntity> getBlockEntityType() {
			return CBCModernWarfareBlockEntities.LAUNCHER_QUICKFIRING_BREECH.get();
		}

		@Override
		public CannonCastShape getCannonShape() {
			return CannonCastShape.SLIDING_BREECH;
		}

		@Override
		public boolean isComplete(BlockState state) {
			return true;
		}

		@Override public MunitionsLauncherEnd getDefaultOpeningType() { return MunitionsLauncherEnd.CLOSED; }


	@Override
		public boolean onInteractWhileAssembled(Player player, BlockPos localPos, Direction side, InteractionHand interactionHand,
												Level level, AbstractMountedCannonContraption contraption, BlockEntity be,
												StructureBlockInfo info, PitchOrientedContraptionEntity entity) {
			if (!(contraption instanceof MountedMunitionsLauncherContraption cannon)
				|| !(be instanceof LauncherQuickFiringBreechBlockEntity breech)
				|| ((MunitionsLauncherBlock) info.state().getBlock()).getFacing(info.state()).getAxis() != side.getAxis()
				|| breech.cannonBehavior().isConnectedTo(side)) return false;
			ItemStack stack = player.getItemInHand(interactionHand);

			Direction pushDirection = side.getOpposite();
			BlockPos nextPos = localPos.relative(pushDirection);

			if (stack.isEmpty()) {
				if (level instanceof ServerLevel slevel) {
					if (!breech.onInteractionCooldown()) {
						SoundEvent sound = breech.getOpenProgress() == 0 ? SoundEvents.IRON_TRAPDOOR_OPEN : SoundEvents.IRON_TRAPDOOR_CLOSE;
						level.playSound(null, player.blockPosition(), sound, SoundSource.BLOCKS, 1.0f, 1.5f);
					}
					breech.toggleOpening();
					Set<BlockPos> changed = new HashSet<>(3);
					changed.add(localPos);

					if (breech.getOpenDirection() > 0) {
						BlockEntity be1 = contraption.presentBlockEntities.get(nextPos);
						if (be1 instanceof IMunitionsLauncherBlockEntity cbe1) {
							StructureBlockInfo info1 = cbe1.cannonBehavior().block();
							ItemStack extract = info1.state().getBlock() instanceof BigCannonMunitionBlock munition ? munition.getExtractedItem(info1) : info1.state().getBlock() instanceof PropelledContraptionMunitionBlock munition ? munition.getExtractedItem(info1) : ItemStack.EMPTY;
							Vec3 normal = new Vec3(side.step());
							Vec3 dir = contraption.entity.applyRotation(normal, 1);
							if (!extract.isEmpty()) {
								Vec3 ejectPos = Vec3.atCenterOf(localPos).add(normal.scale(1.1));
								Vec3 globalPos = entity.toGlobalVector(ejectPos, 1);
								if (CBCConfigs.server().munitions.quickFiringBreechItemGoesToInventory.get()) {
									if (!player.addItem(extract) && !player.isCreative()) {
										ItemEntity item = player.drop(extract, false);
										if (item != null) {
											item.setNoPickUpDelay();
											item.setTarget(player.getUUID());
										}
									}
								} else {
									Vec3 vel = dir.scale(0.075);
									ItemEntity item = new ItemEntity(level, globalPos.x, globalPos.y, globalPos.z, extract, vel.x, vel.y, vel.z);
									item.setPickUpDelay(CBCConfigs.server().munitions.quickFiringBreechItemPickupDelay.get());
									level.addFreshEntity(item);
								}
							}
							cbe1.cannonBehavior().removeBlock();
							changed.add(nextPos);
							if (cannon.hasFired) {
								Vec3 smokePos = Vec3.atCenterOf(localPos).add(normal.scale(0.6));
								Vec3 globalPos = entity.toGlobalVector(smokePos, 1);
								Vec3 vel = dir.scale(0.075);
								slevel.sendParticles(new QuickFiringBreechSmokeParticleData(), globalPos.x, globalPos.y, globalPos.z, 0, vel.x, vel.y, vel.z, 1);
								cannon.hasFired = false;
							}
						}
					}
					BigCannonBlock.writeAndSyncMultipleBlockData(changed, entity, contraption);
				}
				return true;
			}
			if (!breech.isOpen() || breech.onInteractionCooldown()) return false;

			if (Block.byItem(stack.getItem()) instanceof BigCannonMunitionBlock munition) {
				BlockEntity be1 = contraption.presentBlockEntities.get(nextPos);
				if (!(be1 instanceof IMunitionsLauncherBlockEntity cbe1)) return false;

				StructureBlockInfo loadInfo = munition.getHandloadingInfo(stack, nextPos, side);
				if (munition instanceof BigCartridgeBlock) loadInfo = munition.getHandloadingInfo(stack, nextPos, pushDirection);

				StructureBlockInfo info1 = cbe1.cannonBehavior().block();

				if (!level.isClientSide) {
					Set<BlockPos> changes = new HashSet<>(2);

					if (!info1.state().isAir()) {
						BlockPos posAfter = nextPos.relative(pushDirection);
						BlockEntity be2 = contraption.presentBlockEntities.get(posAfter);
						if (!(be2 instanceof IMunitionsLauncherBlockEntity cbe2) || !cbe2.cannonBehavior().canLoadBlock(info1))
							return false;
						cbe2.cannonBehavior().loadBlock(info1);
						cbe1.cannonBehavior().removeBlock();
						changes.add(posAfter);
					}
					cbe1.cannonBehavior().tryLoadingBlock(loadInfo);
					changes.add(nextPos);

					BigCannonBlock.writeAndSyncMultipleBlockData(changes, entity, contraption);

					SoundType sound = loadInfo.state().getSoundType();
					level.playSound(null, player.blockPosition(), sound.getPlaceSound(), SoundSource.BLOCKS, sound.getVolume(), sound.getPitch());
					if (!player.isCreative()) stack.shrink(1);
				}
				return true;
			}
			if (Block.byItem(stack.getItem()) instanceof PropelledContraptionMunitionBlock munition) {
			BlockEntity be1 = contraption.presentBlockEntities.get(nextPos);
			if (!(be1 instanceof IMunitionsLauncherBlockEntity cbe1)) return false;

			StructureBlockInfo loadInfo = munition.getHandloadingInfo(stack, nextPos, side);
			StructureBlockInfo info1 = cbe1.cannonBehavior().block();

			if (!level.isClientSide) {
				Set<BlockPos> changes = new HashSet<>(3);

				if (!info1.state().isAir()) {
					BlockPos posAfter = nextPos.relative(pushDirection);
					BlockEntity be2 = contraption.presentBlockEntities.get(posAfter);
					BlockPos afterAfter = posAfter.relative(pushDirection);
					BlockEntity be3 = contraption.presentBlockEntities.get(afterAfter);
					if ((be2 instanceof IMunitionsLauncherBlockEntity cbe2) && cbe2.cannonBehavior().canLoadBlock(info1)) {
						cbe2.cannonBehavior().loadBlock(info1);
						cbe1.cannonBehavior().removeBlock();
						changes.add(posAfter);
					} else if ((be2 instanceof IMunitionsLauncherBlockEntity cbe2) && !cbe2.cannonBehavior().canLoadBlock(info1) && (be3 instanceof IMunitionsLauncherBlockEntity cbe3) && cbe3.cannonBehavior().canLoadBlock(info1)) {
						StructureBlockInfo info2 = cbe2.cannonBehavior().block();
						cbe3.cannonBehavior().loadBlock(info2);
						cbe2.cannonBehavior().removeBlock();
						cbe2.cannonBehavior().loadBlock(info1);
						cbe1.cannonBehavior().removeBlock();
						changes.add(posAfter);
						changes.add(afterAfter);
					} else {
						return false;
					}

				}
				cbe1.cannonBehavior().tryLoadingBlock(loadInfo);
				changes.add(nextPos);

				BigCannonBlock.writeAndSyncMultipleBlockData(changes, entity, contraption);

				SoundType sound = loadInfo.state().getSoundType();
				level.playSound(null, player.blockPosition(), sound.getPlaceSound(), SoundSource.BLOCKS, sound.getVolume(), sound.getPitch());
				if (!player.isCreative()) stack.shrink(1);
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

		@Override
		public BlockState rotate(BlockState state, Rotation rot) {
			if (rot.ordinal() % 2 == 1) state = state.cycle(AXIS);
			return super.rotate(state, rot);
		}

		@Override
		public BlockState transform(BlockState state, StructureTransform transform) {
			if (transform.mirror != null) {
				state = mirror(state, transform.mirror);
			}

			if (transform.rotationAxis == Direction.Axis.Y) {
				return rotate(state, transform.rotation);
			}

			Direction newFacing = transform.rotateFacing(state.getValue(FACING));
			if (transform.rotationAxis == newFacing.getAxis() && transform.rotation.ordinal() % 2 == 1) {
				state = state.cycle(AXIS);
			}
			return state.setValue(FACING, newFacing);
		}

		protected BlockState getConversion(BlockState old) {
			return this.slidingConversion.get().defaultBlockState()
				.setValue(FACING, old.getValue(FACING))
				.setValue(AXIS, old.getValue(AXIS));
		}

		@Override
		public InteractionResult onWrenched(BlockState state, UseOnContext context) {
			Level level = context.getLevel();
			BlockPos pos = context.getClickedPos();
			if (state.getBlock() instanceof LauncherQuickFiringBreechBlock qfb) {
				Player player = context.getPlayer();
				BlockState newState = qfb.getConversion(state);
				BlockEntity oldBe = level.getBlockEntity(pos);
				if (!(oldBe instanceof IMunitionsLauncherBlockEntity cbe)) return InteractionResult.PASS;
				if (!level.isClientSide) {
					level.setBlock(pos, newState, 11);
					BlockEntity newBe = level.getBlockEntity(pos);

					StructureBlockInfo loaded = cbe.cannonBehavior().block();
					if (player != null) {
						if (loaded != null) {
							Block block = loaded.state().getBlock();
							ItemStack resultStack = block instanceof BigCannonMunitionBlock munition ? munition.getExtractedItem(loaded) :  block instanceof PropelledContraptionMunitionBlock munition ? munition.getExtractedItem(loaded) : new ItemStack(block);
							if (!player.addItem(resultStack) && !player.isCreative()) {
								ItemEntity item = player.drop(resultStack, false);
								if (item != null) {
									item.setNoPickUpDelay();
									item.setTarget(player.getUUID());
								}
							}
						}
						ItemStack resultStack = CBCItems.QUICKFIRING_MECHANISM.asStack();
						if (!player.addItem(resultStack) && !player.isCreative()) {
							ItemEntity item = player.drop(resultStack, false);
							if (item != null) {
								item.setNoPickUpDelay();
								item.setTarget(player.getUUID());
							}
						}
					}

					if (newBe instanceof IMunitionsLauncherBlockEntity cbe1) {
						for (Direction dir : Iterate.directions) {
							boolean flag = cbe.cannonBehavior().isConnectedTo(dir);
							cbe1.cannonBehavior().setConnectedFace(dir, flag);
							if (level.getBlockEntity(pos.relative(dir)) instanceof IMunitionsLauncherBlockEntity cbe2) {
								cbe2.cannonBehavior().setConnectedFace(dir.getOpposite(), flag);
							}
						}
						newBe.setChanged();
					}
					IWrenchable.playRemoveSound(level, pos);
				}
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
			return InteractionResult.PASS;
		}

		@Override
		public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
			return InteractionResult.PASS;
		}

	public void ramOnLauncher(Player player, Level level, BlockPos startPos, Direction face, MountedMunitionsLauncherContraption contraption, RamRodItem tool) {
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

	public void wormOnLauncher(Player player, Level level, BlockPos startPos, Direction face, MountedMunitionsLauncherContraption contraption, WormItem tool) {
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
