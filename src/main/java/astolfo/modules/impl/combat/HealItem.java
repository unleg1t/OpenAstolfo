package astolfo.modules.impl.combat;

import net.minecraft.item.ItemStack;

public class HealItem {
   private final ItemStack stack;
   private final int slot;
   private final AutoHeal.HealItemType type;

   public HealItem(AutoHeal.HealItemType healItemType, ItemStack itemStack, int i) {
      this.type = healItemType;
      this.stack = itemStack;
      this.slot = i;
   }

   public int getSlot() {
      return this.slot;
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public AutoHeal.HealItemType getType() {
      return this.type;
   }

   public static AutoHeal.HealItemType typeOf(HealItem healItem) {
      return healItem.type;
   }

   public static ItemStack stackOf(HealItem healItem) {
      return healItem.stack;
   }

   public static int slotOf(HealItem healItem) {
      return healItem.slot;
   }
}
