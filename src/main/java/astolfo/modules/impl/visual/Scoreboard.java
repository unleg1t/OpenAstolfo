package astolfo.modules.impl.visual;

import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.impl.NumberSetting;
import astolfo.core.settings.Setting;

public final class Scoreboard extends Module {
   public static final NumberSetting<Float> position = new NumberSetting<>("Position", "Position of scoreboard", 150.0F, -200.0F, 200.0F);
   public static final BooleanSetting remove = new BooleanSetting("Remove", "Removes scoreboard", false);

   public Scoreboard(ModuleCategory category) {
      super(category);
      this.addSettings(new Setting[]{position, remove});
      this.setHidden(false);
   }
}
