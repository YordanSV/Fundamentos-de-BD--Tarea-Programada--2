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
import java.util.ArrayList;
import java.util.List;

public class Transfer {
    public String destinatario;
    public String remitente;
    public String monto;

    public Transfer(String destinatario, String remitente, String monto){
        this.destinatario = destinatario;
        this.remitente = remitente;
        this.monto = monto;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getRemitente() {
        return remitente;
    }

    public String getmonto() {
        return monto;
    }

    public void modifyTransfer(List<Transfer> tranfers) { // modificar Account.xml para editar el monto                                                                        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("Transfer.xml"));
            Element root = document.getDocumentElement();
            // Eliminar los nodos de traslado existentes
            NodeList traslados = root.getElementsByTagName("transfer");
            for (int i = traslados.getLength() - 1; i >= 0; i--) {
                Node traslado = traslados.item(i);
                root.removeChild(traslado);
            }
            // Crear nuevos nodos de traslado con los valores de la lista
            for (Transfer nodo : tranfers) {
                Element traslado = document.createElement("transfer");

                Element destinatario = document.createElement("destinatario");
                destinatario.appendChild(document.createTextNode(nodo.getDestinatario()));
                traslado.appendChild(destinatario);

                Element remitente = document.createElement("remitente");
                remitente.appendChild(document.createTextNode(nodo.getRemitente()));
                traslado.appendChild(remitente);

                Element monto = document.createElement("monto");
                monto.appendChild(document.createTextNode(String.valueOf(nodo.getmonto())));
                traslado.appendChild(monto);

                root.appendChild(traslado);
            }

            // Guardar los cambios en el archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new File("Transfer.xml")));
            borrarLineasBlancas();
            System.out.println("Archivo XML modificado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Transfer> getListTransfers(){
        List<Transfer> transfers = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("Transfer.xml");
            Element root = document.getDocumentElement();
            NodeList transferList = root.getElementsByTagName("transfer");

            // Recorrer los nodos 'transfer'
            for (int i = 0; i < transferList.getLength(); i++) {
                Element transfer = (Element) transferList.item(i);
                String destinatario = transfer.getElementsByTagName("destinatario").item(0).getTextContent();
                String remitente = transfer.getElementsByTagName("remitente").item(0).getTextContent();
                String monto = transfer.getElementsByTagName("monto").item(0).getTextContent();
                transfers.add(new Transfer(destinatario, remitente, monto));
            }
        } catch (
        Exception e) {
            e.printStackTrace();
        }
        return transfers;
    }

    
    public void borrarLineasBlancas() throws IOException {
        try {
            File inputFile = new File("Transfer.xml");
    
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
