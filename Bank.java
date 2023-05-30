import java.util.ArrayList;
import java.util.List;
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

public class Bank {
    public List<Account> accounts;

    public Bank() {
        try {
            accounts = new ArrayList<>();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("Account.xml");
            Element root = document.getDocumentElement();
            NodeList accountList = root.getElementsByTagName("account");

            // Recorrer los nodos 'account'
            for (int i = 0; i < accountList.getLength(); i++) {
                Element account = (Element) accountList.item(i);
                String accountNumber = account.getElementsByTagName("accountNumber").item(0).getTextContent();
                String identity = account.getElementsByTagName("identity").item(0).getTextContent();
                String amount = account.getElementsByTagName("amount").item(0).getTextContent();
                accounts.add(new Account(accountNumber, identity, amount));
            }
        } catch (
        Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        for (int i = 0; i < bank.accounts.size(); i++) {
            System.out.println(bank.accounts.get(i).getAccountNumber());
        }
    }
}
