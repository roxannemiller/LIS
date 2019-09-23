import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteUser extends JPanel implements ActionListener {
    private DBWrapper db_conn;
    private JComboBox userBox;

    public DeleteUser(DBWrapper db_conn){
        this.db_conn = db_conn;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(100, 165, 10, 10));

        JLabel user = new JLabel("User");
        user.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(user);

        userBox = ComboBoxes.delete_user;
        userBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        userBox.setMaximumSize(new Dimension(300, 26));
        add(userBox);

        add(Box.createRigidArea(new Dimension(0, 70)));
        JButton saveButton = new JButton("Delete User");
        saveButton.addActionListener(this);
        add(saveButton);
    }

    private int deleteUser(String user_email){
        String query = "delete from users where email = \"" + user_email + "\";";
        return db_conn.updateDb(query);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object choice = userBox.getSelectedItem();
        String user_email = choice.toString();
        int success = -1;

        if(user_email.equals("")) {
            JOptionPane.showMessageDialog(null, "Please select a user");
        }
        else {
            success = deleteUser(user_email);
        }

        if(success < 1){
            JOptionPane.showMessageDialog(null, "There was an issue deleting the user.");
        }
        else{
            JOptionPane.showMessageDialog(null, "User was deleted.");
            ComboBoxes.removeFromTechnicianBoxes(choice);
            userBox.setSelectedIndex(0);
        }
    }
}
