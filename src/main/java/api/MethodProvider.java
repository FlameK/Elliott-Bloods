package api;

import org.powbot.api.Condition;
import org.powbot.api.script.ScriptState;
import org.powbot.mobile.script.ScriptManager;

import java.util.Arrays;
import java.util.function.Supplier;

public class MethodProvider
{
	private static long tick;

	public static void incrementTick()
	{
		tick++;
	}

	public static long getTick()
	{
		return tick;
	}

	public static void tickSleep(int amount)
	{
		tickSleep(amount, (int) ReactionGenerator.nextGaussian(75, 50));
	}

	public static boolean tickSleep(int amount, int polling)
	{
		Log.info("tickSleep: " + amount);

		long tickCount = tick + amount;

		long start = System.currentTimeMillis();
		int timeout = amount * 1000;
		while (start + (long) timeout >= System.currentTimeMillis() && isScriptRunning())
		{
			if (tick >= tickCount)
			{
				Log.info("tickSleep finished");
				return true;
			}
			try
			{
				sleep(polling);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		Log.info("tickSleep timeout");

		return false;
	}

	public static boolean tickSleepUntil(Supplier<Boolean> condition, int tickTimeout)
	{
		Log.info("tickSleepUntil: " + tickTimeout);

		long start = System.currentTimeMillis();
		int timeout = tickTimeout * 1000;
		long tickCount = tick + tickTimeout;
		while (((start + (long) timeout >= System.currentTimeMillis()) || (tick < tickCount)) && isScriptRunning())
		{
			if (condition.get())
			{
				Log.info("tickSleepUntil finished");
				return true;
			}

			try
			{
				sleep((int) ReactionGenerator.nextGaussian(100, 50));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		Log.info("tickSleepUntil timeout");

		return false;
	}

	private static boolean isScriptRunning()
	{
		ScriptState[] validScriptStates = new ScriptState[] {
				ScriptState.Starting,
				ScriptState.Started,
				ScriptState.Running
		};
		return Arrays.stream(validScriptStates).anyMatch(scriptState -> scriptState.equals(ScriptManager.INSTANCE.state()));
	}

	public static void sleep(int time)
	{
		Condition.sleep(time);
	}

	public static void sleep(int min, int max)
	{
		Condition.sleep(Random.asInt(min, max));
	}

	public static boolean sleepUntil(Supplier<Boolean> condition, int timeout)
	{
		long start = System.currentTimeMillis();

		while (start + (long) timeout >= System.currentTimeMillis() && isScriptRunning())
		{
			if (condition.get())
			{
				return true;
			}

			try
			{
				sleep(Random.asInt(100, 150));
				if (Runtime.getRuntime().freeMemory() <= 15000000L)
				{
					Runtime.getRuntime().gc();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return false;
	}

	public static boolean sleepUntil(Supplier<Boolean> condition, int timeout, int polling)
	{
		long start = System.currentTimeMillis();

		while (start + (long) timeout >= System.currentTimeMillis() && isScriptRunning())
		{
			if (condition.get())
			{
				return true;
			}

			try
			{
				sleep(polling);
				if (Runtime.getRuntime().freeMemory() <= 15000000L)
				{
					Runtime.getRuntime().gc();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return false;
	}

	public static long timeFromMark(long start)
	{
		return System.currentTimeMillis() - start;
	}
}
