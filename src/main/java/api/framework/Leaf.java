package api.framework;

public abstract class Leaf
{
	private boolean started;

	public abstract boolean isValid();

	public void onStart()
	{

	}

	public abstract int onLoop();

	public void setStarted()
	{
		started = true;
	}

	public boolean isStarted()
	{
		return started;
	}
}
