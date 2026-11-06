package astolfo.modules.impl.visual;

import astolfo.core.events.EventHandler;
import astolfo.core.events.EventPriority;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.render.RenderEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;

public final class FardESP extends Module {
   @EventHandler(value = RenderEvent.class, priority = EventPriority.LOWEST)
   private Listener<RenderEvent> onRender = renderEvent -> {};

   public FardESP() {
      super("FardESP", ModuleCategory.VISUALS);
   }
}
