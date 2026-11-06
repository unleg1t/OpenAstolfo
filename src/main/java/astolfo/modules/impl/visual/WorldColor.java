package astolfo.modules.impl.visual;

import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.ColorSetting;
import astolfo.core.settings.Setting;
import java.awt.Color;

public class WorldColor extends Module {
   public static final ColorSetting color = new ColorSetting("Color", "Color", Color.GRAY);

   public WorldColor(ModuleCategory category) {
      super(category);
      this.addSettings(new Setting[]{color});
      this.setHidden(false);
   }
}
