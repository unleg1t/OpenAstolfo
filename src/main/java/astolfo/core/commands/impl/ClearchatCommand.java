package astolfo.core.commands.impl;

import astolfo.ClientAccess;
import java.util.Collections;
import astolfo.core.commands.Command;

public final class ClearchatCommand extends Command {
   public ClearchatCommand(String string, String string2) {
      super(string, string2);
      this.setAliases(Collections.singletonList("clear"));
   }

   @Override
   public void runCommand(String[] strings) {
      ClientAccess.mc.ingameGUI.getChatGUI().clearChatMessages();
   }
}
