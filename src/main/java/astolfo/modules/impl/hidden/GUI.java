package astolfo.modules.impl.hidden;

import astolfo.ClientAccess;
import astolfo.api.auth.DisconnectHandler;
import astolfo.core.gui.clickgui.GuiMode;
import astolfo.core.gui.clickgui.defaultgui.DefaultClickGuiScreen;
import astolfo.core.gui.clickgui.sidebar.SidebarClickGuiScreen;
import astolfo.core.gui.clickgui.transparent.TransparentClickGuiScreen;
import astolfo.core.gui.screen.AuthReconnectScreen;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.modules.impl.visual.HUD;

public final class GUI extends Module {
   public static SidebarClickGuiScreen sidebarGui = new SidebarClickGuiScreen();
   public static TransparentClickGuiScreen transparentGui = new TransparentClickGuiScreen();
   public static DefaultClickGuiScreen defaultGui = new DefaultClickGuiScreen();

   public GUI(ModuleCategory category) {
      super(category);
      this.setKey(54);
   }

   @Override
   public void onEnable() {
      if (DisconnectHandler.disconnected) {
         ClientAccess.mc.displayGuiScreen(new AuthReconnectScreen());
      } else {
         openClickGui();
      }

      super.onEnable();
      this.setEnabled(false);
   }

   public static void openClickGui() {
      switch ((GuiMode)HUD.gui.getSetting("Mode").getValue()) {
         case DEFAULT:
            ClientAccess.mc.displayGuiScreen(defaultGui);
            break;
         case TRANSPARENT:
            ClientAccess.mc.displayGuiScreen(transparentGui);
      }
   }
}
