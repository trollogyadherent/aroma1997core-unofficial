package aroma1997.core.inventories;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class SlotItem extends AromaSlot {
  SlotItem(IInventory par1iInventory, int par2, int par3, int par4) {
    super(par1iInventory, par2, par3, par4);
  }
  
  public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
    return false;
  }
  
  public boolean canBeModified() {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isModifyable() {
    return false;
  }
}
