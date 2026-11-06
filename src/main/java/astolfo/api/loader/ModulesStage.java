package astolfo.api.loader;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.api.auth.AuthAccount;
import astolfo.core.commands.impl.BansCommand;
import astolfo.core.commands.impl.BindCommand;
import astolfo.core.commands.impl.ChatCommand;
import astolfo.core.commands.impl.ClearchatCommand;
import astolfo.core.commands.impl.EnemiesCommand;
import astolfo.core.commands.impl.FinalsCommand;
import astolfo.core.commands.impl.FontCommand;
import astolfo.core.commands.impl.FriendsCommand;
import astolfo.core.commands.impl.HclipCommand;
import astolfo.core.commands.impl.HelpCommand;
import astolfo.core.commands.impl.HideCommand;
import astolfo.core.commands.impl.IteminfoCommand;
import astolfo.core.commands.impl.JumpCommand;
import astolfo.core.commands.impl.LimboCommand;
import astolfo.core.commands.impl.NameCommand;
import astolfo.core.commands.impl.NicklistCommand;
import astolfo.core.commands.impl.ScriptCommand;
import astolfo.core.commands.impl.SettingsCommand;
import astolfo.core.commands.impl.ShowCommand;
import astolfo.core.commands.impl.TeleportCommand;
import astolfo.core.commands.impl.VclipCommand;
import astolfo.core.commands.impl.WaypointsCommand;
import astolfo.core.config.ConfigFiles;
import astolfo.core.events.bus.EventBus;
import astolfo.core.events.bus.InvocationStrategy;
import astolfo.core.events.bus.ListenerFilter;
import astolfo.core.events.bus.MemberScanner;
import astolfo.core.gui.clickgui.defaultgui.BooleanComponent;
import astolfo.core.gui.clickgui.defaultgui.CategoryPanel;
import astolfo.core.gui.clickgui.defaultgui.ColorPicker;
import astolfo.core.gui.clickgui.defaultgui.EnumComponent;
import astolfo.core.gui.clickgui.defaultgui.MapComponent;
import astolfo.core.gui.clickgui.defaultgui.ModuleButton;
import astolfo.core.gui.clickgui.defaultgui.NumberSlider;
import astolfo.core.gui.clickgui.defaultgui.RangeSlider;
import astolfo.core.gui.clickgui.defaultgui.StringComponent;
import astolfo.api.loader.check.ProcessCheckSetup;
import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.modules.ModuleManager;
import astolfo.modules.impl.combat.AimAssist;
import astolfo.modules.impl.combat.AntiFireball;
import astolfo.modules.impl.combat.AutoArmor;
import astolfo.modules.impl.combat.AutoBackstab;
import astolfo.modules.impl.combat.AutoClicker;
import astolfo.modules.impl.combat.AutoHeal;
import astolfo.modules.impl.combat.AutoProjectile;
import astolfo.modules.impl.combat.Criticals;
import astolfo.modules.impl.combat.FastBow;
import astolfo.modules.impl.combat.Hitboxes;
import astolfo.modules.impl.combat.KeepSprint;
import astolfo.modules.impl.combat.KillAura;
import astolfo.modules.impl.combat.Knockback;
import astolfo.modules.impl.combat.Reach;
import astolfo.modules.impl.combat.Regen;
import astolfo.modules.impl.combat.Velocity;
import astolfo.modules.impl.exploits.AntiHunger;
import astolfo.modules.impl.exploits.ChatFilter;
import astolfo.modules.impl.exploits.ConsoleKiller;
import astolfo.modules.impl.exploits.Disabler;
import astolfo.modules.impl.exploits.HideWindow;
import astolfo.modules.impl.exploits.PingSpoofer;
import astolfo.modules.impl.exploits.Plugins;
import astolfo.modules.impl.exploits.RiderGod;
import astolfo.modules.impl.exploits.RogueSign;
import astolfo.modules.impl.exploits.SuicideClip;
import astolfo.modules.impl.hidden.BotDetector;
import astolfo.modules.impl.hidden.ChatCommandHandler;
import astolfo.modules.impl.hidden.GUI;
import astolfo.modules.impl.hidden.HypixelGameTracker;
import astolfo.modules.impl.hidden.HypixelPacketHandler;
import astolfo.modules.impl.hidden.InventoryPacketHandler;
import astolfo.modules.impl.hidden.KeybindHandler;
import astolfo.modules.impl.hidden.ListenerSuspender;
import astolfo.modules.impl.hidden.MiddleClickFriends;
import astolfo.modules.impl.hidden.PlayerListTracker;
import astolfo.modules.impl.hidden.ScoreboardTracker;
import astolfo.modules.impl.hidden.ServerRotationTracker;
import astolfo.modules.impl.hidden.TitleAnnouncer;
import astolfo.modules.impl.movement.AntiFall;
import astolfo.modules.impl.movement.DoubleJump;
import astolfo.modules.impl.movement.Flight;
import astolfo.modules.impl.movement.Inventory;
import astolfo.modules.impl.movement.Jesus;
import astolfo.modules.impl.movement.LegitScaffold;
import astolfo.modules.impl.movement.LongJump;
import astolfo.modules.impl.movement.NoFall;
import astolfo.modules.impl.movement.NoSlow;
import astolfo.modules.impl.movement.SafeWalk;
import astolfo.modules.impl.movement.Sneak;
import astolfo.modules.impl.movement.Speed;
import astolfo.modules.impl.movement.Sprint;
import astolfo.modules.impl.movement.Terrain;
import astolfo.modules.impl.movement.Water;
import astolfo.modules.impl.other.Announcer;
import astolfo.modules.impl.other.Annoy;
import astolfo.modules.impl.other.AntiDesync;
import astolfo.modules.impl.other.AntiFrozen;
import astolfo.modules.impl.other.AntiObsidian;
import astolfo.modules.impl.other.AntiTabComplete;
import astolfo.modules.impl.other.AutoHypixel;
import astolfo.modules.impl.other.FastPlace;
import astolfo.modules.impl.other.FreeLook;
import astolfo.modules.impl.other.FriendProtect;
import astolfo.modules.impl.other.HackerDetector;
import astolfo.modules.impl.other.InfiniteReach;
import astolfo.modules.impl.other.KillSay;
import astolfo.modules.impl.other.NoMouseIntersect;
import astolfo.modules.impl.other.PackSpoofer;
import astolfo.modules.impl.other.Spammer;
import astolfo.modules.impl.other.ThunderNotifier;
import astolfo.modules.impl.other.WorldTime;
import astolfo.modules.impl.player.AutoFish;
import astolfo.modules.impl.player.AutoRespawn;
import astolfo.modules.impl.player.AutoTool;
import astolfo.modules.impl.player.Blink;
import astolfo.modules.impl.player.FastMine;
import astolfo.modules.impl.player.FastUse;
import astolfo.modules.impl.player.Freecam;
import astolfo.modules.impl.player.Fucker;
import astolfo.modules.impl.player.InventoryManager;
import astolfo.modules.impl.player.NoRotate;
import astolfo.modules.impl.player.Nuker;
import astolfo.modules.impl.player.Phase;
import astolfo.modules.impl.player.Scaffold;
import astolfo.modules.impl.player.SpeedBridge;
import astolfo.modules.impl.player.Stealer;
import astolfo.modules.impl.player.Step;
import astolfo.modules.impl.player.Timer;
import astolfo.modules.impl.visual.Animations;
import astolfo.modules.impl.visual.BlockOverlay;
import astolfo.modules.impl.visual.Camera;
import astolfo.modules.impl.visual.CaveFinder;
import astolfo.modules.impl.visual.Chams;
import astolfo.modules.impl.visual.ChunkBorders;
import astolfo.modules.impl.visual.Crosshair;
import astolfo.modules.impl.visual.DamageParticles;
import astolfo.modules.impl.visual.ESP;
import astolfo.modules.impl.visual.FullBright;
import astolfo.modules.impl.visual.HUD;
import astolfo.modules.impl.visual.ItemPhysics;
import astolfo.modules.impl.visual.MoreParticles;
import astolfo.modules.impl.visual.NameProtect;
import astolfo.modules.impl.visual.NoEffect;
import astolfo.modules.impl.visual.NoRender;
import astolfo.modules.impl.visual.NoTitle;
import astolfo.modules.impl.visual.Outline;
import astolfo.modules.impl.visual.PlayerList;
import astolfo.modules.impl.visual.Radar;
import astolfo.modules.impl.visual.Scoreboard;
import astolfo.modules.impl.visual.Skeleton;
import astolfo.modules.impl.visual.StreamerMode;
import astolfo.modules.impl.visual.Tracers;
import astolfo.modules.impl.visual.Trajectories;
import astolfo.modules.impl.visual.Waypoints;
import astolfo.modules.impl.visual.WorldColor;
import astolfo.modules.impl.visual.Xray;
import astolfo.scripts.LuaGlobals;
import astolfo.core.settings.impl.BooleanSetting;
import astolfo.core.settings.impl.ColorSetting;
import astolfo.core.settings.impl.EnumSetting;
import astolfo.core.settings.impl.MapSetting;
import astolfo.core.settings.impl.NumberSetting;
import astolfo.core.settings.Setting;
import astolfo.core.settings.impl.StringSetting;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.passive.EntityPig;

public final class ModulesStage extends AbstractLoadStage {
   private final ProcessCheckSetup processCheckSetup = new ProcessCheckSetup();

   @Override
   public StageAction getAction() {
      return new ModulesStage.RegisterModulesAction(this);
   }

   @Override
   public StageValue getValue() {
      return new ModulesStage.ProcessCheckValue(this);
   }

   public static String decrypt(String string) {
      return decryptWithKey(string, "4N4z6CCb6bvBeeJH");
   }

   public static String decryptWithKey(String string, String string2) {
      if (string != null && !string.isEmpty()) {
         try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(string2.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, secretKeySpec);
            byte[] bytes = cipher.doFinal(DatatypeConverter.parseBase64Binary(string));
            return new String(bytes);
         } catch (NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException value) {
            NullPointerException nullPointerException = new NullPointerException();
            nullPointerException.setStackTrace(new StackTraceElement[0]);
            throw nullPointerException;
         }
      } else {
         return string;
      }
   }

   @Override
   public void setState(int i) {
      this.state = i;
   }

   @Override
   public int getState() {
      return this.state;
   }

   public static ProcessCheckSetup accessProcessCheckSetup(ModulesStage modulesStage) {
      return modulesStage.processCheckSetup;
   }

   public class ProcessCheckValue extends StageValue {
      public ProcessCheckValue(LoadStage loadStage) {
         super(loadStage);
      }

      public EntityPig get() {
         return (EntityPig)ModulesStage.accessProcessCheckSetup(ModulesStage.this).getProcessCheck().checkProcesses().value;
      }
   }

   public class RegisterModulesAction extends StageAction {
      public RegisterModulesAction(LoadStage loadStage) {
         super(loadStage);
      }

      @Override
      public void run() {
         if (ModulesStage.this.getState() == 1) {
            if (ClientAccess.client.getEventBus() == null) {
               ClientAccess.client
                  .setEventBus(
                     EventBus.<Field>builder()
                        .withMemberScanner(MemberScanner.forFields())
                        .withListenerFilter(ListenerFilter.forFields())
                        .withInvocationStrategy(InvocationStrategy.FIELD_REFLECTIVE_INVOCATION)
                        .build()
                  );
            }

            ClientAccess.client.register(121968519176554L, ClientAccess.client.getRelationManager());
            LuaGlobals.init();
            StringBuilder stringBuilder = new StringBuilder();

            String string2;
            try (
               InputStream inputStream = this.getClass().getResourceAsStream("/assets/minecraft/blockstates/a.json");
               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            ) {
               while ((string2 = bufferedReader.readLine()) != null) {
                  stringBuilder.append(string2).append("\n");
               }
            } catch (Exception exception) {
               exception.printStackTrace();
            }

            StringBuilder stringBuilder2 = new StringBuilder();

            String string3;
            try (
               InputStream inputStream2 = this.getClass().getResourceAsStream("/assets/minecraft/shaders/post/yellow.json");
               BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
            ) {
               while ((string3 = bufferedReader2.readLine()) != null) {
                  stringBuilder2.append(string3).append("\n");
               }
            } catch (Exception exception2) {
               exception2.printStackTrace();
            }

            String string4 = ModulesStage.decrypt(stringBuilder.toString());
            String string5 = stringBuilder2.toString();
            ClientAccess.client
               .getModuleManager()
               .add(
                  new ListenerSuspender(),
                  new ScoreboardTracker(),
                  new HypixelGameTracker(),
                  new KillAura(ModuleCategory.COMBAT),
                  new KeybindHandler(),
                  new Velocity(ModuleCategory.COMBAT),
                  new ChatCommandHandler(),
                  new Criticals(ModuleCategory.COMBAT),
                  new BotDetector(),
                  new AutoClicker(ModuleCategory.COMBAT),
                  new PlayerListTracker(),
                  new AutoArmor(ModuleCategory.COMBAT),
                  new InventoryPacketHandler(),
                  new Regen(ModuleCategory.COMBAT),
                  new AutoHeal(ModuleCategory.COMBAT),
                  new FastBow(ModuleCategory.COMBAT),
                  new AutoBackstab(ModuleCategory.COMBAT),
                  new Reach(ModuleCategory.COMBAT),
                  new AimAssist(ModuleCategory.COMBAT),
                  new Knockback(ModuleCategory.COMBAT),
                  new Hitboxes(ModuleCategory.COMBAT),
                  new AntiFireball(ModuleCategory.COMBAT),
                  new KeepSprint(ModuleCategory.COMBAT),
                  new AutoProjectile(
                     ModuleCategory.COMBAT,
                     "local a=0.05;local b=1.8;local c,d,e=1,0,0;local f,g=nil,nil;local h=false;local i,j={},{}local k=false;local l,m,n;local o=false;local p;local q,r;local s,t;local u,v;local w,x;local y,z,A,B;local C;function distance_3d(D,E,F,G,H,I)return math.sqrt((G-D)^2+(H-E)^2+(I-F)^2)end;function distance_2d(D,E,G,H)if D and E and G and H then return math.sqrt((G-D)^2+(H-E)^2)end end;function solve_projectile_3d(a,b,J,K,L)if a and b and J and K and L then local M,N,O=player.position()N=N+player.eye_height()local P=distance_2d(J,L,M,O)local Q=K-N;local R=b^4-a*(a*P^2+2*Q*b^2)if R>=0 then local S=(b^2+math.sqrt(R))/(a*P)local T=(b^2-math.sqrt(R))/(a*P)local U=math.atan(S)local V=math.atan(T)local W=U*180/math.pi;local X=V*180/math.pi;local Y,Z=player.angles_for_cords(J,K,L)Z=0-X;return Y,Z end end end;function time_of_flight(a,b,_,a0,J,K,L)local a1,a2,a3=player.position()return distance_2d(a1,a3,J,L)/(math.cos(_)*b)end;function ground_distance(a4)if is_in_water(a4)then return 0 end;local a5,a6,a7,a8,a9,aa=world.bounding_box(a4)for ab=a6,0,-0.1 do if solid_block(a5,ab,a7)or solid_block(a5,ab,aa)or solid_block(a8,ab,a7)or solid_block(a8,ab,aa)then return a6-ab end end;return nil end;function bezier_midpoints(q,r,s,t,ac)local y,z=math.random(math.min(q,s)-ac,math.max(q,s)+ac),math.random(math.min(r,t)-ac,math.max(r,t)+ac)local A,B=math.random(math.min(q,s)-ac,math.max(q,s)+ac),math.random(math.min(r,t)-ac,math.max(r,t)+ac)if distance_2d(q,r,y,z)>distance_2d(q,r,A,B)then local ad,ae=y,B;y,z=A,B;A,B=ad,ae end;return y,z,A,B end;function cubic_bezier_curve(af,D,E,G,H,ag,ah,ai,aj)local ak=(1-af)^3*D+3*(1-af)^2*af*G+3*(1-af)*af^2*ag+af^3*ai;local al=(1-af)^3*E+3*(1-af)^2*af*H+3*(1-af)*af^2*ah+af^3*aj;if al>90 then al=90 end;if al<-90 then al=-90 end;return ak,al end;function solid_block(P,Q,am)if world.block(P,Q,am)~='tile.air'and world.block(P,Q,am)~='tile.water'and world.block(P,Q,am)~='tile.deadbush'and world.block(P,Q,am)~='tile.reeds'and world.block(P,Q,am)~='tile.tallgrass'and world.block(P,Q,am)~='tile.doublePlant'and world.block(P,Q,am)~='tile.web'and world.block(P,Q,am)~='tile.lava'then return true else return false end end;function is_in_water(a4)if a4==nil then return nil end;local an,ao,ap=world.position(a4)if world.block(an,math.floor(ao),ap)=='tile.water'then return true else return false end end;function is_on_team(aq)if aq==nil then return nil end;local ar=world.entities()for ab=1,#ar do if world.name(ar[ab])==player.name()then display_name=world.display_name(ar[ab])end end;if string.find(display_name,\"\\194\\167\")~=nil then substring=string.sub(display_name,string.find(display_name,\"\\194\\167\"),string.find(display_name,\"\\194\\167\")+2)if string.find(world.display_name(aq),substring)~=nil then return true end;return false end end;function check_fov(a4)local as,at=player.angles_for_cords(world.position(a4))local au,av=player.angles()local aw=math.abs(au-as)if aw<module_manager.option('AutoProjectile','Rotations','FOV')/2 then return true else return false end end;function has_clear_view(a4)local an,ao,ap=world.position(a4)local a5,a6,a7,a8,a9,aa=world.bounding_box(a4)ao=ao+(a9-a6)*module_manager.option('AutoProjectile','Rotations','Aim-Height')local w,x=player.angles_for_cords(an,ao,ap)local ax=player.ray_cast(w,x,160)if ax==1 then return true elseif ax==2 then ax,side_hit,block_x,block_y,block_z,hit_x,hit_y,hit_z=player.ray_cast(w,x,160)if player.distance_to(hit_x,hit_y,hit_z)>player.distance_to_entity(a4)then return true end elseif ax==3 then ax,ray_cast_entity_id=player.ray_cast(w,x,160)if ray_cast_entity_id==a4 then return true end end;return false end;function get_lowest_angle_entity()local ay=nil;local az=42069;for ab=1,#p,1 do local aA=p[ab]local aB,Z=player.angles_for_cords(world.position(aA))local au,av=player.angles()local aw,aC=math.abs(aB-au),math.abs(Z-av)local aD=aw+aC;if aD<az and aA~=player.id()and not world.is_bot(aA)and world.name(aA)~='unknown'and(world.is_player(aA)or not module_manager.option('AutoProjectile','Entities','Player-Only'))and(not module_manager.option('AutoProjectile','Entities','Teams')or not is_on_team(aA))and world.max_health(aA)~=nil and has_clear_view(aA)and check_fov(aA)and not world.is_potion_active(aA,14)and world.hurt_time(aA)<=module_manager.option('AutoProjectile','Entities','Hurt-Time')and player.distance_to_entity(aA)>module_manager.option('AutoProjectile','Min-Distance')and player.distance_to_entity(aA)<module_manager.option('AutoProjectile','Max-Distance')then ay=aA;az=aD end end;return ay end;function get_closest_entity()local aE=nil;local aF=42069;for ab=1,#p,1 do local aA=p[ab]if player.distance_to_entity(aA)<aF and aA~=player.id()and not world.is_bot(aA)and world.name(aA)~='unknown'and(world.is_player(aA)or not module_manager.option('AutoProjectile','Entities','Player-Only'))and(not module_manager.option('AutoProjectile','Entities','Teams')or not is_on_team(aA))and world.max_health(aA)~=nil and has_clear_view(aA)and check_fov(aA)and not world.is_potion_active(aA,14)and world.hurt_time(aA)<=module_manager.option('AutoProjectile','Entities','Hurt-Time')and player.distance_to_entity(aA)>module_manager.option('AutoProjectile','Min-Distance')and player.distance_to_entity(aA)<module_manager.option('AutoProjectile','Max-Distance')then aE=aA;aF=player.distance_to_entity(aA)end end;return aE end;function get_target_entity()if player.kill_aura_target()==nil then if tostring(module_manager.option('Auto-Projectile','Sort-Mode'))==\"ANGLE\"then return get_lowest_angle_entity()else return get_closest_entity()end end end;function predict_vertical_motion(aG,a,b,aH)aG=aG*module_manager.option('AutoProjectile','Prediction','Multiplier')+module_manager.option('AutoProjectile','Prediction','Add')local aI={}for ab=0,aG,1 do b=b-a;b=b*aH;table.insert(aI,b)end;local aJ=0;for ab=1,#aI,1 do aJ=aJ+aI[ab]end;return aJ end;function predict_cords(aA)local M,N,O=player.position()local aK,aL,aM=world.position(aA)local aN,aO,aP=world.prev_position(aA)local aG=0;if aK and aL and aM then if aK and aL and aM and aN and aO and aP and player.distance_to(aN,aO,aP)>player.distance_to(aK,aL,aM)then aG=b*player.distance_to_entity(aA)*1.2+module_manager.option('AutoProjectile','Prediction','Add')end;local Y,Z=solve_projectile_3d(a,b,aK,aL,aM)if Y and Z then local _=-1*Z*math.pi/180;aG=time_of_flight(a,b,_,N-aL,aK,aL,aM)end end;local aQ,aR,aS=0,0,0;if aN and aP then aQ=(aK-aN)*(aG*module_manager.option('AutoProjectile','Prediction','Multiplier')+module_manager.option('AutoProjectile','Prediction','Add'))aS=(aM-aP)*(aG*module_manager.option('AutoProjectile','Prediction','Multiplier')+module_manager.option('AutoProjectile','Prediction','Add'))end;if aO then if ground_distance(aA)and ground_distance(aA)<-1*predict_vertical_motion(aG,0.08,aL-aO,0.98)then aR=aR-ground_distance(aA)else aR=aR+predict_vertical_motion(aG,0.08,aL-aO,0.98)end end;local a5,a6,a7,a8,a9,aa=world.bounding_box(f)if a5 and a6 and a7 and a8 and a9 and aa then aR=aR+(a9-a6)*module_manager.option('AutoProjectile','Rotations','Aim-Height')end;i={}i['ticks']=aG*module_manager.option('AutoProjectile','Prediction','Multiplier')+module_manager.option('AutoProjectile','Prediction','Add')i['x']=aK+aQ;i['y']=aL+aR;i['z']=aM+aS;return aK+aQ,aL+aR,aM+aS end;function has_projectile()if client.gui_name()~='chest'and client.gui_name()~='inventory'then for ab=44,0,-1 do local aT,display_name,aU,aV,aW=player.inventory.item_information(ab)if(aT=='item.egg'or aT=='item.snowball')and(not module_manager.option('AutoProjectile','Hotbar-Only')or ab>=36)then return true end end end;return false end;function get_projectile()if client.gui_name()~='chest'then for ab=44,0,-1 do local aT,display_name,aU,aV,aW=player.inventory.item_information(ab)if(aT=='item.egg'or aT=='item.snowball')and(not module_manager.option('AutoProjectile','Hotbar-Only')or ab>=36)then return ab end end end end;local aX={on_pre_motion=function(aJ)for ab=#j,1,-1 do j[ab]['ticks']=j[ab]['ticks']-1;if j[ab]['ticks']<0 then table.remove(j,ab)end end;p=world.entities()f=get_target_entity()local J,K,L;local Y,Z;if f then J,K,L=predict_cords(f)Y,Z=solve_projectile_3d(a,b,J,K,L)end;if has_projectile()and module_manager.is_module_on('Auto-Rod')or not has_projectile()and not module_manager.is_module_on('Auto-Rod')then end;if c~=1 and input.is_mouse_down(1)or module_manager.is_module_on('Legit-Scaff')or module_manager.is_module_on('Scaffold')or m~=module_manager.option('inventorymanager','Slots','Sword-Slot')then if l~=m and c~=1 and input.is_mouse_down(1)then o=true;player.set_held_item_slot(m-2)o=false end;c,d,e=1,1,0;h,k=false,false end;if c==1 and d<=0 and Y and Z and has_projectile()and ground_distance(player.id())~=nil and ground_distance(player.id())<3 then m=player.held_item_slot()q,r=player.angles()s,t=Y,Z;y,z,A,B=bezier_midpoints(q,r,s,t,module_manager.option('AutoProjectile','Rotations','Randomization'))u,v=player.angles()C=0;c=2 end;if Y and Z and w and x and f and g and f~=g and has_projectile()then q,r=w,x;s,t=Y,Z;y,z,A,B=bezier_midpoints(q,r,s,t,module_manager.option('AutoProjectile','Rotations','Randomization'))C=0;c=2 end;if(c==2 or c==3)and(Y==nil or Z==nil or not has_projectile())then q,r=w,x;if module_manager.option('AutoProjectile','Rotations','Lock-View')then player.set_angles(w,x)else s,t=player.angles()end;y,z,A,B=bezier_midpoints(q,r,s,t,module_manager.option('AutoProjectile','Rotations','Randomization'))C=0;c=4 end;if c==2 and d<=0 and Y and Z and has_projectile()then h,k=false,false;s,t=Y,Z;C=C+module_manager.option('AutoProjectile','Rotations','Speed')if C>1 then C=1 end;w,x=cubic_bezier_curve(C,q,r,y,z,A,B,s,t)aJ.yaw,aJ.pitch=w,x;if module_manager.option('AutoProjectile','Rotations','Lock-View')then player.set_angles(w,x)end;if w==s and x==t then c=3 end end;if c==3 and Y and Z and has_projectile()then if k then player.send_packet(0x08)k=false end;h=true;w,x=Y,Z;aJ.yaw,aJ.pitch=w,x;if module_manager.option('AutoProjectile','Rotations','Lock-View')then player.set_angles(w,x)end;if e<=0 then k=true;table.insert(j,i)e=math.random(module_manager.option('AutoProjectile','Min-Delay'),module_manager.option('AutoProjectile','Max-Delay'))n=get_projectile()if n<36 then player.inventory.swap(n,module_manager.option('AutoProjectile','Slot')-1)n=module_manager.option('AutoProjectile','Slot')else n=n-35 end;if l~=n then o=true;player.set_held_item_slot(n-2)o=false end end end;if c==4 and d<=0 then h,k=false,false;s,t=player.angles()C=C+module_manager.option('AutoProjectile','Rotations','Speed')if C>1 then C=1 end;w,x=cubic_bezier_curve(C,q,r,y,z,A,B,s,t)aJ.yaw,aJ.pitch=w,x;if module_manager.option('AutoProjectile','Rotations','Lock-View')then player.set_angles(w,x)end;if w==s and x==t then if l~=m then o=true;player.set_held_item_slot(m-2)o=false end;c=1 end end;g=f;d,e=d-1,e-1;return aJ end,on_render_screen=function(aY)render.scale(1/aY.scale_factor)local M,N,O=player.camera_position()if f and h then if module_manager.option('AutoProjectile','Render','Target-ESP')then local aZ,a_,b0=world.position(f)local a5,a6,a7,a8,a9,aa=world.bounding_box(f)if a5 and a6 and a7 and a8 and a9 and aa then a_=a_+(a9-a6)*module_manager.option('AutoProjectile','Rotations','Aim-Height')end;local b1,b2,b3=render.world_to_screen(aZ-M,a_-N,b0-O)local b4,b5,b6=render.world_to_screen(aZ-M+0.5,a_-N,b0-O)local b7,b8,b9=render.world_to_screen(aZ-M,a_-N,b0-O+0.5)local ba,bb,bc=render.world_to_screen(aZ-M-0.5,a_-N,b0-O)local bd,be,bf=render.world_to_screen(aZ-M,a_-N,b0-O-0.5)local bg,bh,bi=render.world_to_screen(aZ-M,a_-N+0.5,b0-O)local bj,bk,bl=render.world_to_screen(aZ-M,a_-N-0.5,b0-O)if b3<1 and b6<1 and b9<1 and bc<1 and bf<1 and bi<1 and bl<1 then local color=module_manager.option('AutoProjectile','Render','Target-Color')render.line(b1,b2,b4,b5,3,color[1],color[2],color[3],color[4])render.line(b1,b2,b7,b8,3,color[1],color[2],color[3],color[4])render.line(b1,b2,ba,bb,3,color[1],color[2],color[3],color[4])render.line(b1,b2,bd,be,3,color[1],color[2],color[3],color[4])render.line(b1,b2,bg,bh,3,color[1],color[2],color[3],color[4])render.line(b1,b2,bj,bk,3,color[1],color[2],color[3],color[4])end end;if module_manager.option('AutoProjectile','Render','Target-Tracer')then local aZ,a_,b0=world.position(f)local a5,a6,a7,a8,a9,aa=world.bounding_box(f)if a5 and a6 and a7 and a8 and a9 and aa then a_=a_+(a9-a6)*module_manager.option('AutoProjectile','Rotations','Aim-Height')end;local bm,bn,bo=render.world_to_screen(aZ-M,a_-N,b0-O)if bo<1 then render.line(aY.width/2*aY.scale_factor,aY.height/2*aY.scale_factor,bm,bn,3,color[1],color[2],color[3],color[4])end end;if module_manager.option('AutoProjectile','Render','Prediction-ESP')then local aZ,a_,b0=predict_cords(f)local b1,b2,b3=render.world_to_screen(aZ-M,a_-N,b0-O)local b4,b5,b6=render.world_to_screen(aZ-M+0.5,a_-N,b0-O)local b7,b8,b9=render.world_to_screen(aZ-M,a_-N,b0-O+0.5)local ba,bb,bc=render.world_to_screen(aZ-M-0.5,a_-N,b0-O)local bd,be,bf=render.world_to_screen(aZ-M,a_-N,b0-O-0.5)local bg,bh,bi=render.world_to_screen(aZ-M,a_-N+0.5,b0-O)local bj,bk,bl=render.world_to_screen(aZ-M,a_-N-0.5,b0-O)if b3<1 and b6<1 and b9<1 and bc<1 and bf<1 and bi<1 and bl<1 then local color=module_manager.option('AutoProjectile','Render','Prediction-Color')render.line(b1,b2,b4,b5,3,color[1],color[2],color[3],color[4])render.line(b1,b2,b7,b8,3,color[1],color[2],color[3],color[4])render.line(b1,b2,ba,bb,3,color[1],color[2],color[3],color[4])render.line(b1,b2,bd,be,3,color[1],color[2],color[3],color[4])render.line(b1,b2,bg,bh,3,color[1],color[2],color[3],color[4])render.line(b1,b2,bj,bk,3,color[1],color[2],color[3],color[4])end end;if module_manager.option('AutoProjectile','Render','Prediction-Tracer')then local bp,bq,br=world.position(f)local a5,a6,a7,a8,a9,aa=world.bounding_box(f)if a5 and a6 and a7 and a8 and a9 and aa then bq=bq+(a9-a6)*module_manager.option('AutoProjectile','Rotations','Aim-Height')end;local bs,bt,bu=predict_cords(f)local b1,b2,b3=render.world_to_screen(bp-M,bq-N,br-O)local b4,b5,b6=render.world_to_screen(bs-M,bt-N,bu-O)if b3<1 and b6<1 then render.line(b1,b2,b4,b5,3,color[1],color[2],color[3],color[4])end end end;if module_manager.option('AutoProjectile','Render','Thrown-ESP')then for ab=1,#j,1 do local aZ,a_,b0=j[ab]['x'],j[ab]['y'],j[ab]['z']local b1,b2,b3=render.world_to_screen(aZ-M,a_-N,b0-O)local b4,b5,b6=render.world_to_screen(aZ-M+0.25,a_-N,b0-O)local b7,b8,b9=render.world_to_screen(aZ-M,a_-N,b0-O+0.25)local ba,bb,bc=render.world_to_screen(aZ-M-0.25,a_-N,b0-O)local bd,be,bf=render.world_to_screen(aZ-M,a_-N,b0-O-0.25)local bg,bh,bi=render.world_to_screen(aZ-M,a_-N+0.25,b0-O)local bj,bk,bl=render.world_to_screen(aZ-M,a_-N-0.25,b0-O)if b3<1 and b6<1 and b9<1 and bc<1 and bf<1 and bi<1 and bl<1 then local color=module_manager.option('AutoProjectile','Render','Thrown-Color')render.line(b1,b2,b4,b5,3,color[1],color[2],color[3],color[4])render.line(b1,b2,b7,b8,3,color[1],color[2],color[3],color[4])render.line(b1,b2,ba,bb,3,color[1],color[2],color[3],color[4])render.line(b1,b2,bd,be,3,color[1],color[2],color[3],color[4])render.line(b1,b2,bg,bh,3,color[1],color[2],color[3],color[4])render.line(b1,b2,bj,bk,3,color[1],color[2],color[3],color[4])end end end;render.scale(aY.scale_factor)end,on_enable=function()m=player.held_item_slot()c,d,e=1,0,0;h,k=false,false;l=player.held_item_slot()end,on_disable=function()if l and m and l~=m then o=true;player.set_held_item_slot(m-2)o=false end;if module_manager.is_module_on('Auto-Rod')then player.message('.auto-rod')end end,on_send_packet=function(bv)if bv.packet_id==0x09 then l=bv.slot+1;if not o then m=l end end;return bv end}module_manager.register('dajiudwuiha',aX)"
                  ),
                  new Scaffold(ModuleCategory.PLAYER),
                  new AutoRespawn(ModuleCategory.PLAYER),
                  new Timer(ModuleCategory.PLAYER),
                  new NoRotate(ModuleCategory.PLAYER),
                  new FastMine(ModuleCategory.PLAYER),
                  new DoubleJump(ModuleCategory.MOVEMENT),
                  new Stealer(ModuleCategory.PLAYER),
                  new Freecam(ModuleCategory.PLAYER),
                  new MiddleClickFriends(),
                  new Phase(ModuleCategory.PLAYER),
                  new InventoryManager(ModuleCategory.PLAYER),
                  new Blink(ModuleCategory.PLAYER),
                  new Fucker(ModuleCategory.PLAYER),
                  new Step(ModuleCategory.PLAYER),
                  new FastUse(ModuleCategory.PLAYER),
                  new AutoTool(ModuleCategory.PLAYER),
                  new AutoFish(ModuleCategory.PLAYER),
                  new SpeedBridge(ModuleCategory.PLAYER),
                  new NoFall(ModuleCategory.MOVEMENT),
                  new Sprint(ModuleCategory.MOVEMENT),
                  ModuleManager.noSlow = new NoSlow(ModuleCategory.MOVEMENT),
                  new Flight(ModuleCategory.MOVEMENT),
                  new Speed(ModuleCategory.MOVEMENT),
                  ModuleManager.inventory = new Inventory(ModuleCategory.MOVEMENT),
                  new Jesus(ModuleCategory.MOVEMENT),
                  new AntiFall(ModuleCategory.MOVEMENT),
                  new Terrain(ModuleCategory.MOVEMENT),
                  new Sneak(ModuleCategory.MOVEMENT),
                  new LongJump(ModuleCategory.MOVEMENT),
                  new SafeWalk(ModuleCategory.MOVEMENT),
                  new Water(ModuleCategory.MOVEMENT),
                  new LegitScaffold(ModuleCategory.MOVEMENT, string4),
                  new PingSpoofer(ModuleCategory.EXPLOITS),
                  new HypixelPacketHandler(),
                  new ChatFilter(ModuleCategory.EXPLOITS),
                  new AntiHunger(ModuleCategory.EXPLOITS),
                  new ConsoleKiller(ModuleCategory.EXPLOITS),
                  new RogueSign(ModuleCategory.EXPLOITS),
                  new Disabler(
                     ModuleCategory.EXPLOITS,
                     string5,
                     "local a={}local b={}local c=0;local d=false;local e=0;local f,g=0,0;local h=0;local i=0;local j=0;local k=false;local l=false;local m=false;local n=false;local o=0;local function p()do return false end;for q=44,9,-1 do local r,s,t,u,v=player.inventory.item_information(q)if r~=nil and string.match(r,\"compass\")then return true end end;return false end;local function w()if player.ticks_existed()>35 and not p()and false then return true end;return false end;local function x(y)return y.packet_id==0x03 or y.packet_id==0x04 or y.packet_id==0x05 or y.packet_id==0x06 end;local function z(A,B,C)local D,E,F=player.position()return math.sqrt((D-A)^2+(E-B)^2+(F-C)^2)end;local function G(H,I)if I~=nil then while#b>I do if b[1]~=nil then player.send_packet_no_event(0x00,b[1])end;table.remove(b,1)end end;if H~=nil then while#a>H do if a[1]~=nil then player.send_packet_no_event(0x0F,a[1][1],a[1][2],a[1][3])end;table.remove(a,1)end end end;module_manager.register(\"gytffty\",{on_enable=function()e=0;c=client.time()d=false end,on_disable=function()G(0,0)world.set_timer(1)end,on_player_join=function()a={}b={}c=0;d=false;e=0;f,g=0,0;h=0;i=0;j=0;k=false;l=false;m=false;n=false;o=0 end,on_send_packet=function(J)if w()then if J.packet_id==0x0F then J.cancel=true;table.insert(a,{J.window_id,J.uid,J.accept})if not d then d=true end end;if J.packet_id==0x00 then J.cancel=true;table.insert(b,J.keep_alive_key)if n then c=client.time()n=false end end;if J.packet_id==0x02 and J.action==2 then j=client.time()end end;if J.packet_id==0x03 or J.packet_id==0x05 then J.cancel=true end;if J.packet_id==0x0B or J.packet_id==0x02 then if not d and w()or player.ticks_existed()<=35 then J.cancel=true end end;if(J.packet_id==0x0A or J.packet_id==0x08)and player.ticks_existed()<=35 then J.cancel=true end;if player.ticks_existed()<=35 then if J.packet_id==0x06 or J.packet_id==0x05 or J.packet_id==0x04 then if J.packet_id==0x06 or player.ticks_existed()%2==0 then J.cancel=true end end end;return J end,on_receive_packet=function(J)if w()then if J.packet_id==0x02 and l then if J.message:find(\"command. Type\")then J.cancel=true;h=client.time()-i;l=false end end end;if J.packet_id==0x08 then if player.ticks_existed()<=35 and z(J.pos_x,J.pos_y,J.pos_z)<=10 then J.cancel=true;if not m then player.send_packet_no_event(0x04,J.pos_x,J.pos_y+math.random()/1250+math.random()/750,J.pos_z,false)m=true else if player.ticks_existed()>35 then player.send_packet_no_event(0x04,J.pos_x,J.pos_y,J.pos_z,false)end end;player.set_position(J.pos_x,J.pos_y,J.pos_z)player.set_motion(0,-0.0784,0)end end;return J end,on_pre_motion=function(J)if w()then if client.time()-i>=10000 and client.time()-j>=350 then player.message(\"/\"..tostring(math.random()))i=client.time()l=true end;f=math.max(0,400-h)g=math.max(200,600-h)if d then if client.time()-c>=e then if#a>0 or#b>0 then G(nil,0)e=math.random(f,g)n=true;o=client.time()c=client.time()end end;if client.time()-o>=e/2 then G(0,nil)o=client.time()*2 end end end;return J end})"
                  ),
                  new SuicideClip(ModuleCategory.EXPLOITS),
                  new Plugins(ModuleCategory.EXPLOITS),
                  new HideWindow(ModuleCategory.EXPLOITS),
                  new PackSpoofer(ModuleCategory.OTHER),
                  new AntiTabComplete(ModuleCategory.OTHER),
                  new Annoy(ModuleCategory.OTHER),
                  new FriendProtect(ModuleCategory.OTHER),
                  new Announcer(ModuleCategory.OTHER),
                  new AutoHypixel(ModuleCategory.OTHER),
                  new ServerRotationTracker(),
                  new WorldTime(ModuleCategory.OTHER),
                  new AntiFrozen(ModuleCategory.OTHER),
                  new FastPlace(ModuleCategory.OTHER),
                  new KillSay(ModuleCategory.OTHER),
                  new Spammer(ModuleCategory.OTHER),
                  new AntiDesync(ModuleCategory.OTHER),
                  ModuleManager.antiObsidian = new AntiObsidian(ModuleCategory.OTHER),
                  ModuleManager.infiniteReach = new InfiniteReach(ModuleCategory.OTHER),
                  new ThunderNotifier(ModuleCategory.OTHER),
                  ModuleManager.noMouseIntersect = new NoMouseIntersect(ModuleCategory.OTHER),
                  new HackerDetector(ModuleCategory.OTHER),
                  new FreeLook(ModuleCategory.OTHER),
                  new GUI(ModuleCategory.HIDDEN),
                  new ESP(ModuleCategory.VISUALS),
                  new Chams(ModuleCategory.VISUALS),
                  new HUD(ModuleCategory.VISUALS),
                  new FullBright(ModuleCategory.VISUALS),
                  new Waypoints(ModuleCategory.VISUALS),
                  ModuleManager.noEffect = new NoEffect(ModuleCategory.VISUALS),
                  ModuleManager.scoreboard = new Scoreboard(ModuleCategory.VISUALS),
                  new Skeleton(ModuleCategory.VISUALS),
                  ModuleManager.camera = new Camera(ModuleCategory.VISUALS),
                  new Outline(ModuleCategory.VISUALS),
                  new Animations(ModuleCategory.VISUALS),
                  new Tracers(ModuleCategory.VISUALS),
                  new Crosshair(ModuleCategory.VISUALS),
                  new Radar(ModuleCategory.VISUALS),
                  ModuleManager.caveFinder = new CaveFinder(ModuleCategory.VISUALS),
                  ModuleManager.noRender = new NoRender(ModuleCategory.VISUALS),
                  new ChunkBorders(ModuleCategory.VISUALS),
                  new TitleAnnouncer(),
                  ModuleManager.noTitle = new NoTitle(ModuleCategory.VISUALS),
                  new Trajectories(ModuleCategory.VISUALS),
                  new NameProtect(ModuleCategory.VISUALS),
                  ModuleManager.streamerMode = new StreamerMode(ModuleCategory.VISUALS),
                  ModuleManager.xray = new Xray(ModuleCategory.VISUALS),
                  new BlockOverlay(ModuleCategory.VISUALS),
                  new DamageParticles(ModuleCategory.VISUALS),
                  ModuleManager.worldColor = new WorldColor(ModuleCategory.VISUALS),
                  ModuleManager.itemPhysics = new ItemPhysics(ModuleCategory.VISUALS),
                  new PlayerList(ModuleCategory.VISUALS),
                  ModuleManager.moreParticles = new MoreParticles(ModuleCategory.VISUALS),
                  new RiderGod(ModuleCategory.EXPLOITS),
                  new Nuker(ModuleCategory.PLAYER)
               );
            ClientAccess.client
               .getCommandManager()
               .add(
                  new BindCommand("bind", "add <module> <key>/del <module>/clear"),
                  new WaypointsCommand("waypoints", "add <name>/remove <name>"),
                  new NameCommand("name", "Copies your username to clipboard"),
                  new SettingsCommand("settings", "load <name>/save <name>"),
                  new VclipCommand("vclip", "<distance>"),
                  new FriendsCommand("friends", "add <name> <alias>/del <name/alias>/clear"),
                  new JumpCommand("jump", "junps"),
                  new EnemiesCommand("enemies", "add <name> <alias>/del <name/alias>/clear"),
                  new HelpCommand("help", "Help"),
                  new ClearchatCommand("clearchat", "Clears chat"),
                  new ScriptCommand("script", "<list/unsafe/reload/name enable/name disable>"),
                  new HclipCommand("hclip", "Clips horizontally"),
                  new NicklistCommand("nicklist", "Shows all the found nicked players in hypixel by using the denicker module"),
                  new IteminfoCommand("iteminfo", "iteminfo <player name>"),
                  new HideCommand("hide", "Hide in array <module/all>"),
                  new ShowCommand("show", "Show in array <module/all>"),
                  new FinalsCommand("finals", "Shows players with finals kills in Hypixel Mega Walls"),
                  new ChatCommand("chat", "<enabled/pings>"),
                  new FontCommand("font", "Change the client font"),
                  new BansCommand("bans", "sw/bw/uhc/mw/all/pit <hours>"),
                  new TeleportCommand(
                     "teleport",
                     "<x> <y> <z>/<x> <z>",
                     "local a=1;local b,c,d=0,0,0;local e,f,g=0,0,0;local h,i,j;local k,l,m,n,o=0,0,0,0,0;local p,q,r,s,t;local u=-1;local v=247;local w=2000;local x=500;local y={on_send_packet=function(z)if z.packet_id==0x01 then local A={}for B in string.gmatch(z.message,'%S+')do table.insert(A,B)end;if A[1]=='!tp'and#A==4 then z.cancel=true;h,i,j=math.floor(tonumber(A[2])+0.5),math.floor(tonumber(A[3])),math.floor(tonumber(A[4])+0.5)e,f,g=player.position()f=v;player.message('/l')a=2 elseif A[1]=='!tp'and#A==3 then z.cancel=true;h,i,j=math.floor(tonumber(A[2])+0.5),v,math.floor(tonumber(A[3])+0.5)e,f,g=player.position()f=v;player.message('/l')a=2 elseif A[1]=='!tp'and#A==2 then z.cancel=true;if A[2]=='back'then h,i,j=math.floor(e)+0.5,f,math.floor(g)+0.5;e,f,g=player.position()f=v;player.message('/l')a=2 else local C=world.entities()for D=1,#C,1 do local E=C[D]if world.name(E)==A[2]then h,i,j=world.position(E)h,j=math.floor(h+0.5),math.floor(j+0.5)i=v;e,f,g=player.position()f=v;player.message('/l')a=2 end end end end elseif a==4 and(z.packet_id==0x03 or z.packet_id==0x04 or z.packet_id==0x05 or z.packet_id==0x06)then z.cancel=true end;return z end,on_player_join=function()if a==2 then client.print('will attempt to teleport to ('..h..', '..i..', '..j..')')client.print('sneak to cancel')player.message('/rej')a=3 end;if a==3 then l,m=client.time(),client.time()b,c,d=0,0,0;a=4 end;o=client.time()end,on_pre_update=function()if a==1 and input.is_key_down(26)and input.is_mouse_down(2)and client.time()-k>1000 then local F,G=player.angles()local H,I,J,K,L,M,N,O=player.ray_cast(F,G,500)if H==2 then h,i,j=math.floor(J)+0.5,v,math.floor(L)+0.5;k=client.time()player.message('/l')a=2 end end;if a==4 then local F,G=player.angles()player.send_packet_no_event(0x06,h,i,j,F,G,false)player.send_packet_no_event(0x03,false)player.send_packet_no_event(0x06,h,i,j,F,G,false)player.send_packet_no_event(0x03,false)b=b+1;if c>0 and client.time()-o>w and client.time()-m>x then client.print('teleported')player.set_position(h,i,j)u=10;b,c,d=0,0,0;a=1 end;if d>0 and client.time()-o>w and client.time()-l>x then client.print('failed to teleport')u=10;b,c,d=0,0,0;a=1 end;if input.is_key_down(42)then client.print('canceled on sneak')u=10;b,c,d=0,0,0;a=1 end end;if u==0 then world.refresh_chunks()end;u=u-1 end,on_receive_packet=function(z)if z.packet_id==0x08 then p=z.pos_x;q=z.pos_y;r=z.pos_z;s=z.yaw;t=z.pitch;n=client.time()end;if z.packet_id==0x08 and a==4 and math.floor(z.pos_x)==math.floor(h)and math.floor(z.pos_z)==math.floor(j)then player.set_position(h,i,j)c=c+1;d=0;l=client.time()elseif a==4 then d=d+1;c=0;m=client.time()end;return z end}module_manager.register('draaaven',y)"
                  ),
                  new LimboCommand("limbo", "Sends you to limbo in Hypixel")
               );
            String string6 = "1,8,8+latest-DisconnectSystem/FinalKill/FixSpeed/KillAura/Keybindings/Velocity/Commands/Criticals/AntiBot/AutoClicker/HackNotifier/AutoArmor/HandleInventory/Regen/AutoHeal/FastBow/AutoBackStab/Reach/AimAssist/Knockback/Hitboxes/AntiFireball/KeepSprint/AutoProjectile/ScaffoldRecode/AutoRespawn/Timer/NoRotate/FastMine/DoubleJump/Stealer/Freecam/MCF/Phase/InventoryManager/Blink/Fucker/Step/FastUse/AutoTool/AutoFish/Bridge/NoFall/Sprint/NoSlowdown/Flight/Speed/Inventory/Jesus/AntiFall/Terrain/Sneak/LongJump/SafeWalk/WaterHack/LegitScaffold/PingSpoofer/ArgumentPingSpoof/ChatFilter/AntiHunger/ConsoleKiller/RougeSign/Disabler/SuicideClip/Plugins/HideWindow/PackSpoofer/AntiTabComplete/Annoy/FriendProtect/Announcer/AutoHypixel/PingCounter/WorldTime/AntiFrozen/FastPlace/KillSay/Spammer/AntiDesync/AntiObsidian/InfiniteReach/ThunderNotifier/NoMouseIntersect/HackerDetector/Freelook/GUI/ESP/Chams/HUD/FullBright/Waypoints/NoEffect/Scoreboard/Skeleton/Camera/Outline/Animations/Tracers/Crosshair/Radar/CaveFinder/NoRender/ChunkBorders/ServerText/NoTitle/Trajectories/NameProtect/StreamerMode/Xray/BlockOverlay/DamageParticles/WorldColor/ItemPhysics/PlayerList/MoreParticles/HorseGod/Nuker;32.0;5888;free;1;16640;2982;";
            String[] strings = string6.split(";");
            string3 = strings[0];
            String[] strings2 = string3.split("/");
            ModuleManager.setPositionDivisor(Double.parseDouble(strings[1]));
            ModuleManager.setModelViewMatrixMode(Integer.parseInt(strings[2]));
            ClientAccess.mc.mouseHelper.getAuthSession().setAccount(new AuthAccount(strings[3]));
            EntityRenderer.authFovMultiplier = Integer.parseInt(strings[4]);
            EntityRenderer.authClearMask = Integer.parseInt(strings[5]);
            EntityRenderer.authMatrixParam = Integer.parseInt(strings[6]);

            for (int i2 = 0; i2 < strings2.length; i2++) {
               String string7 = strings2[i2];
               if (ClientAccess.client.getModuleManager().getList().get(i2) instanceof Module) {
                  Module module2 = (Module)ClientAccess.client.getModuleManager().getList().get(i2);
                  if (module2.getName() == null) {
                     module2.setName(string7);
                  }

                  if (module2.getDisplayName() == null) {
                     module2.setDisplayName(module2.getName());
                  }
               }
            }

            ConfigFiles.load("default");
            ConfigFiles.save(ConfigFiles.serializeModules(), "default");
            int b10 = 3;
            NumberSetting numberSetting = null;

            for (ModuleCategory category : ModuleCategory.values()) {
               if (category != ModuleCategory.HIDDEN && category != ModuleCategory.CONFIG) {
                  CategoryPanel categoryPanel = new CategoryPanel(category, b10, 3, 107, 17);

                  for (Module module3 : Astolfo.INSTANCE.getModuleManager().getModules(category)) {
                     ModuleButton moduleButton = new ModuleButton(categoryPanel, module3, 103, 15);

                     for (Setting setting : module3.getSettings()) {
                        if (!setting.getName().startsWith("Pos-") && !setting.getName().equals("Hide")) {
                           if (setting instanceof ColorSetting) {
                              moduleButton.getChildren().add(new ColorPicker(category, moduleButton, setting, 99, 70));
                           }

                           if (setting instanceof BooleanSetting) {
                              moduleButton.getChildren().add(new BooleanComponent(category, moduleButton, setting, 99, 10));
                           }

                           if (setting instanceof NumberSetting) {
                              if (setting.getName().startsWith("Min-") && !setting.getName().contains("Min-Health")) {
                                 numberSetting = (NumberSetting)setting;
                                 continue;
                              }

                              if (setting.getName().startsWith("Max-") && numberSetting != null) {
                                 moduleButton.getChildren().add(new RangeSlider(category, moduleButton, numberSetting, setting, 99, 10));
                                 numberSetting = null;
                                 continue;
                              }

                              moduleButton.getChildren().add(new NumberSlider(category, moduleButton, setting, 99, 10));
                           }

                           if (setting instanceof EnumSetting) {
                              moduleButton.getChildren().add(new EnumComponent(moduleButton, setting, 99, 10));
                           }

                           if (setting instanceof StringSetting) {
                              moduleButton.getChildren().add(new StringComponent(category, moduleButton, setting, 93, 10));
                           }

                           if (setting instanceof MapSetting) {
                              MapComponent mapComponent = new MapComponent(moduleButton, setting, 99, 10);

                              for (Setting setting2 : ((MapSetting)setting).getSettings().values()) {
                                 if (!setting2.getName().startsWith("Pos-")) {
                                    if (setting2 instanceof BooleanSetting) {
                                       mapComponent.getChildren().add(new BooleanComponent(category, mapComponent, setting2, 93, 10));
                                    }

                                    if (setting2 instanceof NumberSetting) {
                                       if (setting2.getName().startsWith("Min-") && !setting.getName().contains("Min-Health")) {
                                          numberSetting = (NumberSetting)setting2;
                                          continue;
                                       }

                                       if (setting2.getName().startsWith("Max-")) {
                                          mapComponent.getChildren().add(new RangeSlider(category, mapComponent, numberSetting, setting2, 93, 10));
                                          numberSetting = null;
                                          continue;
                                       }

                                       mapComponent.getChildren().add(new NumberSlider(category, mapComponent, setting2, 93, 10));
                                    }

                                    if (setting2 instanceof EnumSetting) {
                                       mapComponent.getChildren().add(new EnumComponent(mapComponent, setting2, 93, 10));
                                    }

                                    if (setting2 instanceof StringSetting) {
                                       mapComponent.getChildren().add(new StringComponent(category, mapComponent, setting2, 93, 10));
                                    }

                                    if (setting2 instanceof ColorSetting) {
                                       mapComponent.getChildren().add(new ColorPicker(category, mapComponent, setting2, 93, 70));
                                    }
                                 }
                              }

                              moduleButton.getChildren().add(mapComponent);
                           }
                        }
                     }

                     categoryPanel.addChildren(moduleButton);
                  }

                  ClientAccess.client.getDefaultPanelManager().addElements(categoryPanel);
                  b10 += 125;
               }
            }

            int b11 = 3;

            for (ModuleCategory category2 : ModuleCategory.values()) {
               if (category2 != ModuleCategory.HIDDEN && category2 != ModuleCategory.CONFIG) {
                  astolfo.core.gui.clickgui.transparent.CategoryPanel categoryPanel2 = new astolfo.core.gui.clickgui.transparent.CategoryPanel(category2, b11, 3, 105, 10);

                  for (Module module4 : Astolfo.INSTANCE.getModuleManager().getModules(category2)) {
                     astolfo.core.gui.clickgui.transparent.ModuleButton moduleButton2 = new astolfo.core.gui.clickgui.transparent.ModuleButton(categoryPanel2, module4, 103, 10);

                     for (Setting setting3 : module4.getSettings()) {
                        if (!setting3.getName().startsWith("Pos-") && !setting3.getName().equals("Hide")) {
                           if (setting3 instanceof BooleanSetting) {
                              moduleButton2.getChildren().add(new astolfo.core.gui.clickgui.transparent.BooleanComponent(moduleButton2, setting3, 101, 10));
                           }

                           if (setting3 instanceof NumberSetting) {
                              moduleButton2.getChildren().add(new astolfo.core.gui.clickgui.transparent.NumberSlider(moduleButton2, setting3, 101, 10));
                           }

                           if (setting3 instanceof EnumSetting) {
                              moduleButton2.getChildren().add(new astolfo.core.gui.clickgui.transparent.EnumComponent(moduleButton2, setting3, 101, 10));
                           }

                           if (setting3 instanceof StringSetting) {
                              moduleButton2.getChildren().add(new astolfo.core.gui.clickgui.transparent.StringComponent(moduleButton2, setting3, 101, 10));
                           }

                           if (setting3 instanceof MapSetting) {
                              astolfo.core.gui.clickgui.transparent.MapComponent mapComponent2 = new astolfo.core.gui.clickgui.transparent.MapComponent(moduleButton2, setting3, 101, 10);

                              for (Setting setting4 : ((MapSetting)setting3).getSettings().values()) {
                                 if (!setting4.getName().startsWith("Pos-")) {
                                    if (setting4 instanceof BooleanSetting) {
                                       mapComponent2.getChildren().add(new astolfo.core.gui.clickgui.transparent.BooleanComponent(mapComponent2, setting4, 98, 10));
                                    }

                                    if (setting4 instanceof NumberSetting) {
                                       mapComponent2.getChildren().add(new astolfo.core.gui.clickgui.transparent.NumberSlider(mapComponent2, setting4, 98, 10));
                                    }

                                    if (setting4 instanceof EnumSetting) {
                                       mapComponent2.getChildren().add(new astolfo.core.gui.clickgui.transparent.EnumComponent(mapComponent2, setting4, 98, 10));
                                    }

                                    if (setting4 instanceof StringSetting) {
                                       mapComponent2.getChildren().add(new astolfo.core.gui.clickgui.transparent.StringComponent(mapComponent2, setting4, 98, 10));
                                    }
                                 }
                              }

                              moduleButton2.getChildren().add(mapComponent2);
                           }
                        }
                     }

                     categoryPanel2.addChildren(moduleButton2);
                  }

                  Astolfo.INSTANCE.getTransparentPanelManager().addElements(categoryPanel2);
                  b11 += 110;
               }
            }

            System.gc();
            ClientAccess.client.register(121968519176554L, ClientAccess.client.getModuleManager());
            ClientAccess.client.register(121968519176554L, ClientAccess.client.getCommandManager());
            ClientAccess.client.register(121968519176554L, ClientAccess.client.getTransparentPanelManager());
            ClientAccess.client.register(121968519176554L, ClientAccess.client.getScriptManager());
            ClientAccess.client.register(121968519176554L, ClientAccess.client.getSidebarGui());
         }
      }
   }
}
