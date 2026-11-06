package astolfo.scripts.api;

import astolfo.ClientAccess;
import astolfo.scripts.api.functions.world.Angles;
import astolfo.scripts.api.functions.world.Biome;
import astolfo.scripts.api.functions.world.Block;
import astolfo.scripts.api.functions.world.BoundingBox;
import astolfo.scripts.api.functions.world.Burning;
import astolfo.scripts.api.functions.world.DisplayName;
import astolfo.scripts.api.functions.world.Entities;
import astolfo.scripts.api.functions.world.Facing;
import astolfo.scripts.api.functions.world.Health;
import astolfo.scripts.api.functions.world.HeldItem;
import astolfo.scripts.api.functions.world.HurtTime;
import astolfo.scripts.api.functions.world.Inventory;
import astolfo.scripts.api.functions.world.IsBot;
import astolfo.scripts.api.functions.world.IsEnemy;
import astolfo.scripts.api.functions.world.IsFriend;
import astolfo.scripts.api.functions.world.IsInvisible;
import astolfo.scripts.api.functions.world.IsPlayer;
import astolfo.scripts.api.functions.world.IsPotionActive;
import astolfo.scripts.api.functions.world.IsSneaking;
import astolfo.scripts.api.functions.world.MaxHealth;
import astolfo.scripts.api.functions.world.Name;
import astolfo.scripts.api.functions.world.Position;
import astolfo.scripts.api.functions.world.PrevPosition;
import astolfo.scripts.api.functions.world.RefreshChunks;
import astolfo.scripts.api.functions.world.Remove;
import astolfo.scripts.api.functions.world.Riding;
import astolfo.scripts.api.functions.world.SetTimer;
import astolfo.scripts.api.functions.world.Sprinting;
import astolfo.scripts.api.functions.world.TicksExisted;
import astolfo.scripts.api.functions.world.Timer;
import astolfo.scripts.api.functions.world.WidthHeight;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.Entity;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class WorldLibrary extends TwoArgFunction implements ClientAccess {
   public static final Map<Integer, Entity> entitiesById = new HashMap<>();
   private static final ArrayList<LuaValue> entityIdList = new ArrayList<>();
   public static final VarArgFunction entitiesFunction = new Entities();
   public static final VarArgFunction inventoryFunction = new Inventory();
   public static final ThreeArgFunction blockFunction = new Block();
   public static final OneArgFunction nameFunction = new Name();
   public static final OneArgFunction isBotFunction = new IsBot();
   public static final OneArgFunction isFriendFunction = new IsFriend();
   public static final OneArgFunction isEnemyFunction = new IsEnemy();
   public static final OneArgFunction heldItemFunction = new HeldItem();
   public static final OneArgFunction hurtTimeFunction = new HurtTime();
   public static final ZeroArgFunction refreshChunksFunction = new RefreshChunks();
   public static final OneArgFunction setTimerFunction = new SetTimer();
   public static final ZeroArgFunction timerFunction = new Timer();
   public static final OneArgFunction displayNameFunction = new DisplayName();
   public static final OneArgFunction healthFunction = new Health();
   public static final OneArgFunction sprintingFunction = new Sprinting();
   public static final OneArgFunction maxHealthFunction = new MaxHealth();
   public static final OneArgFunction ticksExistedFunction = new TicksExisted();
   public static final OneArgFunction ridingFunction = new Riding();
   public static final VarArgFunction anglesFunction = new Angles();
   public static final VarArgFunction boundingBoxFunction = new BoundingBox();
   public static final VarArgFunction positionFunction = new Position();
   public static final VarArgFunction prevPositionFunction = new PrevPosition();
   public static final VarArgFunction widthHeightFunction = new WidthHeight();
   public static final OneArgFunction isPlayerFunction = new IsPlayer();
   public static final OneArgFunction facingFunction = new Facing();
   public static final ZeroArgFunction biomeFunction = new Biome();
   public static final OneArgFunction isSneakingFunction = new IsSneaking();
   public static final TwoArgFunction isPotionActiveFunction = new IsPotionActive();
   public static final OneArgFunction isInvisibleFunction = new IsInvisible();
   public static final OneArgFunction burningFunction = new Burning();
   public static final OneArgFunction removeFunction = new Remove();

   @Override
   public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
      LuaTable luatable = LuaValue.tableOf();
      luatable.set("timer", timerFunction);
      luatable.set("set_timer", setTimerFunction);
      luatable.set("refresh_chunks", refreshChunksFunction);
      luatable.set("entities", entitiesFunction);
      luatable.set("block", blockFunction);
      luatable.set("name", nameFunction);
      luatable.set("display_name", displayNameFunction);
      luatable.set("held_item", heldItemFunction);
      luatable.set("hurt_time", hurtTimeFunction);
      luatable.set("health", healthFunction);
      luatable.set("sprinting", sprintingFunction);
      luatable.set("facing", facingFunction);
      luatable.set("angles", anglesFunction);
      luatable.set("bounding_box", boundingBoxFunction);
      luatable.set("biome", biomeFunction);
      luatable.set("max_health", maxHealthFunction);
      luatable.set("inventory", inventoryFunction);
      luatable.set("ticks_existed", ticksExistedFunction);
      luatable.set("is_player", isPlayerFunction);
      luatable.set("width_height", widthHeightFunction);
      luatable.set("is_sneaking", isSneakingFunction);
      luatable.set("is_potion_active", isPotionActiveFunction);
      luatable.set("is_invisible", isInvisibleFunction);
      luatable.set("burning", burningFunction);
      luatable.set("is_bot", isBotFunction);
      luatable.set("is_friend", isFriendFunction);
      luatable.set("is_enemy", isEnemyFunction);
      luatable.set("riding", ridingFunction);
      luatable.set("position", positionFunction);
      luatable.set("prev_position", prevPositionFunction);
      luatable.set("remove", removeFunction);
      luaValue2.set("world", luatable);
      return luatable;
   }

   public static ArrayList getEntityIdList() {
      return entityIdList;
   }

   public static boolean F() {
      return true;
   }
}
