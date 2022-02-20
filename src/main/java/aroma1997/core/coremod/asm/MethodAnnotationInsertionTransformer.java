package aroma1997.core.coremod.asm;

import aroma1997.core.coremod.MCPNames;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class MethodAnnotationInsertionTransformer extends BasicTransformer {
  private List<String> methods = new ArrayList<String>();
  
  private List<String> fields = new ArrayList<String>();
  
  private HashMap<String, List> annotations = new HashMap<String, List>();
  
  protected void addMethod(String name) {
    this.methods.add(MCPNames.method(name));
  }
  
  protected void addField(String name) {
    this.fields.add(MCPNames.field(name));
  }
  
  protected void addAnnotation(String clazz, List list) {
    this.annotations.put("L" + clazz.replace('.', '/') + ";", list);
  }
  
  public void transform(ClassNode classNode) {
    if (classNode.methods != null)
      for (MethodNode mn : classNode.methods) {
        if (this.methods.contains(mn.name)) {
          if (mn.visibleAnnotations == null)
            mn.visibleAnnotations = new ArrayList(); 
          for (Map.Entry<String, List> e : this.annotations.entrySet()) {
            AnnotationNode an = new AnnotationNode(e.getKey());
            an.values = e.getValue();
            mn.visibleAnnotations.add(an);
          } 
        } 
      }  
    if (classNode.fields != null)
      for (FieldNode fn : classNode.fields) {
        if (this.fields.contains(fn.name)) {
          if (fn.visibleAnnotations == null)
            fn.visibleAnnotations = new ArrayList(); 
          for (Map.Entry<String, List> e : this.annotations.entrySet()) {
            AnnotationNode an = new AnnotationNode(e.getKey());
            an.values = e.getValue();
            fn.visibleAnnotations.add(an);
          } 
        } 
      }  
  }
}
