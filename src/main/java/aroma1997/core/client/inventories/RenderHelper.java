package aroma1997.core.client.inventories;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderHelper {
  public enum Tex {
    SLOT(162, 0, 18, 18),
    TOPLEFT(36, 0, 18, 18),
    TOP(108, 0, 18, 18),
    TOPRIGHT(54, 0, 18, 18),
    RIGHT(90, 0, 18, 18),
    BOTTOMRIGHT(18, 0, 18, 18),
    BOTTOM(126, 0, 18, 18),
    BOTTOMLEFT(0, 0, 18, 18),
    LEFT(72, 0, 18, 18),
    MIDDLLE(144, 0, 18, 18),
    REDCROSS(180, 0, 18, 18),
    RANDOM(198, 0, 18, 18);
    
    private int xPos;
    
    private int yPos;
    
    private int xLength;
    
    private int yLength;
    
    Tex(int xPos, int yPos, int xLength, int yLength) {
      this.xPos = xPos;
      this.yPos = yPos;
      this.xLength = xLength;
      this.yLength = yLength;
    }
  }
  
  private static ResourceLocation guiRL = new ResourceLocation("Aroma1997Core:textures/gui/gui.png");
  
  public static void renderTex(Gui gui, Tex tex, int posx, int posy) {
    bindTexture(guiRL);
    gui.drawTexturedModalRect(posx, posy, tex.xPos, tex.yPos, tex.xLength, tex.yLength);
  }
  
  public static void bindTexture(ResourceLocation rl) {
    (Minecraft.getMinecraft()).renderEngine.bindTexture(rl);
  }
}
