package BoardFunction;

import SQL.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board_Action implements Action {
    Article article = new Article();
    int number = 1;
    private static final int PAGE_SIZE = 3; // 페이지 상수
    Scanner scanner = new Scanner(System.in);
    // list / search
    public List<Article> selectList(String sql) {
        ArrayList<Article> articles = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        boolean foundResults = false; // 결과가 있으면 true로 설정
        try {
            // SQL 쿼리를 사용하여 데이터베이스에서 게시물 목록을 가져옴
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                foundResults = true;
                Article article = new Article();
                article.setNumber(resultSet.getInt("number"));
                article.setTitle(resultSet.getString("title"));
                article.setContents(resultSet.getString("contents"));
                article.setText_board_member_nickname(resultSet.getString("text_board_member_nickname"));
                article.setTime(resultSet.getString("time"));
                article.setView_count(resultSet.getInt("view_count"));
                article.setText_board_suggestion(resultSet.getInt("text_board_suggestion"));

                articles.add(article);
            }
            if (!foundResults) {
                System.out.println("찾을 수 없습니다.");
            }
            // 자원 해제
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            // 발생할 수 있는 SQLException 처리
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // 닫을 때 발생할 수 있는 SQLException 처리
            }
        }
        return articles;
    }
    // detail
    public Article selectOne(String sql) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                article.setNumber(resultSet.getInt("number"));
                article.setTitle(resultSet.getString("title"));
                article.setContents(resultSet.getString("contents"));
                article.setText_board_member_nickname(resultSet.getString("text_board_member_nickname"));
                article.setTime(resultSet.getString("time"));
                article.setView_count(resultSet.getInt("view_count"));
                article.setText_board_suggestion(resultSet.getInt("text_board_suggestion"));
            }
            resultSet.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // 닫을 때 발생할 수 있는 SQLException 처리
            }
        }
        return article;
    }
    // update / delete
    public int updateArticle(String sql, Object... article) {
        Connection connection = DatabaseConnection.getConnection();
        int affectedRows;
        int i = 0;
        try {
            //반복문 PrepareStatement 완성
            PreparedStatement statement = connection.prepareStatement(sql);
            for(Object str : article){
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

















    @Override
    public void sort(){
        // ASC - 오름차순 / DESC - 내림차순
        // JDBC 연결 설정
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try{
                String column = "";
                String sortOrder = "";
                Scanner scanner = new Scanner(System.in);
                System.out.println("=============================================================================");
                System.out.print("정렬 대상을 선택해주세요. (1. 번호,  2. 조회수) : ");
                int sort1_num = Integer.parseInt(scanner.nextLine());
                if (sort1_num == 1){
                    column = "number"; // 번호를 선택한 경우
                } else if(sort1_num == 2){
                    column = "view_count"; // 조회수를 선택한 경우
                } else {
                    System.out.println("올바른 선택이 아닙니다.");
                    return;
                }
                System.out.println("=============================================================================");
                System.out.print("정렬 방법을 선택해주세요. (1. 오름차순,  2. 내림차순) : ");
                int sort2_num = Integer.parseInt(scanner.nextLine());
                System.out.println("=============================================================================");
                if (sort2_num == 1){
                    sortOrder = "ASC"; // 오름차순을 선택한 경우
                } else if(sort2_num == 2){
                    sortOrder = "DESC"; // 내림차순을 선택한 경우
                } else {
                    System.out.println("올바른 선택이 아닙니다.");
                    return;
                }

                // SQL 쿼리 생성
                String SortSQL = "SELECT * FROM text_board ORDER BY " + column + " " + sortOrder;

                // 이제 SortSQL을 사용하여 데이터베이스에서 데이터를 검색하거나 처리할 수 있습니다.
                PreparedStatement sortStatement = connection.prepareStatement(SortSQL);

                ResultSet resultSet = sortStatement.executeQuery();

//                article.board_list(resultSet);
                System.out.println("=============================================================================");
                sortStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                // 발생할 수 있는 SQLException 처리
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    // 닫을 때 발생할 수 있는 SQLException 처리
                }
            }
        }
    }
    @Override
    public void page(int pageNumber) {
        // JDBC 연결 설정
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                boolean exit = false;
                while (!exit) {
                    // 페이지 번호에 따라 오프셋 계산
                    int offset = (pageNumber - 1) * PAGE_SIZE;

                    // SQL 쿼리를 작성합니다. 여기서는 MySQL을 가정합니다.
                    String pagingSql = "SELECT * FROM text_board LIMIT ? OFFSET ?";
                    PreparedStatement pagingStatement = connection.prepareStatement(pagingSql);
                    pagingStatement.setInt(1, PAGE_SIZE);
                    pagingStatement.setInt(2, offset);

                    // 결과를 순회하면서 출력 (페이징 처리)
                    ResultSet resultSet = pagingStatement.executeQuery();
                    boolean hasNextPage = false;

//                    article.board_list(resultSet);
                    hasNextPage = true;
                    resultSet.close(); // resultSet 닫기
                    pagingStatement.close(); // pagingStatement 닫기

                    if (!hasNextPage) {
                        System.out.println("더 이상 페이지가 없습니다.");
                        break; // 더 이상 페이지가 없으면 반복문 종료
                    }
                    if (pageNumber == 1){
                        System.out.println("[1] 2 3 4 5 >>");
                    }else if(pageNumber == 2){
                        System.out.println("1 [2] 3 4 5 >>");
                    }else if(pageNumber == 3){
                        System.out.println("1 2 [3] 4 5 >>");
                    }else if(pageNumber == 4){
                        System.out.println("1 2 3 [4] 5 >>");
                    }else{
                        System.out.println("1 2 3 4 [5] >>");
                    }
                    boolean innerExit = false;
                    while (!innerExit) {
                        System.out.print("페이징 명령어를 입력해주세요 (1. 이전, 2. 다음, 3. 선택, 4. 뒤로가기) : ");
                        int choice = Integer.parseInt(scanner.nextLine());
                        switch (choice) {
                            case 1:
                                if (pageNumber > 1) {
                                    pageNumber--;
                                    innerExit = true;
                                } else {
                                    System.out.println("첫 번째 페이지입니다.");
                                }
                                break;
                            case 2:
                                pageNumber++;
                                innerExit = true; // 내부 루프 빠져나가도록 설정
                                break;
                            case 3:
                                System.out.print("이동할 페이지 번호를 입력하세요: ");
                                int selectedPage = Integer.parseInt(scanner.nextLine());
                                if (selectedPage >= 1) {
                                    pageNumber = selectedPage;
                                    innerExit = true; // 내부 루프 빠져나가도록 설정
                                } else {
                                    System.out.println("유효하지 않은 페이지 번호입니다.");
                                }
                                break;
                            case 4:
                                exit = true;
                                innerExit = true; // 내부 루프 빠져나가도록 설정
                                System.out.println("페이지 검색 종료합니다.");
                                break;
                            default:
                                System.out.println("유효하지 않은 선택입니다.");
                        }
                    }
                }
                // 연결 닫기
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}