import javax.swing.*;

public class MainTabs extends JTabbedPane {
    public MainTabs(){
        JTabbedPane home_tab = new Home();
        add("Overview", home_tab);
    }

    public static void main(String args[]) {
        JFrame frame = new Frame("Overview");
        JTabbedPane tabs = new MainTabs();
        frame.getContentPane().add(tabs);
        frame.pack();
        frame.setVisible(true);
    }
}
