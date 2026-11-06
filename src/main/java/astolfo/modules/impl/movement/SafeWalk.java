package astolfo.modules.impl.movement;

import astolfo.ClientAccess;
import astolfo.core.events.EventHandler;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.player.state.SafeWalkEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.Setting;

public final class SafeWalk extends Module {
   private final BooleanSetting air = new BooleanSetting("Air", "Safe walks on air", false);
   @EventHandler(SafeWalkEvent.class)
   private Listener<SafeWalkEvent> onSafeWalk = safeWalkEvent -> {
      if (ClientAccess.mc.thePlayer != null) {
         safeWalkEvent.setSafe(!this.air.getValue());
         safeWalkEvent.cancel();
      }
   };

   public SafeWalk(ModuleCategory category) {
      super(category);
      this.addSettings(new Setting[]{this.air});
   }
}
