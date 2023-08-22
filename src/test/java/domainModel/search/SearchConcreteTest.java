package domainModel.search;

import businessLogic.AdController;
import businessLogic.ClientController;
import businessLogic.EstateAgencyController;
import businessLogic.PrivateOwnerController;
import dao.DAO;
import dao.Database;
import dao.SQLiteDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domainModel.Ad;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class SearchConcreteTest {

    @BeforeAll
    static void initDb() throws SQLException, IOException {
        // Set up database
        Database.setDatabase("test.db");
        Database.initDatabase();
    }

    @BeforeEach
    public void init() throws Exception {
        resetDatabase();
        // DAOs
        DAO dao = new SQLiteDAO();

        // Controllers
        AdController adController = new AdController(dao);
        ClientController clientController = new ClientController(dao, adController);
        EstateAgencyController estateAgencyController = new EstateAgencyController(dao);

        clientController.addClient("Mario", "Rossi", "RSSMRA00A00A000A", 1000);
        estateAgencyController.addEstateAgency(1, "Agenzia Sole A Catinelle", 100);
        adController.createAd("Test title", "Test description", "Test address", "Test city", 800, 40,false, 1);

    }

    private void resetDatabase() throws SQLException {
        Connection connection = Database.getConnection();

        // Delete data from all tables
        List<String> tables = Arrays.asList("ads", "clients", "advertisers");
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

    //Test che verifica il corretto funzionamento del metodo SearchAd()
    @Test
    void searchAd() throws SQLException {
        SearchConcrete searchConcrete = new SearchConcrete("Test city");
        Ad[] ads = searchConcrete.searchAd();
        assertEquals(1, ads.length);
        assertEquals("Test title", ads[0].getTitle());
        assertEquals("Test description", ads[0].getDescription());
        assertEquals("Test address", ads[0].getAddress());
        assertEquals("Test city", ads[0].getCity());
        assertEquals(800, ads[0].getPrice());
        assertEquals(40, ads[0].getSqrmt());
        assertEquals(false, ads[0].isSell());
        assertEquals(1, ads[0].getAdvertiserId());
    }

}

