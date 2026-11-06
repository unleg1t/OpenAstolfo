package astolfo.core.events.impl.player.update;

import astolfo.core.events.Event;
import astolfo.core.events.EventState;

public class UpdateEvent implements Event {
   private EventState state;

   public UpdateEvent(EventState eventState) {
      this.state = eventState;
   }

   public EventState getState() {
      return this.state;
   }
}
