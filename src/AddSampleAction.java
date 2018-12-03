import javax.swing.*;
import java.awt.event.*;

public class AddSampleAction extends AbstractAction {
    public AddSampleAction(String actionText){
        super(actionText);
    }

    public void actionPerformed(ActionEvent e){
        System.out.println("Sorry to hear you forgot your password!");
    }
}
