package astolfo.modules.impl.hidden;

import astolfo.core.events.EventHandler;
import astolfo.core.events.EventState;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.player.update.UpdateEvent;
import astolfo.core.events.impl.world.WorldLoadEvent;
import astolfo.modules.HiddenModule;
import astolfo.utilities.impl.client.GameUtil;
import java.util.ArrayList;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;

public final class BotDetector extends HiddenModule {
   public static final ArrayList<EntityLivingBase> bots = new ArrayList<>();
   public static final ArrayList<EntityLivingBase> damagedPlayers = new ArrayList<>();
   @EventHandler(WorldLoadEvent.class)
   private Listener<WorldLoadEvent> onWorldLoad = worldLoadEvent -> {
      damagedPlayers.clear();
      bots.clear();
   };
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = updateEvent -> {
      if (updateEvent.getState() == EventState.PRE) {
         if (!bots.isEmpty()) {
            bots.clear();
         }

         for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityPlayer && !bots.contains(entity) && entity != mc.thePlayer && this.isBot((EntityPlayer)entity)) {
               bots.add((EntityLivingBase)entity);
            }
         }
      }
   };

   public BotDetector() {
      client.getEventBus().subscribe(this);
   }

   private boolean isBot(EntityPlayer entityPlayer) {
      if (GameUtil.isOnServer("syuu")) {
         return false;
      }

      if (entityPlayer.getDisplayName().getFormattedText().startsWith("§8[NPC]") && !mc.thePlayer.getDisplayName().getFormattedText().startsWith("§8[NPC]")) {
         return true;
      }

      if (GameUtil.isOnServer("hypixel")) {
         AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer)entityPlayer;
         if (abstractClientPlayer.getLocationSkin().toString().equalsIgnoreCase("minecraft:skins/b2f3fc4516774596bfe5c2e16f661106f115ebf651e1287a7cca43399e8c194")) {
            return true;
         }

         if (abstractClientPlayer.getName().contains("§") || abstractClientPlayer.hasCustomName() && abstractClientPlayer.getCustomNameTag().contains(abstractClientPlayer.getName())) {
            return true;
         }

         if (abstractClientPlayer.getPlayerInfo() != null && abstractClientPlayer.getPlayerInfo().getResponseTime() > 1 && !damagedPlayers.contains(abstractClientPlayer)) {
            if (abstractClientPlayer.hurtTime > 0) {
               damagedPlayers.add(abstractClientPlayer);
               return false;
            }

            return true;
         }

         if (GameUtil.scoreboardTitleContains("PIT")
            && mc.thePlayer.getDisplayName().getUnformattedText().contains("BEAST")
            && !entityPlayer.getName().contains("BEAST")
            && entityPlayer.getDisplayName().getUnformattedText().contains("BEAST")) {
            return true;
         }

         if (GameUtil.scoreboardTitleContains("BED WARS") && !entityPlayer.getDisplayName().getUnformattedText().contains("§")) {
            return true;
         }
      }

      if (GameUtil.isOnServer("mineplex") && !Double.isNaN(entityPlayer.getHealth())) {
         return true;
      } else if (entityPlayer.getUniqueID().toString().startsWith(entityPlayer.getName())) {
         return true;
      } else {
         return !StringUtils.stripControlCodes(entityPlayer.getGameProfile().getName()).equals(entityPlayer.getName())
            ? true
            : entityPlayer.getGameProfile().getId() != entityPlayer.getUniqueID();
      }
   }
}
