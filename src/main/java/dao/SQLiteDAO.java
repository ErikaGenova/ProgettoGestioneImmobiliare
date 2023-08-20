package dao;

import domainModel.Ad;
import domainModel.Client;
import domainModel.Advertiser;
import domainModel.Booking;
import domainModel.EstateAgency;
import domainModel.PrivateOwner;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDAO implements DAO {

    @Override
    public void insertAd(Ad ad) {
        try {
            Connection connection = Database.getConnection();

            String insertQuery = "INSERT INTO ads (title, description, address, city, price, sqrmt, sell, advertiser_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, ad.getTitle());
            preparedStatement.setString(2, ad.getDescription());
            preparedStatement.setString(3, ad.getAddress());
            preparedStatement.setString(4, ad.getCity());
            preparedStatement.setInt(5, ad.getPrice());
            preparedStatement.setInt(6, ad.getSqrmt());
            preparedStatement.setBoolean(7, ad.isSell());
            preparedStatement.setInt(8, ad.getAdvertiserId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void insertClient(Client client) {
        try {
            Connection connection = Database.getConnection();

            String insertQuery = "INSERT INTO clients (fiscal_code, name, last_name, budget) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, client.getFiscalCode());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getLastName());
            preparedStatement.setInt(4, client.getBudget());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void insertAdvertiser(Advertiser advertiser) {
        try {
            Connection connection = Database.getConnection();

            String insertQuery = "INSERT INTO advertisers (id, bank_account, agency_name, agency_fee, private_owners_name, private_owners_last_name) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, advertiser.getId());
            preparedStatement.setInt(2, advertiser.getBankAccount());

            // Check the type of advertiser and set the appropriate fields
            if (advertiser instanceof EstateAgency) {
                preparedStatement.setString(3, ((EstateAgency) advertiser).getName());
                preparedStatement.setInt(4, ((EstateAgency) advertiser).getAgencyFee());
                preparedStatement.setNull(5, java.sql.Types.VARCHAR);
                preparedStatement.setNull(6, java.sql.Types.VARCHAR);
            } else if (advertiser instanceof PrivateOwner) {
                preparedStatement.setNull(3, java.sql.Types.VARCHAR);
                preparedStatement.setNull(4, java.sql.Types.INTEGER);
                preparedStatement.setString(5, ((PrivateOwner) advertiser).getName());
                preparedStatement.setString(6, ((PrivateOwner) advertiser).getLastName());
            }

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void insertBooking(Booking booking) {
        try {
            Connection connection = Database.getConnection();

            String insertQuery = "INSERT INTO bookings (date, time, id_ad, id_client) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setObject(1, java.sql.Date.valueOf(booking.getDate())); // Convert LocalDate to java.sql.Date
            preparedStatement.setObject(2, java.sql.Time.valueOf(booking.getTime())); // Convert LocalTime to java.sql.Time
            preparedStatement.setInt(3, booking.getAdId());
            preparedStatement.setString(4, booking.getClientId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void insertFavorite(String fiscalCode, int idAd) {
        try {
            Connection connection = Database.getConnection();

            String insertQuery = "INSERT INTO favorites (id_ad, id_client) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, idAd);
            preparedStatement.setString(2, fiscalCode);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void deleteAd(Integer idAd) {
        try {
            Ad ad = getAd(idAd);
            if (ad == null) return; //TODO: perché non mi segna l'errore con il return?

            Connection connection = Database.getConnection();

            String deleteQuery = "DELETE FROM ads WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, idAd);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void deleteClient(String fiscalCode) {
        try {
            Connection connection = Database.getConnection();

            String deleteQuery = "DELETE FROM clients WHERE fiscal_code = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, fiscalCode);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void deleteAdvertiser(String idAdvertiser) {
        try {
            Connection connection = Database.getConnection();

            String deleteQuery = "DELETE FROM advertisers WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, idAdvertiser);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void deleteBooking(int idBooking) {
        try {
            Connection connection = Database.getConnection();

            String deleteQuery = "DELETE FROM bookings WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, idBooking);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void deleteFavorite(String fiscalCode, int idAd) {
        try {
            Connection connection = Database.getConnection();

            String deleteQuery = "DELETE FROM favorites WHERE id_client = ? AND id_ad = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, fiscalCode);
            preparedStatement.setInt(2, idAd);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void updateAd(Ad ad, int newPrice) {
        try {
            Connection connection = Database.getConnection();

            String updateQuery = "UPDATE ads SET price = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, newPrice);
            preparedStatement.setInt(2, ad.getId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void updateClient(Client client, int newBudget) {
        try {
            Connection connection = Database.getConnection();

            String updateQuery = "UPDATE clients SET budget = ? WHERE fiscal_code = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, newBudget);
            preparedStatement.setString(2, client.getFiscalCode());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void updateAdvertiser(Advertiser advertiser, int newBankAccount) {
        try {
            Connection connection = Database.getConnection();

            String updateQuery = "UPDATE advertisers SET bank_account = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, newBankAccount);
            preparedStatement.setInt(2, advertiser.getId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void updateBookingDate(Booking booking, LocalDate newDate) {
        try {
            Connection connection = Database.getConnection();

            String updateQuery = "UPDATE bookings SET date = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setDate(1, java.sql.Date.valueOf(newDate));
            preparedStatement.setInt(2, booking.getId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }

    @Override
    public void updateBookingTime(Booking booking, LocalTime newTime) {
        try {
            Connection connection = Database.getConnection();

            String updateQuery = "UPDATE bookings SET time = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setTime(1, java.sql.Time.valueOf(newTime));
            preparedStatement.setInt(2, booking.getId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }
    }


    @Override
    public Ad getAd(int id) {
        try {
            Connection connection = Database.getConnection();
            Ad ad = null;

            String selectQuery = "SELECT * FROM ads WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ad = new Ad(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getInt("price"),
                        resultSet.getInt("sqrmt"),
                        resultSet.getBoolean("sell"),
                        resultSet.getInt("advertiser_id")
                );
            }

            resultSet.close();
            preparedStatement.close();
            Database.closeConnection(connection);
            return ad;
        } catch (
                SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }

        return null;
    }

    @Override
    public Client getClient(String fiscalCode) {
        try {
            Connection connection = Database.getConnection();
            Client client = null;

            String selectQuery = "SELECT * FROM clients WHERE fiscal_code = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, fiscalCode);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client = new Client(
                        resultSet.getString("fiscal_code"),
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("budget")
                );
            }

            resultSet.close();
            preparedStatement.close();
            Database.closeConnection(connection);
            return client;


        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }

        return null;
    }

    @Override
    public Advertiser getAdvertiser(int id) {
        try {
            Connection connection = Database.getConnection();
            Advertiser advertiser = null;

            String selectQuery = "SELECT * FROM advertisers WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int bankAccount = resultSet.getInt("bank_account");
                String agencyName = resultSet.getString("agency_name");
                int agencyFee = resultSet.getInt("agency_fee");
                String privateOwnersName = resultSet.getString("private_owners_name");
                String privateOwnersLastName = resultSet.getString("private_owners_last_name");

                if (agencyName != null && agencyFee >= 0) {
                    advertiser = new EstateAgency(id, bankAccount, agencyName, agencyFee);
                } else if (privateOwnersName != null && privateOwnersLastName != null) {
                    advertiser = new PrivateOwner(id, bankAccount, privateOwnersName, privateOwnersLastName);
                }
            }

            preparedStatement.close();
            Database.closeConnection(connection);

            return advertiser;
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }

        return null;
    }

    @Override
    public Booking getBooking(int id) {
        try {
            Connection connection = Database.getConnection();
            Booking booking = null;

            String selectQuery = "SELECT * FROM bookings WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                booking = new Booking(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime(),
                        resultSet.getInt("id_ad"),
                        resultSet.getString("id_client")
                );
            }

            resultSet.close();
            preparedStatement.close();
            Database.closeConnection(connection);
            return booking;
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }

        return null;
    }


    @Override
    public Ad[] getAdAll() {
        List<Ad> adList = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String selectQuery = "SELECT * FROM ads";

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Ad ad = extractAdFromResultSet(resultSet);
                adList.add(ad);
            }

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }

        return adList.toArray(new Ad[0]);
    }

    @Override
    public Client[] getClientAll() {
        List<Client> clientList = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String selectQuery = "SELECT * FROM clients";

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Client client = extractClientFromResultSet(resultSet);
                clientList.add(client);
            }

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }

        return clientList.toArray(new Client[0]);
    }

    @Override
    public Advertiser[] getAdvertiserAll() {
        List<Advertiser> advertiserList = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String selectQuery = "SELECT * FROM advertisers";

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Advertiser advertiser = extractAdvertiserFromResultSet(resultSet);
                advertiserList.add(advertiser);
            }

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }

        return advertiserList.toArray(new Advertiser[0]);
    }

    @Override
    public Booking[] getBookingAll() {
        List<Booking> bookingList = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String selectQuery = "SELECT * FROM bookings";

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Booking booking = extractBookingFromResultSet(resultSet);
                bookingList.add(booking);
            }

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }

        return bookingList.toArray(new Booking[0]);
    }

    @Override
    public Ad[] getFavoriteAds(String fiscalCode) {
        List<Ad> favoriteAdList = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String selectQuery = "SELECT ads.* FROM favorites " +
                    "INNER JOIN ads ON favorites.id_ad = ads.id " +
                    "WHERE id_client = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, fiscalCode);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Ad ad = extractAdFromResultSet(resultSet);
                favoriteAdList.add(ad);
            }

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'eccezione, log, o altra logica necessaria
        }

        return favoriteAdList.toArray(new Ad[0]);
    }

    @Override
    public Ad[] getAdsByAdvertiser(String idAdvertiser) {
        List<Ad> adList = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String selectQuery = "SELECT * FROM ads WHERE advertiser_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, idAdvertiser);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Ad ad = extractAdFromResultSet(resultSet);
                adList.add(ad);
            }

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adList.toArray(new Ad[0]);
    }

    @Override
    public Booking[] getBookingsByAd(int idAd) {
        //get bookings by ad
        List<Booking> bookingList = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String selectQuery = "SELECT * FROM bookings WHERE id_ad = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, idAd);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Booking booking = extractBookingFromResultSet(resultSet);
                bookingList.add(booking);
            }

            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookingList.toArray(new Booking[0]);
    }

    @Override
    public Booking[] getBookingsByAdvertiser(String idAdvertiser) {
        //get bookings by advertiser
        List<Booking> bookingList = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            String selectQuery = "SELECT * FROM bookings INNER JOIN ads ON bookings.id_ad = ads.id WHERE advertiser_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, idAdvertiser);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking booking = extractBookingFromResultSet(resultSet);
                bookingList.add(booking);
            }
            preparedStatement.close();
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingList.toArray(new Booking[0]);
    }

    @Override
    public int getNextAdID() throws SQLException {
        Connection connection = Database.getConnection();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM ads");
        int id = rs.getInt(1) + 1;

        rs.close();
        stmt.close();
        Database.closeConnection(connection);

        return id;
    }

    @Override
    public int getNextAdvertiserID() throws SQLException {
        Connection connection = Database.getConnection();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM advertisers");
        int id = rs.getInt(1) + 1;

        rs.close();
        stmt.close();
        Database.closeConnection(connection);

        return id;
    }

    @Override
    public int getNextBookingID() throws SQLException {
        Connection connection = Database.getConnection();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM bookings");
        int id = rs.getInt(1) + 1;

        rs.close();
        stmt.close();
        Database.closeConnection(connection);

        return id;
    }


    // Metodi helper per estrarre oggetti da un ResultSet
    private Ad extractAdFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        String address = resultSet.getString("address");
        String city = resultSet.getString("city");
        int price = resultSet.getInt("price");
        int sqrmt = resultSet.getInt("sqrmt");
        boolean sell = resultSet.getBoolean("sell");
        int advertiserId = resultSet.getInt("advertiser_id");

        return new Ad(id, title, description, address, city, price, sqrmt, sell, advertiserId);
    }

    private Client extractClientFromResultSet(ResultSet resultSet) throws SQLException {
        String fiscalCode = resultSet.getString("fiscal_code");
        String name = resultSet.getString("name");
        String lastName = resultSet.getString("last_name");
        int budget = resultSet.getInt("budget");

        return new Client(fiscalCode, name, lastName, budget);
    }

    private Advertiser extractAdvertiserFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int bankAccount = resultSet.getInt("bank_account");
        String agencyName = resultSet.getString("agency_name");
        int agencyFee = resultSet.getInt("agency_fee");
        String privateOwnersName = resultSet.getString("private_owners_name");
        String privateOwnersLastName = resultSet.getString("private_owners_last_name");

        if (agencyName != null && agencyFee > 0) {
            return new EstateAgency(id, bankAccount, agencyName, agencyFee);
        } else if (privateOwnersName != null && privateOwnersLastName != null) {
            return new PrivateOwner(id, bankAccount, privateOwnersName, privateOwnersLastName);
        }

        return null;
    }

    private Booking extractBookingFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        LocalTime time = resultSet.getTime("time").toLocalTime();
        int idAd = resultSet.getInt("id_ad");
        String idClient = resultSet.getString("id_client");

        return new Booking(id, date, time, idAd, idClient);
    }


}
