package aroma1997.core.coremod.asm;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;
import java.io.IOException;

public class AromaAccessTransformer extends AccessTransformer {
  public AromaAccessTransformer() throws IOException {
    super("aroma1997core_at.cfg");
  }
}
