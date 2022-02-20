package aroma1997.core.config;

import aroma1997.core.util.Util;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;

public class Config {
  public static boolean checkVersion;
  
  private static Configuration config;
  
  private static boolean muffleSounds;
  
  private static List<String> soundsTomuffleList;
  
  private static boolean showHidden;
  
  private static boolean debugEnabled;

  private static boolean registerHammer;
  
  public static void init() {
    config = Conf.getConfig("Aroma1997Core");
    load();
  }
  
  public static void load() {
    config.load();
    checkVersion = config.get("general", "checkVersion", true, "If Aroma1997Core should run a VersionCheck.").getBoolean(true);
    debugEnabled = config.get("DEBUG", "debuggingEnabled", false).getBoolean(false);
    registerHammer = config.get("general", "registerHammer", true).getBoolean(true);
    if (Util.isClient()) {
      muffleSounds = config.get("SOUND", "muffleSounds", false, "If true, some sounds can be disabled.").getBoolean(false);
      String[] soundsToMuffle = config.get("SOUND", "soundsToMuffle", new String[] { "ambient.weather.rain" }, "If sounds should get muffled, the sounds to muffle.").getStringList();
      soundsTomuffleList = Arrays.asList(soundsToMuffle);
      if (config.hasKey("general", "showHidden")) {
        showHidden = config.get("general", "showHidden", false, "If NEI should show hidden recipes").getBoolean(false);
      } else {
        showHidden = false;
      } 
    } 
    if (config.hasChanged())
      config.save(); 
  }
  
  public static boolean muffleSounds() {
    return muffleSounds;
  }
  
  public static List<String> soundsToMuffle() {
    return soundsTomuffleList;
  }
  
  public static boolean debugEnabled() {
    return debugEnabled;
  }

  public static boolean registerHammer() {
    return registerHammer;
  }

  @SideOnly(Side.CLIENT)
  public static boolean shouldShowHiddenRecipes() {
    return ((Minecraft.getMinecraft()).thePlayer.getCommandSenderName().equalsIgnoreCase("Aroma1997") || showHidden);
  }
}
