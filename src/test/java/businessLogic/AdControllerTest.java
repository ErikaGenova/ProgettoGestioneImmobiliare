package businessLogic;

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

public class AdControllerTest {
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
        clientController.addClient("Franco", "Neri", "NRIFNC00A00A000A", 100);
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

    //Test per verificare che un annuncio sia effettivamente aggiunto ai preferiti del cliente
    @Test
    public void addFavouriteTest() throws SQLException {
        adController.addToFavourites("RSSMRA00A00A000A", 1);
        adController.addToFavourites("RSSMRA00A00A000A", 2);

        Ad[] ads = adController.getFavouriteAds("RSSMRA00A00A000A");
        assertEquals(2, ads.length);
        assertEquals(800, ads[0].getPrice());
        assertEquals(500, ads[1].getPrice());
    }

    //Test per verificare che un annuncio sia correttamente eliminato dai preferiti di un cliente
    @Test
    public void removeFavouriteTest() throws SQLException {
        adController.addToFavourites("RSSMRA00A00A000A", 1);
        adController.addToFavourites("RSSMRA00A00A000A", 2);
        adController.removeFromFavourites("RSSMRA00A00A000A", 2);

        Ad[] ads = adController.getFavouriteAds("RSSMRA00A00A000A");
        assertEquals(1, ads.length);
        assertEquals(800, ads[0].getPrice());
    }

    //Test per verificare che mi restituisca correttamente la lista di annunci associati a uno specifico inserzionista
    @Test
    public void getAdsByAdvertiserTest() throws SQLException {
        Ad[] ads = adController.getAdsForAdvertiser(2);
        assertEquals(2, ads.length);
        assertEquals(200, ads[0].getPrice());
        assertEquals(100, ads[1].getPrice());
    }

    @Test
    public void failedPaymentTest() throws SQLException {
        adController.payAd(3, "NRIFNC00A00A000A");
        assertEquals(clientController.getClient("NRIFNC00A00A000A").getBudget(), 100); //controlla se il budget del cliente è rimasto invariato

    }

    //Test corretta notifica di eliminazione di un annuncio dai preferiti
    @Test
    public void observerNotificationTest(){
        adController.addToFavourites("RSSMRA00A00A000A", 1);
        adController.addToFavourites("RSSMRA00A00A000A", 2);
        adController.deleteAd(2);

        assertEquals(1, adController.getFavouriteAds("RSSMRA00A00A000A").length);
    }

}
