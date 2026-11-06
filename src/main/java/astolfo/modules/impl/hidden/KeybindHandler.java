package astolfo.modules.impl.hidden;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.core.events.EventHandler;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.client.KeyPressEvent;
import astolfo.modules.HiddenModule;
import astolfo.modules.Module;
import astolfo.modules.ModuleInfo;
import java.util.List;

public final class KeybindHandler extends HiddenModule {
   @EventHandler(KeyPressEvent.class)
   private Listener<KeyPressEvent> onKeyPress = keyPressEvent -> {
      Astolfo astolfo = ClientAccess.client;
      List list = astolfo.getModuleManager().getList();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         ModuleInfo moduleinfo = (ModuleInfo)list.get(i);
         if (moduleinfo instanceof Module) {
            Module module = (Module)moduleinfo;
            if (module.getKey() == keyPressEvent.getKey()) {
               module.toggle();
            }
         }
      }
   };

   public KeybindHandler() {
      ClientAccess.client.getEventBus().subscribe(this);
   }
}
