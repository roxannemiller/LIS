import javax.swing.*;

public class InProgressTab extends HomeLayout {
    private JScrollPane in_progress_list;

    public InProgressTab(){
        in_progress_list = createScrollableList("Tests In Progress", 700);
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        setHomeLayout(layout, in_progress_list);
    }

    //has createScrollableList

    public JScrollPane getInProgressList(){
        return in_progress_list;
    }
}
