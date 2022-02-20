package aroma1997.core.util;

import aroma1997.core.Tags;
import aroma1997.core.config.Config;
import aroma1997.core.coremod.CoreMod;
import cpw.mods.fml.common.FMLCommonHandler;
import java.io.File;
import net.minecraft.server.MinecraftServer;

public class Util {
  @Deprecated
  public static MinecraftServer getServer() {
    return FMLCommonHandler.instance().getMinecraftServerInstance();
  }
  
  @Deprecated
  public static File getMinecraftFolder() {
    return CoreMod.mcLocation;
  }
  
  public static File getWorldFolder() {
    String world = (getServer().isDedicatedServer() ? "" : "saves/") + getWorldName();
    return new File(CoreMod.mcLocation, world);
  }
  
  public static String getWorldName() {
    return getServer().getFolderName();
  }
  
  public static boolean isClient() {
    return FMLCommonHandler.instance().getEffectiveSide().isClient();
  }
  
  public static int getContentLength(Object[] object) {
    int i = 1;
    for (Object obj : object) {
      if (obj == null)
        return i; 
      i++;
    } 
    return object.length;
  }
  
  public static boolean isDev() {
    return (Tags.VERSION.equals("${version}") || Tags.VERSION.contains("b") || Config.debugEnabled());
  }
}
