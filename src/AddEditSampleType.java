import javax.swing.*;
import java.awt.*;

public class AddEditSampleType extends TabsBasePanel {
    public AddEditSampleType(){
        JPanel addEdit = new SampleTypeLayout();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        addEdit.setPreferredSize(new Dimension(1000, screenSize.height));
        add(addEdit);
    }
}
