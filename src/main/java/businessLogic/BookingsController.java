package businessLogic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import domainModel.Booking;
import dao.DAO;


public class BookingsController {
    private DAO dao;

    public BookingsController(DAO dao) {
        this.dao = dao;
    }

    public boolean checkAvailability(Booking booking) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateTime = LocalDateTime.of(currentDate, currentTime);

        // Calcola la data e l'ora massime entro cui una sovrapposizione è consentita (15 minuti dopo la prenotazione)
        LocalDateTime maxAllowedDateTime = LocalDateTime.of(booking.getDate(), booking.getTime()).plusMinutes(15);

        if (maxAllowedDateTime.isBefore(currentDateTime)) {
            System.out.println("La data o l'ora dell'appuntamento sono precedenti a quelli attuali");
            return false;
        }

        Booking[] allBookings = dao.getBookingAll(); // Assume che dao.getBookingAll() restituisca tutte le prenotazioni dal database

        for (Booking existingBooking : allBookings) {
            LocalDateTime existingDateTime = LocalDateTime.of(existingBooking.getDate(), existingBooking.getTime());

            // Verifica se esiste una sovrapposizione entro i 15 minuti successivi
            if (existingDateTime.isAfter(currentDateTime) && existingDateTime.isBefore(maxAllowedDateTime) &&
                    existingBooking.getAdId() == booking.getAdId()) {
                return false; // Sovrapposizione entro i 15 minuti successivi
            }
        }

        return true; // Nessuna sovrapposizione entro i 15 minuti successivi
    }


    public Booking createBooking(LocalDate date, LocalTime time, int adId, String clientFiscalCode) throws Exception {
        Booking newBooking = new Booking(dao.getNextBookingID(), date, time, adId, clientFiscalCode);
        if (checkAvailability(newBooking)) {
            dao.insertBooking(newBooking);
            return newBooking;
        } else {
            System.out.println("Prenotazione non disponibile. Selezionare un'altra data e/o orario.");
            return null;
        }
    }

    public void deleteBooking(int bookingId) {
        dao.deleteBooking(bookingId);
        System.out.println("Prenotazione rimossa");
    }

    public Booking[] getAllBookingsForAdvertiser(int idAdvertiser) {
        return dao.getBookingsByAdvertiser(idAdvertiser);
    }

    public Booking[] getAllBookings() {
        return dao.getBookingAll();
    }

    public void modifyDate(Booking booking, LocalDate date) {
        booking.setDate(date);
        dao.updateBookingDate(booking, date);
        System.out.println("Prenotazione modificata. Nuova data: " + date.toString());
    }

    public void modifyTime(Booking booking, LocalTime time) {
        booking.setTime(time);
        dao.updateBookingTime(booking, time);
        System.out.println("Prenotazione modificata. Nuovo orario: " + time.toString());
    }
}