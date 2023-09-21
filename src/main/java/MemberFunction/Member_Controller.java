package MemberFunction;

import java.util.Scanner;

public class Member_Controller {
    MemberQueryManager memberQueryManager = new MemberQueryManager();
    Scanner scanner = new Scanner(System.in);
    public void sign_in(){
        System.out.print("아이디를 입력해주세요: ");
        String id = scanner.nextLine();
        Member checkId = memberQueryManager.CheckId(id);
        if(checkId != null){
            System.out.println("중복된 아이디입니다.");
        }else{
            System.out.println("[[아이디를 사용할 수 있습니다.]]");
            System.out.print("패스워드를 입력해주세요: ");
            String pw1 = scanner.nextLine();
            System.out.print("입력하신 패스워드를 재확인 합니다: ");
            String pw2 = scanner.nextLine();
            System.out.print("닉네임을 입력하세요: ");
            String nickname = scanner.nextLine();
            if (pw1.equals(pw2)){
                int inerstMember = memberQueryManager.inerstMember(id, pw1, nickname);
                if (inerstMember > 0){
                    System.out.println("[[회원 가입이 완료되었습니다.]]");
                }
            }else{
                System.out.println("[[패스워드를 잘못입력하셨습니다.]]");
            }
        }
    }
    public String login() {
        String nickname = "";
        boolean logIn = false;

        do {
            System.out.print("아이디를 입력해주세요: ");
            String id = scanner.nextLine();
            System.out.print("패스워드를 입력해주세요: ");
            String pw = scanner.nextLine();

            Member login = memberQueryManager.loginMember(id, pw);
            if (login != null) {
                nickname = login.getNickname();
                System.out.println("로그인 성공");
                logIn = true;
            } else {
                System.out.println("존재하지 않은 회원입니다.");
            }
        } while (!logIn);
        return nickname;
    }

    public void logout(){
        System.out.println("로그아웃 합니다.");
    }
}
