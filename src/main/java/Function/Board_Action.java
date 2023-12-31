package Function;

import Main.Main_textboard;
import SQL.DatabaseConnection;
import commentFunction.Comment_Add;
import commentFunction.Comment_Delete;
import commentFunction.Comment_Recommend;
import commentFunction.Comment_Update;

import java.sql.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import static Main.Main_textboard.number;
public class Board_Action implements Action {
    private static final int PAGE_SIZE = 3; // 페이지 상수
    Scanner scanner = new Scanner(System.in);
    @Override
    public void add(String nickname){
        System.out.print("게시물 제목을 입력해주세요 : ");
        String title = scanner.nextLine();
        System.out.print("게시물 내용을 입력해주세요 : ");
        String contents = scanner.nextLine();
        Article new_Board = new Article(number, title, contents);
        Main_textboard.boardList.add(new_Board);
        String text_board_member_nickname = nickname;
        // JDBC 연결 설정
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                // SQL 쿼리를 사용하여 데이터베이스에 게시물 추가
                String insertQuery = "INSERT INTO text_board (title, contents, time, text_board_member_nickname) VALUES (?, ?, NOW(), ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, contents);
                preparedStatement.setString(3, text_board_member_nickname);

                number++;

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("게시물이 등록되었습니다.");
                } else {
                    System.out.println("게시물 등록에 실패했습니다.");
                }
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {

                }
            }
        }
    }
    @Override
    public void list(){
        System.out.println("=============================================================================");

        // JDBC 연결 설정
        Connection connection = DatabaseConnection.getConnection();
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
                    String text_board_member_nickname = resultSet.getString("text_board_member_nickname");
                    int text_board_suggestion =  resultSet.getInt("text_board_suggestion");

                    // 가져온 결과를 출력
                    System.out.println("[게시글 번호] : " + number);
                    System.out.println("[게시글 제목] : " + title);
                    System.out.println("[작성자] : "+ text_board_member_nickname);
                    System.out.println("[시간] : " + time);
                    System.out.println("[조회수] : " + viewCount);
                    System.out.println("[추천수] : " + text_board_suggestion);
                    System.out.println("=============================================================================");
                }
                preparedStatement.close();
                resultSet.close();
            }catch (SQLException e) {
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

                // 결과를 순회하면서 출력
                while (resultSet.next()) {
                    int number = resultSet.getInt("number");
                    String title = resultSet.getString("title");
                    String time = resultSet.getString("time");
                    int viewCount = resultSet.getInt("view_count");
                    String text_board_member_nickname = resultSet.getString("text_board_member_nickname");
                    int text_board_suggestion = resultSet.getInt("text_board_suggestion");

                    // 가져온 결과를 출력
                    System.out.println("[게시글 번호] : " + number);
                    System.out.println("[게시글 제목] : " + title);
                    System.out.println("[작성자] : "+ text_board_member_nickname);
                    System.out.println("[시간] : " + time);
                    System.out.println("[조회수] : " + viewCount);
                    System.out.println("[추천수] : " + text_board_suggestion);
                    System.out.println("=============================================================================");
                }
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

                    while (resultSet.next()) {
                        int number = resultSet.getInt("number");
                        String title = resultSet.getString("title");
                        String time = resultSet.getString("time");
                        int viewCount = resultSet.getInt("view_count");
                        String text_board_member_nickname = resultSet.getString("text_board_member_nickname");
                        int text_board_suggestion = resultSet.getInt("text_board_suggestion");

                        // 가져온 결과를 출력
                        System.out.println("[게시글 번호] : " + number);
                        System.out.println("[게시글 제목] : " + title);
                        System.out.println("[작성자] : " + text_board_member_nickname);
                        System.out.println("[시간] : " + time);
                        System.out.println("[조회수] : " + viewCount);
                        System.out.println("[추천수] : " + text_board_suggestion);
                        System.out.println("=============================================================================");

                        hasNextPage = true;
                    }
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
    @Override
    public void delete(){
        Scanner scanner = new Scanner(System.in);
        // JDBC 연결 설정
        Connection connection = DatabaseConnection.getConnection();
        try{
            String selectQuery = "SELECT * FROM text_board";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과를 순회하면서 출력
            while (resultSet.next()) {
                int number = resultSet.getInt("number");
                String title = resultSet.getString("title");
                String time = resultSet.getString("time");
                int viewCount = resultSet.getInt("view_count");
                String text_board_member_nickname = resultSet.getString("text_board_member_nickname");
                int text_board_suggestion = resultSet.getInt("text_board_suggestion");

                // 가져온 결과를 출력
                System.out.println("[게시글 번호] : " + number);
                System.out.println("[게시글 제목] : " + title);
                System.out.println("[작성자] : "+ text_board_member_nickname);
                System.out.println("[시간] : " + time);
                System.out.println("[조회수] : " + viewCount);
                System.out.println("[추천수] : " + text_board_suggestion);
                System.out.println("=============================================================================");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.print("삭제할 게시물 번호 : ");
        boolean foundResults = false; // 결과가 있으면 true로 설정
        try {
            int num = Integer.parseInt(scanner.nextLine());

            if (connection != null) {
                try {
                    // SQL DELETE 쿼리 실행
                    String deleteQuery = "DELETE FROM text_board WHERE number = ?";
                    PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                    deleteStatement.setInt(1, num);

                    int deletedRows = deleteStatement.executeUpdate();
                    if (deletedRows > 0) {
                        System.out.printf("%d번 게시물이 삭제되었습니다.\n", num);
                        foundResults = true;

                        // 삭제 후에 데이터 다시 가져와 출력
                        String selectQuery = "SELECT * FROM text_board";
                        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        // 결과를 순회하면서 출력
                        while (resultSet.next()) {
                            int number = resultSet.getInt("number");
                            String title = resultSet.getString("title");
                            String time = resultSet.getString("time");
                            int viewCount = resultSet.getInt("view_count");
                            String text_board_member_nickname = resultSet.getString("text_board_member_nickname");
                            int text_board_suggestion = resultSet.getInt("text_board_suggestion");

                            // 가져온 결과를 출력
                            System.out.println("[게시글 번호] : " + number);
                            System.out.println("[게시글 제목] : " + title);
                            System.out.println("[작성자] : "+ text_board_member_nickname);
                            System.out.println("[시간] : " + time);
                            System.out.println("[조회수] : " + viewCount);
                            System.out.println("[추천수] : " + text_board_suggestion);
                            System.out.println("=============================================================================");
                        }
                    }
                    if(!foundResults){
                        System.out.println("해당 번호를 찾을 수 없습니다.");
                    }
                    deleteStatement.close();
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
        } catch (NumberFormatException e) {
            System.out.println("올바른 번호를 입력해 주세요.");
        }
    }
    @Override
    public void detail(String nickname){
        System.out.println("=============================================================================");
        // JDBC 연결 설정
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement updateViewCountStatement = null;
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
                String text_board_member_nickname = resultSet.getString("text_board_member_nickname");
                int text_board_suggestion = resultSet.getInt("text_board_suggestion");

                // 가져온 결과를 출력
                System.out.println("[게시글 번호] : " + number);
                System.out.println("[게시글 제목] : " + title);
                System.out.println("[작성자] : "+ text_board_member_nickname);
                System.out.println("[시간] : " + time);
                System.out.println("[조회수] : " + viewCount);
                System.out.println("[추천수] : " + text_board_suggestion);
                System.out.println("=============================================================================");
            }
            preparedStatement.close();
            resultSet.close();
        }catch (SQLException e) {

        }
        System.out.print("상세보기 할 게시물 번호를 입력해주세요 : ");
        try {
            int num = Integer.parseInt(scanner.nextLine());
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
                        int text_board_suggestion = resultSet.getInt("text_board_suggestion");
                        String text_board_member_nickname = resultSet.getString("text_board_member_nickname");

                        // 조회수 1 증가
                        viewCount++;

                        // 가져온 결과를 출력
                        System.out.println("[게시글 번호] : " + number);
                        System.out.println("[게시글 제목] : " + title);
                        System.out.println("[게시글 내용] : " + contents);
                        System.out.println("[작성자] : "+ text_board_member_nickname);
                        System.out.println("[시간] : " + time);
                        System.out.println("[조회수] : " + viewCount);
                        System.out.println("[추천수] : " + text_board_suggestion);
                        System.out.println("=============================================================================");
                        System.out.println("================================= 댓글 =======================================");

                        try {
                            String nicknameSQL = "SELECT comment, comment_time, comment_member_nickname FROM text_board_comment WHERE board_number = ?";
                            PreparedStatement nicknameStatement = connection.prepareStatement(nicknameSQL);
                            nicknameStatement.setInt(1, num);

                            ResultSet nicknameResultSet = nicknameStatement.executeQuery();

                            if (nicknameResultSet.next()) {
                                // 댓글 출력을 위한 루프
                                do {
                                    String comment = nicknameResultSet.getString("comment");
                                    String comment_time = nicknameResultSet.getString("comment_time");
                                    String comment_member_nickname = nicknameResultSet.getString("comment_member_nickname");

                                    System.out.println("[댓글 내용] : " + comment);
                                    System.out.println("[작성자] : " + comment_member_nickname);
                                    System.out.println("[댓글 작성일] : " + comment_time);
                                    System.out.println("=============================================================================");
                                } while (nicknameResultSet.next());
                            } else {
                                System.out.println("댓글이 없습니다.");
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }

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
                                        Comment_Add comment_add = new Comment_Add();
                                        comment_add.commentadd(num, nickname);
                                        break;
                                    case 2:
                                        Comment_Recommend comment_recommend = new Comment_Recommend();
                                        comment_recommend.commentrecommend(num, nickname);
                                        break;
                                    case 3:
                                        Comment_Update comment_update = new Comment_Update();
                                        comment_update.commentupdate(num, nickname);
                                        break;
                                    case 4:
                                        Comment_Delete comment_delete = new Comment_Delete();
                                        comment_delete.commentdelete(num, nickname);
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
                                        System.out.println("Error" + e);
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("해당 번호의 게시물을 찾을 수 없습니다.");
                    }
                    // 자원 해제
                    selectContentsStatement.close();
                    // 자원 해제
                    if (updateViewCountStatement != null) {
                        updateViewCountStatement.close();
                    }
                    resultSet.close();
                }catch (SQLException e) {
                    System.out.println("해당 번호의 게시물을 찾을 수 없습니다.");
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
        } catch (NumberFormatException e) {
            System.out.println("올바른 번호를 입력해 주세요.");
        }
    }
    @Override
    public void search() {
        System.out.print("검색 키워드를 입력해주세요: ");
        String keyword = scanner.nextLine();

        // JDBC 연결 설정
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                // SQL 쿼리를 사용하여 키워드 검색
                String selectQuery = "SELECT * FROM text_board WHERE title LIKE ?";
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                selectStatement.setString(1, "%" + keyword + "%");

                ResultSet resultSet = selectStatement.executeQuery();
                boolean foundResults = false; // 결과가 있으면 true로 설정

                // 결과를 순회하면서 출력
                while (resultSet.next()) {
                    foundResults = true;
                    int number = resultSet.getInt("number");
                    String title = resultSet.getString("title");
                    String time = resultSet.getString("time");
                    String text_board_member_nickname = resultSet.getString("text_board_member_nickname");
                    int viewCount = resultSet.getInt("view_count");
                    int text_board_suggestion = resultSet.getInt("text_board_suggestion");

                    // 가져온 결과를 출력
                    System.out.println("[게시판 번호] : " + number);
                    System.out.println("[게시판 제목] : " + title);
                    System.out.println("[작성자] : " + text_board_member_nickname);
                    System.out.println("[시간] : " + time);
                    System.out.println("[조회수] : " + viewCount);
                    System.out.println("[추천수] : " + text_board_suggestion);
                    System.out.println("=============================================================================");
                }

                if (!foundResults) {
                    System.out.println("찾을 수 없습니다.");
                }

                // 자원 해제
                selectStatement.close();
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
    public void update(){
        Scanner scanner = new Scanner(System.in);
        // JDBC 연결 설정
        Connection connection = DatabaseConnection.getConnection();
        try{
            String selectQuery = "SELECT * FROM text_board";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과를 순회하면서 출력
            while (resultSet.next()) {
                int number = resultSet.getInt("number");
                String title = resultSet.getString("title");
                String time = resultSet.getString("time");
                int viewCount = resultSet.getInt("view_count");
                String text_board_member_nickname = resultSet.getString("text_board_member_nickname");
                int text_board_suggestion = resultSet.getInt("text_board_suggestion");

                // 가져온 결과를 출력
                System.out.println("[게시글 번호] : " + number);
                System.out.println("[작성자] : "+ text_board_member_nickname);
                System.out.println("[게시글 제목] : " + title);
                System.out.println("[시간] : " + time);
                System.out.println("[조회수] : " + viewCount);
                System.out.println("[추천수] : " + text_board_suggestion);
                System.out.println("==================");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.print("수정할 게시물 번호 : ");
        boolean foundResults = false; // 결과가 있으면 true로 설정

        try {
            int num = Integer.parseInt(scanner.nextLine());

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
                        foundResults = true;
                        if (updatedRows > 0) {
                            System.out.printf("%d번 게시물이 수정되었습니다.\n", num);
                        } else {
                            System.out.println("게시물 수정에 실패했습니다.");
                        }
                        updateStatement.close();
                    }
                    if(!foundResults){
                        System.out.println("해당 번호를 찾을 수 없습니다.");
                    }
                    resultSet.close();
                    connection.close();
                    selectStatement.close();
                    // Do not close the connection here.
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
        } catch (NumberFormatException e) {
            System.out.println("올바른 번호를 입력해 주세요.");
        }
    }
    @Override
    public void sign_in(){
        PreparedStatement idCheckStatement = null;
        System.out.print("아이디를 입력해주세요: ");
        String id = scanner.nextLine();

        Connection connection = DatabaseConnection.getConnection();
        try {
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
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
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
            idCheckStatement.close();
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
    @Override
    public String login() {
        String nickname = "";
        // JDBC 연결 설정
        Connection connection;
        try {
            connection = DatabaseConnection.getConnection();
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
            return "";
        }
        boolean logIn = false; // 로그인 성공 여부를 확인하는 변수
        do {
            System.out.print("아이디를 입력해주세요: ");
            String id = scanner.nextLine();
            System.out.print("패스워드를 입력해주세요: ");
            String pw = scanner.nextLine();
            try {
                // 아이디와 패스워드로 회원 조회
                String memberCheckQuery = "SELECT * FROM member WHERE id = ? AND password = ?";
                PreparedStatement memberCheckStatement = connection.prepareStatement(memberCheckQuery);
                memberCheckStatement.setString(1, id);
                memberCheckStatement.setString(2, pw);
                ResultSet resultSet = memberCheckStatement.executeQuery();
                if (resultSet.next()) {
                    // 로그인 성공
                    nickname = resultSet.getString("nickname"); // 닉네임을 가져옴
                    System.out.println("로그인 성공!");
                    logIn = true;
                } else {
                    // 로그인 실패: 아이디 또는 패스워드가 일치하지 않음
                    System.out.println("존재하지 않은 회원입니다.\n다시 시도해주세요.");
                }
                // ResultSet을 닫음
                resultSet.close();
            } catch (SQLException e) {
                // 발생할 수 있는 SQLException 처리
            }
        } while (!logIn); // 로그인 성공할 때까지 반복
        // 연결을 닫음
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // 닫을 때 발생할 수 있는 SQLException 처리
        }
        return nickname;
    }
    @Override
    public void logout(){
        System.out.println("로그아웃 합니다.");
    }
}
