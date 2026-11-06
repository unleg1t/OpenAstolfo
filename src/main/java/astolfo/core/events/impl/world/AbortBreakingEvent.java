package astolfo.core.events.impl.world;


import astolfo.core.events.CancellableEvent;

public class AbortBreakingEvent extends CancellableEvent {
   public AbortBreakingEvent(boolean bl) {
      this.setCancelled(bl);
   }
}
