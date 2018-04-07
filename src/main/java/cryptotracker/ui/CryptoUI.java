package cryptotracker.ui;

import cryptotracker.domain.Database;


public class CryptoUI {
    
    public static void main(String args[]) throws Exception {
        Database database = new Database("jdbc:sqlite:db/cryptotracker.db");
    }

}
