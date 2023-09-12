package Function;

import SQL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import static Function.Main_Text.connection;

public class Board_Detail {
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
}
