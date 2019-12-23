import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CurrTestBoxes {
    public static JTable prog_tests;
    public static JTable failed_tests;
    public static JTable fin_tests;
    private static DefaultTableModel prog_test_model;
    private static DefaultTableModel fail_test_model;
    private static DefaultTableModel fin_test_model;
    private static ArrayList<TType> prog;
    private static ArrayList<TType> fail;
    private static ArrayList<TType> finish;
    private static DBWrapper db_conn;

    public static void initTables(DBWrapper dbc){
        db_conn = dbc;

        prog = new ArrayList<>();
        fail = new ArrayList<>();
        finish = new ArrayList<>();

        initModels(db_conn);

        prog_tests = new JTable(prog_test_model);
        failed_tests = new JTable(prog_test_model);
        fin_tests = new JTable(fail_test_model);

        prog_tests.getColumnModel().getColumn(0).setCellRenderer(new WordWrapCellRenderer());
        failed_tests.getColumnModel().getColumn(0).setCellRenderer(new WordWrapCellRenderer());
        fin_tests.getColumnModel().getColumn(0).setCellRenderer(new WordWrapCellRenderer());

        addClickListener(prog_tests);
        addClickListener(failed_tests);
        addClickListener((fin_tests));

        prog_tests.getColumnModel().getColumn(1).setPreferredWidth(50);
        failed_tests.getColumnModel().getColumn(1).setPreferredWidth(50);
        fin_tests.getColumnModel().getColumn(1).setPreferredWidth(50);
    }

    private static void initModels(DBWrapper db_conn){
        prog_test_model = getNotifTable(db_conn, 0);
        fail_test_model = getNotifTable(db_conn, 2);
        fin_test_model = getNotifTable(db_conn, 1);
    }

    private static DefaultTableModel getNotifTable(DBWrapper db_conn, int status){
        ArrayList<String[]> data = new ArrayList<>();
        String[] column_names = {"Type", "Date Made"};
        String query = "select * from tests join testTypes where tests.type = testTypes.id " +
                " and status = " + status + " order by date_made desc;";
        ResultSet tests = db_conn.getDbEntries(query);
        String[][] data_array = new String[0][2];

        try{
            while(tests.next()) {
                String[] sample_info = new String[2];

                int id = tests.getInt("id");
                String type = tests.getString("service");
                TType t = new TType(id, type);
                if(status == 0) {
                    prog.add(t);
                }
                else if(status == 1) {
                    finish.add(t);
                } else {
                    fail.add(t);
                }

                sample_info[0] = type;
                sample_info[1] = tests.getString("date_made");

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

    public static void updateTablesOnAdd(DBWrapper db_conn){
        prog_test_model = getNotifTable(db_conn, 0);
        fail_test_model = getNotifTable(db_conn, 2);
        fin_test_model = getNotifTable(db_conn, 1);

        prog_tests.setModel(prog_test_model);
        failed_tests.setModel(fail_test_model);
        fin_tests.setModel(fin_test_model);

        prog_tests.getColumnModel().getColumn(1).setPreferredWidth(50);
        failed_tests.getColumnModel().getColumn(1).setPreferredWidth(50);
        fin_tests.getColumnModel().getColumn(1).setPreferredWidth(50);
    }

    private static void addClickListener(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable selected = (JTable)mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = selected.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && selected.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel)selected.getModel();
                    if(model == prog_test_model) {
                        new ViewTest(db_conn, prog.get(row).getID());
                    } else if(model == fin_test_model) {
                        new ViewTest(db_conn, finish.get(row).getID());
                    } else {
                        new ViewTest(db_conn, fail.get(row).getID());
                    }
                }
            }
        });
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
