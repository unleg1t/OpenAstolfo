package astolfo.api.loader.check;

import astolfo.core.events.EventPriority;

public interface StageCheck extends Comparable<StageCheck> {
   CheckResult check(CheckRequest var1);

   default EventPriority getPriority() {
      return EventPriority.NORMAL;
   }

   int compareTo(StageCheck var1);
}
