package aroma1997.core.misc;

import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterNormal extends Teleporter {
  private ChunkCoordinates coords;
  
  private final WorldServer world;
  
  public TeleporterNormal(WorldServer world, ChunkCoordinates coords) {
    this(world);
    this.coords = coords;
  }
  
  public TeleporterNormal(WorldServer world) {
    super(world);
    this.world = world;
  }
  
  public void placeInPortal(Entity entity, double par2, double par4, double par6, float par8) {
    if (this.coords != null) {
      entity.setPositionAndRotation((this.coords.posX + 0.5F), (this.coords.posY + 0.1F), (this.coords.posZ + 0.5F), entity.rotationYaw, 0.0F);
    } else {
      entity.setLocationAndAngles(par2, par4, par6, entity.rotationYaw, 0.0F);
    } 
    while (!this.world.getCollidingBoundingBoxes(entity, entity.boundingBox).isEmpty())
      entity.setPosition(entity.posX, entity.posY + 1.0D, entity.posZ); 
  }
}
