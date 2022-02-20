package aroma1997.core.command;

import aroma1997.core.client.util.Colors;
import aroma1997.core.util.ServerUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;

public class Sleeping extends AromaSubCommand {
  private final MinecraftServer server;
  
  public Sleeping(MinecraftServer server) {
    this.server = server;
    addSubCommand(new SleepingInformCommand());
    addSubCommand(new SleepingKickCommand());
  }
  
  public String getCommandName() {
    return "sleep";
  }
  
  private boolean shouldNotDo(EntityPlayerMP sender) {
    if (sender.mcServer.worldServerForDimension(sender.dimension).isDaytime()) {
      sender.addChatMessage((IChatComponent)ServerUtil.getChatForString(Colors.YELLOW + "No need to sleep in dimension " + sender.dimension + "!"));
      return true;
    } 
    List<EntityPlayerMP> players = getPlayersNotSleeping((EntityPlayer)sender);
    if (players.size() == 0) {
      sender.addChatMessage((IChatComponent)ServerUtil.getChatForString("All players are sleeping."));
      return true;
    } 
    return false;
  }
  
  public void processCommandSelf(ICommandSender snd, String[] args) {
    if (shouldNotDo((EntityPlayerMP)snd))
      return; 
    EntityPlayerMP sender = (EntityPlayerMP)snd;
    List<EntityPlayerMP> players = getPlayersNotSleeping((EntityPlayer)sender);
    if (players.size() == 0) {
      sender.addChatMessage((IChatComponent)ServerUtil.getChatForString("All players are sleeping."));
      return;
    } 
    String message = "The players, that are not sleeping are: ";
    boolean first = true;
    for (EntityPlayerMP player : players) {
      if (first) {
        message = message + player.getCommandSenderName();
        first = false;
        continue;
      } 
      message = message + ", " + player.getCommandSenderName();
    } 
    message = message + ".";
    sender.addChatMessage((IChatComponent)ServerUtil.getChatForString(message));
  }
  
  public List<String> getCommandAliases() {
    return Arrays.asList(new String[] { "sleeping" });
  }
  
  public String getSelfDescription(ICommandSender sender) {
    return "/sleep";
  }
  
  private List<EntityPlayerMP> getPlayersNotSleeping(EntityPlayer player) {
    String[] users = this.server.getAllUsernames();
    ArrayList<EntityPlayerMP> players = new ArrayList<EntityPlayerMP>();
    for (Object p : (MinecraftServer.getServer().getConfigurationManager()).playerEntityList) {
      EntityPlayerMP user = (EntityPlayerMP)p;
      if (user != null && !user.isPlayerSleeping() && user.dimension == player.dimension)
        players.add(user); 
    } 
    return players;
  }
  
  public boolean canCommandSenderUseCommand(ICommandSender sender) {
    return (sender != null && sender instanceof EntityPlayerMP);
  }
  
  private class SleepingInformCommand extends AromaBaseCommand {
    private SleepingInformCommand() {}
    
    public String getCommandName() {
      return "inform";
    }
    
    public String getCommandUsage(ICommandSender var1) {
      return "/sleep inform";
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
      return (sender != null && sender instanceof EntityPlayerMP);
    }
    
    public void processCommand(ICommandSender sender, String[] var2) {
      if (Sleeping.this.shouldNotDo((EntityPlayerMP)sender))
        return; 
      List<EntityPlayerMP> players = Sleeping.this.getPlayersNotSleeping((EntityPlayer)sender);
      for (EntityPlayerMP player : players)
        player.addChatMessage((IChatComponent)ServerUtil.getChatForString(Colors.GREEN + sender.getCommandSenderName() + " wants you to sleep!")); 
      sender.addChatMessage((IChatComponent)ServerUtil.getChatForString(Colors.YELLOW.toString() + players.size() + " players were informed about that you want to sleep!"));
    }
  }
  
  private class SleepingKickCommand extends AromaBaseCommand {
    private SleepingKickCommand() {}
    
    public String getCommandName() {
      return "kick";
    }
    
    public String getCommandUsage(ICommandSender var1) {
      return "/sleep kick";
    }
    
    public void processCommand(ICommandSender sender, String[] var2) {
      if (Sleeping.this.shouldNotDo((EntityPlayerMP)sender))
        return; 
      List<EntityPlayerMP> players = Sleeping.this.getPlayersNotSleeping((EntityPlayer)sender);
      for (EntityPlayerMP player : players)
        player.playerNetServerHandler.kickPlayerFromServer(Colors.YELLOW + "You were not sleeping!"); 
      sender.addChatMessage((IChatComponent)ServerUtil.getChatForString(Colors.YELLOW + "" + players.size() + " players were kicked from the server!"));
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
      return (sender != null && sender instanceof EntityPlayerMP && ServerUtil.isPlayerAdmin(sender));
    }
  }
}
