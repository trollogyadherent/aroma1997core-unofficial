package aroma1997.core.recipes;

import aroma1997.core.log.LogHelperPre;
import aroma1997.core.util.ItemUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ShapelessAromicRecipe implements IAromicRecipe {
  private ItemStack output = null;
  
  private List<Object> input;
  
  private boolean hidden = false;
  
  public ShapelessAromicRecipe(ItemStack result, Object... recipe) {
    this.output = result.copy();
    Object[] input = new Object[recipe.length];
    for (int i = 0; i < recipe.length; i++) {
      Object in = recipe[i];
      if (in instanceof ItemStack) {
        input[i] = ((ItemStack)in).copy();
        if ((OreDictionary.getOreIDs((ItemStack)in)).length > 0)
          LogHelperPre.debugLog("Using " + in.toString() + " as a recipe ingredient. You could also have used a OreDict name " + " (in recipe: " + result.toString() + ")"); 
      } else if (in instanceof String) {
        input[i] = OreDictionary.getOres((String)in);
      } else if (in instanceof Class) {
        input[i] = ItemUtil.getItemsFromClass((Class)in);
      } else if (in instanceof RecipePart) {
        input[i] = in;
      } else if (in instanceof Item) {
        ItemStack item = new ItemStack((Item)in, 1, 32767);
        input[i] = item;
        if ((OreDictionary.getOreIDs(item)).length > 0)
          LogHelperPre.debugLog("Using " + in.toString() + " as a recipe ingredient. You could also have used a OreDict name " + " (in recipe: " + result.toString() + ")"); 
      } else if (in instanceof Block) {
        ItemStack item = new ItemStack((Block)in, 1, 32767);
        input[i] = item;
        if ((OreDictionary.getOreIDs(item)).length > 0)
          LogHelperPre.debugLog("Using " + in.toString() + " as a recipe ingredient. You could also have used a OreDict name " + " (in recipe: " + result.toString() + ")"); 
      } else {
        String ret = "Invalid shapeless aromic recipe: ";
        for (Object tmp : recipe)
          ret = ret + tmp + ", "; 
        ret = ret + this.output;
        throw new RuntimeException(ret);
      } 
    } 
    this.input = Arrays.asList(input);
  }
  
  public int getRecipeSize() {
    return this.input.size();
  }
  
  public ItemStack getRecipeOutput() {
    return this.output;
  }
  
  public ItemStack getCraftingResult(InventoryCrafting var1) {
    return this.output.copy();
  }
  
  public boolean matches(InventoryCrafting var1, World world) {
    ArrayList<Object> required = new ArrayList(this.input);
    for (int x = 0; x < var1.getSizeInventory(); x++) {
      ItemStack slot = var1.getStackInSlot(x);
      if (slot != null) {
        boolean inRecipe = false;
        Iterator req = required.iterator();
        while (req.hasNext()) {
          boolean match = false;
          Object next = req.next();
          if (next instanceof ItemStack) {
            match = checkItemEquals((ItemStack)next, slot);
          } else if (next instanceof List) {
            for (Object item : (java.util.List) next)
              match = (match || checkItemEquals((ItemStack) item, slot));
          } else if (next instanceof RecipePart) {
            match = ((RecipePart)next).doesItemMatch(slot);
          } 
          if (match) {
            inRecipe = true;
            required.remove(next);
            break;
          } 
        } 
        if (!inRecipe)
          return false; 
      } 
    } 
    return required.isEmpty();
  }
  
  private boolean checkItemEquals(ItemStack target, ItemStack input) {
    return ItemUtil.areItemsSameMatching(target, input, new ItemUtil.ItemMatchCriteria[] { ItemUtil.ItemMatchCriteria.WILDCARD });
  }
  
  public Object[] getInput() {
    return this.input.toArray();
  }
  
  public boolean isHidden() {
    return this.hidden;
  }
  
  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }
  
  public boolean isMirrorOK() {
    return true;
  }
  
  public void setMirrorOK(boolean mirrorok) {
    if (!mirrorok)
      throw new UnsupportedOperationException("Cannot disable Mirror on a Shapeless Recipe."); 
  }
}
