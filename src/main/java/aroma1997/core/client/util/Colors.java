package aroma1997.core.client.util;

import net.minecraft.util.EnumChatFormatting;

public enum Colors {
  BLACK, DARKBLUE, GREEN, CYAN, RED, PURPLE, ORANGE, LIGHTGRAY, GRAY, LIGHTBLUE, LIME, TURQUISE, PINK, MAGENTA, YELLOW, WHITE, OBFUSCATED, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC, RESET;
  
  private EnumChatFormatting getFormatting() {
    return EnumChatFormatting.values()[ordinal()];
  }
  
  public String toString() {
    return getFormatting().toString();
  }
  
  public String toName() {
    String str = super.toString();
    return str;
  }
  
  public static String applyColors(String string) {
    for (Colors color : values()) {
      String str = string.replace(color.toName(), color.toString());
      if (!str.equals(string))
        string = str; 
    } 
    return string;
  }
}
