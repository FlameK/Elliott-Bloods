package behaviour.bloodRunes.behaviour;


import api.ReactionGenerator;
import api.data.Data;
import api.framework.Leaf;
import behaviour.bloodRunes.data.BloodRuneData;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class CreateBloodRunes extends Leaf
{
	@Override
	public boolean isValid()
	{
		return BloodRuneData.BLOOD_RUNE_ALTAR_AREA.contains(Players.local())
				&& Inventory.stream().id(BloodRuneData.DARK_ESSENCE_FRAGMENTS).count() > 0;
	}

	@Override
	public int onLoop()
	{

		Item chisel = Inventory.stream().id(BloodRuneData.CHISEL).first();
		if (Inventory.selectedItem().id() > -1)
		{
			Data.scriptStatus = "Deselecting Chisel";
			chisel.fclick();
		}

		Data.scriptStatus = "Creating Blood Runes";
		int bloodRuneCount = Inventory.stream().id(BloodRuneData.BLOOD_RUNE).first().getStack();
		GameObject altar = Objects.stream().id(BloodRuneData.BLOOD_ALTAR_ID).action("Bind").first();

		if (altar.valid() && altar.finteract("Bind"))
		{
			Condition.wait(() -> Inventory.stream().id(BloodRuneData.BLOOD_RUNE).first().getStack() > bloodRuneCount, 500, 10);
		}

		return ReactionGenerator.getPredictable();
	}
}
