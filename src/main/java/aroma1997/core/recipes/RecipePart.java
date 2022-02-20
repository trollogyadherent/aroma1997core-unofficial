package aroma1997.core.recipes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

public abstract class RecipePart {
  public abstract boolean doesItemMatch(ItemStack paramItemStack);
  
  @SideOnly(Side.CLIENT)
  public List<ItemStack> getExamples() {
    ArrayList<ItemStack> list = new ArrayList<ItemStack>();
    addToExampleList(list);
    return list;
  }
  
  @SideOnly(Side.CLIENT)
  protected void addToExampleList(ArrayList<ItemStack> list) {}
}
