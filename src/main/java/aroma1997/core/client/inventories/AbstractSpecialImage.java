package aroma1997.core.client.inventories;

import aroma1997.core.inventories.IProgressable;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractSpecialImage {
  private int x;
  
  private int y;
  
  public AbstractSpecialImage(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public abstract float getProgress();
  
  protected abstract boolean progressX();
  
  protected abstract boolean inverted();
  
  public int getStartX() {
    if (!progressX() || !inverted())
      return getMinX(); 
    return getMinX() + (int)(getMaxSizeX() * getProgress());
  }
  
  public int getStartY() {
    if (progressX() || !inverted())
      return getMinY(); 
    return getMinY() + (int)(getMaxSizeY() * getProgress());
  }
  
  public int getSizeX() {
    if (!progressX())
      return getMaxSizeX(); 
    return (int)(getMaxSizeX() * getProgress());
  }
  
  public int getSizeY() {
    if (progressX())
      return getMaxSizeY(); 
    return (int)(getMaxSizeY() * getProgress());
  }
  
  public void draw(Gui gui) {
    RenderHelper.bindTexture(getRL());
    gui.drawTexturedModalRect(getStartX(), getStartY(), getMinX(), getMinY(), getSizeX(), getSizeY());
    if (getBackgroundX() != -1)
      gui.drawTexturedModalRect(getPosX(), getPosY(), getBackgroundX(), getBackgroundY(), getMaxSizeX(), getMaxSizeY()); 
  }
  
  public int getPosX() {
    return this.x;
  }
  
  public int getPosY() {
    return this.y;
  }
  
  protected abstract int getMinX();
  
  protected abstract int getMaxSizeX();
  
  protected abstract int getMinY();
  
  protected abstract int getMaxSizeY();
  
  public abstract ResourceLocation getRL();
  
  public abstract int getBackgroundX();
  
  public abstract int getBackgroundY();
  
  public static abstract class ProgressableImage extends AbstractSpecialImage {
    private IProgressable prog;
    
    public ProgressableImage(int x, int y, IProgressable prog) {
      super(x, y);
      this.prog = prog;
    }
    
    public float getProgress() {
      return this.prog.getProgress(this);
    }
  }
}
