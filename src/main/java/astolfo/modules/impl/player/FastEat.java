package astolfo.modules.impl.player;

import astolfo.core.events.EventHandler;
import astolfo.core.events.EventPriority;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.network.PacketDirection;
import astolfo.core.events.impl.network.PacketEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;

public class FastEat extends Module {
   private int unusedIntB;
   private int unusedIntA;
   private boolean unusedFlagA;
   private boolean unusedFlagB;
   @EventHandler(value = PacketEvent.class, priority = EventPriority.HIGH)
   private Listener<PacketEvent> onPacket = packetEvent -> {
      if (packetEvent.getDirection() == PacketDirection.OUTGOING) {
      }
   };

   public FastEat() {
      super("FastEat", ModuleCategory.PLAYER);
   }
}
