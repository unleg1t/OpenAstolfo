package astolfo.core.events.bus;

import astolfo.core.events.Event;
import astolfo.core.events.EventHandler;
import astolfo.core.events.Listener;
import java.lang.reflect.Field;

public class FieldListenerFilter implements ListenerFilter<Field> {
   public boolean isListener(Field field) {
      return ListenerFilter.super.isListener(field) && Listener.class.isAssignableFrom(field.getType());
   }

   public boolean listensTo(Field field, Class<? extends Event> class2) {
      return this.isListener(field) && field.getDeclaredAnnotation(EventHandler.class).value() == class2;
   }
}
