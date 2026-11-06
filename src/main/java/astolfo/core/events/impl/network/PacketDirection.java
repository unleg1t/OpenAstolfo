package astolfo.core.events.impl.network;


public enum PacketDirection {
   RECEIVED,
   OUTGOING;

   private static final PacketDirection[] VALUES = new PacketDirection[]{RECEIVED, OUTGOING};
}
