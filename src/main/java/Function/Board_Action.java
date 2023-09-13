package Function;

import Main.Main_textboard;
import SQL.DatabaseConnection;
import commentFunction.CommentAdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Board_Action implements Action {
    private Connection connection;
    private Scanner scanner;

    public Board_Action() {
        this.connection = DatabaseConnection.getConnection();
        this.scanner = new Scanner(System.in);
    }
    @Override
    public void add() {
        System.out.print("게시물 제목을 입력해주세요 : ");
        String title = scanner.nextLine();
        System.out.print("게시물 내용을 입력해주세요 : ");
        String contents = scanner.nextLine();
        Article new_Board = new Article(Main_textboard.number, title, contents);
        Main_textboard.boardList.add(new_Board);

        // JDBC 연결 설정
        if (connection != null) {
            try {
                // SQL 쿼리를 사용하여 데이터베이스에 게시물 추가
                String insertQuery = "INSERT INTO text_board (title, contents, time) VALUES (?, ?, NOW())";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, contents);

                Main_textboard.number++;

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("게시물이 등록되었습니다.");
                } else {
                    System.out.println("게시물 등록에 실패했습니다.");
                }

                preparedStatement.close();
                // Do not close the connection here.
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void delete(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 게시물 번호 : ");
        try {
            int num = Integer.parseInt(scanner.nextLine());

            // JDBC 연결 설정
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
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("올바른 번호를 입력해 주세요.");
        }
    }
    @Override
    public void detail(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("상세보기 할 게시물 번호를 입력해주세요 : ");
        try {
            int num = Integer.parseInt(scanner.nextLine());
            // JDBC 연결 설정
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

                    PreparedStatement updateViewCountStatement = null;
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
                        updateViewCountStatement = connection.prepareStatement(updateViewCountQuery);
                        updateViewCountStatement.setInt(1, viewCount);
                        updateViewCountStatement.setInt(2, num);
                        updateViewCountStatement.executeUpdate();

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
                                        CommentAdd commentAdd = new CommentAdd();
                                        commentAdd.commentadd(num);
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
                    // 자원 해제
                    resultSet.close();
                    selectContentsStatement.close();
                    updateViewCountStatement.close();
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("올바른 번호를 입력해 주세요.");
        }
    }
    @Override
    public void list(){
        System.out.println("==================");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // JDBC 연결 설정
        if (connection != null) {
            try {
                // SQL 쿼리를 사용하여 데이터베이스에서 게시물 목록을 가져옴
                String selectQuery = "SELECT * FROM text_board";
                preparedStatement = connection.prepareStatement(selectQuery);
                // 위 쿼리는 매개 변수가 없으므로 매개 변수 설정은 필요 없음

                // 결과셋을 가져오기 위해 executeQuery를 사용
                resultSet = preparedStatement.executeQuery();

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

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    // 자원 해제
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    // Do not close the connection here.
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void search(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("검색 키워드를 입력해주세요: ");
        String keyword = scanner.nextLine();

        // JDBC 연결 설정
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
                // Do not close the connection here.
            } catch (SQLException e) {
                System.out.println("Error" + e);
            }
        }
    }
    @Override
    public void update(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("수정할 게시물 번호 : ");
        try {
            int num = Integer.parseInt(scanner.nextLine());

            // JDBC 연결 설정
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
                    // Do not close the connection here.
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("올바른 번호를 입력해 주세요.");
        }
    }
}
