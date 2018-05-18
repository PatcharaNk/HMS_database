package HMS.GUI;

import HMS.test.HMSdatabase_manager;
import java.awt.Toolkit;

public class editTable extends javax.swing.JFrame {

    HMSdatabase_manager database;
    String[] tableName;
    private javax.swing.JPanel[] fuctionPanel;

    public editTable(HMSdatabase_manager database) {
        initComponents();
        initListener();

        this.setLocationRelativeTo(null);
        this.database = database;
        fuctionPanel = new javax.swing.JPanel[]{jpn_mdf_OrderHasHerb, jpn_mdf_customer,
            jpn_mdf_employee, jpn_mdf_herb, jpn_mdf_machine, jpn_mdf_order, jpn_mdf_warehouse, jpn_mdf_blank};

        setTheme();
    }

    private void setTheme() {
        selectFuctionPanel(jpn_mdf_customer);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Constant.MINILOGO_HMS)));
        //<editor-fold defaultstate="collapsed" desc="set jcbb_selectTable">
        String[] nameTable = new String[]{"customer", "employee", "herb",
            "machine", "order_", "order_has_herb", "warehouse"};
        jcbb_selectTable.setModel(new javax.swing.DefaultComboBoxModel<>(nameTable));
        jtb_table.setModel(database.getNormalTableModel("customer"));
//</editor-fold>
    }
    private void clearTextField(){
        jtf_Emp_empId.setText("");
        jtf_Emp_empName.setText("");
        jtf_customer_cusAddr.setText("");
        jtf_customer_cusId.setText("");
        jtf_customer_cusName.setText("");
        jtf_herb_herbId.setText("");
        jtf_herb_herbName.setText("");
        jtf_herb_price.setText("");
        jtf_machine_empId.setText("");
        jtf_machine_mcNo.setText("");
        jtf_orderH_herbId.setText("");
        jtf_orderH_orderId.setText("");
        jtf_orderH_total.setText("");
        jtf_orderH_weight.setText("");
        jtf_order_cusId.setText("");
        jtf_order_empId.setText("");
        jtf_order_orderDate.setText("");
        jtf_order_orderId.setText("");
        jtf_warehouse_herbId.setText("");
        jtf_warehouse_orderId.setText("");
        jtf_warehouse_whNo.setText("");
    }

    private void initListener() {
        jcbb_selectTable.addActionListener((ActionEvent) -> {
            int nTable = jcbb_selectTable.getSelectedIndex();
            if (nTable == 0) {
                selectFuctionPanel(jpn_mdf_customer);
                jtb_table.setModel(database.getNormalTableModel("customer"));
            } else if (nTable == 1) {
                selectFuctionPanel(jpn_mdf_employee);
                jtb_table.setModel(database.getNormalTableModel("employee"));
            } else if (nTable == 2) {
                selectFuctionPanel(jpn_mdf_herb);
                jtb_table.setModel(database.getNormalTableModel("herb"));
            } else if (nTable == 3) {
                selectFuctionPanel(jpn_mdf_machine);
                jtb_table.setModel(database.getNormalTableModel("machine"));
            } else if (nTable == 4) {
                selectFuctionPanel(jpn_mdf_order);
                jtb_table.setModel(database.getNormalTableModel("order_"));
            } else if (nTable == 5) {
                selectFuctionPanel(jpn_mdf_OrderHasHerb);
                jtb_table.setModel(database.getNormalTableModel("order_has_herb"));
            } else if (nTable == 6) {
                selectFuctionPanel(jpn_mdf_warehouse);
                jtb_table.setModel(database.getNormalTableModel("warehouse"));
            }
        });
        jtb_table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int nTable = jcbb_selectTable.getSelectedIndex();
                setValueToTextField(nTable);
            }
        });
        jbt_finish.addActionListener((ActionEvent) -> {
            this.setVisible(false);
            clearTextField();
        });
        jbt_delete.addActionListener((ActionEvent) -> {
            int nTable = jcbb_selectTable.getSelectedIndex();
            deleteTupple(nTable);
            clearTextField();
        });
        jbt_modify.addActionListener((ActionEvent) -> {
            int nTable = jcbb_selectTable.getSelectedIndex();
            modifyTupple(nTable);
            clearTextField();
        });

    }

    public void selectFuctionPanel(javax.swing.JPanel panel) {
        for (int i = 0; i < fuctionPanel.length; i++) {
            if (fuctionPanel[i].equals(panel)) {
                fuctionPanel[i].setVisible(true);
            } else {
                fuctionPanel[i].setVisible(false);
            }
        }
    }

    /**
     * methodนี้ จะเซ็ตต่าจาก table ที่เราคลิกว่าเลือกแถวไหน ใส่ลงใน textfield
     *
     * @param nTable
     */
    public void setValueToTextField(int nTable) { //and ban textfield that can't edit
        int rowSelect = jtb_table.getSelectedRow();
        if (nTable == 0) {
            jtf_customer_cusId.setText((String) jtb_table.getValueAt(rowSelect, 0));
            jtf_customer_cusName.setText((String) jtb_table.getValueAt(rowSelect, 1));
            jtf_customer_cusAddr.setText((String) jtb_table.getValueAt(rowSelect, 2));
            jtf_customer_cusId.setEditable(false);
        } else if (nTable == 1) {
            jtf_Emp_empId.setText((String) jtb_table.getValueAt(rowSelect, 0));
            jtf_Emp_empName.setText((String) jtb_table.getValueAt(rowSelect, 1));
            jtf_Emp_empId.setEditable(false);
        } else if (nTable == 2) {
            jtf_herb_herbId.setText((String) jtb_table.getValueAt(rowSelect, 0));
            jtf_herb_herbName.setText((String) jtb_table.getValueAt(rowSelect, 1));
            jtf_herb_price.setText((String) jtb_table.getValueAt(rowSelect, 2));
            jtf_herb_herbId.setEditable(false);
        } else if (nTable == 3) {
            jtf_machine_mcNo.setText((String) jtb_table.getValueAt(rowSelect, 0));
            jtf_machine_empId.setText((String) jtb_table.getValueAt(rowSelect, 1));
            jtf_machine_mcNo.setEditable(false);
        } else if (nTable == 4) {
            jtf_order_cusId.setText((String) jtb_table.getValueAt(rowSelect, 0));
            jtf_order_orderId.setText((String) jtb_table.getValueAt(rowSelect, 1));
            jtf_order_orderDate.setText((String) jtb_table.getValueAt(rowSelect, 2));
            jtf_order_empId.setText((String) jtb_table.getValueAt(rowSelect, 3));
            jtf_order_cusId.setEditable(false);
            jtf_order_orderId.setEditable(false);
            jtf_order_orderDate.setEditable(false);
        } else if (nTable == 5) {
            jtf_orderH_orderId.setText((String) jtb_table.getValueAt(rowSelect, 0));
            jtf_orderH_herbId.setText((String) jtb_table.getValueAt(rowSelect, 1));
            jtf_orderH_weight.setText((String) jtb_table.getValueAt(rowSelect, 2));
            jtf_orderH_total.setText((String) jtb_table.getValueAt(rowSelect, 3));
            jtf_orderH_orderId.setEditable(false);
        } else if (nTable == 6) {
            jtf_warehouse_orderId.setText((String) jtb_table.getValueAt(rowSelect, 0));
            jtf_warehouse_herbId.setText((String) jtb_table.getValueAt(rowSelect, 1));
            jtf_warehouse_whNo.setText((String) jtb_table.getValueAt(rowSelect, 2));
            jtf_warehouse_orderId.setEditable(false);
            jtf_warehouse_herbId.setEditable(false);
        }
    }

    public void deleteTupple(int nTable) {
        int rowSelect = jtb_table.getSelectedRow();
        if (nTable == 0) {// customer
            String cus_ID = (String) jtb_table.getValueAt(rowSelect, 0);
            database.ManagerExcecute("delete from customer where cus_ID = '" + cus_ID + "'");
        } else if (nTable == 1) {// employee
            String emp_ID = (String) jtb_table.getValueAt(rowSelect, 0);
            database.ManagerExcecute("delete from employee where emp_ID = '" + emp_ID + "'");
        } else if (nTable == 2) {// herb
            String herb_ID = (String) jtb_table.getValueAt(rowSelect, 0);
            database.ManagerExcecute("delete from herb where herb_ID = '" + herb_ID + "'");
        } else if (nTable == 3) {// machine
            String machine_No = (String) jtb_table.getValueAt(rowSelect, 0);
            database.ManagerExcecute("delete from machine where machine_No = '" + machine_No + "'");
        } else if (nTable == 4) {// order_
            String order_ID = (String) jtb_table.getValueAt(rowSelect, 1);
            database.ManagerExcecute("delete from order_ where order_ID = '" + order_ID + "'");
        } else if (nTable == 5) {// order_has_herb
            String order_ID = (String) jtb_table.getValueAt(rowSelect, 0);
            String herb_ID = (String) jtb_table.getValueAt(rowSelect, 1);
            database.ManagerExcecute("delete from order_has_herb where order_ID = '" + order_ID + "' "
                    + "AND herb_ID = '" + herb_ID + "'");
        } else if (nTable == 6) {// warehouse
            String warehouse_No = (String) jtb_table.getValueAt(rowSelect, 2);
            database.ManagerExcecute("delete from warehouse where warehouse_No = '" + warehouse_No + "'");
        }
        jtb_table.setModel(database.getNormalTableModel(jcbb_selectTable.getItemAt(nTable)));
    }

    public void modifyTupple(int nTable) {
        int rowSelect = jtb_table.getSelectedRow();

        if (nTable == 0) {//customer
            database.ManagerExcecute(String.format("update customer set "
                    + "cus_Name = '%s', cus_Address = '%s' "
                    + "where cus_ID = '%s'",
                    jtf_customer_cusName.getText(), jtf_customer_cusAddr.getText(), jtb_table.getValueAt(rowSelect, 0)));
        } else if (nTable == 1) {//employee
            database.ManagerExcecute(String.format("update employee "
                    + "set emp_Name = '%s' "
                    + "where emp_ID = '%s'",
                    jtf_Emp_empName.getText(), jtb_table.getValueAt(rowSelect, 0)));
        } else if (nTable == 2) {//herb
            database.ManagerExcecute(String.format("update herb "
                    + "set herb_name = '%s', Price = '%d' "
                    + "where herb_ID = '%s' ",
                    jtf_herb_herbName.getText(), Integer.parseInt(jtf_herb_price.getText()), jtb_table.getValueAt(rowSelect, 0)));
        } else if (nTable == 3) {//machine
            database.ManagerExcecute(String.format("update machine "
                    + "set emp_ID = '%s' "
                    + "where machine_No = '%s'",
                    jtf_machine_empId.getText(), jtb_table.getValueAt(rowSelect, 0)));
        } else if (nTable == 4) {//order_
            database.ManagerExcecute(String.format("update order_ "
                    + "set emp_ID = '%s' "
                    + "where cus_ID = '%s' and order_ID = '%s'",
                    jtf_order_empId.getText(), jtb_table.getValueAt(rowSelect, 0), jtb_table.getValueAt(rowSelect, 1)));
        } else if (nTable == 5) {//order_has_herb
            database.ManagerExcecute(String.format("update order_has_herb "
                    + "set weight = '%s', total = '%s' "
                    + "where order_ID = '%s' and herb_ID = '%s'",
                    jtf_orderH_weight.getText(), jtf_orderH_total.getText(), jtb_table.getValueAt(rowSelect, 0), jtb_table.getValueAt(rowSelect, 1)));
        } else if (nTable == 6) {// warehouse
            database.ManagerExcecute(String.format("update warehouse "
                    + "set warehouse_No = '%s' "
                    + "where order_ID = '%s' and herb_ID = '%s'",
                    jtf_warehouse_whNo.getText(), jtb_table.getValueAt(rowSelect, 0), jtb_table.getValueAt(rowSelect, 1)));
        }
        jtb_table.setModel(database.getNormalTableModel(jcbb_selectTable.getItemAt(nTable)));
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

        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jcbb_selectTable = new javax.swing.JComboBox<>();
        jpn_mdf_employee = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jtf_Emp_empId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtf_Emp_empName = new javax.swing.JTextField();
        jpn_mdf_herb = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jtf_herb_herbId = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtf_herb_herbName = new javax.swing.JTextField();
        jtf_herb_price = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jpn_mdf_machine = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jtf_machine_mcNo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtf_machine_empId = new javax.swing.JTextField();
        jpn_mdf_order = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jtf_order_cusId = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jtf_order_orderId = new javax.swing.JTextField();
        jtf_order_empId = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jtf_order_orderDate = new javax.swing.JTextField();
        jpn_mdf_OrderHasHerb = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jtf_orderH_orderId = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jtf_orderH_herbId = new javax.swing.JTextField();
        jtf_orderH_total = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jtf_orderH_weight = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jpn_mdf_warehouse = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jtf_warehouse_orderId = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jtf_warehouse_herbId = new javax.swing.JTextField();
        jtf_warehouse_whNo = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jpn_mdf_blank = new javax.swing.JPanel();
        jpn_mdf_customer = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jtf_customer_cusId = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jtf_customer_cusAddr = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jtf_customer_cusName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtb_table = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jbt_delete = new javax.swing.JButton();
        jbt_modify = new javax.swing.JButton();
        jbt_finish = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(720, 600));
        setMinimumSize(new java.awt.Dimension(720, 600));
        setSize(new java.awt.Dimension(720, 600));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setToolTipText("หน้าต่างแก้ไขข้อมูล");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("เลือกตาราง:");

        jcbb_selectTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbb_selectTable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jpn_mdf_employee.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpn_mdf_employee.setMinimumSize(new java.awt.Dimension(288, 62));
        jpn_mdf_employee.setPreferredSize(new java.awt.Dimension(288, 62));
        jpn_mdf_employee.setLayout(new java.awt.GridBagLayout());

        jLabel5.setText("รหัสพนักงาน:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_employee.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_employee.add(jtf_Emp_empId, gridBagConstraints);

        jLabel6.setText("ชื่อพนักงาน:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_employee.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_employee.add(jtf_Emp_empName, gridBagConstraints);

        jpn_mdf_herb.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpn_mdf_herb.setMinimumSize(new java.awt.Dimension(288, 26));
        jpn_mdf_herb.setLayout(new java.awt.GridBagLayout());
        jpn_mdf_customer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "customer table", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 11)));

        jLabel9.setText("รหัสสมุนไพร:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_herb.add(jLabel9, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_herb.add(jtf_herb_herbId, gridBagConstraints);

        jLabel10.setText("ชื่อสมุนไพร:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_herb.add(jLabel10, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_herb.add(jtf_herb_herbName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_herb.add(jtf_herb_price, gridBagConstraints);

        jLabel11.setText("ราคา:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_herb.add(jLabel11, gridBagConstraints);

        jpn_mdf_machine.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpn_mdf_machine.setMinimumSize(new java.awt.Dimension(288, 26));
        jpn_mdf_machine.setLayout(new java.awt.GridBagLayout());
        jpn_mdf_customer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "customer table", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 11)));

        jLabel7.setText("รหัสเครื่องจักร:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_machine.add(jLabel7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_machine.add(jtf_machine_mcNo, gridBagConstraints);

        jLabel8.setText("รหัสพนักงาน:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_machine.add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_machine.add(jtf_machine_empId, gridBagConstraints);

        jpn_mdf_order.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpn_mdf_order.setMinimumSize(new java.awt.Dimension(288, 26));
        jpn_mdf_order.setLayout(new java.awt.GridBagLayout());
        jpn_mdf_customer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "customer table", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 11)));

        jLabel13.setText("รหัสลูกค้า:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_order.add(jLabel13, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_order.add(jtf_order_cusId, gridBagConstraints);

        jLabel14.setText("รหัสรายการ:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_order.add(jLabel14, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_order.add(jtf_order_orderId, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_order.add(jtf_order_empId, gridBagConstraints);

        jLabel15.setText("วันที่ทำการ:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_order.add(jLabel15, gridBagConstraints);

        jLabel22.setText("รหัสพนักงาน:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        jpn_mdf_order.add(jLabel22, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_order.add(jtf_order_orderDate, gridBagConstraints);

        jpn_mdf_OrderHasHerb.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpn_mdf_OrderHasHerb.setMinimumSize(new java.awt.Dimension(288, 26));
        jpn_mdf_OrderHasHerb.setLayout(new java.awt.GridBagLayout());
        jpn_mdf_customer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "customer table", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 11)));

        jLabel16.setText("รหัสรายการ:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_OrderHasHerb.add(jLabel16, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_OrderHasHerb.add(jtf_orderH_orderId, gridBagConstraints);

        jLabel17.setText("รหัสพนักงาน:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_OrderHasHerb.add(jLabel17, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_OrderHasHerb.add(jtf_orderH_herbId, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_OrderHasHerb.add(jtf_orderH_total, gridBagConstraints);

        jLabel18.setText("ราคารวม:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_OrderHasHerb.add(jLabel18, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_OrderHasHerb.add(jtf_orderH_weight, gridBagConstraints);

        jLabel23.setText("น้ำหนัก:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_OrderHasHerb.add(jLabel23, gridBagConstraints);

        jpn_mdf_warehouse.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpn_mdf_warehouse.setMinimumSize(new java.awt.Dimension(288, 26));
        jpn_mdf_warehouse.setLayout(new java.awt.GridBagLayout());
        jpn_mdf_customer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "customer table", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 11)));

        jLabel19.setText("รหัสรายการ:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_warehouse.add(jLabel19, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_warehouse.add(jtf_warehouse_orderId, gridBagConstraints);

        jLabel20.setText("รหัสสมุนไพร:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_warehouse.add(jLabel20, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_warehouse.add(jtf_warehouse_herbId, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_warehouse.add(jtf_warehouse_whNo, gridBagConstraints);

        jLabel21.setText("รหัสโรงงาน:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_warehouse.add(jLabel21, gridBagConstraints);

        jpn_mdf_blank.setBackground(new java.awt.Color(255, 255, 255));
        jpn_mdf_blank.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpn_mdf_blank.setMinimumSize(new java.awt.Dimension(288, 26));
        jpn_mdf_blank.setLayout(new java.awt.GridBagLayout());

        jpn_mdf_customer.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpn_mdf_customer.setMinimumSize(new java.awt.Dimension(288, 62));
        jpn_mdf_customer.setPreferredSize(new java.awt.Dimension(288, 62));
        jpn_mdf_customer.setLayout(new java.awt.GridBagLayout());

        jLabel12.setText("รหัสลูกค้า:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_customer.add(jLabel12, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_customer.add(jtf_customer_cusId, gridBagConstraints);

        jLabel24.setText("ที่อยู่ลูกค้า:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_customer.add(jLabel24, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_customer.add(jtf_customer_cusAddr, gridBagConstraints);

        jLabel25.setText("ชื่อลูกค้า:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_customer.add(jLabel25, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jpn_mdf_customer.add(jtf_customer_cusName, gridBagConstraints);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbb_selectTable, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(455, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpn_mdf_employee, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpn_mdf_herb, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpn_mdf_machine, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpn_mdf_order, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpn_mdf_OrderHasHerb, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpn_mdf_warehouse, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpn_mdf_blank, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpn_mdf_customer, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jcbb_selectTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(41, Short.MAX_VALUE)
                    .addComponent(jpn_mdf_employee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(41, Short.MAX_VALUE)
                    .addComponent(jpn_mdf_herb, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(41, Short.MAX_VALUE)
                    .addComponent(jpn_mdf_machine, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(42, Short.MAX_VALUE)
                    .addComponent(jpn_mdf_order, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(43, Short.MAX_VALUE)
                    .addComponent(jpn_mdf_OrderHasHerb, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(42, Short.MAX_VALUE)
                    .addComponent(jpn_mdf_warehouse, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(42, Short.MAX_VALUE)
                    .addComponent(jpn_mdf_blank, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(43, Short.MAX_VALUE)
                    .addComponent(jpn_mdf_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jtb_table.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtb_table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtb_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtb_table);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jbt_delete.setText("ลบ");
        jbt_delete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jbt_modify.setText("แก้ไข");
        jbt_modify.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jbt_finish.setText("จบการทำงาน");
        jbt_finish.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbt_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbt_modify, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbt_finish, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbt_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbt_finish, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbt_modify, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbt_delete;
    private javax.swing.JButton jbt_finish;
    private javax.swing.JButton jbt_modify;
    private javax.swing.JComboBox<String> jcbb_selectTable;
    private javax.swing.JPanel jpn_mdf_OrderHasHerb;
    private javax.swing.JPanel jpn_mdf_blank;
    private javax.swing.JPanel jpn_mdf_customer;
    private javax.swing.JPanel jpn_mdf_employee;
    private javax.swing.JPanel jpn_mdf_herb;
    private javax.swing.JPanel jpn_mdf_machine;
    private javax.swing.JPanel jpn_mdf_order;
    private javax.swing.JPanel jpn_mdf_warehouse;
    private javax.swing.JTable jtb_table;
    private javax.swing.JTextField jtf_Emp_empId;
    private javax.swing.JTextField jtf_Emp_empName;
    private javax.swing.JTextField jtf_customer_cusAddr;
    private javax.swing.JTextField jtf_customer_cusId;
    private javax.swing.JTextField jtf_customer_cusName;
    private javax.swing.JTextField jtf_herb_herbId;
    private javax.swing.JTextField jtf_herb_herbName;
    private javax.swing.JTextField jtf_herb_price;
    private javax.swing.JTextField jtf_machine_empId;
    private javax.swing.JTextField jtf_machine_mcNo;
    private javax.swing.JTextField jtf_orderH_herbId;
    private javax.swing.JTextField jtf_orderH_orderId;
    private javax.swing.JTextField jtf_orderH_total;
    private javax.swing.JTextField jtf_orderH_weight;
    private javax.swing.JTextField jtf_order_cusId;
    private javax.swing.JTextField jtf_order_empId;
    private javax.swing.JTextField jtf_order_orderDate;
    private javax.swing.JTextField jtf_order_orderId;
    private javax.swing.JTextField jtf_warehouse_herbId;
    private javax.swing.JTextField jtf_warehouse_orderId;
    private javax.swing.JTextField jtf_warehouse_whNo;
    // End of variables declaration//GEN-END:variables
}
