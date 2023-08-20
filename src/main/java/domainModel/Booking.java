package domainModel;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private final int id;
    private LocalDate date;
    private LocalTime time;
    private final int idAd;
    private final String idClient;

    public Booking(int id, LocalDate date, LocalTime time, int adId, String clientFiscalCode) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.idAd = adId;
        this.idClient = clientFiscalCode;
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

    public int getAdId() {
        return idAd;
    }

    public String getClientId() {
        return idClient;
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
