import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPrivileges extends JPanel implements ActionListener {
    private JComboBox userBox;
    private JComboBox privBox;
    private DBWrapper db_conn;

    public UserPrivileges(DBWrapper db_conn){
        this.db_conn = db_conn;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(100, 165, 10, 10));

        JLabel user = new JLabel("User");
        user.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(user);

        userBox = ComboBoxes.update_privileges;
        addJBox(userBox);

        addJLabel("Privileges");

        privBox = ComboBoxes.privilegesBox();
        addJBox(privBox);

        add(Box.createRigidArea(new Dimension(0, 50)));
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(this);
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

    private int updatePrivilegeLevels(String user, String priv){
        int privilege = 1;
        if(priv.equals("Admin")){
            privilege = 0;
        }
        String query = "update users set type = " + privilege + " where email = \"" + user + "\";";
        return db_conn.updateDb(query);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object choice = userBox.getSelectedItem();
        String user_email = choice.toString();
        String new_priv = privBox.getSelectedItem().toString();
        int success = -1;

        if(user_email.equals("") || new_priv.equals("")) {
            JOptionPane.showMessageDialog(null, "Please select a user and a privilege level");
        }
        else {
            success = updatePrivilegeLevels(user_email, new_priv);
        }

        if(success < 1){
            JOptionPane.showMessageDialog(null, "There was an issue updating the privilege level.");
        }
        else{
            JOptionPane.showMessageDialog(null, "Privilege for " + user_email + " was changed to " + new_priv + ".");
            userBox.setSelectedIndex(0);
            privBox.setSelectedIndex(0);
        }
    }
}
