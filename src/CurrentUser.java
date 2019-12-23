public class CurrentUser {
    public static String curr_user = "";
    public static int privs = -1;

    public static void setUser(String user){
        curr_user = user;
    }

    public static void setPrivs(int new_priv){
        privs = new_priv;
    }

    public static String getUser() {
        return curr_user;
    }

    public static void logout(){
        curr_user = "";
    }
}
