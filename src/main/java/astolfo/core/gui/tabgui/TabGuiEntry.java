package astolfo.core.gui.tabgui;

import astolfo.core.gui.TabGui;

public interface TabGuiEntry {
   String getDisplayText();

   default void onPress() {
   }

   default void onExpand() {
   }

   void handleKeyPress(int var1);

   TabGui.Panel getPanel();
}
