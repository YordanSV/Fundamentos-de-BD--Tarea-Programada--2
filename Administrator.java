import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Administrator extends User {
    public Administrator(String name, String lastName, String age, String identity, String password) {
        super(name, lastName, age, identity, password);
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Administrator administrator = new Administrator("Juan", "Garro", "64", "123", "holamundo");
        administrator.crearUsuario();
        administrator.eliminarUsuario();
    }

    public void modificarUsuario()  throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File inputFile = new File("user.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputFile);
        Element rootElement = document.getDocumentElement();
        NodeList userList = rootElement.getElementsByTagName("user");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite la identificacion de quien desea modificar: ");
        String identity = scanner.nextLine();
        System.out.println("\nQue cambio desea realizar");
        System.out.println("1) Nombre");
        System.out.println("2) Apellido");
        System.out.println("3) Edad");
        System.out.println("4) Clave");
        System.out.println("Seleccione una opcion");
        String option = scanner.nextLine();
        if(option.equals("1")){
            option = "name";
        }else if(option.equals("2")){
            option = "lastName";
        }else if(option.equals("3")){
            option = "age";
        }else if(option.equals("4")){
            option = "password";
        }
        System.out.print("Ingrese el cambio de " + option + ": ");
        String cambio = scanner.nextLine();
        for (int i = 0; i < userList.getLength(); i++) {
            Element user = (Element) userList.item(i);
            String userIdentity = user.getElementsByTagName("identity").item(0).getTextContent();
            if (userIdentity.equals(identity)) {
                user.getElementsByTagName(option).item(0).setTextContent(cambio);
                break;
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("user.xml"));
        transformer.transform(source, result);
    }

    public void eliminarUsuario() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File inputFile = new File("user.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputFile);
        Element rootElement = document.getDocumentElement();
        NodeList userList = rootElement.getElementsByTagName("user");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite la identificacion de quien desea eliminar: ");
        String identity = scanner.nextLine();
        for (int i = 0; i < userList.getLength(); i++) {
            Element user = (Element) userList.item(i);
            String userIdentity = user.getElementsByTagName("identity").item(0).getTextContent();
            if (userIdentity.equals(identity)) {
                System.out.print("Estas apunto de eliminar a " + user.getElementsByTagName("name").item(0).getTextContent() + "\n¿Desea continuar?  y/n \n");
                String respuesta = scanner.nextLine();
                if(respuesta.equals("n")){return;}
                rootElement.removeChild(user);
                break;
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("user.xml"));
        transformer.transform(source, result);
    }

    public void crearUsuario() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File inputFile = new File("user.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputFile);
        Element rootElement = document.getDocumentElement();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del nuevo usuario");
        String name = scanner.nextLine();
        System.out.println("Ingrese el apellido del nuevo usuario");
        String lastName = scanner.nextLine();
        System.out.println("Ingrese la edad del nuevo usuario");
        String age = scanner.nextLine();
        System.out.println("Ingrese la identidad del nuevo usuario");
        String identity = scanner.nextLine();
        System.out.println("Ingrese la contrasena del nuevo usuario");
        String password = scanner.nextLine();
        System.out.println("Desea que sea administrador? y/n");
        String esAdmin = scanner.nextLine();

        Element newUser = document.createElement("user");
        newUser.setAttribute("type", esAdmin.equals("y") ? "administrator" : "client");

        Element ElementName = document.createElement("name");
        ElementName.setTextContent(name);
        Element ElementLastName = document.createElement("lastName");
        ElementLastName.setTextContent(lastName);
        Element ElementAge = document.createElement("age");
        ElementAge.setTextContent(age);
        Element ElementIdentity = document.createElement("identity");
        ElementIdentity.setTextContent(identity);
        Element ElementPassword = document.createElement("password");
        ElementPassword.setTextContent(password);

        newUser.appendChild(ElementName);
        newUser.appendChild(ElementLastName);
        newUser.appendChild(ElementAge);
        newUser.appendChild(ElementIdentity);
        newUser.appendChild(ElementPassword);

        rootElement.appendChild(newUser);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("user.xml"));
        transformer.transform(source, result);
    }

}
