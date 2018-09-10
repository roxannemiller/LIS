import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

abstract class HomeLayout extends JPanel {
    JScrollPane notifications;

    public HomeLayout(){
        notifications = createScrollableList("Notifications", 500);
    }

    public void setHomeLayout(GroupLayout layout, JScrollPane second_pane){
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(5, 100, 1000)
                        .addComponent(second_pane)
                        .addGap(5, 100, 1000)
                        .addComponent(notifications)
                        .addContainerGap(100, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(5, 90, 1000)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(second_pane)
                                .addComponent(notifications))
                        .addContainerGap(90, Short.MAX_VALUE)

        );
    }

    public JScrollPane createScrollableList(String title, int width){
        JList scrollable_list = new JList();
        JScrollPane pane = new JScrollPane();
        pane.setViewportView(scrollable_list);
        pane.setPreferredSize(new Dimension(width, 600));  //all the lists will be the same height
        pane.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEADING, TitledBorder.ABOVE_TOP));

        return pane;
    }
}
