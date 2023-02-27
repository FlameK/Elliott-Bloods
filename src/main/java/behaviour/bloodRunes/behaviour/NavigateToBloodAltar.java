package behaviour.bloodRunes.behaviour;

import api.ReactionGenerator;
import api.data.Data;
import api.framework.Leaf;
import behaviour.bloodRunes.data.BloodRuneData;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;

public class NavigateToBloodAltar extends Leaf
{
	@Override
	public boolean isValid()
	{
		return Inventory.stream().id(BloodRuneData.DARK_ESSENCE_FRAGMENTS).count() > 0
				&& Inventory.stream().id(BloodRuneData.DARK_ESSENCE_BLOCK).count() > 0
				&& Inventory.isFull()
				&& !BloodRuneData.BLOOD_RUNE_ALTAR_AREA.contains(Players.local());
	}

	@Override
	public int onLoop()
	{
		Data.scriptStatus = "Walking to Blood Altar";
		Movement.builder(BloodRuneData.BLOOD_RUNE_ALTAR_TILE)
				.setRunMin(15)
				.setRunMax(80)
				.setWalkUntil(() -> BloodRuneData.BLOOD_RUNE_ALTAR_AREA.contains(Players.local()))
				.move();
		return ReactionGenerator.getPredictable();
	}
}
