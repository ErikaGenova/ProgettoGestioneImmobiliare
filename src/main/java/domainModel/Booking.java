package domainModel;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private final int id;
    private LocalDate date;
    private LocalTime time;
    private final int idAd;
    private final String idClient;

    public Booking(int id, LocalDate date, LocalTime time, Ad ad, Client client) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.idAd = ad.getId();
        this.idClient = client.getFiscalCode();
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", idAd=" + idAd +
                ", idClient='" + idClient + '\'' +
                '}';
    }
}
