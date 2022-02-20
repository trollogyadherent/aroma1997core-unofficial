package aroma1997.core;

import aroma1997.core.inventories.AromaContainer;
import aroma1997.core.inventories.ISpecialGUIProvider;
import aroma1997.core.inventories.ISpecialInventoryProvider;
import aroma1997.core.inventories.Inventories;
import aroma1997.core.log.LogHelperPre;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {
  public void init() {}
  
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if (ID == Inventories.ID_GUI_BLOCK || ID == Inventories.ID_GUI_BLOCK_SECOND) {
      TileEntity te = world.getTileEntity(x, y, z);
      if (te != null && te instanceof ISpecialInventoryProvider) {
        ISpecialInventoryProvider inv = (ISpecialInventoryProvider)te;
        return inv.getInventory(player, ID).getContainer(player, ID);
      } 
      if (te != null && te instanceof ISpecialGUIProvider) {
        AromaContainer cont = ((ISpecialGUIProvider)te).getContainer(player, ID);
        return cont;
      } 
    } else if (ID == Inventories.ID_GUI_ENTITY) {
      Entity e;
      if (world.isRemote) {
        e = Inventories.entityCL;
        Inventories.entityCL = null;
      } else {
        e = (Entity)Inventories.entityS.get(player.getCommandSenderName());
        Inventories.entityS.remove(player.getCommandSenderName());
      } 
      if (e != null && e instanceof ISpecialInventoryProvider) {
        ISpecialInventoryProvider inv = (ISpecialInventoryProvider)e;
        return inv.getInventory(player, ID).getContainer(player, ID);
      } 
      if (e != null && e instanceof ISpecialGUIProvider) {
        AromaContainer cont = ((ISpecialGUIProvider)e).getContainer(player, ID);
        return cont;
      } 
    } else {
      ItemStack item = null;
      try {
        item = player.inventory.getStackInSlot(ID);
      } catch (ArrayIndexOutOfBoundsException e) {
        LogHelperPre.logException("Incorrect GUI ID!", e);
        return null;
      } 
      if (item != null && item.getItem() instanceof ISpecialInventoryProvider) {
        ISpecialInventoryProvider gi = (ISpecialInventoryProvider)item.getItem();
        ISpecialGUIProvider inv = gi.getInventory(player, ID);
        NBTTagCompound nbt = item.getTagCompound();
        if (nbt == null)
          nbt = new NBTTagCompound(); 
        AromaContainer cont = inv.getContainer(player, ID);
        return cont;
      } 
      if (item != null && item.getItem() instanceof ISpecialGUIProvider)
        return ((ISpecialGUIProvider)item.getItem()).getContainer(player, ID); 
    } 
    return null;
  }
  
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    return null;
  }
}
