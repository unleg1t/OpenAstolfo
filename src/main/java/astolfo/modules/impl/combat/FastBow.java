package astolfo.modules.impl.combat;

import astolfo.core.events.EventHandler;
import astolfo.core.events.EventState;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.player.update.UpdateEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.EnumSetting;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import astolfo.utilities.impl.datatypes.functions.Action;

public final class FastBow extends Module {
   private final EnumSetting<FastBowMode> mode = new EnumSetting<>("Mode", "Set your FastBow mode", FastBowMode.VANILLA);
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = updateEvent -> {
      this.updateDisplayName();
      if (updateEvent.getState() == EventState.PRE && this.mode.getValue() == FastBowMode.VANILLA && this.canFastShoot()) {
         mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
         mc.thePlayer.inventory.getCurrentItem().getItem().onItemRightClick(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer);

         for (int i = 0; i < 20; i++) {
            mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer(true));
         }

         mc.thePlayer
            .sendQueue
            .sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
         mc.thePlayer.inventory.getCurrentItem().getItem().onPlayerStoppedUsing(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer, 10);
      }
   };

   public FastBow(ModuleCategory category) {
      super(category);
      this.addSettings(this.mode);
   }

   @Override
   public void updateDisplayName() {
      this.setDisplayName("Fast Bow " + EnumChatFormatting.GRAY + FastBowMode.displayNameOf(this.mode.getValue()));
      super.updateDisplayName();
   }

   private boolean canFastShoot() {
      return mc.thePlayer.inventory.getCurrentItem() != null
         && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow
         && mc.thePlayer.getHealth() > 0.0F
         && mc.thePlayer.onGround
         && mc.gameSettings.keyBindUseItem.pressed;
   }

   public enum FastBowMode {
      VANILLA("Vanilla");

      private String displayName;
      private static final FastBowMode[] VALUES = new FastBowMode[]{VANILLA};

      private FastBowMode(String string2) {
         this.displayName = string2;
      }

      public static String displayNameOf(FastBowMode fastBowMode) {
         return fastBowMode.displayName;
      }
   }
}
