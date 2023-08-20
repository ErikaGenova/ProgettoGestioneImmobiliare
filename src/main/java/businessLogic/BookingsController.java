package businessLogic;

import java.time.LocalDate;
import java.time.LocalTime;
import domainModel.Booking;
import dao.DAO;


public class BookingsController {
    private DAO dao;

    public BookingsController(DAO dao) {
        this.dao = dao;
    }

    public boolean checkAvailability(Booking booking) {
        Booking[] allBookings = dao.getBookingAll(); // Assume che dao.getBookingAll() restituisca tutte le prenotazioni dal database

        for (Booking existingBooking : allBookings) {
            if (existingBooking.getDate().equals(booking.getDate()) &&
                    existingBooking.getTime().equals(booking.getTime()) &&
                    existingBooking.getAdId() == booking.getAdId()) {
                return false; // Prenotazione già esistente per la stessa data, orario e annuncio
            }
        }
        return true; // Nessuna prenotazione esistente per la stessa data e orario
    }

    public Booking createBooking(LocalDate date, LocalTime time, int adId, String clientFiscalCode) throws Exception {
        // Implement the logic to create a new booking if it's available and save it to the database
        Booking newBooking = new Booking(dao.getNextBookingID(), date, time, adId, clientFiscalCode);
        if (checkAvailability(newBooking)) {
            dao.insertBooking(newBooking);
            return newBooking;
        } else {
            // Handle unavailable booking
            System.out.println("Prenotazione non disponibile. Selezionare un'altra data e/o orario.");
            return null;
        }
        //TODO: bisogna definire per bene alcuni dettagli, ad esempio se oggi è il 20 agosto e uno fa una prenotazione per il 10 agosto, deve dare errore
    }

    public void deleteBooking(int bookingId) {
        // Delete the booking with the given ID from the database
        dao.deleteBooking(bookingId);
        System.out.println("Prenotazione rimossa");
    }

    public Booking[] getAllBookings(int idAdvertiser) {
        // Get all bookings associated with the given advertiser ID from the database
        return dao.getBookingsByAdvertiser(idAdvertiser);
    }

    public void modifyDate(Booking booking, LocalDate date) {
        // Modify the date of the given booking
        booking.setDate(date);
        dao.updateBookingDate(booking, date);
        System.out.println("Prenotazione modificata. Nuova data: " + date.toString() );
    }

    public void modifyTime(Booking booking, LocalTime time) {
        // Modify the time of the given booking
        booking.setTime(time);
        dao.updateBookingTime(booking, time);
        System.out.println("Prenotazione modificata. Nuovo orario: " + time.toString() );
    }
}