package riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config;

import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import riftyboi.cbcmodernwarfare.munitions.munitions_contraption_launcher.config.components.ThrusterPropertiesComponent;

public record SplitFuelThrusterProperties(ThrusterPropertiesComponent thrusterProperties, BallisticPropertiesComponent ballisticPropertiesComponent,
                                          EntityDamagePropertiesComponent entityDamagePropertiesComponent) {
}
