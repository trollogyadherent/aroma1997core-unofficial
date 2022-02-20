package aroma1997.core.items;

import aroma1997.core.util.AromaRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AromicItem extends Item {
  public AromicItem setRecipe(Object... params) {
    AromaRegistry.registerShapedAromicRecipe(new ItemStack(this), false, params);
    return this;
  }
  
  public AromicItem setNameAndTexture(String name) {
    setTextureName(name);
    setUnlocalizedName(name);
    return this;
  }
}
