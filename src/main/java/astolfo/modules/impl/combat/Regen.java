package astolfo.modules.impl.combat;

import astolfo.core.events.EventHandler;
import astolfo.core.events.EventState;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.network.PacketDirection;
import astolfo.core.events.impl.network.PacketEvent;
import astolfo.core.events.impl.player.movement.MotionEvent;
import astolfo.core.events.impl.player.update.UpdateEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.EnumSetting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;

public final class Regen extends Module {
   private final EnumSetting<RegenMode> mode = new EnumSetting<>("Mode", "Set your Regen mode", RegenMode.VANILLA);
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = updateEvent -> this.updateDisplayName();
   @EventHandler(MotionEvent.class)
   private Listener<MotionEvent> onMotion = motionEvent -> {
      if (motionEvent.getState() == EventState.PRE && this.canRegen()) {
         switch ((RegenMode)this.mode.getValue()) {
            case POTION:
               if (mc.thePlayer.getActivePotionEffect(Potion.regeneration) != null) {
                  this.sendRegenPackets();
               }
               break;
            case VANILLA:
               this.sendRegenPackets();
         }
      }
   };
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = packetEvent -> {
      if (packetEvent.getDirection() == PacketDirection.OUTGOING
         && packetEvent.getPacket() instanceof C03PacketPlayer
         && mc.thePlayer != null
         && this.mode.getValue() == RegenMode.WAIT
         && this.canRegen()
         && mc.thePlayer.getHealth() < mc.thePlayer.getMaxHealth()
         && mc.thePlayer.ticksExisted % 2 == 0) {
         mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer(((C03PacketPlayer)packetEvent.getPacket()).isOnGround()));
      }
   };

   public Regen(ModuleCategory category) {
      super(category);
      this.addSettings(this.mode);
   }

   private void sendRegenPackets() {
      if (mc.thePlayer.getHealth() < mc.thePlayer.getMaxHealth()) {
         for (int i = 0; i < mc.thePlayer.getMaxHealth() - mc.thePlayer.getHealth(); i++) {
            mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer(mc.thePlayer.onGround));
         }
      }
   }

   private boolean canRegen() {
      return mc.thePlayer.getFoodStats().getFoodLevel() > 16.0F
         && !mc.thePlayer.isUsingItem()
         && (mc.thePlayer.onGround || mc.thePlayer.isOnLadder() || mc.thePlayer.isInWater());
   }

   @Override
   public void updateDisplayName() {
      this.setDisplayName(this.getName() + " " + EnumChatFormatting.GRAY + RegenMode.displayNameOf(this.mode.getValue()));
      super.updateDisplayName();
   }

   public enum RegenMode {
      VANILLA("Vanilla"),
      POTION("Potion"),
      WAIT("Wait");

      private String displayName;
      private static final RegenMode[] VALUES = new RegenMode[]{VANILLA, POTION, WAIT};

      private RegenMode(String string2) {
         this.displayName = string2;
      }

      public static String displayNameOf(RegenMode regenMode) {
         return regenMode.displayName;
      }
   }
}
