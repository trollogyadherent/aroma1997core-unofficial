package aroma1997.core.version;

import aroma1997.core.Tags;
import aroma1997.core.log.AromaSpecialLogger;
import aroma1997.core.log.LogHelperPre;
import aroma1997.core.web.xml.XMLParser;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.command.ICommandSender;
import org.apache.logging.log4j.Level;
import org.w3c.dom.Node;

public class VersionCheck {
  private static boolean hasLoogedError = false;
  
  public static VersionStates checkUpdates(String url, String compareVersion, String compareModID) {
    try {
      XMLParser xml = new XMLParser((new URL(url)).openStream());
      for (Map.Entry<String, Node> entry : (Iterable<Map.Entry<String, Node>>)xml.getAll().entrySet()) {
        if ((((String)entry.getKey()).split("\\.")).length != 2)
          continue; 
        String mod = ((String)entry.getKey()).split("\\.")[0];
        String mcversion = ((String)entry.getKey()).split("\\.")[1];
        if (mod.equalsIgnoreCase(compareModID) && 
          mcversion.equalsIgnoreCase("MC" + Tags.MCVERSION.replace(".", ""))) {
          if (((Node)entry.getValue()).getTextContent().equalsIgnoreCase(compareVersion))
            return VersionStates.LATEST; 
          return VersionStates.OUTDATED;
        } 
      } 
    } catch (Exception e) {
      if (!hasLoogedError) {
        LogHelperPre.log(Level.ERROR, "Failed to do VersionChecking. Check your network connection.");
        hasLoogedError = true;
      } 
    } 
    return VersionStates.FAILED;
  }
  
  private static HashSet<VersionInfo> mods = new HashSet<VersionInfo>();
  
  public static void registerVersionChecker(String compareModID, String compareVersion) {
    if (compareVersion.toLowerCase().contains("b") || compareVersion.toLowerCase().contains("$")) {
      LogHelperPre.log(Level.INFO, "Using a beta-version of the mod: " + compareModID + " not registering VersionChecker.");
      return;
    } 
    mods.add(new VersionInfo(compareModID, compareVersion));
  }
  
  public static void registerVersionChecker(String compareModID, String compareVersion, String xmlFile, String updateURL) {
    if (compareVersion.toLowerCase().contains("b") || compareVersion.toLowerCase().contains("$")) {
      LogHelperPre.log(Level.INFO, "Using a beta-version of the mod: " + compareModID + " not registering VersionChecker.");
      return;
    } 
    mods.add(new VersionInfo(compareModID, compareVersion, updateURL, xmlFile));
  }
  
  static HashSet<String> getUpdateMessages() {
    HashSet<String> messages = new HashSet<String>();
    int i = 0;
    for (Object info : mods.toArray()) {
      if (!(info instanceof VersionInfo)) {
        LogHelperPre.logException("Failed to do VersionChecking.", new RuntimeException("Failed to do versionChecking"));
        return null;
      } 
      VersionInfo versInfo = (VersionInfo)info;
      VersionStates state = checkUpdates(versInfo.getXMLURL(), versInfo.getVersion(), versInfo.getModID());
      if (state == VersionStates.OUTDATED) {
        messages.add("The mod " + versInfo.getModID() + " is outdated. Please update it from " + versInfo.updateURL());
      } else if (state == VersionStates.FAILED) {
        i++;
      } 
    } 
    if (i == mods.size() && mods.size() != 0)
      messages.add("Aroma1997Core failed to do VersionChecking. Either there is something wrong with your network connection or with my server."); 
    return messages;
  }
  
  public static void doVersionCheck(ICommandSender sender) {
    new ThreadVersionChecking(sender);
  }
  
  public static void doVersionCheck() {
    new ThreadVersionChecking((ICommandSender)AromaSpecialLogger.instance);
  }
}
