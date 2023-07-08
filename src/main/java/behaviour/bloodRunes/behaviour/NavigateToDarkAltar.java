package behaviour.bloodRunes.behaviour;

import api.ReactionGenerator;
import api.data.Data;
import api.framework.Leaf;
import api.handlers.LocalPlayer;
import behaviour.bloodRunes.data.BloodRuneData;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

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
			GameObject rocks = Objects.stream().id(BloodRuneData.ROCK_ID).action("Climb").nearest().first();
			if (!LocalPlayer.isAnimating())
			{
				if (rocks.valid() && rocks.interact("Climb"))
				{
					Condition.wait(() -> !BloodRuneData.DENSE_ESSENCE_AREA.contains(Players.local()), 500, 25);
					return ReactionGenerator.getPredictable();
				}
				Movement.builder(BloodRuneData.SHORTCUT_ROCK_TILE_S)
						.setRunMin(15)
						.setRunMax(80)
						.setWalkUntil(() -> BloodRuneData.SHORTCUT_ROCK_TILE_N.equals(Players.local().tile()))
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
		GameObject darkAltar = Objects.stream().id(BloodRuneData.DARK_ALTAR_ID).action("Venerate").first();
		long darkEssenceCount = Inventory.stream().id(BloodRuneData.DARK_ESSENCE_BLOCK).count();

		if (darkAltar.valid() && darkAltar.interact("Venerate"))
		{
			Condition.wait(() -> Inventory.stream().id(BloodRuneData.DARK_ESSENCE_BLOCK).count() > darkEssenceCount, 500, 10);
		}

		return ReactionGenerator.getPredictable();
	}
}
