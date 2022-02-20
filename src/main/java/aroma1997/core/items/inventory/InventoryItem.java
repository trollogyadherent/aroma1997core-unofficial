package aroma1997.core.items.inventory;

import aroma1997.core.client.inventories.GUIContainer;
import aroma1997.core.inventories.AromaContainer;
import aroma1997.core.inventories.ContainerBasic;
import aroma1997.core.inventories.ContainerItem;
import aroma1997.core.inventories.IAdvancedInventory;
import aroma1997.core.inventories.ISpecialInventory;
import aroma1997.core.inventories.SlotAutoAdjust;
import aroma1997.core.util.FileUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class InventoryItem implements ISpecialInventory, IAdvancedInventory {
  protected ItemStack item;
  
  private ItemStack[] items;
  
  public InventoryItem(ItemStack item) {
    this.item = item;
    if (item.getTagCompound() == null)
      item.setTagCompound(new NBTTagCompound()); 
    readFromNBT(item.stackTagCompound);
  }
  
  public void readFromNBT(NBTTagCompound nbt) {
    this.items = new ItemStack[getSizeInventory()];
    FileUtil.readFromNBT((IInventory)this, nbt);
  }
  
  public void writeToNBT(NBTTagCompound nbt) {
    FileUtil.writeToNBT((IInventory)this, nbt);
  }
  
  public ItemStack getStackInSlot(int slot) {
    if (slot >= this.items.length || slot < 0)
      return null; 
    return this.items[slot];
  }
  
  public ItemStack decrStackSize(int slot, int amount) {
    if (slot >= this.items.length || slot < 0)
      return null; 
    if (this.items[slot] != null) {
      if ((this.items[slot]).stackSize <= amount) {
        ItemStack itemStack = this.items[slot];
        this.items[slot] = null;
        markDirty();
        return itemStack;
      } 
      ItemStack itemstack = this.items[slot].splitStack(amount);
      if ((this.items[slot]).stackSize == 0)
        this.items[slot] = null; 
      markDirty();
      return itemstack;
    } 
    return null;
  }
  
  public ItemStack getStackInSlotOnClosing(int slot) {
    return getStackInSlot(slot);
  }
  
  public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
    setStackInSlotWithoutNotify(par1, par2ItemStack);
    markDirty();
  }
  
  public void setStackInSlotWithoutNotify(int slot, ItemStack stack) {
    if (slot >= this.items.length || slot < 0)
      return; 
    this.items[slot] = stack;
  }
  
  public int getInventoryStackLimit() {
    return 64;
  }
  
  public void markDirty() {
    writeToNBT(this.item.stackTagCompound);
  }
  
  public boolean isUseableByPlayer(EntityPlayer player) {
    return true;
  }
  
  public void openChest() {}
  
  public void closeChest() {}
  
  public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
    return true;
  }
  
  public AromaContainer getContainer(EntityPlayer player, int i) {
    return (AromaContainer)new ContainerItem(player.inventory, this, i);
  }
  
  public Slot getSlot(int slot, int index, int x, int y) {
    return (Slot)new SlotAutoAdjust((IInventory)this, index, x, y);
  }
  
  public void drawGuiContainerForegroundLayer(GUIContainer gui, ContainerBasic container, int par1, int par2) {}
  
  public void drawGuiContainerBackgroundLayer(GUIContainer gui, ContainerBasic container, float f, int i, int j) {}
}
