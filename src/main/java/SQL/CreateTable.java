package SQL;

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

                // 게시판 테이블
                String createTableSQL = "CREATE TABLE IF NOT EXISTS text_board (" +
                        "number INT AUTO_INCREMENT PRIMARY KEY," +
                        "title VARCHAR(255)," +
                        "contents TEXT," +
                        "view_count INT DEFAULT 0," +
                        "time TIMESTAMP DEFAULT NOW())";

                // 댓글 테이블
                String createCommentTableSQL = "CREATE TABLE IF NOT EXISTS text_board_comment (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "comment TEXT," +
                        "comment_suggestion INT DEFAULT 0," +
                        "comment_time TIMESTAMP DEFAULT NOW()," +
                        "board_number INT," + // 외래 키 추가
                        "FOREIGN KEY (board_number) REFERENCES text_board(number))"; // 외래 키 제약 조건 추가

                // 회원 테이블
                String createMemberTableSQL = "CREATE TABLE IF NOT EXISTS member("+
                        "id VARCHAR(255) PRIMARY KEY," +
                        "password VARCHAR(255)," +
                        "nickname VARCHAR(255))";

                statement.executeUpdate(createTableSQL);
                statement.executeUpdate(createCommentTableSQL);
                statement.executeUpdate(createMemberTableSQL);
                System.out.println("createTableSQL Table created successfully");
                System.out.println("createCommentTableSQL Table created successfully");
                System.out.println("createMemberTableSQL Table created successfully");

                statement.close();
                connection.close();
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
