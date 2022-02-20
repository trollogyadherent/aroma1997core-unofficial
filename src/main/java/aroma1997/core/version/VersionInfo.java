package aroma1997.core.version;

public class VersionInfo {
  private String modID;
  
  private String version;
  
  private String updateURL;
  
  private String xmlURL;
  
  VersionInfo(String modID, String version, String updateURL, String xmlURL) {
    this.modID = modID;
    this.version = version;
    this.updateURL = updateURL;
    this.xmlURL = xmlURL;
  }
  
  VersionInfo(String modID, String version) {
    this(modID, version, "http://tinyurl.com/aroma1997", "http://a.aroma1997.org/mcmods/version.xml");
  }
  
  public String getModID() {
    return this.modID;
  }
  
  public String getVersion() {
    return this.version;
  }
  
  public String updateURL() {
    return this.updateURL;
  }
  
  public String getXMLURL() {
    return this.xmlURL;
  }
}
