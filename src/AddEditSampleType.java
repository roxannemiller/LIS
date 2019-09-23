import javax.swing.*;
import java.awt.*;

public class AddEditSampleType extends TabsBasePanel {
    public AddEditSampleType(DBWrapper db_conn){
        JPanel addEdit = new SampleTypeLayout(db_conn);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        addEdit.setPreferredSize(new Dimension(1000, screenSize.height));
        add(addEdit);
    }
}
