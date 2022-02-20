package aroma1997.core;

import aroma1997.core.util.BaseReference;
import com.google.common.base.Throwables;
import java.io.InputStream;
import java.util.Properties;

public class Tags extends BaseReference {
  private static String getProp(String id) {
    if (!isPropSetup) {
      try {
        InputStream stream = Tags.class.getResourceAsStream("reference.properties");
        prop.load(stream);
        stream.close();
      } catch (Exception e) {
        Throwables.propagate(e);
      } 
      isPropSetup = true;
    } 
    return prop.getProperty(id);
  }
  
  private static boolean isPropSetup = false;
  
  private static final Properties prop = new Properties();
  
  public static final String MOD_ID = "GRADLETOKEN_MODID";
  
  public static final String MOD_NAME = "GRADLETOKEN_MODNAME";
  
  public static final String VERSION = "GRADLETOKEN_VERSION";
  
  public static final String MCVERSION = "1.7.10";
  
  public static final String VERSION_CHECK_URL = "http://a.aroma1997.org/mcmods/version.xml";
  
  public static final String UPDATE_URL = "http://tinyurl.com/aroma1997";
  
  public static class ReferenceHelper {
    public static final String MOD_ID = "Aroma1997CoreHelper";
    
    public static final String MOD_NAME = "Aroma1997Core|Helper";
  }
}
