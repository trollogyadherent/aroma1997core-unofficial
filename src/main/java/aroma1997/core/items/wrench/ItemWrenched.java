package aroma1997.core.items.wrench;

import aroma1997.core.client.util.Colors;
import aroma1997.core.items.AromicItem;
import aroma1997.core.items.ItemsMain;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemWrenched extends AromicItem {
  public ItemWrenched() {
    setMaxStackSize(1);
    setHasSubtypes(true);
    setUnlocalizedName("aroma1997core:wrenched");
  }
  
  public static ItemStack generateItemFromTE(Block block, int damage, TileEntity te) {
    ItemStack item = new ItemStack((Item)ItemsMain.wrenched);
    item.setTagCompound(new NBTTagCompound());
    Item itemBlock = Item.getItemFromBlock(block);
    NBTTagCompound compItem = new NBTTagCompound();
    (new ItemStack(itemBlock, damage)).writeToNBT(compItem);
    item.getTagCompound().setTag("item", (NBTBase)compItem);
    NBTTagCompound compTE = new NBTTagCompound();
    te.writeToNBT(compTE);
    item.getTagCompound().setTag("te", (NBTBase)compTE);
    return item;
  }
  
  public static ItemStack getContainedItem(ItemStack item) {
    if (item.getTagCompound() == null || !item.getTagCompound().hasKey("item"))
      return null; 
    return ItemStack.loadItemStackFromNBT(item.getTagCompound().getCompoundTag("item"));
  }
  
  @SideOnly(Side.CLIENT)
  public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {}
  
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(ItemStack stack, int pass) {
    if (getContainedItem(stack) == null)
      return this.itemIcon; 
    return getContainedItem(stack).getItem().getIcon(getContainedItem(stack), pass);
  }
  
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
    if (getContainedItem(par1ItemStack) == null)
      return; 
    par3List.add(Colors.ORANGE + "Contains special Data...");
    par3List.add(Colors.RED + "I know, it doesn't have a texture. You don't have to report it.");
    ItemStack itemTmp = getContainedItem(par1ItemStack);
    itemTmp.setTagCompound(par1ItemStack.getTagCompound().getCompoundTag("te"));
    getContainedItem(par1ItemStack).getItem().addInformation(itemTmp, par2EntityPlayer, par3List, par4);
  }
  
  @SideOnly(Side.CLIENT)
  public String getItemStackDisplayName(ItemStack par1ItemStack) {
    if (getContainedItem(par1ItemStack) == null)
      return null; 
    return getContainedItem(par1ItemStack).getItem().getItemStackDisplayName(getContainedItem(par1ItemStack));
  }
  
  public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
    if (getContainedItem(par1ItemStack) == null)
      return false; 
    Block block = par3World.getBlock(par4, par5, par6);
    Block blockPlace = Block.getBlockFromItem(getContainedItem(par1ItemStack).getItem());
    if (block == Blocks.snow_layer && (par3World.getBlockMetadata(par4, par5, par6) & 0x7) < 1) {
      par7 = 1;
    } else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable((IBlockAccess)par3World, par4, par5, par6)) {
      if (par7 == 0)
        par5--; 
      if (par7 == 1)
        par5++; 
      if (par7 == 2)
        par6--; 
      if (par7 == 3)
        par6++; 
      if (par7 == 4)
        par4--; 
      if (par7 == 5)
        par4++; 
    } 
    if (par1ItemStack.stackSize == 0)
      return false; 
    if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
      return false; 
    if (par5 == 255 && blockPlace.getMaterial().isSolid())
      return false; 
    if (par3World.canPlaceEntityOnSide(blockPlace, par4, par5, par6, false, par7, (Entity)par2EntityPlayer, par1ItemStack)) {
      TileEntity te = TileEntity.createAndLoadEntity(par1ItemStack.getTagCompound().getCompoundTag("te"));
      te.readFromNBT(par1ItemStack.getTagCompound().getCompoundTag("te"));
      te.xCoord = par4;
      te.yCoord = par5;
      te.zCoord = par6;
      int i1 = getMetadata(par1ItemStack.getItemDamage());
      int j1 = blockPlace.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, i1);
      if (placeBlockAt(blockPlace, par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, j1)) {
        par3World.playSoundEffect((par4 + 0.5F), (par5 + 0.5F), (par6 + 0.5F), blockPlace.stepSound.soundName, (blockPlace.stepSound.getVolume() + 1.0F) / 2.0F, blockPlace.stepSound.getPitch() * 0.8F);
        par3World.setTileEntity(par4, par5, par6, te);
        par1ItemStack.stackSize--;
      } 
      return true;
    } 
    return false;
  }
  
  private boolean placeBlockAt(Block block, ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
    if (!world.setBlock(x, y, z, block, metadata, 3))
      return false; 
    if (world.getBlock(x, y, z) == block) {
      block.onBlockPlacedBy(world, x, y, z, (EntityLivingBase)player, stack);
      block.onPostBlockPlaced(world, x, y, z, metadata);
    } 
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister iconRegister) {}
}
