package Function;

public interface Action {
    void add(String nickname);
    void delete();
    void detail(String nickname);
    void list();
    void search();
    void update();
    void sign_in();
    String login();
    void logout();
}
