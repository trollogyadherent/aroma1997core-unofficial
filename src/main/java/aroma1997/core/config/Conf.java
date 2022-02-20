package aroma1997.core.config;

import aroma1997.core.log.LogHelperPre;
import java.io.File;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Conf {
  private static File configFolder;
  
  public static class Category {
    public static final String SOUND = "SOUND";
    
    public static final String DEBUG = "DEBUG";
    
    public static final String TWEAK = "TWEAKS";
  }
  
  private static boolean initialized = false;
  
  public static void init(File folder) {
    if (initialized)
      return; 
    LogHelperPre.log(Level.TRACE, "Initializing Configurations from: " + folder);
    configFolder = new File(folder, "aroma1997");
    initialized = true;
  }
  
  public static File getConfigFolder() {
    return configFolder;
  }
  
  public static File getConfigFile(String name) {
    File file = new File(configFolder, name + ".cfg");
    LogHelperPre.log(Level.TRACE, "Loaded configuration " + name + " from: " + file);
    return file;
  }
  
  public static Configuration getConfig(String name) {
    return new Configuration(getConfigFile(name));
  }
  
  public static boolean isInitialized() {
    return initialized;
  }
}
