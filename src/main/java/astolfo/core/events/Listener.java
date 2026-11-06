package astolfo.core.events;

import java.util.function.Consumer;

public interface Listener<E extends Event> extends Consumer<E> {
}
