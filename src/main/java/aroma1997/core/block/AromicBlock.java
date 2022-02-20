package aroma1997.core.block;

import aroma1997.core.util.AromaRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class AromicBlock extends Block {
  private String textureName;
  private String unlocalizedName;
  public AromicBlock(Material material) {
    super(material);
  }
  
  public AromicBlock setRecipe(Object... params) {
    AromaRegistry.registerShapedAromicRecipe(new ItemStack(this), false, params);
    return this;
  }
  
  public AromicBlock setNameAndTexture(String name) {
    setTextureName(name);
    setUnlocalizedName(name);
    return this;
  }

  private void setTextureName(String name) {
    textureName = name;
  }

  private void setUnlocalizedName(String name) {
    textureName = name;
  }
}
