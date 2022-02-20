package aroma1997.core.coremod.asm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

public abstract class ClassAnnotationInsertionTransformer extends BasicTransformer {
  private HashMap<String, List> annotations = new HashMap<String, List>();
  
  protected void addAnnotation(String clazz, List list) {
    this.annotations.put("L" + clazz.replace('.', '/') + ";", list);
  }
  
  public void transform(ClassNode classNode) {
    if (classNode.visibleAnnotations == null)
      classNode.visibleAnnotations = new ArrayList(); 
    for (Map.Entry<String, List> e : this.annotations.entrySet()) {
      AnnotationNode an = new AnnotationNode(e.getKey());
      an.values = e.getValue();
      classNode.visibleAnnotations.add(an);
    } 
  }
}
