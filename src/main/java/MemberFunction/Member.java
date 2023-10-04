package MemberFunction;

public class Member {
    String id;
    String password;
    String nickname;
    Member(String id, String password, String nickname){
        this.id = id;
        this.password = password;
        this.nickname = nickname;
    }
    Member(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
