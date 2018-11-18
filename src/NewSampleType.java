import javax.swing.*;

public class NewSampleType extends SampleType{
    public NewSampleType(){
        super();
    }

    protected JComponent getSampleTypeBox(){
        return new JTextField();
    }
}
