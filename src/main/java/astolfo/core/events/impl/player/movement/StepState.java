package astolfo.core.events.impl.player.movement;

public enum StepState {
   PRE,
   BEFORE,
   POST;

   private static final StepState[] VALUES = new StepState[]{PRE, BEFORE, POST};
}
