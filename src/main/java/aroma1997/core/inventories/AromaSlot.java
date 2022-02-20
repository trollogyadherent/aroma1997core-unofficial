package aroma1997.core.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class AromaSlot extends Slot {
  public AromaSlot(IInventory par1iInventory, int par2, int par3, int par4) {
    super(par1iInventory, par2, par3, par4);
  }
  
  public ItemStack slotClicked(AromaContainer container, int slot, int par2, int par3, EntityPlayer player) {
    if (canBeModified())
      return container.slotClickedNormal(slot, par2, par3, player); 
    return null;
  }
  
  public boolean canTakeStack(EntityPlayer p_82869_1_) {
    return canBeModified();
  }
  
  public boolean canBeModified() {
    return true;
  }
  
  public boolean isModifyable() {
    return true;
  }
}
