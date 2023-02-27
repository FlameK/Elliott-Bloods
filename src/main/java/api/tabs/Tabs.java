package api.tabs;

import org.powbot.api.rt4.Component;

public class Tabs
{
	public static Tab getOpen()
	{
		for (Tab tab : Tab.values())
		{
			if (tab.isOpen())
			{
				return tab;
			}
		}
		return null;
	}

	public static boolean isOpen(Tab tab)
	{
		return tab.isOpen();
	}

	public static boolean isDisabled(Tab tab)
	{
		return tab.isDisabled();
	}


	public static boolean open(Tab tab)
	{
		if (tab.isOpen()) return true;

		Component widgetChild = tab.getWidget();
		if (widgetChild == null || !widgetChild.valid() || widgetChild.hidden() || widgetChild.actions().size() == 0)
		{
			return false;
		}

		for (String action : widgetChild.actions())
		{
			return widgetChild.interact(action);
		}

		return false;
	}
}
