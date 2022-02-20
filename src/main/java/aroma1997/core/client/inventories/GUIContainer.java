package aroma1997.core.client.inventories;

import aroma1997.core.inventories.AromaContainer;
import aroma1997.core.inventories.ContainerBasic;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GUIContainer extends GUIAutoLayout {
  private ContainerBasic container;
  
  public GUIContainer(ContainerBasic container) {
    super(container);
    this.container = container;
    this.xSize = Math.max(container.getAmountPerRow(), 9) * 18 + 2;
    if (Loader.isModLoaded("inventorytweaks") && container.isLargeChest())
      this.xSize += 18; 
    this.ySize = 95 + container.getAmountRows() * 18;
  }
  
  protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    super.drawGuiContainerBackgroundLayer(f, i, j);
    this.container.inv.drawGuiContainerBackgroundLayer(this, this.container, f, i, j);
  }
  
  protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    this.container.inv.drawGuiContainerForegroundLayer(this, this.container, par1, par2);
    this.fontRendererObj.drawString(this.container.inv.hasCustomInventoryName() ? this.container.inv.getInventoryName() : StatCollector.translateToLocal(this.container.inv.getInventoryName()), 8, 35 + (3 - this.container.getAmountRows()) * 18 + this.container.getYOffset(), 4210752);
    this.fontRendererObj.drawString(this.container.playerInv.hasCustomInventoryName() ? this.container.playerInv.getInventoryName() : StatCollector.translateToLocal(this.container.playerInv.getInventoryName()), 8, 100 + this.container.getYOffset(), 4210752);
    super.drawGuiContainerForegroundLayer(par1, par2);
  }
  
  public void initGui() {
    super.initGui();
    this.guiTop += 10;
  }
}
