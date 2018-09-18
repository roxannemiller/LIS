import javax.swing.*;
import java.awt.*;

public class MainTabs extends JPanel {
    public MainTabs(){
        setLayout(new GridLayout());
        setBackground(Color.black);
        add(Tabs());
    }

    private JTabbedPane Tabs(){
        JTabbedPane t = new JTabbedPane();
        JTabbedPane home_tab = new Home();
        t.add("Overview", home_tab);
        return t;
    }

    public static void main(String args[]) {
        JFrame frame = new Frame("Overview");
        JPanel tabs = new MainTabs();
        frame.getContentPane().add(tabs);
        frame.pack();
        frame.setVisible(true);
    }
}
