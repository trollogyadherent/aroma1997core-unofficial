package aroma1997.core.coremod.asm;

import aroma1997.core.coremod.MCPNames;
import com.google.common.collect.Lists;
import java.util.List;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public abstract class CallHookInsertionTransformer extends BasicTransformer {
  private String hookClass;
  
  private String hookMethod;
  
  private boolean ret;
  
  private String retStore;
  
  private List<String> methods;
  
  private int num;
  
  protected CallHookInsertionTransformer(String hookClass, String hookMethod) {
    this(hookClass, hookMethod, false);
  }
  
  protected CallHookInsertionTransformer(String hookClass, String hookMethod, boolean ret) {
    this.retStore = "V";
    this.methods = Lists.newArrayList();
    this.hookClass = hookClass.replace('.', '/');
    this.hookMethod = hookMethod;
    this.ret = ret;
  }
  
  protected void addMethodHook(String srgname) {
    this.methods.add(MCPNames.method(srgname));
  }
  
  public void transform(ClassNode classNode) {
    for (MethodNode mn : classNode.methods) {
      if (this.methods.contains(mn.name)) {
        logger.info("Transforming " + classNode.name + "/" + mn.name + mn.desc);
        logger.info("Now using " + this.hookClass + "/" + this.hookMethod + buildCallHookDescriptor(classNode, mn));
        String toProcess = mn.desc.substring(1);
        InsnList toInsert = new InsnList();
        toInsert.add((AbstractInsnNode)new VarInsnNode(25, 0));
        this.num = 1;
        while (toProcess != null)
          toProcess = process(classNode, mn, toProcess, toInsert); 
        toInsert.add((AbstractInsnNode)new MethodInsnNode(184, this.hookClass, this.hookMethod, buildCallHookDescriptor(classNode, mn), false));
        if (this.ret)
          addRet(classNode, mn, toInsert); 
        mn.instructions.insert(toInsert);
      } 
    } 
  }
  
  private String process(ClassNode cn, MethodNode mn, String desc, InsnList list) {
    int oc;
    char c = desc.charAt(0);
    if (c == 'L') {
      list.add((AbstractInsnNode)new VarInsnNode(25, this.num++));
      return desc.substring(desc.indexOf(';') + 1);
    } 
    if (c == ')') {
      if (this.ret)
        this.retStore = desc.substring(1); 
      return null;
    } 
    if (c == 'Z' || c == 'B' || c == 'S' || c == 'I') {
      oc = 21;
    } else if (c == 'J') {
      oc = 22;
    } else if (c == 'F') {
      oc = 23;
    } else if (c == 'D') {
      oc = 24;
    } else {
      throw new RuntimeException("Failed to parse Method " + cn.name + "/" + mn.name + "  with descriptor left: " + desc + " at num: " + this.num + " Letter: " + c);
    } 
    list.add((AbstractInsnNode)new VarInsnNode(oc, this.num++));
    return desc.substring(1);
  }
  
  private void addRet(ClassNode cn, MethodNode mn, InsnList list) {
    int oc;
    char c = this.retStore.charAt(0);
    if (c == 'L') {
      oc = 173;
    } else if (c == 'Z' || c == 'B' || c == 'S' || c == 'I') {
      oc = 172;
    } else if (c == 'J') {
      oc = 173;
    } else if (c == 'F') {
      oc = 174;
    } else if (c == 'D') {
      oc = 175;
    } else if (c == 'V') {
      oc = 177;
    } else {
      throw new RuntimeException("Failed to parse Method return. " + cn.name + "/" + mn.name + "Letter: " + c);
    } 
    list.add((AbstractInsnNode)new InsnNode(oc));
  }
  
  private String buildCallHookDescriptor(ClassNode cn, MethodNode mn) {
    String ret = "(L" + cn.name + ";" + mn.desc.substring(1);
    String[] tmp = ret.split("\\)");
    ret = tmp[0] + ")" + this.retStore;
    return ret;
  }
}
