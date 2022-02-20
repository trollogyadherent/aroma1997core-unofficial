package aroma1997.core.log;

import aroma1997.core.util.Util;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHelperPre {
  private static Logger logger = LogManager.getLogger("Aroma1997Core");
  
  public static void init() {
    logger.log(Level.INFO, "Logger initialized.");
  }
  
  public static void log(Level level, String message) {
    logger.log(level, message);
  }
  
  public static void log(Level level, String[] messages) {
    for (String message : messages)
      log(level, message); 
  }
  
  public static Logger getLogger() {
    return logger;
  }
  
  public static Logger genNewLogger(String name) {
    Logger newLogger = LogManager.getLogger(name);
    return newLogger;
  }
  
  public static void debugLog(String message) {
    if (!Util.isDev())
      return; 
    log(Level.OFF, message);
  }
  
  public static void logException(String msg, Throwable t) {
    logger.log(Level.ERROR, msg, t);
  }
}
