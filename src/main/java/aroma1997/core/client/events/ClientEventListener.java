package aroma1997.core.client.events;

import aroma1997.core.client.MiscStuff;
import aroma1997.core.client.ThreadDownloadCape;
import aroma1997.core.client.util.ClientUtil;
import aroma1997.core.client.util.Colors;
import aroma1997.core.config.Config;
import aroma1997.core.version.VersionCheck;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.HashSet;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.MinecraftForge;

public class ClientEventListener {
  private String name;
  
  private boolean firsttick;
  
  private HashSet<String> addedPlayers;
  
  private ClientEventListener() {
    this.firsttick = false;
    this.addedPlayers = new HashSet<String>();
    FMLCommonHandler.instance().bus().register(this);
    MinecraftForge.EVENT_BUS.register(this);
  }
  
  @SubscribeEvent
  public void soundStuff(PlaySoundEvent17 event) {
    if (Config.muffleSounds() && Config.soundsToMuffle().contains(event.name))
      event.result = null; 
  }
  
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void renderPlayerPre(RenderPlayerEvent.Pre event) {
    this.name = event.entityPlayer.getCommandSenderName();
    if (MiscStuff.hasColor(this.name))
      ReflectionHelper.setPrivateValue(GameProfile.class, event.entityPlayer.getGameProfile(), MiscStuff.getColor(this.name) + this.name + Colors.RESET, new String[] { "name" }); 
  }
  
  @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
  public void renderPlayerPost(RenderPlayerEvent.Post event) {
    if (MiscStuff.hasColor(this.name))
      ReflectionHelper.setPrivateValue(GameProfile.class, event.entityPlayer.getGameProfile(), this.name, new String[] { "name" }); 
  }
  
  @SubscribeEvent
  public void tick(TickEvent.ClientTickEvent event) {
    if (event.phase == TickEvent.Phase.START) {
      if (this.firsttick && (Minecraft.getMinecraft()).theWorld == null)
        this.firsttick = false; 
      if (!this.firsttick && (Minecraft.getMinecraft()).theWorld != null) {
        init();
        this.firsttick = true;
      } 
    } else if ((ClientUtil.getMinecraft()).theWorld != null && (ClientUtil.getMinecraft()).theWorld.playerEntities.size() > 0) {
      List<?> players = (ClientUtil.getMinecraft()).theWorld.playerEntities;
      for (int counter = 0; counter < players.size(); counter++) {
        if (players.get(counter) != null) {
          EntityPlayer player = (EntityPlayer)players.get(counter);
          if (MiscStuff.hasCape(player.getCommandSenderName())) {
            AbstractClientPlayer thePlayer = (AbstractClientPlayer)player;
            ThreadDownloadCape thread = ThreadDownloadCape.getThread(player.getCommandSenderName());
            if (!this.addedPlayers.contains(player.getCommandSenderName())) {
              if (thread.finishedLoading) {
                thePlayer.locationCape = thread.getCurrentCapeRL();
                this.addedPlayers.add(player.getCommandSenderName());
              } 
            } else if (thePlayer.locationCape != thread.getCurrentCapeRL()) {
              thePlayer.locationCape = thread.getCurrentCapeRL();
            } 
          } 
        } 
      } 
    } 
  }
  
  private static ClientEventListener instance = new ClientEventListener();
  
  public static void resetCounter() {
    instance.addedPlayers.clear();
  }
  
  public static ClientEventListener getInstance() {
    return instance;
  }
  
  private static void init() {
    VersionCheck.doVersionCheck((ICommandSender)(Minecraft.getMinecraft()).thePlayer);
  }
}
