package HMS.GUI;

import HMS.test.HMSdatabase_manager;
import java.awt.Component;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Lenovo
 */
public class mainTable extends javax.swing.JFrame {

    private LogoutListenner logoutlistenner;
    private HMSdatabase_manager database;
    private DateFormat dateFormat;
    private javax.swing.JPanel[] fuctionPanel;
    private String[] nameAllQuery;
    private String[] Search_month, Search_year;

    public mainTable(LogoutListenner logoutlistenner, HMSdatabase_manager database) {
        initComponents();
        initListener();
        this.setLocationRelativeTo(null);
        
        this.logoutlistenner = logoutlistenner;
        this.database = database;
        fuctionPanel = new javax.swing.JPanel[]{jpn_addOrder, jpn_addCustomer,
            jpn_Search, jpn_addEmployee, jpn_addHerb, jpn_addMachine};
        
        setTheme();
    }

    public void setThemeAfterLogin() {
        jtb_presentTable.setModel(database.getNormalTableModel("order_"));
        //<editor-fold defaultstate="collapsed" desc="set jcbb_Search_Warehouse">
        DefaultTableModel model = database.QueryTable("SELECT distinct warehouse_No \n" +
                "FROM hms.warehouse");
        for (int i = 0; i < model.getRowCount(); i++) {
            jcbb_Search_Warehouse.addItem((String) model.getValueAt(i, NORMAL));
        }
//</editor-fold>
        
    }
    public void setTheme() {
        //set logo
        jlb_date.setText(getCurrentDate());
        jpn_subFunc.setVisible(false);
        jlb_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HMS/resource/HMSLOGO200.png")));
        /////////////////////////////////////////////////////////////
        nameAllQuery = new String[]{
            "1) สมุนไพรตัวนี้เก็บที่ใด", "2) สมุนไพรตัวไหนที่ถูกสั่งมากที่สุดในปีและเดือนที่ต้องการ",
            "3) จำนวนสมุนไพรทั้งหมดที่มีอยู่ในคลัง", "4) จำนวนสมุนไพรที่สั่งต่อ 1 ออเดอร์", "5) ลูกค้าที่สั่งมากกว่า n ครั้งในแต่ละเดือน",
            "6) ยอดรวมออเดอร์ในแต่ละไตรมาส", "7) เครื่องจักรนี้ถูกคุมโดยพนักงานคนไหน", "8) ออเดอร์นี้พนักงานคนไหนเป็นผู้รับผิดชอบ",
            "9) พนักงานที่แปรรูปสมุนไพรเยอะที่สุด", "10) พนักงานที่รับผิดชอบออเดอร์มากที่สุด",
            "11) ยอดรวมของออเดอร์นั้นๆ", "12) ออเดอร์นี้น้ำหนักรวมเท่าไหร่", "13) ยอดรวมของแต่ละเดือนของปีนั้นๆ", "14) ยอดรวมของแต่ละปี"
        };
        Search_month = new String[]{"มกราคม","กุมภาพันธ์","มีนาคม","เมษายน","พฤษภาคม",
            "มิถุนายน","กรกฎาคม","สิงหาคม","กันยายน","ตุลาคม","พฤศจิกายน","ธันวาคม"};
        
        dateFormat = new SimpleDateFormat("yyyy");
        int curYear = Integer.parseInt(dateFormat.format(Calendar.getInstance().getTime()));
        for (int i = curYear; i > 2550; i--) {
            jcbb_Search_Year.addItem(""+i);
        }
        
        jcbb_query.setModel(new javax.swing.DefaultComboBoxModel<>(nameAllQuery));
        jcbb_Search_Month.setModel(new javax.swing.DefaultComboBoxModel<>(Search_month));
        setPaletteEnabled(getArrayComponent(0));
        ///////////////////////////////////////////////////////////
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Constant.MINILOGO_HMS)));
        
    }
    public void clearTextField() {
        jtf_AddCusAddr.setText("");
        jtf_AddCusID.setText("");
        jtf_AddCusName.setText("");
        jtf_AddEmp_ID.setText("");
        jtf_AddEmp_Name.setText("");
        jtf_AddHerb_HerbId.setText("");
        jtf_AddHerb_HerbName.setText("");
        jtf_AddHerb_HerbPrice.setText("");
        jtf_AddMaC_EmpId.setText("");
        jtf_AddMaC_MCId.setText("");
        jtf_AddOrder_cusID.setText("");
        jtf_AddOrder_empID.setText("");
        jtf_AddOrder_herbID.setText("");
        jtf_AddOrder_herbWeight.setText("");
//        jtf_AddOrder_orderID.setText("");
        jtf_Search_CusName.setText("");
        jtf_Search_EmpNo.setText("");
        jtf_Search_HerbName.setText("");
        jtf_Search_MachineNo.setText("");
        jtf_Search_OrderID.setText("");
        jtf_Search_n.setText("");
        
        setNextIDToAll();
    }

    /**
     * เป็นการเซ็ตว่าจะให้ panel ของ ฟังก์ชันไหนแสดงออกมา เนื่องจากมันซ้อนๆกันอยู่
     * @param panel 
     */
    public void selectFuctionPanel(javax.swing.JPanel panel) {
        jpn_subFunc.setVisible(true);
        for (int i = 0; i < fuctionPanel.length; i++) {
            if (fuctionPanel[i].equals(panel)) {
                fuctionPanel[i].setVisible(true);
            } else {
                fuctionPanel[i].setVisible(false);
            }
        }
    }
    public String getCurrentDate() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public String getNextID(String curID) {
        //อย่าลืมมาแก้ตัวเลขในsubstring(1,5) และ %c%4d ถ้ารหัสเป็น5หลักแล้ว

        int id = Integer.parseInt(curID.substring(1, curID.length()));
        id += 1;
        String NextID = String.format("%c%4d", curID.charAt(0), id);
        String[] copyNextID = NextID.split("");
        NextID = "";
        for (int i = 0; i < copyNextID.length; i++) {
            if (copyNextID[i].equals(" ")) {
                copyNextID[i] = "0";
            }
            NextID = NextID + copyNextID[i];
        }
        return NextID;
    }
    private void setNextID(javax.swing.JTextField jtf_tmp, String columName, String tableName){
        String numNextID = (String) database.getLastSomething("SELECT MAX("+columName+") FROM "+tableName);
        numNextID = getNextID(numNextID);
        jtf_tmp.setText(numNextID);
        jtf_tmp.setEditable(false);
    }
    public void setNextIDToAll(){
//        setNextID(jtf_AddOrder_orderID, "order_ID", "order_");
        setNextID(jtf_AddCusID, "cus_ID", "customer");
        setNextID(jtf_AddEmp_ID, "emp_ID", "employee");
        setNextID(jtf_AddHerb_HerbId, "herb_ID", "herb");
        setNextID(jtf_AddMaC_MCId, "machine_No", "machine");
    }
    
    /**
     * เมดทอดนี้ มีไว้รอรับฟังว่าเกิดการกดปุ่มอะไรไป แล้วจะให้ทำอะไรต่อ
     */
    private void initListener() {
        
        jbt_logout.addActionListener((ActionEvent) -> {
            logoutlistenner.logout();
        });
        
        //<editor-fold defaultstate="collapsed" desc="//About left all function button">

        jbt_addOrder.addActionListener((ActionEvent) -> {
            selectFuctionPanel(jpn_addOrder);
            jtb_presentTable.setModel(database.getNormalTableModel("Order_"));
            setNextIDToAll();
        });
        jbt_addCustomer.addActionListener((ActionEvent) -> {
            selectFuctionPanel(jpn_addCustomer);
            jtb_presentTable.setModel(database.getNormalTableModel("customer"));
            setNextIDToAll();
        });
        jbt_addEmployee.addActionListener((ActionEvent) -> {
            selectFuctionPanel(jpn_addEmployee);
            jtb_presentTable.setModel(database.getNormalTableModel("employee"));
            setNextIDToAll();
        });
        jbt_search.addActionListener((ActionEvent) -> {
            selectFuctionPanel(jpn_Search);
        });
        jbt_addHerb.addActionListener((ActionEvent) -> {
            selectFuctionPanel(jpn_addHerb);
            jtb_presentTable.setModel(database.getNormalTableModel("herb"));
            setNextIDToAll();
        });
        jbt_addMachine.addActionListener((ActionEvent) -> {
            selectFuctionPanel(jpn_addMachine);
            jtb_presentTable.setModel(database.getNormalTableModel("machine"));
            setNextIDToAll();
        });
        jbt_edit.addActionListener((Actionevent -> {
            editTable editT = new editTable(database);
            editT.setDefaultCloseOperation(editT.DISPOSE_ON_CLOSE);
            editT.setVisible(true);
        }));
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="//About Order function">
        
        jbt_AddOrderGetOrderId.addActionListener((ActionEvent) -> {
            String numNextOrderID = (String) database.getLastSomething("SELECT * FROM last_order");
            numNextOrderID = getNextID(numNextOrderID);
            jtf_AddOrder_orderID.setText(numNextOrderID);
        });
        jbt_AddOrderCustomerSelect.addActionListener((ActionEvent) -> {
            customerList ctmL = new customerList(database, jtf_AddOrder_cusID);
            ctmL.setDefaultCloseOperation(ctmL.DISPOSE_ON_CLOSE);
            ctmL.setVisible(true);
        });
        jbt_AddOrderEmpSelect.addActionListener((ActionEvent) -> {
            employeeList empL = new employeeList(database, jtf_AddOrder_empID);
            empL.setDefaultCloseOperation(empL.DISPOSE_ON_CLOSE);
            empL.setVisible(true);
        });
        jbt_AddOrderHerbSelect.addActionListener((ActionEvent) -> {
            herbList herbL = new herbList(database, jtf_AddOrder_herbID);
            herbL.setDefaultCloseOperation(herbL.DISPOSE_ON_CLOSE);
            herbL.setVisible(true);
        });
        jbt_AddOrderAdd.addActionListener((ActionEvent) -> {
            String Cus_ID = jtf_AddOrder_cusID.getText();
            String order_ID = jtf_AddOrder_orderID.getText();
            String emp_ID = jtf_AddOrder_empID.getText();
            String herb_ID = jtf_AddOrder_herbID.getText();
            Date order_date = Calendar.getInstance().getTime();
            int weight = Integer.parseInt(jtf_AddOrder_herbWeight.getText());
            int total = weight * ((int) database.getLastSomething(String.format("select Price \n" +
                "from herb\n" +
                "where herb_ID = '%s';", herb_ID)));

            AddNewOrder(Cus_ID, order_ID, order_date, emp_ID);
            AddNewOrderHasHerb(order_ID, herb_ID, weight, total);
            jtb_presentTable.setModel(database.getNormalTableModel("order_"));
            clearTextField();
        });
        jbt_AddOrderClear.addActionListener((ActionEvent) -> {
            clearTextField();
        });

        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="//About Customer function">
        
//        jbt_getCusID.addActionListener((ActionEvent) -> {
//            String NumNextCusID = (String) database.getLastSomething("SELECT MAX(cus_ID) FROM customer");
//            NumNextCusID = getNextID(NumNextCusID);
//            jtf_AddCusID.setText(NumNextCusID);
//        });
        jbt_AddCusAdd.addActionListener((ActionEvent) -> {
            String cusID = jtf_AddCusID.getText();
            String cusName = jtf_AddCusName.getText();
            String cusAddr = jtf_AddCusAddr.getText();
            AddNewCustomer(cusID, cusName, cusAddr);
            jtb_presentTable.setModel(database.getNormalTableModel("customer"));
            clearTextField();
        });
        jbt_AddCusClear.addActionListener((ActionEvent) -> {
            clearTextField();
        });
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="//About Employee function">
//        jbt_getEmpID.addActionListener((ActionEvent) -> {
//            String NumNextEmpID = (String) database.getLastSomething("SELECT MAX(emp_ID) FROM employee");
//            NumNextEmpID = getNextID(NumNextEmpID);
//            jtf_AddEmp_ID.setText(NumNextEmpID);
//        });
        jbt_AddEmp_Add.addActionListener((ActionEvent) -> {
            String empID = jtf_AddEmp_ID.getText();
            String empName = jtf_AddEmp_Name.getText();
            AddNewEmployee(empID, empName);
            jtb_presentTable.setModel(database.getNormalTableModel("employee"));
            clearTextField();
            jtf_AddEmp_ID.requestFocus();
        });
        jbt_AddEmp_clear.addActionListener((ActionEvent) -> {
            clearTextField();
        });
        
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="//About Herb function">
//        jbt_getHerbID.addActionListener((ActionEvent) -> {
//            String NumNextHerbID = (String) database.getLastSomething("SELECT MAX(herb_ID) FROM herb");
//            NumNextHerbID = getNextID(NumNextHerbID);
//            jtf_AddHerb_HerbId.setText(NumNextHerbID);
//        });
        jbt_AddHerb_Add.addActionListener((ActionEvent) -> {
            String herbID = jtf_AddHerb_HerbId.getText();
            String herbName = jtf_AddHerb_HerbName.getText();
            int herbPrice = Integer.parseInt(jtf_AddHerb_HerbPrice.getText());
            AddNewHerb(herbID, herbName, herbPrice);
            jtb_presentTable.setModel(database.getNormalTableModel("herb"));
            clearTextField();
        });
        jbt_AddHerb_clear.addActionListener((ActionEvent) -> {
            clearTextField();
        });
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="//About AddMachine function">
//        jbt_getMCID.addActionListener((ActionEvent) -> {
//            String NumNextMcID = (String) database.getLastSomething("SELECT MAX(machine_No) FROM machine");
//            NumNextMcID = getNextID(NumNextMcID);
//            jtf_AddMaC_MCId.setText(NumNextMcID);
//        });
        jbt_AddMaC_selectEmp.addActionListener((ActionEvent) -> {
            employeeList emp = new employeeList(database, jtf_AddMaC_EmpId);
            emp.setDefaultCloseOperation(emp.DISPOSE_ON_CLOSE);
            emp.setVisible(true);
        });
        jbt_AddMaC_Add.addActionListener((ActionEvent) -> {
            String machineID = jtf_AddMaC_MCId.getText();
            String empName = jtf_AddMaC_EmpId.getText();
            AddNewMachine(machineID, empName);
            jtb_presentTable.setModel(database.getNormalTableModel("machine"));
            clearTextField();
        });
        jbt_AddMac_Clear.addActionListener((ActionEvent) -> {
            clearTextField();
        });

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="//About Search function">
        
        jcbb_query.addActionListener((ActionEvent) -> {
            int nQuery = jcbb_query.getSelectedIndex();
            // jcbb_query.getSelectedIndex()มันให้ค่าจาก 0 ถึง n นะ
            setPaletteEnabled(getArrayComponent(nQuery));
        });
        jbt_Search_Search.addActionListener((ActionEvent) -> {
            int nQuery = jcbb_query.getSelectedIndex();
            jtb_presentTable.setModel(getTableFromQuery(nQuery));
            clearTextField();
        });
        
//</editor-fold>
                

//</editor-fold>
    }

    //<editor-fold defaultstate="collapsed" desc="//About AddNew something">
    public void AddNewMachine(String machineID, String empName) {
        database.ManagerExcecute(String.format("INSERT INTO machine"
                + "(machine_No, emp_ID) "
                + "VALUES ('%s', '%s')", machineID, empName));
    }
    public void AddNewHerb(String herbID, String herbName, int herbPrice) {
        database.ManagerExcecute(String.format("INSERT INTO herb"
                + "(herb_ID, herb_name, price) "
                + "VALUES ('%s', '%s', '%d')", herbID, herbName, herbPrice));
    }

    public void AddNewEmployee(String id, String name) {
        database.ManagerExcecute(String.format("INSERT INTO employee"
                + "(emp_ID, emp_Name) "
                + "VALUES ('%s', '%s')", id, name));
    }

    public void AddNewCustomer(String id, String name, String addr) {
        database.ManagerExcecute(String.format("INSERT INTO customer"
                + "(cus_ID, cus_Name, cus_Address) "
                + "VALUES ('%s', '%s', '%s')", id, name, addr));
        
    }
    private void AddNewOrder(String Cus_ID, String order_ID, Date order_date, String emp_ID) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        database.ManagerExcecute(String.format("INSERT INTO order_ (Cus_ID, order_ID, order_date, emp_ID) "
                + "VALUES ('%s','%s','%s','%s')",Cus_ID,order_ID, dateFormat.format(order_date),emp_ID));
        System.out.println("Add into Order_ : "+String.format("INSERT INTO order_ (Cus_ID, order_ID, order_date, emp_ID) "
                + "VALUES ('%s','%s','%s','%s')",Cus_ID,order_ID, dateFormat.format(order_date),emp_ID));
        
    }

    private void AddNewOrderHasHerb(String order_ID, String herb_ID, int weight, int total) {
        database.ManagerExcecute(String.format("INSERT INTO order_has_herb (order_ID, herb_ID, weight, total) "
                + "VALUES ('%s','%s',%d,%d)",order_ID,herb_ID,weight,total));
    }
//</editor-fold>
    
    /**
     * รับว่า query ที่ต้องการคือหมายเลขไหน
     * จากนั้นมันจะ return Array ที่เก็บ component ที่ต้องใช้งาน
     * @param nQuery
     * @return 
     */
    private Component[] getArrayComponent(int nQuery){
        Component[] cpn = null;
        if(nQuery == 0){
            cpn = new Component[]{jtf_Search_HerbName};
        }else if (nQuery == 1) {
            cpn = new Component[]{jcbb_Search_Year, jcbb_Search_Month};
        } else if (nQuery == 2) {
            cpn = new Component[]{jcbb_Search_Warehouse};
        } else if (nQuery == 3) {
            cpn = new Component[]{jtf_Search_OrderID};
        } else if (nQuery == 4) {
            cpn = new Component[]{jlb_Search_n, jtf_Search_n};
        } else if (nQuery == 5) {
            cpn = new Component[]{jcbb_Search_Year};
        } else if (nQuery == 6) {
            cpn = new Component[]{jtf_Search_MachineNo};
        } else if (nQuery == 7) {
            cpn = new Component[]{jtf_Search_OrderID};
        } else if (nQuery == 8) {
            cpn = new Component[]{jtf_Search_OrderID}; // ยังไม่ชัว
        } else if (nQuery == 9) {
            cpn = new Component[]{jcbb_Search_Year};
        } else if (nQuery == 10) {
            cpn = new Component[]{jtf_Search_OrderID};
        } else if (nQuery == 11) {
            cpn = new Component[]{jtf_Search_OrderID};
        } else if (nQuery == 12) {
            cpn = new Component[]{jcbb_Search_Year};
        } else if (nQuery == 13) {
            cpn = new Component[]{jcbb_Search_Year};
        }

        return cpn;
    }
    
    /**
     * เมดทอดนี้จะรีเทิร์น model ตารางที่ควรเป็น เพื่อให้ไปเซ็ต Table ต่อไป
     * โดยการ รับเลข case ว่าผู้ใช้เลือกคิวรีตัวไหน
     * @param nQuery 
     */
    private DefaultTableModel getTableFromQuery(int nQuery) {
        DefaultTableModel tb = null;
        if (nQuery == 0) {
            String nameHerb = jtf_Search_HerbName.getText();
            tb = database.QueryTable(""
                    + "select 	*\n"
                    + "from 	(select h.herb_ID, h.herb_name, w.order_ID, w.warehouse_No\n"
                    + "		from herb as h, warehouse as w\n"
                    + "		where h.herb_ID = w.herb_ID ) AS tmp\n"
                    + "where 	tmp.herb_name = '" + nameHerb + "';");
        } else if (nQuery == 1) {
            String year = (String) jcbb_Search_Year.getSelectedItem();
            String month = "" + (jcbb_Search_Month.getSelectedIndex() + 1);
            tb = database.QueryTable("SELECT DISTINCT\n"
                    + "    h.herb_name\n"
                    + "FROM\n"
                    + "    order_ AS o\n"
                    + "        JOIN\n"
                    + "    order_has_herb AS oh\n"
                    + "        JOIN\n"
                    + "    herb AS h\n"
                    + "WHERE\n"
                    + "    o.order_ID = oh.order_ID\n"
                    + "        && oh.herb_ID = h.herb_ID\n"
                    + "        && o.order_date BETWEEN '" + year + "-" + month + "-01' AND '" + year + "-" + month + "-31'\n"
                    + "ORDER BY (SELECT \n"
                    + "        SUM(oh.weight)\n"
                    + "    FROM\n"
                    + "        order_has_herb AS oh\n"
                    + "    WHERE\n"
                    + "        oh.herb_ID = h.herb_ID) DESC");
        } else if (nQuery == 2) {
            String herbWare = (String) jcbb_Search_Warehouse.getSelectedItem();
            tb = database.QueryTable("SELECT \n"
                    + "		warehouse_No,\n"
                    + "        COUNT(herb_ID) AS quantity\n"
                    + "    FROM\n"
                    + "        warehouse\n"
                    + "    WHERE\n"
                    + "    warehouse_No = '" + herbWare + "'"
                    + "    group by warehouse_No;");
        } else if (nQuery == 3) {
            String order = jtf_Search_OrderID.getText();
            tb = database.QueryTable("SELECT \n"
                    + "		order_ID,\n"
                    + "		count(herb_ID) as NUM_OF_HERB_IN_ORDER\n"
                    + "    FROM\n"
                    + "        order_has_herb\n"
                    + "	where\n"
                    + "		order_ID = '" + order + "'\n"
                    + "    group by order_ID;");
        } else if (nQuery == 4) {
            String n = jtf_Search_n.getText();
            tb = database.QueryTable("SELECT \n"
                    + "    cus_Name, COUNT(order_ID) as Order_quantity\n"
                    + "FROM\n"
                    + "    order_ AS o,\n"
                    + "    customer AS c\n"
                    + "WHERE\n"
                    + "    o.cus_ID = c.cus_ID\n"
                    + "GROUP BY o.cus_ID\n"
                    + "HAVING COUNT(order_ID) > " + n + "");
        } else if (nQuery == 5) {
            String year = (String) jcbb_Search_Year.getSelectedItem();
            tb = database.QueryTable("SELECT \n"
                    + "    (SELECT \n"
                    + "            COUNT(order_ID)\n"
                    + "        FROM\n"
                    + "            order_ AS o\n"
                    + "        WHERE\n"
                    + "            order_date BETWEEN '" + year + "-01-01' AND '" + year + "-03-31') AS Q1,\n"
                    + "    (SELECT \n"
                    + "            COUNT(order_ID)\n"
                    + "        FROM\n"
                    + "            order_ AS o\n"
                    + "        WHERE\n"
                    + "            order_date BETWEEN '" + year + "-04-01' AND '" + year + "-06-30') AS Q2,\n"
                    + "    (SELECT \n"
                    + "            COUNT(order_ID)\n"
                    + "        FROM\n"
                    + "            order_ AS o\n"
                    + "        WHERE\n"
                    + "            order_date BETWEEN '" + year + "-07-01' AND '" + year + "-09-30') AS Q3,\n"
                    + "    (SELECT \n"
                    + "            COUNT(order_ID)\n"
                    + "        FROM\n"
                    + "            order_ AS o\n"
                    + "        WHERE\n"
                    + "            order_date BETWEEN '" + year + "-10-01' AND '" + year + "-12-31') AS Q4");
        } else if (nQuery == 6) {
            String machineEMP = jtf_Search_MachineNo.getText();
            tb = database.QueryTable("select\n"
                    + "	machine_No,emp_Name\n"
                    + "from\n"
                    + "	machine as m,employee as e\n"
                    + "where\n"
                    + "	machine_No = '" + machineEMP + "'&&m.emp_ID = e.emp_ID;");
        } else if (nQuery == 7) {
            String orderEMP = jtf_Search_OrderID.getText();
            tb = database.QueryTable("SELECT  e.emp_Name, o.emp_ID\n"
                    + "from employee as e, order_ as o\n"
                    + "where e.emp_ID = o.emp_ID && o.order_ID='"+orderEMP+"'");
        } else if (nQuery == 8) {

            tb = database.QueryTable("SELECT \n"
                    + "        o.emp_ID,\n"
                    + "        oh.herb_ID,\n"
                    + "        (SELECT \n"
                    + "                COUNT(herb_ID)\n"
                    + "            FROM\n"
                    + "                order_has_herb AS ohh\n"
                    + "            WHERE\n"
                    + "                o.order_ID = ohh.order_ID) AS myCount\n"
                    + "    FROM\n"
                    + "        order_ AS o,\n"
                    + "        order_has_herb AS oh\n"
                    + "    WHERE\n"
                    + "        o.order_ID = oh.order_ID\n"
                    + "    ORDER BY myCount DESC");
        } else if (nQuery == 9) {
            String year = (String) jcbb_Search_Year.getSelectedItem();
            String month = "" + (jcbb_Search_Month.getSelectedIndex() + 1);
            tb = database.QueryTable("SELECT \n"
                    + "        o.emp_ID,\n"
                    + "        c.emp_Name,\n"
                    + "        (SELECT \n"
                    + "                COUNT(order_ID)\n"
                    + "            FROM\n"
                    + "                employee AS e\n"
                    + "            WHERE\n"
                    + "                o.emp_ID = e.emp_ID) AS myCount\n"
                    + "    FROM\n"
                    + "        order_ AS o,\n"
                    + "        employee AS c\n"
                    + "    WHERE\n"
                    + "        o.emp_ID = c.emp_ID\n"
                    + "        AND o.order_date BETWEEN '" + year + "-" + month + "-01' AND '" + year + "-" + month + "-31'\n"
                    + "    GROUP BY emp_ID");
        } else if (nQuery == 10) {
            String sumOrder = jtf_Search_OrderID.getText();
            tb = database.QueryTable("    SELECT \n"
                    + "		order_ID,\n"
                    + "		sum(total) as sum_of_order\n"
                    + "    FROM\n"
                    + "        order_has_herb\n"
                    + "    WHERE\n"
                    + "        order_ID = '" + sumOrder + "'\n"
                    + "    group by order_ID");
        } else if (nQuery == 11) {
            String orderID = jtf_Search_OrderID.getText();
            tb = database.QueryTable("  SELECT \n"
                    + "    order_ID, SUM(weight) \n"
                    + "FROM\n"
                    + "    order_has_herb\n"
                    + "WHERE\n"
                    + "    order_ID = '" + orderID + "'\n"
                    + "GROUP BY order_ID");
        } else if (nQuery == 12) {
            String year = (String) jcbb_Search_Year.getSelectedItem();
            tb = database.QueryTable("SELECT \n"
                    + "		( select sum(oh.total)\n"
                    + "        FROM\n"
                    + "			order_has_herb as oh,order_ as o\n"
                    + "		where \n"
                    + "			o.order_date between '" + year + "-01-01' and '" + year + "-01-31'\n"
                    + "			&& oh.order_ID = o.order_ID) AS january\n"
                    + "		,( select sum(oh.total)\n"
                    + "        FROM\n"
                    + "			order_has_herb as oh,order_ as o\n"
                    + "		where \n"
                    + "			o.order_date between '" + year + "-02-01' and '" + year + "-02-28'\n"
                    + "			&& oh.order_ID = o.order_ID) AS february\n"
                    + "            ,( select sum(oh.total)\n"
                    + "        FROM\n"
                    + "			order_has_herb as oh,order_ as o\n"
                    + "		where \n"
                    + "			o.order_date between '" + year + "-03-01' and '" + year + "-03-31'\n"
                    + "			&& oh.order_ID = o.order_ID) AS march\n"
                    + "		,( select sum(oh.total)\n"
                    + "        FROM\n"
                    + "			order_has_herb as oh,order_ as o\n"
                    + "		where \n"
                    + "			o.order_date between '" + year + "-04-01' and '" + year + "-04-30'\n"
                    + "			&& oh.order_ID = o.order_ID) AS april\n"
                    + "		,( select sum(oh.total)\n"
                    + "        FROM\n"
                    + "			order_has_herb as oh,order_ as o\n"
                    + "		where \n"
                    + "			o.order_date between '" + year + "-05-01' and '" + year + "-05-31'\n"
                    + "			&& oh.order_ID = o.order_ID) AS may\n"
                    + "		,( select sum(oh.total)\n"
                    + "        FROM\n"
                    + "			order_has_herb as oh,order_ as o\n"
                    + "		where \n"
                    + "			o.order_date between '" + year + "-06-01' and '" + year + "-06-30'\n"
                    + "			&& oh.order_ID = o.order_ID) AS june\n"
                    + "		,( select sum(oh.total)\n"
                    + "        FROM\n"
                    + "			order_has_herb as oh,order_ as o\n"
                    + "		where \n"
                    + "			o.order_date between '" + year + "-07-01' and '" + year + "-07-31'\n"
                    + "			&& oh.order_ID = o.order_ID) AS july\n"
                    + "		,( select sum(oh.total)\n"
                    + "        FROM\n"
                    + "			order_has_herb as oh,order_ as o\n"
                    + "		where \n"
                    + "			o.order_date between '" + year + "-08-01' and '" + year + "-08-31'\n"
                    + "			&& oh.order_ID = o.order_ID) AS august\n"
                    + "		,( select sum(oh.total)\n"
                    + "        FROM\n"
                    + "			order_has_herb as oh,order_ as o\n"
                    + "		where \n"
                    + "			o.order_date between '" + year + "-09-01' and '" + year + "-09-30'\n"
                    + "			&& oh.order_ID = o.order_ID) AS september\n"
                    + "		,( select sum(oh.total)\n"
                    + "        FROM\n"
                    + "			order_has_herb as oh,order_ as o\n"
                    + "		where \n"
                    + "			o.order_date between '" + year + "-10-01' and '" + year + "-10-31'\n"
                    + "			&& oh.order_ID = o.order_ID) AS october\n"
                    + "		,( select sum(oh.total)\n"
                    + "        FROM\n"
                    + "			order_has_herb as oh,order_ as o\n"
                    + "		where \n"
                    + "			o.order_date between '" + year + "-11-01' and '" + year + "-11-30'\n"
                    + "			&& oh.order_ID = o.order_ID) AS november\n"
                    + "		,( select sum(oh.total)\n"
                    + "        FROM\n"
                    + "			order_has_herb as oh,order_ as o\n"
                    + "		where \n"
                    + "			o.order_date between '" + year + "-12-01' and '" + year + "-12-31'\n"
                    + "			&& oh.order_ID = o.order_ID) AS december");
        } else if (nQuery == 13) {
            String year = (String) jcbb_Search_Year.getSelectedItem();
            tb = database.QueryTable("SELECT \n"
                    + "		sum(oh.total) as SUM_OF_YEAR\n"
                    + "    FROM\n"
                    + "        order_has_herb as oh,order_ as o\n"
                    + "    where \n"
                    + "		o.order_date between '" + year + "-01-01' and '" + year + "-12-31'\n"
                    + "		&& oh.order_ID = o.order_ID;");
        }
        return tb;
    }
    
    /**
     * เซ็ตทุก componentในfunction search ให้ใช้งานไม่ได้ ยกเว้นตัวที่อยู่ในArray parameter
     */
    private void setPaletteEnabled(Component[] ComPoNent){
        int nComponent = jpn_SearchChoice.getComponentCount();
        for (int i = 0; i < nComponent; i++) {
            jpn_SearchChoice.getComponent(i).setEnabled(false);
        }
        for (int i = 0; i < ComPoNent.length; i++) {
            ComPoNent[i].setEnabled(true);
        }
    }
    public interface LogoutListenner {
        void logout();
    }

    
    
    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jpn_mainFunc = new javax.swing.JPanel();
        jbt_logout = new javax.swing.JButton();
        jpn_logo = new javax.swing.JPanel();
        jlb_logo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jlb_date = new javax.swing.JLabel();
        jbt_addOrder = new javax.swing.JButton();
        jbt_addCustomer = new javax.swing.JButton();
        jbt_search = new javax.swing.JButton();
        jbt_addEmployee = new javax.swing.JButton();
        jbt_addHerb = new javax.swing.JButton();
        jbt_addMachine = new javax.swing.JButton();
        jbt_edit = new javax.swing.JButton();
        jpn_mainTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtb_presentTable = new javax.swing.JTable();
        jpn_subFunc = new javax.swing.JPanel();
        jpn_addOrder = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtf_AddOrder_orderID = new javax.swing.JTextField();
        jtf_AddOrder_cusID = new javax.swing.JTextField();
        jtf_AddOrder_empID = new javax.swing.JTextField();
        jtf_AddOrder_herbWeight = new javax.swing.JTextField();
        jbt_AddOrderAdd = new javax.swing.JButton();
        jbt_AddOrderClear = new javax.swing.JButton();
        jbt_AddOrderCustomerSelect = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jtf_AddOrder_herbID = new javax.swing.JTextField();
        jbt_AddOrderEmpSelect = new javax.swing.JButton();
        jbt_AddOrderHerbSelect = new javax.swing.JButton();
        jbt_AddOrderGetOrderId = new javax.swing.JButton();
        jpn_addCustomer = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jtf_AddCusID = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtf_AddCusName = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtf_AddCusAddr = new javax.swing.JTextField();
        jbt_AddCusClear = new javax.swing.JButton();
        jbt_AddCusAdd = new javax.swing.JButton();
        jpn_Search = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jcbb_query = new javax.swing.JComboBox<>();
        jpn_SearchChoice = new javax.swing.JPanel();
        jlb_Search_cusName = new javax.swing.JLabel();
        jtf_Search_CusName = new javax.swing.JTextField();
        jlb_Search_month = new javax.swing.JLabel();
        jcbb_Search_Month = new javax.swing.JComboBox<>();
        jtf_Search_EmpNo = new javax.swing.JTextField();
        jlb_Search_empNo = new javax.swing.JLabel();
        jtf_Search_OrderID = new javax.swing.JTextField();
        jlb_Search_orderId = new javax.swing.JLabel();
        jcbb_Search_Year = new javax.swing.JComboBox<>();
        jlb_Search_year = new javax.swing.JLabel();
        jtf_Search_HerbName = new javax.swing.JTextField();
        jlb_Search_herbName = new javax.swing.JLabel();
        jlb_Search_whNo = new javax.swing.JLabel();
        jlb_Search_mcNo = new javax.swing.JLabel();
        jtf_Search_MachineNo = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jlb_Search_n = new javax.swing.JLabel();
        jtf_Search_n = new javax.swing.JTextField();
        jcbb_Search_Warehouse = new javax.swing.JComboBox<>();
        jbt_Search_Search = new javax.swing.JToggleButton();
        jpn_addEmployee = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jtf_AddEmp_ID = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jtf_AddEmp_Name = new javax.swing.JTextField();
        jbt_AddEmp_clear = new javax.swing.JButton();
        jbt_AddEmp_Add = new javax.swing.JButton();
        jpn_addHerb = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jtf_AddHerb_HerbId = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jtf_AddHerb_HerbPrice = new javax.swing.JTextField();
        jbt_AddHerb_clear = new javax.swing.JButton();
        jbt_AddHerb_Add = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jtf_AddHerb_HerbName = new javax.swing.JTextField();
        jpn_addMachine = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jtf_AddMaC_MCId = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jtf_AddMaC_EmpId = new javax.swing.JTextField();
        jbt_AddMac_Clear = new javax.swing.JButton();
        jbt_AddMaC_Add = new javax.swing.JButton();
        jbt_AddMaC_selectEmp = new javax.swing.JButton();

        jMenu3.setText("jMenu3");

        jMenu4.setText("jMenu4");

        jMenu5.setText("File");
        jMenuBar2.add(jMenu5);

        jMenu6.setText("Edit");
        jMenuBar2.add(jMenu6);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main - Herb managagement system");
        setMinimumSize(new java.awt.Dimension(947, 584));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jpn_mainFunc.setBackground(new java.awt.Color(255, 255, 255));
        jpn_mainFunc.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpn_mainFunc.setMinimumSize(new java.awt.Dimension(200, 500));
        jpn_mainFunc.setPreferredSize(new java.awt.Dimension(200, 500));

        jbt_logout.setText("ออกจากระบบ");
        jbt_logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jpn_logo.setBackground(new java.awt.Color(255, 255, 255));
        jpn_logo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jlb_logo.setBackground(new java.awt.Color(255, 255, 204));
        jlb_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jpn_logoLayout = new javax.swing.GroupLayout(jpn_logo);
        jpn_logo.setLayout(jpn_logoLayout);
        jpn_logoLayout.setHorizontalGroup(
            jpn_logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlb_logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpn_logoLayout.setVerticalGroup(
            jpn_logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlb_logo, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );

        jlb_date.setBackground(new java.awt.Color(0, 153, 153));
        jlb_date.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlb_date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jbt_addOrder.setText("เพิ่มรายการ");
        jbt_addOrder.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jbt_addCustomer.setText("เพิ่มลูกค้า");
        jbt_addCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jbt_search.setText("ค้นหา");
        jbt_search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jbt_addEmployee.setText("เพิ่มพนักงาน");
        jbt_addEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jbt_addHerb.setText("เพิ่มสมุนไพร");
        jbt_addHerb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jbt_addMachine.setText("เพิ่มเครื่องจักร");
        jbt_addMachine.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jbt_edit.setText("แก้ไขข้อมูล");
        jbt_edit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jpn_mainFuncLayout = new javax.swing.GroupLayout(jpn_mainFunc);
        jpn_mainFunc.setLayout(jpn_mainFuncLayout);
        jpn_mainFuncLayout.setHorizontalGroup(
            jpn_mainFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_mainFuncLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpn_mainFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlb_date, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpn_logo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbt_logout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(jbt_addOrder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jbt_addCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jbt_search, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jbt_addEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jbt_addHerb, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jbt_addMachine, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jbt_edit, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpn_mainFuncLayout.setVerticalGroup(
            jpn_mainFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_mainFuncLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpn_logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlb_date, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jbt_addOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbt_addCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbt_addEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbt_addHerb, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbt_addMachine, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbt_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jbt_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(jpn_mainFunc, gridBagConstraints);

        jpn_mainTable.setBackground(new java.awt.Color(255, 102, 102));
        jpn_mainTable.setMinimumSize(new java.awt.Dimension(500, 250));
        jpn_mainTable.setPreferredSize(new java.awt.Dimension(500, 250));
        jpn_mainTable.setLayout(new java.awt.GridBagLayout());

        jtb_presentTable.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtb_presentTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtb_presentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Order_ID", "Herb_ID", "Herb_name", "Total", "Date_in"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtb_presentTable.setColumnSelectionAllowed(true);
        jtb_presentTable.setMinimumSize(new java.awt.Dimension(375, 64));
        jtb_presentTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtb_presentTable);
        jtb_presentTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jtb_presentTable.getColumnModel().getColumnCount() > 0) {
            jtb_presentTable.getColumnModel().getColumn(0).setResizable(false);
            jtb_presentTable.getColumnModel().getColumn(1).setResizable(false);
            jtb_presentTable.getColumnModel().getColumn(2).setResizable(false);
            jtb_presentTable.getColumnModel().getColumn(3).setResizable(false);
            jtb_presentTable.getColumnModel().getColumn(4).setResizable(false);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 597;
        gridBagConstraints.ipady = 362;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jpn_mainTable.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(jpn_mainTable, gridBagConstraints);

        jpn_subFunc.setMinimumSize(new java.awt.Dimension(192, 140));
        jpn_subFunc.setName(""); // NOI18N
        jpn_subFunc.setPreferredSize(new java.awt.Dimension(192, 200));

        jpn_addOrder.setMinimumSize(new java.awt.Dimension(250, 200));
        jpn_addOrder.setPreferredSize(new java.awt.Dimension(250, 200));
        jpn_addOrder.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "เพิ่มรายการ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14)));
        jpn_addOrder.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("รหัสรายการ:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("รหัสลูกค้า:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("รหัสสมุนไพร:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jLabel3, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("น้ำหนัก:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jLabel5, gridBagConstraints);

        jtf_AddOrder_orderID.setMinimumSize(new java.awt.Dimension(50, 23));
        jtf_AddOrder_orderID.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jtf_AddOrder_orderID, gridBagConstraints);

        jtf_AddOrder_cusID.setMinimumSize(new java.awt.Dimension(100, 20));
        jtf_AddOrder_cusID.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jtf_AddOrder_cusID, gridBagConstraints);

        jtf_AddOrder_empID.setMinimumSize(new java.awt.Dimension(100, 20));
        jtf_AddOrder_empID.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jtf_AddOrder_empID, gridBagConstraints);

        jtf_AddOrder_herbWeight.setMinimumSize(new java.awt.Dimension(100, 20));
        jtf_AddOrder_herbWeight.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jtf_AddOrder_herbWeight, gridBagConstraints);

        jbt_AddOrderAdd.setText("เพิ่ม");
        jbt_AddOrderAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jbt_AddOrderAdd, gridBagConstraints);

        jbt_AddOrderClear.setText("ล้าง");
        jbt_AddOrderClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jbt_AddOrderClear, gridBagConstraints);

        jbt_AddOrderCustomerSelect.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jbt_AddOrderCustomerSelect.setText("เลือก");
        jbt_AddOrderCustomerSelect.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbt_AddOrderCustomerSelect.setMaximumSize(new java.awt.Dimension(57, 21));
        jbt_AddOrderCustomerSelect.setMinimumSize(new java.awt.Dimension(57, 21));
        jbt_AddOrderCustomerSelect.setPreferredSize(new java.awt.Dimension(57, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jbt_AddOrderCustomerSelect, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("รหัสพนักงานดูแล:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jLabel7, gridBagConstraints);

        jtf_AddOrder_herbID.setMinimumSize(new java.awt.Dimension(100, 20));
        jtf_AddOrder_herbID.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jtf_AddOrder_herbID, gridBagConstraints);

        jbt_AddOrderEmpSelect.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jbt_AddOrderEmpSelect.setText("เลือก");
        jbt_AddOrderEmpSelect.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbt_AddOrderEmpSelect.setMaximumSize(new java.awt.Dimension(57, 21));
        jbt_AddOrderEmpSelect.setMinimumSize(new java.awt.Dimension(57, 21));
        jbt_AddOrderEmpSelect.setPreferredSize(new java.awt.Dimension(57, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jbt_AddOrderEmpSelect, gridBagConstraints);

        jbt_AddOrderHerbSelect.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jbt_AddOrderHerbSelect.setText("เลือก");
        jbt_AddOrderHerbSelect.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbt_AddOrderHerbSelect.setMaximumSize(new java.awt.Dimension(57, 21));
        jbt_AddOrderHerbSelect.setMinimumSize(new java.awt.Dimension(57, 21));
        jbt_AddOrderHerbSelect.setPreferredSize(new java.awt.Dimension(57, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jbt_AddOrderHerbSelect, gridBagConstraints);

        jbt_AddOrderGetOrderId.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jbt_AddOrderGetOrderId.setText("รับรหัส");
        jbt_AddOrderGetOrderId.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbt_AddOrderGetOrderId.setMaximumSize(new java.awt.Dimension(57, 21));
        jbt_AddOrderGetOrderId.setMinimumSize(new java.awt.Dimension(57, 21));
        jbt_AddOrderGetOrderId.setPreferredSize(new java.awt.Dimension(60, 21));
        jbt_AddOrderGetOrderId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_AddOrderGetOrderIdActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addOrder.add(jbt_AddOrderGetOrderId, gridBagConstraints);

        jpn_addCustomer.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpn_addCustomer.setMinimumSize(new java.awt.Dimension(250, 200));
        jpn_addCustomer.setPreferredSize(new java.awt.Dimension(250, 200));
        jpn_addCustomer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "เพิ่มลูกค้า", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14)));
        jpn_addCustomer.setLayout(new java.awt.GridBagLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("รหัสลูกค้า:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addCustomer.add(jLabel9, gridBagConstraints);

        jtf_AddCusID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtf_AddCusID.setMinimumSize(new java.awt.Dimension(50, 23));
        jtf_AddCusID.setPreferredSize(new java.awt.Dimension(50, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 53;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addCustomer.add(jtf_AddCusID, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("ชื่อลูกค้า:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addCustomer.add(jLabel10, gridBagConstraints);

        jtf_AddCusName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtf_AddCusName.setMinimumSize(new java.awt.Dimension(50, 23));
        jtf_AddCusName.setPreferredSize(new java.awt.Dimension(50, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 53;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addCustomer.add(jtf_AddCusName, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("ที่อยู่ลูกค้า:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addCustomer.add(jLabel11, gridBagConstraints);

        jtf_AddCusAddr.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtf_AddCusAddr.setMinimumSize(new java.awt.Dimension(50, 23));
        jtf_AddCusAddr.setPreferredSize(new java.awt.Dimension(50, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 53;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addCustomer.add(jtf_AddCusAddr, gridBagConstraints);

        jbt_AddCusClear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbt_AddCusClear.setText("ล้าง");
        jbt_AddCusClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addCustomer.add(jbt_AddCusClear, gridBagConstraints);

        jbt_AddCusAdd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbt_AddCusAdd.setText("เพิ่ม");
        jbt_AddCusAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addCustomer.add(jbt_AddCusAdd, gridBagConstraints);

        jpn_Search.setMinimumSize(new java.awt.Dimension(250, 200));
        jpn_Search.setPreferredSize(new java.awt.Dimension(250, 200));
        jpn_Search.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ค้นหา", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14)));
        jpn_Search.setLayout(new java.awt.GridBagLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("เรียกดูตาราง:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(9, 8, 3, 3);
        jpn_Search.add(jLabel6, gridBagConstraints);

        jcbb_query.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbb_query.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--please select table--" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(9, 3, 3, 3);
        jpn_Search.add(jcbb_query, gridBagConstraints);

        jpn_SearchChoice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ทางเลือกการต้นหา", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma",0,14)));
        jpn_SearchChoice.setMinimumSize(new java.awt.Dimension(474, 50));
        jpn_SearchChoice.setPreferredSize(new java.awt.Dimension(474, 50));
        jpn_SearchChoice.setLayout(new java.awt.GridBagLayout());

        jlb_Search_cusName.setText("ชื่อลูกค้า:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jlb_Search_cusName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 64;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jtf_Search_CusName, gridBagConstraints);

        jlb_Search_month.setText("เดือน:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jlb_Search_month, gridBagConstraints);

        jcbb_Search_Month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jcbb_Search_Month, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 64;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jtf_Search_EmpNo, gridBagConstraints);

        jlb_Search_empNo.setText("รหัสพนักงาน:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jlb_Search_empNo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 64;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jtf_Search_OrderID, gridBagConstraints);

        jlb_Search_orderId.setText("รหัสรายการ:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jlb_Search_orderId, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jcbb_Search_Year, gridBagConstraints);

        jlb_Search_year.setText("ปี พ.ศ.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jlb_Search_year, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 17;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 64;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jtf_Search_HerbName, gridBagConstraints);

        jlb_Search_herbName.setText("ชื่อสมุนไพร:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jlb_Search_herbName, gridBagConstraints);

        jlb_Search_whNo.setText("รหัสโรงงาน:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jlb_Search_whNo, gridBagConstraints);

        jlb_Search_mcNo.setText("รหัสเครื่องจักร:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jlb_Search_mcNo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 17;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 64;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jtf_Search_MachineNo, gridBagConstraints);

        jLabel25.setText("jLabel25");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jLabel25, gridBagConstraints);

        jlb_Search_n.setText("n:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jlb_Search_n, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 17;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 64;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jtf_Search_n, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_SearchChoice.add(jcbb_Search_Warehouse, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 106;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(16, 8, 9, 8);
        jpn_Search.add(jpn_SearchChoice, gridBagConstraints);

        jbt_Search_Search.setText("ค้นหา");
        jbt_Search_Search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 4, 3);
        jpn_Search.add(jbt_Search_Search, gridBagConstraints);

        jpn_addEmployee.setMinimumSize(new java.awt.Dimension(250, 200));
        jpn_addEmployee.setPreferredSize(new java.awt.Dimension(250, 200));
        jpn_addEmployee.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "เพิ่มพนักงาน", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14)));
        jpn_addEmployee.setLayout(new java.awt.GridBagLayout());

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("รหัสพนักงาน:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addEmployee.add(jLabel20, gridBagConstraints);

        jtf_AddEmp_ID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtf_AddEmp_ID.setMinimumSize(new java.awt.Dimension(50, 23));
        jtf_AddEmp_ID.setPreferredSize(new java.awt.Dimension(50, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 53;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addEmployee.add(jtf_AddEmp_ID, gridBagConstraints);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("ชื่อพนักงาน:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addEmployee.add(jLabel21, gridBagConstraints);

        jtf_AddEmp_Name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtf_AddEmp_Name.setMinimumSize(new java.awt.Dimension(50, 23));
        jtf_AddEmp_Name.setPreferredSize(new java.awt.Dimension(50, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 53;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addEmployee.add(jtf_AddEmp_Name, gridBagConstraints);

        jbt_AddEmp_clear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbt_AddEmp_clear.setText("ล้าง");
        jbt_AddEmp_clear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addEmployee.add(jbt_AddEmp_clear, gridBagConstraints);

        jbt_AddEmp_Add.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbt_AddEmp_Add.setText("เพิ่ม");
        jbt_AddEmp_Add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addEmployee.add(jbt_AddEmp_Add, gridBagConstraints);

        jpn_addHerb.setMinimumSize(new java.awt.Dimension(250, 200));
        jpn_addHerb.setPreferredSize(new java.awt.Dimension(250, 200));
        jpn_addHerb.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "เพิ่มสมุนไพร", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14)));
        jpn_addHerb.setLayout(new java.awt.GridBagLayout());

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("รหัสสมุนไพร:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addHerb.add(jLabel22, gridBagConstraints);

        jtf_AddHerb_HerbId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtf_AddHerb_HerbId.setMinimumSize(new java.awt.Dimension(20, 23));
        jtf_AddHerb_HerbId.setPreferredSize(new java.awt.Dimension(20, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 53;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addHerb.add(jtf_AddHerb_HerbId, gridBagConstraints);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("ราคา:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addHerb.add(jLabel23, gridBagConstraints);

        jtf_AddHerb_HerbPrice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtf_AddHerb_HerbPrice.setMinimumSize(new java.awt.Dimension(20, 23));
        jtf_AddHerb_HerbPrice.setPreferredSize(new java.awt.Dimension(20, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 53;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addHerb.add(jtf_AddHerb_HerbPrice, gridBagConstraints);

        jbt_AddHerb_clear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbt_AddHerb_clear.setText("ล้าง");
        jbt_AddHerb_clear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addHerb.add(jbt_AddHerb_clear, gridBagConstraints);

        jbt_AddHerb_Add.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbt_AddHerb_Add.setText("เพิ่ม");
        jbt_AddHerb_Add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addHerb.add(jbt_AddHerb_Add, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("ชื่อสมุนไพร:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addHerb.add(jLabel24, gridBagConstraints);

        jtf_AddHerb_HerbName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtf_AddHerb_HerbName.setMinimumSize(new java.awt.Dimension(20, 23));
        jtf_AddHerb_HerbName.setPreferredSize(new java.awt.Dimension(20, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 53;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addHerb.add(jtf_AddHerb_HerbName, gridBagConstraints);

        jpn_addMachine.setMinimumSize(new java.awt.Dimension(250, 200));
        jpn_addMachine.setPreferredSize(new java.awt.Dimension(250, 200));
        jpn_addMachine.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "เพิ่มเครื่องจักร", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14)));
        jpn_addMachine.setLayout(new java.awt.GridBagLayout());

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("รหัสเครื่องจักร:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addMachine.add(jLabel18, gridBagConstraints);

        jtf_AddMaC_MCId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtf_AddMaC_MCId.setMinimumSize(new java.awt.Dimension(20, 23));
        jtf_AddMaC_MCId.setPreferredSize(new java.awt.Dimension(20, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 53;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addMachine.add(jtf_AddMaC_MCId, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("รหัสพนักงาน:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addMachine.add(jLabel19, gridBagConstraints);

        jtf_AddMaC_EmpId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtf_AddMaC_EmpId.setMinimumSize(new java.awt.Dimension(20, 23));
        jtf_AddMaC_EmpId.setPreferredSize(new java.awt.Dimension(20, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 53;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addMachine.add(jtf_AddMaC_EmpId, gridBagConstraints);

        jbt_AddMac_Clear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbt_AddMac_Clear.setText("ล้าง");
        jbt_AddMac_Clear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbt_AddMac_Clear.setMaximumSize(new java.awt.Dimension(57, 25));
        jbt_AddMac_Clear.setMinimumSize(new java.awt.Dimension(57, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addMachine.add(jbt_AddMac_Clear, gridBagConstraints);

        jbt_AddMaC_Add.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbt_AddMaC_Add.setText("เพิ่ม");
        jbt_AddMaC_Add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addMachine.add(jbt_AddMaC_Add, gridBagConstraints);

        jbt_AddMaC_selectEmp.setText("เลือก");
        jbt_AddMaC_selectEmp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 20;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_addMachine.add(jbt_AddMaC_selectEmp, gridBagConstraints);

        javax.swing.GroupLayout jpn_subFuncLayout = new javax.swing.GroupLayout(jpn_subFunc);
        jpn_subFunc.setLayout(jpn_subFuncLayout);
        jpn_subFuncLayout.setHorizontalGroup(
            jpn_subFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpn_addOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jpn_subFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpn_addCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpn_subFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpn_Search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpn_subFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpn_addMachine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpn_subFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpn_addEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpn_subFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpn_addHerb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpn_subFuncLayout.setVerticalGroup(
            jpn_subFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_subFuncLayout.createSequentialGroup()
                .addComponent(jpn_addOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jpn_subFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpn_subFuncLayout.createSequentialGroup()
                    .addComponent(jpn_addCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jpn_subFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpn_subFuncLayout.createSequentialGroup()
                    .addComponent(jpn_Search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jpn_subFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpn_subFuncLayout.createSequentialGroup()
                    .addComponent(jpn_addMachine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jpn_subFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpn_subFuncLayout.createSequentialGroup()
                    .addComponent(jpn_addEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jpn_subFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpn_subFuncLayout.createSequentialGroup()
                    .addComponent(jpn_addHerb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(jpn_subFunc, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbt_AddOrderGetOrderIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt_AddOrderGetOrderIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbt_AddOrderGetOrderIdActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbt_AddCusAdd;
    private javax.swing.JButton jbt_AddCusClear;
    private javax.swing.JButton jbt_AddEmp_Add;
    private javax.swing.JButton jbt_AddEmp_clear;
    private javax.swing.JButton jbt_AddHerb_Add;
    private javax.swing.JButton jbt_AddHerb_clear;
    private javax.swing.JButton jbt_AddMaC_Add;
    private javax.swing.JButton jbt_AddMaC_selectEmp;
    private javax.swing.JButton jbt_AddMac_Clear;
    private javax.swing.JButton jbt_AddOrderAdd;
    private javax.swing.JButton jbt_AddOrderClear;
    private javax.swing.JButton jbt_AddOrderCustomerSelect;
    private javax.swing.JButton jbt_AddOrderEmpSelect;
    private javax.swing.JButton jbt_AddOrderGetOrderId;
    private javax.swing.JButton jbt_AddOrderHerbSelect;
    private javax.swing.JToggleButton jbt_Search_Search;
    private javax.swing.JButton jbt_addCustomer;
    private javax.swing.JButton jbt_addEmployee;
    private javax.swing.JButton jbt_addHerb;
    private javax.swing.JButton jbt_addMachine;
    private javax.swing.JButton jbt_addOrder;
    private javax.swing.JButton jbt_edit;
    private javax.swing.JButton jbt_logout;
    private javax.swing.JButton jbt_search;
    private javax.swing.JComboBox<String> jcbb_Search_Month;
    private javax.swing.JComboBox<String> jcbb_Search_Warehouse;
    private javax.swing.JComboBox<String> jcbb_Search_Year;
    private javax.swing.JComboBox<String> jcbb_query;
    private javax.swing.JLabel jlb_Search_cusName;
    private javax.swing.JLabel jlb_Search_empNo;
    private javax.swing.JLabel jlb_Search_herbName;
    private javax.swing.JLabel jlb_Search_mcNo;
    private javax.swing.JLabel jlb_Search_month;
    private javax.swing.JLabel jlb_Search_n;
    private javax.swing.JLabel jlb_Search_orderId;
    private javax.swing.JLabel jlb_Search_whNo;
    private javax.swing.JLabel jlb_Search_year;
    private javax.swing.JLabel jlb_date;
    private javax.swing.JLabel jlb_logo;
    private javax.swing.JPanel jpn_Search;
    private javax.swing.JPanel jpn_SearchChoice;
    private javax.swing.JPanel jpn_addCustomer;
    private javax.swing.JPanel jpn_addEmployee;
    private javax.swing.JPanel jpn_addHerb;
    private javax.swing.JPanel jpn_addMachine;
    private javax.swing.JPanel jpn_addOrder;
    private javax.swing.JPanel jpn_logo;
    private javax.swing.JPanel jpn_mainFunc;
    private javax.swing.JPanel jpn_mainTable;
    private javax.swing.JPanel jpn_subFunc;
    private javax.swing.JTable jtb_presentTable;
    private javax.swing.JTextField jtf_AddCusAddr;
    private javax.swing.JTextField jtf_AddCusID;
    private javax.swing.JTextField jtf_AddCusName;
    private javax.swing.JTextField jtf_AddEmp_ID;
    private javax.swing.JTextField jtf_AddEmp_Name;
    private javax.swing.JTextField jtf_AddHerb_HerbId;
    private javax.swing.JTextField jtf_AddHerb_HerbName;
    private javax.swing.JTextField jtf_AddHerb_HerbPrice;
    private javax.swing.JTextField jtf_AddMaC_EmpId;
    private javax.swing.JTextField jtf_AddMaC_MCId;
    private javax.swing.JTextField jtf_AddOrder_cusID;
    private javax.swing.JTextField jtf_AddOrder_empID;
    private javax.swing.JTextField jtf_AddOrder_herbID;
    private javax.swing.JTextField jtf_AddOrder_herbWeight;
    private javax.swing.JTextField jtf_AddOrder_orderID;
    private javax.swing.JTextField jtf_Search_CusName;
    private javax.swing.JTextField jtf_Search_EmpNo;
    private javax.swing.JTextField jtf_Search_HerbName;
    private javax.swing.JTextField jtf_Search_MachineNo;
    private javax.swing.JTextField jtf_Search_OrderID;
    private javax.swing.JTextField jtf_Search_n;
    // End of variables declaration//GEN-END:variables
}
