package aroma1997.core.util.registry;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import net.minecraft.item.ItemBlock;

@Retention(RetentionPolicy.RUNTIME)
public @interface SpecialItemBlock {
  Class<? extends ItemBlock> value();
}
