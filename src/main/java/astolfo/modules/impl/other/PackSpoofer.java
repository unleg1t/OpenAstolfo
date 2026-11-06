package astolfo.modules.impl.other;

import astolfo.core.events.CancellableEvent;
import astolfo.core.events.EventHandler;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.network.PacketDirection;
import astolfo.core.events.impl.network.PacketEvent;
import astolfo.core.events.impl.network.ResourcePackEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.notifications.NotificationType;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import astolfo.utilities.impl.datatypes.functions.Action;

public final class PackSpoofer extends Module {
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = packetEvent -> {
      if (packetEvent.getDirection() == PacketDirection.RECEIVED && packetEvent.getPacket() instanceof S48PacketResourcePackSend && mc.thePlayer != null) {
         S48PacketResourcePackSend s48PacketResourcePackSend = (S48PacketResourcePackSend)packetEvent.getPacket();
         mc.thePlayer
            .sendQueue
            .addToSendQueue(new C19PacketResourcePackStatus(s48PacketResourcePackSend.getHash() == null ? "" : s48PacketResourcePackSend.getHash(), C19PacketResourcePackStatus.Action.ACCEPTED));
         mc.thePlayer
            .sendQueue
            .addToSendQueue(
               new C19PacketResourcePackStatus(s48PacketResourcePackSend.getHash() == null ? "" : s48PacketResourcePackSend.getHash(), C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED)
            );
         client.getNotificationManager()
            .createNotification("Pack spoofed", String.format("Pack spoofed (%s - %s)", s48PacketResourcePackSend.getURL(), s48PacketResourcePackSend.getHash()), NotificationType.SUCCESS, 3500)
            .show();
         packetEvent.cancel();
      }
   };
   @EventHandler(ResourcePackEvent.class)
   private Listener<ResourcePackEvent> onResourcePack = CancellableEvent::cancel;

   public PackSpoofer(ModuleCategory category) {
      super(category);
      this.setHidden(false);
   }
}
