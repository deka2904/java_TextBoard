package Function;

import SQL.DatabaseConnection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Main_textboard {
    static final ArrayList<Article> boardList = new ArrayList<>();
    static int number = 1;
    public static Connection connection = DatabaseConnection.getConnection();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // sign 클래스 호출
        Sign sign = new Sign();
        // JDBC 연결 설정
        Connection connection;
        System.out.println("[[----------게시판----------]]");

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
                        Board_Add add = new Board_Add();
                        add.Add();
                    }
                    // 게시글 전체 제목 조회
                    else if (func.equals("list")) {
                        Board_List board_list = new Board_List();
                        board_list.list();
                    }
                    // 게시글 업데이트
                    else if (func.equals("update")) {
                        Board_Update board_update = new Board_Update();
                        board_update.update();
                    }
                    // 게시글 삭제
                    else if (func.equals("delete")) {
                        Board_Delete board_delete = new Board_Delete();
                        board_delete.delete();
                    }
                    // 게시글 제목으로 조회
                    else if (func.equals("detail")) {
                       Board_Detail board_detail = new Board_Detail();
                       board_detail.detail();
                    }
                    // 게시글 키워드 검색 후 조회
                    else if (func.equals("search")) {
                        Board_Search board_search = new Board_Search();
                        board_search.search();
                    }
                    // 종료
                    else if (func.equals("logout")) {
                        sign.logout();
                        break;
                    }
                }
            }
        }
    }
}
