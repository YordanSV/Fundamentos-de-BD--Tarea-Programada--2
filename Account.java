/**
 * Account
 */
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Random;

public class Account {
    String accountNumber;
    String identity;
    String amount;

    public Account(String accountNumber, String identity, String amount) {
        this.accountNumber = accountNumber;
        this.identity = identity;
        this.amount = amount;
    }
    public Account() {
    }

    public String getIdentity() {
        return identity;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void crearCuenta(String identity) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        File inputFile = new File("account.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputFile);

        Element accountElement = document.createElement("account");

        Element accountNumberElement = document.createElement("accountNumber");
        Random random = new Random();
        accountNumberElement.setTextContent(String.valueOf(random.nextInt(10000)));
        Element identityElement = document.createElement("identity");
        identityElement.setTextContent(identity);
        Element amountElement = document.createElement("amount");
        amountElement.setTextContent("0");

        accountElement.appendChild(accountNumberElement);
        accountElement.appendChild(identityElement);
        accountElement.appendChild(amountElement);

        Element rootElement = document.getDocumentElement();
        rootElement.appendChild(accountElement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("account.xml"));
        transformer.transform(source, result);

        System.out.println("Cuenta agregada exitosamente al archivo XML.");
        borrarLineasBlancas();
    }

    public void setAmount(String modify) {
        amount = Integer.toString(Integer.parseInt(amount) + Integer.parseInt(modify));
    }
    
    
    public void eliminarCuenta(String identity) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File inputFile = new File("Account.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputFile);
        Element rootElement = document.getDocumentElement();
        NodeList accountList = rootElement.getElementsByTagName("account");
        for (int i = 0; i < accountList.getLength(); i++) {
            Element account = (Element) accountList.item(i);
            String userIdentity = account.getElementsByTagName("identity").item(0).getTextContent();
            if (userIdentity.equals(identity)) {
                rootElement.removeChild(account);
                break;
            }
        }
        
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("Account.xml"));
        transformer.transform(source, result);
        System.out.println("Archivo XML modificado exitosamente.");
    }

    public void borrarLineasBlancas() throws IOException {
        try {
            File inputFile = new File("Account.xml");
    
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder stringBuilder = new StringBuilder();
    
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    if (!isFirstLine) {
                        stringBuilder.append(System.lineSeparator()); // Agrega una nueva línea solo si no es la primera línea
                    } else {
                        isFirstLine = false;
                    }
                    stringBuilder.append(line);
                }
            }
            reader.close();
    
            // Sobrescribir el archivo original con el contenido modificado
            FileWriter fileWriter = new FileWriter(inputFile);
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}