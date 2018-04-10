package cryptotracker.ui;

import cryptotracker.dao.Database;
import java.util.Scanner;


public class Main {
    
    public static void main(String[] args) throws ClassNotFoundException {
        Database database = new Database("jdbc:sqlite:db/cryptotracker.db");
        Scanner scanner = new Scanner(System.in);
        
        CryptoUI cryptoui = new CryptoUI(scanner, database);
        cryptoui.start();
    }

}
