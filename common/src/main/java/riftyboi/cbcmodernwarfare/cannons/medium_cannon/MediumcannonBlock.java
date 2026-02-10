package riftyboi.cbcmodernwarfare.cannons.medium_cannon;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import rbasamoyai.createbigcannons.cannons.InteractableCannonBlock;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.Vec3;
import com.simibubi.create.content.contraptions.Contraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannons.CannonContraptionProviderBlock;
import rbasamoyai.createbigcannons.cannons.ItemCannonBehavior;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBehavior;
import rbasamoyai.createbigcannons.cannons.big_cannons.IBigCannonBlockEntity;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import rbasamoyai.createbigcannons.crafting.welding.WeldableBlock;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedMediumcannonContraption;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.material.MediumcannonMaterial;

import javax.annotation.Nonnull;

public interface MediumcannonBlock extends WeldableBlock, CannonContraptionProviderBlock, InteractableCannonBlock {

	MediumcannonMaterial getMediumcannonMaterial();
	default MediumcannonMaterial getMediumcannonMaterialInLevel(LevelAccessor level, BlockState state, BlockPos pos) { return this.getMediumcannonMaterial(); }

	boolean isBreechMechanism(BlockState state);

	default void onRemoveCannon(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!(state.getBlock() instanceof MediumcannonBlock cBlock) || state.is(newState.getBlock())) return;
		Direction facing = cBlock.getFacing(state);
		Direction opposite = facing.getOpposite();
		MediumcannonMaterial material = cBlock.getMediumcannonMaterial();

		if (level.getBlockEntity(pos) instanceof IMediumcannonBlockEntity cbe) {
			for (ItemStack stack : cbe.getDrops()) {
				Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack.copy());
			}
		}

		if (this.canConnectToSide(state, facing)) {
			BlockPos pos1 = pos.relative(facing);
			BlockState state1 = level.getBlockState(pos1);
			BlockEntity be1 = level.getBlockEntity(pos1);

			if (state1.getBlock() instanceof MediumcannonBlock cBlock1
				&& cBlock1.getMediumcannonMaterialInLevel(level, state1, pos1) == material
				&& cBlock1.canConnectToSide(state1, opposite)
				&& be1 instanceof IMediumcannonBlockEntity cbe1) {
				cbe1.cannonBehavior().setConnectedFace(opposite, false);
				cbe1.cannonBehavior().setWelded(opposite, false);
				cbe1.cannonBehavior().blockEntity.setChanged();
			}
		}

		if (this.canConnectToSide(state, opposite)) {
			BlockPos pos2 = pos.relative(opposite);
			BlockState state2 = level.getBlockState(pos2);
			BlockEntity be2 = level.getBlockEntity(pos2);

			if (state2.getBlock() instanceof MediumcannonBlock cBlock2
				&& cBlock2.getMediumcannonMaterialInLevel(level, state2, pos2) == material
				&& cBlock2.canConnectToSide(state2, facing)
				&& be2 instanceof IMediumcannonBlockEntity cbe2) {
				cbe2.cannonBehavior().setConnectedFace(facing, false);
				cbe2.cannonBehavior().setWelded(facing, false);
				cbe2.cannonBehavior().blockEntity.setChanged();
			}
		}
	}

	static void onPlace(Level level, BlockPos pos) {
		if (!(level instanceof ServerLevel slevel)) return;
		BlockState state = level.getBlockState(pos);
		if (!(state.getBlock() instanceof MediumcannonBlock cBlock)) return;

		BlockEntity be = level.getBlockEntity(pos);
		if (!(be instanceof IMediumcannonBlockEntity cbe)) return;

		Direction facing = cBlock.getFacing(state);
		Direction opposite = facing.getOpposite();

		Vec3 center = Vec3.atCenterOf(pos);
		Vec3 offset = Vec3.atBottomCenterOf(facing.getNormal()).scale(0.5d);
		MediumcannonMaterial material = cBlock.getMediumcannonMaterialInLevel(level, state, pos);

		BlockPos pos1 = pos.relative(facing);
		BlockState state1 = level.getBlockState(pos1);
		BlockEntity be1 = level.getBlockEntity(pos1);

		if (cBlock.canConnectToSide(state, facing)
			&& state1.getBlock() instanceof MediumcannonBlock cBlock1
			&& cBlock1.getMediumcannonMaterialInLevel(level, state1, pos1) == material
			&& cBlock1.canConnectToSide(state1, opposite)) {
			if (state1.hasProperty(MediumcannonBarrelBlock.BARREL_END)) {
				level.setBlock(pos1, state1.setValue(MediumcannonBarrelBlock.BARREL_END, MediumcannonBarrelBlock.MediumcannonBarrelEnd.NOTHING), 3);
			}
			if (level.getBlockEntity(pos1) instanceof IMediumcannonBlockEntity cbe1) {
				cbe.cannonBehavior().setConnectedFace(facing, true);
				cbe1.cannonBehavior().setConnectedFace(facing.getOpposite(), true);
				cbe1.cannonBehavior().blockEntity.setChanged();
				Vec3 particlePos = center.add(offset);
				slevel.sendParticles(ParticleTypes.CRIT, particlePos.x, particlePos.y, particlePos.z, 10, 0.5d, 0.5d, 0.5d, 0.1d);
			}
		}

		BlockPos pos2 = pos.relative(opposite);
		BlockState state2 = level.getBlockState(pos2);
		BlockEntity be2 = level.getBlockEntity(pos2);

		if (cBlock.canConnectToSide(state, opposite)
			&& state2.getBlock() instanceof MediumcannonBlock cBlock2
			&& cBlock2.getMediumcannonMaterialInLevel(level, state2, pos2) == material
			&& cBlock2.canConnectToSide(state2, facing)) {
			if (state2.hasProperty(MediumcannonBarrelBlock.BARREL_END)) {
				level.setBlock(pos2, state2.setValue(MediumcannonBarrelBlock.BARREL_END, MediumcannonBarrelBlock.MediumcannonBarrelEnd.NOTHING), 3);
			}
			if (level.getBlockEntity(pos2) instanceof IMediumcannonBlockEntity cbe2) {
				cbe.cannonBehavior().setConnectedFace(facing.getOpposite(), true);
				cbe2.cannonBehavior().setConnectedFace(facing, true);
				cbe2.cannonBehavior().blockEntity.setChanged();
				Vec3 particlePos = center.add(offset.reverse());
				slevel.sendParticles(ParticleTypes.CRIT, particlePos.x, particlePos.y, particlePos.z, 10, 0.5d, 0.5d, 0.5d, 0.1d);
			}
		}

		be.setChanged();
	}

	@Override
	default boolean onInteractWhileAssembled(Player player, BlockPos localPos, Direction side, InteractionHand interactionHand,
											 Level level, AbstractMountedCannonContraption contraption, BlockEntity be, StructureBlockInfo info,
											 PitchOrientedContraptionEntity entity) {
		return false;
	}

	@Override default boolean isWeldable(BlockState state) { return this.getMediumcannonMaterial().properties().isWeldable(); }
	@Override default int weldDamage() { return this.getMediumcannonMaterial().properties().weldDamage(); }

	@Override
	default boolean canWeldSide(Level level, Direction dir, BlockState state, BlockState otherState, BlockPos pos) {
		return otherState.getBlock() instanceof MediumcannonBlock cblock
			&& cblock.getMediumcannonMaterial() == this.getMediumcannonMaterial()
			&& this.isWeldable(state)
			&& cblock.isWeldable(otherState)
			&& this.canConnectToSide(state, dir)
			&& cblock.canConnectToSide(otherState, dir.getOpposite())
			&& level.getBlockEntity(pos) instanceof IMediumcannonBlockEntity cbe
			&& level.getBlockEntity(pos.relative(dir)) instanceof IMediumcannonBlockEntity cbe1
			&& (!cbe.cannonBehavior().isConnectedTo(dir) || !cbe1.cannonBehavior().isConnectedTo(dir.getOpposite()));
	}

	@Override
	default void weldBlock(Level level, BlockState state, BlockPos pos, Direction dir) {
		if (!(level.getBlockEntity(pos) instanceof IMediumcannonBlockEntity cbe)) return;
		ItemCannonBehavior behavior = cbe.cannonBehavior();
		behavior.setConnectedFace(dir, true);
		behavior.setWelded(dir, true);
		behavior.blockEntity.notifyUpdate();
	}

	@Nonnull
	@Override
	default AbstractMountedCannonContraption getCannonContraption() {
		return new MountedMediumcannonContraption();
	}

}
