package MemberFunction;

public class MemberQueryManager {
    Member_Action member_action = new Member_Action();
    public Member CheckId(String id){
        String sql = "SELECT * FROM member WHERE id = ?";
        Member checkid = member_action.checkMenber(sql, id);
        return checkid;
    }
    public Member loginMember(String id, String pw){
        String sql = "SELECT * FROM member WHERE id = ? AND password = ?";
        Member loginMember = member_action.checkMenber(sql, id, pw);
        return loginMember;
    }
    public int inerstMember(String id, String pw, String nickname) {
        String sql = "INSERT INTO member (id, password, nickname) VALUES (?, ?, ?)";
        int insertMember = member_action.member(sql, id, pw, nickname);
        return insertMember;
    }
}
