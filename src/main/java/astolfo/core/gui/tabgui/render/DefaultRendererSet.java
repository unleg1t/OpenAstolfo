package astolfo.core.gui.tabgui.render;

import astolfo.Astolfo;
import astolfo.core.gui.TabGui;
import astolfo.core.gui.tabgui.BooleanSettingItem;
import astolfo.core.gui.tabgui.CategoryItem;
import astolfo.core.gui.tabgui.EnumSettingItem;
import astolfo.core.gui.tabgui.MapSettingItem;
import astolfo.core.gui.tabgui.ModuleItem;
import astolfo.core.gui.tabgui.NumberSettingItem;
import astolfo.core.gui.tabgui.Sized;
import astolfo.core.font.CustomFontRenderer;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;

public class DefaultRendererSet implements RendererSet {
   private final Map<Class<? extends Sized>, ElementRenderer<?>> renderers;
   private static String i;

   @Override
   public CustomFontRenderer getFontRenderer() {
      return Astolfo.INSTANCE.getCustomFontRenderer();
   }

   @Override
   public Map<Class<? extends Sized>, ElementRenderer<?>> getRenderers() {
      return this.renderers;
   }

   public DefaultRendererSet() {
      Object object = null;
      ItemRenderer itemrenderer = new ItemRenderer();
      this.renderers = new Builder<Class<? extends Sized>, ElementRenderer<?>>()
         .put(TabGui.Panel.class, new PanelRenderer())
         .put(CategoryItem.class, itemrenderer)
         .put(ModuleItem.class, itemrenderer)
         .put(BooleanSettingItem.class, itemrenderer)
         .put(NumberSettingItem.class, itemrenderer)
         .put(MapSettingItem.class, itemrenderer)
         .put(EnumSettingItem.class, itemrenderer)
         .build();
   }
}
