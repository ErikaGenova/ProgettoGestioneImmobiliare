package domainModel;

import java.util.Objects;

public class Ad {
    private final Integer id;
    private String title;
    private String description;
    private String address;
    private String city;
    private int price;
    private int sqrmt;
    private boolean sell;
    private final String advertiserId;

    // Costruttore
    public Ad(int id, String title, String description, String address, String city, int price, int sqrmt, boolean sell, Advertiser advertiser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.city = city;
        this.price = price;
        this.sqrmt = sqrmt;
        this.sell = sell;
        this.advertiserId = advertiser.getId(); //TODO: ogni annuncio ha un inserzionista
    }

    // Metodi getter
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public int getPrice() {
        return price;
    }

    public int getSqrmt() {
        return sqrmt;
    }

    public String getAdvertiserId() {
        return advertiserId;
    }

    public boolean isSell() {
        return sell;
    }

    //Metodi setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSqrmt(int sqrmt) {
        this.sqrmt = sqrmt;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", price=" + price +
                ", sqrmt=" + sqrmt +
                ", sell=" + sell +
                ", advertiser=" + advertiserId +
                '}';
    } //TODO: aggiustiamo la stampa
}
