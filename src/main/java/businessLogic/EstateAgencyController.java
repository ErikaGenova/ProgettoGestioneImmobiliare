package businessLogic;

import dao.DAO;
import domainModel.Advertiser;
import domainModel.EstateAgency;
import domainModel.PrivateOwner;

import java.util.ArrayList;
import java.util.List;

public class EstateAgencyController extends AdvertiserController<EstateAgency> {
    private DAO dao;

    public EstateAgencyController(DAO dao) {
        super(dao);
        this.dao = dao;
    }

    public void addEstateAgency(int id, String name, int agencyFee) {
        EstateAgency newAgency = new EstateAgency(id, 0, name, agencyFee);
        dao.insertAdvertiser(newAgency);
    }

    public EstateAgency getEstateAgency(int id) {
        return (EstateAgency) dao.getAdvertiser(id);
    }

    public EstateAgency[] getAllEstateAgencies() {
        Advertiser[] advertisers = dao.getAdvertiserAll();
        List<EstateAgency> estateagencies = new ArrayList<>();

        for (Advertiser advertiser : advertisers) {
            if (advertiser instanceof EstateAgency) {
                estateagencies.add((EstateAgency) advertiser);
            }
        }

        return estateagencies.toArray(new EstateAgency[0]);
    }
}