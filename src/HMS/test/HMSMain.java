package HMS.test;




import HMS.GUI.LoginWindow;
import HMS.GUI.mainTable;


public class HMSMain implements LoginWindow.LoginListenner,mainTable.LogoutListenner{

    private HMSdatabase_manager database;
    private LoginWindow windowLogin;
    private mainTable tablemain;
    
    public HMSMain(){
        this.database = new HMSdatabase_manager("localhost", "3306", "hms", "root");
        this.windowLogin = new LoginWindow(this,database);
        this.tablemain = new mainTable(this,database);
        
    }
    public void start(){
        windowLogin.setVisible(true);
    }
    
    @Override
    public void login() {
        System.out.println("LOGIN successful!");
        windowLogin.setVisible(false);
        tablemain.setVisible(true);
        tablemain.setTheme();
        tablemain.clearTextField();
        tablemain.setThemeAfterLogin();
    }
    
    @Override
    public void logout() {
        System.out.println("LOGOUT successful!");
        tablemain.setVisible(false);
        windowLogin.setVisible(true);
    }
    
    
    
    
    
    
    
    
    //<editor-fold defaultstate="collapsed" desc="------- MAIN -------">
    private static HMSMain HMSmain = null;
    public static void main(String[] args) {
        //<editor-fold defaultstate="collapsed" desc="code for set look and feel">
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.err.println("Error setting look and feel:\n\t" + ex);
        }
        
//</editor-fold>
        HMSmain = new HMSMain();
        HMSmain.start();
    }
    
//</editor-fold>

    
}
