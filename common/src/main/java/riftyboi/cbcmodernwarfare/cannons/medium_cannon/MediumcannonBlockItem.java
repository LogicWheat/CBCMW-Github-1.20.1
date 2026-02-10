package riftyboi.cbcmodernwarfare.cannons.medium_cannon;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import javax.annotation.Nullable;

import rbasamoyai.createbigcannons.base.CBCTooltip;
import riftyboi.cbcmodernwarfare.content.CBCModernWarfareTooltip;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.material.MediumcannonMaterial;

import java.util.List;

public class MediumcannonBlockItem<T extends Block & MediumcannonBlock> extends BlockItem {

	private final T mediumcannonBlock;

	public MediumcannonBlockItem(T block, Properties properties) {
		super(block, properties);
		this.mediumcannonBlock = block;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		CBCModernWarfareTooltip.appendTextMediumcannon(stack, level, tooltip, flag, this.mediumcannonBlock);
	}

	@Override
	public InteractionResult place(BlockPlaceContext context) {
		InteractionResult result = super.place(context);
		Player player = context.getPlayer();
		MediumcannonMaterial material = this.mediumcannonBlock.getMediumcannonMaterial();
		if (player != null && (material.properties().connectsInSurvival() || player.isCreative()))
			MediumcannonBlock.onPlace(context.getLevel(), context.getClickedPos());
		return result;
	}
}
