package astolfo.api.auth.net.reader;

import astolfo.api.auth.net.PacketReader;
import astolfo.api.auth.packet.LoginResponse;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class LoginResponseReader implements PacketReader<LoginResponse> {
   private LoginResponse packet;

   @Override
   public void read(ByteBuffer byteBuffer) {
      byte[] bytes = new byte[byteBuffer.getInt()];
      byteBuffer.get(bytes);
      boolean bl = byteBuffer.getInt() == 1;
      this.packet = new LoginResponse(bl, new String(bytes, StandardCharsets.UTF_8));
   }

   public LoginResponse getPacket() {
      return this.packet;
   }
}
