import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddUser extends JPanel {
    public AddUser(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(100, 165, 10, 10));

        JLabel name = new JLabel("Name");
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(name);

        JTextField nameBox = new JTextField();
        addJBox(nameBox);

        addJLabel("Email");

        JTextField emailBox = new JTextField();
        addJBox(emailBox);

        addJLabel("Privileges");

        JComboBox privBox = new JComboBox();
        addJBox(privBox);

        add(Box.createRigidArea(new Dimension(0, 50)));
        JButton saveButton = new JButton("Create User");
        add(saveButton);
    }

    private void addJLabel(String label_text){
        JLabel label = new JLabel(label_text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(50, 0, 0, 0));
        add(label);
    }

    private void addJBox(JComponent box){
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.setMaximumSize(new Dimension(300, 26));
        add(box);
    }
}
