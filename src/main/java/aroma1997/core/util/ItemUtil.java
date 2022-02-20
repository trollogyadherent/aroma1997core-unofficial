package aroma1997.core.util;

import aroma1997.core.client.util.Colors;
import com.google.common.base.Throwables;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.StatCollector;

public class ItemUtil {
  public static ItemStack getWrittenBook(String name, String author, boolean isLocalized, String... pages) {
    if (pages == null || pages.length == 0)
      return null; 
    ItemStack item = new ItemStack(Items.written_book);
    item.setTagInfo("author", (NBTBase)new NBTTagString(author));
    item.setTagInfo("title", (NBTBase)new NBTTagString(isLocalized ? StatCollector.translateToLocal(name) : name));
    NBTTagList pagesList = new NBTTagList();
    for (int i = 0; i < pages.length; i++) {
      String str = null;
      if (!pages[i].contains("chapter")) {
        str = isLocalized ? StatCollector.translateToLocal(pages[i]) : pages[i];
      } else {
        str = Colors.BOLD.toString() + Colors.UNDERLINE + (isLocalized ? StatCollector.translateToLocal(pages[i]) : pages[i]);
      } 
      pagesList.appendTag((NBTBase)new NBTTagString(str));
    } 
    item.setTagInfo("pages", (NBTBase)pagesList);
    return item;
  }
  
  @Deprecated
  public static boolean areItemsSame(ItemStack item1, ItemStack item2) {
    return areItemsSameMatching(item1, item2, new ItemMatchCriteria[] { ItemMatchCriteria.ID, ItemMatchCriteria.DAMAGE, ItemMatchCriteria.NBT });
  }
  
  public static boolean areItemsSameMatching(ItemStack item1, ItemStack item2, ItemMatchCriteria... crits) {
    if (crits == null || crits.length <= 0)
      return true; 
    if (item1 == null && item2 == null)
      return true; 
    if (item1 == null || item2 == null)
      return false; 
    for (ItemMatchCriteria crit : crits) {
      if (!crit.checkFor(item1, item2))
        return false; 
    } 
    return true;
  }
  
  public static class ItemMatchCriteria {
    public static final ItemMatchCriteria NBT = new ItemMatchCriteria((Class)MatchNBT.class);
    
    public static final ItemMatchCriteria ID = new ItemMatchCriteria((Class)MatchID.class);
    
    public static final ItemMatchCriteria CLASS = new ItemMatchCriteria((Class)MatchCLASS.class);
    
    public static final ItemMatchCriteria DAMAGE = new ItemMatchCriteria((Class)MatchDAMAGE.class);
    
    public static final ItemMatchCriteria WILDCARD = new ItemMatchCriteria((Class)MatchWILDCARD.class);
    
    private ICompareItems comparer;
    
    public ItemMatchCriteria(Class<? extends ICompareItems> clazz) {
      try {
        this.comparer = clazz.newInstance();
      } catch (Exception e) {
        Throwables.propagate(e);
      } 
    }
    
    public boolean checkFor(ItemStack item1, ItemStack item2) {
      return this.comparer.checkFor(item1, item2);
    }
    
    public static interface ICompareItems {
      boolean checkFor(ItemStack param2ItemStack1, ItemStack param2ItemStack2);
    }
    
    static class MatchID implements ICompareItems {
      public boolean checkFor(ItemStack item1, ItemStack item2) {
        return (Item.itemRegistry.getNameForObject(item1.getItem()) == Item.itemRegistry.getNameForObject(item2.getItem()));
      }
    }
    
    static class MatchNBT implements ICompareItems {
      public boolean checkFor(ItemStack item1, ItemStack item2) {
        return ItemStack.areItemStackTagsEqual(item1, item2);
      }
    }
    
    static class MatchCLASS implements ICompareItems {
      public boolean checkFor(ItemStack item1, ItemStack item2) {
        return item1.getItem().getClass().equals(item2.getItem().getClass());
      }
    }
    
    static class MatchDAMAGE implements ICompareItems {
      public boolean checkFor(ItemStack item1, ItemStack item2) {
        return (item1.getItemDamage() == item2.getItemDamage());
      }
    }
    
    static class MatchWILDCARD implements ICompareItems {
      public boolean checkFor(ItemStack item1, ItemStack item2) {
        return (ItemUtil.ItemMatchCriteria.ID.checkFor(item1, item2) && (ItemUtil.ItemMatchCriteria.DAMAGE.checkFor(item1, item2) || item1.getItemDamage() == 32767 || item2.getItemDamage() == 32767));
      }
    }
  }
  
  public static ArrayList<ItemStack> getItemsFromClass(Class<?> claSS) {
    ArrayList<ItemStack> list = new ArrayList<ItemStack>();
    Iterator<Item> it = Item.itemRegistry.iterator();
    while (it.hasNext()) {
      Item item = it.next();
      if (item != null && claSS.isInstance(item))
        list.add(new ItemStack(item, 1, 32767)); 
    } 
    it = Block.blockRegistry.iterator();
    while (it.hasNext()) {
      /*Block block = (Block)it.next();
      if (block != null && claSS.isInstance(block))
        list.add(new ItemStack(block, 1, 32767));*/
      Item item = it.next();
      if (item != null && claSS.isInstance(item)) {
        list.add(new ItemStack(item, 1, 32767));
      }
    } 
    return list;
  }
}
