/*
 * Decompiled with CFR 0.152.
 */
package astolfo.scripts.api;

import astolfo.ClientAccess;
import astolfo.scripts.api.GetSettingFunction2;
import astolfo.scripts.api.functions.modules.IsModuleOn3;
import astolfo.scripts.api.functions.modules.Modules;
import astolfo.scripts.api.functions.modules.Option3;
import astolfo.scripts.api.functions.modules.Register;
import astolfo.scripts.api.functions.modules.RegisterBoolean2;
import astolfo.scripts.api.functions.modules.RegisterNumber2;
import astolfo.scripts.api.functions.modules.SetOption3;
import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.impl.EnumSetting;
import astolfo.core.settings.impl.NumberSetting;
import astolfo.core.settings.Setting;
import astolfo.core.settings.SettingType;
import astolfo.core.settings.impl.StringSetting;
import java.util.ArrayList;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

public class ModuleManagerLibrary2
extends TwoArgFunction
implements ClientAccess {
    public static final TwoArgFunction registerFunction = new Register();
    public static final OneArgFunction getSettingFunction = new GetSettingFunction2();
    public static final VarArgFunction optionFunction = new Option3();
    public static final VarArgFunction setOptionFunction = new SetOption3();
    public static final OneArgFunction isModuleOnFunction = new IsModuleOn3();
    private static final ArrayList<LuaValue> moduleNames = new ArrayList();
    public static final VarArgFunction modulesFunction = new Modules();
    public static final ThreeArgFunction registerBooleanFunction = new RegisterBoolean2();
    public static final VarArgFunction registerNumberFunction = new RegisterNumber2();
    private static String T;

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        LuaTable luaTable = LuaValue.tableOf();
        luaTable.set("register", (LuaValue)registerFunction);
        luaTable.set("register_boolean", (LuaValue)registerBooleanFunction);
        luaTable.set("register_number", (LuaValue)registerNumberFunction);
        luaTable.set("modules", (LuaValue)modulesFunction);
        luaTable.set("option", (LuaValue)optionFunction);
        luaTable.set("set_option", (LuaValue)setOptionFunction);
        luaTable.set("is_module_on", (LuaValue)isModuleOnFunction);
        luaValue2.set("module_manager", (LuaValue)luaTable);
        return luaTable;
    }

    private static void applyLuaValue(LuaValue luaValue, Setting<?> setting) {
        boolean bl = luaValue.isnumber();
        if (bl) {
            if (setting.getType() == SettingType.NUMBER) {
                NumberSetting numberSetting = (NumberSetting)setting;
                if (numberSetting.valueClass == Integer.class) {
                    numberSetting.setValue(luaValue.toint());
                } else {
                    numberSetting.setValue(Float.valueOf(luaValue.tofloat()));
                }
            } else if (setting.getType() == SettingType.ENUM) {
                if (luaValue.isnumber()) {
                    EnumSetting enumSetting = (EnumSetting)setting;
                    Enum[] enumArray = (Enum[])enumSetting.getValue().getClass().getEnumConstants();
                    try {
                        enumSetting.setValue(enumArray[luaValue.toint() + 1]);
                    }
                    catch (Exception exception) {
                        enumSetting.setValue(enumArray[0]);
                    }
                } else if (luaValue.isstring()) {
                    setting.parse(luaValue.checkjstring());
                }
            }
        } else {
            Setting<?> setting2;
            boolean bl2 = luaValue.isboolean();
            if (bl2) {
                if (setting.getType() == SettingType.BOOLEAN) {
                    BooleanSetting booleanSetting = (BooleanSetting)setting;
                    booleanSetting.set(luaValue.checkboolean());
                }
            } else if (luaValue.isstring() && (setting2 = setting).getType() == SettingType.STRING) {
                StringSetting stringSetting = (StringSetting)setting;
                stringSetting.setValue(luaValue.checkjstring());
            }
        }
    }

    public static void applyLuaValueToSetting(LuaValue luaValue, Setting setting) {
        ModuleManagerLibrary2.applyLuaValue(luaValue, setting);
    }

    public static ArrayList getModuleNames() {
        return moduleNames;
    }
}

