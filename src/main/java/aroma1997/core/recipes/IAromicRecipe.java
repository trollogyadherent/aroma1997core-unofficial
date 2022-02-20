package aroma1997.core.recipes;

import net.minecraft.item.crafting.IRecipe;

public interface IAromicRecipe extends IRecipe {
  boolean isHidden();
  
  void setHidden(boolean paramBoolean);
  
  boolean isMirrorOK();
  
  void setMirrorOK(boolean paramBoolean);
  
  Object[] getInput();
}
