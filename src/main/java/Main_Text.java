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
    public static void main(String[] args) {
        ArrayList <Board> Boardlist = new ArrayList<>();
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
                Boardlist.add(new_Board);
                System.out.println("게시물이 등록되었습니다.");
                number++;
            }
            else if (func.equals("list")) {
                for (Board board : Boardlist) {
                    System.out.println("==================");
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
                    boolean found = false;
                    for (Board board : Boardlist) {
                        if (board.getNumber() == num) {
                            System.out.print("새로운 게시물 제목을 입력해주세요 : ");
                            String newTitle = scanner.nextLine();
                            System.out.print("새로운 게시물 내용을 입력해주세요 : ");
                            String newDetail = scanner.nextLine();

                            board.setTitle(newTitle);   // 수정할 게시글 제목
                            board.setDetail(newDetail); // 수정할 게시글 내용

                            System.out.printf("%d번 게시물이 수정되었습니다.\n", num);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
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
                    int index = -1;
                    for (int i = 0; i < Boardlist.size(); i++) {
                        if (Boardlist.get(i).getNumber() == num) {
                            index = i;
                            break;
                        }
                    }
                    if (index != -1) {
                        Boardlist.remove(index);
                        System.out.printf("%d번 게시물이 삭제되었습니다.\n", num);
                    } else {
                        System.out.println("없는 게시물 번호입니다.");
                    }
                    for (Board board : Boardlist) {
                        System.out.println("==================");
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
