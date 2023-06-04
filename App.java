import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException { //Main para probar las funciones
        Bank bank = new Bank();
        Client client = new Client("Juan", "Garro", "64", "123", "holamundo");
        System.out.print("\033[H\033[2J");
        System.out.flush();
        showMenuClient(bank.accounts, client);
    }

    public static void showMenuClient(List<Account> accounts, Client cliente) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido " + cliente.getName());
        System.out.println("1) Consulta");
        System.out.println("2) Retiro");
        System.out.println("3) Deposito");
        System.out.println("4) Traslado");
        System.out.println("Seleccione una operacion");
        if(!scanner.hasNextInt()){
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
            default:
                System.out.println("Ingrese una opcion valida \n");
                break;
        }
        showMenuClient(accounts, cliente);
    }
    
}