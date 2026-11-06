package astolfo.api.account;

import net.minecraft.entity.Entity;

public class AlteningSsl {
   private boolean initialized = false;
   private static Entity[] D;

   public void initialize() {
      if (!this.initialized) {
         this.initialized = true;
      }
   }

   public boolean isInitialized() {
      return this.initialized;
   }

   public void setInitialized(boolean bl) {
      this.initialized = bl;
   }

   public static void y(Entity[] entityArray) {
      D = entityArray;
   }

   public static Entity[] U() {
      return D;
   }

   static {
      y(new Entity[3]);
   }

}
