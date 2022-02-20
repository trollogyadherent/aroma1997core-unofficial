package aroma1997.core.version;

import aroma1997.core.config.Config;
import aroma1997.core.util.ServerUtil;
import java.util.HashSet;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.IChatComponent;

public class ThreadVersionChecking extends Thread {
  private ICommandSender sender;
  
  ThreadVersionChecking(ICommandSender sender) {
    this.sender = sender;
    init();
  }
  
  private void init() {
    setDaemon(true);
    setName("Aroma1997Core VersionChecking");
    start();
  }
  
  public void run() {
    if (!Config.checkVersion)
      return; 
    HashSet<String> message = VersionCheck.getUpdateMessages();
    for (String msg : message)
      this.sender.addChatMessage((IChatComponent)ServerUtil.getChatForString(msg)); 
  }
}
