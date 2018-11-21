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

        t.add("Overview", new Overview());

        t.add("Search", new Search());

        t.add("Samples", new Samples());

        t.add("Services", new Services());

        t.add("Administration", new Administration());

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
