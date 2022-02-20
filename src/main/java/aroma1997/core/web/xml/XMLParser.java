package aroma1997.core.web.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
  HashMap<String, Node> xmlData = new HashMap<String, Node>();
  
  public XMLParser(InputStream XMLFile) throws SAXException, IOException, ParserConfigurationException {
    DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
    Document doc = docBuilder.parse(XMLFile);
    this.xmlData = new HashMap<String, Node>();
    parse("", doc.getDocumentElement());
  }
  
  private void parse(String prefix, Element e) {
    NodeList children = e.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node n = children.item(i);
      if (n.getNodeType() == 1) {
        this.xmlData.put(prefix + n.getNodeName(), n);
        parse(prefix + n.getNodeName() + ".", (Element)n);
      } 
    } 
  }
  
  public boolean containsKey(String key) {
    return this.xmlData.containsKey(key);
  }
  
  public boolean containsValue(String value) {
    return this.xmlData.containsKey(value);
  }
  
  public Node get(String key) {
    return this.xmlData.get(key);
  }
  
  public HashMap<String, Node> getAll() {
    return this.xmlData;
  }
}
