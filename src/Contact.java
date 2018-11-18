import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

abstract class Contact extends JPanel {
    public Contact(){
        String btnTxt;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(90, 165, 10, 10));

        if(editContact()){
            btnTxt = "Save Changes";
            JLabel contact = new JLabel("Contact Name");
            contact.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(contact);

            JComboBox cBox = new JComboBox();
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
        nameBox.setMaximumSize(new Dimension(300, 26));
        add(nameBox);

        addJLabel("Number");

        JTextField numberBox = new JTextField();
        numberBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        numberBox.setMaximumSize(new Dimension(300, 26));
        add(numberBox);

        addJLabel("Affiliation");

        JTextField affilBox = new JTextField();
        affilBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        affilBox.setMaximumSize(new Dimension(300, 26));
        add(affilBox);

        addJLabel("Address");

        JTextField addrBox = new JTextField();
        addrBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        addrBox.setMaximumSize(new Dimension(300, 26));
        add(addrBox);

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

    abstract protected boolean editContact();
}
