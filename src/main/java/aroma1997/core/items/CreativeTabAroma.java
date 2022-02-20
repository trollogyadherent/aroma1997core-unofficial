package aroma1997.core.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabAroma extends CreativeTabs {
  public static final CreativeTabs instance = new CreativeTabAroma();
  
  private CreativeTabAroma() {
    super("aroma1997core:creativetab");
  }
  
  @SideOnly(Side.CLIENT)
  public Item getTabIconItem() {
    return (Item)ItemsMain.wrench;
  }
}
