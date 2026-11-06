package astolfo.modules.impl.other;

import astolfo.core.events.EventHandler;
import astolfo.core.events.EventPriority;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.network.PacketDirection;
import astolfo.core.events.impl.network.PacketEvent;
import astolfo.core.events.impl.player.update.UpdateEvent;
import astolfo.core.events.impl.render.BlockOpaqueEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.BooleanSetting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class AntiObsidian extends Module {
   private final BooleanSetting antiSuffocate = new BooleanSetting("Anti-Suffocate", "Prevents blocks from suffocating you", true);
   public static Block headBlock;
   @EventHandler(value = UpdateEvent.class, priority = EventPriority.HIGHEST)
   private Listener<UpdateEvent> onUpdate = updateEvent -> {
      headBlock = getHeadBlock();
      if (headBlock instanceof BlockObsidian) {
         BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
         IBlockState iBlockState = mc.theWorld.getBlockState(blockPos);
         if (mc.playerController.onPlayerDamageBlock(blockPos, EnumFacing.UP) && iBlockState.getBlock() != Blocks.bedrock && iBlockState.getBlock() != Blocks.obsidian) {
            mc.thePlayer.swingItem();
         }
      }
   };
   public static boolean suffocating;
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = packetEvent -> {
      if (packetEvent.getDirection() == PacketDirection.OUTGOING && packetEvent.getPacket() instanceof C03PacketPlayer) {
         if (this.antiSuffocate.getValue()
            && (headBlock instanceof BlockObsidian || headBlock instanceof BlockWorkbench || headBlock instanceof BlockFalling)
            && this.isStandingStill()) {
            suffocating = true;
            packetEvent.cancel();
         } else {
            suffocating = false;
         }
      }
   };
   @EventHandler(BlockOpaqueEvent.class)
   private final Listener<BlockOpaqueEvent> onBlockOpaque = blockOpaqueEvent -> blockOpaqueEvent.setCancelled(false);

   public AntiObsidian(ModuleCategory category) {
      super(category);
      this.setHidden(false);
      this.addSettings(this.antiSuffocate);
   }

   private boolean isStandingStill() {
      boolean bl = mc.thePlayer.isUsingItem();
      boolean bl2 = mc.thePlayer.motionX != 0.0 || !mc.thePlayer.isCollidedVertically || mc.gameSettings.keyBindJump.pressed || mc.thePlayer.motionZ != 0.0;
      return !bl && !bl2;
   }

   public static Block getHeadBlock() {
      if (mc.thePlayer != null && mc.theWorld != null) {
         BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
         return mc.theWorld.getBlockState(blockPos).getBlock();
      } else {
         return null;
      }
   }

   public static boolean isInObsidian() {
      return headBlock instanceof BlockObsidian;
   }
}
