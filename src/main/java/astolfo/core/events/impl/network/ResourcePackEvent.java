package astolfo.core.events.impl.network;

import astolfo.core.events.CancellableEvent;

public class ResourcePackEvent extends CancellableEvent {
   private String hash;

   public ResourcePackEvent(String string) {
      this.hash = string;
   }

   public String getHash() {
      return this.hash;
   }
}
