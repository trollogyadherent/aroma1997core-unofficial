package aroma1997.core.recipes.nei;

import aroma1997.core.Tags;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;

public class NEIAromaCoreConfig implements IConfigureNEI {
  public void loadConfig() {
    ShapedAromicRecipeHandler shaped = new ShapedAromicRecipeHandler();
    ShapelessAromicRecipeHandler shapeless = new ShapelessAromicRecipeHandler();
    API.registerRecipeHandler((ICraftingHandler)shaped);
    API.registerUsageHandler((IUsageHandler)shaped);
    API.registerRecipeHandler((ICraftingHandler)shapeless);
    API.registerUsageHandler((IUsageHandler)shapeless);
  }
  
  public String getName() {
    return "Aroma1997Core NEI Plugin";
  }
  
  public String getVersion() {
    return Tags.VERSION;
  }
}
