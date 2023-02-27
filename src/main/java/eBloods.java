import api.ContinueDialogue;
import api.MethodProvider;
import api.ZoomOut;
import api.framework.Tree;
import api.data.Data;
import behaviour.bloodRunes.behaviour.*;
import behaviour.bloodRunes.data.BloodRuneData;
import lombok.Getter;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.api.script.paint.TrackInventoryOption;

@ScriptManifest(name = "eBloods", description = "Creates Blood runes in Arceuus", version = "v0.1")
public class eBloods extends AbstractScript
{

	@Getter
	private static final Tree tree = new Tree();

	@Override
	public void onStart()
	{

		addPaint(PaintBuilder.newBuilder()
				.x(40)
				.y(45)
				.trackSkill(Skill.Runecrafting)
				.addString("Status: ", () -> Data.scriptStatus)
				.addString("Current Branch: ", () -> Tree.currentBranch)
				.addString("Current Leaf: ", () -> Tree.currentLeaf)
				.trackInventoryItem(BloodRuneData.BLOOD_RUNE, "Blood Runes Made: ", TrackInventoryOption.QuantityChangeIncOny)
				.trackInventoryItem(BloodRuneData.BLOOD_RUNE, "Profit: ", TrackInventoryOption.Price)
				.build());


		tree.addBranches(
				new ZoomOut(),
				new ContinueDialogue(),
				new ActivateBloodEssence(),
				new CreateEssenceFragments(),
				new CreateBloodRunes(),
				new NavigateToBloodAltar(),
				new NavigateToDarkAltar(),
				new ChipDenseRunestone(),
				new NavigateToDenseEssence()
		);
	}

	@Override
	public void poll()
	{
		MethodProvider.sleep(tree.onLoop());
	}

	public static void main(String[] args)
	{
		// Start your script with this function. Make sure your device is connected via ADB, and only one is connected
		new eBloods().startScript();
	}

}
