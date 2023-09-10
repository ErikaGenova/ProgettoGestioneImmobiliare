package businessLogic;

import dao.DAO;
import domainModel.Ad;
import domainModel.Client;

import java.util.List;

public class ClientController implements Observer{
    private DAO dao;
    AdController adController;

    public ClientController(DAO dao, AdController adController) {
        this.dao = dao;
        this.adController = adController;
        adController.addObserver(this);
    }

    public void addClient(String name, String lastName, String fiscalCode, int budget) {
        // controllo se il cliente esiste già
        Client client = dao.getClient(fiscalCode);
        if (client != null) {
            throw new IllegalArgumentException("Il cliente esiste già");
        } else {
            Client newClient = new Client(fiscalCode, name, lastName, budget);
            dao.insertClient(newClient);
        }

    }

    public void removeClient(String fiscalCode) {
        dao.deleteClient(fiscalCode);
        System.out.println("Cliente rimosso");
    }

    //Metodo update Observer
    public void update(Ad ad){
        Client[] clients = dao.getFavoritesByAd(ad.getId());

        for(Client client : clients){
            System.out.println("L'annuncio " + ad.getTitle() + " è stato rimosso e tu lo avevi tra i preferiti, " + client.getName() + "!");
        }
    }

    public void updateClientBudget(String fiscalCode, int newBudget) {
        // prendi il cliente dal database con il codice fiscale dato
        Client client = dao.getClient(fiscalCode);
        client.setBudget(newBudget);
        dao.updateClient(client, newBudget);
    }

    public Client getClient(String fiscalCode) {
        return dao.getClient(fiscalCode);
    }

}
