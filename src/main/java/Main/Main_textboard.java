package Main;

import Function.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main_textboard {
    public static final int pageNumber = 1; // 원하는 페이지 번호를 지정하세요
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 인터페이스 action 호출
        Action action = new Board_Action();
        BoardController boardController = new BoardController();

        System.out.println("[[----------게시판----------]]");
        while (true) {
            System.out.println("[1. 회원가입 2. 로그인 3. 종료]");
            System.out.print("해당 번호를 입력하세요: ");
            int menuChoice;
            try {
                menuChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("올바른 번호를 입력하세요.");
                continue;
            }

            String nickname = "";
            switch (menuChoice) {
                case 1:
                    action.sign_in();
                    continue;
                case 2:
                    nickname = action.login();
                    break;
                case 3:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("올바른 메뉴를 선택하세요.");
                    continue;
            }

            if (nickname.equals(nickname)) {
                while (true) {
                    // 로그인 성공
                    System.out.println("=============================================================================");
                    System.out.print("명령어(" + nickname + "): ");
                    String func = scanner.nextLine();
                    System.out.println("=============================================================================");
                    // 게시글 추가
                    if (func.equals("add")) {
                        action.add(nickname);
                    }
                    // 게시글 전체 제목 조회
                    else if (func.equals("list")) {
//                        action.list();
                        boardController.list();
                    }
                    // 게시글 정렬 후 조회
                    else if (func.equals("sort")) {
                        action.sort();
                    }
                    // 게시글 업데이트
                    else if (func.equals("update")) {
                        action.update();
                    }
                    // 게시글 삭제
                    else if (func.equals("delete")) {
                        action.delete();
                    }
                    // 게시글 제목으로 조회
                    else if (func.equals("detail")) {
//                        action.detail(nickname);
                        boardController.detail();
                    }
                    // 게시글 키워드 검색 후 조회
                    else if (func.equals("search")) {
//                        action.search();
                        System.out.print("검색 키워드를 입력해주세요: ");
                        String keyword = scanner.nextLine();
                        boardController.search(keyword);
                    }
                    // 게시글 페이지 만들기
                    else if (func.equals("page")) {
                        action.page(pageNumber);
                    }
                    // 종료
                    else if (func.equals("logout")) {
                        action.logout();
                        break;
                    }
                }
            }
        }
    }
}
