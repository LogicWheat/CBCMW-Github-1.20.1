package riftyboi.cbcmodernwarfare.cannons.medium_cannon.breech;

import com.simibubi.create.content.contraptions.Contraption;

import com.simibubi.create.api.contraption.transformable.TransformableBlock;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.quickfiring_breech.QuickfiringBreechBlock;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.effects.particles.smoke.QuickFiringBreechSmokeParticleData;
import rbasamoyai.createbigcannons.index.CBCBlocks;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.IMediumcannonBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBaseBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedMediumcannonContraption;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBlock;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MovesWithMediumcannonRecoilBarrel;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.material.MediumcannonMaterial;
import riftyboi.cbcmodernwarfare.crafting.casting.CBCModernWarfareCastShapes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareShapes;
import riftyboi.cbcmodernwarfare.munitions.medium_cannon.MediumcannonAmmoItem;

public class MediumcannonBreechBlock extends MediumcannonBaseBlock implements IBE<MediumcannonBreechBlockEntity>, TransformableBlock, IWrenchable, MovesWithMediumcannonRecoilBarrel {

	public static final BooleanProperty AXIS = DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE;

	public static final BooleanProperty ASSEMBLED = BooleanProperty.create("assembled");
	public MediumcannonBreechBlock(Properties properties, MediumcannonMaterial material) {
		super(properties, material);
		this.registerDefaultState(this.defaultBlockState().setValue(ASSEMBLED, false));
	}

	@Override
	public Class<MediumcannonBreechBlockEntity> getBlockEntityClass() {
		return MediumcannonBreechBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends MediumcannonBreechBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.MEDIUMCANNON_BREECH.get();
	}

	@Override public BlockState getMovingState(BlockState original) { return original.setValue(ASSEMBLED, false); }
	@Override public BlockState getStationaryState(BlockState original) { return original.setValue(ASSEMBLED, true); }

	@Override
	public CannonCastShape getCannonShape() {
		return CBCModernWarfareCastShapes.MEDIUMCANNON_BREECH;
	}

	@Override public boolean canConnectToSide(BlockState state, Direction face) { return this.getFacing(state) == face; }

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AXIS).add(ASSEMBLED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getNearestLookingDirection()).setValue(AXIS, context.getNearestLookingDirection().getAxis() == Direction.Axis.Z);
	}

	@Override
	public Direction getFacing(BlockState state) {
		return state.getValue(FACING);
	}


	@Override
	public boolean onInteractWhileAssembled(Player player, BlockPos localPos, Direction side, InteractionHand interactionHand,
											Level level, AbstractMountedCannonContraption contraption, BlockEntity be,
											StructureTemplate.StructureBlockInfo info, PitchOrientedContraptionEntity entity) {
		if (!(contraption instanceof MountedMediumcannonContraption cannon)
			|| !(be instanceof MediumcannonBreechBlockEntity breech)
			|| ((MediumcannonBlock) info.state().getBlock()).getFacing(info.state()).getAxis() != side.getAxis()
			|| breech.cannonBehavior().isConnectedTo(side)) return false;
		ItemStack stack = player.getItemInHand(interactionHand);


		if (stack.isEmpty()) {
			if (level instanceof ServerLevel slevel) {
				if (!breech.onInteractionCooldown()) {
					SoundEvent sound = breech.getOpenProgress() == 0 ? SoundEvents.IRON_TRAPDOOR_OPEN : SoundEvents.IRON_TRAPDOOR_CLOSE;
					level.playSound(null, player.blockPosition(), sound, SoundSource.BLOCKS, 1.0f, 1.5f);
				}
				breech.toggleOpening();

				if (breech.getOpenDirection() > 0) {
					BlockEntity be1 = contraption.presentBlockEntities.get(localPos);
					if (be1 instanceof IMediumcannonBlockEntity cbe1) {
						ItemStack extract = breech.getInputBuffer();
						Vec3 normal = new Vec3(side.step());
						Vec3 dir = contraption.entity.applyRotation(normal, 0);
						if (!extract.isEmpty()) {
							Vec3 ejectPos = Vec3.atCenterOf(localPos).add(normal.scale(1.1));
							Vec3 globalPos = entity.toGlobalVector(ejectPos, 0);
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
							breech.clearInputBuffer();
						}
						if (cannon.hasFired) {
							Vec3 smokePos = Vec3.atCenterOf(localPos).add(normal.scale(0.6));
							Vec3 globalPos = entity.toGlobalVector(smokePos, 0);
							Vec3 vel = dir.scale(0.075);
							slevel.sendParticles(new QuickFiringBreechSmokeParticleData(), globalPos.x, globalPos.y, globalPos.z, 0, vel.x, vel.y, vel.z, 1);
							cannon.hasFired = false;
						}
					}
				}

			}
			return true;
		}
		if (!breech.isOpen() || breech.onInteractionCooldown()) return false;

		if (stack.getItem() instanceof MediumcannonAmmoItem munition) {
			BlockEntity be1 = contraption.presentBlockEntities.get(localPos);
			if (!(be1 instanceof IMediumcannonBlockEntity cbe1)) return false;
			if (!level.isClientSide) {
				if (breech.isInputFull()) {
					return false;
				}
				ItemStack load = stack.copy();
				load.setCount(1);
				breech.setInputBuffer(load);
				SoundType sound = CBCBlocks.BIG_CARTRIDGE.getDefaultState().getSoundType();
				level.playSound(null, player.blockPosition(), sound.getPlaceSound(), SoundSource.BLOCKS, sound.getVolume(), sound.getPitch());
				if (!player.isCreative()) stack.shrink(1);
			}
			return true;
		}

		return false;
	}

	@Override
	public boolean isBreechMechanism(BlockState state) {
		return true;
	}

	@Override
	public boolean isComplete(BlockState state) {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return CBCModernWarfareShapes.BREECH.get(this.getFacing(state).getAxis());
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

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		if (rot.ordinal() % 2 == 1) state = state.cycle(AXIS);
		return super.rotate(state, rot);
	}

}
