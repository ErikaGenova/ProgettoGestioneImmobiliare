package businessLogic;

import dao.DAO;
import domainModel.Client;

public class ClientController {
    private DAO dao;

    public ClientController(DAO dao) {
        this.dao = dao;
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
}
