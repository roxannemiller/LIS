import javax.swing.*;
import java.awt.*;

public class AddEditContact extends TabsBasePanel{
    public AddEditContact(DBWrapper db_conn){
        JPanel addEdit = new ContactLayout(db_conn);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        addEdit.setPreferredSize(new Dimension(1000, screenSize.height));
        add(addEdit);
    }
}
