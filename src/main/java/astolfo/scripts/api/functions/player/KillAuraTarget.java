package astolfo.scripts.api.functions.player;

import astolfo.ClientAccess;
import astolfo.modules.impl.combat.KillAura;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class KillAuraTarget extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      EntityPlayerSP entityplayersp = ClientAccess.mc.thePlayer;
      if (entityplayersp != null) {
         Entity entity = KillAura.target;
         if (entity != null) {
            return valueOf(KillAura.target.getEntityId());
         }
      }

      return NIL;
   }
}
