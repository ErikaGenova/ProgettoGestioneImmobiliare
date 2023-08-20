package domainModel.search;

import domainModel.Ad;

import java.util.ArrayList;
import java.util.List;

public class DecoratorSearchSqrmt extends BaseDecoratorSearch {
    private int minSqrmt;

    public DecoratorSearchSqrmt(Search search, int minSqrmt) {
        super(search);
        this.minSqrmt = minSqrmt;
    }

    @Override
    public Ad[] searchAd() {
        Ad[] baseQuery = search.searchAd();
        List<Ad> filteredAds = new ArrayList<>();

        for (Ad ad : baseQuery) {
            if (ad.getSqrmt() >= minSqrmt) {
                filteredAds.add(ad);
            }
        }

        return filteredAds.toArray(new Ad[0]);
    }
}