import javax.swing.*;

public class EditContact extends Contact {
    public EditContact(DBWrapper db_conn, JComboBox affil_box, JComboBox dept_box){
        super(db_conn, affil_box, dept_box, 0);
    }

    protected boolean editContact(){
        return true;
    }
}
