package dao;


import domainModel.Ad;
import domainModel.Client;
import domainModel.Advertiser;
import domainModel.Booking;

import java.time.LocalDate;
import java.time.LocalTime;

public interface DAO {
    String database = "real_estate";

    void insertAd(Ad ad);

    void insertClient(Client client);

    void insertAdvertiser(Advertiser advertiser);

    void insertBooking(Booking booking);

    void insertFavorite(String fiscalCode, int idAd);

    void deleteAd(Integer idAd);

    void deleteClient(String fiscalCode);

    void deleteAdvertiser(int idAdvertiser);

    void deleteBooking(int idBooking);

    void deleteFavorite(String fiscalCode, int idAd);

    void updateAd(Ad ad, int newPrice);

    void updateClient(Client client, int newBudget);

    //nella funzione updateAdvertiser passiamo come parametro il nuovo conto bancario, inteso come valore a cui è già stata aggiunta la somma dell'immobile venduto o affittato
    void updateAdvertiser(Advertiser advertiser, int newBankAccount);

    void updateBookingDate(Booking booking, LocalDate newDate);

    void updateBookingTime(Booking booking, LocalTime newTime);

    Ad getAd(int id);

    Client getClient(String fiscalCode);

    Advertiser getAdvertiser(int id);

    Booking getBooking(int id);

    Ad[] getAdAll();

    Client[] getClientAll();

    Advertiser[] getAdvertiserAll();

    Booking[] getBookingAll();

    Ad[] getFavoriteAds(String fiscalCode);

    Client[] getFavoritesByAd(int idAd);

    Ad[] getAdsByAdvertiser(String idAdvertiser);

    Booking[] getBookingsByAd(int idAd);

    Booking[] getBookingsByAdvertiser(int idAdvertiser);

    public int getNextAdID() throws Exception;

    public int getNextAdvertiserID() throws Exception;

    public int getNextBookingID() throws Exception;
}


