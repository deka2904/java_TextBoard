package MemberFunction;

import SQL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Member_Action {
    public Member checkMenber(String sql, Object... Member){
        Connection connection = DatabaseConnection.getConnection();
        ResultSet resultSet;
        int i = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (Object str : Member) {
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
                Member member = new Member();
                member.setId(resultSet.getString("id"));
                member.setPassword(resultSet.getString("password"));
                member.setNickname(resultSet.getString("nickname"));
                return member;
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int member(String sql, Object... Member){
        Connection connection = DatabaseConnection.getConnection();
        int affectedRows;
        int i = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            for(Object str : Member){
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
