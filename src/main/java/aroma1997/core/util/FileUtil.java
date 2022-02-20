package aroma1997.core.util;

import aroma1997.core.inventories.IAdvancedInventory;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class FileUtil {
  public static void writeToNBT(IInventory inv, NBTTagCompound nbt) {
    NBTTagList list = new NBTTagList();
    for (int i = 0; i < inv.getSizeInventory(); i++) {
      ItemStack item = inv.getStackInSlot(i);
      if (item != null) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setByte("Slot", (byte)i);
        item.writeToNBT(tag);
        list.appendTag((NBTBase)tag);
      } 
    } 
    nbt.setTag("Items", (NBTBase)list);
  }
  
  public static void readFromNBT(IInventory inv, NBTTagCompound nbt) {
    boolean special = false;
    if (inv instanceof IAdvancedInventory)
      special = true; 
    NBTTagList list = nbt.getTagList("Items", (new NBTTagCompound()).getId());
    for (int i = 0; i < list.tagCount(); i++) {
      NBTTagCompound tagT = list.getCompoundTagAt(i);
      int tag = tagT.getByte("Slot") & 0xFF;
      if (tag >= 0 && tag < inv.getSizeInventory()) {
        ItemStack item = ItemStack.loadItemStackFromNBT(tagT);
        if (item != null && item.getItem() != null)
          if (special) {
            ((IAdvancedInventory)inv).setStackInSlotWithoutNotify(tag, item);
          } else {
            inv.setInventorySlotContents(tag, item);
          }  
      } 
    } 
  }
  
  public static void writeString(String string, ByteBuf buf) {
    buf.writeInt(string.length());
    for (int i = 0; i < string.length(); i++)
      buf.writeChar(string.charAt(i)); 
  }
  
  public static String readString(ByteBuf buf) {
    int length = buf.readInt();
    String str = "";
    for (int i = 0; i < length; i++)
      str = str + buf.readChar(); 
    return str.trim();
  }
}
