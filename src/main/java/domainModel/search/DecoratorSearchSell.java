package domainModel.search;

import domainModel.Ad;

import java.util.ArrayList;
import java.util.List;

public class DecoratorSearchSell extends BaseDecoratorSearch {
    private boolean sell;

    public DecoratorSearchSell(Search search, boolean sell) {
        super(search);
        this.sell = sell;
    }

    @Override
    public Ad[] searchAd() {
        Ad[] baseQuery = search.searchAd();
        List<Ad> filteredAds = new ArrayList<>();

        for (Ad ad : baseQuery) {
            if (ad.isSell() == sell) {
                filteredAds.add(ad);
            }
        }

        return filteredAds.toArray(new Ad[0]);
    }
}