package aroma1997.core.log;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

public class AromaSpecialLogger implements ICommandSender {
  public static final AromaSpecialLogger instance = new AromaSpecialLogger();
  
  public String getCommandSenderName() {
    return "Aroma1997Core";
  }

  @Override
  public IChatComponent func_145748_c_() {
    return null;
  }

  public boolean canCommandSenderUseCommand(int i, String s) {
    return true;
  }

  @Override
  public ChunkCoordinates getPlayerCoordinates() {
    return null;
  }

  public ChunkCoordinates getCommandSenderPosition() {
    return new ChunkCoordinates(0, 0, 0);
  }
  
  public World getEntityWorld() {
    return null;
  }
  
  public IChatComponent getFormattedCommandSenderName() {
    return null;
  }
  
  public void addChatMessage(IChatComponent var1) {
    LogHelperPre.log(Level.INFO, var1.getUnformattedText());
  }
}
