import javax.swing.*;

public class NewSampleType extends SampleType{
    public NewSampleType(DBWrapper db_conn){
        super(db_conn, 1);
    }

    protected JComponent getSampleTypeBox(DBWrapper db_conn){
        return new JTextField();
    }
}
