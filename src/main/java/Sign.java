import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Sign {
    Scanner scanner = new Scanner(System.in);
    Connection connection = null;
    PreparedStatement idCheckStatement = null;
    PreparedStatement preparedStatement = null;

    // 회원가입
    public void sign_in() {
        System.out.print("아이디를 입력해주세요: ");
        String id = scanner.nextLine();

        try {
            // JDBC 연결 설정
            connection = DatabaseConnection.getConnection();

            // 아이디 중복 체크
            String idCheckQuery = "SELECT id FROM member WHERE id = ?";
            idCheckStatement = connection.prepareStatement(idCheckQuery);
            idCheckStatement.setString(1, id);

            ResultSet resultSet = idCheckStatement.executeQuery();

            if (resultSet.next()) {
                // 아이디가 이미 존재하는 경우
                System.out.println("[[존재하는 id입니다.]]");
            } else {
                // 아이디가 존재하지 않는 경우
                System.out.println("[[아이디를 사용할 수 있습니다.]]");

                while (true){
                    System.out.print("패스워드를 입력해주세요: ");
                    String pw1 = scanner.nextLine();
                    System.out.print("입력하신 패스워드를 재확인 합니다: ");
                    String pw2 = scanner.nextLine();
                    System.out.print("닉네임을 입력하세요: ");
                    String nickname = scanner.nextLine();
                    if (pw1.equals(pw2)){
                        // SQL 쿼리를 사용하여 데이터베이스에 회원 정보 추가
                        String insertQuery = "INSERT INTO member (id, password, nickname) VALUES (?, ?, ?)";
                        preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setString(1, id);
                        preparedStatement.setString(2, pw1);
                        preparedStatement.setString(3, nickname);

                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("[[회원 가입이 완료되었습니다.]]");
                            break;
                        }
                    }else{
                        System.out.println("[[패스워드를 잘못입력하셨습니다.]]");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 사용한 자원 해제
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (idCheckStatement != null) {
                    idCheckStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // 로그인
    public String login(){
        System.out.print("아이디를 입력해주세요: ");
        String id = scanner.nextLine();
        System.out.print("패스워드를 입력해주세요: ");
        String pw = scanner.nextLine();
        try {
            // JDBC 연결 설정
            connection = DatabaseConnection.getConnection();

            // 아이디와 패스워드로 회원 조회
            String memberCheckQuery = "SELECT * FROM member WHERE id = ? AND password = ?";
            PreparedStatement memberCheckStatement = connection.prepareStatement(memberCheckQuery);
            memberCheckStatement.setString(1, id);
            memberCheckStatement.setString(2, pw);

            ResultSet resultSet = memberCheckStatement.executeQuery();

            if (resultSet.next()) {
                // 로그인 성공
                String nickname = resultSet.getString("nickname"); // 닉네임을 가져옴
                System.out.println("로그인 성공!");
                return nickname;
            } else {
                // 로그인 실패: 아이디 또는 패스워드가 일치하지 않음
                System.out.println("존재하지 않은 회원입니다.");
                return "";
            }
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
            return "";
        }
    }
    // 로그아웃
    public void logout(){
        System.out.println("로그아웃 합니다.");
    }
}
