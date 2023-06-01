
/**
 * Client
 */
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.util.List;

public class Client extends User {

    public Client(String name, String lastName, String age, String identity, String password) {
        super(name, lastName, age, identity, password);
    }

    public static void main(String[] args) throws IOException { //Main para probar las funciones
        Bank bank = new Bank();
        Client client = new Client("Juan", "Garro", "64", "123", "holamundo");
        client.realizarRetiro(bank.accounts, "1500");
    }

    public void realizarRetiro(List<Account> accounts, String amount) throws IOException {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getIdentity().equals(this.identity)) {
                accounts.get(i).setAmount("-" + amount);
                modifyAmount(accounts);
                return;
            }
        }
    }

    public void realizarDeposito(List<Account> accounts, String amount) throws IOException {
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(this.identity);
            if (accounts.get(i).getIdentity().equals(this.identity)) {
                accounts.get(i).setAmount(amount);
                modifyAmount(accounts);
                return;
            }
        }
    }

    public void modifyAmount(List<Account> accounts) throws IOException { // modificar Account.xml para editar el monto                                                                        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("Account.xml"));
            Element root = document.getDocumentElement();
            // Eliminar los nodos de cuenta existentes
            NodeList cuentas = root.getElementsByTagName("account");
            for (int i = cuentas.getLength() - 1; i >= 0; i--) {
                Node cuenta = cuentas.item(i);
                root.removeChild(cuenta);
            }
            // Crear nuevos nodos de cuenta con los valores de la lista
            for (Account nodo : accounts) {
                System.out.println(nodo.getIdentity());

                Element cuenta = document.createElement("account");

                Element accountNumber = document.createElement("accountNumber");
                accountNumber.appendChild(document.createTextNode(nodo.getAccountNumber()));
                cuenta.appendChild(accountNumber);

                Element identity = document.createElement("identity");
                identity.appendChild(document.createTextNode(nodo.getIdentity()));
                cuenta.appendChild(identity);

                Element account = document.createElement("amount");
                account.appendChild(document.createTextNode(String.valueOf(nodo.getAmount())));
                cuenta.appendChild(account);

                root.appendChild(cuenta);
            }

            // Guardar los cambios en el archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new File("Account.xml")));
            borrarLineasBlanco(accounts);
            System.out.println("Archivo XML modificado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrarLineasBlanco(List<Account> accounts) throws IOException {
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

            System.out.println("Archivo XML modificado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showMenu() {

    }
}