package aroma1997.core.inventories;

import aroma1997.core.client.inventories.GUIContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public interface ISpecialInventory extends IInventory, ISpecialGUIProvider {
  int getSizeInventory();
  
  Slot getSlot(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  @SideOnly(Side.CLIENT)
  void drawGuiContainerForegroundLayer(GUIContainer paramGUIContainer, ContainerBasic paramContainerBasic, int paramInt1, int paramInt2);
  
  @SideOnly(Side.CLIENT)
  void drawGuiContainerBackgroundLayer(GUIContainer paramGUIContainer, ContainerBasic paramContainerBasic, float paramFloat, int paramInt1, int paramInt2);
}
