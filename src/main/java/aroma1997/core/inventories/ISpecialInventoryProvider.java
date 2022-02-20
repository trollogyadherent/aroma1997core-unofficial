package aroma1997.core.inventories;

import net.minecraft.entity.player.EntityPlayer;

public interface ISpecialInventoryProvider {
  ISpecialGUIProvider getInventory(EntityPlayer paramEntityPlayer, int paramInt);
}
