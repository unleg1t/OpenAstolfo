package astolfo.scripts.api.functions.player;

import astolfo.ClientAccess;
import astolfo.modules.impl.player.Scaffold;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class IsOnEdge extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      return ClientAccess.mc.thePlayer != null ? valueOf(Scaffold.isOnEdge()) : valueOf(false);
   }
}
