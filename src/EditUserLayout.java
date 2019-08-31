import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditUserLayout extends JPanel {
    private DBWrapper db_conn;
    private CardLayout layout = new CardLayout();
    private JPanel cardHolder = new JPanel(layout);

    public EditUserLayout(DBWrapper db_conn){
        this.db_conn = db_conn;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 0, 1, 0));
        String[] cardLabels = {"Add User", "Delete User", "Edit User Privileges"};
        
        JButton addUser = new JButton(new ButtonAction(cardLabels[0]));
        addUser.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));

        cardHolder.add(cardLabels[0], new AddUser(db_conn));
        buttonPanel.add(addUser);

        JButton delUser = new JButton(new ButtonAction(cardLabels[1]));
        delUser.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));

        cardHolder.add(cardLabels[1], new DeleteUser(db_conn));
        buttonPanel.add(delUser);

        JButton userPrivs = new JButton(new ButtonAction(cardLabels[2]));
        userPrivs.setBorderPainted(false);

        cardHolder.add(cardLabels[2], new UserPrivileges(db_conn));
        buttonPanel.add(userPrivs);

        JButton[] buttons = {addUser, delUser, userPrivs};
        for (JButton b : buttons) {
            b.setFocusPainted(false);
            b.setMargin(new Insets(0, 0, 0, 0));
            b.setContentAreaFilled(false);
            setOpaque(false);
            buttonPanel.add(b);
        }

        buttonPanel.setBorder(new EmptyBorder(200, 10, 10, 10));
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.PAGE_START);
        add(cardHolder, BorderLayout.CENTER);
    }

    private class ButtonAction extends AbstractAction{
        public ButtonAction(String buttonLabel){
            super(buttonLabel);
        }

        @Override
        public void actionPerformed(ActionEvent e){
            layout.show(cardHolder, e.getActionCommand());
        }
    }
}
