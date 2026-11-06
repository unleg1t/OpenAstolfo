package astolfo.core.commands.impl;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.modules.Module;
import astolfo.modules.ModuleInfo;
import astolfo.modules.ModuleManager;
import java.util.Collections;
import astolfo.core.commands.Command;

public final class ShowCommand extends Command {
   public ShowCommand(String string, String string2) {
      super(string, string2);
      this.setAliases(Collections.singletonList("s"));
   }

   @Override
   public void runCommand(String[] strings) {
      int i = strings.length;
      if (i == 2) {
         if (strings[1].equalsIgnoreCase("all")) {
            Astolfo astolfo = ClientAccess.client;

            for (ModuleInfo moduleinfo : astolfo.getModuleManager().getList()) {
               if (moduleinfo instanceof Module) {
                  Module module = (Module)moduleinfo;
                  module.setHidden(true);
               }
            }
         } else {
            Astolfo astolfo1 = ClientAccess.client;
            ModuleManager modulemanager1 = astolfo1.getModuleManager();
            String s = strings[1];
            ModuleManager modulemanager = modulemanager1;
            ModuleInfo moduleinfo1 = modulemanager.getByName(s);
            if (moduleinfo1 instanceof Module) {
               ((Module)moduleinfo1).setHidden(true);
            } else {
               ClientAccess.chat.printError("Module not found");
            }
         }
      }
   }
}
