package aroma1997.core.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class InvUtil {
  public static ItemStack putIntoFirstSlot(IInventory inv, ItemStack item, boolean simulate) {
    return putIntoFirstSlot(inv, item, simulate, null);
  }
  
  public static ItemStack putIntoFirstSlot(IInventory inv, ItemStack item, boolean simulate, ForgeDirection side) {
    if (item == null)
      return null; 
    if (inv == null)
      return item; 
    ItemStack item1 = item.copy();
    int[] slots = getAccessibleSlots(inv, side);
    int i;
    for (i = 0; i < slots.length; i++) {
      ItemStack itemtmp = inv.getStackInSlot(slots[i]);
      if (itemtmp != null) {
        item1 = mergeItems(itemtmp, item1, simulate);
        if (item1 == null) {
          if (!simulate)
            inv.markDirty(); 
          return null;
        } 
      } 
    } 
    for (i = 0; i < slots.length; i++) {
      ItemStack itemtmp = inv.getStackInSlot(slots[i]);
      if (itemtmp == null && canInsert(inv, item1, i, side)) {
        if (!simulate) {
          inv.setInventorySlotContents(slots[i], item1);
          inv.markDirty();
        } 
        return null;
      } 
    } 
    inv.markDirty();
    return item1;
  }
  
  public static ItemStack mergeItems(ItemStack main, ItemStack merge, boolean simulate) {
    if (ItemUtil.areItemsSameMatching(main, merge, new ItemUtil.ItemMatchCriteria[] { ItemUtil.ItemMatchCriteria.ID, ItemUtil.ItemMatchCriteria.DAMAGE, ItemUtil.ItemMatchCriteria.NBT })) {
      if (main.stackSize >= main.getMaxStackSize())
        return merge; 
      int diff = main.getMaxStackSize() - main.stackSize;
      if (diff >= merge.stackSize) {
        if (!simulate)
          main.stackSize += merge.stackSize; 
        return null;
      } 
      if (simulate) {
        merge = merge.copy();
      } else {
        main.stackSize += diff;
      } 
      merge.stackSize -= diff;
      return merge;
    } 
    return merge;
  }
  
  public static void clearInventory(IInventory inv) {
    for (int i = 0; i < inv.getSizeInventory(); i++) {
      ItemStack item = inv.getStackInSlot(i);
      if (item != null)
        inv.decrStackSize(i, item.stackSize); 
    } 
  }
  
  public static int[] getAccessibleSlots(IInventory inv, ForgeDirection side) {
    if (inv == null)
      return null; 
    if (inv instanceof ISidedInventory && side != null)
      return ((ISidedInventory)inv).getAccessibleSlotsFromSide(side.ordinal());//getSlotsForFace(side.ordinal());
    int[] slots = new int[inv.getSizeInventory()];
    for (int i = 0; i < slots.length; i++)
      slots[i] = i; 
    return slots;
  }
  
  public static boolean canInsert(IInventory inv, ItemStack item, int slot, ForgeDirection side) {
    if (inv == null || item == null)
      return false; 
    if (side == null)
      return true; 
    if (inv instanceof ISidedInventory)
      return ((ISidedInventory)inv).canInsertItem(slot, item, side.ordinal()); 
    return true;
  }
  
  public static boolean canExtract(IInventory inv, ItemStack item, int slot, ForgeDirection side) {
    if (inv == null || item == null)
      return false; 
    if (side == null)
      return true; 
    if (inv instanceof ISidedInventory)
      return ((ISidedInventory)inv).canExtractItem(slot, item, slot); 
    return true;
  }
  
  public static int getFirstItem(IInventory inv, Class<?> claSS) {
    return getFirstItem(inv, claSS, (ForgeDirection)null);
  }
  
  public static int getFirstItem(IInventory inv, Class<?> claSS, ForgeDirection side) {
    return getFirstItem(inv, claSS, side, new DefaultFilter());
  }
  
  public static int getFirstItem(IInventory inv, Class<?> claSS, ForgeDirection side, IFilter filter) {
    int[] slots = getAccessibleSlots(inv, side);
    for (int i = 0; i < slots.length; i++) {
      ItemStack item = inv.getStackInSlot(slots[i]);
      if (item != null && claSS.isInstance(item.getItem()) && filter.isOk(item))
        return slots[i]; 
    } 
    return -1;
  }
  
  @Deprecated
  public static ItemStack getFirstItem(IInventory inv, ItemStack item) {
    return getFirstItem(inv, item, false);
  }
  
  public static ItemStack getFirstItem(IInventory inv, ItemStack item, boolean simulate) {
    return getFirstItem(inv, item, simulate, (ForgeDirection)null);
  }
  
  public static ItemStack getFirstItem(IInventory inv, ItemStack item, boolean simulate, ForgeDirection side) {
    return getFirstItem(inv, item, simulate, side, new DefaultFilter());
  }
  
  public static ItemStack getFirstItem(IInventory inv, ItemStack item, boolean simulate, ForgeDirection side, IFilter filter) {
    int[] slots = getAccessibleSlots(inv, side);
    for (int i = 0; i < slots.length; i++) {
      if (canExtract(inv, item, slots[i], side) && ItemUtil.areItemsSameMatching(item, inv.getStackInSlot(slots[i]), new ItemUtil.ItemMatchCriteria[] { ItemUtil.ItemMatchCriteria.ID, ItemUtil.ItemMatchCriteria.DAMAGE, ItemUtil.ItemMatchCriteria.NBT }) && filter.isOk(inv.getStackInSlot(slots[i]))) {
        if (simulate)
          return inv.getStackInSlot(slots[i]); 
        return inv.decrStackSize(slots[i], Math.min((inv.getStackInSlot(slots[i])).stackSize, item.stackSize));
      } 
    } 
    return null;
  }
  
  public static boolean hasSpace(IInventory inv) {
    for (int i = 0; i < inv.getSizeInventory(); i++) {
      if (inv.getStackInSlot(i) == null)
        return true; 
    } 
    return false;
  }
  
  private static class DefaultFilter implements IFilter {
    private DefaultFilter() {}
    
    public boolean isOk(ItemStack items) {
      return true;
    }
  }
  
  public static interface IFilter {
    boolean isOk(ItemStack param1ItemStack);
  }
}
