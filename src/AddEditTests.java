import javax.swing.*;
import java.awt.*;

public class AddEditTests extends TabsBasePanel {
    public AddEditTests(DBWrapper db_conn){
        JPanel addEdit = new TestsLayout(db_conn);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        addEdit.setPreferredSize(new Dimension(1000, screenSize.height));
        add(addEdit);

    }
}
