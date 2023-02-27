package api.handlers;

import org.powbot.api.Tile;
import org.powbot.api.rt4.Players;

public class LocalPlayer
{

	public static boolean isAnimating()
	{
		return Players.local().animation() != -1;
	}

	public static boolean isMoving()
	{
		return Players.local().inMotion();
	}

	public static boolean isInteracting()
	{
		return Players.local().interacting().valid();
	}

	public static boolean isInCombat()
	{
		return isInteracting() || Players.local().healthBarVisible();
	}

	public static Tile getTile()
	{
		return Players.local().tile();
	}

}
