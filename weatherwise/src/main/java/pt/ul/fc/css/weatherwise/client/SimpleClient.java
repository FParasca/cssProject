package pt.ul.fc.css.weatherwise.client;

import pt.ul.fc.css.weatherwise.dataaccess.rdgw.exception.PersistenceException;
import pt.ul.fc.css.weatherwise.dbulits.SetupDatabase;

public class SimpleClient {

    public static void main(String[] args) {
        // --- 1. SETUP THE DATABASE  ---
        try {
            SetupDatabase.run();
        } catch (PersistenceException e) {
            System.err.println("FATAL: Could not setup the database.");
            e.printStackTrace();
            return;
        }

    }
}