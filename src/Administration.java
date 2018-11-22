import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Administration extends JPanel {
    private CardLayout layout = new CardLayout();
    private JPanel cardHolder = new JPanel(layout);

    public Administration(){
        JPanel buttonPanel = new JPanel(new GridLayout(1, 0, 1, 0));
        JPanel[] cards = {new Notifications(), new EditUsers(), new AddEditSampleType()};
        String[] cardLabels = {"Notifications", "Edit Users", "Add/Edit Sample Type"};
        //add user, delete user, manage user privileges, inventory, add/edit test type, edit test steps

        for (int i = 0; i < cards.length; i++) {
            //adds each card to the cardHolder panel with the associated name
            cardHolder.add(cardLabels[i], cards[i]);

            //creates a button corresponding to the correct panel and adds it to the button panel
            buttonPanel.add(new JButton(new ButtonAction(cardLabels[i])));
        }

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
