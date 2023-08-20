package domainModel.search;

import domainModel.Ad;

public abstract class BaseDecoratorSearch implements Search {
    protected Search search;

    public BaseDecoratorSearch(Search search) {
        this.search = search;
    }

    @Override
    public abstract Ad[] searchAd();
}