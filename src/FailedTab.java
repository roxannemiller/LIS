import javax.swing.*;

public class FailedTab extends HomeLayout {
    private JList failedList = new JList();

    public FailedTab(){
        JScrollPane failed = createScrollableList("Failed Tests", 700, failedList);
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        setHomeLayout(layout, failed);
    }

    //has createScrollableList

    public JList getFailedList(){
        return failedList;
    }
}