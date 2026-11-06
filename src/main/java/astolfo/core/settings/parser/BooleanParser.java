package astolfo.core.settings.parser;


import astolfo.core.settings.Parser;
import java.util.Optional;

public class BooleanParser implements Parser<Boolean> {
   @Override
   public Optional<Boolean> parse(String string, Class<? extends Boolean> class2) {
      return Optional.ofNullable(Boolean.parseBoolean(string));
   }
}
