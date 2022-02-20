package aroma1997.core.items.usermanual;

import aroma1997.core.client.inventories.GUIAromaBasic;
import aroma1997.core.client.inventories.GUIAutoLayout;
import aroma1997.core.inventories.AromaContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerUserManual extends AromaContainer {
  @SideOnly(Side.CLIENT)
  public GuiContainer getContainer() {
    return (GuiContainer)new GUIAutoLayout(this);
  }
  
  public void drawGuiContainerForegroundLayer(GUIAromaBasic gui, int par1, int par2) {}
  
  public boolean canInteractWith(EntityPlayer var1) {
    return true;
  }
}
