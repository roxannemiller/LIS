import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.sql.*;

public class Login extends JPanel implements ActionListener {
    private JTextField username;
    private JPasswordField password;
    public JFrame frame;
    public DBWrapper db_conn;

    public Login(JFrame frame, DBWrapper db_conn) {
        this.frame = frame;
        this.db_conn = db_conn;
        setLayout(new GridBagLayout());
        JPanel loginPanel = LoginUI();
        add(loginPanel);
    }

    //sets the background of the screen to a specific image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image i = requestImage("images/loginbkg.png");
        g.drawImage(i, 0, 0, getWidth(), getHeight(), null);
    }

    private Image requestImage(String imagePath){
        Image image = null;

        try {
            image = ImageIO.read(new File(imagePath));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    private JPanel LoginUI(){
        JPanel loginUI = new JPanel();
        GroupLayout layout = new GroupLayout(loginUI);
        loginUI.setLayout(layout);
        loginUI.setPreferredSize(new Dimension(600, 390));
        loginUI.setBackground(Color.white);
        loginUI.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel title = new JLabel("Sign In");
        title.setFont(new Font("Dialog", Font.BOLD, 38));

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 16));

        JLabel pwdLabel = new JLabel("Password");
        pwdLabel.setFont(new Font("Dialog", Font.PLAIN, 16));

        Action forgotPwdAction = new ForgotPasswordAction("Forgot Password?");
        JButton forgotPwd = passwordButton(forgotPwdAction);

        username = new JTextField(20);
        password = new JPasswordField(15);

        JButton signIn = new JButton("Sign In");
        signIn.setActionCommand("Sign In");
        signIn.addActionListener(this);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(signIn)
                                .addGap(80, 80, 80))
                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addContainerGap(70, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(5, 135, 135)
                                                .addComponent(title))
                                        .addComponent(usernameLabel)
                                        .addComponent(username)
                                        .addComponent(pwdLabel)
                                        .addComponent(password)
                                        .addComponent(forgotPwd))
                                .addContainerGap(150, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap(40, Short.MAX_VALUE)
                        .addComponent(title)
                        .addGap(50, 50, 50)
                        .addComponent(usernameLabel)
                        .addComponent(username)
                        .addGap(35, 35, 35)
                        .addComponent(pwdLabel)
                        .addComponent(password)
                        .addComponent(forgotPwd)
                        .addGap(40, 40, 40)
                        .addComponent(signIn)
                        .addGap(40, 40, 40)
        );

        return loginUI;
    }

    private JButton passwordButton(Action btnAction){
        JButton b = new JButton(btnAction);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setMargin(new Insets(0, 0, 0, 0));
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setFont(new Font("Dialog", Font.PLAIN, 11));

        return b;
    }

    private ResultSet getUser(String username){
        String query = "select email, password from users where email = \"" + username + "\";";
        return db_conn.getDbEntries(query);
    }

    private boolean userIsValid(ResultSet db_user, String user, String pwd){
        try{
            if(db_user.next() && db_user.getString("email").equals(user) && db_user.getString("password").equals(pwd)){
                return true;
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = username.getText();
        char[] pwd = password.getPassword();
        ResultSet user_from_db = getUser(user);

        if(userIsValid(user_from_db, user, new String(pwd))) {
            frame.getContentPane().remove(this);
            frame.setTitle("Overview");
            JPanel tabs = new MainTabs(frame, db_conn);
            frame.getContentPane().add(tabs);
            frame.pack();
        }
        else {
            JOptionPane.showMessageDialog(frame, "Incorrect Username/Password Combination.\n"
                    + "                    Please Try Again.");
            password.setText("");
        }
    }
}

