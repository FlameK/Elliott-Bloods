package api;

import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;

import java.util.function.Predicate;

public class CarriedItems
{
	public static boolean contains(Item... items)
	{
		for (Item item : items)
		{
			if (Inventory.stream().contains(item)
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
			if (Inventory.stream().id(itemID).first().valid()
					|| Equipment.stream().id(itemID).first().valid())
			{
				Log.info("We have found the following item: " + itemID);
				return true;
			}
		}
		return false;
	}

	public static boolean contains(Predicate<Item> predicate)
	{
		if (Inventory.stream().filter(predicate).first().valid()
				|| Equipment.stream().filter(predicate).first().valid())
		{
			Log.info("We have found the following item: " + predicate);
			return true;
		}
		return false;
	}


	public static int count(int itemID)
	{
		int count = 0;
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
