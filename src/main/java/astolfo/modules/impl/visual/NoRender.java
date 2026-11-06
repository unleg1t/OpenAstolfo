package astolfo.modules.impl.visual;

import astolfo.core.events.CancellableEvent;
import astolfo.core.events.EventHandler;
import astolfo.core.events.EventPriority;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.player.state.PushOutOfBlocksEvent;
import astolfo.core.events.impl.render.RenderEvent;
import astolfo.core.events.impl.render.RenderType;
import astolfo.core.events.impl.render.SetupTerrainEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.BooleanSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;

public final class NoRender extends Module {
   public static final BooleanSetting smart = new BooleanSetting("Smart", "smart entity rendering hack", true);
   private final BooleanSetting tnt = new BooleanSetting("TNT", "Removes Tnt", true);
   public static final BooleanSetting visGraph = new BooleanSetting("Vis-Graph", "", true);
   public static final BooleanSetting culling = new BooleanSetting("Culling", "disables culling", true);
   @EventHandler(value = RenderEvent.class, priority = EventPriority.LOWEST)
   private Listener<RenderEvent> onRender = renderEvent -> {
      if (this.tnt.getValue() && renderEvent.getType() == RenderType.WORLD) {
         for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityTNTPrimed) {
               mc.theWorld.removeEntity(entity);
            }
         }
      }
   };
   @EventHandler(SetupTerrainEvent.class)
   private Listener<SetupTerrainEvent> onSetupTerrain = setupTerrainEvent -> {
      if (culling.getValue()) {
         setupTerrainEvent.cancel();
      }
   };
   @EventHandler(PushOutOfBlocksEvent.class)
   private Listener<PushOutOfBlocksEvent> onPushOutOfBlocks = CancellableEvent::cancel;

   public NoRender(ModuleCategory category) {
      super(category);
      this.addSettings(smart, visGraph, this.tnt, culling);
      this.setHidden(false);
   }

   @Override
   public void onEnable() {
      mc.renderGlobal.loadRenderers();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      mc.renderGlobal.loadRenderers();
      super.onDisable();
   }
}
