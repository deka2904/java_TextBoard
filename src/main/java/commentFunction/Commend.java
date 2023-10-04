package commentFunction;

public class Commend {
    int id;
    String commend;
    String time;
    int board_number;
    String comment_member_nickname;
    public Commend(){

    }
    public Commend(int id, String commend, String time, int board_number, String comment_member_nickname){
        this.id = id;
        this.commend = commend;
        this.time = time;
        this.board_number = board_number;
        this.comment_member_nickname = comment_member_nickname;
    }
    public int getId() {
        return id;
    }

    public String getCommend() {
        return commend;
    }

    public String getTime() {
        return time;
    }

    public String getComment_member_nickname() {
        return comment_member_nickname;
    }

    public int getBoard_number() {
        return board_number;
    }

    public void setComment_member_nickname(String comment_member_nickname) {
        this.comment_member_nickname = comment_member_nickname;
    }

    public void setBoard_number(int board_number) {
        this.board_number = board_number;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCommend(String commend) {
        this.commend = commend;
    }

    public void setId(int id) {
        this.id = id;
    }

}
