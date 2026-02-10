package riftyboi.cbcmodernwarfare.content.smoke_discharger;

import java.util.function.Consumer;

import com.simibubi.create.AllKeys;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBehaviour.ValueSettings;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBoard;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsScreen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import rbasamoyai.createbigcannons.multiloader.NetworkPlatform;
import rbasamoyai.createbigcannons.network.ServerboundSetFixedCannonMountValuePacket;
import riftyboi.cbcmodernwarfare.network.ServerboundSetSmokeDischargerValuePacket;

public class SmokeDischargerValueScreen  extends ValueSettingsScreen {

	private final boolean pitch;
	private final BlockPos posCopy;

	public SmokeDischargerValueScreen(BlockPos pos, ValueSettingsBoard board, ValueSettings valueSettings,
									   Consumer<ValueSettings> onHover, boolean pitch, int netId) {
		super(pos, board, valueSettings, onHover, netId);
		this.pitch = pitch;
		this.posCopy = pos;
	}

	@Override
	protected void saveAndClose(double pMouseX, double pMouseY) {
		ValueSettings closest = getClosestCoordinate((int) pMouseX, (int) pMouseY);
		// FIXME: value settings may be face-sensitive on future components - taken from ValueSettingsScreen#saveAndClose
		NetworkPlatform.sendToServer(new ServerboundSetSmokeDischargerValuePacket(this.posCopy, closest.row(), closest.value(),
			null, null, Direction.UP, AllKeys.ctrlDown(), this.pitch));
		this.onClose();
	}
}
