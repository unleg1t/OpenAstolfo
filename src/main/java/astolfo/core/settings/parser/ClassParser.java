package astolfo.core.settings.parser;

import astolfo.core.settings.Parser;
import java.util.Optional;

public class ClassParser implements Parser<Class> {
   @Override
   public Optional<Class> parse(String string, Class<? extends Class> class2) {
      Class oclass;
      try {
         oclass = Class.forName(string);
      } catch (ClassNotFoundException classnotfoundexception) {
         classnotfoundexception.printStackTrace();
         return Optional.empty();
      }

      return Optional.ofNullable(oclass);
   }
}
