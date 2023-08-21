import businessLogic.*;
import dao.DAO;
import dao.Database;
import dao.SQLiteDAO;
import domainModel.Ad;
import domainModel.Advertiser;
import domainModel.Client;
import domainModel.EstateAgency;
import domainModel.search.DecoratorSearchPrice;
import domainModel.search.DecoratorSearchSell;
import domainModel.search.DecoratorSearchSqrmt;
import domainModel.search.SearchConcrete;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("Working directory = " + System.getProperty("user.dir"));
        Database.setDatabase("main.db");
        Database.initDatabase();

        // DAOs
        DAO dao = new SQLiteDAO();

        // Controllers
        AdController adController = new AdController(dao);
        ClientController clientController = new ClientController(dao, adController);
        EstateAgencyController estateAgencyController = new EstateAgencyController(dao);
        PrivateOwnerController privateOwnerController = new PrivateOwnerController(dao);
        BookingsController bookingsController = new BookingsController(dao);

        // Add sample clients
        clientController.addClient("Mario", "Rossi", "RSSMRA00A00A000A", 1000);
        clientController.addClient("Luigi", "Verdi", "VRDLGI00A00A000A", 5000);
        clientController.addClient("Giovanni", "Bianchi", "GVNNBN00A00A000A", 200);

        // Add sample estate agencies and private owners
        estateAgencyController.addEstateAgency(1, "Agenzia Sole A Catinelle", 100);
        privateOwnerController.addPrivateOwner(2, "Federico", "Rosa");
        privateOwnerController.addPrivateOwner(3, "Giacomo", "Poretti");
        privateOwnerController.addPrivateOwner(4, "Giovanni", "Storti");
        privateOwnerController.addPrivateOwner(5, "Aldo", "Baglio");
        estateAgencyController.addEstateAgency(6, "Agenzia ComproCasa", 500);

        // Add sample ads
        adController.createAd("Appartamento in centro", "Bilocale secondo piano", "Via Roma 1", "Firenze", 800, 40,false, 1);
        adController.createAd("Villa con piscina", "Villa con piscina e giardino", "Via del corso 2", "Roma", 200000, 200, true, 2);
        adController.createAd("Appartamento in periferia", "Bilocale piano terra", "Via aretina 3", "Arezzo", 600, 50, false, 3);
        adController.createAd("Attico in centro", "Monolocale piano terra", "Via monteleone 43", "Milano", 40000, 20, false, 1);
        adController.createAd("Casolare", "Casolare abbandonato in terribili condizioni", "C.da Pispisia", "Marsala", 50000, 200, true, 4);
        adController.createAd("Alloggio universitario", "Piccolo appartamento condiviso con altre persone. Singolo bagno e cucina in comune", "Via Pasquali 25", "Pisa", 300, 80, false, 6);
        adController.createAd("Appartamento accogliente","Appartamento con vista panoramica", "Via del Sole 123", "Roma", 150000,60,true, 5);
        adController.createAd("Villa elegante", "Splendida villa con giardino privato", "Via dei Fiori 456", "Torino", 2000, 200, false,3);
        adController.createAd("Stanza singola in centro", "Camera singola completamente arredata nel cuore della città", "Corso Italia 10", "Milano", 400, 15, true, 3);
        adController.createAd("Bilocale moderno", "Appartamento con arredi moderni e balcone panoramico", "Via Monti 7", "Torino", 80000, 50, true, 4);
        adController.createAd("Casa indipendente con giardino", "Ampia casa con spazio esterno, ideale per famiglie", "Via Verde 30", "Bologna", 300000, 120, false, 1);
        adController.createAd("Monolocale arredato", "Monolocale completamente arredato con servizi inclusi", "Piazza Rossetti 15", "Firenze", 600, 25, true, 2);
        adController.createAd("Penthouse lussuoso", "Penthouse con finiture di lusso e terrazza panoramica", "Via dei Sogni 20", "Milano", 3500, 150, true, 6);
        adController.createAd("Casa familiare spaziosa", "Ampia casa adatta alle famiglie, con giardino e garage", "Via delle Rose 78", "Roma", 2800, 180, true, 5);
        adController.createAd("Appartamento minimalista", "Appartamento dal design minimalista con dettagli eleganti", "Via della Tranquillità 5", "Firenze", 1400, 70, true, 2);
        adController.createAd("Chalet di montagna", "Accogliente chalet di montagna con camino e vista panoramica", "Via dei Pini 15", "Pisa", 2000, 120, true, 4);
        adController.createAd("Loft industriale", "Loft con soffitti alti e elementi industriali, perfetto per artisti", "Via dell'Industria 30", "Bologna", 1600, 90, true, 3);
        adController.createAd("Villetta a schiera moderna", "Villetta a schiera con arredamento moderno e spazio esterno", "Via delle Palme 7", "Verona", 2200, 110, true, 4);
        adController.createAd("Appartamento vista mare", "Appartamento con vista mozzafiato sul mare e balcone privato", "Lungomare 1", "Palermo", 1800, 85, true, 3);
        adController.createAd("Casa colonica ristrutturata", "Antica casa colonica completamente ristrutturata immersa nel verde", "Via dei Campi 25", "Pisa", 3200, 200, true, 6);
        adController.createAd("Appartamento storico", "Elegante appartamento in un palazzo storico con affreschi", "Corso Storico 10", "Roma", 280000, 100, false, 5);
        adController.createAd("Suite di lusso", "Suite di lusso in un hotel a cinque stelle, servizi esclusivi inclusi", "Piazza del lusso 1", "Bologna", 5000, 120, false, 2);

        //adController.printAllAds();

        Ad[] adsSearched = adController.searchAd(new DecoratorSearchPrice(
                new DecoratorSearchSqrmt(
                        new DecoratorSearchSell(
                                new SearchConcrete("Milano"), false
                        ),10
                ),100000));
        for(Ad ad : adsSearched){
            System.out.println(ad);
        }


        Advertiser[] allAdvertisers = dao.getAdvertiserAll();
        for (Advertiser advertiser : allAdvertisers) {
            advertiser.displayInformation();
            System.out.println("\n");
        }

        estateAgencyController.removeAdvertiser(1);
        privateOwnerController.removeAdvertiser(2);
        privateOwnerController.removeAdvertiser(5);

        allAdvertisers = dao.getAdvertiserAll();
        for (Advertiser advertiser : allAdvertisers) {
            advertiser.displayInformation();
            System.out.println("\n");
        }

        adController.deleteAd(5);
        adController.printAllAds();

        adController.addToFavourites("RSSMRA00A00A000A", 1);
        adController.addToFavourites("RSSMRA00A00A000A", 7);
        adController.addToFavourites("RSSMRA00A00A000A", 10);
        adController.addToFavourites("VRDLGI00A00A000A", 15);
        adController.addToFavourites("VRDLGI00A00A000A", 20);
        adController.addToFavourites("GVNNBN00A00A000A", 2);
        adController.addToFavourites("GVNNBN00A00A000A", 4);
        adController.addToFavourites("GVNNBN00A00A000A", 6);

        adController.getFavouriteAds("RSSMRA00A00A000A");

        adController.deleteAd(10);
//
//        adController.payAd(1, "RSSMRA00A00A000A");
//        clientController.toString();
//






    }
}
