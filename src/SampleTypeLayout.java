import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SampleTypeLayout extends JPanel{
    private CardLayout layout = new CardLayout();
    private JPanel cardHolder = new JPanel(layout);

    public SampleTypeLayout(DBWrapper db_conn){
        JPanel buttonPanel = new JPanel(new GridLayout(1, 0, 1, 0));
        String[] cardLabels = {"Edit Existing Sample Type", "Add New Sample Type"};

        JButton editType = new JButton(new ButtonAction(cardLabels[0]));
        editType.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));

        cardHolder.add(cardLabels[0], new EditSampleType(db_conn));
        buttonPanel.add(editType);

        JButton newType = new JButton(new ButtonAction(cardLabels[1]));
        newType.setBorderPainted(false);

        cardHolder.add(cardLabels[1], new NewSampleType(db_conn));
        buttonPanel.add(newType);

        JButton[] buttons = {editType, newType};
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
