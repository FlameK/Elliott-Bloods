package behaviour.bloodRunes.behaviour;

import api.ReactionGenerator;
import api.data.Data;
import api.framework.Leaf;
import behaviour.bloodRunes.data.BloodRuneData;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;

public class CreateEssenceFragments extends Leaf
{

	private boolean shouldValidateLeaf = false;

	@Override
	public boolean isValid()
	{
		return createAtDarkAltar()
				|| createAtBloodAltar()
				|| shouldValidateLeaf;
	}

	@Override
	public int onLoop()
	{
		Data.scriptStatus = "Create Dark Fragments";
		if (Inventory.stream().id(BloodRuneData.DARK_ESSENCE_BLOCK).count() > 0)
		{
			shouldValidateLeaf = true;
		}
		else
		{
			shouldValidateLeaf = false;
			return ReactionGenerator.getPredictable();
		}

		Item chisel = Inventory.stream().id(BloodRuneData.CHISEL).first();
		Item essence = Inventory.stream().id(BloodRuneData.DARK_ESSENCE_BLOCK).first();

		if (chisel.valid()
				&& Inventory.selectedItem().id() == -1
				&& Inventory.stream().id(BloodRuneData.DARK_ESSENCE_BLOCK).count() > 0)
		{
			Data.scriptStatus = "Selecting Chisel";
			chisel.finteract("Use");
		}
		else if (Inventory.selectedItem().id() == chisel.id())
		{
			Data.scriptStatus = "Using Chisel on Dark Essence Block";
			essence.finteract("Use");
		}
		return ReactionGenerator.getLowPredictable();
	}

	private boolean createAtDarkAltar()
	{
		return BloodRuneData.DARK_ALTAR_AREA.contains(Players.local())
				&& Inventory.stream().id(BloodRuneData.DARK_ESSENCE_BLOCK).count() >= 24
				&& Inventory.isFull();
	}

	private boolean createAtBloodAltar()
	{
		return BloodRuneData.BLOOD_RUNE_ALTAR_AREA.contains(Players.local())
				&& Inventory.stream().id(BloodRuneData.DARK_ESSENCE_BLOCK).count() == 23
				&& !Inventory.isFull();
	}
}
