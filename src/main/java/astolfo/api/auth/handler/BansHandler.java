package astolfo.api.auth.handler;

import astolfo.api.auth.net.PacketOutput;
import astolfo.api.auth.packet.BansPacket;
import astolfo.api.auth.packet.BansPacketType;
import astolfo.api.auth.packet.PacketId;
import astolfo.core.commands.impl.BansCommand;
import net.minecraft.util.EnumChatFormatting;

public class BansHandler extends PacketHandler<BansPacket> {
   public BansHandler() {
      super(PacketId.BANS);
   }

   public void handle(BansPacket bansPacket, PacketOutput packetOutput) {
      if (bansPacket.type == BansPacketType.B) {
         try {
            int i = Integer.parseInt(bansPacket.bans);
            chat.createBuilder(false, false)
               .enableLinkDetection()
               .append("[BANS] ")
               .bold()
               .color(EnumChatFormatting.RED)
               .append(
                  getModeName(bansPacket.mode)
                     + " has "
                     + i
                     + (i <= 1 && i != 0 ? " ban" : " bans")
                     + " in the last "
                     + (BansCommand.hours == 1 ? "hour" : BansCommand.hours + " hours")
               )
               .build()
               .send();
         } catch (NumberFormatException numberFormatException) {
         }
      }
   }

   public static String getModeName(int i) {
      switch (i) {
         case 1:
            return "Skywars";
         case 2:
            return "Bedwars";
         case 3:
            return "UHC";
         case 4:
            return "MegaWalls";
         case 5:
            return "Pit";
         default:
            return "Hypixel";
      }
   }
}
