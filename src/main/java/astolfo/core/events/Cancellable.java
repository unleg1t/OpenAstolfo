package astolfo.core.events;

public interface Cancellable {
   void cancel();

   boolean isCancelled();

   void setCancelled(boolean var1);
}
