package astolfo;

import net.minecraft.client.Minecraft;
import astolfo.core.chat.ClientChat;
import astolfo.core.notifications.Toasts;

public interface ClientAccess {
   Minecraft mc = Minecraft.getMinecraft();
   Astolfo client = Astolfo.INSTANCE;
   ClientChat chat = ClientChat.CHAT;
   Toasts toasts = Toasts.TOASTS;
}
