import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

abstract class Contact extends JPanel {
    private JTextField new_affil_box = new JTextField();
    private JComboBox affil_box = new JComboBox();
    private JTextField new_dept_box = new JTextField();
    private JComboBox dept_box = new JComboBox();

    public Contact(){
        String btnTxt;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(90, 165, 10, 10));

        if(editContact()){
            btnTxt = "Save Changes";
            JLabel contact = new JLabel("Contact Name");
            contact.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(contact);

            JComboBox cBox = ComboBoxes.contactBox();
            cBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            cBox.setMaximumSize(new Dimension(300, 26));
            add(cBox);

            addJLabel("Name");
        }

        else {
            btnTxt = "Create Contact";
            JLabel cName = new JLabel("Contact Name");
            cName.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(cName);
        }

        JTextField nameBox = new JTextField();
        nameBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameBox.setMaximumSize(new Dimension(300, 30));
        add(nameBox);

        addJLabel("Email");

        JTextField numberBox = new JTextField();
        numberBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        numberBox.setMaximumSize(new Dimension(300, 30));
        add(numberBox);

        addJLabel("Affiliation");

        JPanel affil_panel = multipleControlPanel("Choose Affiliation", "New Affiliation", new_affil_box, affil_box);
        affil_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        affil_panel.setMaximumSize(new Dimension(300, 55));
        add(affil_panel);

        addJLabel("Department");

        JPanel dept_panel = multipleControlPanel("Choose Dept", "New Dept", new_dept_box, dept_box);
        dept_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dept_panel.setMaximumSize(new Dimension(300, 55));
        add(dept_panel);

        add(Box.createRigidArea(new Dimension(0, 50)));
        addSaveButton(btnTxt);
    }

    private void addJLabel(String label_text){
        JLabel label = new JLabel(label_text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(50, 0, 0, 0));
        add(label);
    }

    private void addSaveButton(String button_text){
        JButton saveButton = new JButton(button_text);
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

    abstract protected boolean editContact();

    private class ButtonAction extends AbstractAction{
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
}
