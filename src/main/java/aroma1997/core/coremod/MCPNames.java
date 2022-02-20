package aroma1997.core.coremod;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class MCPNames {
  private static Map<String, String> fields;
  
  private static Map<String, String> methods;
  
  static {
    if (mcp()) {
      String mappingDir = "./../mcp/";
      fields = readMappings(new File(mappingDir + "fields.csv"));
      methods = readMappings(new File(mappingDir + "methods.csv"));
    } else {
      fields = methods = null;
    } 
  }
  
  public static boolean mcp() {
    return !CoreMod.runtimeDeobfuscationEnabled;
  }
  
  public static String field(String srgName) {
    if (mcp())
      return fields.get(srgName); 
    return srgName;
  }
  
  public static String method(String srgName) {
    if (mcp())
      return methods.get(srgName); 
    return srgName;
  }
  
  private static Map<String, String> readMappings(File file) {
    if (!file.isFile())
      throw new RuntimeException("Couldn't find MCP mappings."); 
    try {
      return (Map<String, String>)Files.readLines(file, Charsets.UTF_8, new MCPFileParser());
    } catch (IOException e) {
      throw new RuntimeException("Couldn't read SRG->MCP mappings", e);
    } 
  }
  
  private static class MCPFileParser implements LineProcessor<Map<String, String>> {
    private static final Splitter splitter = Splitter.on(',').trimResults();
    
    private final Map<String, String> map = Maps.newHashMap();
    
    private boolean foundFirst;
    
    public boolean processLine(String line) throws IOException {
      if (!this.foundFirst) {
        this.foundFirst = true;
        return true;
      } 
      Iterator<String> splitted = splitter.split(line).iterator();
      try {
        String srg = splitted.next();
        String mcp = splitted.next();
        if (!this.map.containsKey(srg))
          this.map.put(srg, mcp); 
      } catch (NoSuchElementException e) {
        throw new IOException("Invalid Mappings file!", e);
      } 
      return true;
    }
    
    public Map<String, String> getResult() {
      return (Map<String, String>)ImmutableMap.copyOf(this.map);
    }
    
    private MCPFileParser() {}
  }
}
