import javax.swing.*;
import java.awt.*;

public class LIS {
    public static void main(String args[]) {
        JFrame frame = new Frame("LIS Sign In");
        JPanel login = new Login(frame);
        frame.setSize(new Dimension(1505, 1142));
        frame.getContentPane().add(login);
        frame.setVisible(true);
    }
}
