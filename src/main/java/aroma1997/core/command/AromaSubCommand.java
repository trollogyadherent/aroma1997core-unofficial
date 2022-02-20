package aroma1997.core.command;

import aroma1997.core.util.ServerUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public abstract class AromaSubCommand extends AromaBaseCommand {
  private ArrayList<CommandBase> subCommands = new ArrayList<CommandBase>();
  
  public AromaSubCommand addSubCommand(CommandBase command) {
    this.subCommands.add(command);
    return this;
  }
  
  protected void processCommandSelf(ICommandSender sender, String[] args) {
    ServerUtil.printCommandUsage(sender, (ICommand)this);
  }
  
  public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] arg) {
    if (arg == null || arg.length == 0)
      return this.subCommands; 
    ArrayList<String> ret = new ArrayList();
    for (CommandBase command : this.subCommands) {
      if (command.getCommandName().toLowerCase().startsWith(arg[0].toLowerCase()) && command.canCommandSenderUseCommand(par1ICommandSender))
        ret.add(command.getCommandName()); 
    } 
    return ret;
  }
  
  private CommandBase getSubCommand(String[] args) {
    if (args == null || args.length == 0)
      return null; 
    for (CommandBase command : this.subCommands) {
      if (command.getCommandName().equalsIgnoreCase(args[0]))
        return command; 
    } 
    return null;
  }
  
  private String[] getSubArgs(String[] args) {
    if (args == null || args.length <= 1)
      return null; 
    String[] ret = new String[args.length - 2];
    for (int i = 0; i < args.length - 1; i++)
      ret[i] = args[i + 1]; 
    return ret;
  }
  
  public final void processCommand(ICommandSender sender, String[] args) {
    CommandBase cmd = getSubCommand(args);
    if (cmd != null) {
      if (cmd.canCommandSenderUseCommand(sender)) {
        cmd.processCommand(sender, getSubArgs(args));
      } else {
        ServerUtil.printPermissionDenied(sender);
      } 
    } else {
      processCommandSelf(sender, args);
    } 
  }
  
  public boolean isUsernameIndex(String[] args, int num) {
    CommandBase cmd = getSubCommand(args);
    if (cmd != null)
      return isUsernameIndex(getSubArgs(args), num - 1); 
    return false;
  }
  
  public String getSelfDescription(ICommandSender sender) {
    return null;
  }
  
  public final String getCommandUsage(ICommandSender sender) {
    String str = "";
    if (getSelfDescription(sender) != null)
      str = getSelfDescription(sender); 
    for (CommandBase command : this.subCommands) {
      if (command.canCommandSenderUseCommand(sender))
        str = str + " OR " + command.getCommandUsage(sender); 
    } 
    return str;
  }
}
