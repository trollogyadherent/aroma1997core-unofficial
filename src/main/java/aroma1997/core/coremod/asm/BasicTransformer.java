package aroma1997.core.coremod.asm;

import aroma1997.core.log.LogHelperPre;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public abstract class BasicTransformer implements IClassTransformer {
  protected static final Logger logger = LogHelperPre.genNewLogger("Aroma1997Core-Transforming");
  
  private final List<String> classes = Lists.newArrayList();
  
  protected void addClass(String names) {
    this.classes.add(names);
  }
  
  public final byte[] transform(String name, String transformedName, byte[] bytes) {
    if (!this.classes.contains(transformedName))
      return bytes; 
    logger.log(Level.INFO, "Transforming " + transformedName);
    ClassNode classNode = new ClassNode();
    ClassReader classReader = new ClassReader(bytes);
    classReader.accept((ClassVisitor)classNode, 0);
    try {
      transform(classNode);
    } catch (Throwable t) {
      throw new RuntimeException("There was an error transforming " + transformedName, t);
    } 
    ClassWriter writer = new ClassWriter(1);
    classNode.accept((ClassVisitor)writer);
    return writer.toByteArray();
  }
  
  public abstract void transform(ClassNode paramClassNode);
}
