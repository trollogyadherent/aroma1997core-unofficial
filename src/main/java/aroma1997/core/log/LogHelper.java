package aroma1997.core.log;

import aroma1997.core.client.util.Colors;
import aroma1997.core.util.ServerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class LogHelper extends LogHelperPre {
  public static void sendMessageToPlayer(Logger logger, ICommandSender player, String message) {
    debugLog("Message to player: " + player + " Message: " + message);
    player.addChatMessage((IChatComponent)ServerUtil.getChatForString(getLoggerPrefix(true, logger, message)));
  }
  
  public static void sendMessageToPlayers(Logger logger, String message) {
    debugLog("Message to players; Message: " + message);
    MinecraftServer server = MinecraftServer.getServer();
    if (server != null) {
      server.getConfigurationManager().sendChatMsg((IChatComponent)ServerUtil.getChatForString(getLoggerPrefix(true, logger, message)));
    } else {
      sendMessageToPlayer(logger, (ICommandSender)(Minecraft.getMinecraft()).thePlayer, message);
    } 
  }
  
  public static void sendMessageToDimension(Logger logger, int dimension, String message) {
    debugLog("Message to dimension: " + dimension + " Message: " + message);
    MinecraftServer server = MinecraftServer.getServer();
    if (server != null)
      for (Object o : (MinecraftServer.getServer().getConfigurationManager()).playerEntityList) {
        EntityPlayerMP player = (EntityPlayerMP)o;
        if (player.dimension == dimension)
          player.addChatMessage((IChatComponent)ServerUtil.getChatForString(getLoggerPrefix(true, logger, message))); 
      }  
  }
  
  @Deprecated
  public static void logServer(Logger logger, Level level, String message) {
    logger.log(level, message);
  }
  
  private static String getLoggerPrefix(boolean colored, Logger logger, String string) {
    if (colored)
      return "[" + Colors.DARKBLUE + logger.getName() + Colors.RESET + "] " + string; 
    return "[" + logger.getName() + "] " + string;
  }
}
