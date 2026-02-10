package riftyboi.cbcmodernwarfare.forge.mixin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraftforge.common.capabilities.ForgeCapabilities;

import org.spongepowered.asm.mixin.Mixin;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;

import dev.architectury.patchedmixin.staticmixin.spongepowered.asm.mixin.Shadow;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountBlockEntity;

@Mixin(CompactCannonMountBlockEntity.class)
public abstract class CompactCannonMountMixin extends KineticBlockEntity {

	CompactCannonMountMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}


	@Shadow
	protected PitchOrientedContraptionEntity mountedContraption;

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == ForgeCapabilities.ITEM_HANDLER && this.mountedContraption != null) {
			return this.mountedContraption.getCapability(cap, side).cast();
		}
		return super.getCapability(cap, side);
	}

}
