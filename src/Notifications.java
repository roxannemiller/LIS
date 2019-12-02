import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class Notifications extends TabsBasePanel implements ActionListener {
    private DBWrapper db_conn;
    private JTextField cdBox;
    private JCheckBox critical;
    private JTextField notifBox;
    private JTable notifs;

    public Notifications(DBWrapper db_conn){
        this.db_conn = db_conn;
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        JScrollPane currNotifs = createScrollableList("Current Notifications", 600);
        JPanel newNotif = createNewNotification();
        JButton deleteNotif = new JButton("Delete Notification");
        deleteNotif.addActionListener(new deleteNotifAction());

        setNotifLayout(layout, currNotifs, newNotif, deleteNotif);
    }

    private JScrollPane createScrollableList(String title, int width){
        notifs = NotificationBoxes.admin_notifs;
        JScrollPane pane = new JScrollPane(notifs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setPreferredSize(new Dimension(width, 600));
        pane.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEADING, TitledBorder.ABOVE_TOP));

        return pane;
    }

    private JPanel createNewNotification(){
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        p.setPreferredSize(new Dimension(600, 570));
        p.setBorder(BorderFactory.createTitledBorder(null, "New Notification", TitledBorder.LEADING, TitledBorder.ABOVE_TOP));

        JLabel completeDate = new JLabel("Date to Complete By   MM/DD/YYYY");
        addItem(p, completeDate, 0, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 25, 0, 0);

        cdBox = dateEntryField();
        cdBox.setPreferredSize(new Dimension(300, 30));
        addItem(p, cdBox, 0, 1, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.VERTICAL,0,  0, 0);

        critical = new JCheckBox("Critical");
        addItem(p, critical, 1, 1, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,0,  50, 0);

        JLabel notif = new JLabel("Notification");
        addItem(p, notif, 0, 2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 20, 0, 0);

        notifBox = new JTextField();
        notifBox.setPreferredSize(new Dimension(530, 300));
        addItem(p, notifBox, 0, 3, 0.3, 2, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.VERTICAL, 0, 0, 0);

        JButton createNotif = new JButton("Create Notification");
        createNotif.addActionListener(this);
        addItem(p, createNotif, 1, 4, 0, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE, 20, 0, 20);

        return p;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String date = cdBox.getText();
        String query = "";
        int is_critical = critical.isSelected()? 1 : 0;
        String notification = notifBox.getText();

        if(notification.isEmpty()){
            JOptionPane.showMessageDialog(this, "Notification is empty");
            return;
        }

        if(!date.equals("  /  /    ")){
            query = "insert into notifications(due, critical, notif) values (str_to_date(\"" +
                    date + "\", \"%m/%d/%Y\"), " + is_critical + ", \"" + notification + "\");";
        }
        else{
            query = "insert into notifications(critical, notif) values (" + is_critical + ", \"" +
                    notification + "\");";
        }

        int result = db_conn.updateDb(query);
        if (result < 0) {
            JOptionPane.showMessageDialog(this, "There was an issue adding the notification");
        }
        else {
            JOptionPane.showMessageDialog(this, "Notification added");
            NotificationBoxes.updateTablesOnAdd(db_conn);
        }

    }

    class deleteNotifAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            NotificationBoxes.removeNotif(db_conn, notifs.getSelectedRow());
        }
    }

    private JFormattedTextField dateEntryField() {
        JFormattedTextField f = new JFormattedTextField();
        try{
            MaskFormatter mask = new MaskFormatter("##/##/####");
            mask.setPlaceholderCharacter(' ');
            f = new JFormattedTextField(mask);
        }
        catch (ParseException e){
            JOptionPane.showMessageDialog(this, "Enter date in valid format");
        }

        return f;
    }

    private void addItem(JPanel p, JComponent c, int x, int y, double wy, int gw, int anc, int fill, int top, int left, int bottom){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = fill;
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = gw;
        constraints.weightx = 0;
        constraints.weighty = wy;
        constraints.anchor = anc;
        constraints.insets = new Insets(top, left, bottom, 0);
        p.add(c, constraints);
    }

    private void setNotifLayout(GroupLayout layout, JScrollPane currNotifs, JPanel newNotif, JButton deleteNotif){
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(5, 100, 1000)
                        .addComponent(currNotifs)
                        .addGap(5, 100, 1000)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, true)
                            .addComponent(newNotif)
                            .addGap(5, 30, 1000)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 20, 500)
                                .addComponent(deleteNotif)
                                .addGap(5, 10, 500))
                            .addGap(5, 20, 500))
                        .addContainerGap(100, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(5, 90, 1000)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, true)
                                .addComponent(currNotifs)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(newNotif)
                                    .addGap(5, 20, 1000)
                                    .addComponent(deleteNotif)
                                    .addGap(5, 10, 1000)))
                        .addContainerGap(90, Short.MAX_VALUE)

        );
    }
}
