package aroma1997.core.inventories;

import aroma1997.core.Aroma1997Core;
import aroma1997.core.network.NetworkHelper;
import aroma1997.core.network.packets.PacketOpenInv;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class Inventories {
  public static int ID_GUI_BLOCK = -1;
  
  public static int ID_GUI_BLOCK_SECOND = -2;
  
  public static int ID_GUI_ENTITY = -3;
  
  @SideOnly(Side.CLIENT)
  public static Entity entityCL;
  
  public static void openContainerTileEntity(EntityPlayer thePlayer, TileEntity te, boolean first) {
    if (te == null)
      return; 
    if (thePlayer.worldObj.isRemote)
      return; 
    if (!(te instanceof ISpecialGUIProvider) && !(te instanceof ISpecialInventoryProvider))
      throw new IllegalArgumentException("The given TileEntity is neither a ISpecialGUIProvider nor a ISpecialInventoryProvider."); 
    thePlayer.openGui(Aroma1997Core.helper, first ? ID_GUI_BLOCK : ID_GUI_BLOCK_SECOND, thePlayer.worldObj, te.xCoord, te.yCoord, te.zCoord);
  }
  
  public static HashMap<String, Entity> entityS = new HashMap<String, Entity>();
  
  public static void openContainerEntity(EntityPlayer thePlayer, Entity target) {
    if (target == null)
      return; 
    if (!(target instanceof ISpecialGUIProvider) && !(target instanceof ISpecialInventoryProvider))
      throw new IllegalArgumentException("The given Entity is neither a ISpecialGUIProvider nor a ISpecialInventoryProvider."); 
    if (thePlayer.worldObj.isRemote) {
      entityCL = target;
    } else {
      if (entityS.containsKey(thePlayer.getCommandSenderName()))
        ((Entity)entityS.remove(thePlayer)).getCommandSenderName(); 
      entityS.put(thePlayer.getCommandSenderName(), target);
    } 
    thePlayer.openGui(Aroma1997Core.helper, ID_GUI_ENTITY, thePlayer.worldObj, (int)thePlayer.posX, (int)thePlayer.posY, (int)thePlayer.posZ);
  }
  
  public static void openContainerAtPlayer(EntityPlayer thePlayer, int slot) {
    if (thePlayer.worldObj.isRemote)
      return; 
    ItemStack item = thePlayer.inventory.getStackInSlot(slot);
    if ((item != null && item.getItem() instanceof ISpecialInventoryProvider) || item.getItem() instanceof ISpecialGUIProvider)
      thePlayer.openGui(Aroma1997Core.helper, slot, thePlayer.worldObj, (int)thePlayer.posX, (int)thePlayer.posY, (int)thePlayer.posZ); 
  }
  
  @SideOnly(Side.CLIENT)
  public static void sendItemInventoryOpen(int slot) {
    NetworkHelper.getCorePacketHandler().sendPacketToPlayers((IMessage)(new PacketOpenInv()).setSlot(slot));
  }
}
