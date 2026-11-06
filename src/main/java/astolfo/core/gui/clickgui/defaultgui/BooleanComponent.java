package astolfo.core.gui.clickgui.defaultgui;

import astolfo.core.gui.clickgui.SettingComponent;
import astolfo.core.gui.component.Container;
import astolfo.modules.ModuleCategory;
import astolfo.modules.impl.visual.HUD;
import astolfo.utilities.impl.render.RenderUtil;
import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.Setting;
import net.minecraft.client.gui.GuiMainMenu;

public class BooleanComponent extends SettingComponent {
   private final ModuleCategory category;
   private final Container ownerContainer;
   private static final String d = "ModuleCategory-Colors";

   public BooleanComponent(ModuleCategory category, Container container, Setting setting, int i, int j) {
      super(container, setting, i, j);
      this.category = category;
      this.ownerContainer = container;
   }

   @Override
   public void onClick(int i) {
      boolean flag = this.ownerContainer.isExpanded();
      if (flag && i == 0) {
         boolean flag1 = this.getBooleanSetting().getValue();
         BooleanSetting booleansetting = this.getBooleanSetting();
         booleansetting.set(!flag1);
      }
   }

   @Override
   public void render(int i, int j) {
      int i1 = HUD.gui.getSetting(d).asBoolean() ? this.category.getColor() : client.getColorSetting().getValue().getRGB();
      if (this.getBooleanSetting().getValue()) {
         RenderUtil.drawRect(
            (double)this.getX(), (double)this.getYPos(), (double)(this.getX() + (float)this.getWidth()), (double)(this.getYPos() + (float)this.getHeight()), i1
         );
      }

      GuiMainMenu.latoBoldFont15.drawString(this.getBooleanSetting().getName(), this.getX() + 3.0F, this.getYPos(), -1);
   }

   private BooleanSetting getBooleanSetting() {
      return (BooleanSetting)this.getSetting();
   }
}
