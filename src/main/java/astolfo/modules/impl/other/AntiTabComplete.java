package astolfo.modules.impl.other;

import astolfo.core.events.EventHandler;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.network.PacketDirection;
import astolfo.core.events.impl.network.PacketEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.utilities.impl.misc.RandomUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C14PacketTabComplete;

public final class AntiTabComplete extends Module {
   private final String[] fakeCompletions = new String[]{"hey", "brb", "waddup", "hello", "hi"};
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = this::onPacket;

   public AntiTabComplete(ModuleCategory category) {
      super(category);
      this.setHidden(false);
   }

   private void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getDirection() == PacketDirection.OUTGOING) {
         Packet packet = packetEvent.getPacket();
         if (packet instanceof C14PacketTabComplete) {
            C14PacketTabComplete c14packettabcomplete = (C14PacketTabComplete)packetEvent.getPacket();
            if (c14packettabcomplete.getMessage().startsWith(".")) {
               try {
                  c14packettabcomplete.x(c14packettabcomplete.getMessage().substring(c14packettabcomplete.getMessage().lastIndexOf(" ") + 1));
               } catch (Exception exception) {
                  c14packettabcomplete.x(this.fakeCompletions[RandomUtils.RANDOM.nextInt(this.fakeCompletions.length)] + " ");
               }
            }
         }
      }
   }
}
