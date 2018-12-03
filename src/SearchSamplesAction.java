import javax.swing.*;
import java.awt.event.*;

public class SearchSamplesAction extends AbstractAction {
    public SearchSamplesAction(String actionText){
        super(actionText);
    }

    public void actionPerformed(ActionEvent e){
        System.out.println("Sorry to hear you forgot your password!");
    }
}
