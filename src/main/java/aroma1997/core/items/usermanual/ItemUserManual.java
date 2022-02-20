package aroma1997.core.items.usermanual;

import aroma1997.core.inventories.AromaContainer;
import aroma1997.core.inventories.ISpecialGUIProvider;
import aroma1997.core.inventories.Inventories;
import aroma1997.core.items.AromicItem;
import aroma1997.core.items.CreativeTabAroma;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUserManual extends AromicItem implements ISpecialGUIProvider {
  public ItemUserManual() {
    setNameAndTexture("aroma1997core:userManual");
    setCreativeTab(CreativeTabAroma.instance);
    setMaxStackSize(1);
  }
  
  public AromaContainer getContainer(EntityPlayer player, int i) {
    return new ContainerUserManual();
  }
  
  public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
    if (!par3World.isRemote)
      Inventories.openContainerAtPlayer(par2EntityPlayer, par2EntityPlayer.inventory.currentItem); 
    return true;
  }
}
