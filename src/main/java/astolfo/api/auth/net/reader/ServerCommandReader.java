package astolfo.api.auth.net.reader;

import astolfo.api.auth.net.PacketReader;
import astolfo.api.auth.packet.ServerCommand;
import astolfo.api.auth.packet.ServerCommandType;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ServerCommandReader implements PacketReader<ServerCommand> {
   private ServerCommand packet;

   @Override
   public void read(ByteBuffer byteBuffer) {
      byte[] bytes = new byte[byteBuffer.getInt()];
      byteBuffer.get(bytes);
      this.packet = new ServerCommand(ServerCommandType.values()[byteBuffer.getInt()], new String(bytes, StandardCharsets.UTF_8));
   }

   public ServerCommand getPacket() {
      return this.packet;
   }
}
