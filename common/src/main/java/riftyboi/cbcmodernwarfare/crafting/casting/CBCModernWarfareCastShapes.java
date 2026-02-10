package riftyboi.cbcmodernwarfare.crafting.casting;

import com.mojang.serialization.Lifecycle;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.core.Direction;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.base.CBCRegistries;
import rbasamoyai.createbigcannons.base.PropertySetter;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import rbasamoyai.createbigcannons.index.CBCBlocks;
import rbasamoyai.createbigcannons.multiloader.IndexPlatform;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlocks;

public class CBCModernWarfareCastShapes {
	private static final int INGOT_SIZE_MB = 90;

	public static final CannonCastShape

		ROTARYCANNON_BARREL = register("rotarycannon_barrel", new CannonCastShape(3 * INGOT_SIZE_MB, 8, CBCModernWarfareBlocks.ROTARYCANNON_BARREL_CAST_MOULD, false, false, PropertySetter.of(BlockStateProperties.FACING, Direction.UP))),
		ROTARYCANNON_BARREL_FLANGED = register("rotarycannon_barrel_flanged", new CannonCastShape(3 * INGOT_SIZE_MB, 8, () -> Blocks.AIR, false, false)),
		ROTARYCANNON_BREECH = register("rotarycannon_breech", new CannonCastShape(4 * INGOT_SIZE_MB, 16, CBCModernWarfareBlocks.ROTARYCANNON_BREECH_CAST_MOULD, false, false, PropertySetter.of(BlockStateProperties.FACING, Direction.UP))),
		ROTARYCANNON_BEARING = register("rotarycannon_bearing", new CannonCastShape(4 * INGOT_SIZE_MB, 12, CBCModernWarfareBlocks.ROTARYCANNON_BEARING_CAST_MOULD, false, false, PropertySetter.of(BlockStateProperties.FACING, Direction.UP))),

		MEDIUMCANNON_BARREL = register("mediumcannon_barrel", new CannonCastShape(3 * INGOT_SIZE_MB, 8, CBCModernWarfareBlocks.MEDIUMCANNON_BARREL_CAST_MOULD, false, false, PropertySetter.of(BlockStateProperties.FACING, Direction.UP))),
		MEDIUMCANNON_BARREL_FLANGED = register("mediumcannon_barrel_flanged", new CannonCastShape(3 * INGOT_SIZE_MB, 8, () -> Blocks.AIR, false, false)),
		MEDIUMCANNON_BREECH = register("mediumcannon_breech", new CannonCastShape(4 * INGOT_SIZE_MB, 16, CBCModernWarfareBlocks.MEDIUMCANNON_BREECH_CAST_MOULD, false, false, PropertySetter.of(BlockStateProperties.FACING, Direction.UP))),
		MEDIUMCANNON_RECOIL_BARREL = register("mediumcannon_recoil_barrel", new CannonCastShape(4 * INGOT_SIZE_MB, 10, CBCModernWarfareBlocks.MEDIUMCANNON_RECOIL_BARREL_CAST_MOULD, false, false, PropertySetter.of(BlockStateProperties.FACING, Direction.UP)));



	public static void register() {
	}

	private static CannonCastShape register(String id, CannonCastShape shape) {
		return Registry.register(CBCRegistries.cannonCastShapes(), CreateBigCannons.resource(id), shape);
	}

}
