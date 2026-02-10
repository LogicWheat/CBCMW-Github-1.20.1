package riftyboi.cbcmodernwarfare.cannons.medium_cannon.breech;

import static rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBlock.writeAndSyncSingleBlockData;

import java.util.List;

import com.simibubi.create.content.contraptions.Contraption;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.AnimatedMediumcannon;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.MediumcannonBlockEntity;

public class MediumcannonBreechBlockEntity extends MediumcannonBlockEntity implements AnimatedMediumcannon {

	private int animateTicks = 5;
	private int power = 5;
	private int openProgress;
	private int openDirection;
	private int loadingCooldown;



	private ItemStack inputBuffer = ItemStack.EMPTY;

	public MediumcannonBreechBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public void handleFiring() {
		this.animateTicks = 0;
	}

	public void setPower(int p){
		this.power = p * 2;
	}

	public float getAnimateOffset(float partialTicks) {
		float t = ((float) this.animateTicks + partialTicks) * 1.2f;
		if (t <= 0 || t >= ((float) power - 0.2f)) return 1;
		float f = t < 1 ? t : (((float) power - 0.2f) - t) / ((float) power - 1.2f);
		return Mth.cos(f * Mth.HALF_PI);
	}


	public ItemStack getInputBuffer() { return this.inputBuffer; }

	@Override
	public void tick() {
		super.tick();
		this.animateTicks = this.power;
		if (this.openProgress != 0) this.openProgress = 0;
		if (this.openDirection != 0) this.openDirection = 0;
		if (this.loadingCooldown != 0) this.loadingCooldown = 0;
	}

	private void allTick() {
		if (this.animateTicks < power) ++this.animateTicks;
		if (this.animateTicks < 0) this.animateTicks = 0;
	}
	public void tickAnimation() {
		if (this.openDirection != 0 && !isInstantOpen()) {
			this.openProgress = Mth.clamp(this.openProgress + this.openDirection, 0, Math.max(getOpeningTime(), 1));
			if (!this.onInteractionCooldown()) this.openDirection = 0;
		}
		if (this.loadingCooldown > 0) --this.loadingCooldown;
	}

	@Override
	public void tickFromContraption(Level level, PitchOrientedContraptionEntity poce, BlockPos localPos) {
		super.tickFromContraption(level, poce, localPos);
		this.allTick();
		this.tickAnimation();
		if (!level.isClientSide) {
			Contraption contraption = poce.getContraption();
			writeAndSyncSingleBlockData(this, contraption.getBlocks().get(localPos), poce, contraption);
		}
	}

	public boolean canFire() { return this.openProgress == 0; }

	@Override public void incrementAnimationTicks() { ++this.animateTicks; }
	@Override public int getAnimationTicks() { return this.animateTicks; }

	@Override
	protected void read(CompoundTag tag, boolean clientPacket) {
		super.read(tag, clientPacket);
		this.animateTicks = tag.getInt("AnimateTicks");
		this.power = tag.getInt("RecoilPower");
		this.inputBuffer = tag.contains("Input") ? ItemStack.of(tag.getCompound("Input")) : ItemStack.EMPTY;
		this.openProgress = tag.getInt("OpenProgress");
		this.openDirection = Mth.clamp(tag.getInt("OpenDirection"), -1, 1);
		this.loadingCooldown = Math.max(0, tag.getInt("LoadingCooldown"));
		if (!clientPacket) return;
	}

	@Override
	protected void write(CompoundTag tag, boolean clientPacket) {
		super.write(tag, clientPacket);
		tag.putInt("OpenProgress", this.openProgress);
		tag.putInt("OpenDirection", this.openDirection);
		tag.putInt("LoadingCooldown", this.loadingCooldown);
		tag.putInt("AnimateTicks", this.animateTicks);
		tag.putInt("RecoilPower", this.power);
		if (this.inputBuffer != null && !this.inputBuffer.isEmpty()) tag.put("Input", this.inputBuffer.save(new CompoundTag()));
		if (!clientPacket) return;
	}


	public boolean isInputFull() { return !this.inputBuffer.isEmpty(); }

	public void setInputBuffer(ItemStack stack) {
		this.inputBuffer = stack;
	}
	public void clearInputBuffer() {
		this.inputBuffer = ItemStack.EMPTY;
	}

	@Override
	public List<ItemStack> getDrops() {
		List<ItemStack> list = super.getDrops();
		if (!this.inputBuffer.isEmpty()) list.add(this.inputBuffer.copy());
		return list;

	}

	public boolean isOpen() {
		return isInstantOpen() ? this.openProgress > 0 : this.openProgress >= getOpeningTime();
	}

	public int getOpenDirection() {
		return this.openDirection;
	}

	public void toggleOpening() {
		if (isInstantOpen()) {
			this.openProgress = this.openProgress > 0 ? 0 : 1;
			return;
		}
		if (!this.onInteractionCooldown()) {
			this.openDirection = this.isOpen() ? -1 : 1;
		}
	}

	public void setAnimateTicks(int ticks) {
		this.animateTicks = ticks;
	}
	public int getPower() {
		return power;
	}

	public float getOpenProgress(float partialTicks) {
		if (isInstantOpen()) {
			return Mth.clamp(this.openProgress, 0.0f, 1.0f);
		}
		return Mth.clamp((this.openProgress + this.openDirection * partialTicks) / getOpeningTime(), 0.0f, 1.0f);
	}

	public int getOpenProgress() {
		return this.openProgress;
	}

	public boolean onInteractionCooldown() {
		return 0 < this.openProgress && this.openProgress < getOpeningTime();
	}

	public boolean canBeAutomaticallyLoaded() {
		return this.loadingCooldown <= 0 && this.openProgress == 0;
	}

	public void setLoadingCooldown(int value) {
		this.loadingCooldown = value;
	}

	public static int getOpeningTime() {
		return CBCConfigs.server().cannons.quickfiringBreechOpeningCooldown.get();
	}

	public static boolean isInstantOpen() { return getOpeningTime() <= 0; }
}
