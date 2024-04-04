import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConnection {

    private static volatile DatabaseConnection instance;

    private String hostname;
    private String username;
    private String password;
    private String databaseName;

    private DatabaseConnection() {
        loadConfiguration();
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    private void loadConfiguration() {
        try (InputStream input = new FileInputStream("/Users/daurenyesmukhanov/IdeaProjects/singleton/src/data.txt")) {
            Properties prop = new Properties();
            prop.load(input);
            hostname = prop.getProperty("hostname");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            databaseName = prop.getProperty("databaseName");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void connect() {
        System.out.println("Connected to database: " + hostname + "/" + databaseName);
    }

    public void executeQuery(String query) {
        System.out.println("Executing query: " + query);
    }

    public String getConnectionInfo() {
        return "Hostname: " + hostname + ", Username: " + username + ", Database: " + databaseName;
    }

    public static void main(String[] args) {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        dbConnection.connect();

        dbConnection.executeQuery("SELECT * FROM users");

        String connectionInfo = dbConnection.getConnectionInfo();
        System.out.println("Connection Information: " + connectionInfo);
    }
}
