package riftyboi.cbcmodernwarfare.cannon_control.compact_mount;

import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.source.NumericSingleLineDisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import rbasamoyai.createbigcannons.CreateBigCannons;
import riftyboi.cbcmodernwarfare.CBCModernWarfare;

public class CompactCannonMountDisplaySource  extends NumericSingleLineDisplaySource {

	private static final MutableComponent noCannonPresent = Component.translatable(CreateBigCannons.MOD_ID + ".display_source.cannon_mount.no_cannon_present");

	@Override protected String getTranslationKey() { return "cannon_mount_source"; }

	@Override
	protected MutableComponent provideLine(DisplayLinkContext context, DisplayTargetStats stats) {
		BlockEntity source = context.getSourceBlockEntity();
		if (!(source instanceof ExtendsCompactCannonMount ext)) return noCannonPresent;
		CompactCannonMountBlockEntity mount = ext.getCannonMount();
		if (mount == null || mount.mountedContraption == null) return noCannonPresent;
		return Component.literal(String.format("%.1f\u00ba", mount.getDisplayPitch()));
	}

	@Override protected boolean allowsLabeling(DisplayLinkContext context) { return true; }

}
