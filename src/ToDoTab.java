import javax.swing.*;

public class ToDoTab extends HomeLayout {
    private JScrollPane to_do_list;

    public ToDoTab(){
        to_do_list = createScrollableList("To-Do", 700);
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        setHomeLayout(layout, to_do_list);
    }

    //has createScrollableList

    public JScrollPane getToDoList(){
        return to_do_list;
    }
}

