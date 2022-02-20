package aroma1997.core.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class WorldUtil {
  public static void dropItemsRandom(World world, ItemStack items, float x, float y, float z) {
    float f = 0.7F;
    double x1 = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
    double y1 = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
    double z1 = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
    dropItems(world, items, x + x1, y + y1, z + z1);
  }
  
  public static void dropItems(World world, ItemStack items, double x, double y, double z) {
    EntityItem entityitem = new EntityItem(world, x, y, z, items);
    entityitem.delayBeforeCanPickup = 10;
    world.spawnEntityInWorld((Entity)entityitem);
  }
  
  public static void addClientEvent(TileEntity te, int type, int arg) {
    te.getWorldObj().addBlockEvent(te.xCoord, te.yCoord, te.zCoord, te.getBlockType(), type, arg);
  }
  
  public static boolean isBlockAir(IBlockAccess world, int x, int y, int z) {
    return (isBlockAir(world.getBlock(x, y, z)) || world.isAirBlock(x, y, z));
  }
  
  public static boolean isBlockAir(Block block) {
    return (block == null || block == Blocks.air || block.getMaterial() == Material.air);
  }
}
