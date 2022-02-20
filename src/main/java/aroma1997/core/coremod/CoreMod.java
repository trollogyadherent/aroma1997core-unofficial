package aroma1997.core.coremod;

import aroma1997.core.config.Conf;
import aroma1997.core.log.LogHelperPre;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;
import org.apache.logging.log4j.Level;

@TransformerExclusions({"aroma1997.core.coremod", "aroma1997.core.coremod.asm"})
public class CoreMod implements IFMLLoadingPlugin, IFMLCallHook {
  public static File mcLocation;
  
  public static File coremodLocation;
  
  public static boolean runtimeDeobfuscationEnabled;
  
  public String[] getASMTransformerClass() {
    return null;
  }
  
  public String getModContainerClass() {
    return "aroma1997.core.Aroma1997Core";
  }
  
  public String getSetupClass() {
    return "aroma1997.core.coremod.CoreMod";
  }
  
  public void injectData(Map<String, Object> data) {
    for (String name : data.keySet()) {
      try {
        Field field = CoreMod.class.getField(name);
        if (!field.isAccessible())
          field.setAccessible(true); 
        field.set(this, data.get(name));
      } catch (NoSuchFieldException e) {
      
      } catch (SecurityException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        LogHelperPre.logException("Injection data does not match! " + name + " " + data.get(name), e);
      } catch (IllegalAccessException e) {}
    } 
    LogHelperPre.log(Level.INFO, "Finished data injection.");
  }
  
  public String getAccessTransformerClass() {
    return "aroma1997.core.coremod.asm.AromaAccessTransformer";
  }
  
  public Void call() throws Exception {
    if (mcLocation != null) {
      Conf.init(new File(mcLocation, "config"));
    } else {
      LogHelperPre.log(Level.WARN, "Could not setup config.");
    } 
    return null;
  }
}
