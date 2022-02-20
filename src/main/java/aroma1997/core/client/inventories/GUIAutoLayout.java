package aroma1997.core.client.inventories;

import aroma1997.core.inventories.AromaContainer;
import aroma1997.core.inventories.AromaSlot;
import aroma1997.core.log.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIAutoLayout extends GUIAromaBasic {
  private final Container container;
  
  public GUIAutoLayout(AromaContainer container) {
    super(container);
    this.container = (Container)container;
  }
  
  protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    int minX = this.guiLeft - 9;
    int maxX = minX + this.xSize + 9;
    int minY = this.guiTop - 18;
    int maxY = minY + this.ySize;
    LogHelper.debugLog("guiTop:" + this.guiTop + "guiLeft:" + this.guiLeft + "xSize:" + this.xSize + "ysize:" + this.ySize);
    for (int k = minX; k <= maxX; k += 18) {
      for (int l = minY; l < maxY + 18; l += 18) {
        RenderHelper.Tex tex;
        if (k == minX) {
          if (l == minY) {
            tex = RenderHelper.Tex.TOPLEFT;
          } else if (l >= maxY) {
            tex = RenderHelper.Tex.BOTTOMLEFT;
          } else {
            tex = RenderHelper.Tex.LEFT;
          } 
        } else if (k >= maxX - 17) {
          if (l == minY) {
            tex = RenderHelper.Tex.TOPRIGHT;
          } else if (l >= maxY) {
            tex = RenderHelper.Tex.BOTTOMRIGHT;
          } else {
            tex = RenderHelper.Tex.RIGHT;
          } 
        } else if (l == minY) {
          tex = RenderHelper.Tex.TOP;
        } else if (l >= maxY) {
          tex = RenderHelper.Tex.BOTTOM;
        } else {
          tex = RenderHelper.Tex.MIDDLLE;
        } 
        drawTextureAt(k, l, tex);
      } 
    } 
    for (int i11 = 0; i11 < this.container.getInventory().size(); i11++) {
      Slot slot = this.container.getSlot(i11);
      drawTextureAt(slot.xDisplayPosition + this.guiLeft - 1, slot.yDisplayPosition + this.guiTop - 1, RenderHelper.Tex.SLOT);
    } 
  }
  
  protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    for (int i = 0; i < this.container.inventorySlots.size(); i++) {
      Slot slot = this.container.getSlot(i);
      if (slot != null && slot instanceof AromaSlot && !((AromaSlot)slot).isModifyable())
        drawTextureAt(slot.xDisplayPosition - 1, slot.yDisplayPosition - 1, RenderHelper.Tex.REDCROSS); 
    } 
    super.drawGuiContainerForegroundLayer(par1, par2);
  }
  
  protected void drawTextureAt(int x, int y, RenderHelper.Tex texture) {
    RenderHelper.renderTex((Gui)this, texture, x, y);
  }
  
  public void initGui() {
    int d = this.xSize % 18;
    if (d != 0)
      this.xSize += 18 - d; 
    d = this.ySize % 18;
    if (d != 0)
      this.ySize += 18 - d; 
    super.initGui();
  }
}
