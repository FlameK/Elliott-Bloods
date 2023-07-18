package behaviour.bloodRunes.behaviour;

import api.ReactionGenerator;
import api.data.Data;
import api.framework.Leaf;
import api.handlers.LocalPlayer;
import behaviour.bloodRunes.data.BloodRuneData;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class NavigateToDenseEssence extends Leaf
{
	@Override
	public boolean isValid()
	{
		return !BloodRuneData.DENSE_ESSENCE_AREA.contains(Players.local())
				&& !Inventory.isFull();
	}

	@Override
	public int onLoop()
	{

		// I've noticed that Movement Builder doesn't always use shortcuts.
		// So I've gone the following way to ensure shortcuts are used to increase efficiency.

		if (BloodRuneData.BLOOD_RUNE_ALTAR_AREA.contains(Players.local()) || BloodRuneData.SHORTCUT_AREA_TOP_OF_SLOPE.contains(Players.local()))
		{
			GameObject denseEssence = Objects.stream().id(BloodRuneData.SHORTCUT_ROCK_W_ID).action("Climb").first();
			if (denseEssence.valid() && denseEssence.interact("Climb"))
			{
				Condition.wait(() -> BloodRuneData.DENSE_ESSENCE_AREA.contains(Players.local()), 100, 25);
				return ReactionGenerator.getPredictable();
			}
			Data.scriptStatus = "Walking to Dense Essence From Blood Altar";
			Movement.builder(BloodRuneData.SHORTCUT_ROCK_TILE_W)
					.setRunMin(15)
					.setRunMax(75)
					.setWalkUntil(() -> Objects.stream().id(BloodRuneData.SHORTCUT_ROCK_W_ID).action("Climb").first().valid())
					.setUseTeleports(false)
					.move();
			return ReactionGenerator.getPredictable();
		}

		if (BloodRuneData.DARK_ALTAR_AREA.contains(Players.local()))
		{
			Data.scriptStatus = "Walking to Dense Essence Dark Altar";
			GameObject rocks = Objects.stream().id(BloodRuneData.ROCK_ID).action("Climb").nearest().first();
			if (!LocalPlayer.isAnimating())
			{
				if (rocks.valid() && rocks.interact("Climb"))
				{
					Condition.wait(() -> !BloodRuneData.DENSE_ESSENCE_AREA.contains(Players.local()), 500, 25);
					return ReactionGenerator.getPredictable();
				}
				Movement.builder(BloodRuneData.SHORTCUT_ROCK_TILE_N)
						.setRunMin(15)
						.setRunMax(75)
						.setWalkUntil(() -> BloodRuneData.SHORTCUT_ROCK_TILE_S.equals(Players.local().tile()))
						.setUseTeleports(false)
						.move();
			}
			return ReactionGenerator.getPredictable();
		}

		Data.scriptStatus = "Walking to Dense Essence";
		Movement.builder(BloodRuneData.DENSE_ESSENCE_AREA.getCentralTile())
				.setRunMin(15)
				.setRunMax(75)
				.setWalkUntil(() -> Objects.stream().name("Dense runestone").action("Chip").first().valid())
				.setUseTeleports(false)
				.move();

		return ReactionGenerator.getPredictable();
	}
}
