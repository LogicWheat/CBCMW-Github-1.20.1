package riftyboi.cbcmodernwarfare.content.smoke_discharger;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.crank.ValveHandleBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBoard;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsFormatter;
import com.simibubi.create.foundation.utility.CreateLang;

import net.createmod.catnip.math.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.CreateBigCannons;
import riftyboi.cbcmodernwarfare.munitions.smoke_discharger.SmokeDischargerRoundItem;


public class SmokeDischargerBlockEntity extends SmartBlockEntity implements Container {

	private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);

	private SmokeDischargerScrollValueBehavior pitchSlot;
	private SmokeDischargerScrollValueBehavior yawSlot;

	public SmokeDischargerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
		this.setLazyTickRate(3);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		behaviours.add(this.pitchSlot = new SmokeDischargerScrollValueBehavior(this, true));
		behaviours.add(this.yawSlot = new SmokeDischargerScrollValueBehavior(this, false));
	}


	public SmokeDischargerScrollValueBehavior getYawSlot() {
		return yawSlot;
	}
	public SmokeDischargerScrollValueBehavior getPitchSlot() {
		return pitchSlot;
	}


	@Override
	public void tick() {
		super.tick();
	}


	public void onRedstoneUpdate(boolean powered, boolean prevPowered) {
		if (powered != prevPowered) {
			this.level.setBlock(this.worldPosition, this.getBlockState().setValue(SmokeDischargerBlock.POWERED, powered), 3);
		}
	}

	@Override
	protected void write(CompoundTag tag, boolean clientPacket) {
		super.write(tag, clientPacket);
		for (int i = 0; i < inventory.size(); i++) {
			if (!inventory.get(i).isEmpty()) {
				tag.put("Item" + i, inventory.get(i).save(new CompoundTag()));
			}
		}
	}

	@Override
	protected void read(CompoundTag tag, boolean clientPacket) {
		super.read(tag, clientPacket);
		for (int i = 0; i < inventory.size(); i++) {
			if (tag.contains("Item" + i)) {
				inventory.set(i, ItemStack.of(tag.getCompound("Item" + i)));
			}
		}
	}

	// Methods to manage inventory
	@Override
	public int getContainerSize() {
		return inventory.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : inventory) {
			if (!stack.isEmpty()) return false;
		}
		return true;
	}

	public boolean removeOneItem(Player player) {
		// Iterate through each slot
		for (int slot = 0; slot < getContainerSize(); slot++) {
			ItemStack stack = getItem(slot);

			// Check if the slot contains an item
			if (!stack.isEmpty()) {
				// Attempt to give the item to the player
				if (!player.addItem(stack.copy())) {
					// If the player's inventory is full, drop the item at the block's position
					player.drop(stack.copy(), false);
				}

				// Remove the item from the container slot
				setItem(slot, ItemStack.EMPTY);
				this.setChanged();
				return true;
			}
		}
		return false;
	}

	public boolean handleItemInteraction(Player player, InteractionHand hand) {
		ItemStack heldItem = player.getItemInHand(hand);
		// Delegate the item handling to the BlockEntity
		if (!heldItem.isEmpty() && heldItem.getItem() instanceof SmokeDischargerRoundItem && this.insertItem(player, heldItem)) {
			if (!player.isCreative()) {
				heldItem.shrink(1);
			}
			this.setChanged();
			return true;
		}
		return false;
	}

	// Method to drop items when the block is broken
	public void dropItems(Level level, BlockPos pos) {
		for (ItemStack itemStack : inventory) {
			if (!itemStack.isEmpty()) {
				// Create an entity from the item stack and drop it in the world
				ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack);
				level.addFreshEntity(itemEntity);
				itemStack.setCount(0);
			}
		}
	}

	public boolean insertItem(Player player, ItemStack stack) {
		for (int slot = 0; slot < getContainerSize(); slot++) {
			ItemStack existingStack = getItem(slot);
			ItemStack copy = stack.copy();
			copy.setCount(1);
			if (existingStack.isEmpty()) {
				setItem(slot, copy);
				return true;
			}
		}
		return false;
	}


	@Override
	public ItemStack getItem(int slot) {
		return inventory.get(slot);
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		inventory.set(slot, stack);
		setChanged();
	}

	@Override
	public ItemStack removeItem(int slot, int count) {
		ItemStack stack = ContainerHelper.removeItem(inventory, slot, count);
		if (!stack.isEmpty()) setChanged();
		return stack;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(inventory, slot);
	}

	@Override
	public int getMaxStackSize() {
		return 1; // Each slot can only hold one item
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void clearContent() {
		inventory.clear();
	}

	public static class SmokeDischargerScrollValueBehavior extends ValveHandleBlockEntity.ValveHandleScrollValueBehaviour {
		public static final BehaviourType<SmokeDischargerScrollValueBehavior> PITCH_TYPE = new BehaviourType<>();
		public static final BehaviourType<SmokeDischargerScrollValueBehavior> YAW_TYPE = new BehaviourType<>();

		private final boolean pitch;
		private final ValueBoxTransform newSlotPositioning;

		public SmokeDischargerScrollValueBehavior(SmartBlockEntity be, boolean pitch) {
			super(be);
			String suffix = pitch ? "pitch" : "yaw";
			this.setLabel(CreateLang.builder(CreateBigCannons.MOD_ID).translate("fixed_cannon_mount.angle_" + suffix).component());
			this.newSlotPositioning = new SmokeDischargerValueBox(pitch);
			this.pitch = pitch;
			this.between(-45, 45);
			if (pitch) this.between(-75, 75);
			this.withFormatter(v -> {
				return String.format("%s%d", v < 0 ? "-" : v > 0 ? "+" : "", Math.abs(v)) + CreateLang.translateDirect("generic.unit.degrees").getString();
			});
		}

		@Override
		public boolean testHit(Vec3 hit) {
			Level level = this.blockEntity.getLevel();
			BlockPos pos = this.blockEntity.getBlockPos();
			BlockState state = this.blockEntity.getBlockState();
			Vec3 localHit = hit.subtract(Vec3.atLowerCornerOf(this.blockEntity.getBlockPos()));
			return this.newSlotPositioning.testHit(level, pos, state, localHit);
		}


		@Override
		public MutableComponent formatValue(ValueSettings settings) {
			int sgn = settings.row() == 0 ? -1 : 1;
			return CreateLang.number(settings.value() * sgn)
				.add(CreateLang.translateDirect("generic.unit.degrees"))
				.component();
		}


		@Override
		public void setValueSettings(Player player, ValueSettings valueSetting, boolean ctrlHeld) {
			int value = valueSetting.value();
			if (!valueSetting.equals(this.getValueSettings()))
				this.playFeedbackSound(this);
			this.setValue(valueSetting.row() == 0 ? -value : value);
		}


		@Override
		public ValueSettingsBoard createBoard(Player player, BlockHitResult hitResult) {
			ImmutableList<Component> rows = ImmutableList.of(Component.literal("-").withStyle(ChatFormatting.BOLD),
				Component.literal("+").withStyle(ChatFormatting.BOLD));
			if (pitch) {
				rows = ImmutableList.of(Component.literal("+").withStyle(ChatFormatting.BOLD));
			}
			int max = pitch ? 75 : 45;
			return new ValueSettingsBoard(this.label, max, 15, rows, new ValueSettingsFormatter(this::formatValue));
		}

		@Override public ValueBoxTransform getSlotPositioning() { return this.newSlotPositioning; }

		@Override public BehaviourType<?> getType() { return this.pitch ? PITCH_TYPE : YAW_TYPE; }

		public boolean setsPitch() { return this.pitch; }

		@Override
		public void write(CompoundTag nbt, boolean clientPacket) {
			nbt.putInt(this.pitch ? "PitchAdjustment" : "YawAdjustment", this.value);
		}

		@Override
		public void read(CompoundTag nbt, boolean clientPacket) {
			this.value = nbt.getInt(this.pitch ? "PitchAdjustment" : "YawAdjustment");
		}
	}

	public static class SmokeDischargerValueBox extends CenteredSideValueBoxTransform {
		private final boolean pitch;

		public SmokeDischargerValueBox(boolean pitch) {
			super((state, dir) -> {
				return state.getValue(BlockStateProperties.FACING) != dir;
			});
			this.pitch = pitch;
		}

		@Override
		protected Vec3 getSouthLocation() {
			double xOffset = this.pitch ? -4 : 4;
			return VecHelper.voxelSpace(8 + xOffset, 4, 15.5);
		}
	}

}
