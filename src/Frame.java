import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame(String title) {
        super(title);
        setSize(1500, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
    }

}
