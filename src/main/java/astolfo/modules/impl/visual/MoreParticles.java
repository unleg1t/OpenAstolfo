package astolfo.modules.impl.visual;

import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.NumberSetting;
import astolfo.core.settings.Setting;

public class MoreParticles extends Module {
   public static final NumberSetting<Integer> enchantAmount = new NumberSetting<>("Enchant-Amount", "Amount of particles displayed", 1, 0, 20);
   public static final NumberSetting<Integer> criticalAmount = new NumberSetting<>("Critical-Amount", "Amount of particles displayed", 1, 0, 20);

   public MoreParticles(ModuleCategory category) {
      super(category);
      this.addSettings(new Setting[]{enchantAmount, criticalAmount});
   }
}
