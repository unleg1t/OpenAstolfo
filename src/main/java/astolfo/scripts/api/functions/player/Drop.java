package astolfo.scripts.api.functions.player;

import astolfo.ClientAccess;
import astolfo.utilities.impl.player.InventoryUtil;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public final class Drop extends OneArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue) {
      if (ClientAccess.mc.thePlayer != null && luaValue.isnumber()) {
         InventoryUtil.dropSlot(luaValue.checkint(), 0);
      }

      return NIL;
   }
}
