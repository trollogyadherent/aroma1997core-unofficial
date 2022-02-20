package aroma1997.core.inventories;

import aroma1997.core.client.inventories.GUIAromaBasic;
import aroma1997.core.client.inventories.GUIContainer;
import aroma1997.core.util.ItemUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import invtweaks.api.container.ChestContainer;
import invtweaks.api.container.ChestContainer.IsLargeCallback;
import invtweaks.api.container.ChestContainer.RowSizeCallback;
import invtweaks.api.container.ContainerSection;
import invtweaks.api.container.ContainerSectionCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

@ChestContainer
public class ContainerBasic extends AromaContainer {
  public final InventoryPlayer playerInv;
  
  public final ISpecialInventory inv;
  
  private boolean containerLayouted = false;
  
  public ContainerBasic(InventoryPlayer playerInv, ISpecialInventory inv) {
    this(playerInv, inv, true);
  }
  
  protected ContainerBasic(InventoryPlayer playerInv, ISpecialInventory inv, boolean shouldLayout) {
    this.playerInv = playerInv;
    this.inv = inv;
    if (shouldLayout)
      layoutContainer(); 
  }
  
  public void layoutContainer() {
    if (this.containerLayouted)
      return; 
    this.inv.openInventory();
    int c = 11 + getXOffset();
    int d = 9 + getYOffset();
    for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++)
      addSlotToContainer(getSlotForInv(this.playerInv, hotbarSlot, 8 + hotbarSlot * 18 + c, 160 + d)); 
    for (int inventoryRow = 0; inventoryRow < 3; inventoryRow++) {
      for (int inventoryColumn = 0; inventoryColumn < 9; inventoryColumn++)
        addSlotToContainer(getSlotForInv(this.playerInv, inventoryColumn + inventoryRow * 9 + 9, 8 + inventoryColumn * 18 + c, 102 + inventoryRow * 18 + d)); 
    } 
    int rows = getAmountRows();
    int perRow = getAmountPerRow();
    for (int i = 0; i < this.inv.getSizeInventory(); i++)
      addSlotToContainer(this.inv.getSlot(i, i, i % perRow * 18 + 89 - perRow * 9 + c, i / perRow * 18 + (4 - rows) * 18 + 19 + d)); 
    this.containerLayouted = true;
  }
  
  protected Slot getSlotForInv(InventoryPlayer inv, int index, int x, int y) {
    return new Slot((IInventory)inv, index, x, y);
  }
  
  public boolean canInteractWith(EntityPlayer entityplayer) {
    return this.inv.isUseableByPlayer(entityplayer);
  }
  
  public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
    ItemStack itemstack = null;
    Slot slot = (Slot) this.inventorySlots.get(par2);
    if (slot != null && slot.getHasStack()) {
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();
      if (par2 < 36) {
        if (!mergeItemStack(itemstack1, 36, this.inventorySlots.size(), false))
          return null; 
      } else if (!mergeItemStack(itemstack1, 0, 36, false)) {
        return null;
      } 
      if (itemstack1.stackSize == 0) {
        slot.putStack((ItemStack)null);
      } else {
        slot.onSlotChanged();
      } 
    } 
    return itemstack;
  }
  
  public int getAmountRows() {
    return (int)Math.max(Math.sqrt(this.inv.getSizeInventory()) * 0.7D, 1.0D);
  }
  
  @RowSizeCallback
  public int getAmountPerRow() {
    int c = this.inv.getSizeInventory() / getAmountRows();
    if (getAmountRows() * c < this.inv.getSizeInventory())
      c++; 
    return c;
  }
  
  public int getYOffset() {
    int o = -(5 - getAmountRows()) * 18 - 7;
    if (!isLargeChest())
      o += 9; 
    return o;
  }
  
  public int getXOffset() {
    return -(11 - Math.max(getAmountPerRow(), 9)) * 9 + 2;
  }
  
  public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer) {
    return super.slotClick(par1, par2, par3, par4EntityPlayer);
  }
  
  public void onContainerClosed(EntityPlayer par1EntityPlayer) {
    super.onContainerClosed(par1EntityPlayer);
    this.inv.closeInventory();
  }
  
  @ContainerSectionCallback
  public Map<ContainerSection, List<Slot>> func() {
    HashMap<ContainerSection, List<Slot>> map = new HashMap<ContainerSection, List<Slot>>();
    ArrayList<Slot> inventorySlots = new ArrayList<Slot>();
    ArrayList<Slot> hotbarSlots = new ArrayList<Slot>();
    ArrayList<Slot> thisInventorySlots = new ArrayList<Slot>();
    int i;
    for (i = 9; i < 36; i++)
      inventorySlots.add(getSlot(i)); 
    for (i = 0; i < 9; i++)
      hotbarSlots.add(getSlot(i)); 
    for (i = 36; i < this.inventorySlots.size(); i++) {
      Slot slot = getSlot(i);
      if (slot instanceof AromaSlot) {
        if (((AromaSlot)slot).canBeModified())
          thisInventorySlots.add(slot); 
      } else {
        thisInventorySlots.add(slot);
      } 
    } 
    map.put(ContainerSection.INVENTORY_NOT_HOTBAR, inventorySlots);
    map.put(ContainerSection.INVENTORY_HOTBAR, hotbarSlots);
    if (!thisInventorySlots.isEmpty())
      map.put(ContainerSection.CHEST, thisInventorySlots); 
    return map;
  }
  
  @SideOnly(Side.CLIENT)
  public GuiContainer getContainer() {
    return (GuiContainer)new GUIContainer(this);
  }
  
  public void drawGuiContainerForegroundLayer(GUIAromaBasic gui, int par1, int par2) {}
  
  protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4) {
    boolean flag1 = false;
    int k = par2;
    if (par4)
      k = par3 - 1; 
    if (par1ItemStack.isStackable())
      while (par1ItemStack.stackSize > 0 && ((!par4 && k < par3) || (par4 && k >= par2))) {
        Slot slot = (Slot) this.inventorySlots.get(k);
        ItemStack itemstack1 = slot.getStack();
        if (ItemUtil.areItemsSameMatching(par1ItemStack, itemstack1, new ItemUtil.ItemMatchCriteria[] { ItemUtil.ItemMatchCriteria.ID, ItemUtil.ItemMatchCriteria.DAMAGE, ItemUtil.ItemMatchCriteria.NBT }) && slot.isItemValid(par1ItemStack) && (!(slot instanceof AromaSlot) || ((AromaSlot)slot).canBeModified())) {
          int l = itemstack1.stackSize + par1ItemStack.stackSize;
          if (l <= par1ItemStack.getMaxStackSize()) {
            par1ItemStack.stackSize = 0;
            itemstack1.stackSize = l;
            slot.onSlotChanged();
            flag1 = true;
          } else if (itemstack1.stackSize < par1ItemStack.getMaxStackSize()) {
            par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack1.stackSize;
            itemstack1.stackSize = par1ItemStack.getMaxStackSize();
            slot.onSlotChanged();
            flag1 = true;
          } 
        } 
        if (par4) {
          k--;
          continue;
        } 
        k++;
      }  
    if (par1ItemStack.stackSize > 0) {
      if (par4) {
        k = par3 - 1;
      } else {
        k = par2;
      } 
      while ((!par4 && k < par3) || (par4 && k >= par2)) {
        Slot slot = (Slot) this.inventorySlots.get(k);
        ItemStack itemstack1 = slot.getStack();
        if (itemstack1 == null && slot.isItemValid(par1ItemStack) && (!(slot instanceof AromaSlot) || ((AromaSlot)slot).canBeModified())) {
          slot.putStack(par1ItemStack.copy());
          slot.onSlotChanged();
          par1ItemStack.stackSize = 0;
          flag1 = true;
          break;
        } 
        if (par4) {
          k--;
          continue;
        } 
        k++;
      } 
    } 
    return flag1;
  }
  
  @IsLargeCallback
  public boolean isLargeChest() {
    return (getAmountPerRow() > 5);
  }
}
