import javax.swing.*;

public class CompletedTab extends HomeLayout {
    private JScrollPane completedList;

    public CompletedTab(){
        completedList = createScrollableList("Completed Tests", 700);
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        setHomeLayout(layout, completedList);
    }

    //has createScrollableList

    public JScrollPane getCompletedlist(){
        return completedList;
    }
}