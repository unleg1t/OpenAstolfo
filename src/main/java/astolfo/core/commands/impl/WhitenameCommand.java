package astolfo.core.commands.impl;

import astolfo.utilities.impl.server.ServerManager;
import astolfo.utilities.impl.server.ServerType;
import astolfo.utilities.impl.client.GameUtil;
import java.util.Collections;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.Vec3;
import astolfo.core.commands.Command;

public final class WhitenameCommand extends Command {
   public WhitenameCommand() {
      super("whitename", "makes your name white in hypixel");
      this.setAliases(Collections.singletonList("wn"));
   }

   @Override
   public void runCommand(String[] strings) {
      if (ServerManager.currentServerType == ServerType.HYPIXEL) {
         if (GameUtil.scoreboardTitleContains("BED WARS")) {
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(mc.thePlayer, new Vec3(0.0, 0.0, 0.0)));
            chat.print("Made your name white, check tab");
         } else {
            chat.print("Only works at bedwars lobbies");
         }
      } else {
         chat.printError("You must be in Hypixel!");
      }
   }
}
