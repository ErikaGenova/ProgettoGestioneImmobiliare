package domainModel.search;


import dao.Database;
import domainModel.Ad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchConcrete implements Search {
    private String city;

    public SearchConcrete(String city) {
        this.city = city;
    }
    @Override
    public Ad[] searchAd() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Ad> adsList = new ArrayList<>();

        try {
            connection = Database.getConnection();
            String query = "SELECT * FROM ads WHERE city = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, city);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String address = resultSet.getString("address");
                int price = resultSet.getInt("price");
                int sqrmt = resultSet.getInt("sqrmt");
                boolean sell = resultSet.getBoolean("sell");
                int advertiserId = resultSet.getInt("advertiser_id");

                Ad ad = new Ad(id, title, description, address, city, price, sqrmt, sell, advertiserId);
                adsList.add(ad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return adsList.toArray(new Ad[0]);
    }
}