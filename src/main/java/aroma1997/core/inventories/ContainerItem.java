package aroma1997.core.inventories;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerItem extends ContainerBasic {
  final int numb;
  
  public ContainerItem(InventoryPlayer playerInv, ISpecialInventory inv, int c) {
    this(playerInv, inv, c, true);
  }
  
  protected ContainerItem(InventoryPlayer playerInv, ISpecialInventory inv, int c, boolean shouldLayout) {
    super(playerInv, inv, false);
    this.numb = c;
    if (shouldLayout)
      layoutContainer(); 
  }
  
  protected Slot getSlotForInv(InventoryPlayer inv, int index, int x, int y) {
    if (this.numb == index)
      return new SlotItem((IInventory)inv, index, x, y); 
    return new Slot((IInventory)inv, index, x, y);
  }
}
