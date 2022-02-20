package aroma1997.core.items.wrench;

import aroma1997.core.items.AromicItem;
import aroma1997.core.items.CreativeTabAroma;
import aroma1997.core.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class ItemWrench extends AromicItem {
  public ItemWrench() {
    setCreativeTab(CreativeTabAroma.instance);
    setContainerItem((Item)this);
    setMaxStackSize(1);
    setNameAndTexture("aroma1997core:hammer");
    if (OreDictionary.getOres("nuggetIron") != null && OreDictionary.getOres("nuggetIron").size() > 0) {
      setRecipe(new Object[] { "IIN", " S ", " S ", Character.valueOf('I'), new ItemStack(Items.iron_ingot), Character.valueOf('N'), "nuggetIron", Character.valueOf('S'), "stickWood" });
    } else {
      setRecipe(new Object[] { "IIN", " S ", " S ", Character.valueOf('I'), new ItemStack(Items.iron_ingot), Character.valueOf('N'), "cobblestone", Character.valueOf('S'), "stickWood" });
    } 
  }
  
  public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
    if (world.isRemote)
      return false; 
    TileEntity te = world.getTileEntity(x, y, z);
    Block bl = world.getBlock(x, y, z);
    if (te == null || !(te instanceof IAromaWrenchable)) {
      if (!(te instanceof IAromaWrenchable) || bl == null || !(bl instanceof IAromaWrenchable))
        return false; 
      IAromaWrenchable block = (IAromaWrenchable)bl;
      if (player.isSneaking() && block.canPickup(stack, player, ForgeDirection.values()[side])) {
        ItemStack item = new ItemStack(bl, 1, world.getBlockMetadata(x, y, z));
        WorldUtil.dropItemsRandom(world, item, x, y, z);
        world.setBlockToAir(x, y, z);
        return true;
      } 
      return block.onWrenchUsed(stack, player, ForgeDirection.values()[side]);
    } 
    IAromaWrenchable w = (IAromaWrenchable)te;
    ForgeDirection dir = ForgeDirection.values()[side];
    if (player.isSneaking() && w.canPickup(stack, player, dir)) {
      ItemStack item;
      if (w.shouldBeExact() == null) {
        item = ItemWrenched.generateItemFromTE(world.getBlock(x, y, z), world.getBlockMetadata(x, y, z), te);
        world.removeTileEntity(x, y, z);
      } else {
        item = w.shouldBeExact();
        bl.breakBlock(world, x, y, z, bl, side);
      } 
      WorldUtil.dropItemsRandom(world, item, x, y, z);
      world.removeTileEntity(x, y, z);
      world.setBlockToAir(x, y, z);
      return true;
    } 
    return w.onWrenchUsed(stack, player, dir);
  }
  
  public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
    if (entity != null && entity instanceof IAromaWrenchable) {
      IAromaWrenchable wr = (IAromaWrenchable)entity;
      if (player.isSneaking() && wr.canPickup(stack, player, ForgeDirection.UNKNOWN)) {
        ItemStack item = wr.shouldBeExact();
        if (item != null) {
          entity.setDead();
          WorldUtil.dropItems(player.worldObj, item, entity.posX, entity.posY, entity.posZ);
          return true;
        } 
        return false;
      } 
      return wr.onWrenchUsed(stack, player, ForgeDirection.UNKNOWN);
    } 
    return false;
  }
  
  public static boolean hasPlayerWrench(EntityPlayer player) {
    for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
      ItemStack stack = player.inventory.getStackInSlot(i);
      if (stack != null && stack.getItem() instanceof ItemWrench)
        return true; 
    } 
    return false;
  }
}
