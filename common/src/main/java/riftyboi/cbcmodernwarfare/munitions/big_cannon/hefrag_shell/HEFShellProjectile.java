package riftyboi.cbcmodernwarfare.munitions.big_cannon.hefrag_shell;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.index.CBCEntityTypes;
import rbasamoyai.createbigcannons.munitions.ShellExplosion;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.big_cannon.shrapnel.ShrapnelExplosion;
import rbasamoyai.createbigcannons.munitions.big_cannon.shrapnel.ShrapnelShellProperties;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.fragment_burst.CBCProjectileBurst;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlocks;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBigCannonProjectile;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionPropertiesHandlers;

import javax.annotation.Nonnull;

public class HEFShellProjectile  extends FuzedBigCannonProjectile {

	public HEFShellProjectile(EntityType<? extends HEFShellProjectile> type, Level level) {
		super(type, level);
	}

	@Override
	protected void detonate(Position position) {
		Vec3 oldDelta = this.getDeltaMovement();
		HEFShellProperties properties = this.getAllProperties();
		ShellExplosion explosion = new ShellExplosion(this.level(), this, this.indirectArtilleryFire(false), position.x(),
			position.y(), position.z(), this.getAllProperties().explosion().explosivePower(), false,
			CBCConfigs.server().munitions.damageRestriction.get().explosiveInteraction());
		CreateBigCannons.handleCustomExplosion(this.level(), explosion);
		CBCProjectileBurst.spawnConeBurst(this.level(), CBCEntityTypes.SHRAPNEL_BURST.get(), this.position(), oldDelta,
			properties.shrapnelBurst().burstProjectileCount(), properties.shrapnelBurst().burstSpread());
	}

	@Override
	public BlockState getRenderedBlockState() {
		return CBCModernWarfareBlocks.HEF_SHELL.getDefaultState().setValue(BlockStateProperties.FACING, Direction.NORTH);
	}

	@Nonnull
	@Override
	protected BigCannonFuzePropertiesComponent getFuzeProperties() {
		return this.getAllProperties().fuze();
	}

	@Nonnull
	@Override
	protected BigCannonProjectilePropertiesComponent getBigCannonProjectileProperties() {
		return this.getAllProperties().bigCannonProperties();
	}

	@Nonnull
	@Override
	public EntityDamagePropertiesComponent getDamageProperties() {
		return this.getAllProperties().damage();
	}

	@Nonnull
	@Override
	protected BallisticPropertiesComponent getBallisticProperties() {
		return this.getAllProperties().ballistics();
	}

	protected HEFShellProperties getAllProperties() {
		return CBCModernWarfareMunitionPropertiesHandlers.HEF_SHELL.getPropertiesOf(this);
	}

}
