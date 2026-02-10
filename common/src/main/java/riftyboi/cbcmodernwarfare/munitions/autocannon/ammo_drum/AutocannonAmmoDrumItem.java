package riftyboi.cbcmodernwarfare.munitions.autocannon.ammo_drum;

import java.util.List;

import javax.annotation.Nullable;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import rbasamoyai.createbigcannons.index.CBCBlocks;
import rbasamoyai.createbigcannons.index.CBCMenuTypes;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonAmmoType;
import rbasamoyai.createbigcannons.munitions.autocannon.ammo_container.AutocannonAmmoContainerItem;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMenuTypes;

public class AutocannonAmmoDrumItem extends AutocannonAmmoContainerItem {

	public AutocannonAmmoDrumItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override public Component getDisplayName() { return this.getDescription(); }

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return AutocannonAmmoDrumMenu.getServerMenuForItemStack(i, inventory, player.getMainHandItem());
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (player.mayBuild()) {
			ItemStack stack = player.getItemInHand(hand);
			if (player instanceof ServerPlayer splayer) {
				int spacing = getTracerSpacing(stack);
				Component screenName = stack.hasCustomHoverName() ? stack.getHoverName() : this.getDisplayName();

				CBCModernWarfareMenuTypes.AUTOCANNON_AMMO_DRUM.open(splayer, screenName, this, buf -> {
					buf.writeVarInt(spacing);
					buf.writeBoolean(false);
					buf.writeItem(new ItemStack(this));
				});
			}
			return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
		}
		return super.use(level, player, hand);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		InteractionResult superResult = super.useOn(context);
		if (superResult.consumesAction()) return superResult;
		return this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
	}
	public static ItemStack getMainAmmoStack(ItemStack container) {
		CompoundTag tag = container.getOrCreateTag();
		return tag.contains("Ammo") ? ItemStack.of(tag.getCompound("Ammo")) : ItemStack.EMPTY;
	}
	public static ItemStack getSecondaryAmmoStack(ItemStack container) {
		CompoundTag tag = container.getOrCreateTag();
		return tag.contains("Ammo1") ? ItemStack.of(tag.getCompound("Ammo1")) : ItemStack.EMPTY;
	}

	public static ItemStack getTracerAmmoStack(ItemStack container) {
		CompoundTag tag = container.getOrCreateTag();
		return tag.contains("Tracers") ? ItemStack.of(tag.getCompound("Tracers")) : ItemStack.EMPTY;
	}

	public static int getTracerSpacing(ItemStack container) {
		CompoundTag tag = container.getOrCreateTag();
		return tag.contains("TracerSpacing") ? Mth.clamp(tag.getInt("TracerSpacing"), 1, 6) : 1;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
		ItemStack secondaryAmmo = getSecondaryAmmoStack(stack);
		if (!secondaryAmmo.isEmpty()) {
			String secondaryValue = Integer.toString(secondaryAmmo.getCount());
			tooltipComponents.add(Component.translatable("block.createbigcannons.autocannon_ammo_container.tooltip.main_ammo", secondaryValue, secondaryAmmo.getDisplayName()));
		}
		super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
	}

}
