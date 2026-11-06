package astolfo.core.events.bus;

import astolfo.core.events.Event;
import astolfo.core.events.EventHandler;
import java.lang.reflect.AnnotatedElement;

public interface ListenerFilter<A extends AnnotatedElement> {
   default boolean isListener(A annotatedElement) {
      return annotatedElement.isAnnotationPresent(EventHandler.class);
   }

   boolean listensTo(A annotatedElement, Class<? extends Event> class2);

   static FieldListenerFilter forFields() {
      return new FieldListenerFilter();
   }

   static MethodListenerFilter forMethods() {
      return new MethodListenerFilter();
   }
}
