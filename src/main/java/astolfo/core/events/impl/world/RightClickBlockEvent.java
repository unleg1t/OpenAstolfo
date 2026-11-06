package astolfo.core.events.impl.world;

import astolfo.core.events.CancellableEvent;
import astolfo.core.events.EventState;

public class RightClickBlockEvent extends CancellableEvent {
   private EventState state;

   public RightClickBlockEvent(EventState eventState) {
      this.state = eventState;
   }
}
