package aroma1997.core.inventories;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IProgressable {
  @SideOnly(Side.CLIENT)
  float getProgress(Object paramObject);
  
  float getProgress();
}
