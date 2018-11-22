import javax.swing.*;
import java.awt.*;

public class AddEditTests extends TabsBasePanel {
    public AddEditTests(){
        JPanel addEdit = new TestsLayout();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        addEdit.setPreferredSize(new Dimension(1000, screenSize.height));
        add(addEdit);

    }
}
