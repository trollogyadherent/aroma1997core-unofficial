package aroma1997.core.recipes;

import aroma1997.core.util.ItemUtil;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeUtil {
  public static void unregisterRecipe(IRecipe recipe) {
    CraftingManager.getInstance().getRecipeList().remove(recipe);
  }
  
  public static void unregisterRecipe(List<IRecipe> list) {
    for (IRecipe recipe : list)
      unregisterRecipe(recipe); 
  }
  
  public static List<IRecipe> getRecipes(ItemStack item) {
    List<IRecipe> list = new ArrayList<IRecipe>();
    for (Object o : CraftingManager.getInstance().getRecipeList()) {
      IRecipe recipe = (IRecipe)o;
      if (ItemUtil.areItemsSameMatching(recipe.getRecipeOutput(), item, new ItemUtil.ItemMatchCriteria[] { ItemUtil.ItemMatchCriteria.ID, ItemUtil.ItemMatchCriteria.WILDCARD }))
        list.add(recipe); 
    } 
    return list;
  }
  
  public static <E extends IRecipe> List<E> getRecipes(ItemStack item, Class<? super E> clazz) {
    List<IRecipe> list = getRecipes(item);
    List<E> ret = new ArrayList<E>();
    for (IRecipe recipe : list) {
      if (clazz.isInstance(recipe))
        ret.add((E)recipe); 
    } 
    return ret;
  }
  
  public static void unregisterRecipe(ItemStack item) {
    unregisterRecipe(getRecipes(item));
  }
  
  public static IAromicRecipe createAromicRecipeFrom(IRecipe recipe) {
    if (recipe instanceof IAromicRecipe)
      return (IAromicRecipe)recipe; 
    if (recipe instanceof ShapedRecipes) {
      ShapedRecipes r = (ShapedRecipes)recipe;
      ShapedAromicRecipe ret = new ShapedAromicRecipe();
      ret.height = r.recipeHeight;
      ret.width = r.recipeWidth;
      ret.input = (Object[])r.recipeItems;
      ret.output = r.getRecipeOutput();
      return ret;
    } 
    if (recipe instanceof ShapelessRecipes) {
      ShapelessRecipes r = (ShapelessRecipes)recipe;
      ShapelessAromicRecipe ret = new ShapelessAromicRecipe(r.getRecipeOutput(), new Object[] { Boolean.valueOf(false), r.recipeItems.toArray() });
      return ret;
    } 
    if (recipe instanceof ShapedOreRecipe) {
      ShapedOreRecipe r = (ShapedOreRecipe)recipe;
      ShapedAromicRecipe ret = new ShapedAromicRecipe();
      ret.input = r.getInput();
      ret.output = r.getRecipeOutput();
      ret.width = ((Integer)ReflectionHelper.getPrivateValue(ShapedOreRecipe.class, r, new String[] { "width" })).intValue();
      ret.height = ret.input.length / ret.width;
      return ret;
    } 
    if (recipe instanceof ShapelessOreRecipe) {
      ShapelessOreRecipe r = (ShapelessOreRecipe)recipe;
      ShapelessAromicRecipe ret = new ShapelessAromicRecipe(r.getRecipeOutput(), r.getInput().toArray());
      return ret;
    } 
    throw new IllegalArgumentException("Unsupported conversion recipe. " + recipe.getRecipeOutput().toString() + " " + recipe.getClass());
  }
  
  public static void hideRecipe(IRecipe recipe) {
    IAromicRecipe arecipe = createAromicRecipeFrom(recipe);
    arecipe.setHidden(true);
    if (recipe != arecipe) {
      unregisterRecipe(recipe);
      GameRegistry.addRecipe(arecipe);
    } 
  }
}
