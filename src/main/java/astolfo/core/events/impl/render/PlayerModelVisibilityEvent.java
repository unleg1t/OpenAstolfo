package astolfo.core.events.impl.render;

import astolfo.core.events.CancellableEvent;
import net.minecraft.entity.Entity;

public class PlayerModelVisibilityEvent extends CancellableEvent {
   private Entity entity;

   public PlayerModelVisibilityEvent(Entity entity) {
      this.entity = entity;
   }

   public Entity getEntity() {
      return this.entity;
   }
}
