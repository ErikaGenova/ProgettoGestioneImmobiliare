package businessLogic;

import dao.DAO;
import domainModel.*;
import domainModel.search.*;

import java.util.ArrayList;
import java.util.List;


public class AdController implements Subject {
    private DAO dao;

    private List<Observer> observers = new ArrayList<>();//lista observers


    public AdController(DAO dao) {
        this.dao = dao;
    }

    public boolean checkAd(Ad ad) {
        //check if ad exists in database (by title, address, city, price, sqrmt, advertiserId)
        //if exists return true else return false
        Ad[] allAds = dao.getAdAll(); // Assume che dao.getAdAll() restituisca tutti gli annunci dal database

        for (Ad existingAd : allAds) {
            if (existingAd.getTitle().equals(ad.getTitle()) &&
                    existingAd.getAddress().equals(ad.getAddress()) &&
                    existingAd.getCity().equals(ad.getCity()) &&
                    existingAd.getPrice() == ad.getPrice() &&
                    existingAd.getSqrmt() == ad.getSqrmt() &&
                    existingAd.getAdvertiserId() == ad.getAdvertiserId()) {
                return true; // Se esiste un annuncio con gli stessi attributi, restituisci false
            }
        }

        return false;
    }

    public Ad[] searchAd(Search s) {
        return s.searchAd();
    }


    public Ad createAd(String title, String description, String address, String city, int price, int sqrmt, boolean sell, int advertiserId) throws Exception {
        // Implement the logic to create a new ad and save it to the database
        Ad newAd = new Ad(dao.getNextAdID(), title, description, address, city, price, sqrmt, sell, advertiserId);
        if (!checkAd(newAd)) {
            dao.insertAd(newAd);
            return newAd;
        } else {
            // stampa messaggio di errore
            System.out.println("Nel database è già presente un annuncio con gli stessi attributi, non è possibile inserire questo annuncio");
            return null;
        }
    }

    public void deleteAd(int id) {
        // Implement the logic to delete an ad from the database
        Ad adToDelete = dao.getAd(id);
        notifyObservers(adToDelete);
        dao.deleteAd(id);

    }

    public void modifyPrice(Ad ad, int price) {
        // Implement the logic to modify the price of an ad
        ad.setPrice(price);
        dao.updateAd(ad, price);
        System.out.println("Prezzo modificato in" + ad.getPrice());
    }

    public void payAd(int idAd, String fiscalCode) {
        // Get the ad and the client from the DAO
        Ad ad = dao.getAd(idAd);
        Client client = dao.getClient(fiscalCode);

        if (ad != null && client != null) {
            // Calculate the new budget for the client after deducting the ad price
            int newClientBudget = client.getBudget() - ad.getPrice();
            // Update the client's budget in the database
            if (newClientBudget < 0) {
                System.out.println("Non hai abbastanza soldi per pagare questo annuncio");
            } else {
                dao.updateClient(client, newClientBudget);

                // Get the advertiser associated with the ad
                Advertiser advertiser = dao.getAdvertiser(ad.getAdvertiserId());

                if (advertiser != null) {
                    // Calculate the new bank account balance for the advertiser after adding the ad price
                    int newAdvertiserBankAccount = advertiser.getBankAccount() + ad.getPrice();
                    // Update the advertiser's bank account in the database
                    dao.updateAdvertiser(advertiser, newAdvertiserBankAccount);
                }

                // Delete the ad from the database
                dao.deleteAd(idAd);
            }
        }
    }

    public Ad[] getAdsForAdvertiser(String advertiserId) {
        // Implement the logic to get all ads for a specific advertiser
        return dao.getAdsByAdvertiser(advertiserId);
    }

    public Ad[] getFavouriteAds(String idClient) {
        // Implement the logic to get favorite ads for a client
        return dao.getFavoriteAds(idClient);
    }

    public void addToFavourites(String idClient, int idAd) {
        // Implement the logic to add an ad to the client's favorites
        dao.insertFavorite(idClient, idAd);
        System.out.println("Annuncio aggiunto ai preferiti");
    }

    public void removeFromFavourites(String idClient, int idAd) {
        // Implement the logic to remove an ad from the client's favorites
        dao.deleteFavorite(idClient, idAd);
        System.out.println("Annuncio rimosso dai preferiti");
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
        //TODO:Come si gestisce la rimozione del clientController dalla lista degli osservatori
    }

    public void notifyObservers(Ad ad) {
        for (Observer observer : observers) {
            observer.update(ad);
        }
    }
}