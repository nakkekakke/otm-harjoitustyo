package cryptotracker.ui;

import cryptotracker.domain.*;
import cryptotracker.dao.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/** The class for the user interface
 * 
 */
public class CryptoUI extends Application {
    
    private Database database;
    private UserDao userDao;
    private PortfolioDao portfolioDao;
    private CryptoService service;
    
    private Scene loginScene;
    private Scene loggedInScene;
    
    
    private VBox o;
    private Label menuLabel = new Label();
    
    
    /** Initializes the program
     *
     * @throws java.lang.ClassNotFoundException If initializing the database fails
     * @throws java.io.FileNotFoundException If configuration file was not found
     * @throws java.io.IOException
     * @throws java.sql.SQLException 
     */
    @Override
    public void init() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
        Properties properties = new Properties();
        
        properties.load(new FileInputStream("config.properties"));
        
        String databaseAddress = properties.getProperty("databaseAddress");
        
        this.database = new Database("jdbc:sqlite:" + databaseAddress);
        this.database.initializeTables();
        this.userDao = new UserDao(database);
        this.portfolioDao = new PortfolioDao(database, userDao);
        this.service = new CryptoService(userDao, portfolioDao);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Login scene
        
        VBox loginPane = new VBox(10);
        HBox inputPane = new HBox(10);
        loginPane.setPadding(new Insets(10));
        Label loginLabel = new Label("Enter your username");
        TextField usernameInput = new TextField();
        
        inputPane.getChildren().addAll(loginLabel, usernameInput);
        Label loginMessage = new Label();
        
        Button loginButton = new Button("Login");
        Button registrationButton = new Button("Register");
        
        loginButton.setOnAction(e -> {
            String username = usernameInput.getText();
            menuLabel.setText("Welcome, " + username + "!");
            if (service.login(username)) {
                loginMessage.setText("");
                usernameInput.setText("");
                primaryStage.setScene(loggedInScene);
            } else {
                loginMessage.setText("Username doesn't exist!");
                loginMessage.setTextFill(Color.RED);
            }
        });
        
        registrationButton.setOnAction(e -> {
            String username = usernameInput.getText();
            
            if (service.usernameLengthValid(username) == false) {
                loginMessage.setText("Username must be " + 
                        service.getUsernameMinLength() + "-" + 
                        service.getUsernameMaxLength() + " characters long!");
                loginMessage.setTextFill(Color.RED);
            } else if (service.createUser(username)) {
                loginMessage.setText("");
                loginMessage.setText("New user created!");
                loginMessage.setTextFill(Color.GREEN);
            } else {
                loginMessage.setText("Username already exists!");
                loginMessage.setTextFill(Color.RED);
            }
        });
        
        loginPane.getChildren().addAll(loginMessage, inputPane, loginButton, registrationButton);
        
        loginScene = new Scene(loginPane, 400, 250);
        
        // Main scene (shown when logged in)
        
        ScrollPane mainScrollPane = new ScrollPane();
        BorderPane mainPane = new BorderPane(mainScrollPane);
        loggedInScene = new Scene(mainPane, 400, 250);
        
        HBox menuPane = new HBox(10);
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        Button logoutButton = new Button("Logout");
        menuPane.getChildren().addAll(menuLabel, menuSpacer, logoutButton);
        
        logoutButton.setOnAction(e -> {
            service.logout();
            primaryStage.setScene(loginScene);
        });
        
        mainPane.setTop(menuPane);
        
        primaryStage.setTitle("CryptoTracker");
        primaryStage.setScene(loginScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Closing program");
        });
    }
    
    @Override
    public void stop() {
        service.logout();
        System.out.println("Bye!");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
//    public void start() {
//        System.out.println("Welcome to CryptoTracker!");
//        loginView();
//    }
//
//    public void loginView() {
//        while (true) {
//            System.out.println("\n-----------------\n");
//            System.out.println("Choose what to do (0-2):");
//            System.out.println("0: Quit");
//            System.out.println("1: Register");
//            System.out.println("2: Log in");
//            System.out.println("");
//            System.out.print("I choose option: ");
//            String option = scanner.nextLine();
//            
//            if (option.equals("0")) {
//                System.out.println("Bye!");
//                break;
//                
//            } else if (option.equals("1")) {
//                register();
//                
//            } else if (option.equals("2")) {
//                if (login() == true) {
//                    break;
//                }
//                
//            } else {
//                System.out.println("Try again.");
//            }
//        }
//        
//        if (service.getLoggedIn() != null) {
//            loggedInView();
//        }
//    }
//    
//    public void loggedInView() {
//        System.out.println("\n-----------------\n");
//        System.out.println("Welcome, " + service.getLoggedIn().getUsername() + "!");
//        while (true) {
//            System.out.println("\n-----------------\n");
//            System.out.println("Choose what to do: (0-1)");
//            System.out.println("0: Log out");
//            System.out.println("1: Show portfolio");
//            System.out.println("");
//            System.out.print("I choose option: ");
//            String option = scanner.nextLine();
//            
//            if (option.equals("0")) {
//                logout();
//                break;
//            } else if (option.equals("1")) {
//                System.out.println("This feature will be implemented soon, stay tuned!");
//            } else {
//                System.out.println("Try again.");
//            }
//        }
//        
//        if (service.getLoggedIn() == null) {
//            loginView();
//        } else {
//            System.out.println("Something went wrong...");
//        }
//        
//    }
//    
//    private boolean register() {
//        System.out.print("Choose your username: ");
//        String username = scanner.nextLine();
//        if (!service.usernameLengthValid(username)) {
//            System.out.println("Username must be " + service.getUsernameMinLength()
//                    + "-" + service.getUsernameMaxLength() + " characters long");
//            return false;
//        } else if (service.createUser(username) == false) {
//            System.out.println("Username is already taken!");
//            return false;
//        } else {
//            System.out.println("Registration successful!");
//            return true;
//        }
//    }
//    
//    private boolean login() {
//        System.out.print("Enter your username: ");
//        String username = scanner.nextLine();
//        if (service.login(username) == false) {
//            System.out.println("Username doesn't exist!");
//            return false;
//        } else {
//            System.out.println("Username found! Logging in...");
//            return true;
//        }
//    }
//    
//    private void logout() {
//        service.logout();
//        System.out.println("Logged out!");
//    }



}
