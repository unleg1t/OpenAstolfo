package astolfo.core.settings.attribute;

public class ReadOnlyAttribute extends SettingAttribute {
   public ReadOnlyAttribute() {
      super(AttributePriority.HIGHEST);
   }

   public static boolean T() {
      return true;
   }
}
