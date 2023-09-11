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
            System.out.print("입력 : ");
            String func = scanner.nextLine();

            Board new_Board = null;
            if (func.equals("add")) {
                System.out.print("게시물 제목을 입력해주세요 : ");
                String title = scanner.nextLine();
                System.out.print("게시물 내용을 입력해주세요 : ");
                String detail = scanner.nextLine();
                new_Board = new Board(number, title, detail);
                boardList.add(new_Board);
                System.out.println("게시물이 등록되었습니다.");
                number++;
            }
            else if (func.equals("list")) {
                System.out.println("==================");
                for (Board board : boardList) {
                    System.out.println("번호 : " + board.number);
                    System.out.println("제목 : " + board.title);
                    System.out.println("내용 : " + board.detail);
                    System.out.println("==================");
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
                    for (Board board : boardList) {
                        System.out.println("번호 : " + board.number);
                        System.out.println("제목 : " + board.title);
                        System.out.println("내용 : " + board.detail);
                        System.out.println("==================");
                    }
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
