package riftyboi.cbcmodernwarfare.cannons.rotarycannon;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import javax.annotation.Nullable;

import riftyboi.cbcmodernwarfare.content.CBCModernWarfareTooltip;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.material.RotarycannonMaterial;

import java.util.List;

public class RotarycannonBlockItem<T extends Block & RotarycannonBlock> extends BlockItem {

    private final T rotarycannonBlock;

    public RotarycannonBlockItem(T block, Properties properties) {
        super(block, properties);
        this.rotarycannonBlock = block;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        CBCModernWarfareTooltip.appendTextRotarycannon(stack, level, tooltip, flag, this.rotarycannonBlock);
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        InteractionResult result = super.place(context);
		Player player = context.getPlayer();
		RotarycannonMaterial material = this.rotarycannonBlock.getRotarycannonMaterial();
		if (player != null && (material.properties().connectsInSurvival() || player.isCreative()))
			RotarycannonBlock.onPlace(context.getLevel(), context.getClickedPos());
        return result;
    }

}
