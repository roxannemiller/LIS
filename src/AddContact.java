import javax.swing.*;

public class AddContact extends Contact {
    public AddContact(DBWrapper db_conn, JComboBox affil_box, JComboBox dept_box){
        super(db_conn, affil_box, dept_box, 1);
    }

    protected boolean editContact(){
        return false;
    }
}
