package astolfo.core.events.impl.client;

import astolfo.core.events.Event;
import astolfo.core.events.EventState;

public class TickEvent implements Event {
   private EventState state;

   public TickEvent(EventState eventState) {
      this.state = eventState;
   }

   public EventState getState() {
      return this.state;
   }
}
