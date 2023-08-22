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
        // Check if the client already exists
        Client client = dao.getClient(fiscalCode);
        if (client != null) {
            throw new IllegalArgumentException("Il cliente esiste già");
        } else {
            // Create a new Client instance
            Client newClient = new Client(fiscalCode, name, lastName, budget);
            // Insert the new client into the database
            dao.insertClient(newClient);
        }

    }

    public void removeClient(String fiscalCode) {
        // Delete the client with the given fiscal code from the database
        dao.deleteClient(fiscalCode);
        System.out.println("Cliente rimosso");
    }

    public void update(Ad ad){
        Client[] clients = dao.getFavoritesByAd(ad.getId());

        for(Client client : clients){
            System.out.println("L'annuncio " + ad.getTitle() + " è stato rimosso e tu lo avevi tra i preferiti, " + client.getName() + "!");
        }
    }

    public void updateClientBudget(String fiscalCode, int newBudget) {
        // Get the client with the given fiscal code from the database
        Client client = dao.getClient(fiscalCode);
        // Update the client's budget
        client.setBudget(newBudget);
        // Update the client in the database
        dao.updateClient(client, newBudget);
    }

    public Client getClient(String fiscalCode) {
        // Get the client with the given fiscal code from the database
        return dao.getClient(fiscalCode);
    }

}
