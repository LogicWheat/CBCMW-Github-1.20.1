package riftyboi.cbcmodernwarfare.munitions.big_cannon.heap_shell;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.fragment_burst.CBCProjectileBurst;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareBlocks;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareEntityTypes;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBigCannonProjectile;
import riftyboi.cbcmodernwarfare.index.CBCModernWarfareMunitionPropertiesHandlers;

import javax.annotation.Nonnull;

public class HEAPShellProjectile extends FuzedBigCannonProjectile {

	public HEAPShellProjectile(EntityType<? extends HEAPShellProjectile> type, Level level) {
		super(type, level);
	}

	@Override
	protected void detonate(Position position) {
		Vec3 oldDelta = this.getDeltaMovement().normalize().scale(2.0f);
		HEAPShellProperties properties = this.getAllProperties();
		HEAPExplosion explosion = new HEAPExplosion(this.level(), this, this.indirectArtilleryFire(false), position.x(),
			position.y(), position.z(), this.getAllProperties().explosion().explosivePower(), true,
			CBCConfigs.server().munitions.damageRestriction.get().explosiveInteraction());
		CreateBigCannons.handleCustomExplosion(this.level(), explosion);
		CBCProjectileBurst.spawnConeBurst(this.level(), CBCModernWarfareEntityTypes.HEAP_BURST.get(), this.position(), oldDelta,
			properties.shrapnelBurst().burstProjectileCount(), properties.shrapnelBurst().burstSpread());
	}

	@Override
	public BlockState getRenderedBlockState() {
		return CBCModernWarfareBlocks.HEAP_SHELL.getDefaultState().setValue(BlockStateProperties.FACING, Direction.NORTH);
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

	protected HEAPShellProperties getAllProperties() {
		return CBCModernWarfareMunitionPropertiesHandlers.HEAP_SHELL.getPropertiesOf(this);
	}

}
