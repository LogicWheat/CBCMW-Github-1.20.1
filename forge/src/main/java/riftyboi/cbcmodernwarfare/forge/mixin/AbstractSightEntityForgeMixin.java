package riftyboi.cbcmodernwarfare.forge.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;

import net.minecraftforge.network.NetworkHooks;

import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.Shadow;

import riftyboi.cbcmodernwarfare.content.sights.entity.AbstractSightEntity;

import javax.annotation.Nonnull;

@Mixin(AbstractSightEntity.class)
public abstract class AbstractSightEntityForgeMixin extends Entity implements IEntityAdditionalSpawnData {
	public AbstractSightEntityForgeMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Shadow public abstract void readAdditionalSaveData(CompoundTag nbt);

	@Shadow public abstract void addAdditionalSaveData(CompoundTag nbt);






	@Override
	@Nonnull
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		CompoundTag nbt = new CompoundTag();
		addAdditionalSaveData(nbt);
		buffer.writeNbt(nbt);
	}

	@Override
	public void readSpawnData(FriendlyByteBuf buffer) {
		CompoundTag nbt = buffer.readNbt();
		if (nbt != null) {
			readAdditionalSaveData(nbt);
		}
	}

}
