package businessLogic;

import dao.DAO;
import dao.Database;
import dao.SQLiteDAO;
import domainModel.PrivateOwner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PrivateOwnerTest {
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
    public void removePrivateOwnerTest(){
        privateOwnerController.addPrivateOwner(1,"Giuseppe", "Gialli");
        privateOwnerController.removeAdvertiser(1);
        assertNull(privateOwnerController.getPrivateOwner(1));
    }

    @Test
    public void getAllPrivateOwnersTest(){
        privateOwnerController.addPrivateOwner(1,"Giuseppe", "Gialli");
        privateOwnerController.addPrivateOwner(2,"Giuseppe", "Gialli");
        privateOwnerController.addPrivateOwner(3,"Giuseppe", "Gialli");
        assertEquals(3, privateOwnerController.getAllPrivateOwners().length);
    }

}
