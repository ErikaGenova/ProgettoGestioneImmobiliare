package businessLogic;

import businessLogic.Observer;
import domainModel.Ad;

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Ad Ad);
}
