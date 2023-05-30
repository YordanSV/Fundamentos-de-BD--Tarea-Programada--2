import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathConstants;
/**
 * User
 */
public class User {
    String name;
    String lastName;
    String edad;
    String identity;
    String password;
    
    public static boolean permissionAccess(String identity) { // Permisos de administrador
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("User.xml");
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expression = xpath.compile("//user[@type='administrator']");
            NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);

            // Recorrer los nodos encontrados
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element user = (Element) node;
                    String userType = user.getAttribute("type");
                    if (userType.equals("administrator") && user.getElementsByTagName("identity").item(0).getTextContent().equals(identity)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}