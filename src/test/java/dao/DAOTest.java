package dao;

import businessLogic.*;
import domainModel.Ad;
import domainModel.Booking;
import domainModel.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DAOTest {
    DAO dao = new SQLiteDAO();
    AdController adController;
    ClientController clientController;
    EstateAgencyController estateAgencyController;
    PrivateOwnerController privateOwnerController;
    BookingsController bookingsController;

    @BeforeAll
    static void initDb() throws SQLException, IOException {
        // Set up database
        Database.setDatabase("test.db");
        Database.initDatabase();
    }

    @BeforeEach
    public void init() throws Exception {
        resetDatabase();

        // Controllers
        adController = new AdController(dao);
        clientController = new ClientController(dao, adController);
        estateAgencyController = new EstateAgencyController(dao);
        privateOwnerController = new PrivateOwnerController(dao);
        bookingsController = new BookingsController(dao);

        clientController.addClient("Mario", "Rossi", "RSSMRA00A00A000A", 1000);
        clientController.addClient("Marco", "Bianchi", "BNCMRC00A00A000A", 5000);
        estateAgencyController.addEstateAgency(1, "Agenzia Sole A Catinelle", 100);
        privateOwnerController.addPrivateOwner(2,"Giuseppe", "Gialli");
        adController.createAd("Test title", "Test description", "Test address", "Test city", 800, 40,false, 1);
        adController.createAd("Test title", "Test description", "Test address", "Test city", 500, 400,true, 1);
        adController.createAd("Test title", "Test description", "Test address", "Test city", 200, 50,true, 2);
        adController.createAd("Test title", "Test description", "Test address", "Test city", 100, 30,false, 2);

    }

    private void resetDatabase() throws SQLException {
        Connection connection = Database.getConnection();

        // Delete data from all tables
        List<String> tables = Arrays.asList("ads", "clients", "advertisers", "favorites", "bookings");
        for (String table : tables) {
            try {
                connection.prepareStatement("DELETE FROM " + table).executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        connection.prepareStatement("DELETE FROM ads").executeUpdate();
        Database.closeConnection(connection);
    }


    @Test
    public void updateBudgetClientTest(){
        Client client = clientController.getClient("RSSMRA00A00A000A");

        dao.updateClient(client, 2000);

        assertEquals(2000, client.getBudget());
    }

}
