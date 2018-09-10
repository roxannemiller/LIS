import javax.swing.*;

public class CompletedTab extends HomeLayout {
    private JScrollPane completed_list;

    public CompletedTab(){
        completed_list = createScrollableList("Completed Tests", 700);
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        setHomeLayout(layout, completed_list);
    }

    //has createScrollableList

    public JScrollPane getCompletedlist(){
        return completed_list;
    }
}