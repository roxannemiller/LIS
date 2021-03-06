import javax.swing.*;

public class ToDoTab extends HomeLayout {
    private JList toDoList = new JList();

    public ToDoTab(JTable notifications_table){
        super(notifications_table);
        JScrollPane toDo = createScrollableList("To-Do", 700, toDoList);
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        setHomeLayout(layout, toDo);
    }

    //has createScrollableList

    public JList getToDoList(){
        return toDoList;
    }
}

