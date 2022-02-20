package aroma1997.core.util;

import aroma1997.core.client.util.Colors;
import aroma1997.core.log.LogHelperPre;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import org.apache.logging.log4j.Level;

public class ServerUtil {
  public static ChatComponentStyle getChatForString(String str) {
    return (ChatComponentStyle)new ChatComponentText(str);
  }
  
  public static boolean isPlayerAdmin(ICommandSender sender) {
    if (sender == null)
      return false; 
    if (sender instanceof EntityPlayerMP)
      return isPlayerAdmin(((EntityPlayerMP)sender).getGameProfile()); 
    if ("Server".equals(sender.getCommandSenderName()))
      return true; 
    return false;
  }
  
  public static boolean isPlayerAdmin(GameProfile player) {
    if (!MinecraftServer.getServer().isDedicatedServer() && player.equals((Minecraft.getMinecraft()).thePlayer.getGameProfile()))
      return true; 
    if (MinecraftServer.getServer().getConfigurationManager().func_152596_g(player))
      return true; 
    return false;
  }
  
  public static void printCommandUsage(ICommandSender sender, ICommand command) {
    sender.addChatMessage((IChatComponent)new ChatComponentText(command.getCommandUsage(sender)));
  }
  
  public static void sendMessageToAllPlayers(String message) {
    MinecraftServer server = MinecraftServer.getServer();
    if (server != null) {
      server.getConfigurationManager().sendChatMsg((IChatComponent)getChatForString(message));
    } else {
      LogHelperPre.log(Level.WARN, "Failed to send Chat message to all players: " + message);
    } 
  }
  
  public static void printPermissionDenied(ICommandSender sender) {
    sender.addChatMessage((IChatComponent)getChatForString(Colors.PINK + StatCollector.translateToLocal("commands.generic.permission")));
  }
  
  public static EntityPlayerMP getPlayer(String name) {
    for (Object o : (MinecraftServer.getServer().getConfigurationManager()).playerEntityList) {
      EntityPlayerMP player = (EntityPlayerMP)o;
      if (player.getCommandSenderName().equalsIgnoreCase(name))
        return player; 
    } 
    return null;
  }
}
