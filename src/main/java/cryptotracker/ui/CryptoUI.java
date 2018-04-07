package cryptotracker.ui;

import cryptotracker.domain.*;
import cryptotracker.dao.*;
import java.util.Scanner;


public class CryptoUI {
    
    public static void main(String args[]) throws Exception {
        Database database = new Database("jdbc:sqlite:db/cryptotracker.db");
        
        UserDao userDao = new UserDao(database);
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome!");
        System.out.println("");
        while (true) {
            System.out.println("Valitse toiminto (0-3):");
            System.out.println("0: Poistu ohjelmasta");
            System.out.println("1: Rekisteröidy");
            System.out.println("2: Kirjaudu sisään");
            System.out.println("3: Poista henkilö");
            
            System.out.print("Valitsen toiminnon: ");
            
            String toiminto = scanner.nextLine();
            if (toiminto.equals("0")) {
                break;
            } else if (toiminto.equals("1")) {
                System.out.print("Valitse käyttäjänimi: ");
                String kayttajanimi = scanner.nextLine();
                User user = new User(1, kayttajanimi);
                if (userDao.save(user) == null) {
                    System.out.println("Nimi on jo käytössä!");
                } else {
                    System.out.println("Tallennettu!");
                }
            } else if (toiminto.equals("2")) {
                System.out.print("Kirjoita käyttäjänimesi: ");
                String kayttajanimi = scanner.nextLine();
                User user = userDao.findOneWithUsername(kayttajanimi);
                if (user == null) {
                    System.out.println("Ei löytynyt...");
                } else {
                    System.out.println("Löytyi! Kirjaudutaan sisään");
                    break;
                }
            } else if (toiminto.equals("3")) {
                System.out.print("Kirjoita poistettavan nimi: ");
                String poistettava = scanner.nextLine();
                User user = userDao.findOneWithUsername(poistettava);
                if (user == null) {
                    System.out.println("Poistettavaa ei löytynyt...");
                } else {
                    userDao.delete(user.getId());
                    System.out.println("Sinne meni!");
                }
                
            } else {
                System.out.println("Yritäpä uudestaan");
            }
            
            
        }
        
    }

}
