package astolfo.core.events.impl.client;

import astolfo.core.events.Event;

public class KeyPressEvent implements Event {
   private int key;

   public KeyPressEvent(int i) {
      this.key = i;
   }

   public int getKey() {
      return this.key;
   }
}
