import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

class Board {
    int number;
    String title;   // 제목
    String detail;  // 내용

    public Board(int number, String title, String detail){
        this.number = number;
        this.title = title;
        this.detail = detail;
    }
    public int getNumber() {
        return this.number;
    }
    public void setTitle(String newTitle) {
        this.title = newTitle;
    }
    public void setDetail(String newDetail) {
        this.detail = newDetail;
    }
}

public class Main_Text {
    private static int number = 1;
    private static final ArrayList<Board> boardList = new ArrayList<>();
    public static void  board_print(){
        for (Board board : boardList) {
            System.out.println("번호 : " + board.number);
            System.out.println("제목 : " + board.title);
            System.out.println("내용 : " + board.detail);
            System.out.println("==================");
        }
    }
    // 수정, 삭제 공통 프로세스 공유
    public static int findIndex(int num){
        for (int i = 0; i < boardList.size(); i++) {
            if (boardList.get(i).getNumber() == num) {
                return i;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("명령어 : ");
            String func = scanner.nextLine();

            if (func.equals("add")) {
                System.out.print("게시물 제목을 입력해주세요 : ");
                String title = scanner.nextLine();
                System.out.print("게시물 내용을 입력해주세요 : ");
                String detail = scanner.nextLine();
                Board new_Board = new Board(number, title, detail);
                boardList.add(new_Board);
//                System.out.println("게시물이 등록되었습니다.");
                System.out.println(number);

                // JDBC 연결 설정
                Connection connection = DatabaseConnection.getConnection();
                if (connection != null) {
                    try {
                        // SQL 쿼리를 사용하여 데이터베이스에 게시물 추가
                        String insertQuery = "INSERT INTO text_board_ex (number, title, detail) VALUES (?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setInt(1, number);
                        preparedStatement.setString(2, title);
                        preparedStatement.setString(3, detail);
                        number++;

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("게시물이 등록되었습니다.");
                        } else {
                            System.out.println("게시물 등록에 실패했습니다.");
                        }

                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (func.equals("list")) {
                System.out.println("==================");
                // JDBC 연결 설정
                Connection connection = DatabaseConnection.getConnection();
                if (connection != null) {
                    try {
                        // SQL 쿼리를 사용하여 데이터베이스에서 게시물 목록을 가져옴
                        String selectQuery = "SELECT * FROM text_board_ex";
                        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                        // 위 쿼리는 매개 변수가 없으므로 매개 변수 설정은 필요 없음

                        // 결과셋을 가져오기 위해 executeQuery를 사용
                        ResultSet resultSet = preparedStatement.executeQuery();

                        // 결과를 순회하면서 출력
                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            String title = resultSet.getString("title");
                            String detail = resultSet.getString("detail");

                            // 가져온 결과를 출력
                            board_print();
                        }
                        // 자원 해제
                        resultSet.close();
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (func.equals("update")) {
                System.out.print("수정할 게시물 번호 : ");
                try{
                    int num = Integer.parseInt(scanner.nextLine());
                    int index = findIndex(num);
                    if (index != -1) {
                        System.out.print("새로운 게시물 제목을 입력해주세요 : ");
                        String newTitle = scanner.nextLine();
                        System.out.print("새로운 게시물 내용을 입력해주세요 : ");
                        String newDetail = scanner.nextLine();

                        boardList.get(index).setTitle(newTitle);   // 수정할 게시글 제목
                        boardList.get(index).setDetail(newDetail); // 수정할 게시글 내용
                        System.out.printf("%d번 게시물이 수정되었습니다.\n", num);
                    } else {
                        System.out.println("없는 게시물 번호입니다.");
                    }
                }catch (Exception e){
                    System.out.println("올바른 번호 입력해 주세요");
                }
            }
            else if (func.equals("delete")) {
                System.out.print("삭제할 게시물 번호 : ");
                try {
                    int num = Integer.parseInt(scanner.nextLine());
                    int index = findIndex(num);
                    if (index != -1) {
                        boardList.remove(index);
                        System.out.printf("%d번 게시물이 삭제되었습니다.\n", num);
                    } else {
                        System.out.println("없는 게시물 번호입니다.");
                    }
                    System.out.println("==================");
                    board_print();
                }catch (Exception e){
                    System.out.println("올바른 번호 입력해 주세요");
                }
            }
            else if (func.equals("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }
        }
    }
}
