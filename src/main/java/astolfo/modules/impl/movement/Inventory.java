package astolfo.modules.impl.movement;

import astolfo.ClientAccess;
import astolfo.core.events.EventHandler;
import astolfo.core.events.EventState;
import astolfo.core.events.Listener;
import astolfo.core.events.impl.network.PacketDirection;
import astolfo.core.events.impl.network.PacketEvent;
import astolfo.core.events.impl.player.update.UpdateEvent;
import astolfo.core.gui.clickgui.defaultgui.DefaultClickGuiScreen;
import astolfo.core.gui.clickgui.sidebar.SidebarClickGuiScreen;
import astolfo.core.gui.clickgui.transparent.TransparentClickGuiScreen;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.Setting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C16PacketClientStatus;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class Inventory extends Module {
   private final int[] movementKeyCodes = new int[]{
      ClientAccess.mc.gameSettings.keyBindForward.getKeyCode(),
      ClientAccess.mc.gameSettings.keyBindBack.getKeyCode(),
      ClientAccess.mc.gameSettings.keyBindRight.getKeyCode(),
      ClientAccess.mc.gameSettings.keyBindLeft.getKeyCode(),
      ClientAccess.mc.gameSettings.keyBindJump.getKeyCode()
   };
   public static final BooleanSetting percentage = new BooleanSetting("Percentage", "shows percentage of each item", true);
   private final BooleanSetting inventory = new BooleanSetting("Inventory+", "lets u have items in the craft thing", false);
   private final BooleanSetting silent = new BooleanSetting("Silent", "Pranks the server", false);
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = this::onPacket;

   public Inventory(ModuleCategory category) {
      super(category);
      this.addSettings(new Setting[]{percentage, this.inventory, this.silent});
      this.setHidden(false);
   }

   private void onPacket(PacketEvent packetEvent) {
      boolean flag = packetEvent.getDirection().equals(PacketDirection.OUTGOING);
      if (flag) {
         boolean flag1 = this.inventory.getValue();
         if (flag1 && packetEvent.getPacket() instanceof C00PacketKeepAlive) {
            packetEvent.cancel();
         }

         boolean flag2 = this.silent.getValue();
         if (flag2 && packetEvent.getPacket() instanceof C16PacketClientStatus) {
            C16PacketClientStatus c16packetclientstatus = (C16PacketClientStatus)packetEvent.getPacket();
            if (c16packetclientstatus.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
               packetEvent.cancel();
            }
         }
      }
   }

   private void onUpdate(UpdateEvent updateEvent) {
      if (updateEvent.getState() == EventState.PRE) {
         boolean flag = ClientAccess.mc.currentScreen instanceof GuiContainer;
         if (!flag
            && !(ClientAccess.mc.currentScreen instanceof DefaultClickGuiScreen)
            && !(ClientAccess.mc.currentScreen instanceof TransparentClickGuiScreen)
            && !(ClientAccess.mc.currentScreen instanceof SidebarClickGuiScreen)
            && !(ClientAccess.mc.currentScreen instanceof GuiInventory)) {
            return;
         }

         boolean flag1 = Mouse.isButtonDown(2);
         if (flag1) {
            Mouse.setGrabbed(true);
            ClientAccess.mc.inGameHasFocus = true;
         } else {
            Mouse.setGrabbed(false);
            ClientAccess.mc.inGameHasFocus = false;
         }

         for (int i : this.movementKeyCodes) {
            KeyBinding.setKeyBindState(i, !Keyboard.areRepeatEventsEnabled() && Keyboard.isKeyDown(i));
         }

         boolean flag2 = this.inventory.getValue();
         if (flag2 && ClientAccess.mc.currentScreen instanceof GuiInventory) {
            ClientAccess.mc.playerController.updateController();
         }
      }
   }
}
