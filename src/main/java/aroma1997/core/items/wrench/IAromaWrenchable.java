package aroma1997.core.items.wrench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public interface IAromaWrenchable {
  boolean onWrenchUsed(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, ForgeDirection paramForgeDirection);
  
  boolean canPickup(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, ForgeDirection paramForgeDirection);
  
  ItemStack shouldBeExact();
}
