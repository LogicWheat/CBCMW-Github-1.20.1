package riftyboi.cbcmodernwarfare.cannons.rotarycannon.bearing;


import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.AnimatedRotarycannon;
import riftyboi.cbcmodernwarfare.cannons.rotarycannon.RotarycannonBlockEntity;

import java.util.HashMap;
import java.util.Map;

public class RotarycannonBearingBlockEntity extends RotarycannonBlockEntity {

    public Map<BlockPos, BlockState> toAnimate = new HashMap<>();
	private float rotation = 0;
	private float rot = 0;
	private float power = 0;

    public RotarycannonBearingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        this.allTick();
    }

    @Override
    public void tickFromContraption(Level level, PitchOrientedContraptionEntity poce, BlockPos localPos) {
        super.tickFromContraption(level, poce, localPos);
        this.allTick();
    }

    private void allTick() {
		if (this.rotation < 0) this.rotation = 0;
		if (this.rotation > 360) this.rotation -= 360;
    }

    public void setRotation(float rot, float time) {
		this.rotation = ((time * rot * 2.5f / 10 ) % 360) / 180 * (float) Math.PI;;
    }
	public float getRotation() {
		if (power < 1 || power > 15)  {
			return 0;
		}
		return this.rotation;

	}

	public float getRot() {
		return this.rot;
	}

	public void updateRotation(int fireRate, int power) {
		this.power = power;
		this.rot = (fireRate / 1200f) * 360;
	}

	@Override
	protected void write(CompoundTag tag, boolean clientPacket) {
		super.write(tag, clientPacket);

		tag.putFloat("Rotation", this.rotation);

		ListTag renderedList = new ListTag();
		for (Map.Entry<BlockPos, BlockState> entry : this.toAnimate.entrySet()) {
			CompoundTag block = new CompoundTag();
			block.put("Pos", NbtUtils.writeBlockPos(entry.getKey()));
			block.put("Block", NbtUtils.writeBlockState(entry.getValue()));
			renderedList.add(block);
		}

		tag.put("RenderedBlocks", renderedList);
	}

	@Override
	protected void read(CompoundTag tag, boolean clientPacket) {
		super.read(tag, clientPacket);

		this.rotation = tag.getFloat("Rotation");

		this.toAnimate.clear();
		ListTag renderedList = tag.getList("RenderedBlocks", Tag.TAG_COMPOUND);
		for (int i = 0; i < renderedList.size(); ++i) {
			CompoundTag block = renderedList.getCompound(i);
			this.toAnimate.put(NbtUtils.readBlockPos(block.getCompound("Pos")),
				NbtUtils.readBlockState(this.blockHolderGetter(), block.getCompound("Block")));
		}
	}
}
