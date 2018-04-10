package cryptotracker.ui;

import cryptotracker.domain.*;
import cryptotracker.dao.*;
import java.util.Scanner;


/** The class for the user interface
 * 
 */
public class CryptoUI {
    private Scanner scanner;
    private Database database;
    private UserDao userDao;
    private PortfolioDao portfolioDao;
    private CryptoService service;
    
    public CryptoUI(Scanner scanner, Database database) {
        this.scanner = scanner;
        this.database = database;
        this.userDao = new UserDao(database);
        this.portfolioDao = new PortfolioDao(database, userDao);
        this.service = new CryptoService(userDao, portfolioDao);
    }
    
    public void start() {
        System.out.println("Welcome to CryptoTracker!");
        loginView();
    }

    public void loginView() {
        while (true) {
            System.out.println("\n-----------------\n");
            System.out.println("Choose what to do (0-2):");
            System.out.println("0: Quit");
            System.out.println("1: Register");
            System.out.println("2: Log in");
            System.out.println("");
            System.out.print("I choose option: ");
            String option = scanner.nextLine();
            
            if (option.equals("0")) {
                System.out.println("Bye!");
                break;
                
            } else if (option.equals("1")) {
                register();
                
            } else if (option.equals("2")) {
                if (login() == true) {
                    break;
                }
                
            } else {
                System.out.println("Try again.");
            }
        }
        
        if (service.getLoggedIn() != null) {
            loggedInView();
        }
    }
    
    public void loggedInView() {
        System.out.println("\n-----------------\n");
        System.out.println("Welcome, " + service.getLoggedIn().getUsername() + "!");
        while (true) {
            System.out.println("\n-----------------\n");
            System.out.println("Choose what to do: (0-1)");
            System.out.println("0: Log out");
            System.out.println("1: Show portfolio");
            System.out.println("");
            System.out.print("I choose option: ");
            String option = scanner.nextLine();
            
            if (option.equals("0")) {
                logout();
                break;
            } else if (option.equals("1")) {
                System.out.println("This feature will be implemented soon, stay tuned!");
            } else {
                System.out.println("Try again.");
            }
        }
        
        if (service.getLoggedIn() == null) {
            loginView();
        } else {
            System.out.println("Something went wrong...");
        }
        
    }
    
    private boolean register() {
        System.out.print("Choose your username: ");
        String username = scanner.nextLine();
        if (!service.usernameLengthValid(username)) {
            System.out.println("Username must be " + service.getUsernameMinLength()
                    + "-" + service.getUsernameMaxLength() + " characters long");
            return false;
        } else if (service.createUser(username) == false) {
            System.out.println("Username is already taken!");
            return false;
        } else {
            System.out.println("Registration successful!");
            return true;
        }
    }
    
    private boolean login() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        if (service.login(username) == false) {
            System.out.println("Username doesn't exist!");
            return false;
        } else {
            System.out.println("Username found! Logging in...");
            return true;
        }
    }
    
    private void logout() {
        service.logout();
        System.out.println("Logged out!");
    }

}
