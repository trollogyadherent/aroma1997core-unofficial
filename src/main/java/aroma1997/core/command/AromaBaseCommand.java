package aroma1997.core.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;

public abstract class AromaBaseCommand extends CommandBase {
  public int compareTo(ICommand par1ICommand) {
    return getCommandName().compareTo(par1ICommand.getCommandName());
  }
  
  public int compareTo(Object par1Obj) {
    return compareTo((ICommand)par1Obj);
  }
}
