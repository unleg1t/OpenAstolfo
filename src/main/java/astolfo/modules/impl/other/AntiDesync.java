package astolfo.modules.impl.other;

import astolfo.ClientAccess;
import astolfo.core.events.EventHandler;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.world.DestroyBlockEvent;
import astolfo.core.events.impl.world.RightClickBlockEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.Setting;

public final class AntiDesync extends Module {
   private final BooleanSetting breakSetting = new BooleanSetting("Break", "Prevents ghost blocks when breaking blocks", true);
   private final BooleanSetting place = new BooleanSetting("Place", "Prevents ghost blocks when placing blocks", false);
   @EventHandler(DestroyBlockEvent.class)
   private final Listener<DestroyBlockEvent> onDestroyBlock = destroyBlockEvent -> {
      if (this.breakSetting.getValue()) {
         destroyBlockEvent.cancel();
      }
   };
   @EventHandler(RightClickBlockEvent.class)
   private final Listener<RightClickBlockEvent> onRightClickBlock = this::onHeldItemSync;

   public AntiDesync(ModuleCategory category) {
      super(category);
      this.addSettings(new Setting[]{this.breakSetting, this.place});
      this.setHidden(false);
   }

   private void onHeldItemSync(RightClickBlockEvent rightClickBlockEvent) {
      if (this.place.getValue()) {
         ClientAccess.mc.thePlayer.swingItem();
         rightClickBlockEvent.cancel();
      }
   }
}
