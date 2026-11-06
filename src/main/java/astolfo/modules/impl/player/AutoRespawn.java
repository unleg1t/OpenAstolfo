package astolfo.modules.impl.player;

import astolfo.ClientAccess;
import astolfo.core.events.EventHandler;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.player.update.UpdateEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.Setting;
import net.minecraft.client.gui.GuiGameOver;

public final class AutoRespawn extends Module {
   private final BooleanSetting instant = new BooleanSetting("Instant", "Instantly respawns", false);
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;

   public AutoRespawn(ModuleCategory category) {
      super(category);
      this.setHidden(false);
      this.addSettings(new Setting[]{this.instant});
   }

   private void onUpdate(UpdateEvent updateEvent) {
      boolean flag = this.instant.asBoolean();
      if (!flag || ClientAccess.mc.thePlayer.getHealth() != 0.0F) {
         boolean flag1 = ClientAccess.mc.currentScreen instanceof GuiGameOver;
         if (!flag1 || ((GuiGameOver)ClientAccess.mc.currentScreen).enableButtonsTimer < 20) {
            return;
         }
      }

      ClientAccess.mc.thePlayer.respawnPlayer();
      ClientAccess.mc.displayGuiScreen(null);
   }
}
