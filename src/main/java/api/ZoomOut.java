package api;

import api.data.Data;
import api.framework.Leaf;
import org.powbot.api.rt4.*;

public class ZoomOut extends Leaf
{
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
				MethodProvider.sleepUntil(() -> Game.tab() == Game.Tab.SETTINGS, 200, 50);
			}
			return ReactionGenerator.getPredictable();
		}

		Component slider = Components.stream().action("Restore Default Zoom").first();
		if (slider.valid() && slider.visible())
		{
			Data.scriptStatus = "Zooming Out";
			Camera.moveZoomSlider(1.0);
			MethodProvider.sleepUntil(() -> Camera.getZoom() <= 4, 200, 50);
			return ReactionGenerator.getPredictable();
		}

		Component displayWid = Widgets.widget(116).component(112);
		if (displayWid.valid() && displayWid.visible() && displayWid.click())
		{
			Data.scriptStatus = "Opening Device Tab";
			MethodProvider.sleepUntil(
					() -> Components.stream().action("Restore Default Zoom").first().visible()
					, 200, 50);
		}

		return ReactionGenerator.getPredictable();
	}
}
