package businessLogic;

import dao.DAO;
import dao.Database;
import dao.SQLiteDAO;
import domainModel.Client;
import domainModel.EstateAgency;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EstateAgencyTest {
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
    }

    private void resetDatabase() throws SQLException {
        Connection connection = Database.getConnection();

        // Delete data from all tables
        List<String> tables = Arrays.asList("advertisers");
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
    public void insertEstateAgencyTest() throws Exception {
        estateAgencyController.addEstateAgency(1, "Agenzia Test 1", 100);
        assertEquals(1, estateAgencyController.getEstateAgency(1).getId());
    }

    @Test
    public void getEstateAgenciesTest(){
        estateAgencyController.addEstateAgency(1, "Agenzia test 1", 100);
        estateAgencyController.addEstateAgency(2, "Agenzia test 2", 1000);
        estateAgencyController.addEstateAgency(3, "Agenzia test 3", 500);
        EstateAgency agencies = estateAgencyController.getEstateAgency(3);
        assertEquals(3, agencies.getId());
    }

    @Test
    public void getAllAgenciesTest(){
        estateAgencyController.addEstateAgency(1, "Agenzia test 1", 100);
        estateAgencyController.addEstateAgency(2, "Agenzia test 2", 1000);
        estateAgencyController.addEstateAgency(3, "Agenzia test 3", 500);
        assertEquals(3, estateAgencyController.getAllEstateAgencies().length);
    }

}
