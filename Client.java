
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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.*;
import java.util.List;

public class Client extends User {

    public Client(String name, String lastName, String age, String identity, String password) {
        super(name, lastName, age, identity, password);
    }

    public void realizarConsulta(List<Account> accounts){
        for (int i = 0; i < accounts.size(); i++) {
            if(accounts.get(i).getIdentity().equals(this.identity)){
                System.out.println("Usted tiene un saldo de $" + accounts.get(i).getAmount() + "\n");
                return;
            }
        }
    }

    public void realizarRetiro(List<Account> accounts) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Cuanto desea retirar?   $");
        String amount = scanner.nextLine();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getIdentity().equals(this.identity)) {
                if(Integer.parseInt(amount) > Integer.parseInt(accounts.get(i).getAmount())){
                    System.out.println("Fondos insuficientes \n");
                    return;
                }
                accounts.get(i).setAmount("-" + amount);
                modifyAmount(accounts);
                return;
            }
        }
    }

    public void realizarDeposito(List<Account> accounts) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Cuanto desea Depositar?   $");
        String amount = scanner.nextLine();

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getIdentity().equals(this.identity)) {
                accounts.get(i).setAmount(amount);
                modifyAmount(accounts);
                return;
            }
        }
    }

    
    public void realizarTraslado(List<Account> accounts) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Cuanto desea Trasladar?   $");
        String amount = scanner.nextLine();
        System.out.println("Digite el numero de identificacion del destino");
        String destinationIdentity = scanner.nextLine();
        Transfer transfer = new Transfer(destinationIdentity, this.identity, amount);
        List<Transfer> transfers = transfer.getListTransfers();
        transfers.add(transfer);
        transfer.modifyTransfer(transfers);


        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getIdentity().equals(this.identity)) {
                accounts.get(i).setAmount("-" + amount);
                modifyAmount(accounts);
            }
            if (accounts.get(i).getIdentity().equals(destinationIdentity)) {
                accounts.get(i).setAmount(amount);
                modifyAmount(accounts);
            }
        }
    }
    
    public void modifyAmount(List<Account> accounts) { // modificar Account.xml para editar el monto                                                                        
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
            borrarLineasBlancas();
            System.out.println("Archivo XML modificado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
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