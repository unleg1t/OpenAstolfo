package astolfo.scripts.api;

import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.impl.NumberSetting;
import astolfo.core.settings.Setting;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public final class GetSettingFunction2 extends OneArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue) {
      if (luaValue.isstring()) {
         LuaString luastring = (LuaString)luaValue;
         Setting setting = LuaApi.settingsByName.get(luastring.checkjstring());
         if (setting instanceof BooleanSetting) {
            return valueOf(setting.asBoolean());
         } else if (setting instanceof NumberSetting) {
            NumberSetting numbersetting = (NumberSetting)setting;
            return CoerceJavaToLua.coerce(numbersetting.getValue());
         } else {
            return valueOf(true);
         }
      } else {
         return valueOf(false);
      }
   }
}
