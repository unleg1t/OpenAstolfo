package astolfo.core.events.impl.player.movement;

import astolfo.core.events.CancellableEvent;
import net.minecraft.util.Vec3;

public class LookEvent extends CancellableEvent {
   private Vec3 vec;

   public LookEvent(Vec3 vec3) {
      this.vec = vec3;
   }

   public Vec3 getVec() {
      return this.vec;
   }

   public void setVec(Vec3 vec3) {
      this.vec = vec3;
   }
}
