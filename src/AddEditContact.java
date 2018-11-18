import javax.swing.*;
import java.awt.*;

public class AddEditContact extends TabsBasePanel{
    public AddEditContact(){
        JPanel addEdit = new ContactLayout();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        addEdit.setPreferredSize(new Dimension(1000, screenSize.height));
        add(addEdit);
    }
}
