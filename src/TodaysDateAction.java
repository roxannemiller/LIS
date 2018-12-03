import javax.swing.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class TodaysDateAction extends AbstractAction {
    private JTextField target;

    public TodaysDateAction(String actionText, JTextField target){
        super(actionText);
        this.target = target;
    }

    public void actionPerformed(ActionEvent e){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime todaysDate = LocalDateTime.now();
        target.setText(formatter.format(todaysDate));
    }
}
