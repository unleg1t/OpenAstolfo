package astolfo.modules.impl.other;

import astolfo.core.events.EventHandler;
import astolfo.core.events.EventState;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.player.update.UpdateEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.Setting;
import org.lwjgl.input.Keyboard;

public class FreeLook extends Module {
   public static final BooleanSetting mouseWheel = new BooleanSetting("Mouse-Wheel", "Works when clicking mouse-wheel", false);
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;

   public FreeLook(ModuleCategory category) {
      super(category);
      this.setHidden(false);
      this.addSettings(new Setting[]{mouseWheel});
   }

   private void onUpdate(UpdateEvent updateEvent) {
      if (updateEvent.getState() == EventState.PRE && !Keyboard.isKeyDown(this.getKey()) && !mouseWheel.getValue()) {
         this.setEnabled(false);
      }
   }
}
