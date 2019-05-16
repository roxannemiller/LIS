import javax.swing.*;

public class InProgressTab extends HomeLayout {
    private JList inProgressList = new JList();

    public InProgressTab(){
        JScrollPane inProgress = createScrollableList("Tests In Progress", 700, inProgressList);
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        setHomeLayout(layout, inProgress);
    }

    //has createScrollableList

    public JList getInProgressList(){
        return inProgressList;
    }
}
