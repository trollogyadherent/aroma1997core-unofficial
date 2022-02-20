package aroma1997.core.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;

public class EventListener {
  public EventListener() {
    MinecraftForge.EVENT_BUS.register(this);
  }
  
  @SubscribeEvent
  public void onChat(ServerChatEvent event) {}
  
  @SubscribeEvent
  public void login(PlayerEvent.PlayerLoggedInEvent event) {}
}
