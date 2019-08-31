import javax.swing.*;
import java.awt.*;

public class EditUsers extends TabsBasePanel {
    private DBWrapper db_conn;

    public EditUsers(DBWrapper db_conn){
        this.db_conn = db_conn;
        JPanel editUsers = new EditUserLayout(db_conn);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        editUsers.setPreferredSize(new Dimension(1000, screenSize.height));
        add(editUsers);
    }
}
