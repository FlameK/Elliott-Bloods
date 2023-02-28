package behaviour.bloodRunes.behaviour;


import api.MethodProvider;
import api.ReactionGenerator;
import api.data.Data;
import api.framework.Leaf;
import behaviour.bloodRunes.data.BloodRuneData;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;

public class ActivateBloodEssence extends Leaf
{
	@Override
	public boolean isValid()
	{
		return Inventory.stream().id(BloodRuneData.BLOOD_ESSENCE_ACTIVE).isEmpty()
				&& Inventory.stream().id(BloodRuneData.BLOOD_ESSENCE).isNotEmpty();
	}

	@Override
	public int onLoop()
	{
		Data.scriptStatus = "Activating Blood Essence";
		Item bloodEssence = Inventory.stream().id(BloodRuneData.BLOOD_ESSENCE).first();

		if (bloodEssence.valid() && bloodEssence.finteract("Activate"))
		{
			Condition.wait(() -> Inventory.stream().id(BloodRuneData.BLOOD_ESSENCE_ACTIVE).count() > 0, 500, 10);
		}

		return ReactionGenerator.getPredictable();
	}
}
