package domainModel.search;

import domainModel.Ad;

import java.util.ArrayList;
import java.util.List;

public class DecoratorSearchPrice extends BaseDecoratorSearch {
    private int maxPrice;

    public DecoratorSearchPrice(Search search, int maxPrice) {
        super(search);
        this.maxPrice = maxPrice;
    }

    public Ad[] searchAd() {
        Ad[] baseQuery = search.searchAd();
        List<Ad> filteredAds = new ArrayList<>();

        for (Ad ad : baseQuery) {
            if (ad.getPrice() <= maxPrice) {
                filteredAds.add(ad);
            }
        }

        return filteredAds.toArray(new Ad[0]);
    }
}