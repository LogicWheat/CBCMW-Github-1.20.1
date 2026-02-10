package riftyboi.cbcmodernwarfare.mixin;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.cannon_control.ControlPitchContraption;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountBlockEntity;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.cannon_control.fixed_cannon_mount.FixedCannonMountBlockEntity;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.effects.particles.smoke.QuickFiringBreechSmokeParticleData;
import rbasamoyai.createbigcannons.multiloader.NetworkPlatform;
import riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountBlock;
import riftyboi.cbcmodernwarfare.cannon_control.compact_mount.CompactCannonMountBlockEntity;
import riftyboi.cbcmodernwarfare.cannon_control.contraption.MountedMediumcannonContraption;
import riftyboi.cbcmodernwarfare.cannons.RecoilingCannon;
import riftyboi.cbcmodernwarfare.cannons.medium_cannon.breech.MediumcannonBreechBlockEntity;



import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.content.contraptions.OrientedContraptionEntity;

import riftyboi.cbcmodernwarfare.network.RecoilingCannonUpdatePacket;

@Pseudo
@Mixin(value = PitchOrientedContraptionEntity.class, remap = false)
public abstract class PitchContraptionRecoilMixin extends OrientedContraptionEntity implements RecoilingCannon {
	@Shadow public abstract ControlPitchContraption getController();

	@Shadow private BlockPos controllerPos;
	@Unique
	public double tyh$target = 0;
	@Unique
	public boolean tyh$recoiling = false;
	@Unique
	public boolean tyh$ejecting = false;

	public PitchContraptionRecoilMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Inject(
		method = "tickContraption",
		at = @At("HEAD")
	)
	private void mixintickContraption(CallbackInfo ci) {
		if (!(contraption instanceof MountedMediumcannonContraption)) return;

		if (tickCount > 2) {
			ControlPitchContraption controller = this.getController();
			Vec3 origin = null;
			if (controller instanceof CannonMountBlockEntity cmbe) {
				boolean upsideDown = cmbe.getBlockState().getValue(BlockStateProperties.VERTICAL_DIRECTION) == Direction.UP;
				origin = Vec3.atBottomCenterOf((cmbe.getBlockPos().above(upsideDown ? -2 : 2)));
			} else if (controller instanceof CompactCannonMountBlockEntity ccmbe) {
				Direction facing = ccmbe.getBlockState().getValue(CompactCannonMountBlock.HORIZONTAL_FACING);
				origin = Vec3.atBottomCenterOf(ccmbe.getBlockPos().relative(facing.getClockWise(), 1));
			} else if (controller instanceof FixedCannonMountBlockEntity fcmbe) {
				Direction facing = fcmbe.getBlockState().getValue(BlockStateProperties.FACING);
				origin = Vec3.atBottomCenterOf(fcmbe.getBlockPos().relative(facing));
			}
			if (origin == null) return;

			Vec3 normal = getNormal();
			Vec3 target = origin.add(normal.scale(tyh$target));
			Vec3 offset = target.subtract(this.position());

			Vec3 movementVec = VecHelper.clamp(offset, getRecoilSpeed());
//                this.setContraptionMotion(movementVec);
			move(movementVec.x, movementVec.y, movementVec.z);

			if (tyh$recoiling) {
				// goddamn magic numbers
				if (offset.length() < 1e-1) {
					this.setPos(target.x, target.y, target.z);
					if (tyh$target == -1) {    // return to battery
						tyh$ejecting = true;
						tyh$target = 0;
						this.setContraptionMotion(normal.scale(getRecoilSpeed()));
					} else if (tyh$target == 0) {  //stop
						this.setContraptionMotion(Vec3.ZERO);
						tyh$recoiling = false;
					}

					this.tyh$sendPacket();
				}
			}
		}
	}

	@Unique
	protected void tyh$ejectShell() {
		AbstractMountedCannonContraption c = (AbstractMountedCannonContraption) this.contraption;
		BlockEntity be = c.presentBlockEntities.get(c.getStartPos());
		if (be instanceof MediumcannonBreechBlockEntity breech) {
			Level level = this.level();
			level.playSound((Player) null,
				BlockPos.containing(this.toGlobalVector(c.getStartPos().getCenter(), 0)),
				SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS, 1.0F, 1.5F);

			ItemStack extract;
			breech.toggleOpening();
			if (breech.getOpenDirection() > 0) {
				extract = breech.getInputBuffer();
				Vec3 normal = Vec3.atLowerCornerOf(c.initialOrientation().getNormal()).reverse();
				Vec3 dir = this.applyRotation(normal, 0.0F);
				Vec3 smokePos;
				Vec3 globalPos;
				Vec3 vel;
				if (!extract.isEmpty()) {
					smokePos = Vec3.atCenterOf(c.getStartPos()).add(normal.scale(0.2));
					globalPos = this.toGlobalVector(smokePos, 0.0F);

					vel = dir.scale(0.5);
					ItemEntity item = new ItemEntity(level, globalPos.x, globalPos.y, globalPos.z, extract, vel.x, vel.y, vel.z);
					item.setPickUpDelay((Integer) CBCConfigs.server().munitions.quickFiringBreechItemPickupDelay.get());
					level.addFreshEntity(item);

					breech.clearInputBuffer();
				}

				MountedMediumcannonContraption cannon = (MountedMediumcannonContraption) c;
				if (level instanceof ServerLevel slevel && cannon.hasFired) {
					smokePos = Vec3.atCenterOf(c.getStartPos()).add(normal.scale(0.6));
					globalPos = this.toGlobalVector(smokePos, 0.0F);
					vel = dir.scale(0.15);
					slevel.sendParticles(new QuickFiringBreechSmokeParticleData(), globalPos.x, globalPos.y, globalPos.z, 0, vel.x, vel.y, vel.z, 1.0);
					cannon.hasFired = false;
				}
			}
		}
	}

	@Unique
	public void tyh$sendPacket() {
		Vec3 normal = getNormal();
		NetworkPlatform.sendToClientTracking(new RecoilingCannonUpdatePacket(getId(), this.getDeltaMovement().dot(normal), tyh$target), this);
	}

	public Vec3 getNormal() {
		return this.applyRotation(Vec3.atLowerCornerOf(this.getInitialOrientation().getNormal()), 0);
	}

	public float getRecoilSpeed() {
		return (tyh$target < 0) ? 2f : 0.3f;
	}

	@Override
	public void recoil(Vec3 v) {
		// Initial kickback
		tyh$recoiling = true;
		tyh$target = -1;
		this.setContraptionMotion(getNormal().scale(-getRecoilSpeed()));
		if (!this.level().isClientSide) this.tyh$sendPacket();
	}

	@Override
	public void handlePacket(RecoilingCannonUpdatePacket p) {
		Vec3 n = getNormal();
		this.setContraptionMotion(n.scale(p.motion));
		tyh$target = p.target;
	}
}

