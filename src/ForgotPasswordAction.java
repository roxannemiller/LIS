import javax.swing.*;
import java.awt.event.*;

public class ForgotPasswordAction extends AbstractAction {
    public ForgotPasswordAction(String actionText){
        super(actionText);
    }

    public void actionPerformed(ActionEvent e){
        System.out.println("Sorry to hear you forgot your password!");
    }
}
