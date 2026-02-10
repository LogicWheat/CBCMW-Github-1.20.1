package riftyboi.cbcmodernwarfare.munitions.contraptions;

import com.simibubi.create.api.contraption.ContraptionType;
import com.simibubi.create.content.contraptions.AssemblyException;
import com.simibubi.create.content.contraptions.Contraption;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareContraptionTypes;

import java.util.HashMap;
import java.util.Map;

public class MunitionsPhysicsContraption extends Contraption {

	protected BlockPos startPos = BlockPos.ZERO;
	protected Direction initialOrientation = Direction.NORTH;	public final Map<BlockPos, BlockEntity> presentBlockEntities = new HashMap<>();

	@Override
	public boolean assemble(Level world, BlockPos pos) throws AssemblyException {
		return true;
	}

	public AABB createBoundsFromPositions(Vec3 firstPos, Vec3 lastPos) {
		if (this.entity != null && this.entity instanceof MunitionsPhysicsContraptionEntity mpce) {
			Vec3 normal = mpce.getOrientation().normalize();
			AABB aabb = new AABB(BlockPos.ZERO);
			Vec3 middlePoint = this.entity.toLocalVector(this.entity.getPosition(0),0);
			double firstDistance = middlePoint.distanceTo(firstPos);
			double lastDistance = middlePoint.distanceTo(lastPos);
			aabb = aabb.expandTowards(normal.scale(firstDistance)).expandTowards(normal.scale(-lastDistance));
			return aabb.deflate(0.8);
		}
		return new AABB(BlockPos.ZERO);
	}


	public AABB createInitialLightingBounds() {
		Direction.Axis inflateAxis = this.initialOrientation.getAxis() == Direction.Axis.Y ? Direction.Axis.X : Direction.Axis.Y;
		return new AABB(BlockPos.ZERO).inflate(Math.ceil(Math.sqrt(getRadius(this.getBlocks().keySet(), inflateAxis))) + 1);
	}

	@Override
	public boolean canBeStabilized(Direction facing, BlockPos localPos) {
		return false;
	}

	@Override
	public ContraptionType getType() {
		return CBCModernWarfareContraptionTypes.MUNITIONS_CONTRAPTION.value();
	}

	@Override
	public CompoundTag writeNBT(boolean spawnPacket) {
		for (Map.Entry<BlockPos, BlockEntity> entry : this.presentBlockEntities.entrySet()) {
			StructureTemplate.StructureBlockInfo info = this.blocks.get(entry.getKey());
			if (info == null) continue;
			CompoundTag nbt = entry.getValue().saveWithFullMetadata();
			nbt.remove("x");
			nbt.remove("y");
			nbt.remove("z");
			this.blocks.put(entry.getKey(), new StructureTemplate.StructureBlockInfo(info.pos(), info.state(), nbt));
		}

		CompoundTag tag = super.writeNBT(spawnPacket);
		if (this.initialOrientation != null) {
			tag.putString("InitialOrientation", this.initialOrientation.getSerializedName());
		}
		tag.putLong("LocalStartingPos", this.startPos == null ? 0L : this.startPos.asLong());
		return tag;
	}

	@Override
	public void readNBT(Level world, CompoundTag tag, boolean spawnData) {
		super.readNBT(world, tag, spawnData);
		this.initialOrientation = tag.contains("InitialOrientation", Tag.TAG_STRING) ? Direction.byName(tag.getString("InitialOrientation")) : Direction.NORTH;
		this.startPos = BlockPos.of(tag.getLong("LocalStartingPos"));

		if (world.isClientSide) return;
		for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> entry : this.blocks.entrySet()) {
			StructureTemplate.StructureBlockInfo info = this.blocks.get(entry.getKey());
			if (info == null || info.nbt() == null) continue;

			info.nbt().putInt("x", info.pos().getX());
			info.nbt().putInt("y", info.pos().getY());
			info.nbt().putInt("z", info.pos().getZ());

			BlockEntity be = BlockEntity.loadStatic(info.pos(), info.state(), info.nbt());
			if (be == null) continue;
			be.setLevel(world);
			this.presentBlockEntities.put(info.pos(), be);
		}
	}
	public Direction initialOrientation() {
		return this.initialOrientation;
	}
}
