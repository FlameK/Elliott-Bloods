package api;

import java.util.Random;

public class ReactionGenerator
{
	private static final Random random = new Random();

	public static double nextGaussian(double mu, double sigma)
	{
		return mu + random.nextGaussian() * sigma;
	}

	public static double nextExponential(double lambda)
	{
		return -Math.log(1 - random.nextDouble()) / lambda;
	}

	public static double nextExGaussian(double mu, double sigma, double lambda)
	{
		return nextGaussian(mu, sigma) + nextExponential(lambda);
	}

	public static double nextUniform(double minimum, double maximum)
	{
		return random.nextDouble() * (maximum - minimum) + minimum;
	}

	public static long nextReactionTime(double mu, double sigma, double lambda, double p, double minimum, double maximum)
	{
		if (random.nextDouble() < p)
		{
			return (long) nextUniform(minimum, maximum);
		}

		double exGaussian = nextExGaussian(mu, sigma, lambda);
		if (exGaussian < minimum || exGaussian > maximum)
		{
			return nextReactionTime(mu, sigma, lambda, p, minimum, maximum);
		}

		return (long) exGaussian;
	}

	public static int getPredictable()
	{
		return (int) nextReactionTime(30, 10, 0.02, 0.01, 30, 500);
	}

}
