import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotificationBoxes {
    public static JTable admin_notifs;
    public static JTable prog_test_notifs;
    public static JTable complete_test_notifs;
    public static JTable remove_test_notifs;
    private static DefaultTableModel admin_model;
    private static DefaultTableModel prog_test_model;
    private static DefaultTableModel comp_test_model;
    private static DefaultTableModel remove_test_model;

    public static void initTables(DBWrapper db_conn){
        initModels(db_conn);
        admin_notifs = new JTable(admin_model);
        prog_test_notifs = new JTable(prog_test_model);
        complete_test_notifs = new JTable(comp_test_model);
        remove_test_notifs = new JTable(remove_test_model);

        admin_notifs.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());
        prog_test_notifs.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());
        complete_test_notifs.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());
        remove_test_notifs.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());

        admin_notifs.getColumnModel().getColumn(0).setMaxWidth(170);
        prog_test_notifs.getColumnModel().getColumn(0).setMaxWidth(170);
        complete_test_notifs.getColumnModel().getColumn(0).setMaxWidth(170);
        remove_test_notifs.getColumnModel().getColumn(0).setMaxWidth(170);
    }

    private static void initModels(DBWrapper db_conn){
        admin_model = getNotifTable(db_conn);
        prog_test_model = getNotifTable(db_conn);
        comp_test_model = getNotifTable(db_conn);
        remove_test_model = getNotifTable(db_conn);
    }

    private static DefaultTableModel getNotifTable(DBWrapper db_conn){
        ArrayList<String[]> data = new ArrayList<>();
        String[] column_names = {"Finish By", "Notification"};
        String query = "select * from notifications order by critical desc, due;";
        ResultSet notifs = db_conn.getDbEntries(query);
        String[][] data_array = new String[0][2];

        try{
            while(notifs.next()) {
                String[] sample_info = new String[2];
                sample_info[0] = notifs.getDate("due") == null? "" : notifs.getDate("due").toString();
                sample_info[1] = notifs.getString("notif");

                data.add(sample_info);
            }

            data_array = new String[data.size()][2];
            data_array = data.toArray(data_array);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return new DefaultTableModel(data_array, column_names) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };  
    }

    public static void removeNotif(DBWrapper db_conn, int index){
        if(index < 0){
            return;
        }

        String notification = admin_model.getValueAt(index, 1).toString();
        String query = "delete from notifications where notif = \"" + notification + "\";";

        int success = db_conn.updateDb(query);

        if(success < 0){
            JOptionPane.showMessageDialog(null, "There was an issue updating the inventory");
        }
        else {
            JOptionPane.showMessageDialog(null, "Notification was removed");
            admin_model.removeRow(index);
            prog_test_model.removeRow(index);
            comp_test_model.removeRow(index);
            remove_test_model.removeRow(index);
            updateTablesOnDelete();
        }
    }

    private static void updateTablesOnDelete(){
        admin_model.fireTableDataChanged();
        prog_test_model.fireTableDataChanged();
        comp_test_model.fireTableDataChanged();
        remove_test_model.fireTableDataChanged();
    }

    public static void updateTablesOnAdd(DBWrapper db_conn){
        admin_model = getNotifTable(db_conn);
        prog_test_model = getNotifTable(db_conn);
        comp_test_model = getNotifTable(db_conn);
        remove_test_model = getNotifTable(db_conn);

        admin_notifs.setModel(admin_model);
        prog_test_notifs.setModel(prog_test_model);
        complete_test_notifs.setModel(comp_test_model);
        remove_test_notifs.setModel(remove_test_model);

        admin_notifs.getColumnModel().getColumn(0).setMaxWidth(170);
        prog_test_notifs.getColumnModel().getColumn(0).setMaxWidth(170);
        complete_test_notifs.getColumnModel().getColumn(0).setMaxWidth(170);
        remove_test_notifs.getColumnModel().getColumn(0).setMaxWidth(170);
    }

    private static class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
        WordWrapCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
    }
}
