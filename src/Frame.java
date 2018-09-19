import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
    }

    public void changeTitle(String title){
        setTitle(title);
    }

}
