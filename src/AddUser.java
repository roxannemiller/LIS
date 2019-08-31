import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUser extends JPanel implements ActionListener {
    private JTextField nameBox;
    private JTextField emailBox;
    private JComboBox privBox;
    private DBWrapper db_conn;

    public AddUser(DBWrapper db_conn){
        this.db_conn = db_conn;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(100, 165, 10, 10));

        JLabel name = new JLabel("Name");
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(name);

        nameBox = new JTextField();
        addJBox(nameBox);

        addJLabel("Email");

        emailBox = new JTextField();
        addJBox(emailBox);

        addJLabel("Privileges");

        privBox = ComboBoxes.privilegesBox();
        addJBox(privBox);

        add(Box.createRigidArea(new Dimension(0, 50)));
        JButton saveButton = new JButton("Create User");
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

    private int createUser(String name, String email, String type){
        int priv = 1;

        if(type.equals("Admin")){
            priv = 0;
        }

        String query = "insert into users (email, name, type) values (\"" +
                        email + "\", \"" + name + "\", \"" + priv + "\");";

        return db_conn.updateDb(query);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user_name = nameBox.getText();
        String email = emailBox.getText();
        String type = privBox.getSelectedItem().toString();
        int success = -1;

        if(type.equals("") || user_name.equals("") || email.equals("")){
            JOptionPane.showMessageDialog(null, "Please fill out all fields.");
        }
        else{
            success = createUser(user_name, email, type);
        }

        if(success < 1){
            JOptionPane.showMessageDialog(null, "User email must be unique, please try again.");
        }
        else{
            JOptionPane.showMessageDialog(null, "User was added.");
            ComboBoxes.addToTechnicianBoxes(email);
            nameBox.setText("");
            emailBox.setText("");
            privBox.setSelectedIndex(0);
        }
    }
}
