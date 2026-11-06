package astolfo.core.events.bus;

import astolfo.core.events.Event;
import java.util.function.BiPredicate;

public abstract class SubscriptionFilter<E extends Event> implements BiPredicate<Subscription<?, E>, E> {
}
