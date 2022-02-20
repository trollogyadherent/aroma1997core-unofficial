package aroma1997.core.command;

import aroma1997.core.network.NetworkHelper;
import aroma1997.core.network.packets.CapePacket;
import aroma1997.core.util.ServerUtil;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.IChatComponent;

public class AromaCommand extends AromaSubCommand {
  public AromaCommand() {
    addSubCommand(new ClientReloadCommand());
  }
  
  public String getCommandName() {
    return "aroma1997";
  }
  
  public String getSelfDescription(ICommandSender var1) {
    return "/aroma1997";
  }
  
  public class ClientReloadCommand extends AromaBaseCommand {
    public String getCommandName() {
      return "reloadclient";
    }
    
    public String getCommandUsage(ICommandSender var1) {
      return "/aroma1997 reloadclient";
    }
    
    public void processCommand(ICommandSender sender, String[] var2) {
      NetworkHelper.getCorePacketHandler().sendPacketToPlayers((IMessage)new CapePacket());
      sender.addChatMessage((IChatComponent)ServerUtil.getChatForString("Send packet to all players to reload client configs."));
    }
  }
}
