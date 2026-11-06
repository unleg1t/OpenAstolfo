package astolfo.modules.impl.visual;

import astolfo.core.events.CancellableEvent;
import astolfo.core.events.EventHandler;
import astolfo.core.events.EventPriority;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.render.SetupTerrainEvent;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import org.lwjgl.opengl.GL11;

public final class CaveFinder extends Module {
   @EventHandler(value = SetupTerrainEvent.class, priority = EventPriority.LOWEST)
   private final Listener<SetupTerrainEvent> onSetupTerrain = CancellableEvent::cancel;

   public CaveFinder(ModuleCategory category) {
      super(category);
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

   public static void cullFrontFaces() {
      GL11.glCullFace(1028);
   }

   public static void cullBackFaces() {
      GL11.glCullFace(1029);
   }
}
