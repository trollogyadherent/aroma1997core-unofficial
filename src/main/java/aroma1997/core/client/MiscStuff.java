package aroma1997.core.client;

import aroma1997.core.client.events.ClientEventListener;
import aroma1997.core.client.util.Colors;
import aroma1997.core.log.LogHelperPre;
import aroma1997.core.web.xml.XMLParser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Node;

@SideOnly(Side.CLIENT)
public class MiscStuff {
  private static HashMap<String, String> capes = new HashMap<String, String>();
  
  private static HashMap<String, Colors> colors = new HashMap<String, Colors>();
  
  public static void init() {
    (new Thread(new Load(), "Aroma1997Core-InitThread")).start();
  }
  
  private static void load() {
    try {
      XMLParser xml = new XMLParser((new URL("http://a.aroma1997.org/mcmods/info.xml")).openStream());
      for (Map.Entry<String, Node> entry : (Iterable<Map.Entry<String, Node>>)xml.getAll().entrySet()) {
        if ((((String)entry.getKey()).split("\\.")).length != 2)
          continue; 
        String username = ((String)entry.getKey()).split("\\.")[0];
        String part = ((String)entry.getKey()).split("\\.")[1];
        if (part.equalsIgnoreCase("cape") && !((Node)entry.getValue()).getTextContent().isEmpty()) {
          setCape(username, ((Node)entry.getValue()).getTextContent());
          continue;
        } 
        if (part.equalsIgnoreCase("color") && !((Node)entry.getValue()).getTextContent().isEmpty())
          setColor(username, ((Node)entry.getValue()).getTextContent()); 
      } 
    } catch (Exception e) {
      LogHelperPre.logException("Failed to setup Capes.", e);
    } 
    ClientEventListener.resetCounter();
    ThreadDownloadCape.resetThreads();
  }
  
  public static void setCape(String player, String url) {
    player = player.toLowerCase();
    if (capes.containsKey(player))
      capes.remove(player); 
    capes.put(player, url);
  }
  
  public static String getCape(String player) {
    return capes.get(player.toLowerCase());
  }
  
  public static boolean hasCape(String player) {
    return (capes.containsKey(player.toLowerCase()) && capes.get(player.toLowerCase()) != null);
  }
  
  public static void setColor(String player, String color) {
    setColor(player, Colors.valueOf(color));
  }
  
  public static void setColor(String player, Colors color) {
    player = player.toLowerCase();
    if (colors.containsKey(player))
      colors.remove(player); 
    colors.put(player, color);
  }
  
  public static Colors getColor(String player) {
    return colors.get(player.toLowerCase());
  }
  
  public static boolean hasColor(String player) {
    return (colors.containsKey(player.toLowerCase()) && colors.get(player.toLowerCase()) != null);
  }
  
  public static void reloadAll() {
    capes.clear();
    colors.clear();
    init();
  }
  
  private static class Load implements Runnable {
    private Load() {}
    
    public void run() {
      MiscStuff.load();
    }
  }
}
