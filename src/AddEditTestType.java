import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddEditTestType extends JPanel {
    public AddEditTestType(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(90, 165, 10, 10));

        addJLabel("Test Type", 0);

        JComboBox typeBox = ComboBoxes.testTypeBox();
        addJBox(typeBox);

        addJLabel("New Test Type", 50);

        JTextField newTypeBox = new JTextField();
        addJBox(newTypeBox);
        addJLabel("Sub-type", 50);

        JTextField subTypeBox = new JTextField();
        addJBox(subTypeBox);

        addJLabel("Add Nested Subtype", 0);
        addJLabel("Add Another Subtype", 0);

        add(Box.createRigidArea(new Dimension(0, 60)));
        JButton saveButton = new JButton("Save Changes");
        add(saveButton);
    }

    private void addJLabel(String label_text, int top_spacing){
        JLabel label = new JLabel(label_text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(top_spacing, 0, 0, 0));
        add(label);
    }

    private void addJBox(JComponent box){
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.setMaximumSize(new Dimension(300, 26));
        add(box);
    }
}
