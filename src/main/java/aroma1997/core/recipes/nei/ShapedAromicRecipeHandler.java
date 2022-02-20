package aroma1997.core.recipes.nei;

import aroma1997.core.config.Config;
import aroma1997.core.recipes.RecipePart;
import aroma1997.core.recipes.ShapedAromicRecipe;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ShapedRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class ShapedAromicRecipeHandler extends ShapedRecipeHandler {
  public class CachedShapedRecipe extends TemplateRecipeHandler.CachedRecipe {
    public ArrayList<PositionedStack> ingredients;
    
    public PositionedStack result;
    
    private final boolean isHidden;
    
    public CachedShapedRecipe(int width, int height, Object[] items, ItemStack out, boolean isHidden) {
      //super((TemplateRecipeHandler)ShapedAromicRecipeHandler.this);
      super();
      this.result = new PositionedStack(out, 119, 24);
      this.ingredients = new ArrayList<PositionedStack>();
      setIngredients(width, height, items);
      this.isHidden = isHidden;
    }
    
    public CachedShapedRecipe(ShapedAromicRecipe recipe) {
      this(recipe.getRecipeWidth(), recipe.getRecipeHeight(), recipe.getInput(), recipe.getRecipeOutput(), recipe.isHidden());
    }
    
    public void setIngredients(int width, int height, Object[] items) {
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          if (items[y * width + x] != null) {
            PositionedStack stack;
            if (items[y * width + x] instanceof List && ((List)items[y * width + x]).isEmpty()) {
              stack = new PositionedStack(new ItemStack((Block)Blocks.fire), 25 + x * 18, 6 + y * 18, false);
            } else if (items[y * width + x] instanceof RecipePart) {
              stack = new PositionedStack(((RecipePart)items[y * width + x]).getExamples(), 25 + x * 28, 6 + y * 18, false);
            } else {
              stack = new PositionedStack(items[y * width + x], 25 + x * 18, 6 + y * 18, false);
            } 
            stack.setMaxSize(1);
            this.ingredients.add(stack);
          } 
        } 
      } 
    }
    
    public List<PositionedStack> getIngredients() {
      return getCycledIngredients(ShapedAromicRecipeHandler.this.cycleticks / 20, this.ingredients);
    }
    
    public PositionedStack getResult() {
      return this.result;
    }
    
    public void computeVisuals() {
      for (PositionedStack p : this.ingredients)
        p.generatePermutations(); 
      this.result.generatePermutations();
    }
  }
  
  public String getRecipeName() {
    return "Aromic Crafting";
  }
  
  public void loadCraftingRecipes(String outputId, Object... results) {
    if (outputId.equals("crafting")) {
      List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
      for (IRecipe irecipe : allrecipes) {
        CachedShapedRecipe recipe = null;
        if (irecipe instanceof ShapedAromicRecipe)
          recipe = new CachedShapedRecipe((ShapedAromicRecipe)irecipe); 
        if (recipe == null || (recipe.isHidden && !Config.shouldShowHiddenRecipes()))
          continue; 
        recipe.computeVisuals();
        this.arecipes.add(recipe);
      } 
    } else {
      super.loadCraftingRecipes(outputId, results);
    } 
  }
  
  public void loadCraftingRecipes(ItemStack result) {
    List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
    for (IRecipe irecipe : allrecipes) {
      if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result)) {
        CachedShapedRecipe recipe = null;
        if (irecipe instanceof ShapedAromicRecipe)
          recipe = new CachedShapedRecipe((ShapedAromicRecipe)irecipe); 
        if (recipe == null || (recipe.isHidden && !Config.shouldShowHiddenRecipes()))
          continue; 
        recipe.computeVisuals();
        this.arecipes.add(recipe);
      } 
    } 
  }
  
  public void loadUsageRecipes(ItemStack ingredient) {
    List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
    for (IRecipe irecipe : allrecipes) {
      CachedShapedRecipe recipe = null;
      if (irecipe instanceof ShapedAromicRecipe)
        recipe = new CachedShapedRecipe((ShapedAromicRecipe)irecipe); 
      if (recipe == null || (recipe.isHidden && !Config.shouldShowHiddenRecipes()))
        continue; 
      recipe.computeVisuals();
      if (recipe.contains(recipe.ingredients, ingredient)) {
        recipe.setIngredientPermutation(recipe.ingredients, ingredient);
        this.arecipes.add(recipe);
      } 
    } 
  }
}
