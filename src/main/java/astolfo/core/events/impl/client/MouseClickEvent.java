package astolfo.core.events.impl.client;

import astolfo.core.events.CancellableEvent;

public class MouseClickEvent extends CancellableEvent {
   public int button;

   public MouseClickEvent(int i) {
      this.button = i;
   }
}
