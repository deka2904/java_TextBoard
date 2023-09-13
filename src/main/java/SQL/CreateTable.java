package SQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null){
            Statement statement;
            try{
                statement = connection.createStatement();

                // 회원 테이블
                String createMemberTableSQL = "CREATE TABLE IF NOT EXISTS member(" +
                        "id VARCHAR(255) PRIMARY KEY," +
                        "password VARCHAR(255)," +
                        "nickname VARCHAR(255)," +
                        "INDEX idx_nickname (nickname))";


                // 게시판 테이블
                String createTableSQL = "CREATE TABLE IF NOT EXISTS text_board (" +
                        "number INT AUTO_INCREMENT PRIMARY KEY," +
                        "title VARCHAR(255)," +
                        "contents TEXT," +
                        "view_count INT DEFAULT 0," +
                        "time TIMESTAMP DEFAULT NOW()," +
                        "member_nickname VARCHAR(255)," + // 외래 키로 사용할 회원의 닉네임
                        "FOREIGN KEY (member_nickname) REFERENCES member(nickname))";

                // 댓글 테이블
                String createCommentTableSQL = "CREATE TABLE IF NOT EXISTS text_board_comment (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "comment TEXT," +
                        "comment_suggestion INT DEFAULT 0," +
                        "comment_time TIMESTAMP DEFAULT NOW()," +
                        "board_number INT," +           // 외래 키로 사용할 게시판 번호
                        "member_nickname VARCHAR(255)," + // 외래 키로 사용할 회원의 닉네임
                        "FOREIGN KEY (board_number) REFERENCES text_board(number)," +
                        "FOREIGN KEY (member_nickname) REFERENCES member(nickname))";


                statement.executeUpdate(createMemberTableSQL);
                statement.executeUpdate(createTableSQL);
                statement.executeUpdate(createCommentTableSQL);
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
