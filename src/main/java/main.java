import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("입력 : ");
            String func = scanner.nextLine();

            if(func.equals("exit")){
                System.out.println("프로그램을 종료합니다.");
                break;
            }
        }
    }
}
