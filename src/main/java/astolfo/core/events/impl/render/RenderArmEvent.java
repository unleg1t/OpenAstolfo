package astolfo.core.events.impl.render;

import astolfo.core.events.Event;
import astolfo.core.events.EventState;

public class RenderArmEvent implements Event {
   private EventState state;

   public RenderArmEvent(EventState eventState) {
      this.state = eventState;
   }

   public EventState getState() {
      return this.state;
   }
}
