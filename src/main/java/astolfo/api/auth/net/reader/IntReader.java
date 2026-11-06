package astolfo.api.auth.net.reader;

import astolfo.api.auth.net.PacketReader;
import java.nio.ByteBuffer;

public class IntReader implements PacketReader<Integer> {
   private Integer packet;

   @Override
   public void read(ByteBuffer byteBuffer) {
      this.packet = byteBuffer.getInt();
   }

   public Integer getPacket() {
      return this.packet;
   }
}
