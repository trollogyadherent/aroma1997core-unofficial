package aroma1997.core.client.inventories;

import aroma1997.core.inventories.AromaContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

@SideOnly(Side.CLIENT)
public class GUIAromaBasic extends GuiContainer {
  private ArrayList<AbstractSpecialImage> images = new ArrayList<AbstractSpecialImage>();
  
  private final AromaContainer container;
  
  public GUIAromaBasic(AromaContainer par1Container) {
    super((Container)par1Container);
    this.container = par1Container;
  }
  
  public FontRenderer getFontRender() {
    return this.fontRendererObj;
  }
  
  public AromaContainer getContainer() {
    return this.container;
  }
  
  protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    this.container.drawGuiContainerForegroundLayer(this, par1, par2);
    for (AbstractSpecialImage image : this.images);
  }
  
  public void setXSize(int s) {
    this.xSize = s;
  }
  
  public void setYSize(int s) {
    this.ySize = s;
  }
  
  public void addSpecialImage(AbstractSpecialImage image) {
    this.images.add(image);
  }
  
  protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {}
}
