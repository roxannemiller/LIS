import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class SearchTableModel extends AbstractTableModel {
    private String[] column_names;
    private ArrayList<String[]> data;

    public SearchTableModel(String[] column_names, ArrayList<String[]> data){
        super();
        this.column_names = column_names;
        this.data = data;
    }

    public int getColumnCount() {
        return column_names.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getValueAt(int x, int y){
        return data.get(x)[y];
    }

    public String getColumnName(int column){
        return column_names[column];
    }

    public void insertItems(ArrayList<String[]> new_data){
        data = new_data;
        fireTableDataChanged();
    }
}
