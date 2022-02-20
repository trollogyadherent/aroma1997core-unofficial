package aroma1997.core.items.inventory;

import aroma1997.core.inventories.AromaContainer;
import aroma1997.core.inventories.ISpecialGUIProvider;
import aroma1997.core.items.AromicItem;
import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class ItemInventory<I extends ItemInventory> extends AromicItem implements ISpecialGUIProvider {
  private HashMap<ItemStack, I> invs;
  
  public ItemInventory() {
    this.invs = new HashMap<ItemStack, I>();
    setMaxStackSize(1);
  }
  
  public AromaContainer getContainer(EntityPlayer player, int i) {
    I inv = getInventory(player.inventory.getStackInSlot(i));
    return inv.getContainer(player, i);
  }
  
  protected I getInventory(ItemStack item) {
    if (!this.invs.containsKey(item)) {
      I inv = generateInventory(item);
      this.invs.put(item, inv);
      return inv;
    } 
    return this.invs.get(item);
  }
  
  protected abstract I generateInventory(ItemStack paramItemStack);
}
