package api;

import api.framework.Leaf;
import org.powbot.api.Input;
import org.powbot.api.Point;
import org.powbot.api.Rectangle;
import org.powbot.api.rt4.Chat;
import org.powbot.api.rt4.Component;

public class ContinueDialogue extends Leaf
{

	@Override
	public boolean isValid()
	{
		Component component = Chat.chatComponent();
		return component.visible() && component.visible();
	}

	@Override
	public int onLoop()
	{
		clickContinue();
		return ReactionGenerator.getPredictable();
	}

	public static void clickContinue()
	{
		Log.info("Dialog.clickContinue (start)");
		Component component = Chat.chatComponent();
		if (component.valid() && component.visible())
		{
			Log.info("Chat component: " + component + " - " + component.text() + " - " + component.actions());
			Input.tap(getRandomPointInRectangle(component.boundingRect()));
		}
		Log.info("Dialog.clickContinue (end)");
	}

	private static Point getRandomPointInRectangle(Rectangle rectangle)
	{
		int left = rectangle.getX() + 5;
		int right = rectangle.getX() + rectangle.getWidth() - 5;
		int bottom = rectangle.getY() + 5;
		int top = rectangle.getY() + rectangle.getHeight() - 5;
		int x = left + Random.asInt(0, right - left + 1);
		int y = bottom + Random.asInt(0, top - bottom + 1);
		return new Point(x, y);
	}
}
