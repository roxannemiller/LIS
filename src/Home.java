import javax.swing.*;

public class Home extends JTabbedPane{
    //holds the overview sub-tabs
    public Home(){
        JPanel to_do_tab = new ToDoTab();
        add("To-Do", to_do_tab);

        JPanel in_progress_tab = new InProgressTab();
        add("In Progress", in_progress_tab);

        JPanel completed_tab = new CompletedTab();
        add("Completed", completed_tab);

        JPanel failed_tab = new FailedTab();
        add("Failed", failed_tab);

        setTabPlacement(JTabbedPane.BOTTOM);

    }
}
