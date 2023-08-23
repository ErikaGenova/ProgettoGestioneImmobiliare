package businessLogic;

import dao.DAO;
import dao.Database;
import dao.SQLiteDAO;
import domainModel.Booking;
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

public class BookingControllerTest {
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

 //Test verifica la corretta gestione nel caso sia presente una sovrapposizione delle prenotazioni
    @Test
    public void checkOverlapBookingTest() throws Exception {
        LocalDate booking1= LocalDate.parse("2023-09-12");
        LocalTime time1= LocalTime.parse("15:00");

        bookingsController.createBooking(booking1, time1, 1, "RSSMRA00A00A000A");
        Booking overlap = bookingsController.createBooking(booking1, time1, 1, "BNCMRC00A00A000A");

        assertNull(overlap);
    }

    //Test che verifichi la corretta eliminazione di una prenotazione
    @Test
    public void deleteBookingTest() throws Exception {
        LocalDate booking1= LocalDate.parse("2023-09-12");
        LocalTime time1= LocalTime.parse("15:00");

        bookingsController.createBooking(booking1, time1, 1, "RSSMRA00A00A000A");
        bookingsController.deleteBooking(1);

        Booking[] bookings = bookingsController.getAllBookings();
        assertEquals(0, bookings.length);
    }

    //Test che verifichi che la data è stato correttamente modificata
    @Test
    public void updateBookingDateTest() throws Exception {
        LocalDate booking1= LocalDate.parse("2023-09-12");
        LocalTime time1= LocalTime.parse("15:00");

        Booking testBooking = bookingsController.createBooking(booking1, time1, 1, "RSSMRA00A00A000A");
        bookingsController.modifyDate(testBooking, LocalDate.parse("2023-11-24"));

        Booking[] bookings = bookingsController.getAllBookings();
        assertEquals(LocalDate.parse("2023-11-24"), bookings[0].getDate());
    }

    @Test
    public void invalidBookingDateOrTime() throws Exception {
        LocalDate booking1= LocalDate.parse("1996-07-27");
        LocalTime time1= LocalTime.parse("15:00");

        Booking testBooking = bookingsController.createBooking(booking1, time1, 1, "RSSMRA00A00A000A");
        assertNull(testBooking);
    }

}
