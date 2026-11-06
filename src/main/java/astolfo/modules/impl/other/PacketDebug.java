package astolfo.modules.impl.other;

import astolfo.core.events.EventHandler;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.network.PacketDirection;
import astolfo.core.events.impl.network.PacketEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;

public final class PacketDebug extends Module {
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = packetEvent -> {
      if (packetEvent.getDirection() == PacketDirection.OUTGOING) {
      }
   };

   public PacketDebug() {
      super("PacketDebug", ModuleCategory.OTHER);
      this.setHidden(false);
   }
}
