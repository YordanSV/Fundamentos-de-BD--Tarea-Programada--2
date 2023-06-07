import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
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
    public String name;
    public String lastName;
    public String age;
    public String identity;
    public String password;

    public User(String name, String lastName, String age, String identity, String password){
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.identity = identity;
        this.password = password;
    }

    public User(){
    }

    public String getName(){
        return name;
    }
    public String getIdentity(){
        return identity;
    }

    public static User getUser(String identity) {
        try {
            File inputFile = new File("user.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            Element rootElement = document.getDocumentElement();
            NodeList userList = rootElement.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++) {
                Element user = (Element) userList.item(i);
                String userIdentity = user.getElementsByTagName("identity").item(0).getTextContent();
                if (userIdentity.equals(identity)) {
                    String name = user.getElementsByTagName("name").item(0).getTextContent();
                    String lastName = user.getElementsByTagName("lastName").item(0).getTextContent();
                    String age = user.getElementsByTagName("age").item(0).getTextContent();
                    String password = user.getElementsByTagName("password").item(0).getTextContent();
                    return new User(name, lastName, age, identity, password);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return null;
        }

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

    
    public static boolean signIn(String identity, String password){
        try {
            File inputFile = new File("user.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            Element rootElement = document.getDocumentElement();
            NodeList userList = rootElement.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++) {
                Element user = (Element) userList.item(i);
                String userIdentity = user.getElementsByTagName("identity").item(0).getTextContent();
                String userPassword = user.getElementsByTagName("password").item(0).getTextContent();
                if (userIdentity.equals(identity) && userPassword.equals(password)) {
                    return true;
                }else if(userIdentity.equals(identity) && !userPassword.equals(password)){
                    System.out.println("Contrasena incorrecta \nIntente de nuevo\n");
                    break;
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}