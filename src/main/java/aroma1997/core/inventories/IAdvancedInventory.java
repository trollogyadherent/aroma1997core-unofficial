package aroma1997.core.inventories;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IAdvancedInventory extends IInventory {
  void setStackInSlotWithoutNotify(int paramInt, ItemStack paramItemStack);
}
