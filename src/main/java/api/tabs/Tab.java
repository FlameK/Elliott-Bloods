package api.tabs;

import org.powbot.api.rt4.Component;
import org.powbot.api.rt4.Varpbits;

public enum Tab
{
	GROUPING(46, 42, 37, 7),
	ACCOUNT(47, 43, 38, 8),
	FRIEND(48, 44, 39, 9),
	LOGOUT(49, 45, 33, 10),
	SETTINGS(50, 46, 40, 11),
	EMOTE(51, 47, 41, 12),
	MUSIC(52, 48, 42, 13),
	ATTACK(62, 58, 51, 0),
	SKILL(63, 59, 52, 1),
	QUEST(64, 60, 53, 2),
	INVENTORY(65, 61, 54, 3),
	EQUIPMENT(66, 62, 55, 4),
	PRAYER(67, 63, 56, 5),
	MAGIC(68, 64, 57, 6);

	private final int fixedIndex;
	private final int classicIndex;
	private final int resizeableIndex;
	private final int varClientIntValue;

	Tab(int fixedIndex, int classicIndex, int resizeableIndex, int varClientIntValue)
	{
		this.fixedIndex = fixedIndex;
		this.classicIndex = classicIndex;
		this.resizeableIndex = resizeableIndex;
		this.varClientIntValue = varClientIntValue;

	}

	public boolean isOpen()
	{
		return Varpbits.varpbit(171) == this.varClientIntValue;
	}

	public boolean isDisabled()
	{
		Component widgetChild = this.getWidget();
		return !widgetChild.valid() || !widgetChild.visible();
	}

	public Component getWidget()
	{
//		if (ClientMode.get().equals(ClientMode.FIXED))
//		{
//			return Widgets.getWidgetChild(PARENT_ID_FIXED, this.fixedIndex);
//		}
//
//		if (ClientMode.get().equals(ClientMode.RESIZEABLE_CLASSIC))
//		{
//			return Widgets.getWidgetChild(PARENT_ID_CLASSIC, this.classicIndex);
//		}
//
//		if (ClientMode.get().equals(ClientMode.RESIZEABLE_MODERN))
//		{
//			return Widgets.getWidgetChild(PARENT_ID_RESIZEABLE, this.resizeableIndex);
//		}

		return null;
	}

}
