import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Overview extends JPanel{
    //holds the overview cards
    private CardLayout layout = new CardLayout();
    private JPanel cardHolder = new JPanel(layout);

    public Overview(){
        //holds the buttons that swap between the cards
        JPanel buttonPanel = new JPanel(new GridLayout(1, 0, 1, 0));
        JPanel[] cards = {new ToDoTab(), new InProgressTab(), new CompletedTab(), new FailedTab()};
        String[] cardLabels = {"To-Do", "In Progress", "Completed", "Failed"};

        for(int i = 0; i < 4; i++){
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
