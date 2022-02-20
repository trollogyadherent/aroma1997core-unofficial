package aroma1997.core.client.inventories;

import aroma1997.core.inventories.IProgressable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class SpecialImagesBase {
  private static final ResourceLocation mainimage = new ResourceLocation("");
  
  public static class FireBar extends AbstractSpecialImage.ProgressableImage {
    public FireBar(int x, int y, IProgressable prog) {
      super(x, y, prog);
    }
    
    protected boolean progressX() {
      return false;
    }
    
    protected boolean inverted() {
      return true;
    }
    
    protected int getMinX() {
      return 0;
    }
    
    protected int getMaxSizeX() {
      return 0;
    }
    
    protected int getMinY() {
      return 0;
    }
    
    protected int getMaxSizeY() {
      return 0;
    }
    
    public ResourceLocation getRL() {
      return SpecialImagesBase.mainimage;
    }
    
    public int getBackgroundX() {
      return -1;
    }
    
    public int getBackgroundY() {
      return 0;
    }
  }
  
  public static class ProgressBarArrow extends AbstractSpecialImage.ProgressableImage {
    public ProgressBarArrow(int x, int y, IProgressable prog) {
      super(x, y, prog);
    }
    
    protected boolean progressX() {
      return true;
    }
    
    protected boolean inverted() {
      return false;
    }
    
    protected int getMinX() {
      return 0;
    }
    
    protected int getMaxSizeX() {
      return 0;
    }
    
    protected int getMinY() {
      return 0;
    }
    
    protected int getMaxSizeY() {
      return 0;
    }
    
    public ResourceLocation getRL() {
      return SpecialImagesBase.mainimage;
    }
    
    public int getBackgroundX() {
      return -1;
    }
    
    public int getBackgroundY() {
      return 0;
    }
  }
  
  public static class ProgressBarFancy extends AbstractSpecialImage.ProgressableImage {
    public ProgressBarFancy(int x, int y, IProgressable prog) {
      super(x, y, prog);
    }
    
    protected boolean progressX() {
      return true;
    }
    
    protected boolean inverted() {
      return false;
    }
    
    protected int getMinX() {
      return 0;
    }
    
    protected int getMaxSizeX() {
      return 0;
    }
    
    protected int getMinY() {
      return 0;
    }
    
    protected int getMaxSizeY() {
      return 0;
    }
    
    public ResourceLocation getRL() {
      return SpecialImagesBase.mainimage;
    }
    
    public int getBackgroundX() {
      return -1;
    }
    
    public int getBackgroundY() {
      return 0;
    }
  }
  
  public static class EnergyBar extends AbstractSpecialImage.ProgressableImage {
    public EnergyBar(int x, int y, IProgressable prog) {
      super(x, y, prog);
    }
    
    protected boolean progressX() {
      return false;
    }
    
    protected boolean inverted() {
      return true;
    }
    
    protected int getMinX() {
      return 0;
    }
    
    protected int getMaxSizeX() {
      return 0;
    }
    
    protected int getMinY() {
      return 0;
    }
    
    protected int getMaxSizeY() {
      return 0;
    }
    
    public int getBackgroundX() {
      return -1;
    }
    
    public int getBackgroundY() {
      return 0;
    }
    
    public ResourceLocation getRL() {
      return SpecialImagesBase.mainimage;
    }
  }
}
