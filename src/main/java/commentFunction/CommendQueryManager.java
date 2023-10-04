package commentFunction;

import java.util.List;

public class CommendQueryManager {
    Commend_Action commend_action = new Commend_Action();
    // 해당 게시글 닉네임으로 댓글 조회
    public List<Commend> getAllCommend(int number) {
        String sql = "SELECT *  FROM text_board LEFT JOIN text_board_comment ON text_board.number = text_board_comment.board_number WHERE text_board.number = " + number;
        List<Commend> articleList = commend_action.selectList(sql);

        return articleList;
    }

    public int insertComment(String comment, int board_number, String nickname){
        String sql = "INSERT INTO text_board_comment (comment, comment_time, board_number, comment_member_nickname) VALUES (?, NOW(), ?, ?)";
        int insertComment = commend_action.updateComment(sql, comment, board_number, nickname);
        return insertComment;
    }

    public int updateComment(String comment, int comment_number, String nickname){
        String sql = "UPDATE text_board_comment SET comment = ?, comment_time = NOW() WHERE id = ? AND comment_member_nickname = ?";
        int updateComment = commend_action.updateComment(sql, comment, comment_number, nickname);
        return updateComment;
    }
    public int deleteComment(int comment_number, String nickname){
        String sql = "DELETE FROM text_board_comment WHERE id = ? and comment_member_nickname = ?";
        int deleteComment = commend_action.updateComment(sql, comment_number, nickname);
        return deleteComment;
    }
    public int Recomment(int number){
        String sql = "UPDATE text_board SET text_board_suggestion = text_board_suggestion + 1 WHERE number = ?";
        int Recomment = commend_action.updateComment(sql, number);
        return Recomment;
    }
    public int insertRecomment(int board_number, String nickname, boolean check_suggestion){
        String sql = "INSERT INTO text_board_suggestion (board_number, suggestion_member_nickname, check_suggestion) VALUES (?, ?, ?)";
        int insertRecomment = commend_action.updateComment(sql, board_number, nickname, check_suggestion);
        return insertRecomment;
    }
    public int CheckRecomment(int board_number, String nickname){
        String sql = "SELECT * FROM text_board_suggestion WHERE board_number = ? AND suggestion_member_nickname = ?";
        int checkRecomment = commend_action.checkRecomment(sql, board_number, nickname);
        return checkRecomment;
    }
}
