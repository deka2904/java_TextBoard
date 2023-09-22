package commentFunction;

import SQL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Commend_Action {
    public List<Commend> selectList(String sql) {
        ArrayList<Commend> commends = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            // SQL 쿼리를 사용하여 데이터베이스에서 게시물 목록을 가져옴
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Commend commend = new Commend();
                commend.setId(resultSet.getInt("id"));
                commend.setCommend(resultSet.getString("comment"));
                commend.setComment_member_nickname(resultSet.getString("comment_member_nickname"));
                commend.setTime(resultSet.getString("comment_time"));
                commends.add(commend);
            }
            // 자원 해제
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }catch(SQLException e){
            // 발생할 수 있는 SQLException 처리
        }
        return commends;
    }
    // int board_number, String nickname
    public int checkRecomment(String sql, Object... comment) {
        Connection connection = DatabaseConnection.getConnection();
        ResultSet resultSet = null;
        int result = 0;
        int i = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (Object str : comment) {
                if (str instanceof Integer) {
                    i++;
                    statement.setInt(i, (Integer) str);
                } else if (str instanceof Boolean) {
                    i++;
                    statement.setBoolean(i, (Boolean) str);
                } else {
                    i++;
                    statement.setString(i, (String) str);
                }
            }
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = 1;
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public int updateComment(String sql, Object... comment){
        Connection connection = DatabaseConnection.getConnection();
        int affectedRows;
        int i = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            for(Object str : comment){
                if (str instanceof Integer){
                    i++;
                    statement.setInt(i, (Integer)str);
                }else if(str instanceof Boolean){
                    i++;
                    statement.setBoolean(i, (Boolean)str);
                }else{
                    i++;
                    statement.setString(i, (String)str);
                }
            }
            affectedRows = statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affectedRows;
    }
}
