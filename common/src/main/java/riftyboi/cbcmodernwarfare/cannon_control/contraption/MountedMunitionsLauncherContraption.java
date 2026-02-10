package riftyboi.cbcmodernwarfare.cannon_control.contraption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.api.contraption.ContraptionType;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.AssemblyException;

import com.simibubi.create.content.contraptions.OrientedContraptionEntity;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.NotNull;

import org.lwjgl.system.CallbackI;

import rbasamoyai.createbigcannons.CBCTags;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.cannon_control.ControlPitchContraption;
import rbasamoyai.createbigcannons.cannon_control.cannon_types.ICannonContraptionType;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBehavior;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBlock;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.effects.particles.explosions.CannonBlastWaveEffectParticleData;
import rbasamoyai.createbigcannons.effects.particles.plumes.BigCannonPlumeParticleData;
import rbasamoyai.createbigcannons.index.CBCEntityTypes;
import rbasamoyai.createbigcannons.index.CBCSoundEvents;
import rbasamoyai.createbigcannons.munitions.AbstractCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.BigCannonMunitionBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.ProjectileBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.BigCannonPropellantBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.BigCartridgeBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.IntegratedPropellantProjectile;
import rbasamoyai.createbigcannons.munitions.config.BigCannonPropellantCompatibilities;
import rbasamoyai.createbigcannons.munitions.config.BigCannonPropellantCompatibilityHandler;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.utils.CBCUtils;
import rbasamoyai.ritchiesprojectilelib.RitchiesProjectileLib;
import rbasamoyai.ritchiesprojectilelib.effects.screen_shake.ScreenShakeEffect;
import riftyboi.cbcmodernwarfare.cannon_control.cannon_types.CBCMWCannonContraptionTypes;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.IMunitionsLauncherBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBehavior;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.IBreechBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.quickfiring.LauncherQuickFiringBreechBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.breeches.quickfiring.LauncherQuickFiringBreechBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end.MunitionsLauncherEnd;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.material.MunitionsLauncherMaterial;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.material.MunitionsLauncherMaterialProperties;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareContraptionTypes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionsLauncherMaterials;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareSoundEvents;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraption;
import riftyboi.cbcmodernwarfare.munitions.contraptions.MunitionsPhysicsContraptionEntity;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.fuel_tanks.IFuelTankBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.fuel_tanks.MunitionsLauncherFuelTankBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.guidance.MunitionsLauncherGuidanceBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.propulsion.IThrusterBlock;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.propulsion.MunitionsLauncherThrusterBlock;

import javax.annotation.Nullable;

import static rbasamoyai.createbigcannons.munitions.big_cannon.propellant.BigCartridgeBlock.FILLED;

public class MountedMunitionsLauncherContraption extends AbstractMountedCannonContraption {
	private MunitionsLauncherMaterial cannonMaterial;
	public boolean hasFired = false;
	public boolean hasWeldedPenalty = false;
	public BlockPos endPos;

	@Override
	public boolean assemble(Level level, BlockPos pos) throws AssemblyException {
		if (!this.collectCannonBlocks(level, pos)) return false;
		this.bounds = this.createBoundsFromExtensionLengths();
		return !this.blocks.isEmpty();
	}
	private boolean collectCannonBlocks(Level level, BlockPos pos) throws AssemblyException {
		BlockState startState = level.getBlockState(pos);

		if (!(startState.getBlock() instanceof MunitionsLauncherBlock startCannon)) {
			return false;
		}
		if (!startCannon.isComplete(startState)) {
			throw hasIncompleteCannonBlocks(pos);
		}
		if (this.hasCannonLoaderInside(level, startState, pos)) {
			throw cannonLoaderInsideDuringAssembly(pos);
		}
		MunitionsLauncherMaterial material = startCannon.getCannonMaterial();
		MunitionsLauncherEnd startEnd = startCannon.getOpeningType(level, startState, pos);

		List<StructureBlockInfo> cannonBlocks = new ArrayList<>();
		cannonBlocks.add(new StructureBlockInfo(pos, startState, this.getBlockEntityNBT(level, pos)));

		int cannonLength = 1;

		Direction cannonFacing = startCannon.getFacing(startState);

		Direction positive = Direction.get(Direction.AxisDirection.POSITIVE, cannonFacing.getAxis());
		Direction negative = positive.getOpposite();

		BlockPos start = pos;
		BlockState nextState = level.getBlockState(pos.relative(positive));

		MunitionsLauncherEnd positiveEnd = startEnd;
		while (this.isValidCannonBlock(level, nextState, start.relative(positive)) && this.isConnectedToCannon(level, nextState, start.relative(positive), positive, material)) {
			start = start.relative(positive);

			if (!((MunitionsLauncherBlock) nextState.getBlock()).isComplete(nextState)) {
				throw hasIncompleteCannonBlocks(start);
			}

			cannonBlocks.add(new StructureBlockInfo(start, nextState, this.getBlockEntityNBT(level, start)));
			this.frontExtensionLength++;
			cannonLength++;

			positiveEnd = ((MunitionsLauncherBlock) nextState.getBlock()).getOpeningType(level, nextState, start);

			if (this.hasCannonLoaderInside(level, nextState, start)) {
				throw cannonLoaderInsideDuringAssembly(start);
			}

			nextState = level.getBlockState(start.relative(positive));

			if (cannonLength > getMaxCannonLength()) {
				throw cannonTooLarge();
			}
			if (positiveEnd != MunitionsLauncherEnd.OPEN) break;
		}
		BlockPos positiveEndPos = positiveEnd == MunitionsLauncherEnd.OPEN ? start : start.relative(negative);

		start = pos;
		nextState = level.getBlockState(pos.relative(negative));

		MunitionsLauncherEnd negativeEnd = startEnd;
		while (this.isValidCannonBlock(level, nextState, start.relative(negative)) && this.isConnectedToCannon(level, nextState, start.relative(negative), negative, material)) {
			start = start.relative(negative);

			if (!((MunitionsLauncherBlock) nextState.getBlock()).isComplete(nextState)) {
				throw hasIncompleteCannonBlocks(start);
			}

			cannonBlocks.add(new StructureBlockInfo(start, nextState, this.getBlockEntityNBT(level, start)));
			this.backExtensionLength++;
			cannonLength++;

			negativeEnd = ((MunitionsLauncherBlock) nextState.getBlock()).getOpeningType(level, nextState, start);

			if (this.hasCannonLoaderInside(level, nextState, start)) {
				throw cannonLoaderInsideDuringAssembly(start);
			}

			nextState = level.getBlockState(start.relative(negative));

			if (cannonLength > getMaxCannonLength()) {
				throw cannonTooLarge();
			}
			if (negativeEnd != MunitionsLauncherEnd.OPEN) break;
		}
		BlockPos negativeEndPos = negativeEnd == MunitionsLauncherEnd.OPEN ? start : start.relative(positive);

		if (positiveEnd == negativeEnd) {
			throw invalidCannon();
		}

		boolean openEndFlag = positiveEnd == MunitionsLauncherEnd.OPEN;
		this.initialOrientation = openEndFlag ? positive : negative;
		this.startPos = openEndFlag ? negativeEndPos : positiveEndPos;
		this.anchor = pos;

		this.startPos = this.startPos.subtract(pos);
		for (StructureBlockInfo blockInfo : cannonBlocks) {
			BlockPos localPos = blockInfo.pos().subtract(pos);
			StructureBlockInfo localBlockInfo = new StructureBlockInfo(localPos, blockInfo.state(), blockInfo.nbt());
			this.getBlocks().put(localPos, localBlockInfo);

			if (blockInfo.nbt() == null) continue;
			BlockEntity be = BlockEntity.loadStatic(localPos, blockInfo.state(), blockInfo.nbt());
			this.presentBlockEntities.put(localPos, be);
			if (be instanceof IMunitionsLauncherBlockEntity cbe && cbe.cannonBehavior().isWelded()) this.hasWeldedPenalty = true;
		}
		this.cannonMaterial = material;

		return true;
	}

	private boolean isValidCannonBlock(LevelAccessor level, BlockState state, BlockPos pos) {
		return state.getBlock() instanceof MunitionsLauncherBlock;
	}

	private boolean hasCannonLoaderInside(LevelAccessor level, BlockState state, BlockPos pos) {
		BlockEntity be = level.getBlockEntity(pos);
		if (!(be instanceof IMunitionsLauncherBlockEntity cannon)) return false;
		BlockState containedState = cannon.cannonBehavior().block().state();
		return IMunitionsLauncherBlockEntity.isValidLoader(null, new StructureBlockInfo(BlockPos.ZERO, containedState, null));
	}

	private boolean isConnectedToCannon(LevelAccessor level, BlockState state, BlockPos pos, Direction connection, MunitionsLauncherMaterial material) {
		MunitionsLauncherBlock cBlock = (MunitionsLauncherBlock) state.getBlock();
		if (cBlock.getCannonMaterialInLevel(level, state, pos) != material) return false;
		return level.getBlockEntity(pos) instanceof IMunitionsLauncherBlockEntity cbe
			&& level.getBlockEntity(pos.relative(connection.getOpposite())) instanceof IMunitionsLauncherBlockEntity cbe1
			&& cbe.cannonBehavior().isConnectedTo(connection.getOpposite())
			&& cbe1.cannonBehavior().isConnectedTo(connection);
	}
	public float getWeightForStress() {
		if (this.cannonMaterial == null) {
			return this.blocks.size();
		}
		return this.blocks.size() * this.cannonMaterial.properties().weight();
	}


	public void fail(BlockPos localPos, Level level, PitchOrientedContraptionEntity entity, @Nullable BlockEntity failed, int charges) {
		Vec3 failurePoint = entity.toGlobalVector(Vec3.atCenterOf(localPos), 0);
		float failScale = CBCConfigs.server().failure.failureExplosionPower.getF();
		if (this.cannonMaterial.properties().failureMode() == MunitionsLauncherMaterialProperties.FailureMode.RUPTURE) {
			int failInt = Mth.ceil(failScale);
			BlockPos startPos = localPos.relative(this.initialOrientation.getOpposite(), failInt);
			for (int i = 0; i < failInt * 2 + 1; ++i) {
				BlockPos pos = startPos.relative(this.initialOrientation, i);
				this.blocks.remove(pos);
				this.presentBlockEntities.remove(pos);
			}
			ControlPitchContraption controller = entity.getController();
			if (controller != null) controller.disassemble();

			level.explode(null, failurePoint.x, failurePoint.y, failurePoint.z, 2 * failScale + 1, Level.ExplosionInteraction.NONE);
		} else {
			for (Iterator<Map.Entry<BlockPos, StructureBlockInfo>> iter = this.blocks.entrySet().iterator(); iter.hasNext(); ) {
				Map.Entry<BlockPos, StructureBlockInfo> entry = iter.next();
				this.presentBlockEntities.remove(entry.getKey());
				iter.remove();
			}

			float power = (float) charges * failScale;
			level.explode(null, failurePoint.x, failurePoint.y, failurePoint.z, power, Level.ExplosionInteraction.TNT);
			entity.discard();
		}
	}

	@Override
	public void tick(Level level, PitchOrientedContraptionEntity entity) {
		super.tick(level, entity);
		BlockPos endPos = this.startPos.relative(this.initialOrientation.getOpposite());
		if (this.presentBlockEntities.get(endPos) instanceof LauncherQuickFiringBreechBlockEntity qfbreech)
			qfbreech.tickAnimation();
		for (Map.Entry<BlockPos, StructureBlockInfo> entry : this.getBlocks().entrySet()) {
			if (this.getBlocks().get(entry.getKey()).state().getBlock() instanceof MunitionsLauncherGuidanceBlock guidance) {
				guidance.tickFromContraption(level, entry.getKey(), entry.getValue().state, this.presentBlockEntities.get(entry.getKey()), entity);
			}
		}
	}

	@Override
	public void onRedstoneUpdate(ServerLevel level, PitchOrientedContraptionEntity entity, boolean togglePower, int firePower, ControlPitchContraption controller) {
		if (!togglePower || firePower <= 0) return;
		this.fireShot(level, entity);
	}

	@Override
	public void fireShot(ServerLevel level, PitchOrientedContraptionEntity entity) {
		BlockPos endPos = this.startPos.relative(this.initialOrientation.getOpposite());
		BlockPos currentPos = this.startPos.immutable();
		if (this.presentBlockEntities.get(endPos) instanceof LauncherQuickFiringBreechBlockEntity qfbreech && qfbreech.getOpenProgress() > 0)
			return;
		if (!(this.presentBlockEntities.get(endPos) instanceof IBreechBlockEntity)) {
			currentPos = endPos;
		}

		ControlPitchContraption controller = entity.getController();

		RandomSource rand = level.getRandom();
		int count = 0;
		int maxSafeCharges = this.getMaxSafeCharges();
		boolean canFail = !CBCConfigs.server().failure.disableAllFailure.get();
		float spreadSub = this.cannonMaterial.properties().spreadReduction();
		boolean emptyNoProjectile = false;
		boolean hasThruster = false;
		PropellantContext propelCtx = new PropellantContext();
		List<StructureBlockInfo> projectileBlocks = new ArrayList<>();
		AbstractBigCannonProjectile projectile = null;
		BlockPos assemblyPos = null;
		MunitionsLauncherMaterialProperties properties = this.cannonMaterial.properties();
		float minimumSpread = this.cannonMaterial.properties().minimumSpread();
		List<BlockPos> changed = new ArrayList<>();
		MunitionsPhysicsContraption munitionsPhysicsContraption = new MunitionsPhysicsContraption();

		while (this.presentBlockEntities.get(currentPos) instanceof IMunitionsLauncherBlockEntity cbe) {
			MunitionsLauncherBehavior behavior = cbe.cannonBehavior();
			StructureBlockInfo containedBlockInfo = behavior.block();
			StructureBlockInfo cannonInfo = this.blocks.get(currentPos);
			if (cannonInfo == null) break;

			Block block = containedBlockInfo.state().getBlock();

			if (containedBlockInfo.state().isAir()) {
				if (count == 0) return;
				if (projectile == null) {
					if (projectileBlocks.isEmpty()) {
						emptyNoProjectile = true;
						propelCtx.chargesUsed = Math.max(propelCtx.chargesUsed - 1, 0);
					} else if (canFail && !propelCtx.propellantBlocks.isEmpty()) {
						this.fail(currentPos, level, entity, behavior.blockEntity, (int) propelCtx.chargesUsed);
						return;
					}
				} else {
					++propelCtx.barrelTravelled;
					if (cannonInfo.state().is(CBCTags.CBCBlockTags.REDUCES_SPREAD)) {
						propelCtx.spread = Math.max(propelCtx.spread - spreadSub, minimumSpread);
					}
					if (canFail && projectile.canSquib() && this.cannonMaterial.properties().mayGetStuck(propelCtx.chargesUsed, propelCtx.barrelTravelled) && rollSquib(rand)) {
						this.squibBlocks(currentPos, projectileBlocks);
						Vec3 squibPos = entity.toGlobalVector(Vec3.atCenterOf(currentPos.relative(this.initialOrientation)), 0);
						level.playSound(null, squibPos.x, squibPos.y, squibPos.z, cannonInfo.state().getSoundType().getBreakSound(), SoundSource.BLOCKS, 10.0f, 0.0f);
						return;
					}
				}
			} else if (block instanceof BigCannonPropellantBlock cpropel && !(block instanceof ProjectileBlock)) {
				if (!cpropel.canBeIgnited(containedBlockInfo, this.initialOrientation)) return;
				if (!propelCtx.addPropellant(cpropel, containedBlockInfo, this.initialOrientation) && canFail) {
					this.fail(currentPos, level, entity, behavior.blockEntity, (int) propelCtx.chargesUsed);
					return;
				}
				changed.add(currentPos);
				if (canFail && (!cbe.blockCanHandle(cannonInfo) && rollBarrelBurst(rand)
					|| propelCtx.stress > maxSafeCharges && rollOverloadBurst(rand))) {
					this.fail(currentPos, level, entity, behavior.blockEntity, (int) propelCtx.chargesUsed);
					return;
				}
				if (emptyNoProjectile && canFail && rollFailToIgnite(rand)) {
					Vec3 failIgnitePos = entity.toGlobalVector(Vec3.atCenterOf(currentPos.relative(this.initialOrientation)), 0);
					level.playSound(null, failIgnitePos.x, failIgnitePos.y, failIgnitePos.z, cannonInfo.state().getSoundType().getBreakSound(), SoundSource.BLOCKS, 5.0f, 0.0f);
					return;
				}
				emptyNoProjectile = false;
			} else if (block instanceof ProjectileBlock<?> projBlock && projectile == null) {
				if (canFail && emptyNoProjectile && rollFailToIgnite(rand)) {
					Vec3 failIgnitePos = entity.toGlobalVector(Vec3.atCenterOf(currentPos.relative(this.initialOrientation)), 0);
					level.playSound(null, failIgnitePos.x, failIgnitePos.y, failIgnitePos.z, cannonInfo.state().getSoundType().getBreakSound(), SoundSource.BLOCKS, 5.0f, 0.0f);
					return;
				}
				BlockPos localPos = containedBlockInfo.pos().subtract(currentPos);
				StructureBlockInfo localBlockInfo = new StructureBlockInfo(localPos, containedBlockInfo.state(), containedBlockInfo.nbt());
				munitionsPhysicsContraption.getBlocks().put(localPos, localBlockInfo);
				projectileBlocks.add(containedBlockInfo);


				if (assemblyPos == null) assemblyPos = currentPos.immutable();
				if (!propelCtx.propellantBlocks.isEmpty()) {
					List<StructureBlockInfo> copy = ImmutableList.copyOf(projectileBlocks);
					for (ListIterator<StructureBlockInfo> projIter = projectileBlocks.listIterator(); projIter.hasNext(); ) {
						int i = projIter.nextIndex();
						StructureBlockInfo projInfo = projIter.next();
						if (projInfo.state().getBlock() instanceof ProjectileBlock<?> cproj1 && cproj1.isValidAddition(copy, projInfo, i, this.initialOrientation))
							continue;
						if (canFail)
							this.fail(currentPos, level, entity, behavior.blockEntity, (int) propelCtx.chargesUsed);
						return;
					}
				}
				changed.add(currentPos);
				if (cannonInfo.state().is(CBCTags.CBCBlockTags.REDUCES_SPREAD)) {
					propelCtx.spread = Math.max(propelCtx.spread - spreadSub, minimumSpread);
				}
				if (projBlock.isComplete(projectileBlocks, this.initialOrientation) && !propelCtx.propellantBlocks.isEmpty()) {
					projectile = projBlock.getProjectile(level, projectileBlocks);
					propelCtx.chargesUsed += projectile.addedChargePower();
					if (propelCtx.chargesUsed <= 0 || canFail && propelCtx.chargesUsed < projectile.minimumChargePower()) {
						this.squibBlocks(assemblyPos, projectileBlocks);
						return;
					}
				}
				emptyNoProjectile = false;
				if (containedBlockInfo.nbt() != null) {
					BlockEntity be = BlockEntity.loadStatic(containedBlockInfo.pos(), containedBlockInfo.state(), containedBlockInfo.nbt());
					if (be != null) munitionsPhysicsContraption.presentBlockEntities.put(localPos, be);
				}
			} else if (block instanceof MunitionsLauncherThrusterBlock thruster && propelCtx.propellantBlocks.isEmpty()) {
				hasThruster = true;
				changed.add(currentPos);
				BlockPos localPos = containedBlockInfo.pos().subtract(currentPos);
				StructureBlockInfo localBlockInfo = new StructureBlockInfo(localPos, containedBlockInfo.state(), containedBlockInfo.nbt());
				munitionsPhysicsContraption.getBlocks().put(localPos, localBlockInfo);
				propelCtx.addThruster(thruster, containedBlockInfo, this.initialOrientation, currentPos);
			} else if (block instanceof MunitionsLauncherFuelTankBlock fuel && propelCtx.propellantBlocks.isEmpty()) {
				changed.add(currentPos);
				BlockPos localPos = containedBlockInfo.pos().subtract(currentPos);
				StructureBlockInfo localBlockInfo = new StructureBlockInfo(localPos, containedBlockInfo.state(), containedBlockInfo.nbt());
				munitionsPhysicsContraption.getBlocks().put(localPos, localBlockInfo);
				propelCtx.addFuelTank(fuel, containedBlockInfo, this.initialOrientation, currentPos);
			} else if (block instanceof MunitionsLauncherGuidanceBlock guidance && propelCtx.propellantBlocks.isEmpty()) {
				changed.add(currentPos);
				BlockPos localPos = containedBlockInfo.pos().subtract(currentPos);
				StructureBlockInfo localBlockInfo = new StructureBlockInfo(localPos, containedBlockInfo.state(), containedBlockInfo.nbt());
				munitionsPhysicsContraption.getBlocks().put(localPos, localBlockInfo);
				propelCtx.addGuidance(guidance, containedBlockInfo, this.initialOrientation, currentPos);
				if (containedBlockInfo.nbt() != null) {
					BlockEntity be = BlockEntity.loadStatic(containedBlockInfo.pos(), containedBlockInfo.state(), containedBlockInfo.nbt());
					if (be != null) munitionsPhysicsContraption.presentBlockEntities.put(localPos, be);
				}
			}  else {
				if (canFail) {
					this.fail(currentPos, level, entity, behavior.blockEntity, (int) propelCtx.chargesUsed);
					return;
				}
			}
			currentPos = currentPos.relative(this.initialOrientation);
			BlockState cannonState = cannonInfo.state();
			if (cannonState.getBlock() instanceof MunitionsLauncherBlock) {
				++count;
			}
		}
		if (!properties.isGunLauncher() && !propelCtx.propellantBlocks.isEmpty()) {
			if (canFail) this.fail(currentPos, level, entity, null, (int) propelCtx.chargesUsed);
			return;
		}

		if (projectile == null && !projectileBlocks.isEmpty() && !propelCtx.propellantBlocks.isEmpty() && propelCtx.munitionBlocks.isEmpty()) {
			StructureBlockInfo info = projectileBlocks.get(0);
			if (!(info.state().getBlock() instanceof ProjectileBlock<?> projBlock)) {
				if (canFail) this.fail(currentPos, level, entity, null, (int) propelCtx.chargesUsed);
				return;
			}
			int remaining = projBlock.getExpectedSize() - projectileBlocks.size();
			if (remaining < 1) {
				if (canFail) this.fail(currentPos, level, entity, null, (int) propelCtx.chargesUsed);
				return;
			}
			for (int i = 0; i < remaining; ++i) {
				StructureBlockInfo additionalInfo = this.blocks.remove(currentPos);
				if (additionalInfo == null) {
					if (canFail) this.fail(currentPos, level, entity, null, (int) propelCtx.chargesUsed);
					return;
				}
				projectileBlocks.add(additionalInfo);

				List<StructureBlockInfo> copy = ImmutableList.copyOf(projectileBlocks);
				for (ListIterator<StructureBlockInfo> projIter = projectileBlocks.listIterator(); projIter.hasNext(); ) {
					int j = projIter.nextIndex();
					StructureBlockInfo projInfo = projIter.next();
					if (projInfo.state().getBlock() instanceof ProjectileBlock<?> cproj1 && cproj1.isValidAddition(copy, projInfo, j, this.initialOrientation))
						continue;
					if (canFail) this.fail(currentPos, level, entity, null, (int) propelCtx.chargesUsed);
					return;
				}
				currentPos = currentPos.relative(this.initialOrientation);
			}
			assemblyPos = currentPos.immutable().relative(this.initialOrientation.getOpposite());
			if (projBlock.isComplete(projectileBlocks, this.initialOrientation)) {
				projectile = projBlock.getProjectile(level, projectileBlocks);
				propelCtx.chargesUsed += projectile.addedChargePower();
				if (propelCtx.chargesUsed <= 0 || canFail && propelCtx.chargesUsed < projectile.minimumChargePower()) {
					this.squibBlocks(assemblyPos, projectileBlocks);
					return;
				}
			} else if (canFail) {
				this.fail(currentPos, level, entity, null, (int) propelCtx.chargesUsed);
				return;
			}
		}
		if (!(propelCtx.munitionBlocks.isEmpty()) && propelCtx.propellantBlocks.isEmpty()) {
			for (Map.Entry<BlockPos, StructureBlockInfo> entry : munitionsPhysicsContraption.getBlocks().entrySet()) {
				if (munitionsPhysicsContraption.getBlocks().get(entry.getKey()).state().getBlock() instanceof MunitionsLauncherThrusterBlock thruster) {
					if (!(thruster.isComplete(munitionsPhysicsContraption.getBlocks()))) return;
					if (!(thruster.isValidAddition(munitionsPhysicsContraption.getBlocks(), entry.getValue()))) return;
				} else if (munitionsPhysicsContraption.getBlocks().get(entry.getKey()).state().getBlock() instanceof MunitionsLauncherGuidanceBlock guidance) {
					if (!(guidance.isComplete(munitionsPhysicsContraption.getBlocks()))) return;
					if (!(guidance.isValidAddition(munitionsPhysicsContraption.getBlocks(), entry.getValue()))) return;
					if (!(guidance.canFire(level, entry.getKey(), entry.getValue().state, munitionsPhysicsContraption.presentBlockEntities.get(entry.getKey()), entity, currentPos))) return;
				}
			}
		}


		if (!((hasThruster && propelCtx.propellantBlocks.isEmpty() && !propelCtx.munitionBlocks.isEmpty()) || (!propelCtx.propellantBlocks.isEmpty() && !hasThruster && propelCtx.munitionBlocks.isEmpty()))) return;

		Vec3 spawnPos = entity.toGlobalVector(Vec3.atCenterOf(currentPos.relative(this.initialOrientation)), 0);
		Vec3 vec = spawnPos.subtract(entity.toGlobalVector(Vec3.atCenterOf(BlockPos.ZERO), 0)).normalize();
		spawnPos = spawnPos.subtract(vec.scale(2));


		if (!propelCtx.munitionBlocks.isEmpty() && propelCtx.propellantBlocks.isEmpty() && hasThruster) {
			Direction facing = munitionsPhysicsContraption.initialOrientation();
			munitionsPhysicsContraption.anchor = startPos;
			munitionsPhysicsContraption.bounds = new AABB(BlockPos.ZERO);
			munitionsPhysicsContraption.bounds = this.bounds.inflate(Math.ceil(Math.sqrt(getRadius(this.getBlocks().keySet(), Direction.Axis.Y))));

			MunitionsPhysicsContraptionEntity mpce = MunitionsPhysicsContraptionEntity.create(level, munitionsPhysicsContraption, this.initialOrientation());

			mpce.setPos(spawnPos.x(), spawnPos.y() - 0.5, spawnPos.z);
			mpce.shoot(vec.x, vec.y, vec.z,  properties.initialSpeed(), propelCtx.spread);
			mpce.xRotO = mpce.getXRot();
			mpce.yRotO = mpce.getYRot();
			level.addFreshEntity(mpce);


			mpce.addUntouchableEntity(entity, 1);
			Entity vehicle = entity.getVehicle();
			if (vehicle != null && CBCEntityTypes.CANNON_CARRIAGE.is(vehicle))
				mpce.addUntouchableEntity(vehicle, 1);

			this.hasFired = true;

			float soundPower = Mth.clamp(propelCtx.munitionBlocks.size() / 16f, 0, 1);
			SoundEvent event = AllSoundEvents.FWOOMP.getMainEvent();
			float tone = 2 + soundPower * -8 + level.random.nextFloat() * 4f - 2f;
			float pitch = (float) Mth.clamp(Math.pow(2, tone / 12f), 0, 2);
			double shakeDistance = propelCtx.munitionBlocks.size() * CBCConfigs.server().cannons.bigCannonBlastDistanceMultiplier.getF();
			float volume = 10 + soundPower * 30;
			if (properties.isGunLauncher()) {
				event = CBCSoundEvents.FIRE_DROP_MORTAR.getMainEvent();
				soundPower = Mth.clamp(propelCtx.chargesUsed / 16f, 0, 1);
				tone = 2 + soundPower * -8 + level.random.nextFloat() * 4f - 2f;
				pitch = (float) Mth.clamp(Math.pow(2, tone / 12f), 0, 2);
				shakeDistance = propelCtx.chargesUsed * CBCConfigs.server().cannons.bigCannonBlastDistanceMultiplier.getF();
				volume = 10 + soundPower * 30;
			}
			Vec3 plumePos = spawnPos.subtract(vec);


			BigCannonPlumeParticleData plumeParticle = new BigCannonPlumeParticleData(propelCtx.smokeScale, propelCtx.chargesUsed, 10);
			CannonBlastWaveEffectParticleData blastEffect = new CannonBlastWaveEffectParticleData(shakeDistance,
				BuiltInRegistries.SOUND_EVENT.wrapAsHolder(event), SoundSource.BLOCKS, volume, pitch, 2, propelCtx.chargesUsed);
			Packet<?> blastWavePacket = new ClientboundLevelParticlesPacket(blastEffect, true, plumePos.x, plumePos.y, plumePos.z, 0, 0, 0, 1, 0);

			double blastDistSqr = volume * volume * 256 * 1.21;
			for (ServerPlayer player : level.players()) {
				level.sendParticles(player, plumeParticle, true, plumePos.x, plumePos.y, plumePos.z, 0, vec.x, vec.y, vec.z, 1.0f);
				if (player.distanceToSqr(plumePos.x, plumePos.y, plumePos.z) < blastDistSqr)
					player.connection.send(blastWavePacket);
			}


			if (CBCConfigs.server().munitions.projectilesCanChunkload.get()) {
				ChunkPos cpos1 = new ChunkPos(BlockPos.containing(mpce.position()));
				RitchiesProjectileLib.queueForceLoad(level, cpos1.x, cpos1.z);
			}
		} else if (propelCtx.munitionBlocks.isEmpty() && !propelCtx.propellantBlocks.isEmpty() && !hasThruster) {

			if (propelCtx.chargesUsed < minimumSpread) propelCtx.chargesUsed = minimumSpread;

			float recoilMagnitude = 0;

			if (projectile != null) {
				if (projectile instanceof IntegratedPropellantProjectile integPropel && !projectileBlocks.isEmpty()) {
					if (!propelCtx.addIntegratedPropellant(integPropel, projectileBlocks.get(0), this.initialOrientation) && canFail) {
						this.fail(currentPos, level, entity, null, (int) propelCtx.chargesUsed);
						return;
					}
				}
				StructureBlockInfo muzzleInfo = this.blocks.get(currentPos);
				if (canFail && muzzleInfo != null && !muzzleInfo.state().isAir()) {
					this.fail(currentPos, level, entity, null, (int) propelCtx.chargesUsed);
					return;
				}
				projectile.setPos(spawnPos);
				projectile.setChargePower(propelCtx.chargesUsed);
				projectile.shoot(vec.x, vec.y, vec.z, propelCtx.chargesUsed, propelCtx.spread);
				projectile.xRotO = projectile.getXRot();
				projectile.yRotO = projectile.getYRot();


				projectile.addUntouchableEntity(entity, 1);
				Entity vehicle = entity.getVehicle();
				if (vehicle != null && CBCEntityTypes.CANNON_CARRIAGE.is(vehicle))
					projectile.addUntouchableEntity(vehicle, 1);



				level.addFreshEntity(projectile);
				recoilMagnitude += projectile.addedRecoil();
			}

			recoilMagnitude += propelCtx.recoil;
			recoilMagnitude *= CBCConfigs.server().cannons.bigCannonRecoilScale.getF();
			if (controller != null) controller.onRecoil(vec.scale(-recoilMagnitude), entity);

			this.hasFired = true;

			float soundPower = Mth.clamp(propelCtx.chargesUsed / 16f, 0, 1);
			float tone = 2 + soundPower * -8 + level.random.nextFloat() * 4f - 2f;
			float pitch = (float) Mth.clamp(Math.pow(2, tone / 12f), 0, 2);
			double shakeDistance = propelCtx.chargesUsed * CBCConfigs.server().cannons.bigCannonBlastDistanceMultiplier.getF();
			float volume = 10 + soundPower * 30;
			Vec3 plumePos = spawnPos.subtract(vec);
			propelCtx.smokeScale = Math.max(1, propelCtx.smokeScale);

			BigCannonPlumeParticleData plumeParticle = new BigCannonPlumeParticleData(propelCtx.smokeScale, propelCtx.chargesUsed, 10);
			CannonBlastWaveEffectParticleData blastEffect = new CannonBlastWaveEffectParticleData(shakeDistance,
				BuiltInRegistries.SOUND_EVENT.wrapAsHolder(CBCSoundEvents.FIRE_BIG_CANNON.getMainEvent()), SoundSource.BLOCKS, volume, pitch, 2, propelCtx.chargesUsed);
			Packet<?> blastWavePacket = new ClientboundLevelParticlesPacket(blastEffect, true, plumePos.x, plumePos.y, plumePos.z, 0, 0, 0, 1, 0);

			double blastDistSqr = volume * volume * 256 * 1.21;
			for (ServerPlayer player : level.players()) {
				level.sendParticles(player, plumeParticle, true, plumePos.x, plumePos.y, plumePos.z, 0, vec.x, vec.y, vec.z, 1.0f);
				if (player.distanceToSqr(plumePos.x, plumePos.y, plumePos.z) < blastDistSqr)
					player.connection.send(blastWavePacket);
			}

			if (projectile != null && CBCConfigs.server().munitions.projectilesCanChunkload.get()) {
				ChunkPos cpos1 = new ChunkPos(new BlockPos(projectile.blockPosition()));
				RitchiesProjectileLib.queueForceLoad(level, cpos1.x, cpos1.z);
			}

		}
		for (BlockPos c : changed) {
			if (!(this.presentBlockEntities.get(c) instanceof IMunitionsLauncherBlockEntity cbe)) return;
			StructureBlockInfo containedBlockInfo = cbe.cannonBehavior().block();
			if (containedBlockInfo.state().getBlock() instanceof BigCartridgeBlock) {
				StructureBlockInfo oldData = cbe.cannonBehavior().block();
				cbe.cannonBehavior().loadBlock(new StructureBlockInfo(oldData.pos(), oldData.state().setValue(FILLED, false), new CompoundTag()));
				BigCannonBlock.writeAndSyncSingleBlockData(this.presentBlockEntities.get(c), this.blocks.get(c), entity, this);
			} else {
				cbe.cannonBehavior().removeBlock();
				BigCannonBlock.writeAndSyncSingleBlockData(this.presentBlockEntities.get(c), this.blocks.get(c), entity, this);
			}
		}
	}

	@Override
	public Vec3 getInteractionVec(PitchOrientedContraptionEntity poce) {
		return poce.toGlobalVector(Vec3.atCenterOf(this.startPos.relative(this.initialOrientation.getOpposite())), 0);
	}

	private static boolean rollSquib(RandomSource random) {
		float f = CBCConfigs.server().failure.squibChance.getF();
		return f != 0 && random.nextFloat() <= f;
	}


	@Override
	public ICannonContraptionType getCannonType() {
		return CBCMWCannonContraptionTypes.MOUNTED_LAUNCHER;
	}

	public int getMaxSafeCharges() {
		MunitionsLauncherMaterialProperties properties = this.cannonMaterial.properties();
		StructureBlockInfo breech = this.blocks.get(this.startPos.relative(this.initialOrientation.getOpposite()));
		if (breech == null) return 0;
		int materialStrength = properties.maxSafePropellantStress();
		if (this.hasWeldedPenalty) materialStrength -= properties.weldStressPenalty();
		return Math.max(materialStrength, 0);
	}

	private void squibBlocks(BlockPos currentPos, List<StructureBlockInfo> projectileBlocks) {
		for (int i = 0; i < projectileBlocks.size(); ++i) {
			BlockPos pos = currentPos.relative(this.initialOrientation, i);
			StructureBlockInfo cannonInfo1 = this.blocks.get(pos);
			BlockEntity be1 = this.presentBlockEntities.get(pos);
			StructureBlockInfo projBlock = projectileBlocks.get(i);

			if (cannonInfo1 != null && be1 instanceof IMunitionsLauncherBlockEntity cbe1) {
				MunitionsLauncherBehavior behavior1 = cbe1.cannonBehavior();
				behavior1.loadBlock(projBlock);
				CompoundTag tag = behavior1.blockEntity.saveWithFullMetadata();
				tag.remove("x");
				tag.remove("y");
				tag.remove("z");
				StructureBlockInfo squibInfo = new StructureBlockInfo(cannonInfo1.pos(), cannonInfo1.state(), tag);
				this.blocks.put(cannonInfo1.pos(), squibInfo);
			} else {
				CompoundTag tag = projBlock.nbt();
				if (tag != null) {
					tag.remove("x");
					tag.remove("y");
					tag.remove("z");
				}
				this.blocks.put(pos, new StructureBlockInfo(pos, projBlock.state(), tag));
			}
		}
	}

	private static boolean rollBarrelBurst(RandomSource random) {
		float f = CBCConfigs.server().failure.barrelChargeBurstChance.getF();
		return f != 0 && random.nextFloat() <= f;
	}

	private static boolean rollOverloadBurst(RandomSource random) {
		float f = CBCConfigs.server().failure.overloadBurstChance.getF();
		return f != 0 && random.nextFloat() <= f;
	}

	private static boolean rollFailToIgnite(RandomSource random) {
		float f = CBCConfigs.server().failure.interruptedIgnitionChance.getF();
		return f != 0 && random.nextFloat() <= f;
	}

	@Override
	public CompoundTag writeNBT(boolean clientData) {
		CompoundTag tag = super.writeNBT(clientData);
		tag.putString("CannonMaterial", this.cannonMaterial == null ? CBCModernWarfareMunitionsLauncherMaterials.ROCKET_LAUNCHER.name().toString() : this.cannonMaterial.name().toString());
		if (this.hasWeldedPenalty) tag.putBoolean("WeldedCannon", true);
		return tag;
	}

	@Override
	public void readNBT(Level level, CompoundTag tag, boolean clientData) {
		super.readNBT(level, tag, clientData);
		this.cannonMaterial = MunitionsLauncherMaterial.fromNameOrNull(CBCUtils.location(tag.getString("CannonMaterial")));
		this.hasWeldedPenalty = tag.contains("WeldedCannon");
		if (this.cannonMaterial == null) this.cannonMaterial = CBCModernWarfareMunitionsLauncherMaterials.ROCKET_LAUNCHER;
	}


	@Override
	public ContraptionType getType() {
		return CBCModernWarfareContraptionTypes.MOUNTED_LAUNCHER.value();
	}

	protected static class PropellantContext {
		public float chargesUsed = 0;
		public float recoil = 0;
		public float stress = 0;
		public float smokeScale = 0;
		public int barrelTravelled = 0;
		public float spread = 0.0f;
		public List<StructureBlockInfo> propellantBlocks = new ArrayList<>();

		public  List<StructureBlockInfo> munitionBlocks =  new ArrayList<>();

		public boolean addPropellant(BigCannonPropellantBlock propellant, StructureBlockInfo info, Direction initialOrientation) {
			this.propellantBlocks.add(info);
			if (!safeLoad(ImmutableList.copyOf(this.propellantBlocks), initialOrientation)) return false;
			float power = Math.max(0, propellant.getChargePower(info));
			this.chargesUsed += power;
			this.smokeScale += power;
			this.recoil = Math.max(0, propellant.getRecoil(info));
			this.stress += propellant.getStressOnCannon(info);
			this.spread += propellant.getSpread(info);
			return true;
		}

		public boolean addFuelTank(MunitionsLauncherFuelTankBlock fuel, StructureBlockInfo info, Direction initialOrientation, BlockPos pos) {
			this.munitionBlocks.add(info);
			this.spread += fuel.getProperties().fuelTankProperties().addedSpread();
			return true;
		}


		public boolean addThruster(MunitionsLauncherThrusterBlock thruster, StructureBlockInfo info, Direction initialOrientation, BlockPos pos) {
			this.munitionBlocks.add(info);
			this.spread += thruster.addedSpread();
			return true;
		}

		public boolean addGuidance(MunitionsLauncherGuidanceBlock guidance, StructureBlockInfo info, Direction initialOrientation, BlockPos pos) {
			this.munitionBlocks.add(info);
			this.spread += guidance.addedSpread();
			return true;
		}

		public boolean addIntegratedPropellant(IntegratedPropellantProjectile propellant, StructureBlockInfo firstInfo, Direction initialOrientation) {
			List<StructureBlockInfo> copy = ImmutableList.<StructureBlockInfo>builder().addAll(this.propellantBlocks).add(firstInfo).build();
			if (!safeLoad(copy, initialOrientation)) return false;
			float power = Math.max(0, propellant.getChargePower());
			this.chargesUsed += power;
			this.smokeScale += power;
			this.stress += propellant.getStressOnCannon();
			this.spread += propellant.getSpread();
			return true;
		}

		public static boolean safeLoad(List<StructureBlockInfo> propellant, Direction orientation) {
			Map<Block, Integer> allowedCounts = new HashMap<>();
			Map<Block, Integer> actualCounts = new HashMap<>();
			for (ListIterator<StructureBlockInfo> iter = propellant.listIterator(); iter.hasNext(); ) {
				int index = iter.nextIndex();
				StructureBlockInfo info = iter.next();

				Block block = info.state().getBlock();
				if (!(block instanceof BigCannonPropellantBlock cpropel) || !(cpropel.isValidAddition(info, index, orientation))) return false;
				if (actualCounts.containsKey(block)) {
					actualCounts.put(block, actualCounts.get(block) + 1);
				} else {
					actualCounts.put(block, 1);
				}
				BigCannonPropellantCompatibilities compatibilities = BigCannonPropellantCompatibilityHandler.getCompatibilities(block);
				for (Map.Entry<Block, Integer> entry : compatibilities.validPropellantCounts().entrySet()) {
					Block block1 = entry.getKey();
					int oldCount = allowedCounts.getOrDefault(block1, -1);
					int newCount = entry.getValue();
					if (newCount >= 0 && (oldCount < 0 || newCount < oldCount)) allowedCounts.put(block1, newCount);
				}
			}
			for (Map.Entry<Block, Integer> entry : actualCounts.entrySet()) {
				Block block = entry.getKey();
				if (allowedCounts.containsKey(block) && allowedCounts.get(block) < entry.getValue()) return false;
			}
			return true;
		}
	}

}
