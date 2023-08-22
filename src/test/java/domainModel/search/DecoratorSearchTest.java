package domainModel.search;

import businessLogic.AdController;
import businessLogic.ClientController;
import businessLogic.EstateAgencyController;
import dao.DAO;
import dao.Database;
import dao.SQLiteDAO;
import domainModel.Ad;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DecoratorSearchTest {
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
        adController.createAd("Test title", "Test description", "Test address", "Test city", 500, 400,true, 1);

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

    //Test per verificare il corretto funzionamento dei decoratori
    @Test
    void testSearchAdWithMaxPrice() {
        SearchConcrete baseSearch = new SearchConcrete("Test city");
        DecoratorSearchPrice search = new DecoratorSearchPrice(baseSearch, 1000);

        Ad[] ads = search.searchAd();

        assertEquals(2, ads.length);
        assertEquals(800, ads[0].getPrice());
        assertEquals(500, ads[1].getPrice());
    }

    @Test
    void testSearchAdForSale() {
        SearchConcrete baseSearch = new SearchConcrete("Test city");
        DecoratorSearchSell search = new DecoratorSearchSell(baseSearch, true);

        Ad[] ads = search.searchAd();

        assertEquals(1, ads.length);
        assertEquals(true, ads[0].isSell());
    }

    @Test
    void testSearchAdWithMinSqrmt() {
        SearchConcrete baseSearch = new SearchConcrete("Test city");
        DecoratorSearchSqrmt search = new DecoratorSearchSqrmt(baseSearch, 50);

        Ad[] ads = search.searchAd();

        assertEquals(1, ads.length);
        assertEquals(400, ads[0].getSqrmt());
    }
}
