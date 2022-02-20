package aroma1997.core.helper;

import aroma1997.core.Aroma1997Core;
import aroma1997.core.log.LogHelperPre;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

@Mod(modid = "Aroma1997CoreHelper", name = "Aroma1997Core|Helper")
public class Aroma1997CoreHelper {
  @Instance("Aroma1997CoreHelper")
  public static Aroma1997CoreHelper instance;
  
  public static Logger logger;
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = LogHelperPre.genNewLogger("Aroma1997CoreHelper");
    logger.log(Level.INFO, "Helping Aroma1997Core");
    if (!Loader.isModLoaded("Aroma1997Core")) {
      LogHelperPre.log(Level.ERROR, new String[] { "Aroma1997Core could not be loaded as a CoreMod.", "Make sure you are using a unmodified copy of Aroma1997Core.", "If it is not a .jar file, redownload it." });
      throw new RuntimeException("Aroma1997Core could not be loaded as a CoreMod.");
    } 
    (event.getModMetadata()).parent = "Aroma1997Core";
  }
  
  @EventHandler
  public void init(FMLInitializationEvent event) {}
  
  @EventHandler
  public void postInit(FMLInitializationEvent event) {
    NetworkRegistry.INSTANCE.registerGuiHandler(this, (IGuiHandler)Aroma1997Core.proxy);
  }
  
  @NetworkCheckHandler
  public boolean checkModLists(Map<String, String> modList, Side side) {
    return Aroma1997Core.instance.checkModLists(modList, side);
  }
}
