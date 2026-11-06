package astolfo.modules.impl.combat;

import astolfo.ClientAccess;
import astolfo.core.events.EventHandler;
import astolfo.core.events.EventState;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.player.state.HitboxEvent;
import astolfo.core.events.impl.player.update.UpdateEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.modules.impl.hidden.BotDetector;
import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.impl.MapSetting;
import astolfo.core.settings.impl.MapSettingBuilder;
import astolfo.core.settings.impl.NumberSetting;
import astolfo.core.settings.Setting;
import astolfo.utilities.impl.client.GameUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;

public final class Hitboxes extends Module {
   public static final NumberSetting<Float> size = new NumberSetting<>("Size", "Hitbox size expansion", 0.3F, 0.1F, 1.0F);
   public static final BooleanSetting wither = new BooleanSetting(
      "Wither", "Disables other players hitboxes if a wither is near and increments the hitbox of it", false
   );
   private final MapSetting checks = new MapSettingBuilder("Checks")
      .ordered()
      .description("entity checks")
      .add("Invisible", new BooleanSetting("Invisible", "Makes invisible players hitbox bigger", false))
      .add("Teams", new BooleanSetting("Teams", "Removes bigger hitboxes from teammates", false))
      .build();
   public static boolean witherNearby = false;
   @EventHandler(HitboxEvent.class)
   private final Listener<HitboxEvent> onHitbox = hitboxEvent -> {
      boolean flag = hitboxEvent.entity instanceof EntityPlayer;
      if (flag) {
         boolean flag1 = witherNearby;
         if (!flag1 || !wither.getValue()) {
            boolean flag2 = GameUtil.isOnSameTeam(ClientAccess.mc.thePlayer, (EntityPlayer)hitboxEvent.entity);
            if (flag2 && this.checks.getSetting("Teams").asBoolean()) {
               return;
            }

            boolean flag3 = GameUtil.isFullyInvisible(hitboxEvent.entity);
            if ((!flag3 || this.checks.getSetting("Invisible").asBoolean()) && !BotDetector.bots.contains((EntityPlayer)hitboxEvent.entity)) {
               hitboxEvent.borderSize = size.getValue();
            }
         }
      }
   };
   @EventHandler(UpdateEvent.class)
   private final Listener<UpdateEvent> onUpdate = Hitboxes::onUpdate;

   public Hitboxes(ModuleCategory category) {
      super(category);
      this.addSettings(new Setting[]{size, this.checks, wither});
   }

   private static void onUpdate(UpdateEvent updateEvent) {
      EventState eventstate = updateEvent.getState();
      if (eventstate == EventState.PRE) {
         boolean flag = wither.getValue();
         if (flag) {
            witherNearby = false;

            for (Entity entity : ClientAccess.mc.theWorld.loadedEntityList) {
               boolean flag1 = entity instanceof EntityWither;
               if (flag1) {
                  double d0;
                  int i = (d0 = (double)ClientAccess.mc.thePlayer.getDistanceToEntity(entity) - 3.0) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
                  if (i < 0) {
                     boolean flag2 = GameUtil.isBoss(entity);
                     if (!flag2 || !GameUtil.isOnSameTeam(ClientAccess.mc.thePlayer, (EntityLivingBase)entity)) {
                        witherNearby = true;
                        break;
                     }
                  }
               }
            }
         }
      }
   }
}
