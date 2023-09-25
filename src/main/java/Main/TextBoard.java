package Main;

import BoardFunction.*;
import MemberFunction.Member_Controller;

import java.util.Scanner;

public class TextBoard {
    public void start(){
        Scanner scanner = new Scanner(System.in);
        BoardController boardController = new BoardController();
        Member_Controller member_controller = new Member_Controller();
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
                    member_controller.sign_in();
                    continue;
                case 2:
                    nickname = member_controller.login();
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
                        boardController.add(nickname);
                    }
                    // 게시글 전체 제목 조회
                    else if (func.equals("list")) {
                        boardController.list();
                    }
                    // 게시글 정렬 후 조회
                    else if (func.equals("sort")) {
                        boardController.sort();
                    }
                    // 게시글 업데이트
                    else if (func.equals("update")) {
                        boardController.list();
                        boardController.update();
                    }
                    // 게시글 삭제
                    else if (func.equals("delete")) {
                        boardController.list();
                        boardController.delete();
                        boardController.list();
                    }
                    // 게시글을 상세히 조회
                    else if (func.equals("detail")) {
                        boardController.list();
                        boardController.detail(nickname);
                    }
                    // 게시글 키워드 검색 후 조회
                    else if (func.equals("search")) {
                        boardController.search();
                    }
                    // 게시글 페이지 만들기
                    else if (func.equals("page")) {
//                        action.page(pageNumber);
                        boardController.page();
                    }
                    // 종료
                    else if (func.equals("logout")) {
                        member_controller.logout();
                        break;
                    }
                }
            }
        }
    }
}
