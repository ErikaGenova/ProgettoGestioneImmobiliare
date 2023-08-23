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
    private DAO dao;

    @BeforeAll
    static void initDb() throws SQLException, IOException {
        // Set up database
        Database.setDatabase("test.db");
        Database.initDatabase();
    }

    @BeforeEach
    public void init() throws Exception {
        Connection connection = Database.getConnection();
        dao = new SQLiteDAO();

        connection.prepareStatement("DELETE FROM ads").executeUpdate();
        connection.prepareStatement("DELETE FROM advertisers").executeUpdate();
        connection.prepareStatement("DELETE FROM clients").executeUpdate();
        connection.prepareStatement("DELETE FROM bookings").executeUpdate();

        connection.prepareStatement("INSERT INTO clients VALUES ('RSSMRA00A00A000A', 'Mario', 'Rossi', 1000)").executeUpdate();
        connection.prepareStatement("INSERT INTO advertisers VALUES (1, 0, 'Agenzia Sole A Catinelle', 100, null, null)").executeUpdate();
        connection.prepareStatement("INSERT INTO ads VALUES (1, 'Test title', 'Test description', 'Test address', 'Test city', 400, 50, 0, 1)").executeUpdate();


    }

    //Test per estrarre un cliente dal database
    @Test
    public void getClientTest() {
        Client client = dao.getClient("RSSMRA00A00A000A");
        assertEquals("RSSMRA00A00A000A", client.getFiscalCode());
        assertEquals("Mario", client.getName());
        assertEquals("Rossi", client.getLastName());
        assertEquals("RSSMRA00A00A000A", client.getFiscalCode());
        assertEquals(1000, client.getBudget());
    }

    //test per corretta aggiunta di un Ad nel database
    @Test
    public void insertAdTest() throws SQLException {
        Ad ad = new Ad(2, "Test title", "Test description 2", "Test address", "Test city", 400, 50, false, 1);
        dao.insertAd(ad);

        assertEquals(2, dao.getAd(2).getId());
        assertEquals("Test title", dao.getAd(2).getTitle());
        assertEquals("Test city", dao.getAd(2).getCity());
        assertEquals(400, dao.getAd(2).getPrice());
        assertEquals(1, dao.getAd(2).getAdvertiserId());
    }

    //Test per verificare che eliminando un Advertiser vengano eliminati anche gli Ad ad esso associati dal database
    @Test
    public void deleteAdvertiserAndAdTest() {
        dao.deleteAdvertiser(1);
        Ad ad = dao.getAd(1);
        assertNull(ad);
    }

    //Test per verificare che un booking venga inserito correttamente nella tabella del database
    @Test
    public void insertBookingTest() throws SQLException {
        Booking bookingTest = new Booking(1, LocalDate.of(2021, 1, 1), LocalTime.of(10, 0), 2, "RSSMRA00A00A000A");
        dao.insertBooking(bookingTest);

        Booking booking = dao.getBooking(1);
        assertEquals(1, booking.getId());
        assertEquals(LocalDate.of(2021, 1, 1), booking.getDate());
        assertEquals(LocalTime.of(10, 0), booking.getTime());
        assertEquals(2, booking.getAdId());
        assertEquals("RSSMRA00A00A000A", booking.getClientId());

    }

    //Test per verificare che il budget di un client sia correttamente aggiornato nel database
    @Test
    public void updateClientTest() throws SQLException {
       Client client = dao.getClient("RSSMRA00A00A000A");
       dao.updateClient(client,  550);
       assertEquals(550, dao.getClient("RSSMRA00A00A000A").getBudget());
    }

    @Test
    public void getFavoritesByAdTest(){
        dao.insertClient(new Client("LNGGNN00A00A000A", "Gennaro", "Longo", 1000));
        dao.insertFavorite("LNGGNN00A00A000A", 1);
        dao.insertFavorite("RSSMRA00A00A000A", 1);
        assertEquals(2, dao.getFavoritesByAd(1).length);
    }

}
