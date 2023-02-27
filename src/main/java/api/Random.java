package api;

public class Random
{
	public Random()
	{
	}

	public static int loopReturn()
	{
		return asInt(500, 750);
	}

	public static int asInt(int low, int high)
	{
		return (int) (Math.random() * (double) (high + 1 - low) + (double) low);
	}

	public static long asLong(long low, long high)
	{
		return (long) (Math.random() * (double) (high + 1L - low) + (double) low);
	}

	public static short asShort(short low, short high)
	{
		return (short) ((int) (Math.random() * (double) (high + 1 - low) + (double) low));
	}

	public static boolean asBoolean()
	{
		return asInt(0, 1) == 1;
	}
}
