package astolfo.modules.impl.visual;

import astolfo.modules.ModuleCategory;
import astolfo.modules.Module;
import astolfo.core.settings.impl.EnumSetting;
import astolfo.core.settings.impl.MapSetting;
import astolfo.core.settings.impl.MapSettingBuilder;
import astolfo.core.settings.impl.NumberSetting;

public final class Animations extends Module {
   public static final EnumSetting<AnimationsMode> mode = new EnumSetting<>("Mode", "Set your animation mode", AnimationsMode.DEFAULT);
   public static final NumberSetting<Float> spin = new NumberSetting<>("Spin", "Spin", 2.0F, 0.0F, 10.0F);
   public static final NumberSetting<Integer> speed = new NumberSetting<>("Speed", "Speed of the animation", 6, 1, 15);
   public static final MapSetting position = new MapSettingBuilder("Position")
      .ordered()
      .description("")
      .add("X", new NumberSetting<>("X", "X coordinate", 0.0F, -2.0F, 2.0F))
      .add("Y", new NumberSetting<>("Y", "Y coordinate", 0.0F, -2.0F, 2.0F))
      .add("Z", new NumberSetting<>("Z", "Z coordinate", 0.0F, -2.0F, 2.0F))
      .build();

   public Animations(ModuleCategory category) {
      super(category);
      this.addSettings(position, mode, spin, speed);
      this.setHidden(false);
   }

   public enum AnimationsMode {
      DEFAULT,
      ASTOLFO,
      POINT,
      DOWN,
      DOWN_2,
      OLD,
      WINGY,
      PLAIN,
      SPIN,
      SPINNY,
      BLICKY,
      FRONT,
      DASH,
      EDIT;

      private static final AnimationsMode[] VALUES = new AnimationsMode[]{
         DEFAULT, ASTOLFO, POINT, DOWN, DOWN_2, OLD, WINGY, PLAIN, SPIN, SPINNY, BLICKY, FRONT, DASH, EDIT
      };
   }
}
