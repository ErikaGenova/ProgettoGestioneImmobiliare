package businessLogic;

import dao.DAO;
import domainModel.Advertiser;
import domainModel.PrivateOwner;

import java.util.ArrayList;
import java.util.List;

public class PrivateOwnerController extends AdvertiserController<PrivateOwner> {
    private DAO dao;

    public PrivateOwnerController(DAO dao) {
        super(dao);
        this.dao = dao;
    }

    public void addPrivateOwner(int id, String name, String lastName) {
        PrivateOwner newOwner = new PrivateOwner(id, 0, name, lastName);
        dao.insertAdvertiser(newOwner);
    }

    public PrivateOwner getPrivateOwner(int id) {
        return (PrivateOwner) dao.getAdvertiser(id);
    }

    public PrivateOwner[] getAllPrivateOwners() {
        Advertiser[] advertisers = dao.getAdvertiserAll();
        List<PrivateOwner> privateOwners = new ArrayList<>();

        for (Advertiser advertiser : advertisers) {
            if (advertiser instanceof PrivateOwner) {
                privateOwners.add((PrivateOwner) advertiser);
            }
        }

        return privateOwners.toArray(new PrivateOwner[0]);
    }
}