import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InProgressTab extends HomeLayout {
    private JList inProgressList = new JList();
    private DBWrapper db_conn;
    private ArrayList<String[]> data;
    private SearchTableModel t_model;
    private JTable tests;

    public InProgressTab(JTable notifications_table, DBWrapper db_conn){
        super(notifications_table);
        this.db_conn = db_conn;

        tests = CurrTestBoxes.prog_tests;
//        fillTable();

        JScrollPane inProgress = new JScrollPane(tests);
//        tests.getColumnModel().getColumn(0).setCellRenderer(new WordWrapCellRenderer());
        inProgress.setBorder(BorderFactory.createTitledBorder("In Progress"));
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        setHomeLayout(layout, inProgress);
    }

//    private void initTable(){
//        String[] column_names = {"Type", "Date"};
//        data = new ArrayList<>();
//        t_model = new SearchTableModel(column_names, data);
//        tests = new JTable(t_model);
//    }

//    private void fillTable() {
//        String query = "select * from tests join testTypes where tests.type = testTypes.id and status = 0;";
//        ResultSet tests = db_conn.getDbEntries(query);
//
//        try {
//            while(tests.next()) {
//                String[] info = new String[2];
//                String service = tests.getString("service");
//                info[0] = service;
//                String date = tests.getString("date_made");
//                info[1] = date;
//
//                data.add(info);
//                t_model.insertItems(data);
//            }
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//    }

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


    //has createScrollableList

    public JList getInProgressList(){
        return inProgressList;
    }
}
