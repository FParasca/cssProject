package pt.ul.fc.css.weatherwise.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pt.ul.fc.css.weatherwise.dataaccess.exception.PersistenceException;
import pt.ul.fc.css.weatherwise.dbulits.SetupDatabase;

@SpringBootApplication
public class SimpleClient implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SimpleClient.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // --- 1. SETUP THE DATABASE  ---
        try {
            SetupDatabase.run();
            System.out.println("WeatherWise application started successfully!");
        } catch (PersistenceException e) {
            System.err.println("FATAL: Could not setup the database.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}