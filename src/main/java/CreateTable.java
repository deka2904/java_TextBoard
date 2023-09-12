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

                String createTableSQL = "CREATE TABLE IF NOT EXISTS text_board (" +
                        "number INT AUTO_INCREMENT PRIMARY KEY," +
                        "title VARCHAR(255)," +
                        "contents TEXT," +
                        "view_count INT DEFAULT 0," +
                        "time TIMESTAMP DEFAULT NOW())";

                String createCommentTableSQL = "CREATE TABLE IF NOT EXISTS text_board_comment (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "comment TEXT," +
                        "suggestion INT DEFAULT 0," +
                        "time TIMESTAMP DEFAULT NOW())";

                statement.executeUpdate(createTableSQL);
                statement.executeUpdate(createCommentTableSQL);
                System.out.println("createTableSQL Table created successfully");
                System.out.println("createCommentTableSQL Table created successfully");

                statement.close();
                connection.close();
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
