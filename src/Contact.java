import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

abstract class Contact extends JPanel implements ActionListener {
    private static final int IS_NEW = 1;
    private DBWrapper db_conn;
    private int type;
    private JTextField new_affil_box;
    private JTextField new_dept_box;
    private JTextField name_box;
    private JTextField email_box;
    private JComboBox affil_box;
    private JComboBox dept_box;
    private JComboBox c_box;

    public Contact(DBWrapper db_conn, JComboBox affil_box, JComboBox dept_box, int type){
        this.db_conn = db_conn;
        this.type = type;
        this.affil_box = affil_box;
        this.dept_box = dept_box;
        this.new_affil_box = new JTextField();
        this.new_dept_box  = new JTextField();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(90, 165, 10, 10));

        buildSelf();
    }

    private void buildSelf(){
        String btnTxt;

        if(editContact()){
            btnTxt = "Save Changes";
            JLabel contact = new JLabel("Contact Name");
            contact.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(contact);

            c_box = ComboBoxes.getContactBox(db_conn);
            getAutofillAction(c_box);

            c_box.setAlignmentX(Component.LEFT_ALIGNMENT);
            c_box.setMaximumSize(new Dimension(300, 26));
            add(c_box);

            addJLabel("Name");
        }

        else {
            btnTxt = "Create Contact";
            JLabel cName = new JLabel("Contact Name");
            cName.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(cName);
        }

        name_box = new JTextField();
        name_box.setAlignmentX(Component.LEFT_ALIGNMENT);
        name_box.setMaximumSize(new Dimension(300, 30));
        add(name_box);

        addJLabel("Email");

        email_box = new JTextField();
        email_box.setAlignmentX(Component.LEFT_ALIGNMENT);
        email_box.setMaximumSize(new Dimension(300, 30));
        add(email_box);

        addJLabel("Affiliation");
        affil_box.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                ComboBoxes.fillOrgBox(db_conn, affil_box);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });

        JPanel affil_panel = multipleControlPanel("Choose Affiliation", "New Affiliation", new_affil_box, affil_box);
        affil_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        affil_panel.setMaximumSize(new Dimension(300, 55));
        add(affil_panel);

        addJLabel("Department");
        dept_box.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
                ComboBoxes.fillDeptBox(db_conn, dept_box);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {}
        });

        JPanel dept_panel = multipleControlPanel("Choose Dept", "New Dept", new_dept_box, dept_box);
        dept_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dept_panel.setMaximumSize(new Dimension(300, 55));
        add(dept_panel);

        add(Box.createRigidArea(new Dimension(0, 50)));
        addSaveButton(btnTxt);
    }

    abstract protected boolean editContact();

    private void addJLabel(String label_text){
        JLabel label = new JLabel(label_text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(50, 0, 0, 0));
        add(label);
    }

    private void addSaveButton(String button_text){
        JButton saveButton = new JButton(button_text);
        saveButton.addActionListener(this);
        add(saveButton);
    }

    private JPanel multipleControlPanel(String b1, String b2, JTextField tf, JComboBox cb){
        CardLayout base_layout = new CardLayout();
        JPanel base_panel = new JPanel(base_layout);

        JPanel choose_affil = new JPanel();
        choose_affil.setLayout(new BoxLayout(choose_affil, BoxLayout.PAGE_AXIS));

        cb.setAlignmentX(Component.LEFT_ALIGNMENT);
        cb.setMaximumSize(new Dimension(300, 26));
        choose_affil.add(cb);

        JButton to_add = new JButton(new ButtonAction(b2, base_layout, base_panel));
        to_add.setBorderPainted(false);
        choose_affil.add(to_add);

        JPanel new_affil = new JPanel();
        new_affil.setLayout(new BoxLayout(new_affil, BoxLayout.PAGE_AXIS));

        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        tf.setMaximumSize(new Dimension(300, 26));
        new_affil.add(tf);

        JButton to_choose = new JButton(new ButtonAction(b1, base_layout, base_panel));
        to_choose.setBorderPainted(false);
        new_affil.add(to_choose);

        base_panel.add(b1, choose_affil);
        base_panel.add(b2, new_affil);

        return base_panel;
    }

    private void getAutofillAction(JComboBox box){
        box.addActionListener(event -> {
            JComboBox source = (JComboBox) event.getSource();
            String selected_name = source.getSelectedItem().toString();
            String query = "select name, organization, department, email from people where name = \"" +
                    selected_name + "\";";

            if(!selected_name.equals("")){
                ResultSet contact_info = db_conn.getDbEntries(query);
                try{
                    contact_info.next();
                    email_box.setText(contact_info.getString("email") == null? "" : contact_info.getString("email"));
                    name_box.setText(contact_info.getString("name"));
                    affil_box.setSelectedItem(contact_info.getString("organization") == null ? "" : contact_info.getString("organization"));
                    dept_box.setSelectedItem(contact_info.getString("department") == null? "" : contact_info.getString("department"));
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    private class ButtonAction extends AbstractAction {
        CardLayout layout;
        JPanel panel;

        public ButtonAction(String buttonLabel, CardLayout layout, JPanel panel){
            super(buttonLabel);
            this.layout = layout;
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            layout.show(panel, e.getActionCommand());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String affiliation = "";
        String department = "";
        String name = "";

        if(new_affil_box.getText().equals("")){
            affiliation = affil_box.getSelectedItem() == null ? "" : affil_box.getSelectedItem().toString();
        }
        else{
            affiliation = new_affil_box.getText();
        }

        if(new_dept_box.getText().equals("")){
            department = dept_box.getSelectedItem() == null ? "" : dept_box.getSelectedItem().toString();
        }
        else {
            department = new_dept_box.getText();
        }

        if(type == IS_NEW){
            name = name_box.getText();

            if(name.equals("")){
                JOptionPane.showMessageDialog(null, "Name field required.");
            }
            else{
                newContact(name, affiliation, department);
            }
        }
        else{
            name = c_box.getSelectedItem() == null ? "" : c_box.getSelectedItem().toString();
            updateContact(name, affiliation, department);
        }
    }

    private void newContact(String name, String org, String department){
        String email = email_box.getText().equals("") ? null : "\"" + email_box.getText() + "\"";
        String org_s = org.equals("") ? null : "\"" + org + "\"";
        String department_s = department.equals("") ? null : "\"" + department + "\"";

        String query = "insert into people (organization, department, name, email) values (" +
                org_s + ", " + department_s + ", \"" + name + "\", " + email + ");";

        int success = db_conn.updateDb(query);
        if(success < 1){
            JOptionPane.showMessageDialog(null, "There was an issue adding the contact.");
        }
        else{
            JOptionPane.showMessageDialog(null, "User was added.");
            resetBoxes();
        }
    }

    private void updateContact(String name, String org, String department){
        String email = email_box.getText().equals("") ? null : "\"" + email_box.getText() + "\"";
        String new_name = name_box.getText().equals("") ? name : name_box.getText();
        String org_s = org.equals("") ? null : "\"" + org + "\"";
        String department_s = department.equals("") ? null : "\"" + department + "\"";

        String query = "update people set organization = " + org_s + ", department = " + department_s +
                ", email = " + email + ", name = \"" + new_name + "\" where name = \"" + name + "\";";

        int success = db_conn.updateDb(query);
        if(success < 1){
            JOptionPane.showMessageDialog(null, "Contact Name must be present and unique.");
        }
        else{
            JOptionPane.showMessageDialog(null, "User was updated.");
            resetBoxes();
        }
    }

    private void resetBoxes(){
        name_box.setText("");
        new_affil_box.setText("");
        new_dept_box.setText("");
        email_box.setText("");
        affil_box.setSelectedIndex(0);
        dept_box.setSelectedIndex(0);
        c_box.setSelectedIndex(0);
    }
}
