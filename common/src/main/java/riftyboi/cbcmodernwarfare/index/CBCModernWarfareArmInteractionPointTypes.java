package riftyboi.cbcmodernwarfare.index;

import java.util.function.Function;

import javax.annotation.Nullable;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.quickfiring_breech.CannonMountPoint;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountBlockEntity;

	public class CBCModernWarfareArmInteractionPointTypes {
		static {
			register("compact_mount", new CannonMountType());
		}
		private static <T extends ArmInteractionPointType> void register(String name, T type) {
			Registry.register(CreateBuiltInRegistries.ARM_INTERACTION_POINT_TYPE, CBCModernWarfare.resource(name), type);
		}

		public static class CannonMountType extends ArmInteractionPointType {
			@Override
			public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
				if (CBCModernWarfareBlocks.COMPACT_MOUNT.has(state))
					return level.getBlockEntity(pos) instanceof CompactCannonMountBlockEntity;
				return false;
			}

			@Nullable
			@Override
			public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
				return new CannonMountPoint(this, level, pos, state);
			}
		}

		public static void init() {
		}

	}
