package api;

import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;

import java.util.function.Predicate;

public class OwnedItems
{
	public static boolean contains(Item... items)
	{
		for (Item item : items)
		{
			if (Bank.stream().contains(item)
					|| Inventory.stream().contains(item)
					|| Equipment.stream().contains(item))
			{
				Log.info("We have found the following item: " + item);
				return true;
			}
		}
		return false;
	}

	public static boolean contains(int... itemsIDs)
	{
		for (int itemID : itemsIDs)
		{
			if (Bank.stream().id(itemID).first() != null
					|| Inventory.stream().id(itemID).first() != null
					|| Equipment.stream().id(itemID).first() != null)
			{
				Log.info("We have found the following item: " + itemID);
				return true;
			}
		}
		return false;
	}

	public static int count(int itemID)
	{
		int count = 0;
		if (Bank.stream().filtered(item -> item.id() == itemID).count() > 0)
		{
			count += Bank.stream().filtered(item -> item.id() == itemID).count();
		}
		if (Inventory.stream().filtered(item -> item.id() == itemID).count() > 0)
		{
			count += Inventory.stream().filtered(item -> item.id() == itemID).count();
		}
		if (Equipment.stream().filtered(item -> item.id() == itemID).count() > 0)
		{
			count += Equipment.stream().filtered(item -> item.id() == itemID).count();
		}
		return count;
	}

}
