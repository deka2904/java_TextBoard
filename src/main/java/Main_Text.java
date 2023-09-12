import org.xml.sax.helpers.AttributesImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main_Text {
    private static final ArrayList<Article> boardList = new ArrayList<>();
    private static int number = 1;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("명령어 : ");
            String func = scanner.nextLine();
            if (func.equals("add")) {
                System.out.print("게시물 제목을 입력해주세요 : ");
                String title = scanner.nextLine();
                System.out.print("게시물 내용을 입력해주세요 : ");
                String contents = scanner.nextLine();
                Article new_Board = new Article(number, title, contents);
                boardList.add(new_Board);

                // JDBC 연결 설정
                Connection connection = DatabaseConnection.getConnection();
                if (connection != null) {
                    try {
                        // SQL 쿼리를 사용하여 데이터베이스에 게시물 추가
                        String insertQuery = "INSERT INTO text_board_ex (title, contents, time) VALUES (?, ?, NOW())";
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
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (func.equals("update")) {
                System.out.print("수정할 게시물 번호 : ");
                try {
                    int num = Integer.parseInt(scanner.nextLine());

                    // JDBC 연결 설정
                    Connection connection = DatabaseConnection.getConnection();
                    if (connection != null) {
                        try {
                            // SQL 쿼리를 사용하여 게시물을 가져옴
                            String selectQuery = "SELECT * FROM text_board_ex WHERE number = ?";
                            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                            selectStatement.setInt(1, num);

                            ResultSet resultSet = selectStatement.executeQuery();

                            if (resultSet.next()) {
                                System.out.print("새로운 게시물 제목을 입력해주세요 : ");
                                String newTitle = scanner.nextLine();
                                System.out.print("새로운 게시물 내용을 입력해주세요 : ");
                                String newContents = scanner.nextLine();

                                // SQL UPDATE 쿼리 실행
                                String updateQuery = "UPDATE text_board_ex SET title = ?, contents = ?, time = NOW() WHERE number = ?";
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
            else if (func.equals("delete")) {
                System.out.print("삭제할 게시물 번호 : ");
                try {
                    int num = Integer.parseInt(scanner.nextLine());

                    // JDBC 연결 설정
                    Connection connection = DatabaseConnection.getConnection();
                    if (connection != null) {
                        try {
                            // SQL DELETE 쿼리 실행
                            String deleteQuery = "DELETE FROM text_board_ex WHERE number = ?";
                            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                            deleteStatement.setInt(1, num);

                            int deletedRows = deleteStatement.executeUpdate();
                            if (deletedRows > 0) {
                                System.out.printf("%d번 게시물이 삭제되었습니다.\n", num);

                                // 삭제 후에 데이터 다시 가져와 출력
                                String selectQuery = "SELECT * FROM text_board_ex";
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
            else if (func.equals("detail")) {
                System.out.print("상세보기 할 게시물 번호를 입력해주세요 : ");
                try {
                    int num = Integer.parseInt(scanner.nextLine());

                    // JDBC 연결 설정
                    Connection connection = DatabaseConnection.getConnection();
                    if (connection != null) {
                        try {
                            // SQL SELECT 쿼리 실행
                            String selectContentsQuery = "SELECT * FROM text_board_ex WHERE number = ?";
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

                                // 조회수 1 증가
                                viewCount++;

                                // 가져온 결과를 출력
                                System.out.println("번호: " + number);
                                System.out.println("제목: " + title);
                                System.out.println("내용: " + contents);
                                System.out.println("시간: " + time);
                                System.out.println("현재 조회수: " + viewCount);
                                System.out.println("==================");

                                // 조회수 업데이트
                                String updateViewCountQuery = "UPDATE text_board_ex SET view_count = ? WHERE number = ?";
                                PreparedStatement updateViewCountStatement = connection.prepareStatement(updateViewCountQuery);
                                updateViewCountStatement.setInt(1, viewCount);
                                updateViewCountStatement.setInt(2, num);
                                updateViewCountStatement.executeUpdate();

                                // 자원 해제
                                resultSet.close();
                                selectContentsStatement.close();
                                updateViewCountStatement.close();
                                connection.close();
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
            else if (func.equals("search")) {
                System.out.print("검색 키워드를 입력해주세요: ");
                String keyword = scanner.nextLine();

                // JDBC 연결 설정
                Connection connection = DatabaseConnection.getConnection();
                if (connection != null) {
                    try {
                        // SQL 쿼리를 사용하여 키워드 검색
                        String selectQuery = "SELECT * FROM text_board_ex WHERE title LIKE ?";
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
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (func.equals("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }
        }
    }
}
