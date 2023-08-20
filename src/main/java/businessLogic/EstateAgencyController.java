package businessLogic;

import dao.DAO;
import domainModel.EstateAgency;

public class EstateAgencyController extends AdvertiserController<EstateAgency> {
    private DAO dao;

    public EstateAgencyController(DAO dao) {
        super(dao);
    }

    public void addEstateAgency(int id, String name, int agencyFee) {
        EstateAgency newAgency = new EstateAgency(id, 0, name, agencyFee);
        dao.insertAdvertiser(newAgency);
    }
}