package aroma1997.core.inventories;

import aroma1997.core.util.ItemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotGhost extends AromaSlot {
  private static final int MAX_STACKSIZE = 127;
  
  public SlotGhost(IInventory par1iInventory, int par2, int par3, int par4) {
    super(par1iInventory, par2, par3, par4);
  }
  
  public ItemStack slotClicked(AromaContainer container, int slot, int par2, int par3, EntityPlayer player) {
    ItemStack inHand = player.inventory.getItemStack();
    if (par2 == 2 && par3 == 3) {
      putStack(null);
      return inHand;
    } 
    if (inHand == null) {
      if (!getHasStack())
        return inHand; 
      if (par2 == 0) {
        if (par3 == 0) {
          decrStackSize(1);
        } else if (par3 == 1) {
          decrStackSize(10);
        } 
      } else if (par2 == 1) {
        if (par3 == 0) {
          (getStack()).stackSize = Math.min((getStack()).stackSize + 1, maxStackSize());
          onSlotChanged();
        } else if (par3 == 1) {
          (getStack()).stackSize = Math.min((getStack()).stackSize + 10, maxStackSize());
          onSlotChanged();
        } 
      } 
      return inHand;
    } 
    if (!getHasStack() && isItemValid(inHand)) {
      putStack(inHand.copy());
      if ((getStack()).stackSize > maxStackSize())
        (getStack()).stackSize = maxStackSize(); 
      return inHand;
    } 
    if (ItemUtil.areItemsSameMatching(getStack(), inHand, new ItemUtil.ItemMatchCriteria[] { ItemUtil.ItemMatchCriteria.ID, ItemUtil.ItemMatchCriteria.DAMAGE, ItemUtil.ItemMatchCriteria.DAMAGE })) {
      (getStack()).stackSize = Math.min((getStack()).stackSize + inHand.stackSize, maxStackSize());
      onSlotChanged();
      return inHand;
    } 
    return inHand;
  }
  
  protected int maxStackSize() {
    return 127;
  }
  
  public boolean canBeModified() {
    return false;
  }
  
  public boolean isModifyable() {
    return true;
  }
}
