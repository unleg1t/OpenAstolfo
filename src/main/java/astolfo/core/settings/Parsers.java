package astolfo.core.settings;

import astolfo.core.settings.parser.BooleanParser;
import astolfo.core.settings.parser.CharacterParser;
import astolfo.core.settings.parser.ClassParser;
import astolfo.core.settings.parser.EnumParser;
import astolfo.core.settings.parser.NumberParser;
import astolfo.core.settings.parser.StringParser;
import java.util.HashMap;
import java.util.Map;

public class Parsers {
   private Map<Class<?>, Parser<?>> parsers = new HashMap<>();

   public Parsers() {
      this.parsers.put(Class.class, new ClassParser());
      this.parsers.put(Boolean.class, new BooleanParser());
      this.parsers.put(Enum.class, new EnumParser());
      this.parsers.put(Number.class, new NumberParser());
      this.parsers.put(Character.class, new CharacterParser());
      this.parsers.put(String.class, new StringParser());
   }

   public Parser<?> get(Class<?> class2) {
      return this.parsers.get(class2);
   }
}
