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
		return component.valid() && component.visible();
	}

	@Override
	public int onLoop()
	{
		Chat.clickContinue();
		return ReactionGenerator.getPredictable();
	}

}
