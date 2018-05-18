/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HMS.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class HMSdatabase_manager {

    private Connection connection = null;
    private Statement statement = null;

    private String host;
    private String port;
    private String database;
    private String username;
    private String password;

    public HMSdatabase_manager(String host, String port, String database, String username) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
    }
    private void printaddress(){
        System.out.println(host);
        System.out.println(port);
        System.out.println(database);
        System.out.println(username);
        System.out.println(password);
        System.out.println("-------------------------");
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean connect() {
        boolean canConnect = true;
        try {
            System.out.println("");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/"
                    + database + "?user=" + username + "&password=" + password + "&useSSL=false");
            statement = connection.createStatement();
        } catch (ClassNotFoundException ex) {
            canConnect = false;
            System.err.println(ex);
            System.err.flush();
        } catch (SQLException ex) {
            System.err.println("Error connecting to the database :\n\t" + ex);
            System.err.flush();
            canConnect = false;
        }
        return canConnect;
    }
    public boolean disconnect(){
        try{
            this.connection.close();
            return true;
        }
        catch(SQLException ex){
            System.err.println(ex);
            return false;
        }
    }
    /**
     * return current ID of position you need
     * @return 
     */
    public String getCurrentID(){
        try{
            ResultSet result = statement.executeQuery("SELECT CURRENT_USER();");
            result.next();
            return result.getString(1);
        }
        catch(SQLException ex){
            System.out.println(ex);
            System.out.println("getCurrentID() ERROR");
            return null;
        }
    }
    /**
     * สำหรับขอดูตาราง 
     * just enter SQL code to parameter. มันจะรู้เองว่าตารางที่ขอมีกี่คอลัมอะไรยังไง
     * แล้วจะ retuen model สำหรับไปใช้สร้างตาราง
     * เช่น return A ไป, ก็ให้ไปset  ชื่อตารางในGUI.setmodel(A);
     * @param sql
     * @return 
     */
    public DefaultTableModel QueryTable(String sql){
        DefaultTableModel model = new DefaultTableModel();
        ResultSet result = null;
        try{
            result = statement.executeQuery(sql);
            ResultSetMetaData meta = result.getMetaData();
            int nCol = meta.getColumnCount();
            for (int i = 0; i < nCol; i++) {
                model.addColumn(meta.getColumnLabel(i+1));
            }
            while(result.next()){
                Object[] RowData = new Object[nCol];
                for (int i = 0; i < nCol; i++) {
                    RowData[i] = result.getString(i+1);
                }
                model.addRow(RowData);
            }
        }
        catch(SQLException ex){
            System.err.println(ex);
        }
//        return BuildTableModel(result);
        return model;
    }
    /**
     * this method return Normal Table model without hard algorithm.
     * just sent the name of table you need.
     * @param tableName
     * @return 
     */
    public DefaultTableModel getNormalTableModel(String tableName){
        DefaultTableModel model = new DefaultTableModel();
        model = QueryTable("SELECT * FROM "+tableName);
        return model;
    } 
    /**
     * this method return Normal View model without hard algorithm. 
     * just sent the name of view you need. 
     * @param viewName
     * @return 
     */
    public DefaultTableModel getNormalViewingModel(String viewName){
        DefaultTableModel model = new DefaultTableModel();
        model = QueryTable("SELECT * FROM "+viewName);
        return model;
    } 
    /**
     * รับ sql เป็นพารามิเตอร์ เพื่อทำงานแบบไม่returnผลรับ เช่น พวก INSERT CREATE ALTER 
     * ทำพวก SELECT ไม่ได้ เพราะนั่นคือเราต้องการ table ออกมาดู
     * @param sql 
     */
    public void ManagerExcecute(String sql){
        try{
            statement.execute(sql);
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
   
    /**
     * เมดทอดนี้ รีเทิร์น String เป็นค่าตัวบนสุดของตารางที่ได้จาก sql 
     * ซึ่งตารางจะเป็นกี่คอลัมก็ได้ แต่มันจะรีเทิร์นค่าที่ช่อง 'บนซ้าย'
     * @param sql
     * @return 
     */
    public Object getLastSomething(String sql){
        Object last = "";
        try{
            ResultSet result = statement.executeQuery(sql);
            result.next();
            last = result.getObject(1);
        }
        catch(SQLException ex){
            System.err.println(ex);
        }
        return last;
    }

}
