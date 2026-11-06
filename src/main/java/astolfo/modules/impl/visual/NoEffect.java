package astolfo.modules.impl.visual;

import astolfo.ClientAccess;
import astolfo.core.events.EventHandler;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.player.update.UpdateEvent;
import astolfo.core.events.impl.render.HurtCameraEvent;
import astolfo.core.events.impl.render.RenderOverlaysEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.Setting;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.potion.Potion;

public final class NoEffect extends Module {
   private final BooleanSetting hurtCam = new BooleanSetting("Hurt-Cam", "Disables hurtcam effect", true);
   private final BooleanSetting overlay = new BooleanSetting("Overlay", "Disables pumpkin overlay and fire", false);
   private final BooleanSetting weather = new BooleanSetting("Weather", "Disables weather", true);
   private final BooleanSetting potions = new BooleanSetting("Potions", "Disables blindness", true);
   public static final BooleanSetting noFog = new BooleanSetting("No-Fog", "Disables fog", true);
   @EventHandler(HurtCameraEvent.class)
   private Listener<HurtCameraEvent> onHurtCamera = hurtCameraEvent -> {
      if (this.hurtCam.getValue()) {
         hurtCameraEvent.cancel();
      }
   };
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;
   @EventHandler(RenderOverlaysEvent.class)
   private Listener<RenderOverlaysEvent> onRenderOverlays = renderOverlaysEvent -> {
      if (this.overlay.getValue()) {
         renderOverlaysEvent.cancel();
      }
   };

   public NoEffect(ModuleCategory category) {
      super(category);
      this.setHidden(false);
      this.addSettings(new Setting[]{this.hurtCam, this.overlay, this.weather, this.potions, noFog});
   }

   private void onUpdate(UpdateEvent updateEvent) {
      WorldClient worldclient = ClientAccess.mc.theWorld;
      if (worldclient != null && this.weather.getValue()) {
         ClientAccess.mc.theWorld.setRainStrength(0.0F);
      }

      boolean flag = this.potions.getValue();
      if (flag && ClientAccess.mc.thePlayer.isPotionActive(Potion.blindness)) {
         ClientAccess.mc.thePlayer.removePotionEffect(Potion.blindness.id);
      }
   }
}
