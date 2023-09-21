package commentFunction;

import com.mysql.cj.exceptions.CJException;

import java.util.List;
import java.util.Scanner;

public class CommendController {
    CommendQueryManager commendQueryManager;
    CommendPrint commendPrint;
    Scanner scanner = new Scanner(System.in);
    public CommendController(){
        commendQueryManager = new CommendQueryManager();
        commendPrint = new CommendPrint();
    }
    public List<Commend> Commendlist(int number) {
        List<Commend> CommendList = commendQueryManager.getAllCommend(number);
        commendPrint.commendPrint(CommendList);
        return CommendList;
    }
    public int CommendAdd(int number, String nickname){
        // 댓글 입력 받기
        System.out.print("댓글 내용을 입력하세요: ");
        String commentText = scanner.nextLine();

        int insertCommerd = commendQueryManager.insertComment(commentText, number, nickname);
        if (insertCommerd > 0) {
            System.out.println("댓글이 등록되었습니다.");
        } else {
            System.out.println("댓글 등록에 실패했습니다.");
        }
        return insertCommerd;
    }
    public void ReComment(int board_number, String nickname){
        int CheckRecomment = commendQueryManager.CheckRecomment(board_number, nickname);
        if(CheckRecomment == 0){
            int insertRecomment = commendQueryManager.insertRecomment(board_number, nickname, true);
            if (insertRecomment > 0) {
                commendQueryManager.Recomment(board_number);
                System.out.println("추천 등록이 완료되었습니다.");
            } else {
                System.out.println("추천할 수 없습니다.");
            }
        }else{
            System.out.println("이미 추천한 게시글입니다.");
        }
    }
    public int CommentUodate(String nickname) {
        System.out.print("수정할 댓글 번호를 입력하세요 : ");
        int comment_number = Integer.parseInt(scanner.nextLine());
        System.out.print("수정하실 댓글을 입력해주세요 : ");
        String newComment = scanner.nextLine();
        int updateCommerd = commendQueryManager.updateComment(newComment, comment_number, nickname);
        if (updateCommerd > 0) {
            System.out.println("댓글이 수정되었습니다.");
        } else {
            System.out.println("댓글 수정에 실패했습니다.");
        }
        return updateCommerd;
    }
    public int CommentDelete(String nickname){
        System.out.print("삭제할 댓글 번호를 입력하세요 : ");
        int comment_number = Integer.parseInt(scanner.nextLine());
        int deleteCommerd = commendQueryManager.deleteComment(comment_number, nickname);
        if (deleteCommerd > 0) {
            System.out.println("댓글이 삭제되었습니다.");
        } else {
            System.out.println("댓글 삭제에 실패했습니다.");
        }
        return deleteCommerd;
    }
}
