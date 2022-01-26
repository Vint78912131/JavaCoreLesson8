import java.io.IOException;
import java.sql.*;

public class JavaCoreLesson8 {
    public static void main(String[] args) throws IOException {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:geekbrains.db");
            Statement statement = connection.createStatement();

            Weather forcasts = new Weather(3);

            for (WeatherResponse o : forcasts.getWr()) {
                statement.executeUpdate(String.format("insert into weather " +
                    "(city, localDate, weatherText, temperature)" +
                    " values  ('%s', '%s', '%s', %d)",
                    o.getCITY(),
                    o.getDATE(),
                    o.getWEATHER_TEXT(),
                    o.getTEMPERATURE()));
            }

            ResultSet resultSet = statement.executeQuery("select * from weather");

            while (resultSet.next()) {
                System.out.print(resultSet.getInt("id"));
                System.out.print(" ");
                System.out.print(resultSet.getString("city"));
                System.out.print(" ");
                System.out.print(resultSet.getString("localDate"));
                System.out.print(" ");
                System.out.print(resultSet.getString("weatherText"));
                System.out.print(" ");
                System.out.print(resultSet.getString("temperature"));
                System.out.println();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
