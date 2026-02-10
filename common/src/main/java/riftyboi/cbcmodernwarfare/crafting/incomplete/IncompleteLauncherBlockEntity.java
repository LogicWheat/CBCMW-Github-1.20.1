package riftyboi.cbcmodernwarfare.crafting.incomplete;


import com.simibubi.create.api.equipment.goggles.IHaveHoveringInformation;

import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.cannons.big_cannons.material.BigCannonMaterial;
import rbasamoyai.createbigcannons.crafting.WandActionable;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import rbasamoyai.createbigcannons.crafting.incomplete.IncompleteCannonBlockTooltip;
import rbasamoyai.createbigcannons.crafting.incomplete.IncompleteWithItemsCannonBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.IMunitionsLauncherBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlock;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.MunitionsLauncherBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.cannon_end.MunitionsLauncherEndBlockEntity;
import riftyboi.cbcmodernwarfare.cannons.munitions_contraption_launcher.material.MunitionsLauncherMaterial;

import java.util.List;

public class IncompleteLauncherBlockEntity extends MunitionsLauncherEndBlockEntity implements IHaveHoveringInformation, WandActionable {

	public IncompleteLauncherBlockEntity(BlockEntityType<? extends IncompleteLauncherBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public boolean addToTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		if (!(this.getBlockState().getBlock() instanceof IncompleteWithItemsCannonBlock incomplete)) return false;
		IncompleteCannonBlockTooltip.addToTooltip(tooltip, isPlayerSneaking, incomplete, this.getBlockState());
		return true;
	}

	@Override
	public InteractionResult onWandUsed(UseOnContext context) {
		if (!(this.getBlockState().getBlock() instanceof IncompleteWithItemsCannonBlock incomplete))
			return InteractionResult.PASS;
		if (!this.level.isClientSide) {
			CompoundTag loadTag = this.saveWithFullMetadata();
			BlockState state = this.getBlockState();
			BlockState boredState = incomplete.getCompleteBlockState(state);
			this.setRemoved();
			MunitionsLauncherMaterial material = ((MunitionsLauncherBlock) state.getBlock()).getCannonMaterialInLevel(this.level, state, this.worldPosition);
			this.level.setBlock(this.worldPosition, boredState, 11);
			BlockEntity newBE = this.level.getBlockEntity(this.worldPosition);
			if (newBE != null) newBE.load(loadTag);

			for (Direction dir1 : Iterate.directions) {
				if (!this.cannonBehavior().isConnectedTo(dir1)) continue;
				BlockPos pos1 = this.worldPosition.relative(dir1);
				BlockState state1 = this.level.getBlockState(pos1);
				BlockEntity be1 = this.level.getBlockEntity(pos1);
				Direction opposite = dir1.getOpposite();
				if (state1.getBlock() instanceof MunitionsLauncherBlock cBlock1
					&& cBlock1.getCannonMaterialInLevel(this.level, state1, pos1) == material
					&& be1 instanceof IMunitionsLauncherBlockEntity cbe1
					&& cBlock1.canConnectToSide(state1, opposite)) {
					cbe1.cannonBehavior().setConnectedFace(opposite, true);
					be1.setChanged();
				}
			}
			this.level.playSound(null, this.worldPosition, SoundEvents.NETHERITE_BLOCK_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
		}
		return InteractionResult.sidedSuccess(this.level.isClientSide);
	}

}
