import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

abstract class HomeLayout extends TabsBasePanel {
    public JScrollPane notifs;

    public HomeLayout(JTable notifications_table){
        notifs = createNotifsList("Notifications", 500, notifications_table);
    }

    public void setHomeLayout(GroupLayout layout, JScrollPane secondPane){
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(5, 100, 1000)
                        .addComponent(secondPane)
                        .addGap(5, 100, 1000)
                        .addComponent(notifs)
                        .addContainerGap(100, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(5, 90, 1000)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, true)
                                .addComponent(secondPane)
                                .addComponent(notifs))
                        .addContainerGap(90, Short.MAX_VALUE)

        );
    }

    protected JScrollPane createScrollableList(String title, int width, JList content){
        JScrollPane pane = new JScrollPane();
        pane.setViewportView(content);
        pane.setPreferredSize(new Dimension(width, 600));  //all the lists will be the same height
        pane.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEADING, TitledBorder.ABOVE_TOP));

        return pane;
    }

    private JScrollPane createNotifsList(String title, int width, JTable content){
        JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setViewportView(content);
        pane.setPreferredSize(new Dimension(width, 600));
        pane.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEADING, TitledBorder.ABOVE_TOP));

        return pane;
    }
}
