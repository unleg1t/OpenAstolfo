package astolfo.modules.impl.other;

import astolfo.core.events.EventHandler;
import astolfo.core.events.EventPriority;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.network.PacketDirection;
import astolfo.core.events.impl.network.PacketEvent;
import astolfo.core.events.impl.render.RenderEvent;
import astolfo.core.events.impl.render.RenderType;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.EnumSetting;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public final class WorldTime extends Module {
   private final EnumSetting<WorldTimeMode> mode = new EnumSetting<>("Mode", "Set time", WorldTimeMode.SUNRISE);
   private long serverWorldTime = -1L;
   private long loopTime;
   @EventHandler(RenderEvent.class)
   private Listener<RenderEvent> onRender = renderEvent -> {
      if (renderEvent.getType() == RenderType.WORLD) {
         mc.theWorld.setWorldTime(this.mode.getValue() == WorldTimeMode.LOOP ? this.loopTime : WorldTimeMode.getTime(this.mode.getValue()));
         if (this.loopTime++ >= 23999L) {
            this.loopTime = 0L;
         }
      }
   };
   @EventHandler(value = PacketEvent.class, priority = EventPriority.LOWEST)
   private Listener<PacketEvent> onPacket = packetEvent -> {
      if (packetEvent.getDirection() == PacketDirection.RECEIVED && packetEvent.getPacket() instanceof S03PacketTimeUpdate) {
         this.serverWorldTime = ((S03PacketTimeUpdate)packetEvent.getPacket()).getWorldTime();
         packetEvent.cancel();
      }
   };

   public WorldTime(ModuleCategory category) {
      super(category);
      this.setHidden(false);
      this.addSettings(this.mode);
   }

   @Override
   public void onDisable() {
      if (mc.theWorld != null && this.serverWorldTime != -1L) {
         mc.theWorld.setWorldTime(this.serverWorldTime);
      }

      this.loopTime = 0L;
      super.onDisable();
   }

   public enum WorldTimeMode {
      DAY(1000),
      NOON(6000),
      SUNSET(12000),
      NIGHT(13000),
      MIDNIGHT(18000),
      SUNRISE(23000),
      LOOP(0);

      private long time;
      private static final WorldTimeMode[] VALUES = new WorldTimeMode[]{DAY, NOON, SUNSET, NIGHT, MIDNIGHT, SUNRISE, LOOP};

      private WorldTimeMode(int i2) {
         this.time = (long)i2;
      }

      public static long getTime(WorldTimeMode worldTimeMode) {
         return worldTimeMode.time;
      }
   }
}
