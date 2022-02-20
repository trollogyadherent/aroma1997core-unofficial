package aroma1997.core.client;

import aroma1997.core.CommonProxy;
import aroma1997.core.client.events.ClientEventListener;
import aroma1997.core.inventories.AromaContainer;
import aroma1997.core.log.LogHelperPre;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {
  public void init() {
    MiscStuff.init();
    ClientEventListener.getInstance();
  }
  
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    try {
      AromaContainer cont = (AromaContainer)getServerGuiElement(ID, player, world, x, y, z);
      if (cont == null)
        return null; 
      return cont.getContainer();
    } catch (ClassCastException e) {
      LogHelperPre.logException("Somthing went wrong during the creation of the GUI!", e);
      return null;
    } 
  }
}
