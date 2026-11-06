package astolfo.scripts.api.functions.player;

import astolfo.ClientAccess;
import astolfo.modules.impl.movement.Speed;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class BaseSpeed extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      return (LuaValue)(ClientAccess.mc.thePlayer != null ? valueOf(Speed.getBaseSpeed(1.0, true)) : NIL);
   }
}
