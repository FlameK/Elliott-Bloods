package behaviour.bloodRunes.behaviour;

import api.ReactionGenerator;
import api.data.Data;
import api.framework.Leaf;
import behaviour.bloodRunes.data.BloodRuneData;
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
		Data.scriptStatus = "Walking to Dense Essence";
		Movement.builder(BloodRuneData.DENSE_ESSENCE_AREA.getRandomTile())
				.setRunMin(15)
				.setRunMax(75)
				.setWalkUntil(() ->
				{
					GameObject denseEssence = Objects.stream().name("Dense runestone").action("Chip").nearest().firstOrNull();
					return denseEssence != null && denseEssence.reachable();
				})
				.move();

		return ReactionGenerator.getPredictable();
	}
}
