package astolfo.scripts.api.functions.http;

import astolfo.api.auth.AuthUtil;
import astolfo.scripts.ScriptManager;
import astolfo.utilities.impl.client.HttpRequest;
import astolfo.utilities.impl.client.HttpUtil;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public final class Get extends OneArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue) {
      boolean flag = ScriptManager.unsafeFunctions.isEnabled();
      if (!flag) {
         return valueOf(false);
      } else {
         boolean flag1 = luaValue.isstring();
         if (!flag1) {
            return valueOf(false);
         } else {
            HttpRequest httprequest = new HttpRequest().setUrl(luaValue.checkjstring());
            httprequest.setHeaderKeys("Astolfo-User");
            httprequest.setHeaderValues(AuthUtil.getHwid());
            httprequest.setUserAgent("Astolfo-Client");
            httprequest.setAccept("");
            httprequest.setContentType("");
            String s = HttpUtil.get(httprequest);
            return valueOf(s);
         }
      }
   }
}
