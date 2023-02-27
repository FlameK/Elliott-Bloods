package behaviour.bloodRunes.behaviour;

import api.MethodProvider;
import api.ReactionGenerator;
import api.data.Data;
import api.framework.Leaf;
import api.handlers.LocalPlayer;
import behaviour.bloodRunes.data.BloodRuneData;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;

public class NavigateToDarkAltar extends Leaf
{

	@Override
	public boolean isValid()
	{
		return Inventory.isFull()
				&& Inventory.stream().id(BloodRuneData.DENSE_ESSENCE_BLOCK).count() > 0;
	}

	@Override
	public int onLoop()
	{


		if (BloodRuneData.DENSE_ESSENCE_AREA.contains(Players.local()))
		{
			Data.scriptStatus = "Using Shortcut";
			GameObject rocks = Objects.stream().id(BloodRuneData.ROCK_ID).action("Climb").nearest().firstOrNull();
			if (!LocalPlayer.isAnimating())
			{
				if (rocks != null)
				{
					if (rocks.interact("Climb"))
					{
						MethodProvider.sleepUntil(() -> !BloodRuneData.DENSE_ESSENCE_AREA.contains(Players.local()), 5000);
					}
				}
				Movement.builder(BloodRuneData.ROCK_TILE)
						.setRunMin(15)
						.setRunMax(80)
						.setUseTeleports(false)
						.setWalkUntil(() -> Objects.stream().id(BloodRuneData.ROCK_ID).action("Climb").nearest() != null)
						.move();
			}
			return ReactionGenerator.getPredictable();
		}

		if (!BloodRuneData.DARK_ALTAR_AREA.contains(Players.local()))
		{
			Data.scriptStatus = "Walking to Dark Altar";
			Movement.builder(BloodRuneData.DARK_ALTAR_TILE)
					.setRunMin(15)
					.setRunMax(80)
					.setUseTeleports(false)
					.setWalkUntil(() -> BloodRuneData.DARK_ALTAR_AREA.contains(Players.local()))
					.move();
			return ReactionGenerator.getPredictable();
		}

		Data.scriptStatus = "Venerating Dense Essence";
		GameObject darkAltar = Objects.stream().id(BloodRuneData.DARK_ALTAR).action("Venerate").nearest().firstOrNull();
		if (darkAltar != null)
		{
			if (darkAltar.interact("Venerate"))
			{
				MethodProvider.sleepUntil(LocalPlayer::isAnimating, 5000);
			}
		}

		return ReactionGenerator.getPredictable();
	}
}
