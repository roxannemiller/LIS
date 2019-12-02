import javax.swing.*;

public class CompletedTab extends HomeLayout {
    private JList completedList = new JList();

    public CompletedTab(JTable notifications_table){
        super(notifications_table);
        JScrollPane completed = createScrollableList("Completed Tests", 700, completedList);
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        setHomeLayout(layout, completed);
    }

    //has createScrollableList

    public JList getCompletedlist(){
        return completedList;
    }
}