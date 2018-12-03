import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MainTabs extends JPanel {
    JFrame frame;

    public MainTabs(JFrame frame){
        this.frame = frame;
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

        ChangeListener changeListener = (ChangeEvent changeEvent)->{
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                frame.setTitle(sourceTabbedPane.getTitleAt(index));
        };

        t.addChangeListener(changeListener);

        return t;
    }
}
