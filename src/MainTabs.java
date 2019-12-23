import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MainTabs extends JPanel {
    JFrame frame;
    DBWrapper db_conn;

    public MainTabs(JFrame frame, DBWrapper db_conn){
        this.frame = frame;
        this.db_conn = db_conn;
        setLayout(new GridLayout());
        setBackground(Color.black);
        add(Tabs());
    }

    private JTabbedPane Tabs(){
        JTabbedPane t = new JTabbedPane();

        t.add("Overview", new Overview(db_conn));

        t.add("Search", new Search(db_conn));

        t.add("Samples", new Samples(db_conn));

        t.add("Services", new Services(db_conn));

        t.add("Administration", new Administration(db_conn));

        ChangeListener changeListener = (ChangeEvent changeEvent)->{
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                frame.setTitle(sourceTabbedPane.getTitleAt(index));
        };

        t.addChangeListener(changeListener);

        return t;
    }
}
