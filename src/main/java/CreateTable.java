import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null){
            Statement statement = null;
            try{
                statement = connection.createStatement();

                String createTableSQL = "CREATE TABLE IF NOT EXISTS text_board_ex (" +
                        "number INT AUTO_INCREMENT PRIMARY KEY," +
                        "title VARCHAR(255)," +
                        "detail TEXT)";

                statement.executeUpdate(createTableSQL);
                System.out.println("Table created successfully");

                statement.close();
                connection.close();
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
