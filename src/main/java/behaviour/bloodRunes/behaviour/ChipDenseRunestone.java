package behaviour.bloodRunes.behaviour;


import api.MethodProvider;
import api.ReactionGenerator;
import api.data.Data;
import api.framework.Leaf;
import api.handlers.LocalPlayer;
import behaviour.bloodRunes.data.BloodRuneData;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

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
		if (!LocalPlayer.isAnimating())
		{
			Data.scriptStatus = "Mining dense runestone";
			GameObject denseRunestone = Objects.stream().name("Dense runestone").action("Chip").nearest().first();
			if (denseRunestone != null)
			{
				if (denseRunestone.interact("Chip"))
				{
					MethodProvider.sleepUntil(LocalPlayer::isAnimating, 10000, ReactionGenerator.getNormal());
				}
			}
			return ReactionGenerator.getNormal();
		}

		Data.scriptStatus = "Idle while Mining";
		return ReactionGenerator.getNormal();
	}
}