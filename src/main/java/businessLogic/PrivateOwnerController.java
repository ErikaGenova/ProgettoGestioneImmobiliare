package businessLogic;

import dao.DAO;
import domainModel.PrivateOwner;

public class PrivateOwnerController extends AdvertiserController<PrivateOwner> {
    private DAO dao;

    public PrivateOwnerController(DAO dao) {
        super(dao);
    }

    public void addPrivateOwner(int id, String name, String lastName) {
        PrivateOwner newOwner = new PrivateOwner(id, 0, name, lastName);
        dao.insertAdvertiser(newOwner);
    }
}