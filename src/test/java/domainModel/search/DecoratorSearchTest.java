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
    AdController adController;
    ClientController clientController;
    EstateAgencyController estateAgencyController;

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
        adController = new AdController(dao);
        clientController = new ClientController(dao, adController);
        estateAgencyController = new EstateAgencyController(dao);

        clientController.addClient("Mario", "Rossi", "RSSMRA00A00A000A", 1000);
        estateAgencyController.addEstateAgency(1, "Agenzia Sole A Catinelle", 100);
        adController.createAd("Test title", "Test description", "Test address", "Test city", 800, 40, false, 1);
        adController.createAd("Test title", "Test description", "Test address", "Test city", 500, 400, true, 1);
        adController.createAd("Test title", "Test description", "Test address", "Test city", 200, 50, true, 2);
        adController.createAd("Test title", "Test description", "Test address", "Test city", 100, 30, false, 2);

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
        DecoratorSearchPrice search = new DecoratorSearchPrice(baseSearch, 500);

        Ad[] ads = search.searchAd();

        assertEquals(3, ads.length);
        assertEquals(500, ads[0].getPrice());
        assertEquals(200, ads[1].getPrice());
        assertEquals(100, ads[2].getPrice());
    }

    @Test
    void testSearchAdForSale() {
        SearchConcrete baseSearch = new SearchConcrete("Test city");
        DecoratorSearchSell search = new DecoratorSearchSell(baseSearch, true);

        Ad[] ads = search.searchAd();

        assertEquals(2, ads.length);
        assertEquals(true, ads[0].isSell());
        assertEquals(true, ads[1].isSell());
    }

    @Test
    void testSearchAdWithMinSqrmt() {
        SearchConcrete baseSearch = new SearchConcrete("Test city");
        DecoratorSearchSqrmt search = new DecoratorSearchSqrmt(baseSearch, 50);

        Ad[] ads = search.searchAd();

        assertEquals(2, ads.length);
        assertEquals(400, ads[0].getSqrmt());
        assertEquals(50, ads[1].getSqrmt());
    }

    @Test
    void testSearchAllFilters() {
        Ad[] adsSearched = adController.searchAd(new DecoratorSearchPrice(
                new DecoratorSearchSqrmt(
                        new DecoratorSearchSell(
                                new SearchConcrete("Test city"), false
                        ), 10
                ), 500));
        assertEquals(1, adsSearched.length);
        assertEquals("Test title", adsSearched[0].getTitle());
        assertEquals(100, adsSearched[0].getPrice());
        assertEquals(30, adsSearched[0].getSqrmt());
        assertEquals(false, adsSearched[0].isSell());
        assertEquals(2, adsSearched[0].getAdvertiserId());

    }
}
