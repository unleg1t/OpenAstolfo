package astolfo.core.commands.impl;

import astolfo.ClientAccess;
import astolfo.modules.impl.exploits.Denicker;
import net.minecraft.util.EnumChatFormatting;
import astolfo.core.commands.Command;

public final class NicklistCommand extends Command {
   public NicklistCommand(String string, String string2) {
      super(string, string2);
   }

   @Override
   public void runCommand(String[] strings) {
      Denicker.nicks
         .forEach(
            (string, string2) -> ClientAccess.chat
                  .createBuilder(false, false)
                  .append("> ")
                  .append(string)
                  .color(EnumChatFormatting.GRAY)
                  .append(": ")
                  .color(EnumChatFormatting.WHITE)
                  .append(string2)
                  .build()
                  .send()
         );
   }
}
