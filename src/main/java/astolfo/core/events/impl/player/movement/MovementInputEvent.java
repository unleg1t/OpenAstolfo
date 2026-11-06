package astolfo.core.events.impl.player.movement;

import astolfo.core.events.CancellableEvent;
import astolfo.core.events.Event;
import astolfo.core.events.EventState;

public class MovementInputEvent extends CancellableEvent implements Event {
   private EventState state;

   public MovementInputEvent(EventState eventState) {
      this.state = eventState;
   }

   public EventState getState() {
      return this.state;
   }
}
