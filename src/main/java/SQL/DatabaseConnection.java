package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection
    getConnection() {
        Connection connection = null;
        //jdbc:mysql://127.0.0.1:3306/text_board
        String server = "127.0.0.1"; // 서버 주소
        String user_name = "root"; //  접속자 id
        String password = "1234"; // 접속자 pw

        // JDBC 드라이버 로드
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 로드하는데에 문제 발생" + e.getMessage());
            e.printStackTrace();
        }

        // 접속
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + server + "/" + "text_board", user_name, password);
        } catch (SQLException e) {
            System.err.println("연결 오류" + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}

