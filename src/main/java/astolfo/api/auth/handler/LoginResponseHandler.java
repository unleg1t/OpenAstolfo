package astolfo.api.auth.handler;

import astolfo.api.auth.AuthAccount;
import astolfo.api.auth.net.PacketOutput;
import astolfo.api.auth.packet.LoginResponse;
import astolfo.api.auth.packet.PacketId;
import astolfo.core.events.bus.EventBus;
import astolfo.core.events.bus.InvocationStrategy;
import astolfo.core.events.bus.ListenerFilter;
import astolfo.core.events.bus.MemberScanner;
import astolfo.core.gui.screen.LoginScreen;
import astolfo.modules.ModuleManager;
import java.lang.reflect.Field;
import net.minecraft.client.renderer.EntityRenderer;

public class LoginResponseHandler extends PacketHandler<LoginResponse> {
   public LoginResponseHandler() {
      super(PacketId.LOGIN_RESPONSE);
   }

   public void handle(LoginResponse loginResponse, PacketOutput packetOutput) {
      if (loginResponse.success) {
         String[] strings = loginResponse.message.split(";");
         if (strings.length == 7) {
            if (client.getEventBus() == null) {
               client.setEventBus(
                  EventBus.<Field>builder()
                     .withMemberScanner(MemberScanner.forFields())
                     .withListenerFilter(ListenerFilter.forFields())
                     .withInvocationStrategy(InvocationStrategy.FIELD_REFLECTIVE_INVOCATION)
                     .build()
               );
            }

            ModuleManager.setPositionDivisor(Double.parseDouble(strings[1]));
            ModuleManager.setModelViewMatrixMode(Integer.parseInt(strings[2]));
            mc.mouseHelper.getAuthSession().setAccount(new AuthAccount(strings[3]));
            EntityRenderer.authFovMultiplier = Integer.parseInt(strings[4]);
            EntityRenderer.authClearMask = Integer.parseInt(strings[5]);
            EntityRenderer.authMatrixParam = Integer.parseInt(strings[6]);
         }

         LoginScreen.authenticating = false;
         LoginScreen.loggedIn = true;
      } else {
         LoginScreen.loginResponseMessage = loginResponse.message;
         if (loginResponse.message.equalsIgnoreCase("tokenfail")) {
            mc.autoLoginToken = "";
         }

         LoginScreen.loginFailed = true;
         LoginScreen.authenticating = false;
      }
   }
}
