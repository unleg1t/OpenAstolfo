package astolfo.core.events.impl.player.input;

public enum ChatDirection {
   TO_SEND,
   RECEIVED;

   private static final ChatDirection[] VALUES = new ChatDirection[]{TO_SEND, RECEIVED};
}
