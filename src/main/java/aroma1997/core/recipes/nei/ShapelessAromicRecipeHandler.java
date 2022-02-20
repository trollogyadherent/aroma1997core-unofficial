package aroma1997.core.recipes.nei;

import aroma1997.core.client.inventories.RenderHelper;
import aroma1997.core.config.Config;
import aroma1997.core.recipes.RecipePart;
import aroma1997.core.recipes.ShapelessAromicRecipe;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class ShapelessAromicRecipeHandler extends ShapedAromicRecipeHandler {
  public int[][] stackorder = new int[][] { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 }, { 0, 2 }, { 1, 2 }, { 2, 0 }, { 2, 1 }, { 2, 2 } };
  
  public class CachedShapelessRecipe extends TemplateRecipeHandler.CachedRecipe {
    private boolean isHidden = false;
    
    public ArrayList<PositionedStack> ingredients;
    
    public PositionedStack result;
    
    public CachedShapelessRecipe(ItemStack output) {
      this();
      setResult(output);
    }
    
    public CachedShapelessRecipe(ShapelessAromicRecipe recipe) {
      this(recipe.getRecipeOutput());
      setIngredients(recipe);
      this.isHidden = recipe.isHidden();
    }
    
    public void setIngredients(List<?> items) {
      this.ingredients.clear();
      for (int ingred = 0; ingred < items.size(); ingred++) {
        PositionedStack stack;
        Object i = items.get(ingred);
        if (i instanceof List && ((List)i).isEmpty()) {
          stack = new PositionedStack(new ItemStack((Block)Blocks.fire), 25 + ShapelessAromicRecipeHandler.this.stackorder[ingred][0] * 18, 6 + ShapelessAromicRecipeHandler.this.stackorder[ingred][1] * 18);
        } else if (i instanceof RecipePart) {
          stack = new PositionedStack(((RecipePart)i).getExamples(), 25 + ShapelessAromicRecipeHandler.this.stackorder[ingred][0] * 18, 6 + ShapelessAromicRecipeHandler.this.stackorder[ingred][1] * 18);
        } else {
          stack = new PositionedStack(items.get(ingred), 25 + ShapelessAromicRecipeHandler.this.stackorder[ingred][0] * 18, 6 + ShapelessAromicRecipeHandler.this.stackorder[ingred][1] * 18);
        } 
        stack.setMaxSize(1);
        this.ingredients.add(stack);
      } 
    }
    
    public void setIngredients(ShapelessAromicRecipe recipe) {
      List<Object> items = Arrays.asList(recipe.getInput());
      setIngredients(items);
    }
    
    public void setResult(ItemStack output) {
      this.result = new PositionedStack(output, 119, 24);
    }
    
    public List<PositionedStack> getIngredients() {
      return getCycledIngredients(ShapelessAromicRecipeHandler.this.cycleticks / 20, this.ingredients);
    }
    
    public PositionedStack getResult() {
      return this.result;
    }
    
    public CachedShapelessRecipe() {
      //super((TemplateRecipeHandler)ShapelessAromicRecipeHandler.this);
      super();
      this.ingredients = new ArrayList<PositionedStack>();
    }
  }
  
  public String getRecipeName() {
    return "Aromic Crafting";
  }
  
  public void loadCraftingRecipes(String outputId, Object... results) {
    if (outputId.equals("crafting")) {
      List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
      for (IRecipe irecipe : allrecipes) {
        CachedShapelessRecipe recipe = null;
        if (irecipe instanceof ShapelessAromicRecipe)
          recipe = new CachedShapelessRecipe((ShapelessAromicRecipe)irecipe); 
        if (recipe == null || (recipe.isHidden && !Config.shouldShowHiddenRecipes()))
          continue; 
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
        CachedShapelessRecipe recipe = null;
        if (irecipe instanceof ShapelessAromicRecipe)
          recipe = new CachedShapelessRecipe((ShapelessAromicRecipe)irecipe); 
        if (recipe == null || (recipe.isHidden && !Config.shouldShowHiddenRecipes()))
          continue; 
        this.arecipes.add(recipe);
      } 
    } 
  }
  
  public void loadUsageRecipes(ItemStack ingredient) {
    List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
    for (IRecipe irecipe : allrecipes) {
      CachedShapelessRecipe recipe = null;
      if (irecipe instanceof ShapelessAromicRecipe)
        recipe = new CachedShapelessRecipe((ShapelessAromicRecipe)irecipe); 
      if (recipe == null || (recipe.isHidden && !Config.shouldShowHiddenRecipes()))
        continue; 
      if (recipe.contains(recipe.ingredients, ingredient)) {
        recipe.setIngredientPermutation(recipe.ingredients, ingredient);
        this.arecipes.add(recipe);
      } 
    } 
  }
  
  public boolean isRecipe2x2(int recipe) {
    return (getIngredientStacks(recipe).size() <= 4);
  }
  
  public void drawForeground(int recipe) {
    super.drawForeground(recipe);
    RenderHelper.renderTex((Gui)(Minecraft.getMinecraft()).currentScreen, RenderHelper.Tex.RANDOM, 85, 40);
  }
}
