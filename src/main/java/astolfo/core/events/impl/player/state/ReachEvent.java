package astolfo.core.events.impl.player.state;

import astolfo.core.events.Event;

public class ReachEvent implements Event {
   private double reach = 3.0;

   public void setReach(double d) {
      this.reach = d;
   }

   public double getReach() {
      return this.reach;
   }
}
