package commentFunction;

import BoardFunction.Article;
import SQL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Commend_Action {
    public List<Commend> selectOne(String sql) {
        ArrayList<Commend> commends = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            // SQL 쿼리를 사용하여 데이터베이스에서 게시물 목록을 가져옴
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Commend commend = new Commend();
                commend.setCommend(resultSet.getString("comment"));
                commend.setComment_member_nickname(resultSet.getString("comment_member_nickname"));
                commend.setTime(resultSet.getString("comment_time"));
                commends.add(commend);
            }
            // 자원 해제
            preparedStatement.close();
            resultSet.close();
        }catch(SQLException e){
            // 발생할 수 있는 SQLException 처리
        } finally{
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // 닫을 때 발생할 수 있는 SQLException 처리
            }
        }
        return commends;
    }
}
