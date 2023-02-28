package api;

import api.data.Data;
import api.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class ZoomOut extends Leaf
{

	private final int DISPLAY_WIDGET_PARENT = 116;
	private final int DISPLAY_WIDGET_CHILD = 112;

	@Override
	public boolean isValid()
	{
		return Camera.getZoom() > 4;
	}

	@Override
	public int onLoop()
	{

		if (Game.tab() != Game.Tab.SETTINGS)
		{
			Data.scriptStatus = "Opening Settings Tab";
			if (Game.tab(Game.Tab.SETTINGS))
			{
				Condition.wait(() -> Game.tab() == Game.Tab.SETTINGS, 50, 5);
			}
			return ReactionGenerator.getPredictable();
		}

		Component slider = Components.stream().action("Restore Default Zoom").first();
		if (slider.valid() && slider.visible())
		{
			Data.scriptStatus = "Zooming Out";
			Camera.moveZoomSlider(1.0);
			Condition.wait(() -> Camera.getZoom() <= 4, 50, 5);
			return ReactionGenerator.getPredictable();
		}

		Component displayWid = Widgets.widget(DISPLAY_WIDGET_PARENT).component(DISPLAY_WIDGET_CHILD);
		if (displayWid.valid() && displayWid.visible() && displayWid.click())
		{
			Data.scriptStatus = "Opening Device Tab";
			Condition.wait(
					() -> Components.stream().action("Restore Default Zoom").first().visible()
					, 50, 150);
		}

		return ReactionGenerator.getPredictable();
	}
}
