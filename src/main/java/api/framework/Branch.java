package api.framework;

import api.Random;
import org.powbot.api.Condition;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class Branch extends Leaf
{
	public final List<Leaf> children;

	public Branch()
	{
		this.children = new LinkedList<>();
	}

	public Branch addLeafs(Leaf... leaves)
	{
		Collections.addAll(this.children, leaves);
		return this;
	}

	@Override
	public int onLoop()
	{
		return children.stream()
				.filter(c -> Objects.nonNull(c) && c.isValid())
				.findAny()
				.map(tLeaf ->
				{
					Tree.currentBranch = this.getClass().getSimpleName();
					Tree.currentLeaf = tLeaf.getClass().getSimpleName();
					if (!tLeaf.isStarted())
					{
						tLeaf.onStart();
						tLeaf.setStarted();
						return 0;
					}
					return tLeaf.onLoop();
				}).orElseGet(() ->
				{
					Condition.sleep(Random.asInt(300, 600));
					return 0;
				});
	}
}
