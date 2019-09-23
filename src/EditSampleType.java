import javax.swing.*;

public class EditSampleType extends SampleType{

    public EditSampleType(DBWrapper db_conn) {
        super(db_conn, 0);
    }

    protected JComponent getSampleTypeBox(DBWrapper db_conn){
        return ComboBoxes.getSampleTypeBox(db_conn);
    }
}

