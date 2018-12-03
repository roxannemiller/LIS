import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DeleteUser extends JPanel {
    public DeleteUser(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(100, 165, 10, 10));

        JLabel user = new JLabel("User");
        user.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(user);

        JComboBox userBox = ComboBoxes.technicianBox();
        userBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        userBox.setMaximumSize(new Dimension(300, 26));
        add(userBox);

        add(Box.createRigidArea(new Dimension(0, 70)));
        JButton saveButton = new JButton("Delete User");
        add(saveButton);
    }
}
