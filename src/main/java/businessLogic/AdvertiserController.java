package businessLogic;

import dao.DAO;
import domainModel.Advertiser;

public abstract class AdvertiserController<T extends Advertiser> {
    private DAO dao;

    public AdvertiserController(DAO dao) {
        this.dao = dao;
    }

    public void removeAdvertiser(int id) {
        dao.deleteAdvertiser(id);
    }
}