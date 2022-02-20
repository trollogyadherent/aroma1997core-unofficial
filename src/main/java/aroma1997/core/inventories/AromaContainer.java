package aroma1997.core.inventories;

import aroma1997.core.client.inventories.GUIAromaBasic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class AromaContainer extends Container {
  public ItemStack slotClick(int slotNum, int par2, int par3, EntityPlayer player) {
    Slot slot = null;
    if (slotNum == -999)
      return super.slotClick(slotNum, par2, par3, player); 
    if (slotNum < 0)
      return null; 
    if (par3 == 2) {
      slot = getSlot(par2);
      if (slot instanceof AromaSlot) {
        AromaSlot aslot = (AromaSlot)slot;
        if (!aslot.canBeModified())
          return null; 
      } 
    } 
    slot = getSlot(slotNum);
    if (slot instanceof AromaSlot) {
      AromaSlot aslot = (AromaSlot)slot;
      return aslot.slotClicked(this, slotNum, par2, par3, player);
    } 
    return slotClickedNormal(slotNum, par2, par3, player);
  }
  
  public ItemStack slotClickedNormal(int slotNum, int par2, int par3, EntityPlayer player) {
    return super.slotClick(slotNum, par2, par3, player);
  }
  
  @SideOnly(Side.CLIENT)
  public abstract GuiContainer getContainer();
  
  @SideOnly(Side.CLIENT)
  public abstract void drawGuiContainerForegroundLayer(GUIAromaBasic paramGUIAromaBasic, int paramInt1, int paramInt2);
}
