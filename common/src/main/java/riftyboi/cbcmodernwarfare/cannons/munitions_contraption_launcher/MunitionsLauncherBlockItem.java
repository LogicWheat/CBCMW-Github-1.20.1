package riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import riftyboi.cbcmodernwarfare.content.CBCModernWarfareTooltip;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.material.MunitionsLauncherMaterial;

import javax.annotation.Nullable;
import java.util.List;

public class MunitionsLauncherBlockItem<T extends Block & MunitionsLauncherBlock> extends BlockItem {

	private final T cannonBlock;

	public MunitionsLauncherBlockItem(T block, Properties properties) {
		super(block, properties);
		this.cannonBlock = block;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		CBCModernWarfareTooltip.appendLauncherBlockText(stack, level, tooltip, flag, this.cannonBlock);

	}

	@Override
	public InteractionResult place(BlockPlaceContext context) {
		InteractionResult result = super.place(context);
		Player player = context.getPlayer();
		MunitionsLauncherMaterial material = this.cannonBlock.getCannonMaterial();
		if (player != null && (material.properties().connectsInSurvival() || player.isCreative()))
			MunitionsLauncherBlock.onPlace(context.getLevel(), context.getClickedPos());
		return result;
	}

}
