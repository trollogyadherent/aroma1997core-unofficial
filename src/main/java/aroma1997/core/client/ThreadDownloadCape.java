package aroma1997.core.client;

import aroma1997.core.log.LogHelperPre;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.apache.logging.log4j.Level;

@SideOnly(Side.CLIENT)
public class ThreadDownloadCape extends Thread {
  private String username;
  
  private String capeURL;
  
  private ResourceLocation capeRL;
  
  private ThreadDownloadImageData capeImage;
  
  public boolean finishedLoading = false;
  
  public ThreadDownloadCape(String username) {
    threadlist.put(username, this);
    this.username = username;
    this.capeURL = MiscStuff.getCape(username);
    setDaemon(true);
    setName("Cape Downloader: " + username);
  }
  
  public void run() {
    try {
      sleep(2000L);
    } catch (InterruptedException e) {}
    LogHelperPre.log(Level.TRACE, "Adjusting skin for player: " + this.username);
    downloadImageData();
    LogHelperPre.log(Level.TRACE, "Done adjusting skin for player: " + this.username);
  }
  
  private void downloadImageData() {
    String[] n = this.capeURL.split("/");
    this.capeRL = new ResourceLocation("cloak/" + StringUtils.stripControlCodes(this.username + "-" + n[n.length - 1]));
    this.capeImage = getDownloadImage(this.capeRL, this.capeURL, (ResourceLocation)null, (IImageBuffer)null);
    this.finishedLoading = true;
  }
  
  private static ThreadDownloadImageData getDownloadImage(ResourceLocation par0ResourceLocation, String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer) {
    TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
    Object object = texturemanager.getTexture(par0ResourceLocation);
    if (object == null) {
      object = new ThreadDownloadImageData(null, par1Str, par2ResourceLocation, par3IImageBuffer);
      texturemanager.loadTexture(par0ResourceLocation, (ITextureObject)object);
    } 
    return (ThreadDownloadImageData)object;
  }
  
  public ThreadDownloadImageData getCurrentCapeImage() {
    return this.capeImage;
  }
  
  public ResourceLocation getCurrentCapeRL() {
    return this.capeRL;
  }
  
  static void resetThreads() {
    threadlist.clear();
  }
  
  private static HashMap<String, ThreadDownloadCape> threadlist = new HashMap<String, ThreadDownloadCape>();
  
  public static ThreadDownloadCape getThread(String username) {
    if (threadlist.containsKey(username))
      return threadlist.get(username); 
    ThreadDownloadCape thread = new ThreadDownloadCape(username);
    thread.start();
    return thread;
  }
}
