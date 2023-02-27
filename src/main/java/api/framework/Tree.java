package api.framework;

import java.util.List;
import java.util.stream.Collectors;

public class Tree
{
	public static String currentBranch;
	public static String currentLeaf;
	private final Root root;

	public Tree()
	{
		root = new Root();
	}

	public Leaf addBranches(Leaf... leaves)
	{
		root.addLeafs(leaves);
		return root;
	}

	public List<Leaf> getBranches()
	{
		return root.children;
	}

	public void clear()
	{
		root.children.clear();
	}

	public List<String> getActiveBranches()
	{
		return root.children.stream().map(tLeaf -> tLeaf.getClass().getSimpleName()).collect(Collectors.toList());
	}

	public int onLoop()
	{
		currentBranch = null;
		currentLeaf = null;
		return root.onLoop();
	}
}