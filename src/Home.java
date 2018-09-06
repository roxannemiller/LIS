import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Home extends JPanel{
    public Home(){
        JScrollPane notifications = CreateScrollableList("Notifications");
        JScrollPane to_do_list = CreateScrollableList("To-Do");
        JScrollPane in_progress_list = CreateScrollableList("In Progress");
        JScrollPane completed_list = CreateScrollableList("Completed");
        JScrollPane reviewed_list = CreateScrollableList("Reviewed");
        JScrollPane failed_list = CreateScrollableList("Failed");

        JButton to_do = new JButton("To-Do");
        JButton in_progress = new JButton("In Progress");
        JButton completed = new JButton("Completed");
        JButton reviewed = new JButton("Reviewed");
        JButton failed = new JButton("Failed");

        GroupLayout login_layout = new GroupLayout(this);
        setLayout(login_layout);
    }

    public JScrollPane CreateScrollableList(String title, int width){
        JList scrollable_list = new JList();
        JScrollPane pane = new JScrollPane();
        pane.setViewportView(scrollable_list);
        pane.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEADING, TitledBorder.ABOVE_TOP));

        return pane;
    }

}
