package riftyboi.cbcmodernwarfare.munitions.autocannon.canister;

import rbasamoyai.createbigcannons.munitions.autocannon.config.AutocannonProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.ExplosionPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.fragment_burst.ProjectileBurstParentPropertiesComponent;

public record CanisterAutocannonProjectileProperties(BallisticPropertiesComponent ballistics, EntityDamagePropertiesComponent damage,
													 AutocannonProjectilePropertiesComponent autocannonProperties,
													 ExplosionPropertiesComponent explosion,
													 ProjectileBurstParentPropertiesComponent flakBurst) {
}
