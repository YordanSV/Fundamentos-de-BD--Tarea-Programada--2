import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException { // Main para probar las funciones
        System.out.print("\033[H\033[2J");
        System.out.flush();
        showMenuSignIn();
    }

    public static void showMenuSignIn() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        while (true) {
            System.out.println("============Inicia sesion============");
            System.out.println("=====Utiliza tu cuenta de usuario=====");
            System.out.println("Ingrese su numero de identificacion");
            String identity = scanner.nextLine();
            System.out.println("Ingrese su clave");
            String password = scanner.nextLine();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            if (User.signIn(identity, password)) {
                User user = User.getUser(identity);
                if (User.permissionAccess(identity)) {
                    Administrator administrator = new Administrator(user.name, user.lastName, user.age, user.identity,
                            user.password);
                    showMenuAdministrator(administrator);
                } else {
                    Client client = new Client(user.name, user.lastName, user.age, user.identity, user.password);
                    showMenuClient(bank.accounts, client);
                }
            }
        }

    }

    public static void showMenuClient(List<Account> accounts, Client cliente) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido " + cliente.getName());
        System.out.println("1) Consulta");
        System.out.println("2) Retiro");
        System.out.println("3) Deposito");
        System.out.println("4) Traslado");
        System.out.println("5) Ver historial de transacciones");
        System.out.println("Seleccione una operacion");
        if (!scanner.hasNextInt()) {
            System.out.println("Ingrese un valor valido \n");
            showMenuClient(accounts, cliente);
        }
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                System.out.print("\033[H\033[2J");
                System.out.flush();
                cliente.realizarConsulta(accounts);
                break;
            case 2:
                System.out.print("\033[H\033[2J");
                System.out.flush();
                cliente.realizarRetiro(accounts);
                break;
            case 3:
                System.out.print("\033[H\033[2J");
                System.out.flush();
                cliente.realizarDeposito(accounts);
                break;
            case 4:
                System.out.print("\033[H\033[2J");
                System.out.flush();
                cliente.realizarTraslado(accounts);
                break;
            case 5:
                System.out.print("\033[H\033[2J");
                System.out.flush();
                Transaction historial = new Transaction();
                historial.imprimirTransaccionesPorIdentidad(cliente.getIdentity());
                break;
            default:
                System.out.println("Ingrese una opcion valida \n");
                break;
        }
        showMenuClient(accounts, cliente);
    }

    public static void showMenuAdministrator(Administrator administrator)
            throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido " + administrator.getName());
        System.out.println("1) Crear usuario");
        System.out.println("2) Modificar usuario");
        System.out.println("3) Eliminar usuario");
        System.out.println("4) Ver usuarios");
        System.out.println("Seleccione una operacion");
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                System.out.print("\033[H\033[2J");
                System.out.flush();
                administrator.crearUsuario();
                break;
            case 2:
                System.out.print("\033[H\033[2J");
                System.out.flush();
                administrator.modificarUsuario();
                break;
            case 3:
                System.out.print("\033[H\033[2J");
                System.out.flush();
                administrator.eliminarUsuario();
                break;
            case 4:
                System.out.print("\033[H\033[2J");
                System.out.flush();
                administrator.mostrarUsuarios();
                break;
            default:
                System.out.println("Ingrese una opcion valida \n");
                break;
        }
        showMenuAdministrator(administrator);
    }

}