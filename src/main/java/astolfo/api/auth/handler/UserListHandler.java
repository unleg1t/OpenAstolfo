package astolfo.api.auth.handler;

import astolfo.api.auth.net.PacketOutput;
import astolfo.api.auth.packet.PacketId;
import astolfo.api.auth.packet.UserListPacket;
import astolfo.api.auth.packet.UserListType;
import astolfo.api.user.AstolfoUser;
import astolfo.api.user.AstolfoUserStatus;

public class UserListHandler extends PacketHandler<UserListPacket> {
   public UserListHandler() {
      super(PacketId.USER_LIST);
   }

   public void handle(UserListPacket userListPacket, PacketOutput packetOutput) {
      if (userListPacket.type == UserListType.A) {
         client.getUserManager().getUsers().clear();
         String[] strings = userListPacket.users.split(" ");
         int i = 0;

         for (int j = strings.length; i < j; i++) {
            String string = strings[i];
            String[] strings2 = string.split("\\+");
            if (strings2.length == 2) {
               String string2 = strings2[0].substring(0, 1);
               String string3 = strings2[0].substring(1);
               String string4 = strings2[1];
               if (!string3.isEmpty() && !string4.isEmpty()) {
                  client.getUserManager()
                     .getUsers()
                     .put(
                        string4,
                        new AstolfoUser(string3, string4, string2.equals("*") ? AstolfoUserStatus.C : (string2.equals("º") ? AstolfoUserStatus.B : AstolfoUserStatus.A))
                     );
               }
            }
         }
      }
   }
}
