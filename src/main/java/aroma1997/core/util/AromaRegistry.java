package aroma1997.core.util;

import aroma1997.core.log.LogHelperPre;
import aroma1997.core.recipes.ShapedAromicRecipe;
import aroma1997.core.recipes.ShapelessAromicRecipe;
import aroma1997.core.util.registry.SpecialItemBlock;
import aroma1997.core.util.registry.SpecialName;
import cpw.mods.fml.common.registry.GameRegistry;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public class AromaRegistry {
  public static void register(Object o) {
    register(o.getClass(), o);
  }
  
  public static void register(Class<?> clazz) {
    register(clazz, null);
  }
  
  public static void register(Class<?> clazz, Object o) {
    for (Field field : clazz.getFields()) {
      if (!field.isAccessible())
        field.setAccessible(true); 
      try {
        String name = field.getName();
        SpecialName sname = field.<SpecialName>getAnnotation(SpecialName.class);
        if (sname != null)
          name = sname.value(); 
        if (Block.class.isInstance(field.get(o))) {
          SpecialItemBlock s = field.<SpecialItemBlock>getAnnotation(SpecialItemBlock.class);
          Annotation[] a = field.getAnnotations();
          if (s == null) {
            GameRegistry.registerBlock((Block)field.get(o), name);
          } else {
            GameRegistry.registerBlock((Block)field.get(o), s.value(), name);
          } 
        } else if (Item.class.isInstance(field.get(o))) {
          GameRegistry.registerItem((Item)field.get(o), name);
        } else if (IRecipe.class.isInstance(field.get(o))) {
          GameRegistry.addRecipe((IRecipe)field.get(o));
        } 
      } catch (Exception e) {
        LogHelperPre.logException("Failed to register: " + field.getName(), e);
      } 
    } 
    try {
      Method method = clazz.getDeclaredMethod("init", new Class[0]);
      if (!method.isAccessible())
        method.setAccessible(true); 
      method.invoke(o, new Object[0]);
    } catch (Exception e) {
      if (!(e instanceof NoSuchMethodException))
        LogHelperPre.logException("Failed to invoke the Initialization of: " + clazz.getCanonicalName(), e); 
    } 
  }
  
  public static IRecipe getShapedRecipe(ItemStack output, boolean hidden, Object... input) {
    ShapedAromicRecipe shapedAromicRecipe = new ShapedAromicRecipe(output, input);
    shapedAromicRecipe.setHidden(hidden);
    return (IRecipe)shapedAromicRecipe;
  }
  
  public static IRecipe getShapelessRecipe(ItemStack output, boolean hidden, Object... input) {
    ShapelessAromicRecipe shapelessAromicRecipe = new ShapelessAromicRecipe(output, input);
    shapelessAromicRecipe.setHidden(hidden);
    return (IRecipe)shapelessAromicRecipe;
  }
  
  @Deprecated
  public static void registerShapedOreRecipe(ItemStack item, Object... params) {
    registerShapedAromicRecipe(item, false, params);
  }
  
  @Deprecated
  public static void registerShapelessOreRecipe(ItemStack item, Object... params) {
    registerShapelessAromicRecipe(item, false, params);
  }
  
  public static void registerShapedAromicRecipe(ItemStack item, boolean hidden, Object... params) {
    GameRegistry.addRecipe(getShapedRecipe(item, hidden, params));
  }
  
  public static void registerShapelessAromicRecipe(ItemStack item, boolean hidden, Object... params) {
    GameRegistry.addRecipe(getShapelessRecipe(item, hidden, params));
  }
}
