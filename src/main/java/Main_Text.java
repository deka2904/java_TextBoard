import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main_Text {
    private static final ArrayList<Article> boardList = new ArrayList<>();
    private static int number = 1;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // sign 클래스 호출
        Sign sign = new Sign();
        // JDBC 연결 설정
        Connection connection;
        System.out.println("[[------게시판------]]");

        while (true) {
            System.out.println("[1. 회원가입 2. 로그인 3. 종료]");
            System.out.print("해당 번호를 입력하세요: ");
            int menuChoice = Integer.parseInt(scanner.nextLine());

            String nickname = "";
            switch (menuChoice) {
                case 1:
                    sign.sign_in();
                    continue;
                case 2:
                    nickname = sign.login();
                    break;
                case 3:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("올바른 메뉴를 선택하세요.");
                    break;
            }
            if (nickname.equals(nickname)) {
                while (true) {
                    // 로그인 성공
                    System.out.print("명령어(" + nickname + "): ");
                    String func = scanner.nextLine();
                    // 게시글 추가
                    if (func.equals("add")) {
                        System.out.print("게시물 제목을 입력해주세요 : ");
                        String title = scanner.nextLine();
                        System.out.print("게시물 내용을 입력해주세요 : ");
                        String contents = scanner.nextLine();
                        Article new_Board = new Article(number, title, contents);
                        boardList.add(new_Board);

                        // JDBC 연결 설정
                        connection = DatabaseConnection.getConnection();
                        if (connection != null) {
                            try {
                                // SQL 쿼리를 사용하여 데이터베이스에 게시물 추가
                                String insertQuery = "INSERT INTO text_board (title, contents, time) VALUES (?, ?, NOW())";
                                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                                preparedStatement.setString(1, title);
                                preparedStatement.setString(2, contents);

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
                    // 게시글 전체 제목 조회
                    else if (func.equals("list")) {
                        System.out.println("==================");
                        // JDBC 연결 설정
                        connection = DatabaseConnection.getConnection();
                        if (connection != null) {
                            try {
                                // SQL 쿼리를 사용하여 데이터베이스에서 게시물 목록을 가져옴
                                String selectQuery = "SELECT * FROM text_board";
                                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                                // 위 쿼리는 매개 변수가 없으므로 매개 변수 설정은 필요 없음

                                // 결과셋을 가져오기 위해 executeQuery를 사용
                                ResultSet resultSet = preparedStatement.executeQuery();

                                // 결과를 순회하면서 출력
                                while (resultSet.next()) {
                                    int number = resultSet.getInt("number");
                                    String title = resultSet.getString("title");
                                    String time = resultSet.getString("time");
                                    int viewCount = resultSet.getInt("view_count");
                                    // 가져온 결과를 출력
                                    System.out.println("게시글 번호: " + number);
                                    System.out.println("게시글 제목: " + title);
                                    System.out.println("시간: " + time);
                                    System.out.println("조회수: " + viewCount);
                                    System.out.println("==================");
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
                    // 게시글 업데이트
                    else if (func.equals("update")) {
                        System.out.print("수정할 게시물 번호 : ");
                        try {
                            int num = Integer.parseInt(scanner.nextLine());

                            // JDBC 연결 설정
                            connection = DatabaseConnection.getConnection();
                            if (connection != null) {
                                try {
                                    // SQL 쿼리를 사용하여 게시물을 가져옴
                                    String selectQuery = "SELECT * FROM text_board WHERE number = ?";
                                    PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                                    selectStatement.setInt(1, num);

                                    ResultSet resultSet = selectStatement.executeQuery();

                                    if (resultSet.next()) {
                                        System.out.print("새로운 게시물 제목을 입력해주세요 : ");
                                        String newTitle = scanner.nextLine();
                                        System.out.print("새로운 게시물 내용을 입력해주세요 : ");
                                        String newContents = scanner.nextLine();

                                        // SQL UPDATE 쿼리 실행
                                        String updateQuery = "UPDATE text_board SET title = ?, contents = ?, time = NOW() WHERE number = ?";
                                        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                                        updateStatement.setString(1, newTitle);
                                        updateStatement.setString(2, newContents);
                                        updateStatement.setInt(3, num);

                                        int updatedRows = updateStatement.executeUpdate();
                                        if (updatedRows > 0) {
                                            System.out.printf("%d번 게시물이 수정되었습니다.\n", num);
                                        } else {
                                            System.out.println("게시물 수정에 실패했습니다.");
                                        }

                                        updateStatement.close();
                                    } else {
                                        System.out.println("없는 게시물 번호입니다.");
                                    }

                                    resultSet.close();
                                    selectStatement.close();
                                    connection.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("올바른 번호를 입력해 주세요.");
                        }
                    }
                    // 게시글 삭제
                    else if (func.equals("delete")) {
                        System.out.print("삭제할 게시물 번호 : ");
                        try {
                            int num = Integer.parseInt(scanner.nextLine());

                            // JDBC 연결 설정
                            connection = DatabaseConnection.getConnection();
                            if (connection != null) {
                                try {
                                    // SQL DELETE 쿼리 실행
                                    String deleteQuery = "DELETE FROM text_board WHERE number = ?";
                                    PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                                    deleteStatement.setInt(1, num);

                                    int deletedRows = deleteStatement.executeUpdate();
                                    if (deletedRows > 0) {
                                        System.out.printf("%d번 게시물이 삭제되었습니다.\n", num);

                                        // 삭제 후에 데이터 다시 가져와 출력
                                        String selectQuery = "SELECT * FROM text_board";
                                        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                                        ResultSet resultSet = preparedStatement.executeQuery();

                                        // 결과를 순회하면서 출력
                                        while (resultSet.next()) {
                                            int number = resultSet.getInt("number");
                                            String title = resultSet.getString("title");

                                            // 결과를 출력
                                            System.out.println("번호 : " + number);
                                            System.out.println("제목 : " + title);
                                            System.out.println("==================");
                                        }
                                    } else {
                                        System.out.println("게시물 삭제에 실패했습니다. 해당 번호를 찾을 수 없습니다.");
                                    }
                                    deleteStatement.close();
                                    connection.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("올바른 번호를 입력해 주세요.");
                        }
                    }
                    // 게시글 제목으로 조회
                    else if (func.equals("detail")) {
                        System.out.print("상세보기 할 게시물 번호를 입력해주세요 : ");
                        try {
                            int num = Integer.parseInt(scanner.nextLine());
                            // JDBC 연결 설정
                            connection = DatabaseConnection.getConnection();
                            if (connection != null) {
                                try {
                                    // SQL SELECT 쿼리 실행
                                    String selectContentsQuery = "SELECT * " +
                                            "FROM text_board " +
                                            "LEFT JOIN text_board_comment ON text_board.number = text_board_comment.board_number " +
                                            "WHERE text_board.number = ?";

                                    PreparedStatement selectContentsStatement = connection.prepareStatement(selectContentsQuery);
                                    selectContentsStatement.setInt(1, num);

                                    // 결과셋을 가져오기 위해 executeQuery를 사용
                                    ResultSet resultSet = selectContentsStatement.executeQuery();

                                    if (resultSet.next()) {
                                        int number = resultSet.getInt("number");
                                        String title = resultSet.getString("title");
                                        String contents = resultSet.getString("contents");
                                        String time = resultSet.getString("time");
                                        int viewCount = resultSet.getInt("view_count");
                                        String comment = resultSet.getString("comment");
                                        String comment_time = resultSet.getString("comment_time");

                                        // 조회수 1 증가
                                        viewCount++;

                                        // 가져온 결과를 출력
                                        System.out.println("게시글 번호: " + number);
                                        System.out.println("게시글 제목: " + title);
                                        System.out.println("게시글 내용: " + contents);
                                        System.out.println("시간: " + time);
                                        System.out.println("조회수: " + viewCount);
                                        System.out.println("==================");
                                        System.out.println("======= 댓글 =======");

                                        // 댓글 출력을 위한 루프
                                        do {
                                            comment = resultSet.getString("comment");
                                            comment_time = resultSet.getString("comment_time");

                                            System.out.println("댓글 내용: " + comment);
                                            System.out.println("댓글 작성일: " + comment_time);
                                        } while (resultSet.next());

                                        // 조회수 업데이트
                                        String updateViewCountQuery = "UPDATE text_board SET view_count = ? WHERE number = ?";
                                        PreparedStatement updateViewCountStatement = connection.prepareStatement(updateViewCountQuery);
                                        updateViewCountStatement.setInt(1, viewCount);
                                        updateViewCountStatement.setInt(2, num);
                                        updateViewCountStatement.executeUpdate();

                                        // 자원 해제
                                        resultSet.close();
                                        selectContentsStatement.close();
                                        updateViewCountStatement.close();
                                        connection.close();

                                        Outter:
                                        // 외부 무한루프
                                        while (true) {
                                            connection = null; // 루프 내에서 연결 열기
                                            try {
                                                connection = DatabaseConnection.getConnection();
                                                System.out.print("상세보기 기능을 선택해주세요(1. 댓글 등록, 2. 추천, 3. 수정, 4. 삭제, 5. 목록으로): ");
                                                int function = Integer.parseInt(scanner.nextLine());
                                                switch (function) {
                                                    case 1:
                                                        try {
                                                            // 댓글 입력 받기
                                                            System.out.print("댓글 내용을 입력하세요: ");
                                                            String commentText = scanner.nextLine();

                                                            // SQL INSERT 쿼리 실행
                                                            String commentQuery = "INSERT INTO text_board_comment (comment, comment_time, board_number) VALUES (?, NOW(), ?)";
                                                            PreparedStatement commentStatement = connection.prepareStatement(commentQuery);
                                                            commentStatement.setString(1, commentText);
                                                            commentStatement.setInt(2, num); // 게시물 번호를 외래 키로 설정

                                                            // INSERT 실행
                                                            commentStatement.executeUpdate();

                                                            System.out.println("댓글이 성공적으로 등록되었습니다.");

                                                            // 댓글 등록 후 게시글 내용 다시 불러오기
                                                            selectContentsQuery = "SELECT * " +
                                                                    "FROM text_board " +
                                                                    "LEFT JOIN text_board_comment ON text_board.number = text_board_comment.board_number " +
                                                                    "WHERE text_board.number = ?";
                                                            selectContentsStatement = connection.prepareStatement(selectContentsQuery);
                                                            selectContentsStatement.setInt(1, num);
                                                            resultSet = selectContentsStatement.executeQuery();

                                                            if (resultSet.next()) {
                                                                // 게시글 내용 출력
                                                                number = resultSet.getInt("number");
                                                                title = resultSet.getString("title");
                                                                contents = resultSet.getString("contents");
                                                                time = resultSet.getString("time");
                                                                viewCount = resultSet.getInt("view_count");
                                                                comment = resultSet.getString("comment");
                                                                comment_time = resultSet.getString("comment_time");

                                                                // 가져온 결과를 출력
                                                                System.out.println("게시글 번호: " + number);
                                                                System.out.println("게시글 제목: " + title);
                                                                System.out.println("게시글 내용: " + contents);
                                                                System.out.println("시간: " + time);
                                                                System.out.println("조회수: " + viewCount);
                                                                System.out.println("==================");
                                                                System.out.println("======= 댓글 =======");

                                                                // 댓글 출력을 위한 루프
                                                                do {
                                                                    comment = resultSet.getString("comment");
                                                                    comment_time = resultSet.getString("comment_time");

                                                                    System.out.println("댓글 내용: " + comment);
                                                                    System.out.println("댓글 작성일: " + comment_time);
                                                                } while (resultSet.next());

                                                                // 자원 해제
                                                                resultSet.close();
                                                                selectContentsStatement.close();
                                                                updateViewCountStatement.close();
                                                            } else {
                                                                System.out.println("해당 번호의 게시물을 찾을 수 없습니다.");
                                                            }
                                                        } catch (SQLException e) {
                                                            System.out.println("댓글 등록 중 오류가 발생했습니다.");
                                                        }
                                                        break;
                                                    case 2:
                                                        System.out.println("[추천 기능]");
                                                        break;
                                                    case 3:
                                                        System.out.println("[수정 기능]");
                                                        break;
                                                    case 4:
                                                        System.out.println("[삭제 기능]");
                                                        break;
                                                    case 5:
                                                        break Outter;  // 외부로 빠져나감
                                                    default:
                                                        System.out.println("잘못된 입력입니다.");
                                                }
                                            } finally {
                                                if (connection != null) {
                                                    try {
                                                        connection.close(); // 루프가 종료될 때 연결 닫기
                                                    } catch (SQLException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        System.out.println("해당 번호의 게시물을 찾을 수 없습니다.");
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("올바른 번호를 입력해 주세요.");
                        }
                    }
                    // 게시글 키워드 검색 후 조회
                    else if (func.equals("search")) {
                        System.out.print("검색 키워드를 입력해주세요: ");
                        String keyword = scanner.nextLine();

                        // JDBC 연결 설정
                        connection = DatabaseConnection.getConnection();
                        if (connection != null) {
                            try {
                                // SQL 쿼리를 사용하여 키워드 검색
                                String selectQuery = "SELECT * FROM text_board WHERE title LIKE ?";
                                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                                selectStatement.setString(1, "%" + keyword + "%");

                                ResultSet resultSet = selectStatement.executeQuery();
                                // 결과를 순회하면서 출력
                                while (resultSet.next()) {
                                    int number = resultSet.getInt("number");
                                    String title = resultSet.getString("title");
                                    String time = resultSet.getString("time");
                                    // 가져온 결과를 출력
                                    System.out.println("번호: " + number);
                                    System.out.println("제목: " + title);
                                    System.out.println("시간: " + time);
                                    System.out.println("==================");
                                }

                                // 자원 해제
                                resultSet.close();
                                selectStatement.close();
                                connection.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    // 종료
                    else if (func.equals("exit")) {
                        System.out.println("프로그램을 종료합니다.");
                        break;
                    }
                }
            }
        }
    }
}
