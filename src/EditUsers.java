import javax.swing.*;
import java.awt.*;

public class EditUsers extends TabsBasePanel {
    public EditUsers(){
        JPanel editUsers = new EditUserLayout();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        editUsers.setPreferredSize(new Dimension(1000, screenSize.height));
        add(editUsers);
    }
}
