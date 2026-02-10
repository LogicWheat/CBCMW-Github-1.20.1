package riftyboi.cbcmodernwarfare.cannons.rotarycannon.breech;

import static rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBlock.writeAndSyncSingleBlockData;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import rbasamoyai.createbigcannons.index.CBCSoundEvents;
import rbasamoyai.createbigcannons.munitions.autocannon.ammo_container.AutocannonAmmoContainerItem;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBaseBlock;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.material.RotarycannonMaterial;
import riftyboi.cbcmodernwarfare.crafting.casting.CBCModernWarfareCastShapes;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlockEntities;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareShapes;

public class RotarycannonBreechBlock extends RotarycannonBaseBlock implements IBE<AbstractRotarycannonBreechBlockEntity>, IWrenchable {

	public RotarycannonBreechBlock(Properties properties, RotarycannonMaterial material) {
		super(properties, material);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
	}

	@Override
	public Class<AbstractRotarycannonBreechBlockEntity> getBlockEntityClass() {
		return AbstractRotarycannonBreechBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends AbstractRotarycannonBreechBlockEntity> getBlockEntityType() {
		return CBCModernWarfareBlockEntities.ROTARYCANNON_BREECH.get();
	}

	@Override
	public CannonCastShape getCannonShape() {
		return CBCModernWarfareCastShapes.ROTARYCANNON_BREECH;
	}

	@Override public boolean canConnectToSide(BlockState state, Direction face) { return this.getFacing(state) == face; }

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection());
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
	public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
		return InteractionResult.PASS;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getItemInHand(hand);
		if (level.getBlockEntity(pos) instanceof AbstractRotarycannonBreechBlockEntity breech) {

			ItemStack container = breech.getMagazine();
			boolean changed = false;
			boolean tryAdd = false;
			if (!container.isEmpty()) {
				if (!level.isClientSide) {
					tryAdd = true;
					breech.setMagazine(ItemStack.EMPTY);
				}
				changed = true;
			}
			if (stack.getItem() instanceof AutocannonAmmoContainerItem) {
				if (!level.isClientSide) {
					breech.setMagazine(stack);
					player.setItemInHand(hand, ItemStack.EMPTY);
					CBCSoundEvents.PLACE_AUTOCANNON_AMMO_CONTAINER.playOnServer(level, pos);
				}
				changed = true;
			}

			if (tryAdd && !player.addItem(container)) {
				Vec3 spawnLoc = Vec3.atCenterOf(pos);
				ItemEntity dropEntity = new ItemEntity(level, spawnLoc.x, spawnLoc.y, spawnLoc.z, container);
				level.addFreshEntity(dropEntity);
			}
			if (changed) {
				breech.notifyUpdate();
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
		}
		return super.use(state, level, pos, player, hand, result);
	}

	@Override
	public boolean onInteractWhileAssembled(Player player, BlockPos localPos, Direction side, InteractionHand interactionHand,
											Level level, AbstractMountedCannonContraption contraption, BlockEntity be, StructureBlockInfo info,
											PitchOrientedContraptionEntity entity) {
		if (!(be instanceof AbstractRotarycannonBreechBlockEntity breech)) return false;

		ItemStack stack = player.getItemInHand(interactionHand);
		ItemStack container = breech.getMagazine();
		Vec3 globalPos = entity.toGlobalVector(Vec3.atCenterOf(localPos), 0);

		boolean insertingContainer = stack.getItem() instanceof AutocannonAmmoContainerItem;
		boolean canRemove = insertingContainer || stack.isEmpty() && (AutocannonAmmoContainerItem.getTotalAmmoCount(container) == 0 || player.isShiftKeyDown());

		boolean changed = false;
		boolean tryAdd = false;
		if (!container.isEmpty() && canRemove) {
			if (!level.isClientSide) {
				tryAdd = true;
				breech.setMagazine(ItemStack.EMPTY);
			}
			changed = true;
		}
		if (breech.getMagazine().isEmpty() && insertingContainer) {
			if (!level.isClientSide) {
				breech.setMagazine(stack);
				player.setItemInHand(interactionHand, ItemStack.EMPTY);
				CBCSoundEvents.PLACE_AUTOCANNON_AMMO_CONTAINER.playOnServer(level, localPos);
			}
			changed = true;
		}

		if (tryAdd && !player.addItem(container)) {
			ItemEntity dropEntity = new ItemEntity(level, globalPos.x, globalPos.y, globalPos.z, container);
			level.addFreshEntity(dropEntity);
		}
		if (changed && !level.isClientSide) writeAndSyncSingleBlockData(be, info, entity, contraption);
		return changed;
	}

}
