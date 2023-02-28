package behaviour.bloodRunes.behaviour;

import api.CarriedItems;
import api.ReactionGenerator;
import api.data.Data;
import api.framework.Leaf;
import api.handlers.LocalPlayer;
import behaviour.bloodRunes.data.BloodRuneData;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class ChipDenseRunestone extends Leaf
{
	@Override
	public boolean isValid()
	{
		return BloodRuneData.DENSE_ESSENCE_AREA.contains(Players.local())
				&& !Inventory.isFull();
	}

	@Override
	public int onLoop()
	{

		if (!CarriedItems.contains(x -> x.name().contains("pickaxe")))
		{
			Data.scriptStatus = "No pickaxe found.";
			return ReactionGenerator.getPredictable();
		}

		if (!LocalPlayer.isAnimating())
		{
			Data.scriptStatus = "Mining dense runestone";
			GameObject denseRunestone = Objects.stream().name("Dense runestone").action("Chip").nearest().first();
			if (denseRunestone.valid() && denseRunestone.interact("Chip"))
			{
				Condition.wait(LocalPlayer::isAnimating, 500, 20);
			}
			return ReactionGenerator.getPredictable();
		}

		Data.scriptStatus = "Idle while Mining";
		return ReactionGenerator.getPredictable();
	}
}
