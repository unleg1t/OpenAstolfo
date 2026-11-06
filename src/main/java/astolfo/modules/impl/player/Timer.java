package astolfo.modules.impl.player;

import astolfo.ClientAccess;
import astolfo.core.events.EventHandler;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.player.update.UpdateEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.impl.NumberSetting;
import astolfo.core.settings.Setting;
import astolfo.utilities.impl.client.GameUtil;
import astolfo.utilities.impl.math.MathUtil;
import net.minecraft.util.EnumChatFormatting;

public final class Timer extends Module {
   private final NumberSetting<Float> speed = new NumberSetting<>("Speed", "Speed of timer", 1.0F, 0.1F, 10.0F);
   private final BooleanSetting tick = new BooleanSetting("Tick", "Dynamic timer", false);
   private final BooleanSetting onlyMoving = new BooleanSetting("Only-Moving", "Only when moving", true);
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;

   public Timer(ModuleCategory category) {
      super(category);
      this.addSettings(new Setting[]{this.tick, this.speed, this.onlyMoving});
   }

   @Override
   public void onDisable() {
      ClientAccess.mc.timer.timerSpeed = 1.0F;
      super.onDisable();
      ClientAccess.mc.timer.timerSpeed = 1.0F;
   }

   @Override
   public void updateDisplayName() {
      this.setDisplayName(this.getName() + " " + EnumChatFormatting.GRAY + MathUtil.ONE_DECIMAL.format(this.speed.getValue()));
      super.updateDisplayName();
   }

   private void onUpdate(UpdateEvent updateEvent) {
      this.updateDisplayName();
      boolean flag = this.onlyMoving.getValue();
      if (flag && !GameUtil.isMoving(ClientAccess.mc.thePlayer)) {
         ClientAccess.mc.timer.timerSpeed = 1.0F;
      } else {
         boolean flag1 = this.tick.getValue();
         if (flag1) {
            if (ClientAccess.mc.thePlayer.ticksExisted % 2 == 0) {
               ClientAccess.mc.timer.timerSpeed = this.speed.getValue();
            } else {
               ClientAccess.mc.timer.timerSpeed = 1.0F;
            }
         } else {
            ClientAccess.mc.timer.timerSpeed = this.speed.getValue();
         }
      }
   }
}
