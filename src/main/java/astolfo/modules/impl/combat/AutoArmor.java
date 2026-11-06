package astolfo.modules.impl.combat;

import astolfo.core.events.EventHandler;
import astolfo.core.events.EventState;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.network.PacketDirection;
import astolfo.core.events.impl.network.PacketEvent;
import astolfo.core.events.impl.player.movement.MotionEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.impl.NumberSetting;
import astolfo.utilities.impl.player.InventoryUtil;
import astolfo.utilities.impl.misc.Stopwatch;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.util.EnumChatFormatting;
import astolfo.utilities.impl.player.ArmorSlot;

public final class AutoArmor extends Module {
   private final Stopwatch clickTimer = new Stopwatch();
   private int currentArmorType;
   private final NumberSetting<Integer> delay = new NumberSetting<>("Delay", "Delay in milliseconds between moving each item", 250, 0, 1000, "ms");
   private final BooleanSetting drop = new BooleanSetting("Drop", "Drops old armor", true);
   private final BooleanSetting inventory = new BooleanSetting("Inventory", "Only changes armour if in inventory", false);
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = packetEvent -> {
      if (packetEvent.getDirection() == PacketDirection.OUTGOING && packetEvent.getPacket() instanceof C0EPacketClickWindow) {
         this.clickTimer.reset();
      }
   };
   @EventHandler(MotionEvent.class)
   private Listener<MotionEvent> onMotion = motionEvent -> {
      if (motionEvent.getState() == EventState.PRE) {
         if (mc.currentScreen instanceof GuiContainer && !(mc.currentScreen instanceof GuiInventory)) {
            return;
         }

         if (!(mc.currentScreen instanceof GuiInventory) && this.inventory.getValue()) {
            return;
         }

         ArmorSlot armorSlot = null;
         int i = -1;
         int j = 0;

         for (int k = 9; k <= 44; k++) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(k).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
               ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
               ArmorSlot armorSlot2 = getArmorSlotByType(itemArmor.armorType);
               if (armorSlot2 != null && itemArmor.armorType == this.currentArmorType) {
                  int l = InventoryUtil.getArmorScore(itemStack);
                  Slot slot2 = mc.thePlayer.inventoryContainer.getSlot(armorSlot2.slot);
                  int m = InventoryUtil.getArmorScore(slot2.getStack());
                  if (l != m) {
                     if (l > m && l > j) {
                        armorSlot = armorSlot2;
                        i = k;
                        j = l;
                     }
                  } else if (itemStack.hasEffect() && !slot2.getStack().hasEffect()) {
                     armorSlot = armorSlot2;
                     i = k;
                     j = l;
                  }
               }
            }
         }

         if (i >= 0 && this.clickTimer.hasElapsed(this.delay.getValue().intValue())) {
            ItemStack itemStack2 = mc.thePlayer.inventoryContainer.getSlot(armorSlot.slot).getStack();
            if (itemStack2 == null) {
               mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 0, 1, mc.thePlayer);
            } else if (KillAura.target == null) {
               if (this.drop.getValue()) {
                  InventoryUtil.dropSlot(armorSlot.slot, 0);
               } else if (!isInventoryFull()) {
                  mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, armorSlot.slot, 0, 1, mc.thePlayer);
               } else {
                  mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, armorSlot.slot, 0, 4, mc.thePlayer);
               }
            }
         }

         if (this.currentArmorType < 3) {
            this.currentArmorType++;
         } else {
            this.currentArmorType = 0;
         }
      }
   };

   public AutoArmor(ModuleCategory category) {
      super(category);
      this.addSettings(this.delay, this.drop, this.inventory);
   }

   @Override
   public void updateDisplayName() {
      this.setDisplayName("Auto Armor " + EnumChatFormatting.GRAY + this.delay.getValue());
      super.updateDisplayName();
   }

   @Override
   public void onDisable() {
      this.clickTimer.reset();
      super.onDisable();
   }

   public static boolean isInventoryFull() {
      boolean bl = true;

      for (int i = 0; i < 35; i++) {
         if (mc.thePlayer.inventory.getStackInSlot(i) == null) {
            bl = false;
         }
      }

      return bl;
   }

   public static ArmorSlot getArmorSlotByType(int i) {
      for (ArmorSlot armorSlot : ArmorSlot.values()) {
         if (ArmorSlot.armorTypeOf(armorSlot) == i) {
            return armorSlot;
         }
      }

      return null;
   }

   public enum ArmorSlot {
      HELMET(5, 0),
      CHEST_PLATE(6, 1),
      LEGGINGS(7, 2),
      BOOTS(8, 3);

      public int slot;
      private int armorType;
      private static final ArmorSlot[] VALUES = new ArmorSlot[]{HELMET, CHEST_PLATE, LEGGINGS, BOOTS};

      ArmorSlot(int j, int k) {
         this.slot = j;
         this.armorType = k;
      }

      public static int armorTypeOf(ArmorSlot armorSlot) {
         return armorSlot.armorType;
      }
   }
}
