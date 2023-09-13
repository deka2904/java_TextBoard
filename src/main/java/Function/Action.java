package Function;

import java.sql.SQLException;

public interface Action {
    void add();
    void delete();
    void detail();
    void list();
    void search();
    void update();
    void sign_in();
    String login();
    void logout();
}
