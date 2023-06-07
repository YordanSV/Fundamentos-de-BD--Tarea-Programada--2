/**
 * Transaction
 */
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.util.Scanner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    public String identidad;
    public String tipo; 
    public String monto;

    public Transaction(String identidad, String tipo, String monto){
        this.identidad = identidad;
        this.tipo = tipo;
        this.monto = monto;
    }
    public Transaction(){
    }


    public void crearTransaccion() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File inputFile = new File("Transaction.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputFile);
        Element rootElement = document.getDocumentElement();

        Element newTransaction = document.createElement("transaction");

        Element ElementIdentidad = document.createElement("identidad");
        ElementIdentidad.setTextContent(this.identidad);
        Element ElementTipo = document.createElement("tipo");
        ElementTipo.setTextContent(this.tipo);
        Element ElementMonto = document.createElement("monto");
        ElementMonto.setTextContent(this.monto);

        newTransaction.appendChild(ElementIdentidad);
        newTransaction.appendChild(ElementTipo);
        newTransaction.appendChild(ElementMonto);

        rootElement.appendChild(newTransaction);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("Transaction.xml"));
        transformer.transform(source, result);
        borrarLineasBlancas();
        System.out.println("Archivo XML modificado exitosamente.");
    }

    public void imprimirTransaccionesPorIdentidad(String identidadParametro) throws ParserConfigurationException, IOException, SAXException {
            File inputFile = new File("Transaction.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            
            NodeList transactionList = document.getElementsByTagName("transaction");
            
            for (int i = 0; i < transactionList.getLength(); i++) {
                Node transactionNode = transactionList.item(i);
                
                if (transactionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element transactionElement = (Element) transactionNode;
                    String identidad = transactionElement.getElementsByTagName("identidad").item(0).getTextContent();
                    
                    if (identidad.equals(identidadParametro)) {
                        System.out.print("Transaccion " + (i + 1) + ": ");
                        System.out.print("Identidad: " + identidad);
                        System.out.print(", Tipo: " + transactionElement.getElementsByTagName("tipo").item(0).getTextContent());
                        System.out.println(", Monto: " + transactionElement.getElementsByTagName("monto").item(0).getTextContent());
                        System.out.println("-----------------------------------------------------------------------------------------");
                    }
                }
            }
        }
        
    public void borrarLineasBlancas() throws IOException {
        try {
            File inputFile = new File("Transaction.xml");
    
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