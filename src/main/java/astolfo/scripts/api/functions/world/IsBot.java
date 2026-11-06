package astolfo.scripts.api.functions.world;

import astolfo.scripts.api.WorldLibrary;
import astolfo.modules.impl.hidden.BotDetector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public final class IsBot extends OneArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue) {
      if (!luaValue.isnil() && luaValue.isnumber() && luaValue.isint()) {
         Entity entity = WorldLibrary.entitiesById.get(luaValue.checkint());
         if (entity instanceof EntityLivingBase) {
            return valueOf(BotDetector.bots.contains(entity));
         }
      }

      return NIL;
   }
}
