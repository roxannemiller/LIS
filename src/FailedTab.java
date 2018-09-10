import javax.swing.*;

public class FailedTab extends HomeLayout {
    private JScrollPane failed_list;

    public FailedTab(){
        failed_list = createScrollableList("Failed Tests", 700);
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        setHomeLayout(layout, failed_list);
    }

    //has createScrollableList

    public JScrollPane getFailedList(){
        return failed_list;
    }
}